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
import pl.wilanowskiartur.models.SocketObserver;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, SocketObserver{

        @FXML
        private TextArea textMessage;

        @FXML
        private TextField textWriteMessage;

        @FXML
        private Button buttonSend;

    private SocketConnector socketConnector = SocketConnector.getInstance();

    public void initialize(URL location, ResourceBundle resources) {
        clickEnterOnWriteMessage();
        clickButtonSend();
        textMessage.setWrapText(true);

        socketConnector.connect();
        socketConnector.registerObserver(this);
    }

    private void clickButtonSend(){
        buttonSend.setOnMouseClicked(event -> sendAndClear());
    }

    private void clickEnterOnWriteMessage() {
        textWriteMessage.setOnKeyPressed(event ->  {
                if (event.getCode() == KeyCode.ENTER){
                    sendAndClear();
                }
        });
    }

    private void sendAndClear(){
        if (!textWriteMessage.getText().isEmpty())
        socketConnector.sendMessage(textWriteMessage.getText());
        textWriteMessage.clear();
    }

    public void onMessage(String s) {
        textMessage.appendText(s);
    }
}
