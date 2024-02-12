package com.cda.winit.match.domain.entity;

import com.cda.winit.tournament.domain.entity.Tournament;
import com.cda.winit.team.domain.entity.Team;
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

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "team1_id", nullable = false)
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team2_id", nullable = false)
    private Team team2;

    @Column(name = "is_draw")
    private Boolean isDraw;

    @Column(name = "winner_team_id")
    private Long winnerTeamId;

    @Column(name = "loser_team_id")
    private Long loserTeamId;

    @Column(name = "score")
    private String score;
}
