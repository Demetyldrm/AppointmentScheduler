package yildirim.dbclientapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import yildirim.dbclientapp.dao.JDBC;


/**
 * This is the start method.It will be called at when the launch method is called.
 * This is where the first FXML form is initialized.
 * @param stage the primary stage for this application
 * @throws IOException
 */


import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class for starting the application*/

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/yildirim/dbclientapp/view/LoginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 518, 381);
        stage.setTitle("Appointment Scheduling System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en"));



        JDBC.getConnection();
        launch(args);
        JDBC.closeConnection();



    }
}