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
public class RouteSummaryController extends MenuController{
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

    App parent;

    public void setApp(App parent){
        this.parent = parent;
    }

    public void loadTables() {
        columnAirline.setCellValueFactory(new PropertyValueFactory<Route, String>("Airline"));
        columnDepart.setCellValueFactory(new PropertyValueFactory<Route, String>("DepartureAirport"));
        columnArrive.setCellValueFactory(new PropertyValueFactory<Route, String>("ArrivalAirport"));
        columnStops.setCellValueFactory(new PropertyValueFactory<Route, String>("Stops"));
        columnEquipment.setCellValueFactory(new PropertyValueFactory<Route, String>("Equipment"));
        currentData = this.parent.getCurrentDataset();
        tableView.setItems(FXCollections.observableArrayList(currentData.getRoutes()));
    }
    public void routeRawDataButton() {
        try {
            RouteRDController rawDataController = (RouteRDController)
                    parent.replaceSceneContent(SceneCode.ROUTE_RAW_DATA);
            rawDataController.setApp(parent);
            rawDataController.loadTables();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void flightSummaryButton() {
        try {
            FlightSummaryController summaryController = (FlightSummaryController)
                    parent.replaceSceneContent(SceneCode.FLIGHT_SUMMARY);
            summaryController.setApp(parent);
            summaryController.flightPathListView();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void airportSummaryButton() {
        try {
            AirportSummaryController summaryController = (AirportSummaryController)
                    parent.replaceSceneContent(SceneCode.AIRPORT_SUMMARY);
            summaryController.setApp(parent);
            summaryController.loadTables();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void airlineSummaryButton() {
        try {
            AirlineSummaryController summaryController = (AirlineSummaryController)
                    parent.replaceSceneContent(SceneCode.AIRLINE_SUMMARY);
            summaryController.setApp(parent);
            summaryController.loadTables();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}