package yildirim.dbclientapp.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import yildirim.dbclientapp.ZoneIDDisplay;
import yildirim.dbclientapp.dao.DBAppointments;
import yildirim.dbclientapp.dao.DBUsers;
import yildirim.dbclientapp.model.Appointments;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.EventObject;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;




public class LoginScreen implements Initializable {
    public GridPane gridPaneLogin;
    public TextField txtUserName;
    public TextField txtPassword;
    public Label lblUserName;
    public Label lblPassword;
    public Button btnLogin;
    public Button btnCancel;
    public AnchorPane anchorPaneLogin;
    public Label lblLogin;
    public Label lblLocation;
    ResourceBundle resourceLogin = ResourceBundle.getBundle("/yildirim/dbclientapp/language", Locale.getDefault());
    private ResourceBundle resourceBundle;

    /**
     * Method to verify login, update login and set locale/language based on the user's language settings.
     * @param url url
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //lambda expression 2

        ZoneIDDisplay zID = () -> ZoneId.systemDefault();
        ZoneId zone = zID.displayZoneID();
        lblLocation.setText(String.valueOf(zone));
        lblPassword.setText(resourceLogin.getString("Password"));
        lblUserName.setText(resourceLogin.getString("Username"));
        btnLogin.setText(resourceLogin.getString("Login"));
        btnCancel.setText(resourceLogin.getString("Cancel"));


    }
    /**
     *  Login button for the main screen.When it is clicked, it takes the user to the main screen of the application.
     * @param actionEvent action event
     **/
    public void btnOnActionLogin(ActionEvent actionEvent) {

        try {

            String usernameInput = txtUserName.getText();
            String passwordInput = txtPassword.getText();
            int userId = DBUsers.setUser(usernameInput, passwordInput);

            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            PrintWriter outputFile = new PrintWriter(fileWriter);
            ZonedDateTime localDT = ZonedDateTime.of(LocalDateTime.now(),ZoneId.systemDefault());
            if (usernameInput.isBlank()){
                usernameInput = "<" + usernameInput + ">" ;
            }


            if (userId > 0) {
                outputFile.print("user: " + usernameInput + " successfully logged in at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");


                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/yildirim/dbclientapp/view/MainScreen.fxml"));
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.hide();
                Parent root = loader.load();

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();


            } else if (userId < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceLogin.getString("Error"));
                alert.setContentText(resourceLogin.getString("Incorrect"));
                alert.show();

                outputFile.print("user: " + usernameInput + " failed login attempt at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");

            }

            outputFile.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        }


    /**
     * Takes the user back to the main menu of the application.
     * @param actionEvent action event
     */

    public void btnOnActionCancel(ActionEvent actionEvent) {
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












