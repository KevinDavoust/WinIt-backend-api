package com.cda.winit.member.domain.service;

import com.cda.winit.member.domain.dto.MemberRequest;
import com.cda.winit.member.domain.entity.Member;
import com.cda.winit.member.domain.service.interfaces.IMemberService;
import com.cda.winit.member.infrastructure.repository.MemberRepository;
import com.cda.winit.team.domain.entity.Team;
import com.cda.winit.team.repository.TeamRepository;
import com.cda.winit.team.repository.exception.TeamServiceException;
import com.cda.winit.user.domain.entity.User;
import com.cda.winit.user.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements IMemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public void addMemberToTeam(String teamName, MemberRequest memberRequest) {
        Team team = teamRepository.findTeamByName(teamName)
                .orElseThrow(() -> new TeamServiceException("Team not found with name: " + teamName));

        User user = userRepository.findById(memberRequest.getId())
                .orElseThrow(() -> new TeamServiceException("User not found with name: " + memberRequest.getId()));

        boolean isMember = memberRepository.existsByUserAndTeam(user, team);

        if (isMember) {
            throw new TeamServiceException("The user already belongs to this team.");
        }

        Member member = new Member();
        member.setUser(user);
        member.setTeam(team);

        memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(String teamName, Long memberId) {
        Team team = teamRepository.findTeamByName(teamName)
                .orElseThrow(() -> new TeamServiceException("Team not found with name: " + teamName));

        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new TeamServiceException("User not found with name: " + memberId));

        if (!team.getLeaderTeamId().equals(user.getId())) {
            if (memberRepository.existsByUserAndTeam(user, team)) {
                memberRepository.deleteByUserAndTeam(user, team);
            } else {
                throw new TeamServiceException("User " + memberId + " is not a member of team " + teamName);
            }
        } else {
            throw new TeamServiceException("User " + memberId + " is the lead of team " + teamName + ". Lead cannot be removed.");
        }
    }
}
