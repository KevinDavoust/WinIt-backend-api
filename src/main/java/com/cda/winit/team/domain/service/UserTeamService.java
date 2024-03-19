package com.cda.winit.team.domain.service;

import com.cda.winit.team.domain.dto.MemberDto;
import com.cda.winit.team.domain.service.mapper.MemberMapper;
import com.cda.winit.team.repository.exception.TeamServiceException;
import com.cda.winit.user.domain.entity.User;
import com.cda.winit.user.infrastructure.repository.UserRepository;
import com.cda.winit.team.domain.entity.Team;
import com.cda.winit.team.domain.entity.UserTeam;
import com.cda.winit.team.repository.TeamRepository;
import com.cda.winit.team.repository.UserTeamRepository;
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

    public List<MemberDto> getAllMemberByTeamId(Long teamId) {
        List<UserTeam> usersTeam = userTeamRepository.findAllByTeamId(teamId);
        System.out.println(usersTeam);

        List<Long> userIds = usersTeam.stream()
                .map(UserTeam::getUser)
                .map(User::getId)
                .collect(Collectors.toList());

        List<User> users = userRepository.findAllById(userIds);

        return users.stream()
                .map(memberMapper::toDto)
                .collect(Collectors.toList());
    }
}
