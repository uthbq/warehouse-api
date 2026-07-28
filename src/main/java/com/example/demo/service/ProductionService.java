package com.example.demo.service;

import com.example.demo.dto.ProductionDto;
import com.example.demo.model.Stock;
import com.example.demo.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class ProductionService {

    private final StockRepository stockRepository;
    private final TransactionTemplate template;

    public ProductionService(StockRepository stockRepository, TransactionTemplate template) {
        this.stockRepository = stockRepository;
        this.template = template;
    }

    public void produceGoods(ProductionDto productionDto) {
        template.execute(status -> {
            int productId = productionDto.getProductId();
            int producedQuantity = productionDto.getQuantity();
            Stock currentStock = stockRepository.findByProductId(productId);

            if (currentStock != null) {
                int newQuantity = currentStock.getQuantity() + producedQuantity;
                stockRepository.updateQuantity(productId, newQuantity);
            } else {
                Stock newStock = new Stock(productId, producedQuantity);
                stockRepository.add(newStock);
            }

            return null;
        });
    }
}