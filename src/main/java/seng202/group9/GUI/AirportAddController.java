package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.Session;

/**
 * Created by Sunguin on 2016/09/22.
 */
public class AirportAddController extends Controller {
    //Setting up text fields for adding data
    @FXML
    private TextField airpNameAdd;
    @FXML
    private TextField airpCityAdd;
    @FXML
    private TextField airpCountryAdd;
    @FXML
    private TextField airpIATAFAAAdd;
    @FXML
    private TextField airpICAOAdd;
    @FXML
    private TextField airpLatitudeAdd;
    @FXML
    private TextField airpLongitudeAdd;
    @FXML
    private TextField airpAltitudeAdd;
    @FXML
    private TextField airpTimezoneAdd;
    @FXML
    private TextField airpDSTAdd;
    @FXML
    private TextField airpTzAdd;
    @FXML
    private TextField addButton;

    //Set an empty Dataset to be assigned later
    private Dataset theDataSet = null;

    /**
     * Adds a single airport entry in the database.
     * Takes in values from the GUI the user has typed in.
     * @see Dataset
     */
    public void addAirportSingle() {
        //Tries to add a new airport and clears the fields to their initial state if successful.
        //Otherwise an error message will pop up with what is wrong with the manual data.
        try {
            theDataSet.addAirport(
                    airpNameAdd.getText(),
                    airpCityAdd.getText(),
                    airpCountryAdd.getText(),
                    airpIATAFAAAdd.getText(),
                    airpICAOAdd.getText(),
                    airpLatitudeAdd.getText(),
                    airpLongitudeAdd.getText(),
                    airpAltitudeAdd.getText(),
                    airpTimezoneAdd.getText(),
                    airpDSTAdd.getText(),
                    airpTzAdd.getText());
            airpNameAdd.clear();
            airpCityAdd.clear();
            airpCountryAdd.clear();
            airpIATAFAAAdd.clear();
            airpICAOAdd.clear();
            airpLatitudeAdd.clear();
            airpLongitudeAdd.clear();
            airpAltitudeAdd.clear();
            airpTimezoneAdd.clear();
            airpDSTAdd.clear();
            airpTzAdd.clear();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Airport Add Successful");
            alert.setHeaderText("New Airport added!");
            alert.setContentText("Your new airport has been successfully added into the database.");
            alert.showAndWait();

            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();

        } catch ( Exception e ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Airport Data Error");
            alert.setHeaderText("Error adding a custom airport entry.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void load() {
        theDataSet = getParent().getCurrentDataset();
    }
}
