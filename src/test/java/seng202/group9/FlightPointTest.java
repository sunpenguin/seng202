package seng202.group9;/**
 * Created by Gondr on 19/09/2016.
 */

import static org.junit.Assert.*;

import org.junit.Test;
import seng202.group9.Controller.DataException;
import seng202.group9.Core.FlightPoint;

public class FlightPointTest {

    @Test
    public void testConstructor(){
        FlightPoint startPoint = new FlightPoint("NZCH", 1, 1, "APT", "", 0, 0, 0, 0, 172.5336822, -43.48664019);

        try {
            assertTrue(startPoint.getID() == 1);
            assertTrue(startPoint.getIndexID() == 1);
        } catch (DataException e) {
            fail("The Initial ID is set for this class");
        }
        assertEquals(startPoint.getName(), "NZCH");
        assertEquals(startPoint.getType(), "APT");
        assertEquals(startPoint.getVia(), "");
        assertTrue(startPoint.getHeading() == 0);
        assertTrue(startPoint.getLegDistance() == 0);
        assertTrue(startPoint.getAltitude() == 0);
        assertTrue(startPoint.getTotalDistance() == 0);
        assertTrue(startPoint.getLatitude() == 172.5336822);
        assertTrue(startPoint.getLongitude() == -43.48664019);

        startPoint.setName("NZAK");
        startPoint.setType("FIX");
        startPoint.setID(2);
        startPoint.setIndexID(2);
        startPoint.setVia("SIN");
        startPoint.setHeading(15);
        startPoint.setLegDistance(95.4);
        startPoint.setAltitude(0.5);
        startPoint.setTotalDistance(540.8);
        startPoint.setLatitude(172.5);
        startPoint.setLongitude(-43.5);

        try {
            assertTrue(startPoint.getID() == 2);
            assertTrue(startPoint.getIndexID() == 2);
        } catch (DataException e) {
            fail("The Initial ID is set for this class");
        }
        assertEquals(startPoint.getName(), "NZAK");
        assertEquals(startPoint.getType(), "FIX");
        assertEquals(startPoint.getVia(), "SIN");
        assertTrue(startPoint.getHeading() == 15);
        assertTrue(startPoint.getLegDistance() == 95.4);
        assertTrue(startPoint.getAltitude() == 0.5);
        assertTrue(startPoint.getTotalDistance() == 540.8);
        assertTrue(startPoint.getLatitude() == 172.5);
        assertTrue(startPoint.getLongitude() == -43.5);

    }

    @Test (expected = DataException.class)
    public void checkIDFail() throws  DataException{
        FlightPoint endPoint = new FlightPoint("APT", "WSSS", 0, 103.995603, 1.35191714);
        endPoint.getID();
    }

    @Test (expected = DataException.class)
    public void checkIndexIDFail() throws  DataException{
        FlightPoint endPoint = new FlightPoint("APT", "WSSS", 0, 103.995603, 1.35191714);
        endPoint.getIndexID();
    }


    @Test (expected = DataException.class)
    public void checkIndexFail() throws  DataException{
        FlightPoint endPoint = new FlightPoint("APT", "WSSS", 0, 103.995603, 1.35191714);
        endPoint.getIndex();
    }

}