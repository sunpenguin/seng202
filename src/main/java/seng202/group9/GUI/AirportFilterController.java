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
import seng202.group9.Controller.AirportFilter;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.Session;
import seng202.group9.Core.Airport;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * The GUI controller class for airport_filter_form.fxml.
 * Extends from the abstract class {@link Controller}.
 * Created by Sunguin.
 */
public class AirportFilterController extends Controller {
    //Setting up text fields for filtering data
    @FXML
    private TextField airpNameFilter;
    @FXML
    private TextField airpCityFilter;
    @FXML
    private TextField airpCountryFilter;
    @FXML
    private TextField airpIATAFAAFilter;
    @FXML
    private TextField airpICAOFilter;
    @FXML
    private TextField airpLatitudeFilter;
    @FXML
    private TextField airpLongitudeFilter;
    @FXML
    private TextField airpAltitudeFilter;
    @FXML
    private TextField airpTimezoneFilter;
    @FXML
    private TextField airpDSTFilter;
    @FXML
    private TextField airpTzFilter;
    @FXML
    private Button applyButton;
    @FXML
    private GridPane airportContainer;

    private Dataset theDataSet = null;
    private Session currentSession = null;
    //Sets up a session filter dictionary
    private HashMap<String, String> sesFilter;


    /**
     * Loads up the current dataset and current session.
     */
    public void load() {
        if (!checkDataset()){
            return;
        }
        theDataSet = getParent().getCurrentDataset();
        currentSession = getParent().getSession();
        sesFilter = currentSession.getAirportFilter();

        airpNameFilter.setText(sesFilter.get("Name"));
        airpCityFilter.setText(sesFilter.get("City"));
        airpCountryFilter.setText(sesFilter.get("Country"));
        airpIATAFAAFilter.setText(sesFilter.get("IATA/FFA"));
        airpICAOFilter.setText(sesFilter.get("ICAO"));
        airpLatitudeFilter.setText(sesFilter.get("Latitude"));
        airpLongitudeFilter.setText(sesFilter.get("Longitude"));
        airpAltitudeFilter.setText(sesFilter.get("Altitude"));
        airpTimezoneFilter.setText(sesFilter.get("Tz"));
        airpDSTFilter.setText(sesFilter.get("DST"));
        airpTzFilter.setText(sesFilter.get("Olson"));
        airportContainer.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    filterAirports();
                }
            }
        });
    }


    /**
     * Filters the airports table by any field.
     * These are specified by what the user has typed in the filter boxes.
     * Updates the GUI accordingly.
     * @see AirportFilter
     */
    public void filterAirports() {
        //The filter function also operates like a search function
        AirportFilter filter = new AirportFilter(theDataSet.getAirports());
        currentSession.setAirportFilter(new HashMap<String, String>());
        sesFilter = currentSession.getAirportFilter();
        if (airpNameFilter.getText() != null) {
            filter.filterName(airpNameFilter.getText());
            sesFilter.put("Name", airpNameFilter.getText());
        }
        if (airpCityFilter.getText() != null) {
            filter.filterCity(airpCityFilter.getText());
            sesFilter.put("City", airpCityFilter.getText());
        }
        if (airpCountryFilter.getText() != null) {
            filter.filterCountry(airpCountryFilter.getText());
            sesFilter.put("Country", airpCountryFilter.getText());
        }
        if (airpIATAFAAFilter.getText() != null) {
            filter.filterIATA_FFA(airpIATAFAAFilter.getText());
            sesFilter.put("IATA/FFA", airpIATAFAAFilter.getText());
        }
        if (airpICAOFilter.getText() != null) {
            filter.filterICAO(airpICAOFilter.getText());
            sesFilter.put("ICAO", airpICAOFilter.getText());
        }
        if (airpLatitudeFilter.getText() != null) {
            filter.filterLatitude(airpLatitudeFilter.getText());
            sesFilter.put("Latitude", airpLatitudeFilter.getText());
        }
        if (airpLongitudeFilter.getText() != null) {
            filter.filterLongitude(airpLongitudeFilter.getText());
            sesFilter.put("Longitude", airpLongitudeFilter.getText());
        }
        if (airpAltitudeFilter.getText() != null) {
            filter.filterAltitude(airpAltitudeFilter.getText());
            sesFilter.put("Altitude", airpAltitudeFilter.getText());
        }
        if (airpTimezoneFilter.getText() != null) {
            filter.filterTimezone(airpTimezoneFilter.getText());
            sesFilter.put("Tz", airpTimezoneFilter.getText());
        }
        if (airpDSTFilter.getText() != null) {
            filter.filterDST(airpDSTFilter.getText());
            sesFilter.put("DST", airpDSTFilter.getText());
        }
        if (airpTzFilter.getText() != null) {
            filter.filterOlson(airpTzFilter.getText());
            sesFilter.put("Olson", airpTzFilter.getText());
        }

        //Saying to the user that the airports have been successfully filtered.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Airport Filter Successful");
        alert.setHeaderText("Airport data filtered!");
        alert.setContentText("Your airport data has been successfully filtered.");
        alert.showAndWait();

        //Creates a new hashmap for airports and fills it with airports that fit the criteria specified by the user.
        //Saves it into the current session.
        HashMap<Integer, String> airportsHM = new HashMap<Integer, String>();
        ArrayList<Airport> airports = filter.getFilteredData();
        for (int index = 0; index < airports.size(); index++) {
            airportsHM.put(index, airports.get(index).getName());
        }
        currentSession.setFilteredAirports(airportsHM);

        //Closes the popup.
        Stage stage = (Stage) applyButton.getScene().getWindow();
        stage.close();
    }


    /**
     * Resets all the fields in the form to an empty state.
     */
    public void resetForm() {
        airpNameFilter.clear();
        airpCityFilter.clear();
        airpCountryFilter.clear();
        airpIATAFAAFilter.clear();
        airpICAOFilter.clear();
        airpLatitudeFilter.clear();
        airpLongitudeFilter.clear();
        airpAltitudeFilter.clear();
        airpTimezoneFilter.clear();
        airpDSTFilter.clear();
        airpTzFilter.clear();
    }
}
