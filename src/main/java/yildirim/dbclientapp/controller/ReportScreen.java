package yildirim.dbclientapp.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import yildirim.dbclientapp.dao.*;
import yildirim.dbclientapp.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Month;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

/** This is the report controller class */

public class ReportScreen implements Initializable {
    public TableView tblViewReports;
    public RadioButton rbByContact;
    public RadioButton rbByType;
    public RadioButton rbByMonth;
    public Button btnCancel;
    public Label lblUsers;
    public TableColumn tColAppID;
    public TableColumn tColTitle;
    public TableColumn tColDescription;
    public TableColumn tColType;
    public TableColumn tColStartDate;
    public TableColumn tColEndDate;
    public TableColumn tColContactID;
    public TableColumn tColLocation;
    public TableColumn tColCustomerID;
    public TableColumn tColUserID;
    public Label lblReports;
    public RadioButton rbByLocation;
    public ComboBox comboContacts;
    public ComboBox <Contacts>comboReportsContacts;
    public ObservableList<Reports> countryReportList = FXCollections.observableArrayList();
    public ObservableList<String> appointmentTypes = FXCollections.observableArrayList(
            "Planning",
            "Birthday",
            "De-Briefing",
            "Training",
            "Brain Storming",
            "Other"
    );

    public ObservableList<String> Months = FXCollections.observableArrayList(
            "January", "February", "March", "April", "May", "June", "July","August", "September", "October", "November", "December"
    );
    public TableView tblViewReportsTypeMonth;
    public TableView tblViewReportsCountry;
    public TableView tblViewReportsContacts;
    public TableColumn tColAppID1;
    public TableColumn tColTitle1;
    public TableColumn tColDescription1;
    public TableColumn tColLocation1;
    public TableColumn tColType1;
    public TableColumn tColStartDate1;
    public TableColumn tColEndDate1;
    public TableColumn tColCustomerID1;
    public TableColumn tColUserID1;
    public TableColumn tColContactID1;
    public TableColumn tColAppID11;
    public TableColumn tColTitle11;
    public TableColumn tColDescription11;
    public TableColumn tColLocation11;
    public TableColumn tColType11;
    public TableColumn tColStartDate11;
    public TableColumn tColEndDate11;
    public TableColumn tColCustomerID11;
    public TableColumn tColUserID11;
    public TableColumn tColContactID11;
    public TableColumn tColContactsAppID;
    public TableColumn tColContactsTitle;
    public TableColumn tColContactsDescription;
    public TableColumn tColContactsLocation;
    public TableColumn tColContactsType;
    public TableColumn tColContactsStartDate;
    public TableColumn tColContactsEndDate;
    public TableColumn tColContactsCustomerID;
    public TableColumn tColContactsUserID;
    public TableColumn tColContactsContactID;
    public TableView tblViewReportsContacts1;
    public TableColumn tColContactsAppID1;
    public TableColumn tColContactsTitle1;
    public TableColumn tColContactsDescription1;
    public TableColumn tColContactsLocation1;
    public TableColumn tColContactsType1;
    public TableColumn tColContactsStartDate1;
    public TableColumn tColContactsEndDate1;
    public TableColumn tColContactsCustomerID1;
    public TableColumn tColContactsUserID1;
    public TableColumn tColContactsContactID1;
    public ComboBox comboByCustomerID;
    public TableView tblViewReportsCustomerID;
    public ComboBox <String> comboByMonth;
    public ComboBox <String> comboByType;
    public Label lblCount;



    /**
     * Initialize and setup fields on the form.
     * @param url  the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ObservableList<Contacts> contactsObservableList = null;
        try {
            contactsObservableList = DBContacts.getAllContacts();

            comboReportsContacts.setItems(contactsObservableList);

            ObservableList<Customers> customersObservableList = null;

            customersObservableList = DBCustomers.getAllCustomers();

            comboByCustomerID.setItems(customersObservableList);

            ObservableList<String> monthList = FXCollections.observableArrayList("January", "February","March","April","May","June","July","August","September","October","November","December");
            comboByMonth.setItems(monthList);

            comboByType.setItems(DBAppointments.getAllTypes());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Cancel Button to go back to the main menu.
     * @param actionEvent action event
     * @throws IOException IO Exception.
     */
    public void btnOnActionCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/yildirim/dbclientapp/view/MainScreen.fxml"));
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        Scene scene = new Scene(root, 492, 421);
        stage.setTitle("Appointment Scheduling System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Combo box for pulling the reports by the contact name.
     * @param event event
     */

    public void onActionComboReportContact(ActionEvent event) {

            try {
                Connection connection = JDBC.getConnection();
                Contacts c = comboReportsContacts.getValue();

                tColContactsAppID.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
                tColContactsTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
                tColContactsDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
                tColContactsLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
                tColContactsType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
                tColContactsStartDate.setCellValueFactory(new PropertyValueFactory<>("start"));
                tColContactsEndDate.setCellValueFactory(new PropertyValueFactory<>("end"));
                tColContactsCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
                tColContactsUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
                tColContactsContactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));

                tblViewReportsContacts.setItems(DBAppointments.getAllAppointmentsByContact(c.getId()));


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    /**
     * Combo box for pulling the reports by the Customer name.
     * @param event event
     */

    public void onActionComboCustomerID(ActionEvent event) {

        try {
            Connection connection = JDBC.getConnection();
            Customers c = (Customers) comboByCustomerID.getValue();

            tColCustomerID11.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
            tColTitle11.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            tColDescription11.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            tColLocation11.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            tColType11.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            tColStartDate11.setCellValueFactory(new PropertyValueFactory<>("start"));
            tColEndDate11.setCellValueFactory(new PropertyValueFactory<>("end"));
            tColCustomerID11.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            tColUserID11.setCellValueFactory(new PropertyValueFactory<>("userID"));
            tColContactID11.setCellValueFactory(new PropertyValueFactory<>("contactID"));

            tblViewReportsCustomerID.setItems(DBAppointments.getAllAppointmentsByCustomer(c.getCustomerID()));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Combo box for pulling the reports by month.
     * @param event event
     * @throws SQLException SQL Exception
     */

    public void onActionTypeMonth(ActionEvent event) throws SQLException {
        String month = comboByMonth.getValue();
        String type = comboByType.getValue();
        if (month==null){
            return;

        }
        if (type==null){
            return;
        }
        lblCount.setText(DBAppointments.getMonthType(month,type));

    }
}


