package seng202.group9;/**
 * Created by Gondr on 19/09/2016.
 */

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;
import seng202.group9.Controller.App;
import seng202.group9.Controller.DataException;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.*;

public class DatasetTest {
    static App app = new App();

    @Test
    public void DatasetConstructor(){
        try {
            app.createDataset("FORUNITTESTINGONLY");
        } catch (DataException e) {
            fail("There seems to be database already by this name");
        }

        Dataset dataset = app.getCurrentDataset();

        assertEquals(dataset.getName(), "FORUNITTESTINGONLY");

        try {
            dataset.importAirline("res/Reduced Samples/Airlines.txt");
        } catch (DataException e) {
            fail("The sample file is missing");
        }
        assertTrue(dataset.getAirlines().size() == dataset.getAirlineDictionary().size());
        try {
            assertTrue(dataset.getAirlines().get(0).getID() == 1);
        } catch (DataException e) {
            fail("The first index of Airlines should have an id of 1 as there has been no tampering with the data yet");
        }

        try {
            dataset.importAirport("res/Reduced Samples/Airports.txt");
        } catch (DataException e) {
            fail("The sample file is missing");
        }
        assertTrue(dataset.getAirports().size() == dataset.getAirportDictionary().size());
        assertTrue(dataset.getCities().size() == dataset.getCityDictionary().size());
        assertTrue(dataset.getCountries().size() == dataset.getCountryDictionary().size());
        try {
            assertTrue(dataset.getAirports().get(0).getID() == 1);
        } catch (DataException e) {
            fail("The first index of Airports should have an id of 1 as there has been no tampering with the data yet");
        }

        try {
            dataset.importRoute("res/Reduced Samples/Routes.txt");
        } catch (DataException e) {
            fail("The sample file is missing");
        }
        assertTrue(dataset.getRoutes().size() == dataset.getRouteDictionary().size());
        try {
            assertTrue(dataset.getRoutes().get(0).getID() == 1);
        } catch (DataException e) {
            fail("The first index of Routes should have an id of 1 as there has been no tampering with the data yet");
        }

        try {
            dataset.importFlight("res/Reduced Samples/NZCH-WSSS.csv");
        } catch (DataException e) {
            fail("The sample file is missing");
        }
        assertTrue(dataset.getFlightPaths().size() == dataset.getFlightPathDictionary().size());
        try {
            assertTrue(dataset.getFlightPaths().get(0).getID() == 1);
        } catch (DataException e) {
            fail("The first index of Flight Paths should have an id of 1 as there has been no tampering with the data yet");
        }

        dataset.createDataLinks();

        assertTrue(dataset.getAirlines().size() == dataset.getAirlineDictionary().size());
        assertTrue(dataset.getAirports().size() == dataset.getAirportDictionary().size());
        assertTrue(dataset.getCities().size() == dataset.getCityDictionary().size());
        assertTrue(dataset.getCountries().size() == dataset.getCountryDictionary().size());
        assertTrue(dataset.getRoutes().size() == dataset.getRouteDictionary().size());
        assertTrue(dataset.getFlightPaths().size() == dataset.getFlightPathDictionary().size());
        //make sure that datalinks does not increase the size of the dictionaries etc

        app.deleteDataset(app.getCurrentDataset());
    }

