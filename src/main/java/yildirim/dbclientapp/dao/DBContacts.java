package yildirim.dbclientapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import yildirim.dbclientapp.model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for creating ObservableList from all contacts.
 */

public class DBContacts {

    public static ObservableList<Contacts> getAllContacts() throws SQLException {
        ObservableList<Contacts> contactsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contacts contact = new Contacts(contactID, contactName, contactEmail);
            contactsObservableList.add(contact);
        }
        return contactsObservableList;
    }
    /**
     * ObservableList that gets contacts by the contact id from database .
     * @throws SQLException SQL Exception
     * @return contact
     */

    public static Contacts findContactID(int cID) throws SQLException {
        ObservableList<Contacts> contactsObservableList = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT * FROM contacts WHERE Contact_ID = ?");
        ps.setInt(1, cID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int contactID = cID;
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contacts contact = new Contacts(contactID, contactName, contactEmail);
            return contact;
        }
        return null;
    }
}
