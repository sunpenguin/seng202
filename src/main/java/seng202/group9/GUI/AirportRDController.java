package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Controller.Session;
import seng202.group9.Core.Airport;

import java.util.ArrayList;
import java.util.Optional;

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
    //Set an empty session to be assigned to the current session.
    private Session currentSession = null;

    /**
     * Loads the initial airport data to the GUI table.
     * Also sets up the dropdown menu options.
     */
    public void load() {
        if (!checkDataset()){
            return;
        }
        theDataSet = getParent().getCurrentDataset();
        //Sets up the table columns to be ready for use for Airport data
        airpIDCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("ID"));
        airpNameCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Name"));
        airpCityCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("CityName"));
        airpCountryCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("CountryName"));
        airpIATAFFACol.setCellValueFactory(new PropertyValueFactory<Airport, String>("IATA_FFA"));
        airpICAOCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("ICAO"));
        airpLatitudeCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Latitude"));
        airpLongitudeCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Longitude"));
        airpAltitudeCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Altitude"));
        airpTimezoneCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Timezone"));
        airpDSTCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("DST"));
        airpTzCol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Tz"));

        //Assigning the Dataset to the current Dataset's airports and displaying it in a table
        currentSession = getParent().getSession();

        tableViewAirportRD.setItems(observableArrayList(theDataSet.getAirports()));
        tableViewAirportRD.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void openAdd() {
        createPopUpStage(SceneCode.AIRPORT_ADD, 600, 480);
        tableViewAirportRD.setItems(FXCollections.observableArrayList(theDataSet.getAirports()));
    }

    public void openFilter() {
        createPopUpStage(SceneCode.AIRPORT_FILTER, 600, 480);
        ArrayList<Airport> d = new ArrayList();
        for (int key: currentSession.getFilteredAirports().keySet()){
            d.add(theDataSet.getAirportDictionary().get(currentSession.getFilteredAirports().get(key)));
        }
        tableViewAirportRD.setItems(FXCollections.observableArrayList(d));
    }

    /**
     * Deletes a single selected airport entry from the database.
     * Updates the GUI accordingly.
     * @see Dataset
     */
    public void deleteAirport(){
        //Gets an airport from the table and deletes it before updating the table
        ObservableList<Airport> toDelete = tableViewAirportRD.getSelectionModel().getSelectedItems();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Airport Delete Confirmation");
        alert.setHeaderText("You are about to delete some data.");
        alert.setContentText("Are you sure you want to delete the selected airport(s)?");
        Optional<ButtonType> result = alert.showAndWait();
        Airport air = null;
        if (result.isPresent() && result.get() == ButtonType.OK) {
            for (int i = 0; i < toDelete.size(); i++) {
                air = toDelete.get(i);
                theDataSet.deleteAirport(air);
            }
            tableViewAirportRD.setItems(FXCollections.observableArrayList(theDataSet.getAirports()));
        }
    }

    public void editAirport() {
        Airport toEdit = tableViewAirportRD.getSelectionModel().getSelectedItem();
        currentSession.setAirportToEdit(toEdit.getName());
        createPopUpStage(SceneCode.AIRPORT_EDIT, 600, 480);
        tableViewAirportRD.refresh();
    }

    /**
     * Analyses the current data and creates a graph based on the data.
     *
     */
    public void analyse_Button(){ replaceSceneContent(SceneCode.AIRPORT_GRAPHS);}

    public void airportSummaryButton() {
        replaceSceneContent(SceneCode.AIRPORT_SUMMARY);
    }

    /**
     * Opens a map with the data currently being displayed in the table.
     */
    public void openMap() {
        createPopUpStage(SceneCode.POP_UP_AIRPORT_MAP, 600, 400);
    }
}
