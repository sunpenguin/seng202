package seng202.group9.GUI;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seng202.group9.Controller.Dataset;


/**
 * The GUI controller class for airport_add_form.fxml.
 * Extends from the abstract class {@link Controller}
 * Created by Sunguin.
 */
public class AirportAddController extends Controller {
    //Setting up text fields for adding data.
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
    private Button addButton;
    @FXML
    private GridPane airportContainer;

    private Dataset theDataSet = null;


    /**
     * Loads up the current dataset.
     */
    public void load() {
        theDataSet = getParent().getCurrentDataset();

        airportContainer.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    addAirportSingle();
                }
            }
        });
    }


    /**
     * Adds a single airport entry in the database.
     * Takes in values from the GUI the user has typed in.
     * @see Dataset
     */
    public void addAirportSingle() {
        //Tries to add a new airport to the current dataset.
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

            //Saying to the user that the airport has successfully added.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Airport Add Successful");
            alert.setHeaderText("New Airport added!");
            alert.setContentText("Your new airport has been successfully added into the database.");
            alert.showAndWait();

            //Close the add form
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        } catch ( Exception e ) {
            //Tells the user what and where the error is.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Airport Data Error");
            alert.setHeaderText("Error adding a custom airport entry.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
