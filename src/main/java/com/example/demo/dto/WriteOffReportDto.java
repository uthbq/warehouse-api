package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class WriteOffReportDto {

    @Schema(description = "Название магазина", example = "Склад просрочки")
    private String storeName;
    @Schema(description = "Наименование товара", example = "Эклер")
    private String productName;
    @Schema(description = "Суммарное количество списанного товара", example = "5")
    private int totalQuantity;
    @Schema(description = "Суммарная стоимость списания", example = "400")
    private int totalPrice;

    public WriteOffReportDto() {
    }

    public WriteOffReportDto(String storeName, String productName, int totalQuantity, int totalPrice) {
        this.storeName = storeName;
        this.productName = productName;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
