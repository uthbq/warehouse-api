package com.example.demo.service;

import com.example.demo.dto.ProductionDto;
import com.example.demo.model.Stock;
import com.example.demo.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductionServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private TransactionTemplate template;

    @InjectMocks
    private ProductionService productionService;

    @BeforeEach
    void setUp() {
        when(template.execute(any())).thenAnswer(invocation -> {
            TransactionCallback<?> callback = invocation.getArgument(0);
            return callback.doInTransaction(null);
        });
    }

    @Test
    @DisplayName("Успешное производство: если товар уже есть на складе, обновляем его количество")
    void produceGoods_WhenStockExists_UpdatesQuantity() {
        ProductionDto dto = new ProductionDto();
        dto.setProductId(5);
        dto.setQuantity(20);
        Stock existingStock = new Stock(5, 50);
        when(stockRepository.findByProductId(5)).thenReturn(existingStock);

        assertDoesNotThrow(() -> productionService.produceGoods(dto));

        verify(stockRepository, times(1)).updateQuantity(5, 70);

        verify(stockRepository, never()).add(any());
    }

    @Test
    @DisplayName("Успешное производство: если товара нет на складе, создаем новую запись")
    void produceGoods_WhenStockDoesNotExist_CreatesNewStock() {
        ProductionDto dto = new ProductionDto();
        dto.setProductId(10);
        dto.setQuantity(100);
        when(stockRepository.findByProductId(10)).thenReturn(null);

        assertDoesNotThrow(() -> productionService.produceGoods(dto));

        verify(stockRepository, times(1)).add(argThat(stock ->
                stock.getProduct_id() == 10 && stock.getQuantity() == 100
        ));

        verify(stockRepository, never()).updateQuantity(anyInt(), anyInt());
    }
}