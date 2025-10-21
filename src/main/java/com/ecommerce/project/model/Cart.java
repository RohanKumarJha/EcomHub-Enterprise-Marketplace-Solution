//package com.ecommerce.project.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "carts")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Cart {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long cartId;
//
//    private Double totalPrice = 0.0;
//
//    @OneToOne
//    @JoinColumn(name = "user_id", unique = true)
//    private User user;
//
//    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private List<CartItem> cartItems = new ArrayList<>();
//
//}
