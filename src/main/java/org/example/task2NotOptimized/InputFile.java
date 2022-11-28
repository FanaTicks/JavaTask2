package org.example.task2NotOptimized;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class InputFile {
    public void readJson(int quantityReport) throws ParseException {
        Map<String, Double> map = new HashMap<>();
        for (int value =1;value<= quantityReport;value++) {
            try(FileReader fileRead = new FileReader("src\\main\\resources\\report"+value+".json")) {
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(fileRead);
                String type = (String) jsonObject.get("type"); //поиск type
                Double fineAmount = (Double)jsonObject.get("fine_amount");//поиск fine_amount
                if (map.containsKey(type)) {
                    Double get = map.get(type);//импорт значения ключа
                    map.put(type, get+fineAmount);//повышение значения ключа
                }
                else {
                    map.put(type, fineAmount);//добавление ключа
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        OutputFile.writeFile(map.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldVal, newVal) -> oldVal, LinkedHashMap::new)));//вызов метода для записи сортированой мапы
    }
}
