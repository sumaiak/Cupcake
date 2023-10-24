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




}
