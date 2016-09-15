package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.Airline;

/**
 * Created by michael on 14/09/2016.
 */
public class AirlineSummaryController extends MenuController{
    @FXML
    private TableView<Airline> tableView;
    @FXML
    private TableColumn<Airline, String> columnName;
    @FXML
    private TableColumn<Airline, String> columnAlias;
    @FXML
    private TableColumn<Airline, String> columnCountry;
    @FXML
    private TableColumn<Airline, String> columnActive;
    @FXML
    private TableColumn<Airline, String> columnIATA;

    private Dataset currentData = null;

    App parent;

    public void setApp(App parent){
        this.parent = parent;
    }

    public void loadTables() {
        columnName.setCellValueFactory(new PropertyValueFactory<Airline, String>("Name"));
        columnAlias.setCellValueFactory(new PropertyValueFactory<Airline, String>("Alias"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<Airline, String>("Country"));
        columnIATA.setCellValueFactory(new PropertyValueFactory<Airline, String>("IATA"));
        columnActive.setCellValueFactory(new PropertyValueFactory<Airline, String>("Active"));
        currentData = this.parent.getCurrentDataset();
        tableView.setItems(FXCollections.observableArrayList(currentData.getAirlines()));
    }
    public void airlineRawDataButton() {
        try {
            AirlineRDController rawDataController = (AirlineRDController)
                    parent.replaceSceneContent("airline_raw_data.fxml");
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
                    parent.replaceSceneContent("flight_data_summary.fxml");
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
                    parent.replaceSceneContent("airport_summary.fxml");
            summaryController.setApp(parent);
            summaryController.loadTables();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void routeSummaryButton() {
        try {
            RouteSummaryController summaryController = (RouteSummaryController)
                    parent.replaceSceneContent("routes_summary.fxml");
            summaryController.setApp(parent);
            summaryController.loadTables();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
