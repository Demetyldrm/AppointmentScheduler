package yildirim.dbclientapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import yildirim.dbclientapp.dao.DBAppointments;
import yildirim.dbclientapp.dao.DBContacts;
import yildirim.dbclientapp.dao.JDBC;
import yildirim.dbclientapp.model.Appointments;
import yildirim.dbclientapp.model.Contacts;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/** this class is for the  main appointment screen. */

public class AppointmentScreens implements Initializable {
    public RadioButton rbByWeek;
    public RadioButton rbByMonth;
    public RadioButton rbViewAll;
    public Button btnAddApp;
    public Button btnModifyApp;
    public Button btnDeleteApp;
    public Button btnCancel;
    public AnchorPane anchorPaneAppointments;
    public TableView tblViewApp;
    public TableColumn tColAppID;
    public TableColumn tColTitle;
    public TableColumn tColDescription;
    public TableColumn tColLocation;
    public TableColumn tColContact;
    public TableColumn tColType;
    public TableColumn tColStartDate;
    public TableColumn tColEndDate;
    public TableColumn tColCustomerID;
    public TableColumn tColUserID;
    public Label lblAppSchedule;
    public Label lblContactName;
    public ComboBox comboContactName;


    /**
     *
     * Initialize controls and setup an observable list.
     * @param resourceBundle resource bundle.
     * @param url url.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            Connection connection = JDBC.getConnection();

            ObservableList<Appointments> allAppointmentsList = DBAppointments.getAllAppointments();

            tColAppID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            tColTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            tColDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            tColLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            tColContact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            tColType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            tColStartDate.setCellValueFactory(new PropertyValueFactory<>("start"));
            tColEndDate.setCellValueFactory(new PropertyValueFactory<>("end"));
            tColCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            tColUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));


            tblViewApp.setItems(allAppointmentsList);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * Method for selecting the radio button
     @param actionEvent action event
     */

    public void rbOnActionByWeek(ActionEvent actionEvent) {
        try {

            ObservableList<Appointments> allAppointmentsList = DBAppointments.getAllAppointments();
            ObservableList<Appointments> appointmentsWeek = FXCollections.observableArrayList();

            LocalDateTime weekStart = LocalDateTime.now().minusWeeks(1);
            LocalDateTime weekEnd = LocalDateTime.now().plusWeeks(1);

            if (allAppointmentsList != null)
                //IDE converted forEach
                allAppointmentsList.forEach(appointment -> {
                    if (appointment.getEnd().isAfter(weekStart) && appointment.getEnd().isBefore(weekEnd)) {
                        appointmentsWeek.add(appointment);
                    }
                    tblViewApp.setItems(appointmentsWeek);
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Method for selecting the radio button
     @param actionEvent action event
     */

    public void rbOnActionByMonth(ActionEvent actionEvent) {
        try {
            ObservableList<Appointments> allAppointmentsList = DBAppointments.getAllAppointments();
            ObservableList<Appointments> appointmentsMonth = FXCollections.observableArrayList();

            LocalDateTime currentMonthStart = LocalDateTime.now().minusMonths(1);
            LocalDateTime currentMonthEnd = LocalDateTime.now().plusMonths(1);


            if (allAppointmentsList != null)
                //IDE converted to forEach
                allAppointmentsList.forEach(appointment -> {
                    if (appointment.getEnd().isAfter(currentMonthStart) && appointment.getEnd().isBefore(currentMonthEnd)) {
                        appointmentsMonth.add(appointment);
                    }
                    tblViewApp.setItems(appointmentsMonth);
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * When radio button for "All appointments" is selected.
     * @param actionEvent action event
     */
    public void rbOnActionViewAll(ActionEvent actionEvent) {
        try {
            ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();

            if (allAppointments != null)
                for (Appointments appointment : allAppointments) {
                    tblViewApp.setItems(allAppointments);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Opens AddAppointment.fxml when the add button is clicked.
     * @param actionEvent action event
     * @throws IOException IO Exception
     */
    public void btnOnActionAddApp(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/yildirim/dbclientapp/view/AddAppointment.fxml"));
        Stage stage = (Stage) btnAddApp.getScene().getWindow();
        Scene scene = new Scene(root, 569, 635);
        stage.setTitle("Appointment Scheduling System");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * The method that deletes appointment when delete button is clicked.
     * @throws IOException IO exception
     * @param event event
     */
    public void btnOnActionModifyApp(ActionEvent event) throws IOException {


        Appointments selectedAppointment = (Appointments) tblViewApp.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Please select an appointment");
            alert.showAndWait();
            return;
        }


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/yildirim/dbclientapp/view/ModifyAppointment.fxml"));
            Parent parent = loader.load();
            Stage stage;
            ModifyAppointment modifyAppointment = loader.getController();
            modifyAppointment.setAppointment(((Appointments) tblViewApp.getSelectionModel().getSelectedItem()));
            Scene scene = new Scene(parent, 518, 538);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();


        }

    /**
     * Delete appointment on button press.
     * @throws SQLException SQL Exception
     * @param actionEvent action event
     */
    public void btnOnActionDeleteApp(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointments> appointmentObservableList;
        Appointments appointmentToDelete = (Appointments) tblViewApp.getSelectionModel().getSelectedItem();
        if (appointmentToDelete == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Select an appointment to delete.");
            alert.showAndWait();
        } else if (tblViewApp.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete the selected appointment?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                try {
                    int deleteSuccess = DBAppointments.deleteAppointment(((Appointments) tblViewApp.getSelectionModel().getSelectedItem()).getAppointmentID());

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Delete Successful");
                    alert.setContentText("Appointment ID: " + appointmentToDelete.getAppointmentID() + " Type: " + appointmentToDelete.getAppointmentType() + " is deleted.");
                    alert.showAndWait();

                    appointmentObservableList = DBAppointments.getAllAppointments();
                    tblViewApp.setItems(appointmentObservableList);
                    tblViewApp.refresh();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * takes the user back to the main menu when cancel button is clicked.
     * @throws IOException IO exception
     * @param actionEvent action event
     */
        public void btnOnActionCancel (ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/yildirim/dbclientapp/view/MainScreen.fxml"));
            Stage stage = (Stage) btnAddApp.getScene().getWindow();
            Scene scene = new Scene(root, 492, 421);
            stage.setTitle("Appointment Scheduling System");
            stage.setScene(scene);
            stage.show();
        }

    }

