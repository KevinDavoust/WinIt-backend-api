package com.cda.winit.team.domain.service;

import com.cda.winit.team.domain.dto.MemberDto;
import com.cda.winit.team.domain.service.mapper.MemberMapper;
import com.cda.winit.user.domain.entity.User;
import com.cda.winit.user.infrastructure.repository.UserRepository;
import com.cda.winit.team.domain.entity.Team;
import com.cda.winit.team.domain.entity.UserTeam;
import com.cda.winit.team.repository.TeamRepository;
import com.cda.winit.team.repository.UserTeamRepository;
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
                .orElseThrow(() -> new RuntimeException("Team not found with name: " + teamName));

        System.out.println(teamName);


        User user = userRepository.findByFirstName(memberDto.getName())
                .orElseThrow(() -> new RuntimeException("User not found with firstname: " + memberDto.getName()));

        System.out.println(user);

        UserTeam userTeam = new UserTeam();
        userTeam.setUser(user);
        userTeam.setTeam(team);

        userTeamRepository.save(userTeam);
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
