package seng202.group9.GUI;

import seng202.group9.Controller.SceneCode;

/**
 * Created by spe76 on 26/09/16.
 */
public class GettingStartedController extends Controller {

    public void load() {

    }

    public void importAirlines() {
        Importer importer = new Importer(SceneCode.AIRLINE_RAW_DATA, getParent(), getParent().getPrimaryStage());
    }

    public void importAirports() {
        Importer importer = new Importer(SceneCode.AIRPORT_RAW_DATA, getParent(), getParent().getPrimaryStage());
    }

    public void importRoutes() {
        Importer importer = new Importer(SceneCode.ROUTE_RAW_DATA, getParent(), getParent().getPrimaryStage());
    }

    public void importFlightData() {
        Importer importer = new Importer(SceneCode.FLIGHT_RAW_DATA, getParent(), getParent().getPrimaryStage());
    }

    public void goToAirlineSummary() {
        replaceSceneContent(SceneCode.AIRLINE_SUMMARY);
    }
}
