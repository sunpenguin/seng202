package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.group9.Controller.AirportFilter;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.Session;
import seng202.group9.Core.Airport;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sunguin on 2016/09/22.
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

    //Set an empty Dataset to be assigned later
    private Dataset theDataSet = null;
    //Set an empty session to be assigned to the current session.
    private Session currentSession = null;

    /**
     * Filters the airports table by any field.
     * These are specified by what the user has typed in the filter boxes.
     * Updates the GUI accordingly.
     * @see AirportFilter
     */
    public void filterAirports() {
        //The filter function also operates like a search function
        AirportFilter filter = new AirportFilter(theDataSet.getAirports());
        if (airpNameFilter.getText() != null) {
            filter.filterName(airpNameFilter.getText());
        }
        if (airpCityFilter.getText() != null) {
            filter.filterCity(airpCityFilter.getText());
        }
        if (airpCountryFilter.getText() != null) {
            filter.filterCountry(airpCountryFilter.getText());
        }
        if (airpIATAFAAFilter.getText() != null) {
            filter.filterIATA_FFA(airpIATAFAAFilter.getText());
        }
        if (airpICAOFilter.getText() != null) {
            filter.filterICAO(airpICAOFilter.getText());
        }
        if (airpLatitudeFilter.getText() != null) {
            filter.filterLatitude(airpLatitudeFilter.getText());
        }
        if (airpLongitudeFilter.getText() != null) {
            filter.filterLongitude(airpLongitudeFilter.getText());
        }
        if (airpAltitudeFilter.getText() != null) {
            filter.filterAltitude(airpAltitudeFilter.getText());
        }
        if (airpTimezoneFilter.getText() != null) {
            filter.filterTimezone(airpTimezoneFilter.getText());
        }
        if (airpDSTFilter.getText() != null) {
            filter.filterDST(airpDSTFilter.getText());
        }
        if (airpTzFilter.getText() != null) {
            filter.filterOlson(airpTzFilter.getText());
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Airline Filter Successful");
        alert.setHeaderText("Airline data filtered!");
        alert.setContentText("Your airline data has been successfully filtered.");
        alert.showAndWait();

        //currentSession.setFilteredAirlines(FXCollections.observableArrayList(filter.getFilteredData()));

        HashMap<Integer, String> airportsHM = new HashMap<Integer, String>();
        ArrayList<Airport> airports = filter.getFilteredData();
        for (int index = 0; index < airports.size(); index++) {
            airportsHM.put(index, airports.get(index).getName());
        }
        currentSession.setFilteredAirports(airportsHM);

        Stage stage = (Stage) applyButton.getScene().getWindow();
        stage.close();
    }

    public void load() {
        theDataSet = getParent().getCurrentDataset();
        currentSession = getParent().getSession();
    }
}
