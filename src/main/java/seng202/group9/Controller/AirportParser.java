package seng202.group9.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group9.Core.Airport;
import seng202.group9.Core.City;
import seng202.group9.Core.Country;

import javax.jws.Oneway;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class AirportParser extends Parser {
    String filePath = "";
    ArrayList<Airport> parsedAirports;
    ArrayList<City> parsedCities;
    ArrayList<Country> parsedCountries;

    public AirportParser(String filePath){
        this.filePath = filePath;
        parsedAirports = new ArrayList<Airport>();
        parsedCities = new ArrayList<City>();
        parsedCountries = new ArrayList<Country>();
    }

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
                //id, name, city, country, iata/ffa, icao, lat, long, altitude, Timezone, DST, TimeOlson
                //sample line: 1,"Goroka","Goroka","Papua New Guinea","GKA","AYGA",-6.081689,145.391881,5282,10,"U","Pacific/Port_Moresby"
                String airpName = "";
                String airpCity = "";
                String airpCountry = "";
                String airpIATA_FFA = "";
                String airpICAO = "";
                double airpLat = 0.0;
                double airpLong = 0.0;
                double airpAltitude = 0.0;
                double airpTimezone = 0;
                String airpDST = "U";
                String airpOlson = "";
                //line = line.replaceAll("\"", "");
                String parts[] = {"", "", "", "", "", "", "", "", "", "", "", ""};
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
                if (parts.length != 12){
                    System.out.println(parts[1] + " does not have 12 entries.");
                    error++;
                    continue;
                }
                //check types I = integer, S = String D = Double
                char partTypes[] = {'I','S', 'S','S', 'S', 'S', 'D', 'D', 'D', 'D', 'S', 'S'};
                //ignore the first value as their id is not useful to us.
                boolean errorBreak = false;
                for (int i = 1; i < partTypes.length; i ++){
                    if (partTypes[i] == 'I'){
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
                int partSizes[][] = {{-1}, {-1}, {-1}, {-1}, {3,0}, {4,0}, {-1}, {-1}, {-1}, {-1}, {1,0}, {-1}};
                for (int i = 1; i < partSizes.length; i ++){
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
                airpName = parts[1];
                airpCity = parts[2];
                airpCountry = parts[3];
                airpIATA_FFA = parts[4];
                airpICAO = parts[5];
                airpLat = Double.parseDouble(parts[6]);
                airpLong = Double.parseDouble(parts[7]);
                airpAltitude = Double.parseDouble(parts[8]);
                airpTimezone = Double.parseDouble(parts[9]);
                airpDST = parts[10];
                airpOlson = parts[11];
                parsedAirports.add(new Airport(airpName, airpCity, airpCountry, airpIATA_FFA, airpICAO, airpLat, airpLong, airpAltitude));
                parsedCities.add(new City(airpCity, airpCountry, airpTimezone, airpOlson));
                parsedCountries.add(new Country(airpDST, airpCountry));
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

        return String.format("Entries Successfully Entered: %1$d.\n" +
                "Entries With Errors: %2$d", successful, error);
    }

    public ArrayList<Airport> getResult(){
        return parsedAirports;
    }

    public ArrayList<City> getCityResult(){
        return parsedCities;
    }

    public ArrayList<Country> getCountryResult(){
        return parsedCountries;
    }

}
