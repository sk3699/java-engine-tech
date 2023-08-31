package com.cg.Server.service;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

class BasicWeightServiceTest {

    Gson gson = new Gson();

    private String getClient(String pathMapping, String method, String body) throws IOException {
        URL url = new URL(String.format("http://localhost:8008/%s", pathMapping));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod(method);
        if(method.equals("POST") && body != null && !body.isEmpty()) {
            byte[] outputInBytes = body.getBytes("UTF-8");
            OutputStream os = con.getOutputStream();
            os.write( outputInBytes );
            os.close();
        }
        InputStream in = new BufferedInputStream(con.getInputStream());
        String result;
        try (Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())) {
            result = scanner.useDelimiter("\\A").next();
        }
        in.close();
        con.disconnect();
        return result;
    }

    private Map<String, Double> getErrorMargin() {
        Map<String, Double> map = new HashMap<>();
        map.put("£0.00", 40.00);
        map.put("£1.00", 30.00);
        map.put("£2.00", 20.00);
        map.put("£3.00", 10.00);
        return map;
    }

    private Map<String, Double> getErrorMarginPercent() {
        Map<String, Double> map = new HashMap<>();
        map.put("£0.00", 0.003997);
        map.put("£1.00", 0.003739);
        map.put("£2.00", 0.003263);
        map.put("£3.00", 0.002448);
        return map;
    }

    private Map<String, Double> getSpinChances() {
        Map<String, Double> map = new HashMap<>();
        map.put("3ACE", 0.4630);
        map.put("3KING", 1.0974);
        map.put("3QUEEN", 2.1433);
        map.put("3JACK", 3.7037);
        map.put("2ACE", 6.9444);
        return map;
    }

    @Test
    void test_getRandomValue() {
        AtomicReference<Map<String, Integer>> resultMap = new AtomicReference<>(new HashMap<>());
        IntStream.range(0, 50).forEach(i -> {
            try {
                String json = getClient("table", "GET", null);
                String value = (String) gson.fromJson(json, Map.class).get("value");
                if(resultMap.get().containsKey(value)) {
                    resultMap.get().put(value, resultMap.get().get(value) + 1);
                } else {
                    resultMap.get().put(value, 1);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        resultMap.get().forEach((key, value) -> {
            double actualOccurrence = (double) value / 50 * 100;
            double expectedOccurrence = getErrorMargin().get(key);
            System.out.println("Expected, Actual" + expectedOccurrence + "," + actualOccurrence);
            double error = Math.abs(actualOccurrence - expectedOccurrence);
            if (error <= getErrorMarginPercent().get(key)) {
                System.out.println(key + " is within expected(" + getErrorMarginPercent().get(key) + ") range:" + error);
            } else {
                System.out.println(key + " is not within expected(" + getErrorMarginPercent().get(key) + ") range:" + error);
            }
        });

        System.out.println(resultMap);
    }

    @Test
    public void test_spin() {
        AtomicReference<Map<String, Integer>> resultMap = new AtomicReference<>(new HashMap<>());
        IntStream.range(0, 50).forEach(i -> {
            try {
                String json = getClient("spin", "GET", null);
                String pattern = (String) gson.fromJson(json, Map.class).get("pattern");
                if(pattern != null) {
                    if (resultMap.get().containsKey(pattern)) {
                        resultMap.get().put(pattern, resultMap.get().get(pattern) + 1);
                    } else {
                        resultMap.get().put(pattern, 1);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        resultMap.get().forEach((key, value) -> {
            double actualOccurrence = (double) value / 50 * 100;
            double expectedOccurrence = getSpinChances().get(key);
            if (actualOccurrence <= expectedOccurrence) {
                System.out.println(key + " is within expected(" + getSpinChances().get(key) + ") range:" + actualOccurrence);
            } else {
                System.out.println(key + " is not within expected(" + getSpinChances().get(key) + ") range:" + actualOccurrence);
            }
        });
    }
}