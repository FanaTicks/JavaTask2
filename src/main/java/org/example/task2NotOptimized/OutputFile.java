package org.example.task2NotOptimized;

import java.io.File;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class OutputFile {
    public static void writeFile(Map<String, Double> map) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element rootElement = doc.createElementNS("https://polis.ua/shtraf", "Reports");//создание елемента
            doc.appendChild(rootElement);

            for(Map.Entry<String, Double> item : map.entrySet()) {
                rootElement.appendChild(getReport(doc, item.getKey(), item.getValue()));//добавление Report с каждого значения мапы
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            StreamResult file = new StreamResult(new File("src/main/resources/Reports.xml"));

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