    @Test
    public void checkoutDataLinks() throws DataException {
        try {
            app.createDataset("FORUNITTESTINGONLY");
        } catch (DataException e) {
            fail("There seems to be database already by this name");
        }

        Dataset dataset = app.getCurrentDataset();

        Airline allNipponAirways = new Airline(324, "All Nippon Airways", "ANA All Nippon Airways",
                "NH", "ANA", "ALL NIPPON", "Japan", "Y");
        dataset.addAirline(allNipponAirways);
        assertEquals(dataset.getAirlines().get(0), allNipponAirways);
        dataset.addAirline("British Airways","\\N","BA","BAW","SPEEDBIRD","United Kingdom","Y");

        assertEquals(dataset.getAirlines().get(1).getName(), "British Airways");
        assertTrue(dataset.getAirlines().get(0).getID() != -1);
        assertTrue(dataset.getAirlines().get(1).getID() != -1);

        dataset.addAirport("Heathrow","London","United Kingdom","LHR","EGLL","51.4775","-0.461389","83","0","E","Europe/London");
        dataset.addAirport("Changi Intl","Singapore","Singapore","SIN","WSSS","1.350189","103.994433","22","8","N","Asia/Singapore");
        dataset.addAirport("Matsumoto","Matsumoto","Japan","MMJ","RJAF","36.166758","137.922669","2182","9","U","Asia/Tokyo");
        assertTrue(dataset.getAirports().size() == 3);
        assertTrue(dataset.getCities().size() == 3);
        assertTrue(dataset.getCountries().size() == 3);

        Route route = new Route("BA", "SIN", "LHR", "", 0, "744 777");
        dataset.addRoute(route);
        dataset.addRoute("NH", "SIN", "LHR", "", "0","777");
        //checked if everything is linked together properly
        assertTrue(dataset.getAirlines().get(0).getCountry() != null);
        assertTrue(dataset.getAirlines().get(1).getCountry() != null);

        assertTrue(dataset.getAirlines().get(0).getRoutes().size() == 1);
        assertTrue(dataset.getAirlines().get(1).getRoutes().size() == 1);

        assertTrue(dataset.getCountryDictionary().get("United Kingdom").getAirlines().size() == 1);
        assertTrue(dataset.getCountryDictionary().get("Japan").getAirlines().size() == 1);

        assertEquals(dataset.getRoutes().get(0).getSourceAirport(), dataset.getAirportDictionary().get("Changi Intl"));
        assertEquals(dataset.getRoutes().get(0).getDestinationAirport(), dataset.getAirportDictionary().get("Heathrow"));
        assertEquals(dataset.getRoutes().get(0).getAirline(), dataset.getAirlineDictionary().get("British Airways"));

        assertEquals(dataset.getRoutes().get(1).getSourceAirport(), dataset.getAirportDictionary().get("Changi Intl"));
        assertEquals(dataset.getRoutes().get(1).getDestinationAirport(), dataset.getAirportDictionary().get("Heathrow"));
        assertEquals(dataset.getRoutes().get(1).getAirline(), dataset.getAirlineDictionary().get("All Nippon Airways"));

        //slowly delete everything and check if the links are retained/gone.
        dataset.deleteRoute(0);
        assertTrue(dataset.getRoutes().size() == 1);
        assertTrue(dataset.getRoutes().size() == dataset.getRouteDictionary().size());

        assertTrue(dataset.getAirlines().get(0).getRoutes().size() == 1);
        assertTrue(dataset.getAirlines().get(1).getRoutes().size() == 0);
        //add the route back
        dataset.addRoute(route);

        dataset.deleteAirport(0);

        assertTrue(dataset.getCityDictionary().get("London") == null);
        assertTrue(dataset.getCountryDictionary().get("United Kingdom") == null);
        assertTrue(dataset.getRoutes().get(0).getDestinationAirport() == null);
        assertTrue(dataset.getRoutes().get(1).getDestinationAirport() == null);

        dataset.addAirport("Heathrow","London","United Kingdom","LHR","EGLL","51.4775","-0.461389","83","0","E","Europe/London");

        //check that all changes are now back;
        assertTrue(dataset.getCityDictionary().get("London") != null);
        assertTrue(dataset.getCountryDictionary().get("United Kingdom") != null);
        assertTrue(dataset.getRoutes().get(0).getDestinationAirport() != null);
        assertTrue(dataset.getRoutes().get(1).getDestinationAirport() != null);

        dataset.deleteAirline(dataset.getAirlines().get(0));

        assertTrue(dataset.getRoutes().get(0).getAirline() == null);
        assertTrue(dataset.getCountryDictionary().get("Japan").getAirlines().size() == 0);

        //clean database and check whether dictionarys and arrays are empty
        dataset.deleteAirline(0);
        dataset.deleteAirport(0);
        dataset.deleteAirport(0);
        dataset.deleteAirport(0);
        dataset.deleteRoute(0);
        dataset.deleteRoute(0);

        assertTrue(dataset.getAirlines().size() == 0);
        assertTrue(dataset.getAirports().size() == 0);
        assertTrue(dataset.getAirlineDictionary().size() == 0);
        assertTrue(dataset.getAirportDictionary().size() == 0);
        assertTrue(dataset.getRoutes().size() == 0);
        assertTrue(dataset.getRouteDictionary().size() == 0);
        assertTrue(dataset.getCities().size() == 0);
        assertTrue(dataset.getCityDictionary().size() == 0);
        assertTrue(dataset.getCountries().size() == 0);
        assertTrue(dataset.getCountryDictionary().size() == 0);

        app.deleteDataset(app.getCurrentDataset());
    }

