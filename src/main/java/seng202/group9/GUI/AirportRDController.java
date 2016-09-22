package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group9.Controller.AirportFilter;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Core.Airport;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * The GUI controller class for airport_raw_data.fxml.
 * Extends from the abstract class {@link Controller}.
 * Created by Sunguin
 */
public class AirportRDController extends Controller{
    //Setting up the table from the FXML file
    @FXML
    private TableView<Airport> tableViewAirportRD;
    @FXML
    private TableColumn<Airport, String> airpIDCol;
    @FXML
    private TableColumn<Airport, String> airpNameCol;
    @FXML
    private TableColumn<Airport, String> airpCityCol;
    @FXML
    private TableColumn<Airport, String> airpCountryCol;
    @FXML
    private TableColumn<Airport, String> airpIATAFFACol;
    @FXML
    private TableColumn<Airport, String> airpICAOCol;
    @FXML
    private TableColumn<Airport, String> airpLatitudeCol;
    @FXML
    private TableColumn<Airport, String> airpLongitudeCol;
    @FXML
    private TableColumn<Airport, String> airpAltitudeCol;
    @FXML
    private TableColumn<Airport, String> airpTimezoneCol;
    @FXML
    private TableColumn<Airport, String> airpDSTCol;
    @FXML
    private TableColumn<Airport, String> airpTzCol;

    //Set an empty Dataset to be assigned later
    private Dataset theDataSet = null;

    /**
     * Loads the initial airport data to the GUI table.
     * Also sets up the dropdown menu options.
     */
    public void load() {
        //Sets up the table columns to be ready for use for Airport data
        airpIDCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("ID"));
        airpNameCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Name"));
        airpCityCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("CityName"));
        airpCountryCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("CountryName"));
        airpIATAFFACol.setCellValueFactory(new PropertyValueFactory<Airport, String>("IATA_FFA"));
        airpICAOCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("ICAO"));
        airpLatitudeCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Latitude"));
        airpLongitudeCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Longitude"));
        airpAltitudeCol.setCellValueFactory(new PropertyValueFactory<Airport, String> ("Altitude"));
        airpTimezoneCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Timezone"));
        airpDSTCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("DST"));
        airpTzCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Tz"));

        //Assigning the Dataset to the current Dataset's airports and displaying it in a table
        theDataSet = getParent().getCurrentDataset();
        tableViewAirportRD.setItems(observableArrayList(theDataSet.getAirports()));
    }

    public void openAdd() {
        createPopUpStage(SceneCode.AIRPORT_ADD, 600, 480);
        tableViewAirportRD.setItems(FXCollections.observableArrayList(theDataSet.getAirports()));
    }

    public void openFilter() {
        createPopUpStage(SceneCode.AIRPORT_FILTER, 600, 480);
    }

    /**
     * Deletes a single selected airport entry from the database.
     * Updates the GUI accordingly.
     * @see Dataset
     */
    public void deleteAirport(){
        //Gets an airport from the table and deletes it before updating the table
        Airport toDelete = tableViewAirportRD.getSelectionModel().getSelectedItem();
        theDataSet.deleteAirport(toDelete);
        tableViewAirportRD.setItems(observableArrayList(theDataSet.getAirports()));
    }


    /**
     * Analyses the current data and creates a graph based on the data.
     * @see AirportAnalyser
     */
    public void analyse_Button(){ replaceSceneContent(SceneCode.AIRPORT_ANALYSER);}

    public void airportSummaryButton() {
        replaceSceneContent(SceneCode.AIRPORT_SUMMARY);
    }
}
