package seng202.group9;

import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import seng202.group9.Core.Airline;
import seng202.group9.Core.Route;

/**
 * Test the functions for the Airline
 * this is the get, set methods for in the class Airline
 * Airline
 * @author Fan-Wu Yang
 *
 */
public class AirlineTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
	public AirlineTest(String testName){
		super(testName);
	}
	

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AirlineTest.class );
    }
	
    /**
     * Test for Airline get and setting methods
     * (Currently missing ADD ROUTES)
     */
    public void testAirlineGetSet(){
    	//ID, Name, Alias, IATA, ICAO, CallSign, Country, Active
    	//324,"All Nippon Airways","ANA All Nippon Airways","NH","ANA","ALL NIPPON","Japan","Y"
    	Airline allNipponAirways = new Airline(324, "All Nippon Airways", "ANA All Nippon Airways",
    			"NH", "ANA", "ALL NIPPON", "Japan", "Y");
    	//check constructor
    	assertTrue(allNipponAirways.getID() == 324); //test id
    	assertTrue(allNipponAirways.getName() == "All Nippon Airways");//test name
    	assertTrue(allNipponAirways.getAlias() == "ANA All Nippon Airways");// test alias
    	assertTrue(allNipponAirways.getIATA() == "NH");// test iata
    	assertTrue(allNipponAirways.getICAO() == "ANA");// test icao
    	assertTrue(allNipponAirways.getCallSign() == "ALL NIPPON");// test call sign
    	assertTrue(allNipponAirways.getCountry() == "Japan");// test country
    	assertTrue(allNipponAirways.getActive() == "Y");// get active
    	//check set
    	allNipponAirways.setID(322);//ID
    	assertTrue(allNipponAirways.getID() == 322);
    	
    	allNipponAirways.setName("All Nike Airways");//Name
    	assertTrue(allNipponAirways.getName() == "All Nike Airways");
    	
    	allNipponAirways.setAlias("ANA All Nike Airways");//Alias
    	assertTrue(allNipponAirways.getAlias() == "ANA All Nike Airways");
    	
    	allNipponAirways.setIATA("NA");//IATA
    	assertTrue(allNipponAirways.getIATA() == "NA");
    	
    	allNipponAirways.setICAO("ANNA");//ICAO
    	assertTrue(allNipponAirways.getICAO() == "ANNA");
    	
    	allNipponAirways.setCallSign("NIKE RULES");//Call Sign
    	assertTrue(allNipponAirways.getCallSign() =="NIKE RULES");
    	
    	allNipponAirways.setCountry("Nikeland");//Country
    	assertTrue(allNipponAirways.getCountry() == "Nikeland");
    	
    	allNipponAirways.setActive("N");//Active
    	assertTrue(allNipponAirways.getActive() == "N");
    	
    	//TODO Add Route adding setting and getting.
    	//try add one route and test
    	Route route1 = new Route("NH", "Japan", "New Zealand", "JSQ", 3, "Boeing 707");
    	allNipponAirways.addRoutes(route1);
    	
    	assertEquals(allNipponAirways.getRoutes().get(0), route1);//list was originally empty now its first member should be route 1
    	assertTrue(allNipponAirways.getRoutes().size() == 1);//list was originally empty now it should have 1 member
    	
    	Route route2 = new Route("NH", "Japan", "Donald-Trumps Backyard", "KAMIKAZE ATTACK", 0, "Warplane 101");
    	//test set routes
    	//adding routes
    	ArrayList<Route> routes = new ArrayList<Route>();
    	routes.add(route1);
    	routes.add(route2);
    	
    	allNipponAirways.setRoutes(routes);//set routes test
    	assertTrue(allNipponAirways.getRoutes().size() == 2);
    	assertEquals(allNipponAirways.getRoutes().get(0), route1);
    	assertEquals(allNipponAirways.getRoutes().get(1), route2);
    	
    	//test add arraylist of routes. this also makes sure that the above
    	//code doesn't loop infinitily incase the object pointer has been set
    	//instead of cloning the data.
    	allNipponAirways.addRoutes(routes);
    	assertTrue(allNipponAirways.getRoutes().size() == 4);
    	assertEquals(allNipponAirways.getRoutes().get(0), route1);
    	assertEquals(allNipponAirways.getRoutes().get(1), route2);
    	assertEquals(allNipponAirways.getRoutes().get(2), route1);
    	assertEquals(allNipponAirways.getRoutes().get(3), route2);
    }
    
    public void testToString(){
    	//ID, Name, Alias, IATA, ICAO, CallSign, Country, Active
    	//324,"All Nippon Airways","ANA All Nippon Airways","NH","ANA","ALL NIPPON","Japan","Y"
    	Airline allNipponAirways = new Airline(324, "All Nippon Airways", "ANA All Nippon Airways",
    			"NH", "ANA", "ALL NIPPON", "Japan", "Y");
    	assertTrue(allNipponAirways.toString() == "All Nippon Airways");
    	assertEquals(allNipponAirways + "" ,"All Nippon Airways");
    }
}
