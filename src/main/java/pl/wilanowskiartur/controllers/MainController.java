package pl.wilanowskiartur.controllers;

import javafx.fxml.Initializable;
import pl.wilanowskiartur.models.SocketConnector;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    private SocketConnector socketConnector = SocketConnector.getInstance();

    public void initialize(URL location, ResourceBundle resources) {
        socketConnector.connect();

        socketConnector.sendMessage("Ping");
    }
}
