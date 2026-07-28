package com.example.demo.controller;

import com.example.demo.model.Stock;
import com.example.demo.repository.StockRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Складские остатки", description = "Просмотр текущего количества товаров на центральном складе")
@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockRepository stockRepository;

    public StockController(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @GetMapping
    @Operation(summary = "Получить остатки всех товаров",
            description = "Возвращает полный список текущих остатков всех товаров на центральном складе")
    @ApiResponse(responseCode = "200", description = "Список остатков успешно получен")
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Operation(summary = "Получить остаток товара по ID",
            description = "Возвращает текущее количество конкретного товара на складе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Остаток товара найден"),
            @ApiResponse(responseCode = "404",
                    description = "Товар с указанным ID не найден или для него нет записи на складе")
    })
    @GetMapping("/{productId}")
    public Stock getStockByProductId(@Parameter(description = "Идентификатор товара", example = "1")
                                         @PathVariable("productId") int productId) {
        return stockRepository.findByProductId(productId);
    }
}