package com.cg.Server.service;

import com.cg.Server.dao.BasicWeightDao;
import com.cg.Server.dao.ReelsDao;
import com.cg.Server.dao.WinPatternDao;
import com.cg.Server.util.Utils;
import com.cg.Server.wrapper.BasicWeightWrapper;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class BasicWeightService {
    private final BasicWeightDao basicWeightDao = new BasicWeightDao();

    private final ReelsDao reelsDao = new ReelsDao();
    private final Utils utils = new Utils();
    private final WinPatternDao winPatternDao = new WinPatternDao();
    Random random = new Random();

    private int totalTableReqs;
    private int totalSpinReqs;
    private double totalWeightValues;
    private double totalSpinValues;

    public String getRandomValue() {
        List<BasicWeightWrapper> allRecords = basicWeightDao.getAllRecords();
        //double cumulativeChances = allRecords.stream().mapToDouble(BasicWeightWrapper::getChance).sum();
        double randomValue = random.nextDouble();// * cumulativeChances;
        AtomicReference<Double> cumChance = new AtomicReference<>((double) 0);
        totalTableReqs++;
        System.out.println("Total hits to table endpoint: " + totalTableReqs);

        return utils.convertMapToJson(allRecords.stream()
                .filter(record -> {
                    cumChance.updateAndGet(v -> v + record.getChance());
                    return randomValue <= cumChance.get();
                })
                .findFirst()
                .map(record -> {
                    double weightValue = record.getWeightValue();
                    totalWeightValues += weightValue;
                    double rtpValue = (totalWeightValues / (totalTableReqs * 3.50)) * 100;
                    Map<String, String> map = new HashMap<>();
                    map.put("value", String.format("£%.2f",weightValue));
                    map.put("RTPValue", String.format("%.2f",rtpValue));
                    map.put("reqPath", "table");
                    map.put("totalNoofReqs", String.format("%d", totalTableReqs));
                    map.put("totalWeight", String.format("%.2f", totalWeightValues));
                    return map;
                }).orElse(new HashMap<>()));
    }

    public String getSpin() {
        List<List<String>> reels = new ArrayList<>(reelsDao.getAllRecords());
        String[][] spinResult = new String[3][3];
        totalSpinReqs++;

        for (int col = 0; col < 3; col++) {
            int chosenPosition = random.nextInt(reels.size());
            List<String> chosenReel = reels.get(chosenPosition);
            for (int row = 0; row < 3; row++) {
                int symbolIndex = (chosenPosition + row) % chosenReel.size();
                spinResult[row][col] = chosenReel.get(symbolIndex);
            }
        }

        int middleRow = 1;
        int count = 1;
        String pattern;
        Map<String, String> map = new HashMap<>();
        for (int col = 0; col < 2; col++) {
            if (spinResult[middleRow][col].equals(spinResult[middleRow][col + 1])) {
                count++;
                if(count == 3 || "ACE".equals(spinResult[middleRow][col])) {
                    pattern = spinResult[middleRow][col];
                    double spinValue = winPatternDao.getValueByPattern(pattern, count);
                    totalSpinValues += spinValue;
                    double rtpValue = (totalSpinValues / (totalSpinReqs * 3.50)) * 100;

                    map.put("value", String.format("£%.2f",spinValue));
                    map.put("matrix", utils.convertMatrixToJson(spinResult));
                    map.put("pattern", count + pattern);
                    map.put("reqPath", "spin");
                    map.put("RTPValue", String.format("%.2f",rtpValue));
                    map.put("totalNoofReqs", String.format("%d", totalSpinReqs));
                    map.put("totalWeight", String.format("%.2f", totalSpinValues));
                    return utils.convertMapToJson(map);
                    //break;
                }
            }
        }
        map.put("value","0");
        return utils.convertMapToJson(map);
    }
}
