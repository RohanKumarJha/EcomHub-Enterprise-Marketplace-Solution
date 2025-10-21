//package com.ecommerce.project.model;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//import lombok.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "users")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long userId;
//
//    @NotBlank(message = "Username is required")
//    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
//    @Column(nullable = false)
//    private String userName;
//
//    @NotBlank(message = "Email is required")
//    @Email(message = "Invalid email format")
//    @Column(nullable = false, unique = true)
//    private String email;
//
//    @NotBlank(message = "Password is required")
//    @Size(min = 6, message = "Password must be at least 6 characters long")
//    @Column(nullable = false)
//    private String password;
//
//    public User(String userName, String email, String password) {
//        this.userName = userName;
//        this.email = email;
//        this.password = password;
//    }
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private Cart cart;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private List<Order> orders = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private List<Address> addresses = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private List<Review> reviews = new ArrayList<>();
//
//}
