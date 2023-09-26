package yildirim.dbclientapp.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import yildirim.dbclientapp.dao.*;
import yildirim.dbclientapp.model.Appointments;
import yildirim.dbclientapp.model.Contacts;
import yildirim.dbclientapp.model.Customers;
import yildirim.dbclientapp.model.Users;
import yildirim.dbclientapp.utility.TimeUtility;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the Modify appointment controller class */

public class ModifyAppointment implements Initializable {

    public GridPane gridPaneModApp;
    public TextField txtAppID;
    public TextField txtDescription;
    public TextField txtLocation;
    public TextField txtType;
    public Button btnSave;
    public Button btnCancel;
    public Label lblModifyAppointment;
    public DatePicker datePickerModifyAppStartDate;
    public DatePicker datePickerModifyAppEndDate;
    public ComboBox<LocalTime> comboModifyAppStartTime;
    public ComboBox<LocalTime> comboModifyAppEndTime;
    public ComboBox<Users> comboModifyAppUserID;
    public Label lblAppCustomerID;
    public TextField txtAppTitle;
    public ComboBox<Contacts> comboModifyAppContactName;
    public ComboBox<Customers> comboModifyAppCustomerName;
    private Appointments modifyAppointment;

    /**
     * The method for setting appointments.
     * @param appointment appointment parameter.
     */

    public void setAppointment(Appointments appointment) {

        modifyAppointment = appointment;
        txtAppID.setText(Integer.toString(appointment.getAppointmentID()));
        txtAppTitle.setText(appointment.getAppointmentTitle());
        txtDescription.setText(appointment.getAppointmentDescription());
        txtLocation.setText(appointment.getAppointmentLocation());
        txtType.setText(appointment.getAppointmentType());
        datePickerModifyAppStartDate.setValue(modifyAppointment.getStart().toLocalDate());
        datePickerModifyAppEndDate.setValue(modifyAppointment.getEnd().toLocalDate());
        comboModifyAppStartTime.setValue(modifyAppointment.getStart().toLocalTime());
        comboModifyAppEndTime.setValue(modifyAppointment.getEnd().toLocalTime());
        try {
            comboModifyAppUserID.setValue(DBUsers.getUser(modifyAppointment.getUserID()));
            comboModifyAppCustomerName.setValue(DBCustomers.getCustomer(modifyAppointment.getCustomerID()));
            comboModifyAppContactName.setValue(DBContacts.findContactID(modifyAppointment.contactID));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


        /**
         * Method to save an appointment when save button is clicked.
         * @param actionEvent action event
         */

    public void btnSaveOnAction(ActionEvent actionEvent) {


            try
            {
                LocalDate date = datePickerModifyAppStartDate.getValue();
                LocalTime time = comboModifyAppStartTime.getValue();
                LocalDateTime startTime = LocalDateTime.of(date, time);

                date = datePickerModifyAppEndDate.getValue();
                time = comboModifyAppEndTime.getValue();
                LocalDateTime endTime = LocalDateTime.of(date, time);

                boolean isComplete = DBAppointments.modifyAppointment(
                        txtAppTitle.getText(),
                        txtDescription.getText(),
                        txtLocation.getText(),
                        txtType.getText(),
                        startTime,
                        endTime,
                        comboModifyAppCustomerName.getValue().getCustomerID(),
                        comboModifyAppUserID.getValue().getUserID(),
                        comboModifyAppContactName.getValue().getId(),
                        Integer.parseInt(txtAppID.getText())

                        );



                if(isComplete)
                {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Customer has been updated.");
                    Optional<ButtonType> result = alert.showAndWait();

                    if(result.isPresent() && (result.get() == ButtonType.OK))
                    {
                        try
                        {
                            Parent root = FXMLLoader.load(getClass().getResource("/yildirim/dbclientapp/view/AppointmentScreens.fxml"));
                            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root);
                            stage.setTitle("Customers");
                            stage.setScene(scene);
                            stage.show();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setContentText("Error Loading Screen.");
                            alert.showAndWait();
                        }
                    }
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "New Customer could not be created.");
                    Optional<ButtonType> result = alert.showAndWait();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

//    }
    /**
     * Method to cancel an appointment when save button is clicked.
     * @param actionEvent action event
     * @throws IOException IO Exception
     */

    public void btnCancelOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/yildirim/dbclientapp/view/AppointmentScreens.fxml"));
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        Scene scene = new Scene(root, 779, 535);
        stage.setTitle("Appointment Scheduling System");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Initializes the controller class.
     * @param url the url
     * @param resourceBundle The resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            comboModifyAppContactName.setItems(DBContacts.getAllContacts());
            comboModifyAppUserID.setItems(DBUsers.getAllUsers());
            comboModifyAppCustomerName.setItems(DBCustomers.getAllCustomers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        comboModifyAppStartTime.setItems(TimeUtility.getStartTimes());
        comboModifyAppEndTime.setItems(TimeUtility.getEndTimes());


    }
    /**
     * method for the combo box for modifying User ID
     * @param event  event
     * */
    public void comboOnActionModAppUserID(ActionEvent event) {

    }

    /**
     * method for the combo box for modifying Customer Name.
     * @param event  event
     * */
    public void comboOnActionCustomerName(ActionEvent event) {
    }

    /**
     * method for the combo box for modifying Contact ID
     * @param actionEvent  Action event
     * */
    public void comboOnActionContact(ActionEvent actionEvent) {
    }
}




