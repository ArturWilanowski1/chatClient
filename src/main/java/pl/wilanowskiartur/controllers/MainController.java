package pl.wilanowskiartur.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pl.wilanowskiartur.models.SocketConnector;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable{

        @FXML
        private TextArea textMessage;

        @FXML
        private TextField textWriteMessage;

        @FXML
        private Button buttonSend;

    private SocketConnector socketConnector = SocketConnector.getInstance();

    public void initialize(URL location, ResourceBundle resources) {
        clickEnterOnWriteMessage();
        socketConnector.connect();
        socketConnector.sendMessage("Ping");
    }

    private void clickEnterOnWriteMessage() {
        textWriteMessage.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){
                    socketConnector.sendMessage(textWriteMessage.getText());
                    textWriteMessage.clear();
                }
            }
        });
    }
}
