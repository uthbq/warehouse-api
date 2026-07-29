package com.example.demo.repository;

import com.example.demo.dto.SalesReportDto;
import com.example.demo.dto.WriteOffReportDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class ReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<WriteOffReportDto> getWriteOffReport(Date startDate, Date endDate) {
        String sql = """
                SELECT 
                    s.name AS store_name,
                    p.name AS product_name,
                    SUM(ii.quantity) AS total_quantity,
                    SUM(ii.quantity * ii.price) AS total_price
                FROM invoices i
                JOIN invoice_items ii ON i.id = ii.invoice_id
                JOIN stores s ON i.store_id = s.id
                JOIN products p ON ii.product_id = p.id
                WHERE i.operation = 'WRITE_OFF'
                  AND i.date BETWEEN ? AND ?
                GROUP BY s.name, p.name
                ORDER BY total_price DESC;
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new WriteOffReportDto(
                rs.getString("store_name"),
                rs.getString("product_name"),
                rs.getInt("total_quantity"),
                rs.getInt("total_price")
        ), startDate, endDate);
    }

    public List<SalesReportDto> getSalesReport(Date startDate, Date endDate) {
        String sql = """
                SELECT 
                    s.name AS store_name,
                    p.name AS product_name,
                    SUM(ii.quantity) AS total_quantity,
                    SUM(ii.quantity * ii.price) AS total_revenue
                FROM invoices i
                JOIN invoice_items ii ON i.id = ii.invoice_id
                JOIN stores s ON i.store_id = s.id
                JOIN products p ON ii.product_id = p.id
                WHERE i.operation = 'SALE'
                  AND i.date BETWEEN ? AND ?
                GROUP BY s.name, p.name
                ORDER BY total_revenue DESC;
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new SalesReportDto(
                rs.getString("store_name"),
                rs.getString("product_name"),
                rs.getInt("total_quantity"),
                rs.getInt("total_revenue")
        ), startDate, endDate);
    }
}