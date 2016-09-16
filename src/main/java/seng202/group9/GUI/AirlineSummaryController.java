package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Core.Airline;

/**
 * Created by michael on 14/09/2016.
 */
public class AirlineSummaryController extends Controller{
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

    public void load() {
        columnName.setCellValueFactory(new PropertyValueFactory<Airline, String>("Name"));
        columnAlias.setCellValueFactory(new PropertyValueFactory<Airline, String>("Alias"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<Airline, String>("Country"));
        columnIATA.setCellValueFactory(new PropertyValueFactory<Airline, String>("IATA"));
        columnActive.setCellValueFactory(new PropertyValueFactory<Airline, String>("Active"));
        currentData = getParent().getCurrentDataset();
        tableView.setItems(FXCollections.observableArrayList(currentData.getAirlines()));
    }

    public void airlineRawDataButton() {
        replaceSceneContent(SceneCode.AIRLINE_RAW_DATA);
    }
    public void flightSummaryButton() {
        replaceSceneContent(SceneCode.FLIGHT_SUMMARY);
    }
    public void airportSummaryButton() {
        replaceSceneContent(SceneCode.AIRPORT_SUMMARY);
    }
    public void routeSummaryButton() {
        replaceSceneContent(SceneCode.ROUTE_SUMMARY);
    }
}
