package seng202.group9;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test the functions inside the COre package to make sure all their functions are working correctly
 * Core package is located at seng202.group9.Core
 * The Core package has 7 classes needed to be tested.
 * Airline
 * Airport
 * City
 * FlightPath
 * FlightPoint
 * Route
 * @author Fan-Wu Yang
 *
 */
public class CorePackageTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
	public CorePackageTest(String testName){
		super(testName);
	}
	

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }
	
    /**
     * Test for Airline
     */
    public void testAirline(){
    	
    }
}
