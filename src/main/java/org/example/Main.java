package org.example;

import org.example.task1MyParser.ParseXml;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static org.example.task1ReadyParser.InputOutputFilesXml.readFile;
import static org.example.task2Optimized.InputOutputFiles.readAndParse;



public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        ParseXml parseXml = new ParseXml();
        parseXml.editXml("src\\main\\resources\\xml_file_task1.xml");

        readFile();

        readAndParse(5);


    }
}