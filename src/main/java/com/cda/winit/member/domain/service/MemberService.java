package com.cda.winit.member.domain.service;

import com.cda.winit.member.domain.dto.MemberRequestDto;
import com.cda.winit.member.domain.dto.MemberResponseDto;
import com.cda.winit.member.domain.entity.Member;
import com.cda.winit.member.domain.service.interfaces.IMemberService;
import com.cda.winit.member.infrastructure.repository.MemberRepository;
import com.cda.winit.team.domain.dto.TeamMembersWithLead;
import com.cda.winit.team.domain.entity.Team;
import com.cda.winit.member.domain.service.mapper.MemberMapper;
import com.cda.winit.team.repository.TeamRepository;
import com.cda.winit.team.repository.exception.TeamServiceException;
import com.cda.winit.user.domain.entity.User;
import com.cda.winit.user.infrastructure.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService implements IMemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final MemberMapper memberMapper;

    public void addMemberToTeam(String teamName, MemberRequestDto memberRequestDto) {
        Team team = teamRepository.findTeamByName(teamName)
                .orElseThrow(() -> new TeamServiceException("Team not found with name: " + teamName));

        User user = userRepository.findByEmail(memberRequestDto.getEmail())
                .orElseThrow(() -> new TeamServiceException("User not found with name: " + memberRequestDto.getEmail()));

        boolean isMember = memberRepository.existsByUserAndTeam(user, team);

        if (isMember) {
            throw new TeamServiceException("L'utilisateur appartient déjà à cette équipe.");
        }

        Member member = new Member();
        member.setUser(user);
        member.setTeam(team);

        memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(String teamName, String memberName) {
        Team team = teamRepository.findTeamByName(teamName)
                .orElseThrow(() -> new TeamServiceException("Team not found with name: " + teamName));

        User user = userRepository.findByFirstName(memberName)
                .orElseThrow(() -> new TeamServiceException("User not found with name: " + memberName));

        if (!team.getLeadTeamId().equals(user.getId())) {
            if (memberRepository.existsByUserAndTeam(user, team)) {
                memberRepository.deleteByUserAndTeam(user, team);
            } else {
                throw new TeamServiceException("User " + memberName + " is not a member of team " + teamName);
            }
        } else {
            throw new TeamServiceException("User " + memberName + " is the lead of team " + teamName + ". Lead cannot be removed.");
        }
    }

    public TeamMembersWithLead getAllMemberByTeamId(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));

        String leadTeamName = userRepository.findById(team.getLeadTeamId())
                .map(User::getFirstName)
                .orElseThrow(() -> new EntityNotFoundException("Lead team user not found"));

        List<Member> usersTeam = memberRepository.findAllByTeamId(teamId);

        List<Long> userIds = usersTeam.stream()
                .map(Member::getUser)
                .map(User::getId)
                .collect(Collectors.toList());

        List<User> users = userRepository.findAllById(userIds);

        List<MemberResponseDto> members = users.stream()
                .map(user -> memberMapper.toDto(user))
                .collect(Collectors.toList());

        TeamMembersWithLead response = new TeamMembersWithLead();
        response.setMembers(members);
        response.setLeadTeamName(leadTeamName);

        return response;
    }
}
