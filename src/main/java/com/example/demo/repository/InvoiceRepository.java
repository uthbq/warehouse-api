package com.example.demo.repository;

import com.example.demo.model.Invoice;
import com.example.demo.model.Type;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@Repository
public class InvoiceRepository {

    private final JdbcTemplate jdbcTemplate;

    public InvoiceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int add(Invoice invoice) {
        String sql = "INSERT INTO invoices (store_id, operation, date) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, invoice.getStore_id());
                ps.setString(2, String.valueOf(invoice.getOperation()));
                ps.setDate(3, invoice.getDate());
                return ps;
            }
        }, keyHolder);
        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null && keys.containsKey("id")) {
            return ((Number) keys.get("id")).intValue();
        }

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        }

        throw new IllegalStateException("Не удалось получить сгенерированный ID для накладной");
    }

    public Invoice findById(int id) {
        String sql = "SELECT * FROM invoices WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, ((rs, rowNum) ->
                new Invoice(rs.getInt("id"), rs.getInt("store_id")
                        , Type.valueOf(rs.getString("operation")), rs.getDate("date"))), id);
    }
}
