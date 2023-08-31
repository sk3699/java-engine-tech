package com.cg.Server.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class SpinWebSocket {

    private Session session;
    @OnWebSocketConnect
    public void onOpen(Session session) {
        System.out.println ("Spin WebSocket Connected, sessionID = " + session.getRemoteAddress());
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        System.out.println("Received message from client to Spin WebSocket: " + message);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String closeReason) {
        System.out.println("Spin WebSocket Session " + session.getRemoteAddress() +
                " closed because " + closeReason + ". Status Code: " + statusCode);
    }
}
