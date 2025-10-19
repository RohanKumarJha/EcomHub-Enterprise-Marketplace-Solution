package com.ecommerce.project.model;

import com.ecommerce.project.model.enums.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Role name must not be blank")
    @Size(min = 4, max = 20, message = "Role name must be between 4 and 20 characters")
    private RoleType roleName;

    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<User> users = new HashSet<>();

}