package pl.wilanowskiartur.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pl.wilanowskiartur.models.MessageModel;
import pl.wilanowskiartur.models.SocketConnector;
import pl.wilanowskiartur.models.SocketObserver;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, SocketObserver{

    public static final Gson GSON = new GsonBuilder().create();


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
        sendMessagePacket(textWriteMessage.getText());
        textWriteMessage.clear();
    }

    private void sendMessagePacket(String message){
        MessageModel messageModel = new MessageModel();
        messageModel.setContext(message);
        messageModel.setMessageType(MessageModel.MessageType.MESSAGE);

        socketConnector.sendMessage(GSON.toJson(messageModel));
    }

    public void onMessage(String s) {
        MessageModel messageModel = GSON.fromJson(s, MessageModel.class);

        switch (messageModel.getMessageType()){
            case MESSAGE:{
                textMessage.appendText(messageModel.getContext());
                break;
            }
            case OEPN_DIALOG:{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("");
                alert.setTitle("Serwer");
                alert.setContentText(messageModel.getContext());
                alert.show();
                break;
            }
        }
    }
}
