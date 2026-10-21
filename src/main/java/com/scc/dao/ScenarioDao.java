package com.scc.dao;

import com.scc.waterstation.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    public void addOrder(Order order) {
        String sql = "INSERT INTO orders (customer_name, qty_slim, qty_round, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, order.getCustomerName());
            pstmt.setInt(2, order.getQtySlim());
            pstmt.setInt(3, order.getQtyRound());
            pstmt.setString(4, order.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting order: " + e.getMessage());
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, customer_name, qty_slim, qty_round, status FROM orders";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getInt("qty_slim"),
                        rs.getInt("qty_round"),
                        rs.getString("status")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching orders: " + e.getMessage());
        }

        return orders;
    }
}