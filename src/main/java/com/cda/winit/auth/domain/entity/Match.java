package com.cda.winit.auth.domain.entity;

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
@Table(name = "`match`")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_one_id", nullable = false)
    private Long teamOneId;

    @Column(name = "team_two_id", nullable = false)
    private Long teamTwoId;

    @Column(name = "tournament_id", nullable = false)
    private Long tournamentId;

    @Column(name = "is_draw")
    private Boolean isDraw;

    @Column(name = "winner_team_id")
    private Long winnerTeamId;

    @Column(name = "loser_team_id")
    private Long loserTeamId;

    @Column(name = "score")
    private String score;
}
