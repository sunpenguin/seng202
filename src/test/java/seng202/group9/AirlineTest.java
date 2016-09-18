package seng202.group9;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import seng202.group9.Controller.DataException;
import seng202.group9.Core.Airline;
import seng202.group9.Core.Country;
import seng202.group9.Core.Route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test the functions for the Airline
 * this is the get, set methods for in the class Airline
 * Airline
 * @author Fan-Wu Yang
 *
 */
public class AirlineTest{
	
    /**
     * Test for Airline get and setting methods
     * (Currently missing ADD ROUTES)
     */
    @Test
    public void testAirlineGetSet(){
    	//ID, Name, Alias, IATA, ICAO, CallSign, Country, Active
    	//324,"All Nippon Airways","ANA All Nippon Airways","NH","ANA","ALL NIPPON","Japan","Y"
    	Airline allNipponAirways = new Airline(324, "All Nippon Airways", "ANA All Nippon Airways",
    			"NH", "ANA", "ALL NIPPON", "Japan", "Y");
    	//check constructor
		try {
			assertTrue(allNipponAirways.getID() == 324); //test id
		} catch (DataException e) {
			e.printStackTrace();
		}
		assertTrue(allNipponAirways.getName() == "All Nippon Airways");//test name
    	assertTrue(allNipponAirways.getAlias() == "ANA All Nippon Airways");// test alias
    	assertTrue(allNipponAirways.getIATA() == "NH");// test iata
    	assertTrue(allNipponAirways.getICAO() == "ANA");// test icao
    	assertTrue(allNipponAirways.getCallSign() == "ALL NIPPON");// test call sign
    	assertTrue(allNipponAirways.getCountryName() == "Japan");// test country
    	assertTrue(allNipponAirways.getActive() == "Y");// get active
    	//check set
    	allNipponAirways.setID(322);//ID
		try {
			assertTrue(allNipponAirways.getID() == 322);
		} catch (DataException e) {
			e.printStackTrace();
		}

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
    	
    	allNipponAirways.setCountryName("Nikeland");//Country
    	assertTrue(allNipponAirways.getCountryName() == "Nikeland");
    	
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
		try {
			allNipponAirways.delRoutes(3);
		}catch (DataException e){
			fail("3 is a valid deletable index.");
		}
		assertTrue(allNipponAirways.getRoutes().size() == 3);

		allNipponAirways.delRoutes(route2);
		assertTrue(allNipponAirways.getRoutes().size() == 2);
    }

    @Test(expected = DataException.class)
    public void testInvalidRouteDelete() throws DataException{
		//ID, Name, Alias, IATA, ICAO, CallSign, Country, Active
		//324,"All Nippon Airways","ANA All Nippon Airways","NH","ANA","ALL NIPPON","Japan","Y"
		Airline allNipponAirways = new Airline(324, "All Nippon Airways", "ANA All Nippon Airways",
				"NH", "ANA", "ALL NIPPON", "Japan", "Y");
		allNipponAirways.delRoutes(0);
	}

	@Test(expected = DataException.class)
	public void testInvalidIndexRouteDelete() throws DataException{
		//ID, Name, Alias, IATA, ICAO, CallSign, Country, Active
		//324,"All Nippon Airways","ANA All Nippon Airways","NH","ANA","ALL NIPPON","Japan","Y"
		Airline allNipponAirways = new Airline(324, "All Nippon Airways", "ANA All Nippon Airways",
				"NH", "ANA", "ALL NIPPON", "Japan", "Y");
		allNipponAirways.delRoutes(-1);
	}

    @Test(expected = DataException.class)
	public void hasDuplicateNameTest() throws DataException {
		Airline allNipponAirways = new Airline(324, "All Nippon Airways", "ANA All Nippon Airways",
				"NH", "ANA", "ALL NIPPON", "Japan", "Y");

		Airline allNipponAirway = new Airline(325, "All Nippon Airways", "ANA All Nippon Airway",
				"NP", "ANP", "ALL NIPPONP", "Japan", "Y");//duplicate name should be thrown

		allNipponAirways.hasDuplicate(allNipponAirway);
	}

