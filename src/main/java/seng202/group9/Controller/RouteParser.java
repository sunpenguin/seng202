package seng202.group9.Controller;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.group9.Core.Route;

import java.io.*;
import java.util.ArrayList;

/**
 * Created By Fan-Wu Yang(fwy13)
 */
public class RouteParser extends Parser {
    String filePath = "";
    ArrayList<Route> parsedRoutes;

    /**
     * Constructor for Route Parser takes in a file and gets ready for the parse() call.
     * @param filePath
     */
    public RouteParser(String filePath){
        this.filePath = filePath;
        parsedRoutes = new ArrayList<Route>();
    }

    /**
     * parses the given file for ROutes.
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
            reader = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = reader.readLine()) != null) {
                //read file here
                //String airline, airline id, String departureAirport, airport id, String arrivalAirport, arrival airport id, String codeShare, int stops, String equipment
                //sample line: 2B,410,AER,2965,KZN,2990,,0,CR2
                String routeAirName = "";
                String routeDepart = "";
                String routeArrive = "";
                String routeCodeshare = "";
                int routeStops = 0;
                String routeEquip = "";
                //line = line.replaceAll("\"", "");
                String parts[] = {"", "", "", "", "", "", "", "", ""};
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
                //System.out.println(parts[0] + " | " + parts[1] + " | "  + parts[2] + " | "  + parts[3] + " | "  + parts[4] + " | "
                //        + parts[5] + " | "  + parts[6] + " | "  + parts[7] + " | "  + parts[8] + " | "  + parts[9] + " | "  + parts[10] + " | "  + parts[11]);
                //check length
                if (parts.length != 9){
                    System.out.println(parts[1] + " does not have 9 entries.");
                    error++;
                    continue;
                }
                //check types I = integer, S = String D = Double P = Pass
                char partTypes[] = {'S', 'P', 'S','P', 'S', 'P', 'S', 'I', 'S'};
                boolean errorBreak = false;
                for (int i = 0; i < partTypes.length; i ++){
                    if(partTypes[i] == 'P'){
                        //if P it is a pass
                    } else if (partTypes[i] == 'I'){
                        try {
                            if (parts[i].equals("\\N")){
                                parts[i] = "0";
                            }
                            Integer.parseInt(parts[i]);
                        }catch (NumberFormatException e){
                            System.out.println(parts[1] + " has Value: " + parts[i] + " And is not a Integer Formattable Value.");
                            error++;
                            errorBreak = true;
                            break;
                        }
                    }else if (partTypes[i] == 'D'){
                        try{
                            if (parts[i].equals("\\N")){
                                parts[i] = "0";
                            }
                            Double.parseDouble(parts[i]);
                        }catch (NumberFormatException e){
                            System.out.println(parts[1] + " has Value: " + parts[i] + " And is not a Double Formattable Value.");
                            error++;
                            errorBreak = true;
                            break;
                        }
                    }
                }
                if (errorBreak == true){
                    continue;
                }
                //cehck sizes of [][] eg {3,0} will check if length == 3 or == 0. if -1 the size is ignored
                int partSizes[][] = {{2,3}, {-1}, {3,4}, {-1}, {3,4}, {-1}, {1,0}, {-1}, {-1}};
                for (int i = 0; i < partSizes.length; i ++){
                    boolean passable = false;
                    for (int j = 0; j < partSizes[i].length; j++){
                        if (partSizes[i][j] != -1) {
                            if (parts[i].equals("\\N")){
                                parts[i] = "";
                            }
                            if (parts[i].length() == partSizes[i][j]) {
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
                //String airline, airline id, String departureAirport, airport id, String arrivalAirport, arrival airport id, String codeShare, int stops, String equipment
                routeAirName = parts[0];
                routeDepart = parts[2];
                routeArrive = parts[4];
                routeCodeshare = parts[6];
                routeStops = Integer.parseInt(parts[7]);
                routeEquip = parts[8];
                parsedRoutes.add(new Route(routeAirName, routeDepart, routeArrive, routeCodeshare, routeStops, routeEquip));
                successful++;
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

        return String.format("Routes Successfully Entered: %1$d.\n" +
                "Routes With Errors: %2$d", successful, error);
    }

    /**
     * returns the final successful results.
     * @return
     */
    public ArrayList<Route> getResult(){
        return parsedRoutes;
    }

}
