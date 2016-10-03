package seng202.group9.Controller;

import java.io.*;
import java.util.ArrayList;

public abstract class Parser {

    abstract String parse() throws DataException;

    public int getLines(String filePath) throws IOException{
        File file = new File(filePath);
        BufferedReader reader = null;
        int lineCount = 0;
            reader= new BufferedReader(new FileReader(file));
        //get total lines
        while(reader.readLine() != null) {
            lineCount++;
        }
        reader.close();
        return lineCount;
    }
}
