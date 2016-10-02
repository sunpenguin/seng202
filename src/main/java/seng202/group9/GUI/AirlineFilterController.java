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
import seng202.group9.Controller.AirlineFilter;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.Session;
import seng202.group9.Core.Airline;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * The GUI controller class for airline_filter_form.fxml.
 * Extends from the abstract class {@link Controller}.
 * Created by Sunguin.
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
    @FXML
    private GridPane airlineContainer;

    private Dataset theDataSet = null;
    private Session currentSession = null;
    //Sets up a session filter dictionary
    private HashMap<String,String> sesFilter;


    /**
     * Loads up the current dataset and current session.
     */
    public void load() {
        if (!checkDataset()){
            return;
        }
        theDataSet = getParent().getCurrentDataset();
        currentSession = getParent().getSession();

        airlineContainer.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    filterAirlines();
                }
            }
        });

        sesFilter = currentSession.getAirlineFilter();

        airlNameFilter.setText(sesFilter.get("Name"));
        airlAliasFilter.setText(sesFilter.get("Alias"));
        airlIATAFilter.setText(sesFilter.get("IATA"));
        airlICAOFilter.setText(sesFilter.get("ICAO"));
        airlCallsignFilter.setText(sesFilter.get("Callsign"));
        airlCountryFilter.setText(sesFilter.get("Country"));
        airlActiveFilter.setText(sesFilter.get("Active"));
    }


    /**
     * Filters airlines by any field.
     * These are specified by what the user has typed in the filter boxes.
     * Updates the GUI accordingly.
     * @see AirlineFilter
     */
    public void filterAirlines() {
        //The filter function also operates like a search function
        AirlineFilter filter = new AirlineFilter(theDataSet.getAirlines());
        currentSession.setAirlineFilter(new HashMap<String, String>());
        sesFilter = currentSession.getAirlineFilter();
        if (airlNameFilter.getText() != null) {
            filter.filterName(airlNameFilter.getText());
            sesFilter.put("Name", airlNameFilter.getText());
        }
        if (airlAliasFilter.getText() != null) {
            filter.filterAlias(airlAliasFilter.getText());
            sesFilter.put("Alias", airlAliasFilter.getText());
        }
        if (airlIATAFilter.getText() != null) {
            filter.filterIATA(airlIATAFilter.getText());
            sesFilter.put("IATA", airlIATAFilter.getText());
        }
        if (airlICAOFilter.getText() != null) {
            filter.filterICAO(airlICAOFilter.getText());
            sesFilter.put("ICAO", airlICAOFilter.getText());
        }
        if (airlCallsignFilter.getText() != null) {
            filter.filterCallsign(airlCallsignFilter.getText());
            sesFilter.put("Callsign", airlCallsignFilter.getText());
        }
        if (airlCountryFilter.getText() != null) {
            filter.filterCountry(airlCountryFilter.getText());
            sesFilter.put("Country", airlCountryFilter.getText());
        }
        if (airlActiveFilter.getText() != null) {
            filter.filterActive(airlActiveFilter.getText());
            sesFilter.put("Active", airlActiveFilter.getText());
        }

        //Saying to the user that the airlines have been successfully filtered.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Airline Filter Successful");
        alert.setHeaderText("Airline data filtered!");
        alert.setContentText("Your airline data has been successfully filtered.");
        alert.showAndWait();

        //Creates a new hashmap for airlines and fills it with airlines that fit the criteria specified by the user.
        //Saves it into the current session.
        HashMap<Integer, String> airlinesHM = new HashMap<Integer, String>();
        ArrayList<Airline> airlines = filter.getFilteredData();
        for (int index = 0; index < airlines.size(); index++) {
            airlinesHM.put(index, airlines.get(index).getName());
        }
        currentSession.setFilteredAirlines(airlinesHM);

        //Closes the popup.
        Stage stage = (Stage) applyButton.getScene().getWindow();
        stage.close();
    }


    /**
     * Resets all the fields in the form to an empty state.
     */
    public void resetForm() {
        airlNameFilter.clear();
        airlAliasFilter.clear();
        airlIATAFilter.clear();
        airlICAOFilter.clear();
        airlCallsignFilter.clear();
        airlCountryFilter.clear();
        airlActiveFilter.clear();
    }
}
