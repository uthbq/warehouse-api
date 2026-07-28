package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class SalesReportDto {

    @Schema(description = "Название магазина", example = "Магазин №1")
    private String storeName;
    @Schema(description = "Наименование товара", example = "Торт Прага")
    private String productName;
    @Schema(description = "Суммарное количество проданного товара", example = "50")
    private int totalQuantity;
    @Schema(description = "Общая выручка от продаж данного товара", example = "2500")
    private int totalRevenue;

    public SalesReportDto() {
    }

    public SalesReportDto(String storeName, String productName, int totalQuantity, int totalRevenue) {
        this.storeName = storeName;
        this.productName = productName;
        this.totalQuantity = totalQuantity;
        this.totalRevenue = totalRevenue;
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

    public int getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(int totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
