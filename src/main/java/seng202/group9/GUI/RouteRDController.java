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
 * Created by Sunguin on 2016/09/14.
 */
public class RouteRDController extends Controller {

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

    private Dataset theDataSet = null;

    /**
     * Loads the initial route data to the GUI table.
     * Also sets up the dropdown menu options.
     */
    public void load() {
        rAirlineCol.setCellValueFactory(new PropertyValueFactory<Route, String>("AirlineName"));
        rAirlineIDCol.setCellValueFactory(new PropertyValueFactory<Route, String>("AirlineID"));
        rSourceCol.setCellValueFactory(new PropertyValueFactory<Route, String>("DepartureAirport"));
        rSourceIDCol.setCellValueFactory(new PropertyValueFactory<Route, String>("SourceID"));
        rDestCol.setCellValueFactory(new PropertyValueFactory<Route, String>("ArrivalAirport"));
        rDestIDCol.setCellValueFactory(new PropertyValueFactory<Route, String>("DestID"));
        rCodeshareCol.setCellValueFactory(new PropertyValueFactory<Route, String>("Code"));
        rStopsCol.setCellValueFactory(new PropertyValueFactory<Route, String>("Stops"));
        rEquipmentCol.setCellValueFactory(new PropertyValueFactory<Route, String>("Equipment"));

        theDataSet = getParent().getCurrentDataset();
        tableViewRouteRD.setItems(FXCollections.observableArrayList(theDataSet.getRoutes()));

        rCodeshareCBox.setValue("");
        rCodeshareCBox.getItems().addAll("Y", "");
    }

    /**
     * Adds a single route entry in the database.
     * Takes in values from the GUI the user has typed in.
     * @see Dataset
     */
    public void addRouteSingle() {
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
        tableViewRouteRD.setItems(FXCollections.<Route>observableArrayList(filter.getFilteredData()));
    }

    public void analyse_Button() {
        replaceSceneContent(SceneCode.ROUTE_ANALYSER);
    }
}
