package com.example.demo.controller;

import com.example.demo.dto.InvoiceDto;
import com.example.demo.service.InvoiceService;
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

@Tag(name = "Накладные", description = "Проведение движения товаров (продажи, списания)")
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Operation(summary = "Провести новую накладную", description = "Принимает накладную и выполняет" +
            " списывание в зависимости от типа операции (SALE, WRITE_OFF)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Накладная успешно проведена"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные в накладной или " +
                    "недостаточный остаток товара на складе")
    })
    @PostMapping
    public ResponseEntity<InvoiceDto> create(@Valid @RequestBody InvoiceDto invoiceDto) {
        invoiceService.executeInvoice(invoiceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceDto);
    }
}
