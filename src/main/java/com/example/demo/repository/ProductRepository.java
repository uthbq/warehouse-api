package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Product product) {
        jdbcTemplate.update("INSERT INTO products (name, price) VALUES (?, ?)",
                product.getName(), product.getPrice());
    }

    public Product findById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, ((rs, rowNum) ->
                new Product(rs.getInt("id"), rs.getString("name"),
                        rs.getInt("price"))), id);
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM products";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Product(rs.getInt("id"), rs.getString("name"),
                        rs.getInt("price")));
    }
}
