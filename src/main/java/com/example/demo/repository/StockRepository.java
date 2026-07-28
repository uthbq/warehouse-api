package com.example.demo.repository;

import com.example.demo.model.Stock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StockRepository {

    private final JdbcTemplate jdbcTemplate;

    public StockRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Stock stock) {
        String sql = """
                INSERT INTO stock (product_id, quantity)
                VALUES (?, ?)
                ON CONFLICT (product_id) 
                DO UPDATE SET quantity = EXCLUDED.quantity;
                """;
        jdbcTemplate.update(sql,
                stock.getProduct_id(), stock.getQuantity());
    }

    public void updateQuantity(int productId, int newQuantity) {
        jdbcTemplate.update("UPDATE stock SET quantity = ? WHERE product_id = ?", newQuantity, productId);
    }

    public Stock findByProductId(int productId) {
        String sql = "SELECT * FROM stock WHERE product_id = ?";

        return jdbcTemplate.queryForObject(sql, ((rs, rowNum) ->
                new Stock(rs.getInt("product_id"), rs.getInt("quantity"))), productId);
    }

    public List<Stock> findAll() {
        String sql = "SELECT * FROM stock";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Stock(rs.getInt("product_id"), rs.getInt("quantity")));
    }
}
