package seng202.group9.Controller;

import javafx.scene.chart.PieChart;
import seng202.group9.Core.Airline;
import seng202.group9.Core.Airport;
import seng202.group9.Core.FlightPoint;
import seng202.group9.Core.Route;

/**
 * Created by Gondr on 21/09/2016.
 */
public class EntryParser {

    public EntryParser(){

    }

    public Airline parseAirline(String name, String alias, String IATA, String ICAO, String callsign, String country, String active) throws DataException{
        if (name.equals("")){
            throw new DataException("Name cannot be Empty.");
        }
        //alias
        if (alias.equals("\\N") || alias.equals("\\n")){
            alias = "";
        }
        //IATA must be either empty, - or a 2 letter code
        if (IATA.length() != 2 && IATA.length() != 1 && IATA.equals("-")){
            throw new DataException("IATA Code must be either empty or 2 letters.");
        }
        if (IATA.equals("-")){
            IATA = "";
        }
        IATA = IATA.toUpperCase();
        //ICAO must be 3 letters or nothing
        if (ICAO.length() != 3 && ICAO.length() != 0){
            throw new DataException("ICAO Code must be either 3 letters or empty.");
        }
        ICAO = ICAO.toUpperCase();
        //Active must be Y or N
        if (!active.equals("Y") && !active.equals("N")){
            throw new DataException("Active must be Y or N.");
        }
        Airline successParse = new Airline(name, alias, IATA, ICAO, callsign, country, active);
        return successParse;
    }

    public Airport parseAirport(String name, String city, String country, String IATA_FFA, String ICAO, String latitude, String longitude, String altitude, String timezone, String DST, String olson) throws DataException{
        if (name.equals("")){
            throw new DataException("Name cannot be Empty.");
        }
        //IATA_FFA
        if (IATA_FFA.length() != 3 && IATA_FFA.length() != 0){
            throw new DataException("IATA/FFA Code must be 3 letters long or blank.");
        }
        IATA_FFA = IATA_FFA.toUpperCase();
        //ICAO
        if (ICAO.length() != 4 && ICAO.length() != 0){
            throw new DataException("ICAO must be 4 letters long or blank.");
        }
        //latitude
        double lat;
        try{
            lat = Double.parseDouble(latitude);
        }catch (NumberFormatException e){
            throw new DataException("Latitude must be a Number");
        }
        if (lat > 90 || lat < -90){
            throw new DataException("Latitude must be between -90 and 90 inclusive.");
        }
        //longitude
        double lng;
        try{
            lng = Double.parseDouble(longitude);
        }catch (NumberFormatException e){
            throw new DataException("Longitude must be a Number");
        }
        if (lng > 180 || lng < -180){
            throw new DataException("Longitude must be between -180 and 180 inclusive.");
        }
        //altitude
        double alt;
        try{
            alt = Double.parseDouble(altitude);
        }catch (NumberFormatException e){
            throw new DataException ("Altitude must be a number");
        }
        //timezone
        double tz;
        try{
            tz = Double.parseDouble(timezone);
        }catch (NumberFormatException e){
            throw new DataException ("Timezone must be a number");
        }
        if (tz > 14 || tz < -12){
            throw new DataException("Timezone must be between 14 and -12 UTC inclusive");
        }
        //DST
        if (!DST.equals("E") && !DST.equals("A") && !DST.equals("S") && !DST.equals("O") && !DST.equals("Z") && !DST.equals("N") && !DST.equals("U")){
            throw new DataException ("TDST must be either E (Europe), A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown)");
        }
        if (olson.equals("\\N") || olson.equals("\\n")){
            olson.equals("");
        }
        Airport successParse = new Airport(name, city, country, IATA_FFA, ICAO, lat, lng, alt);
        return successParse;
    }

    public Route parseRoute(String airline, String source, String dest, String code, String stops, String equip) throws DataException {
        if (airline.length() != 2 && airline.length() != 3){
            throw new DataException("Airline must be 2 letters (IATA Code) or 3 letters (ICAO Code).");
        }
        if (source.length() != 3 && source.length() != 4){
            throw new DataException("Source Airport must be 3 letters (IATA Code) or 4 letters (ICAO Code).");
        }
        if (dest.length() != 3 && dest.length() != 4){
            throw new DataException("Destination Airport must be 3 letters (IATA Code) or 4 letters (ICAO Code).");
        }
        if (!code.equals("Y") && !code.equals("")){
            throw new DataException("Codeshare must be Y or empty.");
        }
        int stop;
        try{
            stop = Integer.parseInt(stops);
        }catch (NumberFormatException e){
            throw new DataException ("Number of Stops must be a number.");
        }
        if (stop < 0){
            throw new DataException("Number of Stops must be more than 0.");
        }
        Route successParse = new Route(airline, source, dest, code, stop, equip);
        return successParse;
    }

    public FlightPoint parsePoint(String name, String type, String altitude, String latitude, String longitude) throws DataException{
        //(airport) name (first and last point)
        if (!isLetter(name)) {
            throw new DataException("Airport ICAO code must contain only letters");
        }
        if (name.length() != 4) {
            throw new DataException("Aiport ICAO code must be of length four");
        }
        //type
        type = type.toUpperCase();
        if (!type.equals("APT") && !type.equals("VOR") && !type.equals("FIX") && !type.equals("NDB") && !type.equals("LATLON")){
            throw new DataException("Type of flight must be either APT, VOR, FIX, NDB or LATLON");
        }
        //latitude
        double lat;
        try{
            lat = Double.parseDouble(latitude);
        }catch (NumberFormatException e){
            throw new DataException("Latitude must be a Number");
        }
        if (lat > 90 || lat < -90){
            throw new DataException("Latitude must be between -90 and 90 inclusive.");
        }
        //longitude
        double lng;
        try{
            lng = Double.parseDouble(longitude);
        }catch (NumberFormatException e){
            throw new DataException("Longitude must be a Number");
        }
        if (lng > 180 || lng < -180){
            throw new DataException("Longitude must be between -180 and 180 inclusive.");
        }
        //altitude
        double alt;
        try{
            alt = Double.parseDouble(altitude);
        }catch (NumberFormatException e){
            throw new DataException ("Altitude must be a number");
        }
        FlightPoint parseSuccess = new FlightPoint(type, name, alt, lat, lng);
        return parseSuccess;
    }

    /**
     * Throws an exception if the point name is not a valid AIRPORT ICAO code.
     * @param name
     * @throws DataException
     */
    public void parsePointName(String name)throws DataException{
        if (!isLetter(name)){
            throw new DataException("Airport ICAO code must contain only letters!");
        }
        if (name.length() != 4) {
            throw new DataException("Aiport ICAO code must be of length four!");
        }
    }
    /*
     * Cycles through a string to make sure all the characters are letters
     */
    private static boolean isLetter(String string){
        char[] chars = string.toCharArray();

        for (char element : chars) {
            if(!Character.isLetter(element)) {
                return false;
            }
        }
        return true;
    }

}
