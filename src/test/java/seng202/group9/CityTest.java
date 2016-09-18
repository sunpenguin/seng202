package seng202.group9;/**
 * Created by Gondr on 18/09/2016.
 */

import static org.junit.Assert.*;

import org.junit.Test;
import seng202.group9.Core.Airport;
import seng202.group9.Core.City;

import java.util.ArrayList;

public class CityTest {

    @Test
    public void testConstructor(){
        //Matsumoto, Japan 9,"Asia/Tokyo"
        City matsumoto = new City("Matsumoto", "Japan", 9, "Asia/Tokyo");

        assertEquals(matsumoto.getName(), "Matsumoto");
        assertEquals(matsumoto.getCountry(), "Japan");
        assertTrue(matsumoto.getTimezone() == 9);
        assertEquals(matsumoto.getTimeOlson(), "Asia/Tokyo");

        //check setters
        matsumoto.setName("matsumoto");
        matsumoto.setCountry("japan");
        matsumoto.setTimezone(10);
        matsumoto.setTimeOlson("Japan/Tokyo");

        assertEquals(matsumoto.getName(), "matsumoto");
        assertEquals(matsumoto.getCountry(), "japan");
        assertTrue(matsumoto.getTimezone() == 10);
        assertEquals(matsumoto.getTimeOlson(), "Japan/Tokyo");
    }

    @Test
    public void testAirports(){
        City matsumoto = new City("Matsumoto", "Japan", 9, "Asia/Tokyo");
        Airport matsumotoAirport = new Airport(2280,"Matsumoto","Matsumoto","Japan","MMJ","RJAF",36.166758,137.922669,2182);
        assertTrue(matsumoto.getAirports().size() == 0);
        matsumoto.addAirport(matsumotoAirport);
        assertEquals(matsumoto.getAirports().get(0), matsumotoAirport);
        assertTrue(matsumoto.getAirports().size() == 1);
        matsumoto.delAirport(matsumotoAirport);
        assertTrue(matsumoto.getAirports().size() == 0);
        ArrayList<Airport> airports = new ArrayList<Airport>();
        airports.add(matsumotoAirport);
        airports.add(matsumotoAirport);
        matsumoto.addAirport(airports);
        assertTrue(matsumoto.getAirports().size() == 2);
        matsumoto.delAirport(0);
        assertTrue(matsumoto.getAirports().size() == 1);
        matsumoto.delAirport(0);
        //try to remove more than the city has for airports
        matsumoto.delAirport(0);
        matsumoto.delAirport(matsumotoAirport);
        //set the airports
        matsumoto.setAirports(airports);
        assertTrue(matsumoto.getAirports().size() == 2);
    }

    @Test
    public void testString(){
        City matsumoto = new City("Matsumoto", "Japan", 9, "Asia/Tokyo");
        assertEquals(matsumoto.toString(), "Matsumoto has 0 Airports and is in Asia/Tokyo");
    }

}