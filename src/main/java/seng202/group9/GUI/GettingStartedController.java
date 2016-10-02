package seng202.group9.GUI;

import javafx.scene.control.Alert;
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

    public void manageDatasets() {
        getParent().getMenuController().openDataset();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dataset Selected");
        alert.setHeaderText("You have decided to change the Dataset.");
        alert.setContentText("The Flight Data System will now use your selected Dataset.");
        alert.showAndWait();
    }
}
