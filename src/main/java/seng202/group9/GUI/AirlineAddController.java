package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import seng202.group9.Controller.Dataset;

/**
 * Created by Sunguin on 2016/09/22.
 */
public class AirlineAddController extends Controller {
    //Setting up text fields for adding data
    @FXML
    private TextField airlNameAdd;
    @FXML
    private TextField airlAliasAdd;
    @FXML
    private TextField airlIATAAdd;
    @FXML
    private TextField airlICAOAdd;
    @FXML
    private TextField airlCallsignAdd;
    @FXML
    private TextField airlCountryAdd;
    @FXML
    private TextField airlActiveAdd;

    private Dataset theDataSet = null;

    /**
     * Adds a single airline entry to the database.
     * Takes in values from the GUI the user has typed in.
     * @see Dataset
     */
    public void addAirlineSingle() {
        //Tries to add a new airline and clears the fields to their initial state if successful.
        //Otherwise an error message will pop up with what is wrong with the manual data.

        try {
            theDataSet.addAirline(
                    airlNameAdd.getText(),
                    airlAliasAdd.getText(),
                    airlIATAAdd.getText(),
                    airlICAOAdd.getText(),
                    airlCallsignAdd.getText(),
                    airlCountryAdd.getText(),
                    airlActiveAdd.getText());
            airlNameAdd.clear();
            airlAliasAdd.clear();
            airlIATAAdd.clear();
            airlICAOAdd.clear();
            airlCallsignAdd.clear();
            airlCountryAdd.clear();
            airlActiveAdd.clear();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Airline Data Error");
            alert.setHeaderText("Error adding a custom airline entry.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void load() {
        theDataSet = getParent().getCurrentDataset();
    }
}
