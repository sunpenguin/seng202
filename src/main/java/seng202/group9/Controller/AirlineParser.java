package seng202.group9.Controller;

import com.sun.javaws.progress.Progress;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group9.Core.Airline;

import java.io.*;
import java.util.ArrayList;

/**
 * Created By Fan-Wu Yang (fwy13)
 */
public class AirlineParser extends Parser {
    String filePath = "";
    ArrayList<Airline> parsedAirline;

    /**
     * Constructor takes in a filepath and waits for the parse() call.
     * @param filePath
     */
    public AirlineParser(String filePath){
        this.filePath = filePath;
        parsedAirline = new ArrayList<Airline>();
    }

    /**
     * Parses data and then returns a message of number of successful parses.
     * @return
     * @throws DataException
     */
    public String parse() throws DataException{
        int successful = 0;
        int error = 0;
        int duplicate = 0;

        File file = new File(filePath);
        BufferedReader reader = null;

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("To Load");
        alert.setHeaderText("Importing may take time");
        alert.setContentText("Please wait paitiently when you import data.\n It may take a long time.\n" +
                " Press OK to Continue.");
        alert.showAndWait();

        try {
            String line = null;
            int lines = getLines(filePath);

            int currentProgress = 0;

            reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {
                //read file here
                //id, name, alias, city, iata, icao, callsign, country, active
                //sample line: 2,"135 Airways",\N,"","GNL","GENERAL","United States","N"
                String airlName = "";
                String airlAlias = "";
                String airlIATA = "";
                String airlICAO = "";
                String airlCallsign = "";
                String airlCountry = "";
                String airlActive = "";
                String parts[] = {"", "", "", "", "", "", "", ""};
                int part = 0;
                boolean inQuotes = false;
                for (int i = 0; i < line.length(); i ++){
                    if (line.charAt(i) == ','){
                        if (inQuotes == true){
                            parts[part] += line.charAt(i);
                        }else{
                            part++;
                        }
                    }else if (line.charAt(i) == '\"'){
                        inQuotes = !inQuotes;
                    }else {
                        parts[part] += line.charAt(i);
                    }
                }
                //check length
                if (parts.length != 8){
                    System.out.println(parts[1] + " does not have 8 entries.");
                    error++;
                    continue;
                }
                //types do not need to be checked as they are all strings
                boolean errorBreak = false;
                //cehck sizes of [][] eg {3,0} will check if length == 3 or == 0. if -1 the size is ignored
                int partSizes[][] = {{-1}, {-1}, {-1}, {2,0}, {3,0}, {-1}, {-1}, {1}};
                for (int i = 1; i < partSizes.length; i ++){
                    boolean passable = false;
                    for (int j = 0; j < partSizes[i].length; j++){
                        if (partSizes[i][j] != -1) {
                            if (parts[i].equals("\\N")){
                                parts[i] = "";
                            }
                            if (parts[i].length() == partSizes[i][j] || parts[i].equals("-") ||
                                    parts[i].equals("N/A")) {
                                passable = true;
                            }
                        }else{
                            passable = true;
                        }
                    }
                    if (passable == false){
                        System.out.println(parts[1] + " has Length: " + parts[i].length() + ", Value: " + parts[i] + " @ Index: " + i);
                        error++;
                        errorBreak = true;
                        break;
                    }
                }
                if (errorBreak == true){
                    continue;
                }
                //passing is done now add stuff to array
                //id, name, alias, iata, icao, callsign, country, active
                airlName = parts[1];
                airlAlias = parts[2];
                airlIATA = parts[3];
                airlICAO = parts[4];
                airlCallsign = parts[5];
                airlCountry = parts[6];
                airlActive = parts[7];
                parsedAirline.add(new Airline(airlName, airlAlias, airlIATA, airlICAO, airlCallsign, airlCountry, airlActive));
                successful++;
                currentProgress++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new DataException("File: " +this.filePath+" is Missing.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataException(this.filePath + " is Corrupted.");
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                throw new DataException(this.filePath + " is unable to initialise reader.");
            }
        }
        return String.format("Airlines Successfully Entered: %1$d.\n" +
                "Airlines With Errors: %2$d", successful, error);
    }

    /**
     * Returns the result of the Parse.
     * @return
     */
    public ArrayList<Airline> getResult(){
        return parsedAirline;
    }

}
