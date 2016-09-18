package seng202.group9;/**
 * Created by Gondr on 19/09/2016.
 */

import static org.junit.Assert.*;

import org.junit.Test;
import seng202.group9.Core.Position;
import seng202.group9.Core.RoutePath;

public class RoutePathTest {

    @Test
    public void testConstructors(){
        RoutePath routePath = new RoutePath(
                new Position(5, 6),
                new Position(0, 1)
        );

        assertTrue(routePath.getRoute().get(0).lat == 5);
        assertTrue(routePath.getRoute().get(0).lng == 6);
        assertTrue(routePath.getRoute().get(1).lat == 0);
        assertTrue(routePath.getRoute().get(1).lng == 1);
        assertTrue(routePath.getRoute().size() == 2);

        routePath.addPosition(new Position(3, 4));

        assertTrue(routePath.getRoute().size() == 3);
        assertTrue(routePath.getRoute().get(2).lat == 3);
        assertTrue(routePath.getRoute().get(2).lng == 4);

        assertEquals(routePath.toJSONArray(), "[{lat: 5.000000, lng: 6.000000}, {lat: 0.000000, lng: 1.000000}, {lat: 3.000000, lng: 4.000000}, ]");

        RoutePath routePath1 = new RoutePath();
        assertTrue(routePath1.getRoute().size() == 0);
    }
}