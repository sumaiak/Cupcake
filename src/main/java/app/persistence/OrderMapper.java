package app.persistence;

import app.Exception.DatabaseException;
import app.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class OrderMapper {
    public static Order insertOrder(User user, int price, String status, String date, ConnectionPool connectionPool) throws DatabaseException {
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

    public static List<Order> getAllOrdersPerUser(int order_id, ConnectionPool connectionPool) throws DatabaseException {

        List<Order> OrderList = new ArrayList<>();
        String sql = "SELECT * FROM \"Orders\" WHERE orders.order_id = ?";

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, order_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int orderid = rs.getInt("order_id");
                    int price = rs.getInt("price");
                    String status = rs.getString("status");
                    String date = rs.getString("date");


                    OrderList.add(new Order(order_id,price,status,date));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("could not get the orderlist");
        }

        return OrderList;
    }

    public static void insertOrderLine(int bottom_id, int top_id, ConnectionPool connectionpool) throws DatabaseException {
        String sql = "INSERT INTO \"Orderline\" (bottom_id, top_id) VALUES (?, ?)";
        try (Connection connection = connectionpool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bottom_id);
            ps.setInt(2, top_id);


            int rowsaffected = ps.executeUpdate();

            if (rowsaffected == 1) {
                System.out.println("A new order line was inserted !");
            }

            OrderLine orderline = new OrderLine(bottom_id, top_id);
        } catch (SQLException e) {
            throw new DatabaseException("Error inserting order line");
        }
    }

    public static void insertingOrderLineInOrder(int order_id, ConnectionPool connectionpool) throws DatabaseException {
        String sql = "INSERT INTO \"Orders\"(order_id) VALUES(?)";
        try (Connection connection = connectionpool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, order_id);

            int rowsaffected = ps.executeUpdate();

            if (rowsaffected == 1) {
                System.out.println("A new order was inserted !");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error inserting order");
        }
    }


    public static List<Bottom> bottoms( ConnectionPool connectionPool) throws DatabaseException {
        String bottomSQL = "SELECT buttom_id, bottom, price FROM \"Bottom\"";
        List<Bottom> bottoms = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement bottomPS = connection.prepareStatement(bottomSQL);
            ResultSet bottomResult = bottomPS.executeQuery();

            while (bottomResult.next()) {
                int bottomIds = bottomResult.getInt("buttom_id");
                String bottom1 = bottomResult.getString("bottom");
                int price = bottomResult.getInt("price");
                bottoms.add(new Bottom(bottomIds, bottom1, price));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());

        }

        return bottoms;
    }

    public static List<Topping> toppings(ConnectionPool connectionPool) throws DatabaseException {
        String toppingSQL = "SELECT top_id, toppings, price FROM \"Toppings\"";
        List<Topping> toppings = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement topPS = connection.prepareStatement(toppingSQL);

            ResultSet toppingResult = topPS.executeQuery();

            while (toppingResult.next()) {
                int toppingId = toppingResult.getInt("top_id");
                String toppingName = toppingResult.getString("toppings");
                int price = toppingResult.getInt("price");
                toppings.add(new Topping(toppingId, toppingName, price));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't upload the toppings from database");
        }

        return toppings;
    }
}