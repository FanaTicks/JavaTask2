package org.example.task1MyParser;

import java.io.*;
import java.util.Iterator;
import java.util.List;

public class WriteXml {
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
