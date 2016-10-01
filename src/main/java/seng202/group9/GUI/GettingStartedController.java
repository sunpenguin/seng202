package seng202.group9.GUI;

import seng202.group9.Controller.SceneCode;

/**
 * Created by spe76 on 26/09/16.
 */
public class GettingStartedController extends Controller {

    public void load() {

    }

    public void importAirlines() {
        getParent().getMenuController().changeDatasetPrompt();
        Importer importer = new Importer(SceneCode.AIRLINE_RAW_DATA, getParent(), getParent().getPrimaryStage());
    }

    public void importAirports() {
        getParent().getMenuController().changeDatasetPrompt();
        Importer importer = new Importer(SceneCode.AIRPORT_RAW_DATA, getParent(), getParent().getPrimaryStage());
    }

    public void importRoutes() {
        getParent().getMenuController().changeDatasetPrompt();
        Importer importer = new Importer(SceneCode.ROUTE_RAW_DATA, getParent(), getParent().getPrimaryStage());
    }

    public void importFlightData() {
        getParent().getMenuController().changeDatasetPrompt();
        Importer importer = new Importer(SceneCode.FLIGHT_RAW_DATA, getParent(), getParent().getPrimaryStage());
    }

    public void goToAirlineSummary() {
        replaceSceneContent(SceneCode.AIRLINE_SUMMARY);
    }
}
