package yildirim.dbclientapp.controller;

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
import yildirim.dbclientapp.dao.*;
import yildirim.dbclientapp.model.Appointments;
import yildirim.dbclientapp.model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.EventObject;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the customer screen 's controller class */

public class CustomerScreens implements Initializable {
    public TableView tblViewCustomer;
    public Button btnAddCustomer;
    public Button btnModifyCustomer;
    public Button btnDeleteCustomer;
    public Button btnCancel;
    public TableColumn tColCustomerID;
    public TableColumn tColName;
    public TableColumn tColAddress;
    public TableColumn tColZipCode;
    public TableColumn tColPhone;
    public TableColumn tColFirstLevel;
    public AnchorPane anchorPaneCustomer;
    public TableColumn tColCountry;
    public TableColumn tColDivision;

    /**
     * Initialize controls and sets initial variables/observable lists.
     * @param url url
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = JDBC.getConnection();
            ObservableList<Customers> allCustomersList = DBCustomers.getAllCustomers();

            tColCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            tColName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            tColAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            tColZipCode.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            tColPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            tColDivision.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
            tblViewCustomer.setItems(allCustomersList);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to add customer records.
     * @throws IOException IO Exception
     * @param actionEvent action event
     */
    public void btnOnActionAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/yildirim/dbclientapp/view/AddCustomer.fxml"));
        Stage stage = (Stage) btnAddCustomer.getScene().getWindow();
        Scene scene = new Scene(root, 635, 493);
        stage.setTitle("Appointment Scheduling System");
        stage.setScene(scene);
        stage.show();



    }

    /**
     * Method to modify customer records.
     * @throws IOException IO Exception
     * @param event event
     */
    public void btnOnActionModifyCustomer(ActionEvent event) throws IOException, SQLException {


        Customers selectedCustomer = (Customers) tblViewCustomer.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Please select a customer");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/yildirim/dbclientapp/view/ModifyCustomer.fxml"));
        Parent parent = loader.load();
        Stage stage;
        ModifyCustomer modifyCustomer = loader.getController();
        modifyCustomer.setCustomer(((Customers) tblViewCustomer.getSelectionModel().getSelectedItem()));
        Scene scene = new Scene(parent, 635, 493);
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method to delete customer records.
     * @throws SQLException SQL Exception
     * @param actionEvent action event
     */
        public void btnOnActionDeleteCustomer(ActionEvent actionEvent) throws SQLException {

        ObservableList<Customers> customersObservableList;
        Customers customersToDelete = (Customers) tblViewCustomer.getSelectionModel().getSelectedItem();
        if (customersToDelete == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Select a customer to delete.");
            alert.showAndWait();
        } else  {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete the selected customer?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                int deleteSuccess = DBCustomers.deleteCustomer(((Customers) tblViewCustomer.getSelectionModel().getSelectedItem()).getCustomerID());

                tblViewCustomer.setItems(DBCustomers.getAllCustomers());
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Successful");
                alert.setContentText("Customer ID: " + customersToDelete.getCustomerID() + " Name: " + customersToDelete.getCustomerName() + " is deleted.");
                alert.showAndWait();

            }
        }
    }
    /**
     * Method to go back to main screen when cancel button is clicked.
     * @throws IOException IO exception
     */
    public void btnOnActionCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/yildirim/dbclientapp/view/MainScreen.fxml"));
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        Scene scene = new Scene(root, 492, 421);
        stage.setTitle("Appointment Scheduling System");
        stage.setScene(scene);
        stage.show();
    }


}
