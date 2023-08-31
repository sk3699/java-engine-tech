package com.cg.Server.websocket;

import com.cg.Server.service.BasicWeightService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class ServerWebSocket {

    private Session session;

    private final BasicWeightService service = new BasicWeightService();

    @OnWebSocketConnect
    public void onOpen(Session session) {
        this.session = session;
        System.out.println ("Table WebSocket Connected, sessionID = " + session.getRemoteAddress());
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        String response = null;
        try {
            if ("table".equals(message)) {
                response = service.getRandomValue();
            } else if ("spin".equals(message)) {
                response = service.getSpin();
            } else {
                System.out.println("Received message from client to Table WebSocket: " + message);
            }
            if (response != null) {
                session.getRemote().sendString(response);
            } else {
                System.out.println("Invalid request...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String closeReason) {
        System.out.println("Table WebSocket Session " + session.getRemoteAddress() +
                " closed because " + closeReason + ". Status Code: " + statusCode);
    }
}
