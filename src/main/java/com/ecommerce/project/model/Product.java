package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String productName;

    @NotBlank(message = "Image URL is required")
    @Column(nullable = false)
    private String image;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    @Column(nullable = false, length = 1000)
    private String description;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    @Column(nullable = false)
    private Integer quantity;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(nullable = false)
    private double price;

    @DecimalMin(value = "0.0", message = "Discount cannot be negative")
    @DecimalMax(value = "100.0", message = "Discount cannot exceed 100%")
    private double discount;

    @DecimalMin(value = "0.0", message = "Special price cannot be negative")
    private double specialPrice;

    public Product(String productName, String image, String description, Integer quantity, double price, double discount, double specialPrice) {
        this.productName = productName;
        this.image = image;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.specialPrice = specialPrice;
    }

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

}
