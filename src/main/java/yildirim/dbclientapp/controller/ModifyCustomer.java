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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import yildirim.dbclientapp.dao.*;
import yildirim.dbclientapp.model.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.EventObject;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is Modify customer controller class. */

public class ModifyCustomer implements Initializable {

    public TextField txtAddress;
    Stage stage;
    Parent scene;

    private static Customers selectedCustomer;
    private static int selectedCountryID;
    private static Countries selectedCountry;

    public GridPane gridPaneModCus;
    public TextField txtCustomerID;
    public TextField txtCustomerName;
    public TextField txtZipCode;
    public TextField txtPhoneNumber;
    public ComboBox<Countries> comboModifyCountry;
    public ComboBox<FirstLevelDivisions> comboModifyDivision;
    public Button btnSave;
    public Button btnCancel;
    private Customers modifyCustomer;

    private FirstLevelDivisions firstLevelDivisions;
    /**
     * method for pulling the customer data on to the modify customer screen.
     * @param customer customer
     * @throws SQLException SQL Exception
     * */

    public void setCustomer(Customers customer) throws SQLException {
        modifyCustomer = customer;
        txtCustomerID.setText(Integer.toString(customer.getCustomerID()));
        txtCustomerName.setText(customer.getCustomerName());
        txtAddress.setText(customer.getCustomerAddress());
        txtZipCode.setText(customer.getCustomerPostalCode());
        txtPhoneNumber.setText(customer.getCustomerPhone().toString());


        //lambda expression 1
        comboModifyCountry.getItems().stream()
                .filter(country -> customer.getCountryID() == country.getCountryID())
                .findFirst()
                .ifPresent(country -> comboModifyCountry.getSelectionModel().select(country));


        try {
            ObservableList allFirstDivisions=DBFirstLevelDivisions.getAllFirstLevelDivisions(modifyCustomer.getCountryID());
            comboModifyDivision.setItems(allFirstDivisions);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        comboModifyDivision.getItems().stream()
                .filter(division -> customer.getCustomerDivisionID() == division.getDivisionID())
                .findFirst()
                .ifPresent(division -> comboModifyDivision.getSelectionModel().select(division));

    }
    /**
     * Method to save a customer when save button is clicked.
     * @param actionEvent action event
     */


    public void btnSaveOnAction(ActionEvent actionEvent) {

        boolean isValid = emptyCheck(
                txtCustomerName.getText(),
                txtAddress.getText(),
                txtZipCode.getText(),
                txtPhoneNumber.getText()
        );

        if(isValid)
        {
            try
            {
                boolean isComplete = DBCustomers.ModifyCustomer(
                        txtCustomerName.getText(),
                        txtAddress.getText(),
                        txtZipCode.getText(),
                        txtPhoneNumber.getText(),
                        comboModifyDivision.getValue().getDivisionID(),
                        Integer.parseInt(txtCustomerID.getText()));


                if(isComplete)
                {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Customer has been updated.");
                    Optional<ButtonType> result = alert.showAndWait();

                    if(result.isPresent() && (result.get() == ButtonType.OK))
                    {
                        try
                        {
                            Parent root = FXMLLoader.load(getClass().getResource("/yildirim/dbclientapp/view/CustomerScreens.fxml"));
                            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();                            Scene scene = new Scene(root);
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
    }
    /**
     * method to check empty text fields in the modify customer screen.
     * @param Address address
     * @param Name name
     * @param PhoneNumber phone number
     * @param PostalCode  zip code
     * */

    private boolean emptyCheck(String Name, String Address, String PostalCode, String PhoneNumber) {
        if (Name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("A name needs to be entered.");
            alert.showAndWait();
            return false;
        }
        if (Address.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("An address needs to be entered.");
            alert.showAndWait();
            return false;
        }
        if (PostalCode.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("A postal code needs to be entered.");
            alert.showAndWait();
            return false;
        }
        if (PhoneNumber.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("A phone number needs to be entered.");
            alert.showAndWait();
            return false;
        }
        if (comboModifyDivision.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Division needs to be selected.");
            alert.showAndWait();
            return false;
        }
        if (comboModifyCountry.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Country needs to be selected.");
            alert.showAndWait();
            return false;
        }
        return true;

    }

    /**
     * Method to cancel the modification without saving it when cancel button is clicked.
     * It takes the user to the main screen.
     * @param actionEvent action event
     * @throws IOException IO Exception.
     */

    public void btnCancelOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/yildirim/dbclientapp/view/CustomerScreens.fxml"));
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        Scene scene = new Scene(root, 762, 469);
        stage.setTitle("Appointment Scheduling System");
        stage.setScene(scene);
        stage.show();
    }

    public static void receiveSelectedCustomer(Customers customer) {
        selectedCustomer = customer;
    }

    /**
     * Initializes the Modify Customer class.
     * @param url the url
     * @param resourceBundle The resource bundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            comboModifyCountry.setItems(DBCountries.getCountries());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * combo box for country in modify customer screen.
     * @param event event
     */

    public void comboModifyCountryOnAction(ActionEvent event) {

        try {
            ObservableList allFirstDivisions=DBFirstLevelDivisions.getAllFirstLevelDivisions(comboModifyCountry.getSelectionModel().getSelectedIndex()+1);
            comboModifyDivision.setItems(allFirstDivisions);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * method to select the division combo box on modify customer screen.
     * @param event event
     * */

    public void comboModifyDivisionOnAction(ActionEvent event) {

    }
}


