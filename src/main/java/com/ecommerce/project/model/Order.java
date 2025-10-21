//package com.ecommerce.project.model;
//
//import com.ecommerce.project.model.enums.OrderStatus;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Positive;
//import lombok.*;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "orders")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long orderId;
//
//    @Email(message = "Email should be valid")
//    @NotBlank(message = "Email is mandatory")
//    private String email;
//
//    @NotNull(message = "Order date is required")
//    private LocalDate orderDate;
//
//    @NotNull(message = "Total amount is required")
//    @Positive(message = "Total amount must be greater than 0")
//    private Double totalAmount;
//
//    @NotNull(message = "Order status is required")
//    @Enumerated(EnumType.STRING)
//    private OrderStatus orderStatus;
//
//    @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private List<OrderItem> orderItems = new ArrayList<>();
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @OneToOne(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private Payment payment;
//
//    @OneToOne
//    @JoinColumn(name = "address_id", nullable = false)
//    private Address address;
//
//}
