package com.fashionstore.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OrderRequest(@NotBlank String customerName, @Email @NotBlank String email,
                           @NotBlank String shippingAddress, @NotBlank String phone,
                           @NotEmpty List<@Valid Item> items) {
    public record Item(@NotNull Long productId, @Min(1) int quantity) {}
}
