package app.persistence;

import app.Exception.DatabaseException;
import app.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static User login(String username, String password,ConnectionPool connectionPool) throws DatabaseException{
        String sql = "select * from \"user\" where username = ? and password = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int id = rs.getInt("id");
                    return new User(id, username, password);
                }else{
                    throw new DatabaseException("Error - could not login. Try again");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public static List<User> getAllUsers(ConnectionPool connectionPool) throws DatabaseException
    {
        List<User> userList = new ArrayList<>();
        String sql = "select * from \"user\"";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();

                while (rs.next())
                {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String userName = rs.getString("username");
                    String password = rs.getString("password");
                    int balance = rs.getInt("balance");
                    String role = rs.getString("role");

                    User user = new User(id,name,userName,password,balance,role);
                    userList.add(user);
                }
            }
        }

        catch (SQLException ex)
        {
            throw new DatabaseException("Could not get users from database");
        }
        return userList;
    }
    public static void createuser(String name,String username, String password, ConnectionPool connectionPool) throws DatabaseException{

        String sql = "insert into \"user\" (name,username, password) values(?,?,?)";

        try (Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){

                ps.setString(1, name);
                ps.setString(2,username);
                ps.setString(3, password);

                //her benyttes update fordi en tabel skal opdateres. i dette tilfælde opdateres tabellen med en ny bruger
                // ps.executeUpdate();  <-- der retuneres et integer

                // fremfor at køre ps.executeUpdate(); så gør vi følgende for at sikre at der reelt er sket en opdatering

                int rowsAffected = ps.executeUpdate();

                if(rowsAffected !=1){
                    throw new DatabaseException("Error, could not create an account.");
                }


            }
        } catch (SQLException e) {
            String msg = "Error, could not create an account. Try again";

            if(e.getMessage().startsWith("ERROR: duplicate key value")){
                msg = "The username is already used. Try another username";
            }
            throw new DatabaseException(msg);
        }
    }
}






