package com.cda.winit.roaster.domain.entity;

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
public class Roaster {    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "sport_id", nullable = false)
    private Long sport_id;

    @Column(name = "is_validated")
    private Boolean isValidated;

}