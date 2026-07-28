package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public class ProductionDto {

    @Schema(description = "Идентификатор производимого товара", example = "5")
    @Positive
    private int productId;
    @Schema(description = "Количество произведенной продукции", example = "100")
    @Min(value = 1)
    private int quantity;

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
