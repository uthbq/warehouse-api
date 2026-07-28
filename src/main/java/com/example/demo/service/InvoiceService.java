package com.example.demo.service;

import com.example.demo.dto.InvoiceDto;
import com.example.demo.dto.InvoiceItemDto;
import com.example.demo.exceptions.NotEnoughStockException;
import com.example.demo.model.*;
import com.example.demo.repository.InvoiceItemRepository;
import com.example.demo.repository.InvoiceRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final TransactionTemplate template;

    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceItemRepository invoiceItemRepository,
                          StockRepository stockRepository, ProductRepository productRepository, TransactionTemplate template) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.template = template;
    }

    public void executeInvoice(InvoiceDto invoiceDto) {

        template.execute(status -> {
            Invoice invoice = new Invoice(0, invoiceDto.getStoreId(), invoiceDto.getOperation(),
                    new Date(System.currentTimeMillis()));
            int generatedInvoiceId = invoiceRepository.add(invoice);
            List<InvoiceItem> itemsToSave = new ArrayList<>();
            for (InvoiceItemDto item : invoiceDto.getItems()) {
                int productId = item.getProductId();
                Product product = productRepository.findById(productId);

                InvoiceItem invoiceItem = new InvoiceItem(0, generatedInvoiceId, productId,
                        item.getQuantity(), product.getPrice());
                itemsToSave.add(invoiceItem);
                if (invoice.getOperation() == Type.SALE || invoice.getOperation() == Type.WRITE_OFF) {
                    Stock currentStock = stockRepository.findByProductId(productId);
                    if (currentStock.getQuantity() >= item.getQuantity()) {
                        int newQuantity = currentStock.getQuantity() - item.getQuantity();
                        stockRepository.updateQuantity(productId, newQuantity);
                    } else {
                        throw new NotEnoughStockException("Недостаточно товара на складе для товара ID: " + productId);
                    }
                }
            }
            invoiceItemRepository.saveAll(itemsToSave);
            return null;
        });
    }
}
