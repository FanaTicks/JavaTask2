package org.example.task1ReadyParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.IOException;

public class InputOutputFilesXml {
    private static final String FILENAME1 = "src/main/resources/xml_file_task1.xml";
    private static final String FILENAME2 = "src/main/resources/xml_file_task1_read_parser.xml";

    public static void readFile() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc1 = builder.newDocument();

            Element root = doc1.createElementNS("","persons");
            doc1.appendChild(root);
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(FILENAME1));

            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("person");

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    String birthData = element.getAttribute("birthDate");//поиск элемента birthDate
                    String name = element.getAttribute("name");//поиск элемента name
                    String surname = element.getAttribute("surname");//поиск элемента surname

                    root.appendChild(createUser(doc1, name, surname, birthData));//создание нового createUser
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transf = transformerFactory.newTransformer();//добавление свойств

            transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transf.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc1);

            File myFile = new File(FILENAME2);//новый файл

            StreamResult file = new StreamResult(myFile);

            transf.transform(source, file);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

    }


    private static Node createUser(Document doc, String name, String surname, String birthDate) {
        Element user = doc.createElement("person");

        user.setAttribute("name", name+" "+surname);
        user.setAttribute("birthDate", birthDate);

        return user;
    }


}
