package seng202.group9;/**
 * Created by Gondr on 19/09/2016.
 */

import static org.junit.Assert.*;

import org.junit.Test;
import seng202.group9.Controller.DataException;
import seng202.group9.Core.FlightPath;
import seng202.group9.Core.FlightPoint;
import seng202.group9.Core.RoutePath;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FlightPathTest {

    @Test
    public void testConstructor(){
        FlightPath flightPath = new FlightPath(1, "NZCH", "WSSS");

        try {
            assertTrue(flightPath.getID() == 1);
        } catch (DataException e) {
            fail("There is an ID set already.");
        }
        assertEquals(flightPath.arrivesAt(), "WSSS");
        assertEquals(flightPath.departsFrom(), "NZCH");

        flightPath.setArrivalAirport("WSSI");
        flightPath.setDepartureAirport("NZAK");
        flightPath.setID(2);

        try {
            assertTrue(flightPath.getID() == 2);
        } catch (DataException e) {
            fail("There is an ID set already.");
        }
        assertEquals(flightPath.arrivesAt(), "WSSI");
        assertEquals(flightPath.departsFrom(), "NZAK");
    }

    @Test(expected = DataException.class)
    public void testIDError() throws DataException{
        FlightPath flightPath = new FlightPath("NZCH", "WSSS");
        flightPath.getID();
    }

    @Test
    public void testFlightPoints(){
        FlightPath flightPath = new FlightPath(1, "NZCH", "WSSS");
        FlightPoint startPoint = new FlightPoint("NZCH", 1, 1, "APT", "", 0, 0, 0, 0, 172.5336822, -43.48664019);
        FlightPoint endPoint = new FlightPoint("WSSS", 2, 1, "APT", "", 0, 0, 0, 0, 103.995603, 1.35191714);

        ArrayList<FlightPoint> flightPoints = new ArrayList<FlightPoint>();
        flightPoints.add(startPoint);
        flightPoints.add(endPoint);

        flightPath.setFlightPoints(flightPoints);
        assertTrue(flightPath.getFlightPoints().size() == 2);
        assertTrue(flightPath.getFlight().size() == 2);

        flightPath.delFlightPoint(endPoint);
        assertTrue(flightPath.getFlightPoints().size() == 1);

        flightPath.addFlightPoint(endPoint);
        assertTrue(flightPath.getFlightPoints().size() == 2);
        assertEquals(flightPath.getFlightPoints().get(1), endPoint);

        try {
            flightPath.delFlightPoint(0);
        } catch (DataException e) {
            fail("There is an existing point therefore it should be deletable");
        }

        flightPath.addFlightPoint(startPoint, 0);
        assertEquals(flightPath.getFlightPoints().get(0), startPoint);

        flightPath.addFlightPoint(flightPoints);
        assertTrue(flightPath.getFlightPoints().size() == 4);
    }

    @Test
    public void testAddingFlightPointAtInvalidPoints(){
        FlightPath flightPath = new FlightPath(1, "NZCH", "WSSS");
        FlightPoint startPoint = new FlightPoint("NZCH", 1, 1, "APT", "", 0, 0, 0, 0, 172.5336822, -43.48664019);
        FlightPoint endPoint = new FlightPoint("WSSS", 2, 1, "APT", "", 0, 0, 0, 0, 103.995603, 1.35191714);

        flightPath.addFlightPoint(startPoint);
        flightPath.addFlightPoint(startPoint);
        flightPath.addFlightPoint(startPoint);

        flightPath.addFlightPoint(endPoint, -1);
        flightPath.addFlightPoint(endPoint, 100);

        assertEquals(flightPath.getFlightPoints().get(4), endPoint);
        assertEquals(flightPath.getFlightPoints().get(4), endPoint);
    }

    @Test
    public void getRoutePath(){
        FlightPath flightPath = new FlightPath(1, "NZCH", "WSSS");
        FlightPoint startPoint = new FlightPoint("NZCH", 1, 1, "APT", "", 0, 0, 0, 0, 172.5336822, -43.48664019);
        FlightPoint endPoint = new FlightPoint("WSSS", 2, 1, "APT", "", 0, 0, 0, 0, 103.995603, 1.35191714);

        flightPath.addFlightPoint(startPoint);
        flightPath.addFlightPoint(endPoint);

        RoutePath routePath = flightPath.getRoutePath();
        assertTrue(routePath.getRoute().get(0).lat == 172.5336822);
        assertTrue(routePath.getRoute().get(0).lng == -43.48664019);
        assertTrue(routePath.getRoute().get(1).lat == 103.995603);
        assertTrue(routePath.getRoute().get(1).lng == 1.35191714);
    }
}