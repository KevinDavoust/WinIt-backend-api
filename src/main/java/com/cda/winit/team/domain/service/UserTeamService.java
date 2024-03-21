package com.cda.winit.team.domain.service;

import com.cda.winit.team.domain.dto.MemberDto;
import com.cda.winit.team.domain.dto.TeamMembersWithLead;
import com.cda.winit.team.domain.service.mapper.MemberMapper;
import com.cda.winit.team.repository.exception.TeamServiceException;
import com.cda.winit.user.domain.entity.User;
import com.cda.winit.user.infrastructure.repository.UserRepository;
import com.cda.winit.team.domain.entity.Team;
import com.cda.winit.team.domain.entity.UserTeam;
import com.cda.winit.team.repository.TeamRepository;
import com.cda.winit.team.repository.UserTeamRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserTeamService {

    private final UserTeamRepository userTeamRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final MemberMapper memberMapper;
    public void createUserTeam(String username, Long teamId) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found with ID: " + teamId));

        UserTeam userTeam = new UserTeam();
        userTeam.setUser(user);
        userTeam.setTeam(team);

        userTeamRepository.save(userTeam);
    }

    public void createMember(String teamName, MemberDto memberDto) {
        Team team = teamRepository.findTeamByName(teamName)
                .orElseThrow(() -> new TeamServiceException("Team not found with name: " + teamName));

        User user = userRepository.findByFirstName(memberDto.getName())
                .orElseThrow(() -> new TeamServiceException("User not found with name: " + memberDto.getName()));

        boolean isMember = userTeamRepository.existsByUserAndTeam(user, team);

        if (isMember) {
            throw new TeamServiceException("L'utilisateur appartient déjà à cette équipe.");
        }

        UserTeam userTeam = new UserTeam();
        userTeam.setUser(user);
        userTeam.setTeam(team);

        userTeamRepository.save(userTeam);
    }

    @Transactional
    public void deleteMember(String teamName, String memberName) {
        Team team = teamRepository.findTeamByName(teamName)
                .orElseThrow(() -> new TeamServiceException("Team not found with name: " + teamName));

        User user = userRepository.findByFirstName(memberName)
                .orElseThrow(() -> new TeamServiceException("User not found with name: " + memberName));

        if (!team.getLeadTeamId().equals(user.getId())) {
            if (userTeamRepository.existsByUserAndTeam(user, team)) {
                userTeamRepository.deleteByUserAndTeam(user, team);
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

        List<UserTeam> usersTeam = userTeamRepository.findAllByTeamId(teamId);

        List<Long> userIds = usersTeam.stream()
                .map(UserTeam::getUser)
                .map(User::getId)
                .collect(Collectors.toList());

        List<User> users = userRepository.findAllById(userIds);

        List<MemberDto> members = users.stream()
                .map(user -> memberMapper.toDto(user))
                .collect(Collectors.toList());

        TeamMembersWithLead response = new TeamMembersWithLead();
        response.setMembers(members);
        response.setLeadTeamName(leadTeamName);

        return response;
    }
}