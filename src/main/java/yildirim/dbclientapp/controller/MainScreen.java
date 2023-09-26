package yildirim.dbclientapp.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import yildirim.dbclientapp.dao.DBAppointments;
import yildirim.dbclientapp.model.Appointments;
import yildirim.dbclientapp.model.Customers;
import yildirim.dbclientapp.model.Reports;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Main screen class, a menu to go to Appointments, Customers, and Reports screens.
 */

public class MainScreen implements Initializable {
    public Button btnViewApp;
    public Button btnViewCustomer;
    public Button btnViewReports;
    public Button btnLogout;
    private static boolean firstTime =true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boolean appointmentWithin15Min = false;
        int getAppointmentID = 0;
        LocalDateTime displayTime = null;
        try {



            LocalDateTime currentTimePlus15Min = LocalDateTime.now().plusMinutes(15);
            LocalDateTime startTime;


            for (Appointments appointment : DBAppointments.getAllAppointments()) {
                startTime = appointment.getStart();
                if ((startTime.isAfter(LocalDateTime.now()) && (startTime.isBefore(currentTimePlus15Min)))) {
                    getAppointmentID = appointment.getAppointmentID();
                    displayTime = startTime;
                    appointmentWithin15Min = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (firstTime) {
            firstTime = false;


            if (appointmentWithin15Min) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment is within 15 minutes: " + getAppointmentID + " and the appointment start time is: " + displayTime);
                Optional<ButtonType> confirmation = alert.showAndWait();
                System.out.println("No appointments scheduled within 15 minutes");
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No upcoming appointments.");
                Optional<ButtonType> confirmation = alert.showAndWait();
                System.out.println("no upcoming appointments");
            }
        }

    }

    /**
     * Takes the user to the appointment menu.
     * @param actionEvent action event
     * @throws IOException IO Exception.
     */

    public void btnOnActionViewApp(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Appointments.class.getResource("/yildirim/dbclientapp/view/AppointmentScreens.fxml"));
        Scene scene1 = new Scene(fxmlLoader.load(), 779, 535);
        stage.setTitle("Appointment Screen");
        stage.setScene(scene1);
        stage.show();
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();


    }
    /**
     * Takes the user to the Customer menu.
     * @param actionEvent action event
     * @throws IOException IO Exception.
     */
    public void btnOnActionViewCustomer(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Customers.class.getResource("/yildirim/dbclientapp/view/CustomerScreens.fxml"));
        Scene scene1 = new Scene(fxmlLoader.load(), 778, 536);
        stage.setTitle("Customer Screen");
        stage.setScene(scene1);
        stage.show();
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();


    }
    /**
     * Takes the user to the report menu.
     * @param actionEvent action event
     * @throws IOException IO Exception.
     */
    public void btnOnActionViewReports(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Reports.class.getResource("/yildirim/dbclientapp/view/ReportScreen.fxml"));
        Scene scene1 = new Scene(fxmlLoader.load(), 812, 710);
        stage.setTitle("Report Screen");
        stage.setScene(scene1);
        stage.show();
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

    }

    /**
     * Exits the application when logout button is clicked.
     * @param actionEvent action event
     * @throws IOException IO Exception
     */
    public void btnOnActionLogout(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("");
        alert.setContentText("Are you sure to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
        }

        System.exit(0);
    }

}

