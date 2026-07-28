package com.example.demo.controller;

import com.example.demo.dto.SalesReportDto;
import com.example.demo.dto.WriteOffReportDto;
import com.example.demo.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Аналитика и отчеты", description = "Формирование отчетов по списаниям и продажам за выбранный период")
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(summary = "Получить отчет по списаниям",
            description = "Возвращает агрегированную аналитику по списанным товарам за указанный интервал дат " +
                    "(или за всё время, если даты не заданы)")
    @ApiResponse(responseCode = "200", description = "Отчет по списаниям успешно сформирован")
    @GetMapping("/write-offs")
    public ResponseEntity<List<WriteOffReportDto>> getWriteOffReport(
            @Parameter(description = "Начальная дата периода (формат YYYY-MM-DD)", example = "2026-01-01")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Конечная дата периода (формат YYYY-MM-DD)", example = "2026-12-31")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<WriteOffReportDto> report = reportService.getWriteOffReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    @Operation(summary = "Получить отчет по продажам",
            description = "Возвращает информацию о проданном" +
                    " и количестве проданных товаров по магазинам за выбранный период")
    @ApiResponse(responseCode = "200", description = "Отчет по продажам успешно сформирован")
    @GetMapping("/sales")
    public ResponseEntity<List<SalesReportDto>> getSalesReport(
            @Parameter(description = "Начальная дата периода (формат YYYY-MM-DD)", example = "2026-01-01")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Конечная дата периода (формат YYYY-MM-DD)", example = "2026-12-31")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<SalesReportDto> report = reportService.getSalesReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }
}