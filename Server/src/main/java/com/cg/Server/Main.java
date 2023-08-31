package com.cg.Server;

import com.cg.Server.service.BasicWeightService;
import com.cg.Server.websocket.ServerWebSocket;

import static spark.Spark.*;

public class Main {

    private static final BasicWeightService service = new BasicWeightService();

    public static void main(String[] args) {
        try {
            configureWebSockets();
            serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void configureWebSockets() {
        webSocket("/websocket", ServerWebSocket.class);
        /*webSocket("/websocket/table", TableWebSocket.class);
        webSocket("/websocket/spin", SpinWebSocket.class);*/
    }

    public static void serve() {
        port(8008);
        post("/serve", (req, res) -> {
                switch(req.body()) {
                    case "Hello":
                        return hello();
                    //other scenarios could go here ;)
                    default:
                        return "Error! No or invalid request name specified! (" + req.body() + ")";
                }
            }
        );
        get("/table", (req, res) -> service.getRandomValue());
        get("/spin", (req, res) -> service.getSpin());
    }

    /*public static Connection dbConnection() throws SQLException {
        String jdbcURL = Constants.H2_JDBC_URL;
        System.out.println("Connected to H2 in-memory database.");
        return DriverManager.getConnection(jdbcURL);
    }*/

    public static String hello() {
        return "Hello stranger!";
    }
}
