package org.example;

import org.example.task1MyParser.ParseXml;
import org.example.task1ReadyParser.InputOutputFilesXml;
import org.example.task2NotOptimized.InputFile;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static org.example.task2Optimized.InputOutputFiles.readAndParse;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        ParseXml parseXml = new ParseXml();
        parseXml.readFile("src\\main\\resources\\xml_file_task1.xml");

        InputOutputFilesXml inputOutputFilesXml = new InputOutputFilesXml();
        inputOutputFilesXml.readFile();

        InputFile inputFile = new InputFile();
        inputFile.readJson(5);

        readAndParse(5);


    }
}