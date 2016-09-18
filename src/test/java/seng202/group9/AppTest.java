package seng202.group9;

import org.junit.Test;
import seng202.group9.Controller.App;
import seng202.group9.Controller.DataException;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void testApp(){
        App app = new App();
        assertTrue(app.getMenuController() == null);
        assertTrue(app.getPrimaryStage() == null);
        try {
            app.createDataset("FORUNITTESTINGDATASET");
        } catch (DataException e) {
            e.printStackTrace();
        }
        assertEquals(app.getCurrentDataset().getName(), "FORUNITTESTINGDATASET");

        app.deleteDataset(app.getCurrentDataset());

        for (int i = 0; i < app.getDatasets().size(); i++){
            assertTrue(!app.getDatasets().get(i).getName().equals("FORUNITTESTINGDATASET"));
        }
        //more to come as the program upgrades for for as of now this is the limit.
    }
}
