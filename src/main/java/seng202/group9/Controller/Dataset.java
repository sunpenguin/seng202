package seng202.group9.Controller;


import seng202.group9.Core.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Dataset {

    String name;
    static boolean getExisting = true;
    static boolean createNew = false;
    ArrayList<Airline> airlines;
    ArrayList<Airport> airports;
    ArrayList<Route> routes;
    ArrayList<FlightPath> flightPaths;
    ArrayList<Country> countries;
    ArrayList<City> cities;

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
                airlines.add(new Airline(airID, airName, airIATA, airICAO, airAlias, airCallsign, airCountry, airActive));
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
                airports.add(new Airport(airpID, airpName, airpCity, airpCountry, airpIATA_FFA, airpICAO, airpLatitude, airpLongitude, airpAltitude));
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
                cities.add(new City(cityName, cityCountry, cityTz, cityTimeOlson));
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
                countries.add(new Country(countDST, countName));
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
                String routeDestAirport = rs.getString("Source_Airport");
                String routeArrvAirport = rs.getString("Destination_Airport");
                String routeCodeShare = rs.getString("Codeshare");
                int routeStops = rs.getInt("Stops");
                String routeEquip = rs.getString("Equipment");
                routes.add(new Route(routeID, routeAirline, routeDestAirport, routeArrvAirport, routeCodeShare, routeStops, routeEquip));
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

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

}
