package com.example.demo.dto;

import com.example.demo.model.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class InvoiceDto {

    @Schema(description = "Идентификатор магазина", example = "1")
    @Positive
    private int storeId;
    @Schema(description = "Тип операции (SALE, WRITE_OFF)", example = "SALE")
    @NotNull
    private Type operation;
    @Schema(description = "Список позиций в накладной")
    @Valid
    @NotEmpty
    private List<InvoiceItemDto> items;

    public InvoiceDto(int storeId, Type operation, List<InvoiceItemDto> items) {
        this.storeId = storeId;
        this.operation = operation;
        this.items = items;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public Type getOperation() {
        return operation;
    }

    public void setOperation(Type operation) {
        this.operation = operation;
    }

    public List<InvoiceItemDto> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItemDto> items) {
        this.items = items;
    }
}
