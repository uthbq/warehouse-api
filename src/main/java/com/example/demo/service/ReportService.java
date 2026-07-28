package com.example.demo.service;

import com.example.demo.dto.SalesReportDto;
import com.example.demo.dto.WriteOffReportDto;
import com.example.demo.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<WriteOffReportDto> getWriteOffReport(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            startDate = LocalDate.now().withDayOfMonth(1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        return reportRepository.getWriteOffReport(
                Date.valueOf(startDate),
                Date.valueOf(endDate)
        );
    }

    public List<SalesReportDto> getSalesReport(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            startDate = LocalDate.now().withDayOfMonth(1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        return reportRepository.getSalesReport(
                Date.valueOf(startDate),
                Date.valueOf(endDate)
        );
    }
}