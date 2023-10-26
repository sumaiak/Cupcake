package app.persistence;

import app.Exception.DatabaseException;
import app.entities.Cart;
import app.entities.Order;
import app.entities.OrderLine;
import app.entities.User;

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


    public static void selectingToppingAndBottom(Cart cart,int topping, int bottom, int price, ConnectionPool connectionPool) {
        String bottomSQL = "SELECT bottom_id, price FROM Bottom WHERE bottom_id = ?";
        String toppingSQL = "SELECT top_id, price FROM Toppings WHERE top_id = ?";

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement bottomPS = connection.prepareStatement(bottomSQL);
            bottomPS.setInt(1, bottom);
            ResultSet bottomResult = bottomPS.executeQuery();

            if (bottomResult.next()) {
                int bottomId = bottomResult.getInt("bottom_id");
                int bottomPrice = bottomResult.getInt("price");

                System.out.println("Selected Bottom: ID = " + bottomId + ", Price = " + bottomPrice);



            }

            PreparedStatement toppingPS = connection.prepareStatement(toppingSQL);
            toppingPS.setInt(1, topping);
            ResultSet toppingResult = toppingPS.executeQuery();

            if (toppingResult.next()) {
                int topId = toppingResult.getInt("top_id");
                double topPrice = toppingResult.getDouble("price");

                System.out.println("Selected Topping: ID = " + topId + ", Price = " + topPrice);

                Cart.addItem("Topping", topId, topPrice);
            }

            // Add the total price of the selected items to the cart
            Cart.addTotalPrice(price);

            // Assuming you have a method to display the cart contents
            Cart.displayCart();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in your application
        }
    }
