package yildirim.dbclientapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import yildirim.dbclientapp.dao.*;
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

/** this is the add appointment class*/
public class AddAppointment implements Initializable {
    public GridPane gridPaneAddAppointment1;
    public Button btnSave;
    public TextField txtID;
    public TextField txtTitle;
    public TextField txtDescription;
    public TextField txtLocation;
    public TextField txtType;
    public Button btnCancel;
    public DatePicker datePickerAppStartDate;
    public DatePicker datePickerAppEndDate;
    public Label lblAppointmentID;
    public RowConstraints gridPaneAddAppointment;
    public Label lblAppStartTime;
    public Label lblAppEndTime;
    public ComboBox<LocalTime> comboStartTime;
    public ComboBox<LocalTime> comboEndTime;
    public ComboBox<Contacts> comboContact;
    public ComboBox<Users> comboUserID;
    public ComboBox<Customers> comboCustomerName;


    /**
     * Initializes and controls the combo boxes.
     * @param url URL
     * @param resourceBundle resource bundle
     * @throws RuntimeException Runtime exception
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            comboContact.setItems(DBContacts.getAllContacts());
            comboCustomerName.setItems(DBCustomers.getAllCustomers());
            comboUserID.setItems(DBUsers.getAllUsers());
            comboStartTime.setItems(TimeUtility.getStartTimes());
            comboEndTime.setItems(TimeUtility.getEndTimes());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * The Method to go back to main screen when cancel is clicked.
     * @throws IOException IO Exception
     * @param actionEvent  action event
     */
    public void cancelBtnOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/yildirim/dbclientapp/view/AppointmentScreens.fxml"));
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        Scene scene = new Scene(root, 779, 535);
        stage.setTitle("Appointment Scheduling System");
        stage.setScene(scene);
        stage.show();

    }
    /**
     * Method to save an appointment when the save button is clicked.
     * @param actionEvent action event
     * @throws SQLException SQL exception
     */
    public void saveBtnOnAction(ActionEvent actionEvent) throws SQLException {
        boolean isValid = appointmentCheck(
                txtTitle.getText(),
                txtDescription.getText(),
                txtLocation.getText(),
                txtType.getText()

        );

        LocalDate date = datePickerAppStartDate.getValue();
        LocalTime time = comboStartTime.getValue();
        LocalDateTime startTime = LocalDateTime.of(date, time);

        date = datePickerAppEndDate.getValue();
        time = comboEndTime.getValue();
        LocalDateTime endTime = LocalDateTime.of(date, time);

        if(isValid)
        {
            try
            {
                boolean correct = DBAppointments.createAppointment(
                        txtTitle.getText(),
                        comboContact.getValue().toString(),
                        txtDescription.getText(),
                        comboUserID.getValue().getUserID(),
                        txtLocation.getText(),
                        txtType.getText(),
                        startTime,
                        endTime,
                        (comboCustomerName.getValue().getCustomerID()),
                        (comboContact.getValue().getId())

                );

                if(correct)
                {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "New appointment created.");
                    Optional<ButtonType> result = alert.showAndWait();

                    if(result.isPresent() && (result.get() == ButtonType.OK))
                    {
                        try
                        {
                            Parent root = FXMLLoader.load(getClass().getResource("/yildirim/dbclientapp/view/AppointmentScreens.fxml"));
                            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root);
                            stage.setTitle("Appointment Scheduling System   ");
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
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to create new appointment");
                    Optional<ButtonType> result = alert.showAndWait();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * method for checking errors in add appointments screen.
     @param appointmentID appointment id
     @param appointmentDescription appointment description
     @param appointmentLocation appointment location
     @param appointmentTitle appointment title
     * */


    private boolean appointmentCheck(String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentID) throws SQLException
    {
        if(comboContact.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("A contact needs to be entered.");
            alert.showAndWait();
            return false;
        }

        else if(comboCustomerName.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("A Customer ID needs to be entered.");
            alert.showAndWait();
            return false;
        }

        else if(comboStartTime.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("A Start Time needs to be entered.");
            alert.showAndWait();
            return false;
        }
        else if(comboEndTime.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("A End Time needs to be entered.");
            alert.showAndWait();
            return false;
        }
        else if(datePickerAppStartDate.getValue() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("A Start Date needs to be entered.");
            alert.showAndWait();
            return false;
        }
        else if(datePickerAppEndDate.getValue() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("An End Date needs to be entered.");
            alert.showAndWait();
            return false;
        }
        else if(appointmentTitle.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("A Title needs to be entered.");
            alert.showAndWait();
            return false;
        }
        else if(appointmentDescription.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("A Description needs to be entered.");
            alert.showAndWait();
            return false;
        }
        else if(appointmentLocation.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("A Location needs to be entered.");
            alert.showAndWait();
            return false;
        }



        LocalDate date = datePickerAppStartDate.getValue();
        LocalTime time = LocalTime.parse((CharSequence) comboStartTime.getValue().toString());
        LocalDateTime start = LocalDateTime.of(date, time);

        date = datePickerAppStartDate.getValue();
        time = LocalTime.parse((CharSequence) comboStartTime.getValue().toString());
        LocalDateTime end = LocalDateTime.of(date, time);

        if(end.isBefore(start))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("The start time needs to be before the end time.");
            alert.showAndWait();
            return false;
        }

        LocalDate dateStart = datePickerAppStartDate.getValue();
        LocalDate dateEnd = datePickerAppEndDate.getValue();

        if(!dateStart.equals(dateEnd))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("The start date and end date need to be on the same day.");
            alert.showAndWait();
            return false;
        }

        LocalDateTime appointmentStart = dateStart.atTime(LocalTime.from(start));
        LocalDateTime appointmentEnd = dateEnd.atTime(LocalTime.from(end));

       return true;

   }

     /**
      * @param actionEvent  action event
      */

        public void datePickerOnActionStartDate (ActionEvent actionEvent){
        }
        /**
         * @param actionEvent action event
         */

        public void datePickerOnActionEndDate (ActionEvent actionEvent){
        }
        /**
         @param actionEvent action event
         */

        public void comboOnActionStartTime (ActionEvent actionEvent){
        }
    /**
     @param actionEvent action event
     */

        public void comboOnActionAppEndTime (ActionEvent actionEvent){
        }

        /**
         @param event event
         */

    public void comboOnActionUserID(ActionEvent event) {
    }
    /**
     @param event event
     */
    public void comboOnActionCustomerID(ActionEvent event) {
    }
}

