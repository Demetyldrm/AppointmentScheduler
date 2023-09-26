package yildirim.dbclientapp.dao;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import yildirim.dbclientapp.model.Contacts;
import yildirim.dbclientapp.model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBCustomers {

    /**
     * Creates ObservableList for the Customer class.
     * @return customerList
     */

    public static ObservableList<Customers> getAllCustomers() throws SQLException {
        ObservableList<Customers> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, " +
                    "first_level_divisions.COUNTRY_ID, first_level_divisions.Division FROM customers, first_level_divisions , countries WHERE customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID ORDER BY Customer_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                int countryId = rs.getInt("COUNTRY_ID");
                String divisionName = rs.getString("Division");


                Customers c = new Customers(customerId, customerName, address, postalCode, phone, divisionId, countryId, divisionName);
                customerList.add(c);
            }
        } catch (SQLException e) {
           throw e;
        }

        return customerList;
    }

    /**
     * method for getting customer data
     * @param customerID customer id
     * @throws SQLException SQL Exception
     * */

    public static Customers getCustomer(int customerID) throws SQLException {
        ObservableList<Customers> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT C.Customer_ID, C.Customer_Name, C.Address, C.Postal_Code, C.Phone, C.Division_ID, " +
                    "D.COUNTRY_ID, D.Division FROM customers AS C, first_level_divisions AS D, countries AS CN " +
                    "WHERE C.Division_ID = D.Division_ID AND D.Country_ID = CN.Country_ID AND C.Customer_ID = ? " +
                    "ORDER BY Customer_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1,customerID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                int countryId = rs.getInt("COUNTRY_ID");
                String divisionName = rs.getString("Division");


                Customers c = new Customers(customerId, customerName, address, postalCode, phone, divisionId, countryId, divisionName);
                return c;
            }
        } catch (SQLException e) {
            throw e;
        }

        return null;
    }


    /**
     * Method for adding customer information.
     * @param customerName customer name
     * @param phone customer phone number
     * @param address customer address
     * @param postalCode customer zip code
     * @param divisionId  customer division id
     */

    public static boolean addCustomer(String customerName, String address, String postalCode, String phone, int divisionId) {
        try {

            String sql = "INSERT INTO customers VALUES (NULL, ?, ?, ?, ?, NOW(), 'RZ', NOW(), 'RZ', ?)";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionId);
            System.out.println(ps);

            ps.executeUpdate();
            return true;


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }
    /**
     * Method that modifies customer.
     * @param address address
     * @param customerID customer id
     * @param phone customer phone number
     * @param divisionID division id
     * @param customerName customer name
     * @param postalCode customer zip code
     * @return customerId
     */

    public static boolean ModifyCustomer(String customerName, String address, String postalCode, String phone, int divisionID, int customerID) {
        try {

            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";


            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionID);
            ps.setInt(6, customerID);

            ps.execute();
            return true;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * Method that deletes customer based on customer ID.
     *
     * @param customerId customer id
     * @return customerId
     */
    public static int deleteCustomer(int customerId) {
        try {

            String sql = "DELETE from appointments where Customer_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setInt(1, customerId);

            ps.execute();


            sql = "DELETE from customers where Customer_ID = ?";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            preparedStatement.setInt(1, customerId);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerId;
    }

}



