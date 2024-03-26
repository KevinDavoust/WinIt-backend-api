package com.cda.winit.team.domain.service;

import com.cda.winit.member.domain.entity.Member;
import com.cda.winit.member.domain.service.MemberService;
import com.cda.winit.member.infrastructure.repository.MemberRepository;
import com.cda.winit.team.domain.dto.TeamMembersWithLead;
import com.cda.winit.team.repository.exception.TeamServiceException;
import com.cda.winit.user.domain.entity.User;
import com.cda.winit.user.infrastructure.repository.UserRepository;
import com.cda.winit.team.domain.dto.TeamDto;
import com.cda.winit.team.domain.entity.Team;
import com.cda.winit.team.domain.service.mapper.TeamMapper;
import com.cda.winit.team.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMapper teamMapper;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public void createUserTeam(String username, Long teamId) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found with ID: " + teamId));

        Member member = new Member();
        member.setUser(user);
        member.setTeam(team);

        memberRepository.save(member);
    }
    public Team mapTeamDTOToEntity(TeamDto teamDto, String username) {
        return teamMapper.toEntity(teamDto, username);
    }

    public List<TeamDto> listTeamsCreatedByUser(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));

        List<Team> teams = teamRepository.findByLeadTeamId(user.getId());
        List<TeamDto> teamDtos = new ArrayList<>();
        for (Team team : teams) {
            teamDtos.add(teamMapper.toDto(team));
        }
        return teamDtos;
    }

    public TeamDto getTeamByName(String teamName) {
        Team team = teamRepository.findTeamByName(teamName)
                .orElseThrow(() -> new RuntimeException("Team not found with name: " + teamName));

        String leadTeamName = userRepository.findById(team.getLeadTeamId())
                .map(User::getFirstName)
                .orElseThrow(() -> new EntityNotFoundException("Lead team user not found"));

        int memberCount = memberRepository.countMembersByTeamId(team.getId());

        TeamDto teamDto = teamMapper.toDto(team);
        teamDto.setNumberOfMemberInTeam(memberCount);
        teamDto.setLeadTeamName(leadTeamName);

        return teamDto;
    }

    public void saveTeam(Team team) {
        teamRepository.save(team);
    }

    public boolean verifyTeamLead(String teamName, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with name: " + username));

        Team team = teamRepository.findTeamByName(teamName)
                .orElseThrow(() -> new RuntimeException("Team not found with name: " + teamName));

        if (user != null && team != null) {
            return Objects.equals(user.getId(), team.getLeadTeamId());
        } else {
            return false;
        }
    }

    public TeamMembersWithLead memberByTeam(String teamName) {
        Team team = teamRepository.findTeamByName(teamName)
                .orElseThrow(() -> new RuntimeException("Team not found with name: " + teamName));

        return memberService.getAllMemberByTeamId(team.getId());
    }

    @Transactional
    public void deleteTeam(String teamName) {
        Team team = teamRepository.findTeamByName(teamName)
                .orElseThrow(() -> new TeamServiceException("Team not found with name: " + teamName));

        memberRepository.deleteByTeamId(team.getId());

        teamRepository.delete(team);
    }
}
