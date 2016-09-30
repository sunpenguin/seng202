package seng202.group9;/**
 * Created by Gondr on 19/09/2016.
 */

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;
import seng202.group9.Controller.*;

public class FilterUnitTest {
    static App app = new App();

    @Test
    public void airlineFilterTest(){
        try {
            app.createDataset("FORUNITTESTINGONLY");
        } catch (DataException e) {
            fail("There seems to be database already by this name");
        }

        Dataset dataset = app.getCurrentDataset();

        try {
            dataset.importAirline("res/Reduced Samples/Airlines.txt");
        } catch (DataException e) {
            fail("The sample file is missing");
        }

        AirlineFilter airlineFilter = new AirlineFilter(dataset.getAirlines());

        int size = airlineFilter.getFilteredData().size();

        airlineFilter.filterCountry("New Zealand");
        assertTrue(airlineFilter.getFilteredData().size() == 25);
        airlineFilter.reset();

        airlineFilter.filterActive("Y");

        assertTrue(size != airlineFilter.getFilteredData().size());

        airlineFilter.reset();
        airlineFilter.filterAlias("A");

        assertTrue(size != airlineFilter.getFilteredData().size());

        airlineFilter.reset();
        airlineFilter.filterCallsign("Y");

        assertTrue(size != airlineFilter.getFilteredData().size());

        airlineFilter.reset();
        airlineFilter.filterCountry("E");

        assertTrue(size != airlineFilter.getFilteredData().size());

        airlineFilter.reset();
        airlineFilter.filterIATA("I");

        assertTrue(size != airlineFilter.getFilteredData().size());

        airlineFilter.reset();
        airlineFilter.filterICAO("O");

        assertTrue(size != airlineFilter.getFilteredData().size());

        airlineFilter.reset();
        airlineFilter.filterName("T");

        assertTrue(size != airlineFilter.getFilteredData().size());

        airlineFilter.printFilter();

        airlineFilter.setBaseList(airlineFilter.getFilteredData());

        app.deleteDataset(app.getCurrentDataset());
    }

    @Test
    public void airportFilterTest(){
        try {
            app.createDataset("FORUNITTESTINGONLY");
        } catch (DataException e) {
            fail("There seems to be database already by this name");
        }

        Dataset dataset = app.getCurrentDataset();

        try {
            dataset.importAirport("res/Reduced Samples/Airports.txt");
        } catch (DataException e) {
            fail("The sample file is missing");
        }

        AirportFilter filter = new AirportFilter(dataset.getAirports());

        int size = dataset.getAirports().size();

        filter.filterName("d");
        assertTrue(size != filter.getFilteredData().size());

        filter.reset();

        filter.filterCity("P");
        assertTrue(size != filter.getFilteredData().size());

        filter.reset();

        filter.filterCountry("Q");
        assertTrue(size != filter.getFilteredData().size());

        filter.reset();

        filter.filterIATA_FFA("U");
        assertTrue(size != filter.getFilteredData().size());

        filter.reset();

        filter.filterICAO("L");
        assertTrue(size != filter.getFilteredData().size());

        filter.reset();

        filter.filterLatitude("7");
        assertTrue(size != filter.getFilteredData().size());

        filter.reset();

        filter.filterLongitude("4");
        assertTrue(size != filter.getFilteredData().size());

        filter.reset();

        filter.filterAltitude("0");
        assertTrue(size != filter.getFilteredData().size());

        filter.reset();

        filter.filterTimezone("1");
        assertTrue(size != filter.getFilteredData().size());

        filter.reset();

        filter.filterOlson("Europe");
        assertTrue(size != filter.getFilteredData().size());

        filter.reset();

        filter.filterDST("A");
        assertTrue(size != filter.getFilteredData().size());

        filter.printFilter();
        filter.setBaseList(filter.getFilteredData());

        size = filter.getFilteredData().size();
        filter.reset();
        assertTrue(size == filter.getFilteredData().size());

        app.deleteDataset(app.getCurrentDataset());
    }

    @Test
    public void routeFilterTest(){
        try {
            app.createDataset("FORUNITTESTINGONLY");
        } catch (DataException e) {
            fail("There seems to be database already by this name");
        }

        Dataset dataset = app.getCurrentDataset();

        try {
            dataset.importRoute("res/Reduced Samples/Routes.txt");
        } catch (DataException e) {
            fail("The sample file is missing");
        }

        RouteFilter filter = new RouteFilter(dataset.getRoutes());

        int size = dataset.getRoutes().size();

        filter.filterAirline("N");
        assertTrue(size != filter.getFilteredData().size());

        filter.reset();

        filter.filterSourceAirport("H");
        assertTrue(size != filter.getFilteredData().size());

        filter.reset();

        filter.filterDestinationAirport("P");
        assertTrue(size != filter.getFilteredData().size());

        filter.reset();

        filter.filterCodeshare("Y");
        assertTrue(size != filter.getFilteredData().size());

        filter.reset();

        filter.filterDestinationStops("3");
        assertTrue(size != filter.getFilteredData().size());

        filter.reset();

        filter.filterEquipment("777");
        assertTrue(size != filter.getFilteredData().size());
        filter.printFilter();
        filter.setBaseList(filter.getFilteredData());
        size = filter.getFilteredData().size();

        filter.reset();

        assertTrue(filter.getFilteredData().size() == size);

        app.deleteDataset(app.getCurrentDataset());
    }

    @AfterClass
    public static void deleteDataset(){
        while(app.getCurrentDataset() != null) {
            app.deleteDataset(app.getCurrentDataset());
        }
    }
}