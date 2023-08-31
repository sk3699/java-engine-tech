package com.cg.Client;

import com.cg.Client.websocket.ClientWebSocket;

/*import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;*/
import java.net.*;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

public class Client {

    public static String postRequest(String body) throws MalformedURLException, IOException, ProtocolException {
        URL url = new URL("http://localhost:8008/serve");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        byte[] outputInBytes = body.getBytes("UTF-8");

        OutputStream os = con.getOutputStream();
        os.write( outputInBytes );    
        os.close();

        InputStream in = new BufferedInputStream(con.getInputStream());
        String result = null;
        try (Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())) {
            result = scanner.useDelimiter("\\A").next();
        }

        in.close();
        con.disconnect();

        return result;
    }

    public static void main (String [] args) {
        connectWebSocket();
    }

    public static void connectWebSocket() {
        try {
            String serverUrl = "ws://localhost:8008/websocket";
            ClientWebSocket client = new ClientWebSocket();
            client.connect(new URI(serverUrl));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
