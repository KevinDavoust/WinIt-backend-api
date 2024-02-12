package com.cda.winit.roaster.domain.entity;

import com.cda.winit.auth.domain.entity.User;
import com.cda.winit.sport.domain.entity.Sport;
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
@Table(name = "`user_roaster`")
public class UserRoaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "roaster_id", nullable = false)
    private Roaster roaster;
}
