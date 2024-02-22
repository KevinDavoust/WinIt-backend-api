package com.cda.winit.tournament.domain.entity;

import com.cda.winit.match.domain.entity.Match;
import com.cda.winit.sport.domain.entity.Sport;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tournament {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "date", nullable = false)
        private Date date;

        @Column(name = "inscription_limit_date", nullable = false)
        private Date inscriptionLimitDate;

        @Column(name = "place", nullable = false)
        private String place;

        @Column(name = "current_number_of_participants")
        private Long currentNumberOfParticipants;

        @Column(name = "players_per_team", nullable = false)
        private int playersPerTeam;

        @Column(name = "min_number_of_teams")
        private int minNumberOfTeams;

        @Column(name = "max_number_of_teams", nullable = false)
        private int maxNumberOfTeams;

        @Column(name = "game_length", nullable = false)
        private int gameLength;

        @Column(name = "player_category")
        private String playerCategory;

        @Column(name = "privacy")
        private String privacy;

        @Column(name = "format")
        private String format;

        @Column(name = "image_url")
        private String imageUrl;

        @OneToMany(mappedBy = "tournament")
        private List<Match> matches;

        @ManyToOne
        @JoinColumn(name = "sport_id", nullable = false)
        private Sport sport;

        @Column(name = "created_At", nullable = false)
        private LocalDateTime createdAt;

        @Column(name = "updated_at")
        private LocalDateTime updatedAt;
}
