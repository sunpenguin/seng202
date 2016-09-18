package seng202.group9;

import javafx.scene.chart.PieChart;
import org.junit.Test;

import junit.framework.TestCase;
import seng202.group9.Controller.DataException;
import seng202.group9.Core.Airline;
import seng202.group9.Core.Airport;
import seng202.group9.Core.Route;
import seng202.group9.Core.RoutePath;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * 
 * Route tests
 * @author Fan-Wu Yang
 *
 */
public class RouteTest{
    
	@Test
    public void testRouteGetSet(){
    	//sample entry
    	//BA,1355,SIN,3316,LHR,507,,0,744 777
    	//airline, airline ID (This is not parsed as this will be different
    	//from our database id for this route), source Aiport, source Airport ID (not parsed),
    	//destination airport, destination ID (not parsed), Codeshare, stops, Equipment
    	Route route = new Route("BA", "SIN", "LHR", "", 0, "744 777");
    	//////////////
    	//test getters
    	//////////////
    	assertEquals(route.getAirlineName(), "BA");
    	assertEquals(route.getDepartureAirport(), "SIN");
    	assertEquals(route.getArrivalAirport(), "LHR");
    	assertEquals(route.getAirlineName(), "BA");
    	assertEquals(route.getCode(), "");
    	assertEquals(route.getEquipment(), "744 777");
    	///////////////
    	//test setters
    	///////////////
		route.setID(5);
    	route.setAirlineName("BAH");
    	route.setArrivalAirport("LEFT-HAND-RULE");
    	route.setDepartureAirport("SING-SONG");
    	route.setCode("Y");
    	route.setStops(5);
    	route.setEquipment("747 840");

		try {
			assertTrue(route.getID() == 5);
		} catch (DataException e) {
			fail("ID has been set it shouldn't fail.");
		}
		assertEquals(route.getDepartureAirport(), "SING-SONG");
    	assertEquals(route.getArrivalAirport(), "LEFT-HAND-RULE");
    	assertEquals(route.getAirlineName(), "BAH");
    	assertEquals(route.getCode(), "Y");
    	assertEquals(route.getEquipment(), "747 840");
    	////////////////////////////
    	//test alternate constructor
    	////////////////////////////
    	Route route2 = new Route(1355, "BA", "SIN", "LHR", "", 0, "744 777");
    	try {
			assertTrue(1355 == route2.getID());
		} catch (DataException e) {
			e.printStackTrace();
		}
    }
	
	@Test(expected = DataException.class)
	public void testRouteIDException() throws DataException {
    	//sample entry
    	//BA,1355,SIN,3316,LHR,507,,0,744 777
    	//airline, airline ID (This is not parsed as this will be different
    	//from our database id for this route), source Aiport, source Airport ID (not parsed),
    	//destination airport, destination ID (not parsed), Codeshare, stops, Equipment
    	Route route = new Route("BA", "SIN", "LHR", "", 0, "744 777");
		route.getID();
	}

	@Test
	public void testAirlineAdding(){
		//1355,"British Airways",\N,"BA","BAW","SPEEDBIRD","United Kingdom","Y" AIrline
		Airline airline = new Airline(1355,"British Airways","\\N","BA","BAW","SPEEDBIRD","United Kingdom","Y");
		//sample entry
		//BA,1355,SIN,3316,LHR,507,,0,744 777
		//airline, airline ID (This is not parsed as this will be different
		//from our database id for this route), source Aiport, source Airport ID (not parsed),
		//destination airport, destination ID (not parsed), Codeshare, stops, Equipment
		Route route = new Route("BA", "SIN", "LHR", "", 0, "744 777");
		route.setAirline(airline);
		assertEquals(airline, route.getAirline());
		try {
			assertTrue(airline.getID() == route.getAirlineID());
			assertTrue(route.getAirlineID() == 1355);
		}catch(DataException e){
			fail("The ID is set before it should not fail.");
		}
		Airport sourceAirport = new Airport(3316,"Changi Intl","Singapore","Singapore","SIN","WSSS",1.350189,103.994433,22);
		Airport destAirport = new Airport(507,"Heathrow","London","United Kingdom","LHR","EGLL",51.4775,-0.461389,83);

		route.setSourceAirport(sourceAirport);
		route.setDestinationAirport(destAirport);

		assertEquals(sourceAirport, route.getSourceAirport());
		assertEquals(destAirport, route.getDestinationAirport());
		try {
			assertTrue(route.getSourceID() == 3316);
			assertTrue(route.getDestID() == 507);
		} catch (DataException e) {
			e.printStackTrace();
		}

		assertEquals(route.getDepartureAirport(), route.getSourceAirport().getIATA_FFA());
		assertEquals(route.getArrivalAirport(), route.getDestinationAirport().getIATA_FFA());
	}

