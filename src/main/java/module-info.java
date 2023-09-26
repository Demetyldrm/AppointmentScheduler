module yildirim.dbclientapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens yildirim.dbclientapp to javafx.fxml;
    exports yildirim.dbclientapp;
    opens yildirim.dbclientapp.dao to javafx.fxml;
    exports yildirim.dbclientapp.dao;
    opens yildirim.dbclientapp.model to javafx.fxml;
    exports yildirim.dbclientapp.model;
    opens yildirim.dbclientapp.controller to javafx.fxml;
    exports yildirim.dbclientapp.controller;


}