package yildirim.dbclientapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import yildirim.dbclientapp.model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUsers {

    /**
     * This method sets user for the login form.
     * @param username username
     * @param password password
     */
    public static int setUser(String username, String password) {
        try {
            String sqlQuery = "SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password + "'";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getString("User_Name").equals(username)) {
                if (rs.getString("Password").equals(password)) {
                    return rs.getInt("User_ID");

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    /**
     * Method to create ObservableList to pull getAllUsers data.
     * @throws SQLException SQL Exception
     * @return allUsers
     */

    public static Users getUser(int userID) throws SQLException {

        try {

            String sql = "SELECT * FROM users WHERE user_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                //int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");

                Users user = new Users(userID, userName, password);
                return user;

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static ObservableList<Users> getAllUsers() throws SQLException {
        ObservableList<Users> allUsers = FXCollections.observableArrayList();

        try {

            String sql = "SELECT * FROM users";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");

                allUsers.add(new Users(userId, userName, password));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allUsers;
    }

}