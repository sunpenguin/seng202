package seng202.group9.GUI;

import javafx.scene.control.Alert;
import seng202.group9.Controller.SceneCode;


/**
 * The GUI controller class for getting_started.fxml.
 * Extends from the abstract class {@link Controller}
 * Created by Sunguin.
 */
public class GettingStartedController extends Controller {
    /**
     * Initial load for getting started
     */
    public void load() {
    }

    /**
     * import airlines from file function.
     */
    public void importAirlines() {
        if (getParent().getCurrentDataset() == null){
            createPopUpStage(SceneCode.DATASET_CONTROLLER, 600, 400);
            if (getParent().getCurrentDataset() != null){
                Importer importer = new Importer(SceneCode.AIRLINE_RAW_DATA, getParent(), getParent().getPrimaryStage());
            }
        }else {
            getParent().getMenuController().changeDatasetPrompt();
            Importer importer = new Importer(SceneCode.AIRLINE_RAW_DATA, getParent(), getParent().getPrimaryStage());
        }
    }

    /**
     * import airports for file functions.
     */
    public void importAirports() {
        if (getParent().getCurrentDataset() == null){
            createPopUpStage(SceneCode.DATASET_CONTROLLER, 600, 400);
            if (getParent().getCurrentDataset() != null){
                Importer importer = new Importer(SceneCode.AIRPORT_RAW_DATA, getParent(), getParent().getPrimaryStage());
            }
        }else {
            getParent().getMenuController().changeDatasetPrompt();
            Importer importer = new Importer(SceneCode.AIRPORT_RAW_DATA, getParent(), getParent().getPrimaryStage());
        }
    }

    /**
     * import routes from file functions.
     */
    public void importRoutes() {
        if (getParent().getCurrentDataset() == null){
            createPopUpStage(SceneCode.DATASET_CONTROLLER, 600, 400);
            if (getParent().getCurrentDataset() != null){
                Importer importer = new Importer(SceneCode.ROUTE_RAW_DATA, getParent(), getParent().getPrimaryStage());
            }
        }else {
            getParent().getMenuController().changeDatasetPrompt();
            Importer importer = new Importer(SceneCode.ROUTE_RAW_DATA, getParent(), getParent().getPrimaryStage());
        }
    }

    /**
     * import flight data from file functions.
     */
    public void importFlightData() {
        if (getParent().getCurrentDataset() == null){
            createPopUpStage(SceneCode.DATASET_CONTROLLER, 600, 400);
            if (getParent().getCurrentDataset() != null){
                Importer importer = new Importer(SceneCode.FLIGHT_RAW_DATA, getParent(), getParent().getPrimaryStage());
            }
        }else {
            getParent().getMenuController().changeDatasetPrompt();
            Importer importer = new Importer(SceneCode.FLIGHT_RAW_DATA, getParent(), getParent().getPrimaryStage());
        }
    }

    /**
     * manage the datasets.
     */
    public void manageDatasets() {
        getParent().getMenuController().openDataset();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dataset Selected");
        alert.setHeaderText("You have decided to change the Dataset.");
        alert.setContentText("The Flight Data System will now use your selected Dataset.");
        alert.showAndWait();
    }
}
