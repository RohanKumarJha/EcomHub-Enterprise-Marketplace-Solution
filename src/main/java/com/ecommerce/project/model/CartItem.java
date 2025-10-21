//package com.ecommerce.project.model;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.DecimalMax;
//import jakarta.validation.constraints.DecimalMin;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "cartItems")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class CartItem {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long cartItemId;
//
//    @NotNull(message = "Quantity is required")
//    @Min(value = 1, message = "Quantity must be at least 1")
//    @Column(nullable = false)
//    private Integer quantity;
//
//    @DecimalMin(value = "0.0", message = "Discount cannot be negative")
//    @DecimalMax(value = "100.0", message = "Discount cannot exceed 100%")
//    @Column(nullable = false)
//    private double discount;
//
//    @NotNull(message = "Product price is required")
//    @DecimalMin(value = "0.0", inclusive = false, message = "Product price must be greater than 0")
//    @Column(nullable = false)
//    private double productPrice;
//
//    public CartItem(Integer quantity, double discount, double productPrice) {
//        this.quantity = quantity;
//        this.discount = discount;
//        this.productPrice = productPrice;
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "product_id", nullable = false)
//    private Product product;
//
//    @ManyToOne
//    @JoinColumn(name = "cart_id", nullable = false)
//    private Cart cart;
//
//}
