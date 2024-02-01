package com.cda.winit.auth.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Column(name = "is_draw")
    private Boolean isDraw;

    @Column(name = "winner_team_id")
    private Long winnerTeamId;

    @Column(name = "loser_team_id")
    private Long loserTeamId;

    @Column(name = "score")
    private String score;

    @OneToMany(mappedBy = "match")
    private List<MatchTeam> teams;
}
