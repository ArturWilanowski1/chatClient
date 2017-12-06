package pl.wilanowskiartur.models;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

@ClientEndpoint
public class SocketConnector {

    private static SocketConnector ourInstance = new SocketConnector();

    public static SocketConnector getInstance() {
        return ourInstance;
    }

    private WebSocketContainer container;

    private SocketConnector(){
        container = ContainerProvider.getWebSocketContainer();
    }

    public void connect(){
        URI uri = URI.create("ws://localhost:8080/chat");
        try {
            container.connectToServer(this, uri);
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Połączono!");
    }

    @OnOpen
    public void onOpen(Session session){
        System.out.println("POLACZONO!");
    }


}
