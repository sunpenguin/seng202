package seng202.group9.Controller;


import seng202.group9.Core.*;

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
    static boolean createNew = false;//constructor variables for action
    ArrayList<Airline> airlines;
    ArrayList<Airport> airports;
    ArrayList<Route> routes;
    ArrayList<FlightPath> flightPaths;
    ArrayList<Country> countries;
    ArrayList<City> cities;
    LinkedHashMap<String, Airline> airlineDictionary;
    LinkedHashMap<String, Airport> airportDictionary;
    LinkedHashMap<String, Route> routeDictionary;
    LinkedHashMap<String, FlightPath> flightPathDictionary;
    LinkedHashMap<String, Country> countryDictionary;
    LinkedHashMap<String, City> cityDictionary;

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
                Airline airlineToAdd = new Airline(airID, airName, airIATA, airICAO, airAlias, airCallsign, airCountry, airActive);
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
                flightPaths.add(new FlightPath(flightpID, flightpDepart, flightpArrive));
            }
            rs.close();
            stmt.close();
            /*///////////////////////
            //get all flight points//
            ///////////////////////*/
            for (int i = 0; i < flightPaths.size(); i++){
                stmt = c.createStatement();
                String queryFlightPoints = "SELECT * FROM `" + this.name + "_Flight_Points` WHERE `Index_ID` = "+flightPaths.get(i).getID() + " ORDER BY `Order` ASC";
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
                    flightPaths.get(i).addFlightPoint(new FlightPoint(flightPtName, flightPtID, flightPtInd
                    , flightPtType, flightPtVia, flightPtheading, flightPtAltitude, flightPtLegDistance, flightPtTotDist,
                            flightPtLatitude, flightPtLongitude));
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
                    "`Longitude` REAL, " +
                    "`Latitude` REAL, " +
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
        int nextID = -1;
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
        int nextID = -1;
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
            System.out.println(nextID);
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
        int nextID = -1;
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
        int numOfDuplicates = 0;
        int nextID = -1;
        //query database.
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
            stmt = c.createStatement();
            String queryName = this.name.replace("'", "''");
            String IDQuery = "SELECT * FROM `sqlite_sequence` WHERE `name` = '"+queryName+"_Flight_Points' LIMIT 1;";
            ResultSet IDResult = stmt.executeQuery(IDQuery);
            while(IDResult.next()){
                nextID = Integer.parseInt(IDResult.getString("seq")) + 1;//for some reason sqlite3 stores incremental values as a string...
            }
            stmt.close();
            stmt = c.createStatement();
            //ADDED
            String firstPt = flightPointsToImport.get(0).getName();
            String lastPt = flightPointsToImport.get(flightPointsToImport.size() - 1).getName();
            FlightPath flightPathToAdd = new FlightPath(firstPt, lastPt);

            String insertFlightPathQuery = "INSERT INTO `" + this.name + "_Flight_Path` (`Source_Airport`, `Destination_Airport`)" +
                    "VALUES ( \"" + firstPt + "\",\"" + lastPt + "\") ";
            stmt.execute(insertFlightPathQuery);
            stmt.close();
            stmt = c.createStatement();
            int flightPathId = 0;
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
            //ADDED
            String insertFlightPointQuery = "INSERT INTO `" + this.name + "_Flight_Points` (`Index_ID`, `Name`, `Type`," +
                    " `Altitude`, `Longitude`, `Latitude`) VALUES ";
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
                if (numOfFlights > 0){
                    insertFlightPointQuery += ",";
                }
                insertFlightPointQuery += "(" + flightPathId +", \""+ flightName +"\", \"" + flightType + "\",  "+ flightAltitude + ", " +
                        "" + flightLatitude + ", " + flightLongitude + ")";
                flightPointsToImport.get(i).setID(nextID);
                //add data to dataset array.
                //this is placed after incase the database messes up
                flightPathToAdd.addFlightPoint(flightPointsToImport.get(i));
                //routeDictionary.put(routeIdentifier, flightsToImport.get(i));
                nextID++;
                numOfFlights++;
                //}
            }
            if (numOfFlights > 0){
                stmt.execute(insertFlightPointQuery);
                stmt.close();
            }
            flightPaths.add(flightPathToAdd);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        message += "\nDuplicates ommitted: "+numOfDuplicates;
        createDataLinks();
        return message;
    }




    /**
     * This function updates the connections between airports citys countries etc.
     */

    public void createDataLinks(){
        //this may be seperated into more sepearate function in the future for time optimisation
        //create Airline country link
        for (Airline airline: airlines){
            airline.setCountry(countryDictionary.get(airline.getCountryName()));
        }
        //create Airport City and Country Link
        HashMap<String, Airport> airportsByIATA = new HashMap<String, Airport>(); //this is used later for connecting the routes
        HashMap<String, Airport> airportsByICAO = new HashMap<String, Airport>(); //this is used later for connecting the routes
        for (Airport airport: airports){
            airportsByIATA.put(airport.getIATA_FFA(), airport);
            airportsByICAO.put(airport.getICAO(), airport);
            airport.setCountry(countryDictionary.get(airport.getCountryName()));
            //TODO Add City in country (This is extra work).
            airport.setCity(cityDictionary.get(airport.getCityName()));
            airport.getCity().addAirport(airport);
        }
        //set Airport variables for route
        for (Route route: routes){
            if (route.getDepartureAirport().length() > 3){
                route.setSourceAirport(airportsByICAO.get(route.getDepartureAirport()));
            }else{
                route.setSourceAirport(airportsByIATA.get(route.getDepartureAirport()));
            }
            if (route.getArrivalAirport().length() > 3){
                route.setDestinationAirport(airportsByICAO.get(route.getArrivalAirport()));
            }else{
                route.setDestinationAirport(airportsByIATA.get(route.getArrivalAirport()));
            }
            route.setAirline(airlineDictionary.get(route.getAirlineName()));
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
        addAirline(airlineToAdd);
    }

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
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public void addAirport(String name, String city, String country, String IATA_FFA, String ICAO, String latitude, String longitude,
                           String altitude, String timezone, String DST, String olsonTz) throws DataException{
        try{
            double latitudeVal = Double.parseDouble(latitude);
            double longitudeVal = Double.parseDouble(longitude);
            double altitudeVal = Double.parseDouble(altitude);
            double timezoneVal = Double.parseDouble(timezone);
            Airport airportToAdd = new Airport(name, city, country, IATA_FFA, ICAO, latitudeVal, longitudeVal, altitudeVal);
            City cityToAdd = new City(city, country, timezoneVal, olsonTz);
            Country countryToAdd = new Country(DST, country);
            addAirport(airportToAdd);
            addCity(cityToAdd);
            addCountry(countryToAdd);
        }catch (NumberFormatException e){
            throw new DataException("Latitude, Longitude, Altitude and Timezone must be numbers");
        }
    }

    public void addAirport(Airport airportToAdd) throws DataException{
        if (airportToAdd.getIATA_FFA() != "" && airportToAdd.getIATA_FFA().length() != 3){
            throw new DataException("IATA/FFA either empty or 3 letters");
        }
        if (airportToAdd.getICAO() != "" && airportToAdd.getICAO().length() != 4){
            throw new DataException("ICAO either empty or 4 letters");
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
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public void addCity(City city){
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
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
        }
    }

    public void addCountry(Country country){
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
            String routeKey = routeToAdd.getAirline() + routeToAdd.getDepartureAirport() + routeToAdd.getArrivalAirport() + routeToAdd.getCode() + routeToAdd.getStops() + routeToAdd.getEquipment();
            routeDictionary.put(routeKey, routeToAdd);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
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
                stmt.execute(tablesToDrop[i]);
                stmt.close();
            }
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public ArrayList<Airline> getAirlines() {
        return airlines;
    }

    public ArrayList<Airport> getAirports() {
        return airports;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public ArrayList<FlightPath> getFlightPaths() {
        return flightPaths;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public ArrayList<City> getCities() {
        return cities;
    }
}
