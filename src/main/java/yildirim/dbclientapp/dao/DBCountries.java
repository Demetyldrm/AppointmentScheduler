package yildirim.dbclientapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import yildirim.dbclientapp.model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class DBCountries  {

    /**
     * Creates ObservableList that queries Country_ID and Country Name from the countries database table.
     * @throws SQLException SQL Exception
     * @return countriesObservableList
     */

    public static ObservableList<Countries> getCountries() throws SQLException {
        ObservableList<Countries> countriesObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries C = new Countries(countryId, countryName);
                countriesObservableList.add((C));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return countriesObservableList;
    }

    /**
     * method for date conversion*/

    public static void checkDateConversion(){
        System.out.println("CREATE DATE TEST");
        String sql = "select Create_Date from countries";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs =ps.executeQuery();
            while (rs.next()){
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println("CD: " + ts.toLocalDateTime());
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }


}




