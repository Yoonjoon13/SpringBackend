package com.example.springbackend.user.userModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_user_name", columnNames = "NAME"),
                @UniqueConstraint(name = "uq_user_email", columnNames = "EMAIL")
        }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDX")
    private Long idx;

    @Column(name = "NAME", nullable = false, length = 100, unique = true)
    private String name;

    @Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "PASSWORD", nullable = false, length = 100)
    private String password;

    @Column(name = "ENABLED")
    private Boolean enabled;

    @Column(name = "ROLE", length = 50)
    private String role;
}
