package com.cda.winit.auth.domain.entity;

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

        @Column(name = "created_At", nullable = false)
        private LocalDateTime createdAt;

        @Column(name = "inscription_limit_date", nullable = false)
        private Date inscriptionLimitDate;

        @Column(name = "city", nullable = false)
        private String city;

        @Column(name = "number_of_participants", nullable = false)
        private Long numberOfParticipants;

        @ManyToOne
        @JoinColumn(name = "sport_id", nullable = false)
        private Sport sport;

        @Column(name = "level")
        private String level;

        @Column(name = "type")
        private String type;

        @Column(name = "format")
        private String format;

        @Column(name = "image_url")
        private String image_url;

        @OneToMany(mappedBy = "tournament")
        private List<Match> matches;
}
