package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Controller.RouteFilter;
import seng202.group9.Core.Route;

/**
 * The GUI controller class for route_raw_data.fxml.
 * Extends from the abstract class {@link Controller}.
 * Created by Sunguin
 */
public class RouteRDController extends Controller {
    //Setting up the table from the FXML file
    @FXML
    private TableView<Route> tableViewRouteRD;
    @FXML
    private TableColumn<Route, String> rAirlineCol;
    @FXML
    private TableColumn<Route, String> rAirlineIDCol;
    @FXML
    private TableColumn<Route, String> rSourceCol;
    @FXML
    private TableColumn<Route, String> rSourceIDCol;
    @FXML
    private TableColumn<Route, String> rDestCol;
    @FXML
    private TableColumn<Route, String> rDestIDCol;
    @FXML
    private TableColumn<Route, String> rCodeshareCol;
    @FXML
    private TableColumn<Route, String> rStopsCol;
    @FXML
    private TableColumn<Route, String> rEquipmentCol;

    //Setting up text fields for adding data
    @FXML
    private TextField rAirlineBox;
    @FXML
    private TextField rSourceBox;
    @FXML
    private TextField rDestBox;
    @FXML
    private ComboBox<String> rCodeshareCBox;
    @FXML
    private TextField rStopsBox;
    @FXML
    private TextField rEquipmentBox;

    //Setting up text fields for filtering data
    @FXML
    private TextField rAirlineFilter;
    @FXML
    private TextField rSourceFilter;
    @FXML
    private TextField rDestFilter;
    @FXML
    private TextField rCodeshareFilter;
    @FXML
    private TextField rStopsFilter;
    @FXML
    private TextField rEquipmentFilter;

    //Set an empty Dataset to be assigned later
    private Dataset theDataSet = null;

    /**
     * Loads the initial route data to the GUI table.
     * Also sets up the dropdown menu options.
     */
    public void load() {
        //Sets up the table columns to be ready for use for Route data
        rAirlineCol.setCellValueFactory(new PropertyValueFactory<Route, String>("AirlineName"));
        rAirlineIDCol.setCellValueFactory(new PropertyValueFactory<Route, String>("AirlineID"));
        rSourceCol.setCellValueFactory(new PropertyValueFactory<Route, String>("DepartureAirport"));
        rSourceIDCol.setCellValueFactory(new PropertyValueFactory<Route, String>("SourceID"));
        rDestCol.setCellValueFactory(new PropertyValueFactory<Route, String>("ArrivalAirport"));
        rDestIDCol.setCellValueFactory(new PropertyValueFactory<Route, String>("DestID"));
        rCodeshareCol.setCellValueFactory(new PropertyValueFactory<Route, String>("Code"));
        rStopsCol.setCellValueFactory(new PropertyValueFactory<Route, String>("Stops"));
        rEquipmentCol.setCellValueFactory(new PropertyValueFactory<Route, String>("Equipment"));

        //Assigning the Dataset to the current Dataset's routes and displaying it in a table
        theDataSet = getParent().getCurrentDataset();
        tableViewRouteRD.setItems(FXCollections.observableArrayList(theDataSet.getRoutes()));

        //Initializes the value for the drop-down menu for Codeshare for adding a new Route
        rCodeshareCBox.setValue("");
        rCodeshareCBox.getItems().addAll("Y", "");
    }

    /**
     * Adds a single route entry in the database.
     * Takes in values from the GUI the user has typed in.
     * @see Dataset
     */
    public void addRouteSingle() {
        //Tries to add a new route and clears the fields to their initial state if successful.
        //Otherwise an error message will pop up with what is wrong with the manual data.
        try {
            theDataSet.addRoute(
                    rAirlineBox.getText(),
                    rSourceBox.getText(),
                    rDestBox.getText(),
                    rCodeshareCBox.getSelectionModel().getSelectedItem().toString(),
                    rStopsBox.getText(),
                    rEquipmentBox.getText()
            );
            rAirlineBox.clear();
            rSourceBox.clear();
            rDestBox.clear();
            rCodeshareCBox.getSelectionModel().clearSelection();
            rCodeshareCBox.setValue("");
            rStopsBox.clear();
            rEquipmentBox.clear();
            tableViewRouteRD.setItems(FXCollections.observableArrayList(theDataSet.getRoutes()));
        } catch ( Exception e ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Route Data Error");
            alert.setHeaderText("Error adding a custom route entry.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Deletes a single selected route entry from the database.
     * Updates the GUI accordingly.
     * @see Dataset
     */
    public void deleteRoute(){
        //Gets a route from the table and deletes it before updating the table
        Route toDelete = tableViewRouteRD.getSelectionModel().getSelectedItem();
        theDataSet.deleteRoute(toDelete);
        tableViewRouteRD.setItems(FXCollections.observableArrayList(theDataSet.getRoutes()));
    }

    /**
     * Filters the routes table by any field.
     * These are specified by what the user has typed in the filter boxes.
     * Updates the GUI accordingly.
     * @see RouteFilter
     */
    public void filterRoutes(){
        //The filter function also operates like a search function
        RouteFilter filter = new RouteFilter(theDataSet.getRoutes());
        if (rAirlineFilter.getText() != null) {
            filter.filterAirline(rAirlineFilter.getText());
        }
        if (rSourceFilter.getText() != null) {
            filter.filterSourceAirport(rSourceFilter.getText());
        }
        if (rDestFilter.getText() != null) {
            filter.filterDestinationAirport(rDestFilter.getText());
        }
        if (rCodeshareFilter.getText() != null) {
            filter.filterCodeshare(rCodeshareFilter.getText());
        }
        if (rStopsFilter.getText() != null) {
            filter.filterDestinationStops(rStopsFilter.getText());
        }
        if (rEquipmentFilter.getText() != null) {
            filter.filterEquipment(rEquipmentFilter.getText());
        }
        //Sets the data according to the criteria specified by the user
        tableViewRouteRD.setItems(FXCollections.<Route>observableArrayList(filter.getFilteredData()));
    }

    /**
     * Analyses the current data and creates a graph based on the data.
     * @see RouteAnalyser
     */
    public void analyse_Button() {
        replaceSceneContent(SceneCode.ROUTE_ANALYSER);
    }
}
