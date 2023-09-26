package yildirim.dbclientapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import yildirim.dbclientapp.dao.*;
import yildirim.dbclientapp.model.Countries;
import yildirim.dbclientapp.model.FirstLevelDivisions;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the add customer controller class*/
public class AddCustomer implements Initializable {
    public TextField txtID;
    public TextField txtCustomerName;
    public TextField txtZipCode;
    public TextField txtPhoneNum;
    public Button btnSave;
    public Button btnCancel;
    public TextField txtAddress;
    public ComboBox<FirstLevelDivisions> comboCustomerDivision;
    public ComboBox <Countries>comboCustomerCountry;


    /**
     * method to save a customer to the database.
     * Error messages for empty fields.
     * @param actionEvent action event
     */
    public void saveBtnOnAction(ActionEvent actionEvent) {
        boolean isValid = false;
        isValid = customerCheck(
                txtCustomerName.getText(),
                txtAddress.getText(),
                txtZipCode.getText(),
                txtPhoneNum.getText()
        );

        if(isValid)
        {
            try
            {
                boolean correct = DBCustomers.addCustomer(
                        txtCustomerName.getText(),
                        txtAddress.getText(),
                        txtZipCode.getText(),
                        txtPhoneNum.getText(),
                        comboCustomerDivision.getValue().getDivisionID()



                );

                if(correct)
                {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "New customer created.");
                    Optional<ButtonType> result = alert.showAndWait();

                    if(result.isPresent() && (result.get() == ButtonType.OK))
                    {
                        try
                        {
                            Parent root = FXMLLoader.load(getClass().getResource("/yildirim/dbclientapp/view/CustomerScreens.fxml"));
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

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }   isValid = customerCheck(
                txtCustomerName.getText(),
                txtAddress.getText(),
                txtZipCode.getText(),
                txtPhoneNum.getText()
        );

            if(isValid)
            {
                try
                {
                    boolean correct = DBCustomers.addCustomer(
                            txtCustomerName.getText(),
                            txtAddress.getText(),
                            txtZipCode.getText(),
                            txtPhoneNum.getText(),
                            comboCustomerDivision.getValue().getDivisionID()

                    );

                    if(correct)
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "New customer created.");
                        Optional<ButtonType> result = alert.showAndWait();

                        if(result.isPresent() && (result.get() == ButtonType.OK))
                        {
                            try
                            {
                                Parent root = FXMLLoader.load(getClass().getResource("/yildirim/dbclientapp/view/CustomerScreens.fxml"));
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

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        }

    private boolean customerCheck(String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber) {
        return true;
    }

    /**
     * Method to go back to main screen when cancel button is clicked.
     * @param actionEvent action event
     * @throws IOException IO exception
     */
    public void cancelBtnOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/yildirim/dbclientapp/view/CustomerScreens.fxml"));
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        Scene scene = new Scene(root, 762, 469);
        stage.setTitle("Appointment Scheduling System");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Controller class for AddCustomer.fxml
     * Initializes the add customer screen.
     * @param resourceBundle resource bundle
     * @param url url
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            comboCustomerCountry.setItems(DBCountries.getCountries());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Combo box for selecting the country.
     * @param event event
     */

    public void comboCountryOnAction(ActionEvent event) {
        try {
            ObservableList allFirstDivisions=DBFirstLevelDivisions.getAllFirstLevelDivisions(comboCustomerCountry.getSelectionModel().getSelectedIndex()+1);
            comboCustomerDivision.setItems(allFirstDivisions);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Combo box for selecting the division selection.
     * @param event event
     */
    public void comboDivisionOnAction(ActionEvent event) {
    }
}









