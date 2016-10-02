package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.group9.Controller.Dataset;

/**
 * Created by Sunguin on 2016/09/23.
 */
public class RouteAddController extends Controller {
    //Setting up text fields for adding data
    @FXML
    private TextField rAirlineAdd;
    @FXML
    private TextField rSourceAdd;
    @FXML
    private TextField rDestAdd;
    @FXML
    private TextField rCodeshareAdd;
    @FXML
    private TextField rStopsAdd;
    @FXML
    private TextField rEquipmentAdd;
    @FXML
    private Button addButton;

    private Dataset theDataSet = null;

    /**
     * Adds a single route entry in the database.
     * Takes in values from the GUI the user has typed in.
     * @see Dataset
     */
    public void addRouteSingle() {
        //Tries to add a new route and clears the fields to their initial state if successful.
        //Otherwise an error message will pop up with what is wrong with the manual data.
        try {
            theDataSet.addRoute(
                    rAirlineAdd.getText(),
                    rSourceAdd.getText(),
                    rDestAdd.getText(),
                    rCodeshareAdd.getText(),
                    rStopsAdd.getText(),
                    rEquipmentAdd.getText()
            );
            rAirlineAdd.clear();
            rSourceAdd.clear();
            rDestAdd.clear();
            rCodeshareAdd.clear();
            rStopsAdd.clear();
            rEquipmentAdd.clear();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Route Add Successful");
            alert.setHeaderText("New Route added!");
            alert.setContentText("Your new route has been successfully added into the database.");
            alert.showAndWait();

            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();

        } catch ( Exception e ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Route Data Error");
            alert.setHeaderText("Error adding a custom route entry.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void load() {
        if (!checkDataset()){
            return;
        }
        theDataSet = getParent().getCurrentDataset();
    }
}
