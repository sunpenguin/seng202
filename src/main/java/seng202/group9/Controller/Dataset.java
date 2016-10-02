package seng202.group9.Controller;


import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import seng202.group9.Core.*;
import sun.awt.image.ImageWatched;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Dataset {

    String name;
    public static boolean getExisting = true;//constructor variables for action
    public static boolean createNew = false;//constructor variables for action
    private ArrayList<Airline> airlines;
    private ArrayList<Airport> airports;
    private ArrayList<Route> routes;
    private ArrayList<FlightPath> flightPaths;
    private ArrayList<Country> countries;
    private ArrayList<City> cities;
    private LinkedHashMap<String, Airline> airlineDictionary;//key name
    private LinkedHashMap<String, Airport> airportDictionary;//key name
    private LinkedHashMap<String, Route> routeDictionary;//key routeAirline + routeSourceAirport + routeArrvAirport + routeCodeShare + routeStops + routeEquip
    private LinkedHashMap<Integer, FlightPath> flightPathDictionary;//key path id
    private LinkedHashMap<String, Country> countryDictionary;//key name
    private LinkedHashMap<String, City> cityDictionary;//key city name
    private LinkedHashMap<Integer, FlightPoint> flightPointDictionary;//key point id
    private LinkedHashMap<String, Equipment> equipmentDictionary;

    /**
     *
     * @param name Name of the database
     * @param action either Dataset.getExisting or Dataset.createNew
     * @throws DataException Throws an exception if there is some error ie databases with the same name
     */
    public Dataset(String name, boolean action) throws DataException {
        this.name = name;
        this.airlines = new ArrayList<Airline>();
        this.airports = new ArrayList<Airport>();
        this.flightPaths = new ArrayList<FlightPath>();
        this.routes = new ArrayList<Route>();
        this.cities = new ArrayList<City>();
        this.countries = new ArrayList<Country>();
        this.airlineDictionary = new LinkedHashMap<String, Airline>();
        this.airportDictionary = new LinkedHashMap<String, Airport>();;
        this.routeDictionary = new LinkedHashMap<String, Route>();;
        this.countryDictionary = new LinkedHashMap<String, Country>();;
        this.cityDictionary = new LinkedHashMap<String, City>();;
        this.flightPathDictionary = new LinkedHashMap<Integer, FlightPath>();
        this.flightPointDictionary = new LinkedHashMap<Integer, FlightPoint>();
        this.equipmentDictionary = new LinkedHashMap<>();
        if (action == getExisting){
            updateDataset();
            //after this make connections. ie filling in the country.cities airports.routes etc
        }else if (action == createNew){
            createTables();
        }

    }

    /**
     * Updates Dataset Arrays from Database.
     * @throws DataException
     */
    public void updateDataset() throws DataException{
        Connection c = null;
        Statement stmt = null;
        int numOfDuplicateNames = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            stmt = c.createStatement();
            String queryName = this.name.replace("'", "''").replace("\"", "\"\"");//this allows quotes in datasets
            ResultSet rs = stmt.executeQuery( "SELECT COUNT(*) FROM `Datasets` WHERE `Dataset_Name` = '"+queryName+"';" );
            while ( rs.next() ) {
                numOfDuplicateNames = rs.getInt("COUNT(*)");
            }
            if (numOfDuplicateNames == 0){
                throw new DataException("There is no Dataset under this Name.");
            }
            rs.close();
            stmt.close();
            //if no problem update data;
            /*//////////////////
            //get all airlines//
            //////////////////*/
            stmt = c.createStatement();
            String queryAirlines = "SELECT * FROM `"+this.name+"_Airline`";
            rs = stmt.executeQuery(queryAirlines);
            while ( rs.next() ){
                //Airline(int ID, String name, String alias, String IATA, String ICAO, String callSign, String country, String active)
                int airID = rs.getInt("Airline_ID");
                String airName = rs.getString("Name");
                String airIATA = rs.getString("IATA");
                String airICAO = rs.getString("ICAO");
                String airAlias = rs.getString("Alias");
                String airCallsign = rs.getString("CallSign");
                String airCountry = rs.getString("Country");
                String airActive = rs.getString("Active");
                Airline airlineToAdd = new Airline(airID, airName, airAlias, airIATA, airICAO, airCallsign, airCountry, airActive);
                //assuming that all names will be unique
                airlineDictionary.put(airName, airlineToAdd);
                airlines.add(airlineToAdd);
            }
            rs.close();
            stmt.close();
            /*//////////////////
            //get all Airports//
            //////////////////*/
            stmt = c.createStatement();
            String queryAirport = "SELECT * FROM `"+this.name+"_Airport`";
            rs = stmt.executeQuery(queryAirport);
            while ( rs.next() ){
                //Airport(int ID, String name, String city, String country, String IATA_FFA, String ICAO, double latitude, double longitude, double altitude)
                int airpID = rs.getInt("Airport_ID");
                String airpName = rs.getString("Name");
                String airpCity = rs.getString("City");
                String airpCountry = rs.getString("Country");
                String airpIATA_FFA = rs.getString("IATA/FFA");
                String airpICAO = rs.getString("ICAO");
                double airpLatitude = rs.getDouble("Latitude");
                double airpLongitude = rs.getDouble("Longitude");
                double airpAltitude = rs.getDouble("Altitude");
                Airport airportToAdd = new Airport(airpID, airpName, airpCity, airpCountry, airpIATA_FFA, airpICAO, airpLatitude, airpLongitude, airpAltitude);
                //assuming all names will be unique
                airportDictionary.put(airpName, airportToAdd);
                airports.add(airportToAdd);
            }
            rs.close();
            stmt.close();
            /*////////////////
            //get all cities//
            ////////////////*/
            stmt = c.createStatement();
            String queryCities = "SELECT * FROM `"+this.name+"_City`";
            rs = stmt.executeQuery(queryCities);
            while ( rs.next() ){
	            //City(String name, String timezone, String timeOlson)
                String cityName = rs.getString("City_Name");
                String cityCountry = rs.getString("Country_Name");
                double cityTz = rs.getDouble("Timezone");
                String cityTimeOlson = rs.getString("Olson_Timezone");
                City cityToAdd = new City(cityName, cityCountry, cityTz, cityTimeOlson);
                //considering all city names are unique duplicates are handled elsewhere
                cityDictionary.put(cityName, cityToAdd);
                cities.add(cityToAdd);
            }
            rs.close();
            stmt.close();
            /*///////////////////
            //get all Countries//
            ///////////////////*/
            stmt = c.createStatement();
            String queryCountry = "SELECT * FROM `"+this.name+"_Country`";
            rs = stmt.executeQuery(queryCountry);
            while ( rs.next() ){
                //Country(String DST, String name)
                String countName = rs.getString("Country_Name");
                String countDST = rs.getString("DST");
                Country countryToAdd = new Country(countDST, countName);
                //we expect all country names to be unique from other countries
                countryDictionary.put(countName, countryToAdd);
                countries.add(countryToAdd);
            }
            rs.close();
            stmt.close();
            /*/////////////////////
            //get all Flight Path//
            /////////////////////*/
            stmt = c.createStatement();
            String queryFlightPath = "SELECT * FROM `"+this.name+"_Flight_Path`";
            rs = stmt.executeQuery(queryFlightPath);
            while ( rs.next() ){
                //FlightPath(int ID, String departureAirport, String arrivalAirport)
                int flightpID = rs.getInt("Path_ID");
                String flightpDepart = rs.getString("Source_Airport");
                String flightpArrive = rs.getString("Destination_Airport");
                //duplicates are fine so no flight dictionary is made.
                FlightPath flightPathToAdd = new FlightPath(flightpID, flightpDepart, flightpArrive);
                flightPaths.add(flightPathToAdd);
                flightPathDictionary.put(flightpID, flightPathToAdd);
            }
            rs.close();
            stmt.close();
            /*///////////////////////
            //get all flight points//
            ///////////////////////*/
            for (int i = 0; i < flightPaths.size(); i++){
                stmt = c.createStatement();
                String queryFlightPoints = "SELECT * FROM `" + this.name + "_Flight_Points` WHERE `Index_ID` = "+flightPaths.get(i).getID() + "  ORDER BY `Index_ID` ASC, `Order` ASC";
                rs = stmt.executeQuery(queryFlightPoints);
                while (rs.next()) {
                    //FlightPoint(String name, int ID, int indexID, String type, String via,
                    //int heading, double altitude, double legDistance, double totalDistance,
                    //double latitude, double longitude)
                    String flightPtName = rs.getString("Name");
                    int flightPtID = rs.getInt("Point_ID");
                    int flightPtInd = rs.getInt("Index_ID");
                    String flightPtType = rs.getString("Type");
                    String flightPtVia = rs.getString("Via");
                    int flightPtheading = rs.getInt("Heading");
                    double flightPtAltitude = rs.getDouble("Altitude");
                    double flightPtLegDistance = rs.getDouble("Leg_Dist");
                    double flightPtTotDist = rs.getDouble("Tot_Dist");
                    double flightPtLatitude = rs.getDouble("Latitude");
                    double flightPtLongitude = rs.getDouble("Longitude");
                    FlightPoint flightPoint = new FlightPoint(flightPtName, flightPtID, flightPtInd
                            , flightPtType, flightPtVia, flightPtheading, flightPtAltitude, flightPtLegDistance, flightPtTotDist,
                            flightPtLatitude, flightPtLongitude);
                    flightPaths.get(i).addFlightPoint(flightPoint);
                    flightPointDictionary.put(flightPtID, flightPoint);
                }
                rs.close();
                stmt.close();
            }
            /*////////////////
            //Get all Routes//
            ////////////////*/
            stmt = c.createStatement();
            String queryRoute = "SELECT * FROM `"+this.name+"_Routes`";
            rs = stmt.executeQuery(queryRoute);
            while ( rs.next() ){
                //Route(int ID, String airline, String departureAirport, String arrivalAirport,
                //String codeShare, int stops, String equipment)
                int routeID = rs.getInt("Route_ID");
                String routeAirline = rs.getString("Airline");
                String routeSourceAirport = rs.getString("Source_Airport");
                String routeArrvAirport = rs.getString("Destination_Airport");
                String routeCodeShare = rs.getString("Codeshare");
                int routeStops = rs.getInt("Stops");
                String routeEquip = rs.getString("Equipment");
                Route routeToAdd = new Route(routeID, routeAirline, routeSourceAirport, routeArrvAirport, routeCodeShare, routeStops, routeEquip);
                //unique identifier for the dictionary
                String identifier = routeAirline + routeSourceAirport + routeArrvAirport + routeCodeShare + routeStops + routeEquip;
                routeDictionary.put(identifier, routeToAdd);
                routes.add(routeToAdd);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        //update all flightpaths
        for (FlightPath flightPath: flightPaths){
            //updateFlightPointInfo(flightPath);
        }
        createDataLinks();
    }

    /**
     * Creates new Dataset with empty data tables etc
     * @throws DataException
     */
    public void createTables() throws DataException{
        Connection c = null;
        Statement stmt = null;
        int numOfDuplicateNames = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            stmt = c.createStatement();
            String queryName = this.name.replace("'", "''").replace("\"", "\"");//this allows quotes in datasets
            ResultSet rs = stmt.executeQuery( "SELECT COUNT(*) FROM `Datasets` WHERE `Dataset_Name` = '"+queryName+"';" );
            while ( rs.next() ) {
                numOfDuplicateNames = rs.getInt("COUNT(*)");
            }
            if (numOfDuplicateNames > 0){
                throw new DataException("There is already a Dataset with this Name.");
            }
            stmt.close();
            rs.close();
            //if no problem create tables;
            //create airline table;
            stmt = c.createStatement();
            String createAirlineQuery = "CREATE TABLE `"+this.name+"_Airline` " +
                    "(`Airline_ID` INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    "`Name` TEXT, " +
                    "`Alias` TEXT, " +
                    "`IATA` VARCHAR(2), " +
                    "`ICAO` VARCHAR(3), " +
                    "`Callsign` TEXT, " +
                    "`Country` TEXT, " +
                    "`Active` VARCHAR(1));";
            stmt.execute(createAirlineQuery);
            stmt.close();
            //create airport table;
            stmt = c.createStatement();
            String createAirportQuery = "CREATE TABLE `"+this.name+"_Airport` " +
                    "(`Airport_ID` INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    "`Name` TEXT, " +
                    "`City` TEXT, " +
                    "`Country` TEXT, " +
                    "`IATA/FFA` VARCHAR(3), " +
                    "`ICAO` VARCHAR(4), " +
                    "`Latitude` REAL, " +
                    "`Longitude` REAL, " +
                    "`Altitude` REAL);";
            stmt.execute(createAirportQuery);
            stmt.close();
            //create City table;
            stmt = c.createStatement();
            String createCityTable = "CREATE TABLE `"+this.name+"_City` " +
                    "(`City_Name` TEXT UNIQUE, " +
                    "`Country_Name` TEXT, " +
                    "`Timezone` REAL, " +
                    "`Olson_Timezone` TEXT)";
            stmt.execute(createCityTable);
            stmt.close();
            //create Country Table
            stmt = c.createStatement();
            String createCountryTable = "CREATE TABLE `"+this.name+"_Country` " +
                    "(`Country_Name` TEXT UNIQUE, " +
                    "`DST` VARCHAR(1))";
            stmt.execute(createCountryTable);
            stmt.close();
            //create flightpath table
            stmt = c.createStatement();
            String createFlightPathTable = "CREATE TABLE `"+this.name+"_Flight_Path` " +
                    "(`Path_ID` INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    "`Source_Airport` TEXT, " +
                    "`Destination_Airport` TEXT)";
            stmt.execute(createFlightPathTable);
            //create flight point table
            stmt = c.createStatement();
            String createFlightPointTable = "CREATE TABLE `"+this.name+"_Flight_Points` " +
                    "(`Index_ID` INTEGER ," +
                    "`Name` TEXT, " +
                    "`Point_ID` INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    "`Type` TEXT, " +
                    "`Via` TEXT, " +
                    "`Heading` TEXT, " +
                    "`Altitude` INTEGER, " +
                    "`Tot_Dist` INTEGER, " +
                    "`Latitude` REAL, " +
                    "`Longitude` REAL, " +
                    "`Leg_Dist` INTEGER, " +
                    "`Order` INTEGER)";
            stmt.execute(createFlightPointTable);
            stmt.close();
            //create routes table
            stmt = c.createStatement();
            String createRoutesTable = "CREATE TABLE `"+this.name+"_Routes` " +
                    "(`Route_ID` INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    "`Airline` TEXT, " +
                    "`Source_Airport` TEXT, " +
                    "`Destination_Airport` TEXT, " +
                    "`Codeshare` VARCHAR(1), " +
                    "`Stops` INTEGER, " +
                    "`Equipment` TEXT)";
            stmt.execute(createRoutesTable);
            stmt.close();
            //insert dataset into table
            stmt = c.createStatement();
            String insertDataset = "INSERT INTO `Datasets` (`Dataset_Name`) VALUES ('"+queryName+"');";
            stmt.execute(insertDataset);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    /**
     * Imports Airline files to the dataset
     * @param filePath
     * @return Success Message
     * @throws DataException
     */
    public String importAirline(String filePath) throws DataException {
        AirlineParser parser = new AirlineParser(filePath);
        //remember this still has to append the duplicate message to it.
        //airlines are identified by their names
        String message = parser.parse();
        ArrayList<Airline> airlinesToImport = parser.getResult();
        //check for dup
        int numOfDuplicates = 0;
        int nextID = 1;
        //query database.
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            stmt = c.createStatement();
            String queryName = this.name.replace("'", "''").replace("\"", "\"\"");
            String IDQuery = "SELECT * FROM `sqlite_sequence` WHERE `name` = '"+queryName+"_Airline' LIMIT 1;";
            ResultSet IDResult = stmt.executeQuery(IDQuery);
            while(IDResult.next()){
                nextID = Integer.parseInt(IDResult.getString("seq")) + 1;//for some reason sqlite3 stores incremental values as a string...
            }
            stmt.close();
            stmt = c.createStatement();
            String insertAirlineQuery = "INSERT INTO `" + this.name + "_Airline` (`Name`, `Alias`, `IATA`, `ICAO`" +
                    ", `Callsign`, `Country`, `Active`) VALUES ";
            int numOfAirlines = 0;
            for (int i = 0; i < airlinesToImport.size(); i ++){
                if (airlineDictionary.containsKey(airlinesToImport.get(i).getName())){
                    numOfDuplicates ++;
                }else{
                    //insert import into database
                    String airName = airlinesToImport.get(i).getName().replace("\"", "\"\"");
                    String airAlias = airlinesToImport.get(i).getAlias().replace("\"", "\"\"");
                    String airIATA = airlinesToImport.get(i).getIATA().replace("\"", "\"\"");
                    String airICAO = airlinesToImport.get(i).getICAO().replace("\"", "\"\"");
                    String airCallsign = airlinesToImport.get(i).getCallSign().replace("\"", "\"\"");
                    String airCountry = airlinesToImport.get(i).getCountryName().replace("\"", "\"\"");
                    String airActive = airlinesToImport.get(i).getActive().replace("\"", "\"\"");
                    if (numOfAirlines > 0){
                        insertAirlineQuery += ",";
                    }
                    insertAirlineQuery += "(\""+airName+"\", \"" + airAlias + "\", \"" + airIATA + "\"," +
                            " \"" + airICAO + "\", \"" + airCallsign + "\", \"" + airCountry + "\", \"" + airActive + "\")";
                    airlinesToImport.get(i).setID(nextID);
                    //add data to dataset array.
                    //this is placed after incase the database messes up
                    airlines.add(airlinesToImport.get(i));
                    airlineDictionary.put(airName, airlinesToImport.get(i));
                    nextID++;
                    numOfAirlines++;
                }
            }
            if (numOfAirlines > 0){
                stmt.execute(insertAirlineQuery);
                stmt.close();
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        message += "\nDuplicates ommitted: "+numOfDuplicates;
        createDataLinks();
        return message;
    }
    /**
     * Imports Airport files to the dataset
     * @param filePath
     * @return Success Message
     * @throws DataException
     */
    public String importAirport(String filePath) throws DataException {
        AirportParser parser = new AirportParser(filePath);
        //remember this still has to append the duplicate message to it.
        //airport are identified by their names
        String message = parser.parse();
        ArrayList<Airport> airportsToImport = parser.getResult();
        ArrayList<City> citiesToImport = parser.getCityResult();
        ArrayList<Country> countriesToImport = parser.getCountryResult();
        //check for dup
        int numOfDuplicates = 0;
        int nextID = 1;
        //query database.
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            //get next ID
            stmt = c.createStatement();
            String queryName = this.name.replace("'", "''");
            String IDQuery = "SELECT * FROM `sqlite_sequence` WHERE `name` = '" + queryName + "_Airport' LIMIT 1;";
            ResultSet IDResult = stmt.executeQuery(IDQuery);
            while (IDResult.next()) {
                nextID = Integer.parseInt(IDResult.getString("seq")) + 1;//for some reason sqlite3 stores incremental values as a string...
            }
            stmt.close();
            stmt = c.createStatement();
            String insertAirportQuery = "INSERT INTO `" + this.name + "_Airport` (`Name`, `City`, `Country`, `IATA/FFA`," +
                    " `ICAO`, `Latitude`, `Longitude`, `Altitude`) VALUES ";
            int numOfAirports = 0;
            for (int i = 0; i < airportsToImport.size(); i++) {
                if (airportDictionary.containsKey(airportsToImport.get(i).getName())) {
                    numOfDuplicates++;
                } else {
                    //airport variables
                    String airpName = airportsToImport.get(i).getName().replace("\"", "\"\"");
                    String airpCity = airportsToImport.get(i).getCityName().replace("\"", "\"\"");
                    String airpCountry = airportsToImport.get(i).getCountryName().replace("\"", "\"\"");
                    String airpIATA_FFA = airportsToImport.get(i).getIATA_FFA().replace("\"", "\"\"");
                    String airpICAO = airportsToImport.get(i).getICAO().replace("\"", "\"\"");
                    double airpLat = airportsToImport.get(i).getLatitude();
                    double airpLong = airportsToImport.get(i).getLongitude();
                    double airpAltitude = airportsToImport.get(i).getAltitude();
                    if (numOfAirports > 0) {
                        insertAirportQuery += ",";
                    }
                    insertAirportQuery += "(\"" + airpName + "\", \"" + airpCity + "\", \"" + airpCountry + "\", \"" + airpIATA_FFA + "\"," +
                            " \"" + airpICAO + "\", " + airpLat + ", " + airpLong + ", " + airpAltitude + ")";
                    airportsToImport.get(i).setID(nextID);
                    //this is placed after incase the database messes up
                    airports.add(airportsToImport.get(i));
                    airportDictionary.put(airpName, airportsToImport.get(i));
                    nextID++;
                    numOfAirports++;
                }
            }
            if (numOfAirports > 0) {
                stmt.execute(insertAirportQuery);
            }
            stmt.close();
            stmt = c.createStatement();
            /*///////////////
            //Insert Cities//
            ///////////////*/
            String insertCityQuery = "INSERT INTO `" + this.name + "_City` (`City_Name`, `Country_Name`, `Timezone`, " +
                    "`Olson_Timezone`) VALUES ";
            int numOfCities = 0;
            for (int i = 0; i < citiesToImport.size(); i++) {
                if (cityDictionary.containsKey(citiesToImport.get(i).getName())) {
                    //duplicates are not increased as this is not an airport
                } else {
                    //city variables
                    String cityName = citiesToImport.get(i).getName().replace("\"", "\"\"");
                    String cityCountry = citiesToImport.get(i).getCountry().replace("\"", "\"\"");
                    double cityTz = citiesToImport.get(i).getTimezone();
                    String cityOlson = citiesToImport.get(i).getTimeOlson().replace("\"", "\"\"");
                    if (numOfCities > 0){
                        insertCityQuery += ",";
                    }
                    insertCityQuery += "(\"" + cityName + "\", \"" + cityCountry + "\", " + cityTz + ", \"" + cityOlson + "\")";
                    //this is placed after incase the database messes up
                    cities.add(citiesToImport.get(i));
                    cityDictionary.put(cityName, citiesToImport.get(i));
                    numOfCities++;
                }
            }
            if (numOfCities > 0) {
                stmt.execute(insertCityQuery);
            }
            stmt.close();
            stmt = c.createStatement();
            /*//////////////////
            //Insert Countries//
            //////////////////*/
            String insertCountryQuery = "INSERT INTO `" + this.name + "_Country` (`Country_Name`, `DST`) VALUES ";
            int numOfCountries = 0;
            for (int i = 0; i < countriesToImport.size(); i++) {
                if (countryDictionary.containsKey(countriesToImport.get(i).getName())) {
                    //duplicates are not increased as this is not an airport
                } else {
                    //country variables
                    String countryName = countriesToImport.get(i).getName().replace("\"", "\"\"");
                    String countryDST = countriesToImport.get(i).getDST().replace("\"", "\"\"");
                    if (numOfCountries > 0){
                        insertCountryQuery += ",";
                    }
                    insertCountryQuery += "(\"" + countryName + "\", \"" + countryDST + "\")";
                    //this is placed after incase the database messes up
                    countries.add(countriesToImport.get(i));
                    countryDictionary.put(countryName, countriesToImport.get(i));
                    numOfCountries++;
                }
            }
            if (numOfCountries > 0){
                stmt.execute(insertCountryQuery);
            }
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        message += "\nDuplicates ommitted: "+numOfDuplicates;
        createDataLinks();
        return message;
    }

    /**
     * Imports Route files to dataset
     * @param filePath
     * @return Success Message
     * @throws DataException
     */
    public String importRoute(String filePath) throws DataException {
        RouteParser parser = new RouteParser(filePath);
        //remember this still has to append the duplicate message to it.
        //routes are identified in the diction by routeAirline + routeSourceAirport + routeArrvAirport + routeCodeShare + routeStops + routeEquip;
        String message = parser.parse();
        ArrayList<Route> routesToImport = parser.getResult();
        //check for dup
        int numOfDuplicates = 0;
        int nextID = 1;
        //query database.
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            stmt = c.createStatement();
            String queryName = this.name.replace("'", "''").replace("\"", "\"\"");
            String IDQuery = "SELECT * FROM `sqlite_sequence` WHERE `name` = '"+queryName+"_Routes' LIMIT 1;";
            ResultSet IDResult = stmt.executeQuery(IDQuery);
            while(IDResult.next()){
                nextID = Integer.parseInt(IDResult.getString("seq")) + 1;//for some reason sqlite3 stores incremental values as a string...
            }
            stmt.close();

            stmt = c.createStatement();
            String insertRouteQuery = "INSERT INTO `" + this.name + "_Routes` (`Airline`, `Source_Airport`, `Destination_Airport`," +
                    " `Codeshare`, `Stops`, `Equipment`) VALUES ";
            int numOfRoutes = 0;
            for (int i = 0; i < routesToImport.size(); i ++){
                String routeIdentifier = routesToImport.get(i).getAirline() + routesToImport.get(i).getDepartureAirport() + routesToImport.get(i).getArrivalAirport() +
                        routesToImport.get(i).getCode() + routesToImport.get(i).getStops() + routesToImport.get(i).getEquipment();
                if (routeDictionary.containsKey(routeIdentifier)){
                    numOfDuplicates ++;
                }else{
                    //route variables
                    String routeAirline = routesToImport.get(i).getAirlineName().replace("\"", "\"\"");
                    String routeSource = routesToImport.get(i).getDepartureAirport().replace("\"", "\"\"");
                    String routeDestination = routesToImport.get(i).getArrivalAirport().replace("\"", "\"\"");
                    String routeCode = routesToImport.get(i).getCode().replace("\"", "\"\"");
                    int routeStops = routesToImport.get(i).getStops();
                    String routeEquipment = routesToImport.get(i).getEquipment().replace("\"", "\"\"");
                    //insert import into database
                    if (numOfRoutes > 0){
                        insertRouteQuery += ",";
                    }
                    insertRouteQuery += "(\""+routeAirline+"\", \"" + routeSource + "\", \"" + routeDestination + "\", " +
                            "\"" + routeCode + "\", " + routeStops + ", \"" + routeEquipment + "\")";
                    routesToImport.get(i).setID(nextID);
                    //add data to dataset array.
                    //this is placed after incase the database messes up
                    routes.add(routesToImport.get(i));
                    routeDictionary.put(routeIdentifier, routesToImport.get(i));
                    nextID++;
                    numOfRoutes++;
                }
            }
            if (numOfRoutes > 0){
                stmt.execute(insertRouteQuery);
                stmt.close();
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        message += "\nDuplicates ommitted: "+numOfDuplicates;
        createDataLinks();
        return message;
    }

    /**
     * Imports Flight files to dataset
     * @param filePath
     * @return Success Message
     * @throws DataException
     */
    public String importFlight(String filePath) throws DataException {
        FlightPathParser parser = new FlightPathParser(filePath);
        //remember this still has to append the duplicate message to it.
        //routes are identified in the diction by routeAirline + routeSourceAirport + routeArrvAirport + routeCodeShare + routeStops + routeEquip;
        String message = parser.parse();
        ArrayList<FlightPoint> flightPointsToImport = parser.getResult();
        //check for dup
        int nextID = 1;
        //query database.
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            stmt = c.createStatement();
            String queryName = this.name.replace("\"", "\"\"");
            String IDQuery = "SELECT * FROM `sqlite_sequence` WHERE `name` = \""+queryName+"_Flight_Points\" LIMIT 1;";
            ResultSet IDResult = stmt.executeQuery(IDQuery);
            while(IDResult.next()){
                nextID = Integer.parseInt(IDResult.getString("seq")) + 1;//for some reason sqlite3 stores incremental values as a string...
            }
            stmt.close();
            stmt = c.createStatement();

            String firstPt = flightPointsToImport.get(0).getName();
            String lastPt = flightPointsToImport.get(flightPointsToImport.size() - 1).getName();
            FlightPath flightPathToAdd = new FlightPath(firstPt, lastPt);

            String insertFlightPathQuery = "INSERT INTO `" + this.name + "_Flight_Path` (`Source_Airport`, `Destination_Airport`)" +
                    "VALUES ( \"" + firstPt + "\",\"" + lastPt + "\") ";
            stmt.execute(insertFlightPathQuery);
            stmt.close();
            stmt = c.createStatement();
            int flightPathId = 1;
            String getLastestIndex = "SELECT * FROM `sqlite_sequence` WHERE `name` = \"" + this.name.replace("\"", "\"\"") +
                    "_Flight_Path\"  LIMIT 1;";
            ResultSet lastestIdResult = stmt.executeQuery(getLastestIndex);
            while(lastestIdResult.next()){
                flightPathId = Integer.parseInt(lastestIdResult.getString("seq"));//for some reason sqlite3 stores incremental values as a string...
            }
            stmt.close();
            lastestIdResult.close();
            stmt = c.createStatement();
            flightPathToAdd.setID(flightPathId);

            String insertFlightPointQuery = "INSERT INTO `" + this.name + "_Flight_Points` (`Index_ID`, `Name`, `Type`," +
                    " `Altitude`, `Latitude`, `Longitude`, `Order`) VALUES ";
            int numOfFlights = 0;
            for (int i = 0; i < flightPointsToImport.size(); i ++){
                String flightPointIdentifier = flightPointsToImport.get(i).getType() + flightPointsToImport.get(i).getName() +
                        flightPointsToImport.get(i).getAltitude() + flightPointsToImport.get(i).getLatitude() +
                        flightPointsToImport.get(i).getLongitude();

                String flightType = flightPointsToImport.get(i).getType().replace("\"", "\"\"");
                String flightName = flightPointsToImport.get(i).getName().replace("\"", "\"\"");
                double flightAltitude = flightPointsToImport.get(i).getAltitude();
                double flightLatitude = flightPointsToImport.get(i).getLatitude();
                double flightLongitude = flightPointsToImport.get(i).getLongitude();
                //insert import into database
                if (numOfFlights > 0) {
                    insertFlightPointQuery += ",";
                }
                insertFlightPointQuery += "(" + flightPathId +", \""+ flightName +"\", \"" + flightType + "\",  "+ flightAltitude + ", " +
                        "" + flightLatitude + ", " + flightLongitude + ", "+numOfFlights+")";
                flightPointsToImport.get(i).setID(nextID);
                flightPointsToImport.get(i).setIndexID(flightPathId);
                //add data to dataset array.
                //this is placed after incase the database messes up
                flightPathToAdd.addFlightPoint(flightPointsToImport.get(i));
                //routeDictionary.put(routeIdentifier, flightsToImport.get(i));
                flightPointDictionary.put(nextID, flightPointsToImport.get(i));
                nextID++;
                numOfFlights++;
                //}
            }
            if (numOfFlights > 0){
                stmt.execute(insertFlightPointQuery);
            }
            stmt.close();
            c.close();

            flightPaths.add(flightPathToAdd);
            updateFlightPointInfo(flightPathToAdd);
            flightPathDictionary.put(flightPathToAdd.getID(), flightPathToAdd);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            e.printStackTrace();
            System.exit(0);
        }
        createDataLinks();
        return message;
    }

    /**
     * This function updates the connections between airports citys countries etc.
     */

    public void createDataLinks(){
        //this may be seperated into more sepearate function in the future for time optimisation
        HashMap<String, Airline> airlineByIATA= new HashMap<String, Airline>();
        for (Country country: countries){
            country.setAirlines(new ArrayList<Airline>());
            country.setCities(new ArrayList<City>());
        }
        for (City city: cities){
            city.setAirports(new ArrayList<Airport>());
        }
        //create Airline country link
        for (Airline airline: airlines){
            airlineByIATA.put(airline.getIATA(), airline);
            airline.setRoutes(new ArrayList<Route>());
            airline.setCountry(countryDictionary.get(airline.getCountryName()));
            Country country = countryDictionary.get(airline.getCountryName());
            if (country != null){
                country.addAirline(airline);
            }
        }
        //create Airport City and Country Link
        HashMap<String, Airport> airportsByIATA = new HashMap<String, Airport>(); //this is used later for connecting the routes
        HashMap<String, Airport> airportsByICAO = new HashMap<String, Airport>(); //this is used later for connecting the routes
        for (Airport airport: airports){
            //System.out.println(airport.getIATA_FFA());
            airportsByIATA.put(airport.getIATA_FFA(), airport);
            airportsByICAO.put(airport.getICAO(), airport);
            airport.setCountry(countryDictionary.get(airport.getCountryName()));
            //airport.getCountry().setPosition(new Position(airport.getLatitude(), airport.getLongitude()));
            //TODO Add City in country (This is extra work).
            airport.setCity(cityDictionary.get(airport.getCityName()));
            if (airport.getCity() != null) {
                airport.getCity().addAirport(airport);
            }
            airport.setDepartureRoutes(new ArrayList<Route>());
            airport.setArrivalRoutes(new ArrayList<Route>());
        }
        equipmentDictionary = new LinkedHashMap<>();
        //set Airport variables for route
        for (Route route: routes){
            String[] equipment = route.getEquipment().split(" ");
            for (String equip: equipment){
                if (equip != "" && equip != null){
                    Equipment equipment1 = equipmentDictionary.get(equip);
                    if (equipment1 != null){
                        equipment1.addRoute(route);
                    }else{
                        equipment1 = new Equipment(equip);
                        equipment1.addRoute(route);
                        equipmentDictionary.put(equip, equipment1);
                    }
                }
            }
            if (route.getDepartureAirport().length() > 3){
                route.setSourceAirport(airportsByICAO.get(route.getDepartureAirport()));
                if (airportsByICAO.get(route.getDepartureAirport()) != null) {
                    airportsByICAO.get(route.getDepartureAirport()).addDepartureRoutes(route);
                }
            }else{
                route.setSourceAirport(airportsByIATA.get(route.getDepartureAirport()));
                if (airportsByIATA.get(route.getDepartureAirport()) != null){
                    airportsByIATA.get(route.getDepartureAirport()).addDepartureRoutes(route);
                }
            }
            if (route.getArrivalAirport().length() > 3){
                route.setDestinationAirport(airportsByICAO.get(route.getArrivalAirport()));
                if (airportsByICAO.get(route.getArrivalAirport()) != null) {
                    airportsByICAO.get(route.getArrivalAirport()).addArrivalRoutes(route);
                }
            }else{
                route.setDestinationAirport(airportsByIATA.get(route.getArrivalAirport()));
                if (airportsByIATA.get(route.getArrivalAirport()) != null) {
                    airportsByIATA.get(route.getArrivalAirport()).addArrivalRoutes(route);
                }
            }
            route.setAirline(airlineByIATA.get(route.getAirlineName()));
            Airline airline = airlineByIATA.get(route.getAirlineName());
            if (airline != null){
                airline.addRoutes(route);
            }
        }
        System.out.println("Links Made");
    }

    /**
     * Addes Single Airline to Program and Database.
     * @param name
     * @param alias
     * @param IATA
     * @param ICAO
     * @param callsign
     * @param country
     * @param active
     * @throws DataException
     */
    public void addAirline(String name, String alias, String IATA, String ICAO, String callsign, String country, String active) throws DataException{
        Airline airlineToAdd = new Airline(name, alias, IATA, ICAO, callsign, country, active);
        if (name.equals("")) {
            throw new DataException("You cannot have a blank airline name.");
        }
        if (alias.length() <= 0) {
            throw new DataException("Please insert '\\N' if the airline has no alias.");
        }
        if (country.equals("")) {
            throw new DataException("You cannot have a blank country of origin field.");
        }
        addAirline(airlineToAdd);
    }

    /**
     * Adds a Single Airline from the Program to the Database
     * @param airlineToAdd
     * @throws DataException
     */
    public void addAirline(Airline airlineToAdd) throws DataException{
        if (airlineToAdd.getIATA().length() != 0 && airlineToAdd.getIATA().length() != 2){
            throw new DataException("IATA is either empty or length of 2 Letters.");
        }
        if (airlineToAdd.getICAO().length() != 0 && airlineToAdd.getICAO().length() != 3){
            throw new DataException("ICAO is either empty or length of 3 Letters.");
        }
        if (airlineToAdd.getActive().length() != 1){
            throw new DataException ("Active must be Y or N.");
        }
        for (String key : airlineDictionary.keySet()){
            airlineDictionary.get(key).hasDuplicate(airlineToAdd);
        }
        //checking is done now we add it to the dictionary and the database
        //query database.
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            //add the airline
            stmt = c.createStatement();
            String insertAirlineQuery = "INSERT INTO `" + this.name + "_Airline` (`Name`, `Alias`, `IATA`, `ICAO`" +
                    ", `Callsign`, `Country`, `Active`) VALUES (\""+airlineToAdd.getName()+"\", \"" + airlineToAdd.getAlias() + "\", " +
                    "\"" + airlineToAdd.getIATA() + "\", \"" + airlineToAdd.getICAO() + "\", \"" + airlineToAdd.getCallSign() + "\", " +
                    "\"" + airlineToAdd.getCountryName() + "\", \"" + airlineToAdd.getActive() + "\");";
            stmt.execute(insertAirlineQuery);
            //get the airline id
            stmt = c.createStatement();
            String airlineIDQuery = "SELECT * FROM `sqlite_sequence` WHERE `name` = \""+this.name+"_Airline\" LIMIT 1;";
            ResultSet airlineIDRes= stmt.executeQuery(airlineIDQuery);
            int airlineID = 0;
            while (airlineIDRes.next()){
                airlineID = Integer.parseInt(airlineIDRes.getString("seq"));
            }
            airlineToAdd.setID(airlineID);
            airlines.add(airlineToAdd);
            airlineDictionary.put(airlineToAdd.getName(), airlineToAdd);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        createDataLinks();
    }

    /**
     * Adds a single Airport from the Program to the Database
     * @param name
     * @param city
     * @param country
     * @param IATA_FFA
     * @param ICAO
     * @param latitude
     * @param longitude
     * @param altitude
     * @param timezone
     * @param DST
     * @param olsonTz
     * @throws DataException
     */
    public void addAirport(String name, String city, String country, String IATA_FFA, String ICAO, String latitude, String longitude,
                           String altitude, String timezone, String DST, String olsonTz) throws DataException{
        try{
            //System.out.print(name + city + country + IATA_FFA + ICAO + latitude + longitude + altitude + timezone + DST + olsonTz);
            double latitudeVal = Double.parseDouble(latitude);
            double longitudeVal = Double.parseDouble(longitude);
            double altitudeVal = Double.parseDouble(altitude);
            double timezoneVal = Double.parseDouble(timezone);
            if (city.equals("")) {
                throw new DataException("You cannot have a blank city name.");
            }
            if (country.equals("")) {
                throw new DataException("You cannot have a blank country name.");
            }
            Airport airportToAdd = new Airport(name, city, country, IATA_FFA, ICAO, latitudeVal, longitudeVal, altitudeVal);
            City cityToAdd = new City(city, country, timezoneVal, olsonTz);
            Country countryToAdd = new Country(DST, country);
            addAirport(airportToAdd);
            addCity(cityToAdd);
            addCountry(countryToAdd);
            createDataLinks();
        }catch (NumberFormatException e){
            throw new DataException("Latitude, Longitude, Altitude and Timezone must be numbers");
        }
    }

    /**
     * gets the name of the dataset.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Adds an Airport to the database and dataset.
     * @param airportToAdd
     * @throws DataException
     */
    private void addAirport(Airport airportToAdd) throws DataException{
        if (airportToAdd.getIATA_FFA().length() != 0 && airportToAdd.getIATA_FFA().length() != 3){
            throw new DataException("IATA/FFA either empty or 3 letters");
        }
        if (airportToAdd.getICAO().length() != 0 && airportToAdd.getICAO().length() != 4){
            throw new DataException("ICAO either empty or 4 letters");
        }
        if (airportToAdd.getName().equals("")) {
            throw new DataException("You cannot have an airport without a name.");
        }
        for (String key : airportDictionary.keySet()){
            airportDictionary.get(key).hasDuplicate(airportToAdd);
        }
        //checking is done now we add it to the dictionary and the database
        //query database.
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            //add the airport
            stmt = c.createStatement();
            String insertAirportQuery = "INSERT INTO `" + this.name + "_Airport`  (`Name`, `City`, `Country`, `IATA/FFA`, " +
                    "`ICAO`, `Latitude`, `Longitude`, `Altitude`) VALUES (\""+airportToAdd.getName()+"\", \""+airportToAdd.getCityName()+"\", " +
                    "\""+airportToAdd.getCountryName()+"\", \""+airportToAdd.getIATA_FFA()+"\", \""+airportToAdd.getICAO()+"\", " +
                    ""+airportToAdd.getLatitude()+", "+airportToAdd.getLongitude()+", "+airportToAdd.getAltitude()+");";
            stmt.execute(insertAirportQuery);
            stmt.close();
            //get the airport id
            stmt = c.createStatement();
            String airportIDQuery = "SELECT * FROM `sqlite_sequence` WHERE `name` = \""+this.name+"_Airport\" LIMIT 1;";
            ResultSet airportIDRes= stmt.executeQuery(airportIDQuery);
            int airportID = 0;
            while (airportIDRes.next()){

                airportID = Integer.parseInt(airportIDRes.getString("seq"));
            }
            airportToAdd.setID(airportID);
            airports.add(airportToAdd);
            airportDictionary.put(airportToAdd.getName(), airportToAdd);
            airportIDRes.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    /**
     * Adds a city to the dataset and database
     * @param city
     */
    private void addCity(City city){
        if (!cityDictionary.containsKey(city.getName())){
            Connection c = null;
            Statement stmt = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
                //add the city
                stmt = c.createStatement();
                String cityName = city.getName().replace("\"", "\"\"");
                String countryName = city.getCountry().replace("\"", "\"\"");
                String olson = city.getTimeOlson().replace("\"", "\"\"");
                String insertCityQuery = "INSERT INTO `" + this.name + "_City` (`City_Name`, `Country_Name`, `Timezone`, " +
                        "`Olson_Timezone`) VALUES (\""+cityName+"\", \""+countryName+"\", "+city.getTimezone() + ", \""+olson+"\");";
                stmt.execute(insertCityQuery);
                stmt.close();
                cityDictionary.put(city.getName(), city);
                cities.add(city);
                c.close();
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
        }
    }

    /**
     * Adds a Country to the dataset and database
     * @param country
     */
    private void addCountry(Country country){
        if (!countryDictionary.containsKey(country.getName())){
            Connection c = null;
            Statement stmt = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
                //add the city
                stmt = c.createStatement();
                String countryName = country.getName().replace("\"", "\"\"");
                String countryDST = country.getDST().replace("\"", "\"\"");
                String insertCityQuery = "INSERT INTO `" + this.name + "_Country` (`Country_Name`, `DST`) VALUES" +
                        " (\""+countryName+"\", \""+countryDST+"\");";
                stmt.execute(insertCityQuery);
                stmt.close();
                countryDictionary.put(country.getName(), country);
                countries.add(country);
                c.close();
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
        }
    }

    /**
     * Adds one single route to the program.
     * @param airline
     * @param sourceAirport
     * @param destAirport
     * @param codeshare
     * @param stops
     * @param equipment
     * @throws DataException
     */
    public void addRoute(String airline, String sourceAirport, String destAirport, String codeshare, String stops, String equipment) throws DataException{
        int stopsVal = 0;
        try{
            stopsVal = Integer.parseInt(stops);
        }catch (NumberFormatException e){
            throw new DataException("Stops must be a greater than or equal to 0.");
        }
        if (stopsVal < 0){
            throw new DataException("Stops must be a greater than or equal to 0.");
        }
        Route routeToAdd = new Route(airline, sourceAirport, destAirport, codeshare, stopsVal, equipment);
        addRoute(routeToAdd);
    }

    /**
     * Adds a single route the dataset and database.
     * @param routeToAdd
     * @throws DataException
     */
    public void addRoute(Route routeToAdd) throws DataException{
        if (routeToAdd.getAirlineName().length() != 2 && routeToAdd.getAirlineName().length() != 3){
            throw new DataException("Airline ICAO code must be 2 or 3 letters.");
        }
        if (routeToAdd.getDepartureAirport().length() != 3 && routeToAdd.getDepartureAirport().length() != 4){
            throw new DataException("Airport Source Airport IATA must be 3 letters or 4 letters if ICAO.");
        }
        if (routeToAdd.getArrivalAirport().length() != 3 && routeToAdd.getArrivalAirport().length() != 4){
            throw new DataException("Airport Destination Airport IATA must be 3 letters or 4 letters if ICAO.");
        }
        if (routeToAdd.getCode().length() != 0 && routeToAdd.getCode().length() != 1){
            throw new DataException("Codeshare has to be empty or Y.");
        }
        for (String key : routeDictionary.keySet()){
            routeDictionary.get(key).hasDuplicate(routeToAdd);
        }
        //checking is done now we add it to the dictionary and the database
        //query database.
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            //add the airline
            stmt = c.createStatement();
            String airline = routeToAdd.getAirlineName().replace("\"", "\"\"");
            String sourceAir = routeToAdd.getDepartureAirport().replace("\"", "\"\"");
            String destAir = routeToAdd.getArrivalAirport().replace("\"", "\"\"");
            String equipment = routeToAdd.getEquipment().replace("\"", "\"\"");
            String insertRouteQuery = "INSERT INTO `" + this.name + "_Routes` (`Airline`, `Source_Airport`, `Destination_Airport`," +
                    " `Codeshare`, `Stops`, `Equipment`) VALUES (\""+airline+"\", \""+sourceAir+"\", \""+destAir+"\", " +
                    "\""+routeToAdd.getCode()+"\", "+routeToAdd.getStops()+", \""+equipment+"\")";
            stmt.execute(insertRouteQuery);
            //get the airline id
            stmt = c.createStatement();
            String routeIDQuery = "SELECT * FROM `sqlite_sequence` WHERE `name` = \""+this.name+"_Route\" LIMIT 1;";
            ResultSet routeIDRes= stmt.executeQuery(routeIDQuery);
            int routeID = 0;
            while (routeIDRes.next()){
                routeID = Integer.parseInt(routeIDRes.getString("seq"));
            }
            routeToAdd.setID(routeID);
            routes.add(routeToAdd);
            //routeAirline + routeSourceAirport + routeArrvAirport + routeCodeShare + routeStops + routeEquip
            String routeKey = routeToAdd.getAirlineName() + routeToAdd.getDepartureAirport() + routeToAdd.getArrivalAirport() + routeToAdd.getCode() + routeToAdd.getStops() + routeToAdd.getEquipment();
            routeDictionary.put(routeKey, routeToAdd);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        createDataLinks();
    }

    /**
     * Adds a path to the database and to the path dictionary
     * @param sourceAirport
     * @param destAirport
     */
    public void addFlightPath(String sourceAirport, String destAirport){
        FlightPath newPath = new FlightPath(sourceAirport, destAirport);
        Connection c;
        Statement stmt;
        int pathID = 0;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");

            stmt = c.createStatement();
            String flightPathIDQuery = "SELECT * FROM `sqlite_sequence` WHERE `name` = \""+this.name+"_Flight_Path\" LIMIT 1;";
            ResultSet pathIDRes= stmt.executeQuery(flightPathIDQuery);
            while (pathIDRes.next()){
                pathID = Integer.parseInt(pathIDRes.getString("seq"));
            }
            pathID +=1;
            stmt.close();

            stmt = c.createStatement();
            String insertPathQuery = "INSERT INTO `" + this.name + "_Flight_Path` (`Path_ID`, `Source_Airport`, " +
                    "`Destination_Airport`) VALUES ("+pathID+", \""+sourceAirport+"\", \""+destAirport+"\" )";
            stmt.execute(insertPathQuery);
            newPath.setID(pathID);

            flightPathDictionary.put(pathID, newPath);
            flightPaths.add(newPath);
            FlightPoint sourcePoint = new FlightPoint(sourceAirport, pathID);
            FlightPoint destinationPoint = new FlightPoint(destAirport, pathID);

            addFlightPointToPath(sourcePoint);
            addFlightPointToPath(destinationPoint);
            updateFlightPath(newPath);
        } catch (DataException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    /**
     * Adds a flight point to a given path woth the given id
     * @param id
     * @param name
     * @param type
     * @param altitude
     * @param latitude
     * @param longitude
     */
    public void addFlightPointToPath(int id, String name, String type, String altitude, String latitude, String longitude, int index) throws DataException{
        double altitudeVal = 0.0;
        double latitudeVal = 0.0;
        double longitudeVal = 0.0;

        try{
            altitudeVal = Double.parseDouble(altitude);
        }catch (NumberFormatException e){
            throw new DataException("Altitude must be a double value");
        }
        try{
            latitudeVal = Double.parseDouble(latitude);
        }catch (NumberFormatException e){
            throw new DataException("Latitude must be a double value");
        }
        try{
            longitudeVal = Double.parseDouble(longitude);
        }catch (NumberFormatException e){
            throw new DataException("Longitude must be a double value");
        }
        if (index == -1){
            index = flightPathDictionary.get(id).getFlightPoints().size();
        }
        Connection c = null;
        Statement stmt;
        int pointID = 0;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");

            stmt = c.createStatement();
            String flightPointIDQuery = "SELECT * FROM `sqlite_sequence` WHERE `name` = \"" + this.name + "_Flight_Points\" LIMIT 1;";
            ResultSet pointIDRes = stmt.executeQuery(flightPointIDQuery);
            while (pointIDRes.next()) {
                pointID = Integer.parseInt(pointIDRes.getString("seq"));
            }
            stmt.close();

            stmt = c.createStatement();
            String insertFlightPointQuery = "INSERT INTO `" + this.name + "_Flight_Points` (`Index_ID`, `Name`, `Type`," +
                    " `Altitude`, `Latitude`, `Longitude`, `Order`) VALUES ";
            String flightType = type.replace("\"", "\"\"");
            String flightName = name.replace("\"", "\"\"");
            insertFlightPointQuery += "(" + id +", \""+ flightName +"\", \"" + flightType + "\",  "+ altitudeVal + ", " +
                    "" + latitudeVal + ", " + longitudeVal + ", "+index+")";
            stmt.execute(insertFlightPointQuery);
            stmt.close();
            //move all the points after this forward
            stmt = c.createStatement();
            String updatePointOrderQuery = "";
            FlightPath flightPath = flightPathDictionary.get(Integer.valueOf(id));
            for (int i = index + 1; i < flightPath.getFlightPoints().size(); i ++){
                updatePointOrderQuery = "UPDATE `"+this.name+"_Flight_Points` SET `Order` = "+i+" WHERE `Point_ID` = "+flightPath.getFlightPoints().get(i).getID()+";";
                stmt.execute(updatePointOrderQuery);
            }
            stmt.close();
            //if the index is the first or last we need to update the flight
            if (index == 0){
                try {
                    stmt = c.createStatement();
                    String query = "UPDATE `"+this.name+"_Flight_Path` SET `Source_Airport` = \""+flightName+"\" " +
                            "WHERE `Path_ID` = "+flightPath.getID();
                    stmt.execute(query);
                } catch ( Exception e ) {
                    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                }
                flightPath.setDepartureAirport(flightName);
            }else if (index == flightPath.getFlightPoints().size() - 1){
                try {
                    stmt = c.createStatement();
                    String query = "UPDATE `"+this.name+"_Flight_Path` SET `Destination_Airport` = \""+flightName+"\" " +
                            "WHERE `Path_ID` = "+flightPath.getID();
                    stmt.execute(query);
                } catch ( Exception e ) {
                    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                }
                flightPath.setArrivalAirport(flightName);
            }
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        FlightPoint pointToAdd = new FlightPoint(type, pointID+1, id, name, altitudeVal, latitudeVal, longitudeVal);
        flightPathDictionary.get(Integer.valueOf(id)).addFlightPoint(pointToAdd, index);
        flightPointDictionary.put(pointID + 1, pointToAdd);
        updateFlightPointInfo(flightPathDictionary.get(Integer.valueOf(id)));
    }

    /***
     * Adds a single flight Point to an Existing FLight Path.
     * @param point
     * @param index
     * @throws DataException
     */
    public void addFlightPointToPath(FlightPoint point, int index) throws DataException{
        addFlightPointToPath(point.getIndex(), point.getName(), point.getType(), String.valueOf(point.getAltitude()),
                String.valueOf( point.getLatitude()),String.valueOf(point.getLongitude()), index);
    }
    /***
     * Adds a single flight Point to an Existing FLight Path appended on the end of the list.
     * @param point
     * @throws DataException
     */
    public void addFlightPointToPath(FlightPoint point) throws DataException{
        addFlightPointToPath(point.getIndex(), point.getName(), point.getType(), String.valueOf(point.getAltitude()),
                String.valueOf( point.getLatitude()),String.valueOf(point.getLongitude()), -1);
    }

    /**
     * Adds a single flight Point to an Existing FLight Path appended on the end of the list.
     * @param id
     * @param name
     * @param type
     * @param altitude
     * @param latitude
     * @param longitude
     * @throws DataException
     */

    public void addFlightPointToPath(int id, String name, String type, String altitude, String latitude, String longitude) throws DataException{
        addFlightPointToPath(id, name, type, altitude, latitude, longitude, -1);
    }
    /**
     * This is called in conjunction to the App deleteDataset DO NOT CALL UNLESS THROUGH APP.DELETEDATASET
     */
    public void deleteDataset(){
        //drop the tables
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            String[] tablesToDrop = {"_Airline", "_Airport", "_City", "_Country", "_Routes", "_Flight_Path", "_Flight_Points"};
            for (int i = 0; i < tablesToDrop.length; i++){
                stmt = c.createStatement();
                String dropTableStatment = "DROP TABLE `"+this.name+tablesToDrop[i]+"`";
                stmt.execute(dropTableStatment);
                stmt.close();
            }
            stmt = c.createStatement();
            String deleteDatasetEntry = "DELETE FROM `Datasets` WHERE `Dataset_Name` = \""+this.name+"\"";
            stmt.execute(deleteDatasetEntry);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    /**
     * deletes an airline from the dataset.
     * @param airline
     */
    public void deleteAirline(Airline airline){
        //drop the entries
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            String deleteQuery = "DELETE FROM `" + this.name + "_Airline` WHERE `Airline_ID` = " + airline.getID() + ";";
            stmt = c.createStatement();
            stmt.execute(deleteQuery);
            stmt.close();
            stmt = c.createStatement();
            //check if number of countries that contain airlines > 0 else delete the country
            String countCountry = "SELECT COUNT(*) FROM `"+this.name+"_Airline` JOIN `"+this.name+"_Country` ON" +
                    " `"+this.name+"_Country`.`Country_Name` = `"+this.name+"_Airline`.`Country`" +
                    " WHERE `"+this.name+"_Airline`.`Country` = \""+airline.getCountryName().replace("\"", "\"\"")+"\"";
            ResultSet countCountryRes = stmt.executeQuery(countCountry);
            int countryCount = 0;
            while (countCountryRes.next()) {
                countryCount += countCountryRes.getInt("COUNT(*)");
            }
            countCountryRes.close();
            stmt.close();
            stmt = c.createStatement();
            //check if number of counties that contain airports > 0 else delete the country
            String countCountryA = "SELECT COUNT(*) FROM `"+this.name+"_Airport` JOIN `"+this.name+"_Country` ON" +
                    " `"+this.name+"_Country`.`Country_Name` = `"+this.name+"_Airport`.`Country`" +
                    " WHERE `"+this.name+"_Airport`.`Country` = \""+airline.getCountryName().replace("\"", "\"\"")+"\"";
            countCountryRes = stmt.executeQuery(countCountryA);
            while (countCountryRes.next()){
                countryCount +=  countCountryRes.getInt("COUNT(*)");
            }
            countCountryRes.close();
            stmt.close();
            //delete country if there are no matches
            if (countryCount == 0){
                stmt = c.createStatement();
                String deleteCountry = "DELETE FROM `"+this.name+"_Country` WHERE `Country_Name` = \""+airline.getCountryName()+"\"";
                stmt.execute(deleteCountry);
                stmt.close();
            }
            c.close();
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            //System.exit(0);
        }
        airlines.remove(airline);
        airlineDictionary.remove(airline.getName());
        createDataLinks();
    }

    /**
     * Deletes an AIrline from the dataset and database based on it index
     * @param index
     */
    public void deleteAirline(int index){
        deleteAirline(airlines.get(index));
    }

    /**
     * deletes an airport from the dataset.
     * @param airport
     */
    public void deleteAirport(Airport airport){
        //drop the entries
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            String deleteQuery = "DELETE FROM `"+this.name+"_Airport` WHERE `Airport_ID` = " + airport.getID() + ";";
            stmt = c.createStatement();
            stmt.execute(deleteQuery);
            stmt.close();
            //check if number of countries that contain airports and airlines > 0 else delete the country
            String countCountry = "SELECT COUNT(*) FROM `"+this.name+"_Airport` JOIN `"+this.name+"_Country` ON" +
                    " `"+this.name+"_Country`.`Country_Name` = `"+this.name+"_Airport`.`Country`" +
                    " WHERE `"+this.name+"_Airport`.`Country` = \""+airport.getCountry().getName().replace("\"", "\"\"")+"\"";
            ResultSet countCountryRes = stmt.executeQuery(countCountry);
            int countryCount = 0;
            while (countCountryRes.next()){
                countryCount =  countCountryRes.getInt("COUNT(*)");
            }
            countCountryRes.close();
            stmt.close();
            //check if number of countries that contain airlines > 0 else delete the country
            String countCountryA = "SELECT COUNT(*) FROM `"+this.name+"_Airline` JOIN `"+this.name+"_Country` ON" +
                    " `"+this.name+"_Country`.`Country_Name` = `"+this.name+"_Airline`.`Country`" +
                    " WHERE `"+this.name+"_Airline`.`Country` = \""+airport.getCountry().getName().replace("\"", "\"\"")+"\"";
            ResultSet countCountryResA = stmt.executeQuery(countCountry);
            while (countCountryResA.next()){
                countryCount +=  countCountryResA.getInt("COUNT(*)");
            }
            countCountryResA.close();
            stmt.close();
            //delete country if no matches
            if (countryCount == 0){
                stmt = c.createStatement();
                String deleteCountry = "DELETE FROM `"+this.name+"_Country` WHERE `Country_Name` = \""+airport.getCountry().getName()+"\"";
                stmt.execute(deleteCountry);
                stmt.close();
                countries.remove(countryDictionary.get(airport.getCountryName()));
                countryDictionary.remove(airport.getCountryName());
            }
            //cehck if number cities that contain airports > 0 else delete the city
            String countCity = "SELECT COUNT(*) FROM `"+this.name+"_Airport` JOIN `"+this.name+"_City` ON" +
                    " `"+this.name+"_City`.`City_Name` = `"+this.name+"_Airport`.`City`" +
                    " WHERE `"+this.name+"_Airport`.`City` = \""+airport.getCityName().replace("\"", "\"\"")+"\"";
            ResultSet countCityRes = stmt.executeQuery(countCity);
            int cityCount = 0;
            while (countCityRes.next()){
                cityCount = countCityRes.getInt("COUNT(*)");
            }
            countCountryRes.close();
            stmt.close();
            //delete country if no matches
            if (cityCount == 0){
                stmt = c.createStatement();
                String deleteCity = "DELETE FROM `"+this.name+"_City` WHERE `City_Name` = \""+airport.getCityName()+"\"";
                stmt.execute(deleteCity);
                stmt.close();
                cities.remove(cityDictionary.get(airport.getCityName()));
                cityDictionary.remove(airport.getCityName());
            }
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        airports.remove(airport);
        airportDictionary.remove(airport.getName());
        createDataLinks();
    }

    /**
     * Deletes an Airport from the dataset and database based on it index.
     * @param index
     */
    public void deleteAirport(int index){
        deleteAirport(airports.get(index));
    }
    /**
     * deletes an route from the dataset.
     * @param route
     */
    public void deleteRoute(Route route){
        //drop the entries
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            String deleteQuery = "DELETE FROM `"+this.name+"_Routes` WHERE `Route_ID` = " + route.getID() + ";";
            stmt = c.createStatement();
            stmt.execute(deleteQuery);
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        routes.remove(route);
        //routeAirline + routeSourceAirport + routeArrvAirport + routeCodeShare + routeStops + routeEquip
        String key = route.getAirlineName() + route.getDepartureAirport() + route.getArrivalAirport() + route.getCode() + route.getStops() + route.getEquipment();
        routeDictionary.remove(key);
        createDataLinks();
    }

    /**
     * Deletes a Route from the dataset and database based on its index
     * @param index
     */
    public void deleteRoute(int index){
        deleteRoute(routes.get(index));
    }
    /**
     * deletes an airline from the dataset.
     * @param flightPath
     */
    public void deleteFlightPath(FlightPath flightPath){
        //delete all flight points with the id
        while(flightPath.getFlightPoints().size() > 0){
            try {
                flightPointDictionary.remove(flightPath.getFlightPoints().get(0).getID());
                flightPath.getFlightPoints().remove(0);
            } catch (DataException e) {
                e.printStackTrace();
            }
        }
        //drop the entries
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            stmt = c.createStatement();
            String deletePointsQuery = "DELETE FROM `"+this.name+"_Flight_Points` WHERE `Index_ID` = "+flightPath.getID()+ ";";
            stmt.execute(deletePointsQuery);
            stmt.close();
            String deleteQuery = "DELETE FROM `"+this.name+"_Flight_Path` WHERE `Path_ID` = " + flightPath.getID() + ";";
            stmt = c.createStatement();
            stmt.execute(deleteQuery);
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        flightPaths.remove(flightPath);
        try {
            flightPathDictionary.remove(flightPath.getID());
        } catch (DataException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a flight path from the database based on its index.
     * @param index
     */
    public void deleteFlightPath(int index){
        deleteFlightPath(flightPaths.get(index));
    }

    /**
     * deletes an airline from the dataset.
     * @param flightPoint
     */
    public void deleteFlightPoint(FlightPoint flightPoint, FlightPath flightPath){
        if (flightPath.getFlightPoints().size() > 2){
            //drop the tables
            Connection c = null;
            Statement stmt = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
                String deleteQuery = "DELETE FROM `" + this.name + "_Flight_Points` WHERE `Point_ID` = " + flightPoint.getID() + ";";
                stmt = c.createStatement();
                stmt.execute(deleteQuery);

                stmt = c.createStatement();
                String updatePointOrderQuery = "";
                for (int i = 0; i < flightPath.getFlightPoints().size(); i++) {
                    updatePointOrderQuery = "UPDATE `" + this.name + "_Flight_Points` SET `Order` = " + i + " WHERE `Point_ID` = " + flightPath.getFlightPoints().get(i).getID() + ";";
                    stmt.execute(updatePointOrderQuery);
                }
                stmt.close();

                c.close();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            flightPath.getFlightPoints().remove(flightPoint);
            flightPointDictionary.remove(flightPoint);
            updateFlightPointInfo(flightPath);
            updateFlightPath(flightPath);
        }else{
            Alert cannotDelete = new Alert(Alert.AlertType.ERROR);
            cannotDelete.setTitle("Flight Path Error");
            cannotDelete.setHeaderText("Cannot Delete Flight Point.");
            cannotDelete.setContentText("You cannot have less than 2 Points in a Flight Path.");
            cannotDelete.showAndWait();
        }
    }

    /**
     * deletes a single flight point from a given path.
     * @param pathIndex
     * @param pointIndex
     */
    public void deleteFlightPoint(int pathIndex, int pointIndex){
        deleteFlightPoint(flightPathDictionary.get(pathIndex).getFlightPoints().get(pointIndex), flightPathDictionary.get(pathIndex));
    }

    /**
     * returns the airlines that are part of this dataset.
     * @return
     */
    public ArrayList<Airline> getAirlines() {
        return airlines;
    }

    /**
     * returns the airports that are associated with this dataset.
     * @return
     */
    public ArrayList<Airport> getAirports() {
        return airports;
    }

    /**
     * returns the routes that are associated with this dataset.
     * @return
     */
    public ArrayList<Route> getRoutes() {
        return routes;
    }

    /**
     * returns the flight paths that are associated with this dataset.
     * @return
     */
    public ArrayList<FlightPath> getFlightPaths() {
        return flightPaths;
    }

    /**
     * returns the countries that are associated with this dataset.
     * @return
     */
    public ArrayList<Country> getCountries() {
        return countries;
    }

    /**
     * returns the cities that are associate wit hthis dataset.
     * @return
     */
    public ArrayList<City> getCities() {
        return cities;
    }

    /**
     * returns a dictionary with the airlines that are associated with this datatset.
     * @return
     */
    public LinkedHashMap<String, Airline> getAirlineDictionary() {
        return airlineDictionary;
    }

    /**
     * returns a dictionary with the airports that are associated with this dataset.
     * @return
     */
    public LinkedHashMap<String, Airport> getAirportDictionary() {
        return airportDictionary;
    }

    /**
     * returns a route dictionary with the routes that are associated wit hthis dataset.
     * @return
     */
    public LinkedHashMap<String, Route> getRouteDictionary() {
        return routeDictionary;
    }

    /**
     * returns a flightpath dictionary with the flights that are associated with this dataset.
     * @return
     */
    public LinkedHashMap<Integer, FlightPath> getFlightPathDictionary() {
        return flightPathDictionary;
    }

    /**
     * returns a flightpoint dictionary with the flights that are associated with this dataset.
     *
     * @return
     */
    public LinkedHashMap<Integer, FlightPoint> getFlightPointDictionary() {
        return flightPointDictionary;
    }

    /**
     * returns a Country Dictionary with the COuntries that are associated with this dataset.
     * @return
     */
    public LinkedHashMap<String, Country> getCountryDictionary() {
        return countryDictionary;
    }

    /**
     * returns a City Dictionary with the Cities that are associated with this datatset.
     * @return
     */
    public LinkedHashMap<String, City> getCityDictionary() {
        return cityDictionary;
    }

    public LinkedHashMap<String, Equipment> getEquipmentDictionary() {
        return equipmentDictionary;
    }

    /**
     * Edits Airline and commits them to the database.
     * @param index
     * @param name
     * @param alias
     * @param IATA
     * @param ICAO
     * @param callsign
     * @param country
     * @param active
     * @throws DataException
     */
    public void editAirline(int index, String name, String alias, String IATA, String ICAO, String callsign, String country, String active ) throws DataException {
        editAirline(airlines.get(index), name, alias, IATA, ICAO, callsign, country, active);
    }
    /**
     * Edits Airline and commits them to the database.
     * @param airline
     * @param name
     * @param alias
     * @param IATA
     * @param ICAO
     * @param callsign
     * @param country
     * @param active
     * @throws DataException
     */
    public void editAirline(Airline airline, String name, String alias, String IATA, String ICAO, String callsign, String country, String active ) throws DataException {
        //check the data errors
        EntryParser parser = new EntryParser();
        airlineDictionary.remove(airline);
        parser.parseAirline(name, alias, IATA,ICAO, callsign, country, active);
        airline.setName(name);
        airline.setAlias(alias);
        airline.setIATA(IATA);
        airline.setICAO(ICAO);
        airline.setCallSign(callsign);
        airline.setCountryName(country);
        airline.setActive(active);
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            stmt = c.createStatement();
            String query = "UPDATE `"+this.name+"_Airline` SET `Name` = \""+airline.getName().replace("\"", "\"\"")+"\", `Alias` = \""+airline.getAlias().replace("\"", "\"\"")+"\", " +
                    "`IATA` = \""+airline.getIATA().replace("\"", "\"\"")+"\", `ICAO` = \""+airline.getICAO().replace("\"", "\"\"")+"\" , `Callsign` = \""+airline.getCallSign().replace("\"", "\"\"")+"\", " +
                    "`Country` = \""+airline.getCountryName().replace("\"", "\"\"")+"\", `Active` = \""+airline.getActive().replace("\"", "\"\"")+"\" WHERE `Airline_ID` = "+airline.getID();
            stmt.execute(query);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        airlineDictionary.put(airline.getName(), airline);
        createDataLinks();
    }

    /**
     * Edits the Airport in the dataset then commits it to the database.
     * @param index
     * @param name
     * @param city
     * @param country
     * @param IATA_FFA
     * @param ICAO
     * @param lat
     * @param lng
     * @param alt
     * @param timezone
     * @param DST
     * @param olson
     * @throws DataException
     */
    public void editAirport(int index, String name, String city, String country, String IATA_FFA, String ICAO, String lat, String lng, String alt, String timezone, String DST, String olson) throws DataException {
        editAirport(airports.get(index), name, city, country, IATA_FFA, ICAO, lat, lng, alt, timezone, DST, olson);
    }

    /**
     * Edits the Airport in the dataset then commits it to the database.
     * @param airport
     * @param name
     * @param city
     * @param country
     * @param IATA_FFA
     * @param ICAO
     * @param lat
     * @param lng
     * @param alt
     * @param timezone
     * @param DST
     * @param olson
     * @throws DataException
     */
    public void editAirport(Airport airport, String name, String city, String country, String IATA_FFA, String ICAO, String lat, String lng, String alt, String timezone, String DST, String olson) throws DataException {
        EntryParser parser = new EntryParser();
        airportDictionary.remove(airport.getName());
        Airport newAirport = parser.parseAirport(name, city, country, IATA_FFA, ICAO, lat, lng, alt, timezone, DST, olson);
        airport.setName(name);
        airport.setCityName(city);
        //airport.getCity().setName(city);
        airport.setCountryName(country);
        //airport.getCountry().setName(country);
        airport.setIATA_FFA(IATA_FFA);
        airport.setICAO(ICAO);
        airport.setLatitude(newAirport.getLatitude());
        airport.setLongitude(newAirport.getLongitude());
        airport.setAltitude(newAirport.getAltitude());
        airport.getCity().setTimezone(Double.parseDouble(timezone));
        airport.getCountry().setDST(DST);
        airport.getCity().setTimeOlson(olson);

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            /*
            //UPDATE CITY AND COUNTRIES
             */
            if (cityDictionary.containsKey(city) && cityDictionary.get(city) != null){
                airport.setCity(cityDictionary.get(city));
                airport.getCity().setCountry(country);
                airport.getCity().setTimezone(Double.parseDouble(timezone));
                airport.getCity().setTimeOlson(olson);
                //update city in database
                stmt = c.createStatement();
                String updateCityQuery = "UPDATE `"+this.name+"_City` SET `Country_Name` = \""+country+"\", " +
                        "`Timezone` = "+timezone+", `Olson_Timezone` = \""+olson+"\" WHERE `City_Name` = \""+city+"\"";
                stmt.execute(updateCityQuery);
                stmt.close();
            }else {
                City newCity = new City(city, country, Double.parseDouble(timezone), olson);
                airport.setCity(newCity);
                airport.setCityName(city);
                cities.add(newCity);
                cityDictionary.put(city, newCity);
                //add new City to database
                stmt = c.createStatement();
                String addNewCity = "INSERT INTO `"+this.name+"_City` (`City_Name`, `Country_name`, `Timezone`, `Olson_Timezone`) VALUES " +
                        "(\""+city+"\", \""+country+"\", "+timezone+", \""+olson+"\")";
                stmt.execute(addNewCity);
                stmt.close();
            }

            if (countryDictionary.containsKey(country) && countryDictionary.get(country) != null){
                airport.setCountry(countryDictionary.get(country));
                airport.getCountry().setDST(DST);
                //update country in database
                stmt = c.createStatement();
                String updateCountryQuery = "UPDATE `"+this.name+"_Country` SET `DST` = \""+DST+"\" WHERE `Country_Name` = \""+country+"\"";
                stmt.execute(updateCountryQuery);
                stmt.close();
            }else{
                Country newCountry = new Country(DST, name);
                airport.setCountry(newCountry);
                airport.setCountryName(country);
                countries.add(newCountry);
                countryDictionary.put(country, newCountry);
                //add new COuntry to database
                stmt = c.createStatement();
                String createCountryQuery = "INSERT INTO `"+this.name+"_Country` (`Country_Name`, `DST`) VALUES (\""+country+"\", \""+DST+"\")";
                stmt.execute(createCountryQuery);
                stmt.close();
            }

            stmt = c.createStatement();
            String query = "UPDATE `"+this.name+"_Airport` SET `Name` = \""+airport.getName().replace("\"", "\"\"")+"\", `City` = \""+airport.getCityName().replace("\"", "\"\"")+"\", " +
                    "`Country` = \""+airport.getCountryName().replace("\"", "\"\"")+"\", `IATA/FFA` = \""+airport.getIATA_FFA().replace("\"", "\"\"")+"\", " +
                    "`ICAO` = \""+airport.getICAO().replace("\"", "\"\"")+"\", `Latitude` = "+airport.getLatitude() + ", `Longitude` = "+airport.getLongitude()+", " +
                    "`Altitude` = "+airport.getAltitude() + " WHERE `Airport_ID` = "+airport.getID();
            stmt.execute(query);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        airportDictionary.put(airport.getName(), airport);
        createDataLinks();
    }

    /**
     * Edits the ROutes in the dataset and commits it to the database.
     * @param index
     * @param airline
     * @param source
     * @param dest
     * @param code
     * @param stops
     * @param equip
     * @throws DataException
     */
    public void editRoute(int index, String airline, String source, String dest, String code, String stops, String equip) throws DataException {
        editRoute(routes.get(index), airline, source, dest, code, stops, equip);
    }

    /**
     * Edits the ROutes in the dataset and then commits it to the database.
     * @param route
     * @param airline
     * @param source
     * @param dest
     * @param code
     * @param stops
     * @param equip
     * @throws DataException
     */
    public void editRoute(Route route, String airline, String source, String dest, String code, String stops, String equip) throws DataException {
        EntryParser entryParser = new EntryParser();
        //routeAirline + routeSourceAirport + routeArrvAirport + routeCodeShare + routeStops + routeEquip
        routeDictionary.remove(route.getAirlineName()+route.getDepartureAirport()+route.getArrivalAirport()+route.getCode()+route.getStops() + route.getEquipment());
        Route newRoute = entryParser.parseRoute(airline, source, dest, code, stops, equip);
        route.setAirlineName(newRoute.getAirlineName());
        route.setDepartureAirport(newRoute.getDepartureAirport());
        route.setArrivalAirport(newRoute.getArrivalAirport());
        route.setCode(newRoute.getCode());
        route.setEquipment(equip);

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            stmt = c.createStatement();
            String query = "UPDATE `" + this.name + "_Routes` SET `Airline` = \"" + route.getAirlineName().replace("\"", "\"\"") + "\", " +
                    "`Source_Airport` = \"" + route.getDepartureAirport().replace("\"", "\"\"") + "\", `Destination_Airport` = \"" + route.getArrivalAirport().replace("\"", "\"\"") + "\", " +
                    "`Codeshare` = \"" + route.getCode().replace("\"", "\"\"") + "\", `Stops` = " + route.getStops() + ", `Equipment` = \"" + route.getEquipment().replace("\"", "\"\"") + "\" " +
                    "WHERE `Route_ID` = " + route.getID();
            stmt.execute(query);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        //routeAirline + routeSourceAirport + routeArrvAirport + routeCodeShare + routeStops + routeEquip
        routeDictionary.put(route.getAirlineName()+route.getDepartureAirport()+route.getArrivalAirport()+route.getCode()+route.getStops() + route.getEquipment(), route);
        createDataLinks();
    }

    /**
     * Edits a flight Point in the dataset then commits it to the database.
     * @param flightPath
     * @param index
     * @param name
     * @param type
     * @param altitude
     * @param latitude
     * @param longitude
     * @throws DataException
     */
    public void editFlight(FlightPath flightPath, int index, String name, String type, String altitude, String latitude, String longitude) throws DataException{
        editFlight(flightPath.getFlightPoints().get(index), name, type, altitude, latitude, longitude);
    }

    /**
     * Edits a flight Point in the dataset then commits it to the database.
     * @param flightPoint
     * @param name
     * @param type
     * @param altitude
     * @param latitude
     * @param longitude
     * @throws DataException
     */
    public void editFlight(FlightPoint flightPoint, String name, String type, String altitude, String latitude, String longitude) throws DataException {
        EntryParser entryParser = new EntryParser();
        FlightPoint parsedFlightPoint = entryParser.parsePoint(name, type, altitude, latitude, longitude);
        flightPoint.setName(parsedFlightPoint.getName());
        flightPoint.setType(parsedFlightPoint.getType());
        flightPoint.setAltitude(parsedFlightPoint.getAltitude());
        flightPoint.setLatitude(parsedFlightPoint.getLatitude());
        flightPoint.setLongitude(parsedFlightPoint.getLongitude());


        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            stmt = c.createStatement();
            String query = "UPDATE `"+this.name+"_Flight_Points` SET `Name` = \""+flightPoint.getName().replace("\"", "\"\"")+"\", " +
                    "`Type` = \""+flightPoint.getType().replace("\"", "\"\"")+"\", `Altitude` = "+flightPoint.getAltitude()+", " +
                    "`Latitude` = "+flightPoint.getLatitude()+", `Longitude` = "+flightPoint.getLongitude()+" WHERE " +
                    "`POINT_ID` = "+flightPoint.getID();
            stmt.execute(query);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        updateFlightPath(flightPathDictionary.get(flightPoint.getIndex()));
        createDataLinks();
    }

    /**
     * Updates the flight path to the first Point and the last point
     * @param flightPath
     */
    private void updateFlightPath(FlightPath flightPath){
        Connection c = null;
        Statement stmt = null;
        FlightPoint startPoint = flightPath.getFlightPoints().get(0);
        FlightPoint endPoint = flightPath.getFlightPoints().get(flightPath.getFlightPoints().size() - 1);
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            stmt = c.createStatement();
            String query= "UPDATE `"+this.name+"_Flight_Path` SET `Source_Airport` = \""+startPoint.getName().replace("\"", "\"\"")+"\", " +
                    "`Destination_Airport` = \""+endPoint.getName().replace("\"", "\"\"") + "\" " +
                    "WHERE `Path_ID` = "+startPoint.getIndex();
            stmt.execute(query);
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        flightPath.setArrivalAirport(endPoint.getName());
        flightPath.setDepartureAirport(startPoint.getName());
    }

    /**
     * moves a flight point to another place in the Flight.
     * @param flightPoint
     * @param index
     * @throws DataException
     */
    public void moveFlightPoint(FlightPoint flightPoint, int index) throws DataException {
        //remove and add it to the arraylist first
        System.out.println(flightPoint.getIndex());
        FlightPath flightPath = flightPathDictionary.get(flightPoint.getIndex());
        int curIndex = flightPath.getFlightPoints().indexOf(flightPoint);
        flightPath.getFlightPoints().remove(flightPoint);
        int indexToAdd = index;
        flightPath.getFlightPoints().add(indexToAdd, flightPoint);

        Connection c = null;
        Statement stmt;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            //move all the points after this forward
            stmt = c.createStatement();
            String updatePointOrderQuery = "";
            for (int i = index; i < flightPath.getFlightPoints().size(); i ++){
                updatePointOrderQuery = "UPDATE `"+this.name+"_Flight_Points` SET `Order` = "+i+" WHERE `Point_ID` = "+flightPath.getFlightPoints().get(i).getID()+";";
                stmt.execute(updatePointOrderQuery);
            }
            stmt.close();
            updateFlightPath(flightPath);
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        updateFlightPointInfo(flightPath);
    }

    /**
     * Updates the Leg Distance, Total Distance and Bearing(Heading) of the Flight points in the flight path.
     * @param flightPath
     */
    public void updateFlightPointInfo(FlightPath flightPath){
        flightPath.updateFlightPointInfo();
        Connection c = null;
        Statement stmt;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            //move all the points after this forward
            for (FlightPoint flightPoint: flightPath.getFlightPoints()){
                stmt = c.createStatement();
                String updatePointQuery = "UPDATE `"+this.name+"_Flight_Points` SET `Heading` = "+flightPoint.getHeading()+", " +
                        "`Tot_Dist` = "+flightPoint.getTotalDistance()+", `Leg_Dist` = "+flightPoint.getLegDistance()+" WHERE `POINT_ID` = " +
                        "" + flightPoint.getID();
                stmt.execute(updatePointQuery);
                stmt.close();
            }
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Name of the dataset in the database
     */
    @Override
    public String toString(){
        return this.name;
    }
}
