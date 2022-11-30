package org.example.task1MyParser;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseXml {
    public void editXml(String fileName){
        List<String> lineInPerson = new ArrayList<>();
        cleareFile();
        try (
                var fr = new FileReader(fileName);
                var br = new BufferedReader(fr)
        ) {
            var line = br.readLine();
            while (line != null) {
                if(line.contains("<person") && !line.contains("<persons")){//начало человека
                    if(line.contains("/>")){//конец в той же строке
                        lineInPerson.add(line);
                        editNameSurname(lineInPerson);
                        lineInPerson.clear();
                    } else {
                        lineInPerson.add(line);//конец не в той же строке, просто добавляем строку
                    }
                } else if(line.contains("/>") || line.contains("</")){//конец не в той же строке
                    lineInPerson.add(line);
                    editNameSurname(lineInPerson);
                    lineInPerson.clear();
                } else {
                    lineInPerson.add(line);
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static void cleareFile(){
        try (BufferedWriter bf = Files.newBufferedWriter(Path.of("src\\main\\resources\\xml_file_task1_output.xml"),
                StandardOpenOption.TRUNCATE_EXISTING)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editNameSurname(List<String> fileName){
        List<String> fileContent = new ArrayList<>();
        List<String> fileContent2 = new ArrayList<>();
        String Surname= null,Name = null;
        Pattern attributePatternName = Pattern.compile("[^r][n][a][m][e]\\s*=\\s*\"([^\"]*)\"\\S*");
        Pattern attributePatternSurname = Pattern.compile("([s][u][r][n][a][m][e]\\s*=\\s*\"([^\"]*)\"\\S*)");

        for (String logItem : fileName) {
            Matcher matcherName = attributePatternName.matcher(logItem);
            Matcher matcherSurname = attributePatternSurname.matcher(logItem);
            if (matcherSurname.find( )) {
                fileContent.add(logItem.replaceAll(matcherSurname.group(1), ""));
                Surname=matcherSurname.group(2);//запоминаем фамилю
            } else {
                fileContent.add(logItem);
            }
            if (matcherName.find( )) {
                Name=matcherName.group(1);//запоминаем имя
            }
        }
        for (String logItem : fileContent) {
            Matcher matcherName = attributePatternName.matcher(logItem);
            if (matcherName.find( )) {
                fileContent2.add(logItem.replaceAll(matcherName.group(1), connectNameSurname(Name, Surname)));//меняем имя на имя+фамилия
            } else {
                fileContent2.add(logItem);
            }
        }

        writeXmlFile(fileContent2);//записываем в новый файл
    }
    public String connectNameSurname(String Name, String Surname){
        return Name+" "+Surname;
    }

    public void writeXmlFile(List<String> fileName){
        Iterator<String> nameIterator = fileName.iterator();
        try(PrintWriter output = new PrintWriter(new FileWriter("src\\main\\resources\\xml_file_task1_output.xml",true)))
        {
            while (nameIterator.hasNext()) {
                String fileNameString = nameIterator.next();
                output.printf("%s\r\n", fileNameString);//каждую часть листа записывать с новой строки
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

