package com.example.demo.service;

import com.example.demo.dto.InvoiceDto;
import com.example.demo.dto.InvoiceItemDto;
import com.example.demo.exceptions.NotEnoughStockException;
import com.example.demo.model.Product;
import com.example.demo.model.Stock;
import com.example.demo.model.Store;
import com.example.demo.model.Type;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StockRepository;
import com.example.demo.repository.StoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class InvoiceServiceTest {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("Успешное проведение продажи (SALE)")
    void shouldExecuteSaleInvoiceSuccessfully() {
        storeRepository.add(new Store(0, "Магазин №1"));

        Integer storeId = jdbcTemplate.queryForObject(
                "SELECT id FROM stores WHERE name = ?", Integer.class, "Магазин №1");

        productRepository.add(new Product(0, "Прага", 500));

        Integer productId = jdbcTemplate.queryForObject(
                "SELECT id FROM products WHERE name = ?", Integer.class, "Прага");

        stockRepository.add(new Stock(productId, 10));

        InvoiceItemDto itemDto = new InvoiceItemDto(productId, 3);
        InvoiceDto invoiceDto = new InvoiceDto(storeId, Type.SALE, List.of(itemDto));

        invoiceService.executeInvoice(invoiceDto);

        Stock updatedStock = stockRepository.findByProductId(productId);
        Assertions.assertEquals(7, updatedStock.getQuantity());
    }

    @Test
    @DisplayName("Ошибка при недостатке товара на складе")
    void shouldThrowExceptionWhenNotEnoughStock() {
        storeRepository.add(new Store(0, "Магазин №2"));
        Integer storeId = jdbcTemplate.queryForObject(
                "SELECT id FROM stores WHERE name = ?", Integer.class, "Магазин №2");

        productRepository.add(new Product(0, "Наполеон", 1000));
        Integer productId = jdbcTemplate.queryForObject(
                "SELECT id FROM products WHERE name = ?", Integer.class, "Наполеон");

        stockRepository.add(new Stock(productId, 2));

        InvoiceItemDto itemDto = new InvoiceItemDto(productId, 5);
        InvoiceDto invoiceDto = new InvoiceDto(storeId, Type.SALE, List.of(itemDto));

        Assertions.assertThrows(
                NotEnoughStockException.class,
                () -> invoiceService.executeInvoice(invoiceDto)
        );
    }
}