package seng202.group9;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import seng202.group9.Core.Airline;

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
    	assertTrue(allNipponAirways.getID() == 324);
    	assertTrue(allNipponAirways.getName() == "All Nippon Airways");
    	assertTrue(allNipponAirways.getAlias() == "ANA All Nippon Airways");
    	assertTrue(allNipponAirways.getIATA() == "NH");
    	assertTrue(allNipponAirways.getICAO() == "ANA");
    	assertTrue(allNipponAirways.getCallSign() == "ALL NIPPON");
    	assertTrue(allNipponAirways.getCountry() == "Japan");
    	assertTrue(allNipponAirways.getActive() == "Y");
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
