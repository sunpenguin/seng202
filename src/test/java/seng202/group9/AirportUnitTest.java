package seng202.group9;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import seng202.group9.Controller.DataException;
import seng202.group9.Core.Airport;

/**
 * Unit test for Airport
 * @author Fan-Wu Yang
 *
 */
public class AirportUnitTest {
	//exception checker
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void airportGetterAndSetterTest() {
		//507,"Heathrow","London","United Kingdom","LHR","EGLL",51.4775,-0.461389,83,0,"E","Europe/London"
		//ID, Name, City, Country, IATA/FFA, ICAO, Latitude, Longitude, Altitude, Timezone, DST, Tz Data
		Airport heathrow = new Airport(507, "Heathrow", "London", "LHR", "EGLL", 51.4775, -0.41389, 83);
		/////////////////
		//check getters//
		/////////////////
		try {
			assertEquals(heathrow.getID(), 507);//check ID no id should be thrown
		} catch (DataException e) {
			fail("No Exception should be thrown by the constructor where the ID is already set bye trying to get the ID.");
		}
		assertEquals(heathrow.getName(), "Heathrow");
		assertEquals(heathrow.getCity(), "London");//check city
		assertEquals(heathrow.getIATA_FFA(), "LHR");//check IATA/FFA
		assertEquals(heathrow.getICAO(), "EGLL");//check ICAO
		assertTrue(heathrow.getLatitude() == 51.4775);//check latitude
		assertTrue(heathrow.getLongitude() == -0.41389);//check longitude
		assertTrue(heathrow.getAltitude() == 83);//check altitude.
		/////////////////
		//check setters//
		/////////////////
		heathrow.setID(544);
		try {
			assertEquals(heathrow.getID(), 544);//check ID no id should be thrown
		} catch (DataException e) {
			fail("No Exception should be thrown by the constructor where the ID is already set bye trying to get the ID.");
		}
		heathrow.setName("Hearthstone");//check name
		assertEquals(heathrow.getName(), "Hearthstone");
		heathrow.setCity("Blizzard Servers");//check city
		assertEquals(heathrow.getCity(), "Blizzard Servers");
		heathrow.setIATA_FFA("HTS");//test set IATA/FFA
		assertEquals(heathrow.getIATA_FFA(), "HTS");
		heathrow.setICAO("BLIZ");// test set ICAO
		assertEquals(heathrow.getICAO(), "BLIZ");
		heathrow.setLatitude(50.1);//test set latitude
		assertTrue(heathrow.getLatitude() == 50.1);
		heathrow.setLongitude(0.006);//test set longitude
		assertTrue(heathrow.getLongitude() == 0.006);
		heathrow.setCoordinates(51.4775, -0.41389);//test set coordinates
		assertTrue(heathrow.getLatitude() == 51.4775);
		assertTrue(heathrow.getLongitude() == -0.41389);
		heathrow.setAltitude(0);//test set altitude
		assertTrue(heathrow.getAltitude() == 0);
	}
	
	public void checkIDException(){
		//507,"Heathrow","London","United Kingdom","LHR","EGLL",51.4775,-0.461389,83,0,"E","Europe/London"
		//ID, Name, City, Country, IATA/FFA, ICAO, Latitude, Longitude, Altitude, Timezone, DST, Tz Data
		Airport heathrow = new Airport("Heathrow", "London", "LHR", "EGLL", 51.4775, -0.41389, 83);
		thrown.expect(DataException.class);
		thrown.expectMessage("ID not set.");
		try {
			assertEquals(heathrow.getID(), 544);//check ID no id should be thrown
		} catch (DataException e) {
			
		}
	}

}
