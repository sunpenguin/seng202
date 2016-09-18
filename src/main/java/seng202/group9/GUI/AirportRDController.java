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
    private TableColumn<Airport, String> airpIDcol;
    @FXML
    private TableColumn<Airport, String> airpNamecol;
    @FXML
    private TableColumn<Airport, String> airpCitycol;
    @FXML
    private TableColumn<Airport, String> airpCountrycol;
    @FXML
    private TableColumn<Airport, String> airpIATAFFAcol;
    @FXML
    private TableColumn<Airport, String> airpICAOcol;
    @FXML
    private TableColumn<Airport, String> airpLatitudecol;
    @FXML
    private TableColumn<Airport, String> airpLongitudecol;
    @FXML
    private TableColumn<Airport, String> airpAltitudecol;
    @FXML
    private TableColumn<Airport, String> airpTimezonecol;
    @FXML
    private TableColumn<Airport, String> airpDSTcol;
    @FXML
    private TableColumn<Airport, String> airpTzcol;

    //Setting up text fields for adding data
    @FXML
    private TextField airpNameBox;
    @FXML
    private TextField airpCityBox;
    @FXML
    private TextField airpCountryBox;
    @FXML
    private TextField airpIATAFFABox;
    @FXML
    private TextField airpICAOBox;
    @FXML
    private TextField airpLatitudeBox;
    @FXML
    private TextField airpLongitudeBox;
    @FXML
    private TextField airpAltitudeBox;
    @FXML
    private TextField airpTimezoneBox;
    @FXML
    private ComboBox<String> airpDSTCBox;
    @FXML
    private TextField airpTzBox;

    //Setting up text fields for filtering data
    @FXML
    private TextField airpNameFilter;
    @FXML
    private TextField airpCityFilter;
    @FXML
    private TextField airpCountryFilter;
    @FXML
    private TextField airpIATAFFAFilter;
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

    //Set an empty Dataset to be assigned later
    private Dataset theDataSet = null;

    /**
     * Loads the initial airport data to the GUI table.
     * Also sets up the dropdown menu options.
     */
    public void load() {
        //Sets up the table columns to be ready for use for Airport data
        airpIDcol.setCellValueFactory(new PropertyValueFactory<Airport, String>("ID"));
        airpNamecol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Name"));
        airpCitycol.setCellValueFactory(new PropertyValueFactory<Airport, String>("CityName"));
        airpCountrycol.setCellValueFactory(new PropertyValueFactory<Airport, String>("CountryName"));
        airpIATAFFAcol.setCellValueFactory(new PropertyValueFactory<Airport, String>("IATA_FFA"));
        airpICAOcol.setCellValueFactory(new PropertyValueFactory<Airport, String>("ICAO"));
        airpLatitudecol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Latitude"));
        airpLongitudecol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Longitude"));
        airpAltitudecol.setCellValueFactory(new PropertyValueFactory<Airport, String> ("Altitude"));
        airpTimezonecol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Timezone"));
        airpDSTcol.setCellValueFactory(new PropertyValueFactory<Airport, String>("DST"));
        airpTzcol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Tz"));

        //Assigning the Dataset to the current Dataset's airports and displaying it in a table
        theDataSet = getParent().getCurrentDataset();
        tableViewAirportRD.setItems(observableArrayList(theDataSet.getAirports()));


        airpDSTCBox.setValue("E");//Initializes the value for the drop-down menu for DST for adding a new Airport
        airpDSTCBox.getItems().addAll("E", "A", "S", "O", "Z", "N", "U");
    }

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
                    airpNameBox.getText(),
                    airpCityBox.getText(),
                    airpCountryBox.getText(),
                    airpIATAFFABox.getText(),
                    airpICAOBox.getText(),
                    airpLatitudeBox.getText(),
                    airpLongitudeBox.getText(),
                    airpAltitudeBox.getText(),
                    airpTimezoneBox.getText(),
                    airpDSTCBox.getSelectionModel().getSelectedItem().toString(),
                    airpTzBox.getText());
                airpCityBox.clear();
                airpCountryBox.clear();
                airpIATAFFABox.clear();
                airpICAOBox.clear();
                airpLatitudeBox.clear();
                airpLongitudeBox.clear();
                airpAltitudeBox.clear();
                airpTimezoneBox.clear();
                airpDSTCBox.getSelectionModel().clearSelection();
                airpDSTCBox.setValue("E");
                airpTzBox.clear();
            tableViewAirportRD.setItems(FXCollections.observableArrayList(theDataSet.getAirports()));
        } catch ( Exception e ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Airport Data Error");
            alert.setHeaderText("Error adding a custom airport entry.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
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
        if (airpIATAFFAFilter.getText() != null) {
            filter.filterIATA_FFA(airpIATAFFAFilter.getText());
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
        //Sets the data according to the criteria specified by the user
        tableViewAirportRD.setItems(FXCollections.<Airport>observableArrayList(filter.getFilteredData()));
    }

    /**
     * Analyses the current data and creates a graph based on the data.
     * @see AirportAnalyser
     */
    public void analyse_Button(){ replaceSceneContent(SceneCode.AIRPORT_ANALYSER);}
}
