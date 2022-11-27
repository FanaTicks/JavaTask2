package org.example.task2Optimized;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class InputOutputFiles {
    private static final String ITEMS_NAME = "repots_details";
    private static final String TYPE_PROPERTY = "type";
    private static final String FINE_AMOUNT__PROPERTY = "fine_amount";

    static Map<String, Double> map = new HashMap<>();


    public static void readAndParse(int quantityReport) throws IOException {
        for (int value =1;value<= quantityReport;value++) {
            try (final JsonReader jsonReader = new JsonReader(new BufferedReader(new InputStreamReader(new FileInputStream("src\\main\\resources\\report1." + value + ".json"))))) {
                parseReports(jsonReader);
            }
        }
        writeFile(map.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldVal, newVal) -> oldVal, LinkedHashMap::new)));
    }

    public static void parseReports(final JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        final String itemsName = jsonReader.nextName();
        if ( !itemsName.equals(ITEMS_NAME) ) {
            throw new MalformedJsonException(ITEMS_NAME + " expected but was " + itemsName);
        }
        jsonReader.beginArray();
        while ( jsonReader.hasNext() ) {
            jsonReader.beginObject();
            String type = "";
            double fineAmount = 0;
            while ( jsonReader.hasNext() ) {
                final String property = jsonReader.nextName();
                switch ( property ) {
                    case TYPE_PROPERTY:
                        type = jsonReader.nextString();
                        break;
                    case FINE_AMOUNT__PROPERTY:
                        fineAmount = jsonReader.nextDouble();
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }

            if (map.containsKey(type)) {
                Double get = map.get(type);//импорт значения ключа
                map.put(type, get+fineAmount);//повышение значения ключа
            }
            else map.put(type, fineAmount);//добавление ключа

            jsonReader.endObject();
        }
        jsonReader.endArray();
        jsonReader.endObject();

    }
    public static void writeFile(Map<String, Double> map) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element rootElement = doc.createElementNS("https://polis.ua/shtraf", "Reports");
            doc.appendChild(rootElement);

            for(Map.Entry<String, Double> item : map.entrySet())
                rootElement.appendChild(getReport(doc, item.getKey(), item.getValue()));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            StreamResult file = new StreamResult(new File("src/main/resources/ReportsTop.xml"));

            transformer.transform(source, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Node getReport(Document doc, String type, Double fine_amount) {
        Element language = doc.createElement("Report");

        language.appendChild(gerReportElement(doc, language, "type", type));
        language.appendChild(gerReportElement(doc, language, "fine_amount", String.valueOf(fine_amount)));

        return language;
    }

    private static Node gerReportElement(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

}

