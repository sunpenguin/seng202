package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.group9.Controller.AirlineFilter;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.Session;
import seng202.group9.Core.Airline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sunguin on 2016/09/22.
 */
public class AirlineFilterController extends Controller {

    //Setting up text fields for filtering data
    @FXML
    private TextField airlNameFilter;
    @FXML
    private TextField airlAliasFilter;
    @FXML
    private TextField airlIATAFilter;
    @FXML
    private TextField airlICAOFilter;
    @FXML
    private TextField airlCallsignFilter;
    @FXML
    private TextField airlCountryFilter;
    @FXML
    private TextField airlActiveFilter;
    @FXML
    private Button applyButton;

    private Dataset theDataSet = null;
    private Session currentSession = null;

    /**
     * Filters airlines by any field.
     * These are specified by what the user has typed in the filter boxes.
     * Updates the GUI accordingly.
     * @see AirlineFilter
     */
    public void filterAirlines() {
        //The filter function also operates like a search function
        AirlineFilter filter = new AirlineFilter(theDataSet.getAirlines());
        if (airlNameFilter.getText() != null) {
            filter.filterName(airlNameFilter.getText());
        }
        if (airlAliasFilter.getText() != null) {
            filter.filterAlias(airlAliasFilter.getText());
        }
        if (airlIATAFilter.getText() != null) {
            filter.filterIATA(airlIATAFilter.getText());
        }
        if (airlICAOFilter.getText() != null) {
            filter.filterICAO(airlICAOFilter.getText());
        }
        if (airlCallsignFilter.getText() != null) {
            filter.filterCallsign(airlCallsignFilter.getText());
        }
        if (airlCountryFilter.getText() != null) {
            filter.filterCountry(airlCountryFilter.getText());
        }
        if (airlActiveFilter.getText() != null) {
            filter.filterActive(airlActiveFilter.getText());
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Airline Filter Successful");
        alert.setHeaderText("Airline data filtered!");
        alert.setContentText("Your airline data has been successfully filtered.");
        alert.showAndWait();

        //currentSession.setFilteredAirlines(FXCollections.observableArrayList(filter.getFilteredData()));

        HashMap<Integer, String> airlinesHM = new HashMap<Integer, String>();
        ArrayList<Airline> airlines = filter.getFilteredData();
        //for (Airline airline: airlines) {
        for (int index = 0; index < airlines.size(); index++) {
            airlinesHM.put(index, airlines.get(index).getName());
        }
        currentSession.setFilteredAirlines(airlinesHM);

        Stage stage = (Stage) applyButton.getScene().getWindow();
        stage.close();
    }

    public void load() {
        theDataSet = getParent().getCurrentDataset();
        currentSession = getParent().getSession();
    }
}