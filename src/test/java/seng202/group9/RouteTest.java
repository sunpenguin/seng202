package seng202.group9;

import org.junit.rules.ExpectedException;
import org.junit.Test;

import junit.framework.TestCase;
import seng202.group9.Controller.DataException;
import seng202.group9.Core.Route;
/**
 * 
 * Route tests
 * @author Fan-Wu Yang
 *
 */
public class RouteTest extends TestCase {
	//exception checker
	public ExpectedException thrown = ExpectedException.none();
    
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
    	route.setAirlineName("BAH");
    	route.setArrivalAirport("LEFT-HAND-RULE");
    	route.setDepartureAirport("SING-SONG");
    	route.setCode("Y");
    	route.setStops(5);
    	route.setEquipment("747 840");

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
	
	@Test
	public void testRouteIDException() throws DataException{
    	//sample entry
    	//BA,1355,SIN,3316,LHR,507,,0,744 777
    	//airline, airline ID (This is not parsed as this will be different
    	//from our database id for this route), source Aiport, source Airport ID (not parsed),
    	//destination airport, destination ID (not parsed), Codeshare, stops, Equipment
    	Route route = new Route("BA", "SIN", "LHR", "", 0, "744 777");
    	/////////////////////////////////////////////////
    	//test that id is not set and throws an exception
    	/////////////////////////////////////////////////
    	thrown.expect(DataException.class);
    	thrown.expectMessage("ID not set. Error in Database Implementation for adding code.");
    	try{
    		route.getID();
    	}catch (DataException e){
    		//e.printStackTrace();
    	}
	}
}
