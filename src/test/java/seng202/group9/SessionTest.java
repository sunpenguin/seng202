package seng202.group9;/**
 * Created by Gondr on 19/09/2016.
 */

import static org.junit.Assert.*;

import org.junit.Test;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Controller.Session;

public class SessionTest {
    @Test
    public void testConstructor(){
        Session session = new Session();
        assertTrue(session.getSceneDisplayed() == SceneCode.INITIAL);
        session.setSceneDisplayed(SceneCode.AIRLINE_RAW_DATA);
        assertTrue(session.getSceneDisplayed() == SceneCode.AIRLINE_RAW_DATA);

        Session session1 = new Session(SceneCode.ROUTE_RAW_DATA);
        assertTrue(session1.getSceneDisplayed() == SceneCode.ROUTE_RAW_DATA);
    }

}