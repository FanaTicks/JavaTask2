package org.example.task1MyParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchNameSurname {
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
            }
            else {
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
            }
            else {
                fileContent2.add(logItem);
            }
        }
        WriteXml writeXml = new WriteXml();
        writeXml.writeXmlFile(fileContent2);//записываем в новый файл
    }
    public String connectNameSurname(String Name, String Surname){
        return Name+" "+Surname;
    }
}
