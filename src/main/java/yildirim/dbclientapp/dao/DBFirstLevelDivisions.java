package yildirim.dbclientapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import yildirim.dbclientapp.model.Countries;
import yildirim.dbclientapp.model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBFirstLevelDivisions extends FirstLevelDivisions {

    public DBFirstLevelDivisions(int divisionID, String divisionName, int country_ID) {
        super(divisionID, divisionName, country_ID);
    }
    /**
     * ObservableList that takes data from first_level_divisions table.
     * @throws SQLException SQL Exception
     * @return firstLevelDivisionsObservableList
     */

    public static ObservableList<DBFirstLevelDivisions> getAllFirstLevelDivisions(Integer selectedItem) throws SQLException {
        ObservableList<DBFirstLevelDivisions> firstLevelDivisionsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1,( selectedItem));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int country_ID = rs.getInt("COUNTRY_ID");
            DBFirstLevelDivisions firstLevelDivision = new DBFirstLevelDivisions(divisionID, divisionName, country_ID);
            firstLevelDivisionsObservableList.add(firstLevelDivision);
        }
        return firstLevelDivisionsObservableList;
    }

}
