package app.persistence;

import app.Exception.DatabaseException;
import app.entities.Order;
import app.entities.OrderLine;
import app.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class OrderMapper {
    public static Order insertOrder( User user, int price, String status, String date, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO \"orders\" (user_id, price, status, date) VALUES (?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            ps.setInt(1, user.getId());
            ps.setInt(2, price);
            ps.setString(3, status);
            ps.setString(4, date);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    return new Order(newId, price, status, date);
                } else {
                    throw new DatabaseException("Couldn't get the generated order ID.");
                }
            } else {
                throw new DatabaseException("Couldn't add the new order ");
            }

        } catch (SQLException e) {
            String msg = "Error, could not create an order. Try again";

            if (e.getMessage().startsWith("ERROR: duplicate key value")) {
                msg = "The order ID is already used. Try another order ID";
            }
            throw new DatabaseException(msg);
        }
    }

    public static List<OrderLine> getAllOrdersPerUser(int order_id, ConnectionPool connectionPool) throws DatabaseException {

        List<OrderLine> OrderList = new ArrayList<>();
        String sql = "SELECT * FROM orderline WHERE orders.user_id = ?";

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, order_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int orderid = rs.getInt("order_id");
                    int toppings = rs.getInt("top_id");
                    int bottoms = rs.getInt("bottom_id");

                    OrderList.add(new OrderLine(orderid, toppings, bottoms));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting task data");
        }

        return OrderList;
    }
}