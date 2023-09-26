package yildirim.dbclientapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import yildirim.dbclientapp.model.Appointments;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static yildirim.dbclientapp.dao.JDBC.getConnection;


public class DBAppointments {

    /**
     * ObservableList for all appointments in database.
     * @throws SQLException SQL exception
     * @param contactID contact ID
     * @return appointmentsObservableList appointment observable list
     */

    public static ObservableList<Appointments> getAllAppointmentsByContact(int contactID) throws SQLException {
        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from appointments WHERE contact_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1,contactID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");
            //LocalDateTime start = convertTimeDateLocal(rs.getTimestamp("Start").toLocalDateTime());
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            //LocalDateTime end = convertTimeDateLocal(rs.getTimestamp("End").toLocalDateTime());
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactIDX = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, start, end, customerID, userID, contactIDX);
            appointmentsObservableList.add(appointment);
        }


        return appointmentsObservableList;
    }
    /**
     * ObservableList for all appointments by customer in database.
     * @throws SQLException SQL exception
     * @param customerID customer ID
     * @return appointmentsObservableList appointment observable list
     */

    public static ObservableList<Appointments> getAllAppointmentsByCustomer(int customerID) throws SQLException {
        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from appointments WHERE customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1,customerID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");
            //LocalDateTime start = convertTimeDateLocal(rs.getTimestamp("Start").toLocalDateTime());
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            //LocalDateTime end = convertTimeDateLocal(rs.getTimestamp("End").toLocalDateTime());
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerIDX = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactIDX = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, start, end, customerIDX, userID, contactIDX);
            appointmentsObservableList.add(appointment);
        }


        return appointmentsObservableList;
    }

    /**
     * ObservableList that pulls all appointments from database .
     * @throws SQLException SQL Exception
     * @return appointmentsObservableList appointment observable list
     */

    public static ObservableList<Appointments> getAllAppointments() throws SQLException {
        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");
            //LocalDateTime start = convertTimeDateLocal(rs.getTimestamp("Start").toLocalDateTime());
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            //LocalDateTime end = convertTimeDateLocal(rs.getTimestamp("End").toLocalDateTime());
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, start, end, customerID, userID, contactID);
            appointmentsObservableList.add(appointment);
        }


        return appointmentsObservableList;
    }
    /**
     * Method that deletes appointment based on appointment ID.
     * @param customer customer
     * @return result result
     * @throws SQLException SQL Exception
     */


    public static int deleteAppointment(int customer) throws SQLException {
        String query = "DELETE FROM appointments WHERE Appointment_ID=?";
        PreparedStatement ps = getConnection().prepareStatement(query);
        ps.setInt(1, customer);
        int result = ps.executeUpdate();
        //ps.close();
        return result;
    }

    /**
     * Method that adds appointment based on appointment ID.
     * @param app app
     * @return result result
     * @throws SQLException SQL Exception
     */


    public static int addAppointment(Appointments app) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PreparedStatement ps = JDBC.getConnection().prepareStatement("INSERT INTO APPOINTMENTS (Title ,Description , Location, Type , Start , End ,Create_Date, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?) ");
        ps.setString(1, app.getAppointmentTitle());
        ps.setString(2, app.getAppointmentDescription());
        ps.setString(3, app.getAppointmentLocation());
        ps.setString(4, app.getAppointmentType());
        ps.setTimestamp(5, Timestamp.valueOf(app.getStart()));
        ps.setTimestamp(6, Timestamp.valueOf(app.getEnd()));
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setInt(8, app.getCustomerID());
        ps.setInt(9, app.getUserID());
        ps.setInt(10, app.getContactID());

        int result = ps.executeUpdate();
        //ps.close();
        return result;

    }

    /**
     * Method that modifies appointment based on appointment ID.
     * @param appointment_ID app id
     * @param contact_ID contact id
     * @param customer_ID customer id
     * @param description  appointment description
     * @param end appointment end date
     * @param location  appointment location
     * @param start  appointment start date
     * @param title appointment title
     * @param type appointment type
     * @param user_ID user id
     * @return result result
     */

    public static boolean modifyAppointment (String title , String description, String location, String type, LocalDateTime start, LocalDateTime end, int customer_ID, int user_ID, int contact_ID, int appointment_ID )  {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PreparedStatement ps = null;
        try {
         //   "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?"

            ps = JDBC.getConnection().prepareStatement("UPDATE appointments SET Title = ? ,Description = ?, Location =? , Type = ? , Start = ? , End =? , Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ? " );
            ps.setString(1,title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5,Timestamp.valueOf(start) );
            ps.setTimestamp(6,Timestamp.valueOf(end));
            ps.setInt(7, customer_ID);
            ps.setInt(8, user_ID);
            ps.setInt(9, contact_ID);
            ps.setInt(10, appointment_ID);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected>0) {
                System.out.println("update passed");
                return true;
            }

        } catch (SQLException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }


        System.out.println("update failed");

        return false;


    }

    /**
     * Method to create appointment.
     * @param type appointment  type
     * @param title  appointment title
     * @param location  appointment location
     * @param description  appointment description
     * @param contactID  contact id
     * @param contactName  contact name
     * @param customerID customer id
     * @param endTime  appointment end time
     * @param startTime appointment start time
     * @param userID user id
     */

    public static boolean createAppointment(String title, String contactName, String description,
                                            int userID, String location, String type,
                                            LocalDateTime startTime, LocalDateTime endTime, int customerID,int contactID) throws SQLException {

        Appointments appointments = new Appointments(1,title,description,location,type,startTime,endTime,customerID,userID,contactID) ;

        try {
            addAppointment(appointments);


            return true;
        } catch (Exception e) {
           e.printStackTrace();
        return false;
        }
    }
    /**
     * ObservableList that pulls all types of appointments from database .
     * @throws SQLException SQL Exception
     * @return appointmentTypeList
     */

    public static ObservableList<String> getAllTypes() throws SQLException {
        ObservableList<String> appointmentTypeList = FXCollections.observableArrayList();
        String sql = "SELECT Distinct Type FROM client_schedule.appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            String appointmentType = rs.getString("Type");
            appointmentTypeList.add(appointmentType);
        }


        return appointmentTypeList;
    }

    /**
     * method to select appointment by month
     * @param type type
     * @param month month
     * @throws SQLException SQL Exception
     * @return total
     * */


    public static String getMonthType(String month, String type) throws SQLException {
        String sql = "SELECT count(*) as total FROM client_schedule.appointments WHERE type = ? AND monthName(start) = ?;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1,type);
        ps.setString(2,month);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            String total = rs.getString("total");
            return total;

        }


        return  "0";

    }
}
