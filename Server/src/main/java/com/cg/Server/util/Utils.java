package com.cg.Server.util;

import com.google.gson.Gson;

import java.util.Map;

public class Utils {

    Gson gson = new Gson();

    public String convertMapToJson(Map map) {
        return gson.toJson(map);
    }

    public String convertMatrixToJson(String[][] matrix) {
        return gson.toJson(matrix);
    }
}