    @Test
    public void flightPathTester() throws DataException {
        try {
            app.createDataset("FORUNITTESTINGONLY");
        } catch (DataException e) {
            fail("There seems to be database already by this name");
        }

        Dataset dataset = app.getCurrentDataset();

        try {
            dataset.importFlight("res/Reduced Samples/NZCH-WSSS.csv");
        } catch (DataException e) {
            fail("The sample file is missing");
        }

        assertTrue(dataset.getFlightPaths().get(0).getFlightPoints().get(0).getID() == 1);

        FlightPoint flightPoint = dataset.getFlightPaths().get(0).getFlightPoints().get(6);
        FlightPoint flightPoint1 = dataset.getFlightPaths().get(0).getFlightPoints().get(5);
        dataset.deleteFlightPoint(1, 5);
        assertEquals(dataset.getFlightPaths().get(0).getFlightPoints().get(5), flightPoint);
        int currentSize = dataset.getFlightPaths().get(0).getFlightPoints().size();
        dataset.deleteFlightPoint(flightPoint, dataset.getFlightPaths().get(0));
        assertTrue(dataset.getFlightPaths().get(0).getFlightPoints().size() == currentSize - 1);

        dataset.addFlightPointToPath(flightPoint1, 5);
        dataset.addFlightPointToPath(flightPoint, 6);
        assertEquals(dataset.getFlightPaths().get(0).getFlightPoints().get(5).getName(), flightPoint1.getName());
        assertEquals(dataset.getFlightPaths().get(0).getFlightPoints().get(6).getName(), flightPoint.getName());

        //edit order
        FlightPoint wasLast = dataset.getFlightPaths().get(0).getFlightPoints().get(dataset.getFlightPaths().get(0).getFlightPoints().size() - 1);
        FlightPoint wasSecondToLast = dataset.getFlightPaths().get(0).getFlightPoints().get(dataset.getFlightPaths().get(0).getFlightPoints().size() - 2);
        FlightPoint wasFirst = dataset.getFlightPaths().get(0).getFlightPoints().get(0);
        dataset.moveFlightPoint(wasLast, 0);
        assertTrue(dataset.getFlightPaths().get(0).getFlightPoints().indexOf(wasLast) == 0);
        assertTrue(dataset.getFlightPaths().get(0).getFlightPoints().indexOf(wasSecondToLast) == dataset.getFlightPaths().get(0).getFlightPoints().size() - 1);
        assertTrue(dataset.getFlightPaths().get(0).getFlightPoints().indexOf(wasFirst) == 1);

        app.deleteDataset(app.getCurrentDataset());
    }

    @AfterClass
    public static void deleteDataset(){
        while(app.getCurrentDataset() != null) {
            app.deleteDataset(app.getCurrentDataset());
        }
    }
}