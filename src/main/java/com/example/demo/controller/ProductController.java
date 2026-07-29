package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Товары", description = "Управление каталогом товаров (просмотр и добавление)")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Operation(summary = "Получить товар по ID", description = "Возвращает информацию о конкретном " +
            "товаре по его идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар успешно найден"),
            @ApiResponse(responseCode = "404", description = "Товар с указанным ID не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@Parameter(description = "Идентификатор товара", example = "1")
                                           @PathVariable int id) {
        Product product = productRepository.findById(id);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Получить список всех товаров",
            description = "Возвращает полный перечень товаров из справочника")
    @ApiResponse(responseCode = "200", description = "Список товаров успешно получен")
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Создать новый товар", description = "Добавляет новый товар в каталог")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Товар успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные (ошибка валидации)")
    })
    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        productRepository.add(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}