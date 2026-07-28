package com.example.demo.controller;

import com.example.demo.dto.ProductionDto;
import com.example.demo.service.ProductionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Производство", description = "Учет выпуска и производства готовой продукции")
@RestController
@RequestMapping("/api/production")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @Operation(summary = "Зафиксировать выпуск продукции",
            description = "Регистрирует факт производства товара и автоматически увеличивает его остаток на центральном складе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Продукция успешно произведена и добавлена на склад"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные в форме производства")
    })
    @PostMapping
    public ResponseEntity<String> produce(@Valid @RequestBody ProductionDto productionDto) {
        productionService.produceGoods(productionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Продукция успешно произведена и добавлена на склад!");
    }
}