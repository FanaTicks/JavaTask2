package org.example.task1MyParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class InputXml {
    public void readFile(String fileName){
        List<String> lineInPerson = new ArrayList<>();
        SearchNameSurname searchNameSurname = new SearchNameSurname();
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
                        searchNameSurname.editNameSurname(lineInPerson);
                        lineInPerson.clear();
                    }
                    else lineInPerson.add(line);//конец не в той же строке
                }
                else if(line.contains("/>")){//конец не в той же строке
                    lineInPerson.add(line);
                    searchNameSurname.editNameSurname(lineInPerson);
                    lineInPerson.clear();
                }
                else if(line.contains("</")){
                    lineInPerson.add(line);
                    searchNameSurname.editNameSurname(lineInPerson);
                    lineInPerson.clear();
                }
                else lineInPerson.add(line);
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
}

