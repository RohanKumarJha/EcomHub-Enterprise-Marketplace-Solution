package com.ecommerce.project.model;

import com.ecommerce.project.model.enums.PaymentMethod;
import com.ecommerce.project.model.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @NotNull(message = "Payment method is required")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @NotBlank(message = "Payment Gateway transaction ID is required")
    private String pgPaymentId;

    @NotNull(message = "Payment status is required")
    @Enumerated(EnumType.STRING)
    private PaymentStatus pgStatus;

    private String pgResponseMessage;

    @NotBlank(message = "Payment Gateway name is required")
    private String pgName;

    
}