	@Test(expected = DataException.class)
	public void testNameDuplicate() throws DataException{
		//sample entry
		//BA,1355,SIN,3316,LHR,507,,0,744 777
		//airline, airline ID (This is not parsed as this will be different
		//from our database id for this route), source Aiport, source Airport ID (not parsed),
		//destination airport, destination ID (not parsed), Codeshare, stops, Equipment
		//should only be a duplicate if every entry is the same
		Route route = new Route("BA", "SIN", "LHR", "", 0, "744 777");
		Route route1 = new Route("BA", "SIN", "LHR", "", 0, "744 777");
		Route route2 = new Route("BA", "SII", "LHR", "", 0, "744 777");
		Route route3 = new Route("BQ", "SIN", "LHR", "", 0, "744 777");
		Route route4 = new Route("BA", "SIN", "RHR", "", 0, "744 777");
		Route route5 = new Route("BA", "SIN", "LHR", "Y", 0, "744 777");
		Route route6 = new Route("BA", "SIN", "LHR", "", 8, "744 777");
		Route route7 = new Route("BA", "SIN", "LHR", "", 0, "745 777");
		//this shouldn't throw
		try {
			route.hasDuplicate(route2);
			route.hasDuplicate(route3);
			route.hasDuplicate(route4);
			route.hasDuplicate(route5);
			route.hasDuplicate(route6);
			route.hasDuplicate(route7);
		} catch (DataException e) {
			fail("These two routes are different they should not throw an error");
		}
		//this should throw an exception.
		route.hasDuplicate(route1);
	}

	@Test
	public void testGetPath(){
		//sample entry
		//BA,1355,SIN,3316,LHR,507,,0,744 777
		//airline, airline ID (This is not parsed as this will be different
		//from our database id for this route), source Aiport, source Airport ID (not parsed),
		//destination airport, destination ID (not parsed), Codeshare, stops, Equipment
		//should only be a duplicate if every entry is the same
		Route route = new Route("BA", "SIN", "LHR", "", 0, "744 777");
		Airport sourceAirport = new Airport(3316,"Changi Intl","Singapore","Singapore","SIN","WSSS",1.350189,103.994433,22);
		Airport destAirport = new Airport(507,"Heathrow","London","United Kingdom","LHR","EGLL",51.4775,-0.461389,83);
		assertTrue(route.getRoutePath().getRoute().size() == 0);
		route.setSourceAirport(sourceAirport);
		assertTrue(route.getRoutePath().getRoute().size() == 0);
		route.setDestinationAirport(destAirport);
		assertTrue(route.getRoutePath().getRoute().size() == 2);
	}

	@Test
	public void testToString() {
		//sample entry
		//BA,1355,SIN,3316,LHR,507,,0,744 777
		//airline, airline ID (This is not parsed as this will be different
		//from our database id for this route), source Aiport, source Airport ID (not parsed),
		//destination airport, destination ID (not parsed), Codeshare, stops, Equipment
		//should only be a duplicate if every entry is the same
		Route route = new Route("BA", "SIN", "LHR", "", 0, "744 777");
		assertEquals(route.toString(), "BA flies from SIN to LHR on a 744 777 stopping 0 amount of times");
	}
}
