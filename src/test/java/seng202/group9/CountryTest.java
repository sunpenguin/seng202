package seng202.group9;/**
 * Created by Gondr on 19/09/2016.
 */

import static org.junit.Assert.*;

import org.junit.Test;
import seng202.group9.Controller.DataException;
import seng202.group9.Core.Airline;
import seng202.group9.Core.City;
import seng202.group9.Core.Country;
import seng202.group9.Core.Position;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CountryTest {

    @Test
    public void testConstructor(){
        Country country = new Country("A", "Japan");

        assertEquals(country.getName(), "Japan");
        assertEquals(country.getDST(), "A");

        country.setName("United Kingdom");
        country.setDST("E");

        assertEquals(country.getName(), "United Kingdom");
        assertEquals(country.getDST(), "E");
    }

    @Test
    public void testAirlines(){
        Country country = new Country("A", "Japan");
        //71,"Advanced Air Co.",\N,"","ADD","","Japan","N"
        Airline advancedAir = new Airline(71,"Advanced Air Co.","\\N","","ADD","","Japan","N");
        //248,"ANA & JP Express",\N,"","AJV","AYJAY CARGO","Japan","N"
        Airline jPExpress = new Airline(248,"ANA & JP Express","\\N","","AJV","AYJAY CARGO","Japan","N");

        country.addAirline(advancedAir);
        assertEquals(country.getAirlines().get(0), advancedAir);
        assertTrue(country.getAirlines().size() == 1);

        country.delAirline(advancedAir);
        assertTrue(country.getAirlines().size() == 0);

        ArrayList<Airline> airlines = new ArrayList<Airline>();
        airlines.add(advancedAir);
        airlines.add(jPExpress);

        country.addAirline(airlines);
        assertTrue(country.getAirlines().size() == 2);

        try {
            country.delAirline(0);
        } catch (DataException e) {
            e.printStackTrace();
        }
        assertTrue(country.getAirlines().size() == 1);

        country.setAirlines(airlines);
        assertTrue(country.getAirlines().size() == 2);

        //Tokyo, Japan,9,"Asia/Tokyo"
        City tokyo = new City("Tokyo", "Japan", 9, "Asia/Tokyo");
        City matsumoto = new City("Matsumoto", "Japan", 9, "Asia/Tokyo");

        country.addCities(tokyo);
        assertEquals(country.getCities().get(0), tokyo);

        country.delCities(tokyo);
        assertTrue(country.getCities().size() == 0);

        ArrayList<City> cities = new ArrayList<City>();
        cities.add(tokyo);
        cities.add(matsumoto);

        country.addCities(cities);
        assertTrue(country.getCities().size() == 2);
        try {
            country.delCities(0);
        } catch (DataException e) {
            fail("There is a city deletable");
        }
        country.setCities(cities);
        assertTrue(country.getCities().size() == 2);

        Position position = new Position(0, 0);
        country.setPosition(position);
        assertEquals(country.getPosition(), position);
    }

    @Test (expected = DataException.class)
    public void delAirlineIndexOutOfRange() throws DataException{
        Country country = new Country("A", "Japan");
        country.delAirline(0);
    }

    @Test (expected = DataException.class)
    public void delAirlineIndexNegative() throws DataException{
        Country country = new Country("A", "Japan");
        country.delAirline(-1);
    }

    @Test (expected = DataException.class)
    public void delCityIndexOutOfRange() throws DataException{
        Country country = new Country("A", "Japan");
        country.delCities(0);
    }

    @Test (expected = DataException.class)
    public void delCityIndexNegative() throws DataException{
        Country country = new Country("A", "Japan");
        country.delCities(-1);
    }

}