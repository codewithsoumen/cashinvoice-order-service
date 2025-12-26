package org.cashinvoice.com.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    @NotBlank(message = "customerId must not be null or empty")
    private String customerId;

    @NotBlank(message = "product must not be null or empty")
    private String product;

    @Min(value = 1, message = "amount must be greater than 0")
    private long amount;
}

