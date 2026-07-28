package com.example.demo.repository;

import com.example.demo.model.Store;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StoreRepository {

    private final JdbcTemplate jdbcTemplate;

    public StoreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Store store) {
        jdbcTemplate.update("INSERT INTO stores (name) VALUES (?)",
                store.getName());
    }

    public Store findById(int id) {
        String sql = "SELECT * FROM stores WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, ((rs, rowNum) ->
                new Store(rs.getInt("id"), rs.getString("name"))), id);
    }

}