	@Test(expected = DataException.class)
	public void hasDuplicateMissingNameTest() throws DataException {
		Airline allNipponAirways = new Airline(324, "All Nippon Airways", "ANA All Nippon Airways",
				"NH", "ANA", "ALL NIPPON", "Japan", "Y");

		Airline allNipponAirway = new Airline(325, "", "ANA All Nippon Airway",
				"NP", "ANP", "ALL NIPPONP", "Japan", "Y");//duplicate name should be thrown

		allNipponAirways.hasDuplicate(allNipponAirway);
	}

	@Test(expected = DataException.class)
	public void hasDuplicateIATATest() throws DataException {
		Airline allNipponAirways = new Airline(324, "All Nippon Airways", "ANA All Nippon Airways",
				"NH", "ANA", "ALL NIPPON", "Japan", "Y");

		Airline allNipponAirway = new Airline(325, "All Nippon Airway", "ANA All Nippon Airway",
				"NH", "ANP", "ALL NIPPONP", "Japan", "Y");//duplicate name should be thrown

		allNipponAirways.hasDuplicate(allNipponAirway);
	}

	@Test(expected = DataException.class)
	public void hasDuplicateICAOTest() throws DataException {
		Airline allNipponAirways = new Airline(324, "All Nippon Airways", "ANA All Nippon Airways",
				"NH", "ANA", "ALL NIPPON", "Japan", "Y");

		Airline allNipponAirway = new Airline(325, "All Nippon Airway", "ANA All Nippon Airway",
				"NP", "ANA", "ALL NIPPONP", "Japan", "Y");//duplicate name should be thrown

		allNipponAirways.hasDuplicate(allNipponAirway);
	}

	@Test(expected = DataException.class)
	public void hasDuplicateAliasTest() throws DataException {
		Airline allNipponAirways = new Airline(324, "All Nippon Airways", "ANA All Nippon Airways",
				"NH", "ANA", "ALL NIPPON", "Japan", "Y");

		Airline allNipponAirway = new Airline(325, "All Nippon Airway", "ANA All Nippon Airways",
				"NH", "ANA", "ALL NIPPONP", "Japan", "Y");//duplicate name should be thrown

		allNipponAirways.hasDuplicate(allNipponAirway);
	}

	@Test(expected = DataException.class)
	public void hasDuplicateCallTest() throws DataException {
		Airline allNipponAirways = new Airline(324, "All Nippon Airways", "ANA All Nippon Airways",
				"NH", "ANA", "ALL NIPPON", "Japan", "Y");

		Airline allNipponAirway = new Airline(325, "All Nippon Airway", "ANA All Nippon Airway",
				"NH", "ANP", "ALL NIPPON", "Japan", "Y");//duplicate name should be thrown

		allNipponAirways.hasDuplicate(allNipponAirway);
	}

	@Test
	public void testCountry(){
		Airline allNipponAirways = new Airline(324, "All Nippon Airways", "ANA All Nippon Airways",
				"NH", "ANA", "ALL NIPPON", "Japan", "Y");
		//,9,"U","Asia/Tokyo"
		Country japan = new Country("U", "Japan");
		allNipponAirways.setCountry(japan);
		assertEquals(japan, allNipponAirways.getCountry());
	}

	@Test(expected = DataException.class)
	public void getIDNullError() throws DataException{
		Airline allNipponAirways = new Airline("All Nippon Airways", "ANA All Nippon Airways",
				"NH", "ANA", "ALL NIPPON", "Japan", "Y");
		allNipponAirways.getID();
	}

	@Test
    public void testToString(){
    	//ID, Name, Alias, IATA, ICAO, CallSign, Country, Active
    	//324,"All Nippon Airways","ANA All Nippon Airways","NH","ANA","ALL NIPPON","Japan","Y"
    	Airline allNipponAirways = new Airline(324, "All Nippon Airways", "ANA All Nippon Airways",
    			"NH", "ANA", "ALL NIPPON", "Japan", "Y");
		//name + ", IATA:" + IATA + ", ICAO: " + ICAO
		System.out.println(allNipponAirways.toString());
    	assertTrue(allNipponAirways.toString().equals("All Nippon Airways, IATA: NH, ICAO: ANA"));
    	assertEquals(allNipponAirways + "" ,"All Nippon Airways, IATA: NH, ICAO: ANA");
    }
}
