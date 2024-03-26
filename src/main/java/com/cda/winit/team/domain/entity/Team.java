package com.cda.winit.team.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "sport_id", nullable = false)
    private Long sport_id;

    @Column(name = "lead_team_id", nullable = false)
    private Long leaderTeamId;

    @Column(name = "is_validated")
    private Boolean isValidated;
}
