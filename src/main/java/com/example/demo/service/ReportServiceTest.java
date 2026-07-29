package com.example.demo.service;

import com.example.demo.dto.SalesReportDto;
import com.example.demo.dto.WriteOffReportDto;
import com.example.demo.repository.ReportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    @Test
    @DisplayName("Отчет по списаниям: когда даты переданы, репозиторий вызывается с этими датами")
    void getWriteOffReport_WithExplicitDates_CallsRepositoryWithGivenDates() {
        LocalDate startDate = LocalDate.of(2026, 1, 1);
        LocalDate endDate = LocalDate.of(2026, 1, 15);

        WriteOffReportDto dto = new WriteOffReportDto("Склад 1", "Эклер", 5, 400);
        when(reportRepository.getWriteOffReport(Date.valueOf(startDate), Date.valueOf(endDate)))
                .thenReturn(List.of(dto));

        List<WriteOffReportDto> result = reportService.getWriteOffReport(startDate, endDate);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Склад 1", result.get(0).getStoreName());
        assertEquals("Эклер", result.get(0).getProductName());

        verify(reportRepository, times(1))
                .getWriteOffReport(Date.valueOf(startDate), Date.valueOf(endDate));
    }

    @Test
    @DisplayName("Отчет по списаниям: когда даты null, подставляются даты по умолчанию (1-е число месяца и сегодня)")
    void getWriteOffReport_WithNullDates_UsesDefaultDates() {
        LocalDate expectedStart = LocalDate.now().withDayOfMonth(1);
        LocalDate expectedEnd = LocalDate.now();

        when(reportRepository.getWriteOffReport(Date.valueOf(expectedStart), Date.valueOf(expectedEnd)))
                .thenReturn(List.of());

        List<WriteOffReportDto> result = reportService.getWriteOffReport(null, null);

        assertNotNull(result);
        verify(reportRepository, times(1))
                .getWriteOffReport(Date.valueOf(expectedStart), Date.valueOf(expectedEnd));
    }

    @Test
    @DisplayName("Отчет по продажам: когда даты переданы, репозиторий вызывается с этими датами")
    void getSalesReport_WithExplicitDates_CallsRepositoryWithGivenDates() {
        // 1. GIVEN
        LocalDate startDate = LocalDate.of(2026, 2, 1);
        LocalDate endDate = LocalDate.of(2026, 2, 28);

        SalesReportDto dto = new SalesReportDto("Магазин №1", "Торт Прага", 50, 2500);
        when(reportRepository.getSalesReport(Date.valueOf(startDate), Date.valueOf(endDate)))
                .thenReturn(List.of(dto));

        List<SalesReportDto> result = reportService.getSalesReport(startDate, endDate);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Магазин №1", result.get(0).getStoreName());
        assertEquals(2500, result.get(0).getTotalRevenue());

        verify(reportRepository, times(1))
                .getSalesReport(Date.valueOf(startDate), Date.valueOf(endDate));
    }

    @Test
    @DisplayName("Отчет по продажам: когда даты null, подставляются даты по умолчанию")
    void getSalesReport_WithNullDates_UsesDefaultDates() {
        LocalDate expectedStart = LocalDate.now().withDayOfMonth(1);
        LocalDate expectedEnd = LocalDate.now();

        when(reportRepository.getSalesReport(Date.valueOf(expectedStart), Date.valueOf(expectedEnd)))
                .thenReturn(List.of());

        List<SalesReportDto> result = reportService.getSalesReport(null, null);

        assertNotNull(result);
        verify(reportRepository, times(1))
                .getSalesReport(Date.valueOf(expectedStart), Date.valueOf(expectedEnd));
    }
}