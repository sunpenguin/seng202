package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Core.Route;

/**
 * Created by michael on 14/09/2016.
 */
public class RouteSummaryController extends Controller{
    @FXML
    private TableView<Route> tableView;
    @FXML
    private TableColumn<Route, String> columnAirline;
    @FXML
    private TableColumn<Route, String> columnDepart;
    @FXML
    private TableColumn<Route, String> columnArrive;
    @FXML
    private TableColumn<Route, String> columnStops;
    @FXML
    private TableColumn<Route, String> columnEquipment;

    private Dataset currentData = null;

    public void load() {
        columnAirline.setCellValueFactory(new PropertyValueFactory<Route, String>("Airline"));
        columnDepart.setCellValueFactory(new PropertyValueFactory<Route, String>("DepartureAirport"));
        columnArrive.setCellValueFactory(new PropertyValueFactory<Route, String>("ArrivalAirport"));
        columnStops.setCellValueFactory(new PropertyValueFactory<Route, String>("Stops"));
        columnEquipment.setCellValueFactory(new PropertyValueFactory<Route, String>("Equipment"));
        currentData = getParent().getCurrentDataset();
        tableView.setItems(FXCollections.observableArrayList(currentData.getRoutes()));
    }
    public void routeRawDataButton() {
        replaceSceneContent(SceneCode.ROUTE_RAW_DATA);
    }
    public void flightSummaryButton() {
        replaceSceneContent(SceneCode.FLIGHT_SUMMARY);
    }
    public void airportSummaryButton() {
        replaceSceneContent(SceneCode.AIRPORT_SUMMARY);
    }
    public void airlineSummaryButton() {
        replaceSceneContent(SceneCode.AIRLINE_SUMMARY);
    }

}