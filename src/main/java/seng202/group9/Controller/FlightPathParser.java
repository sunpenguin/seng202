package seng202.group9.Controller;

import seng202.group9.Core.FlightPoint;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Fan-Wu Yang(fwy13)
 */
public class FlightPathParser extends Parser {
    String filePath = "";
    ArrayList<FlightPoint> parsedPoints;

    /**
     * Constructor for parsing flight paths, reads in a file and gets it ready for the parse() call.
     * @param filePath
     */
    public FlightPathParser(String filePath){
        this.filePath = filePath;
        parsedPoints = new ArrayList<FlightPoint>();
    }

    /**
     * Parses the file that and returns a success message.
     * @return
     * @throws DataException
     */
    public String parse() throws DataException{
        int successful = 0;
        int error = 0;
        int duplicate = 0;

        File file = new File(filePath);
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = reader.readLine()) != null) {
                //read file here
                //(String type, String via, double altitude, double latitude, double longitude
                //APT,NZCH,0,-43.48664019,172.53368221
                String flightPtType = "";
                String flightPtVia = "";
                double flightPtAltitude = 0.0;
                double flightPtLat = 0.0;
                double flightPtLong = 0.0;
                //line = line.replaceAll("\"", "");
                String parts[] = {"", "", "", "", ""};
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
                //check length this may need to be optional depending on size as the lecturers have given us size 5 while the websites give a lot more
                if (parts.length != 5){
                    System.out.println(parts[1] + " does not have 5 entries.");
                    error++;
                    continue;
                }
                //check types I = integer, S = String D = Double P = Pass
                char partTypes[] = {'S', 'S', 'D', 'D', 'D'};
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
                int partSizes[][] = {{3}, {-1}, {-1}, {-1}, {-1}};
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
                //(String type, String via, double altitude, double latitude, double longitude
                flightPtType = parts[0];
                flightPtVia = parts[1];
                flightPtAltitude = Double.parseDouble(parts[2]);
                flightPtLat = Double.parseDouble(parts[3]);
                flightPtLong = Double.parseDouble(parts[4]);
                parsedPoints.add(new FlightPoint(flightPtType, flightPtVia, flightPtAltitude, flightPtLat, flightPtLong));
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

        return String.format("Flight Points Successfully Entered: %1$d.\n" +
                "Flight Points With Errors: %2$d", successful, error);
    }

    /**
     * returns the resultant Flight points that are successfully parsed.
     * @return
     */
    public ArrayList<FlightPoint> getResult(){
        return parsedPoints;
    }

}
