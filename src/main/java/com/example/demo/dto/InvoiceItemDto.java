package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public class InvoiceItemDto {

    @Schema(description = "Идентификатор товара", example = "10")
    @Positive
    private int productId;
    @Schema(description = "Количество товара", example = "3")
    @Min(value = 1)
    private int quantity;

    public InvoiceItemDto(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
