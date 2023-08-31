package com.cg.Client.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

@WebSocket
public class ClientWebSocket {

    private Session session;
    private final AtomicReference<CountDownLatch> reqsLatch = new AtomicReference<>(new CountDownLatch(100));

    private final Map<String, String> rtpValues =  new ConcurrentHashMap<>();

    private final Gson gson = new Gson();

    public void connect(URI serverUri) throws Exception {
        WebSocketClient client = new WebSocketClient();
        client.start();
        Future<Session> future = client.connect(this, serverUri);
        session = future.get();
        List.of("table", "spin").forEach(value -> {
            rtpValues.put(value, "0");
            IntStream.range(0, 50).forEach(i -> {
                try {
                    session.getRemote().sendString(value);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        this.session = session;
        System.out.println ("--- WebSocket Connected " + session.getRemoteAddress());
        try {
            session.getRemote().sendString("start");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnWebSocketMessage
    public void onMessage(String message) throws InterruptedException {
        //System.out.println ("--- Received " + message);
        Map map = gson.fromJson(message, Map.class);
        if(map.containsKey("reqPath") && map.containsKey("RTPValue")) {
            rtpValues.put((String) map.get("reqPath"), (String) map.get("RTPValue"));
        }
        reqsLatch.get().countDown();
        if(reqsLatch.get().getCount() == 0 && reqsLatch.get().await(5, TimeUnit.SECONDS)) {
            System.out.println("Total RTP values are: \n" + rtpValues);
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String closeReason) {
        System.out.println("Session " + session.getRemoteAddress() +
                " closed because " + closeReason + ". Status Code: " + statusCode);
    }
}
