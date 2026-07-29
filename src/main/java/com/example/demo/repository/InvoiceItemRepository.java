package com.example.demo.repository;

import com.example.demo.model.InvoiceItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class InvoiceItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public InvoiceItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(InvoiceItem invoiceItem) {
        jdbcTemplate.update("INSERT INTO invoice_items (invoice_id, product_id, quantity, price) VALUES (?,?,?,?)",
                invoiceItem.getInvoice_id(), invoiceItem.getProduct_id(), invoiceItem.getQuantity(), invoiceItem.getPrice());
    }

    public List<InvoiceItem> findByInvoiceId(int invoice_id) {
        String sql = "SELECT * FROM invoice_items WHERE invoice_id = ?";

        return jdbcTemplate.query(sql, ((rs, rowNum) ->
                new InvoiceItem(rs.getInt("id"), rs.getInt("invoice_id")
                        , rs.getInt("product_id"), rs.getInt("quantity"),
                        rs.getInt("price"))), invoice_id);
    }

    public void saveAll(List<InvoiceItem> items) {
        String sql = "INSERT INTO invoice_items (invoice_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, items, items.size(), (PreparedStatement ps, InvoiceItem item) -> {
            ps.setInt(1, item.getInvoice_id());
            ps.setInt(2, item.getProduct_id());
            ps.setInt(3, item.getQuantity());
            ps.setInt(4, item.getPrice());
        });
    }
}
