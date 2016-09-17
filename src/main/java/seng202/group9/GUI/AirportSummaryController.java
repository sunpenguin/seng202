package seng202.group9.GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Core.Airport;
import seng202.group9.Core.Position;
import seng202.group9.Core.RoutePath;
import seng202.group9.Map.Map;

/**
 * Created by michael on 14/09/2016.
 */
public class AirportSummaryController extends Controller{
    @FXML
    private TableView<Airport> tableView;
    @FXML
    private WebView mapView;
    @FXML
    private TableColumn<Airport, String> columnName;
    @FXML
    private TableColumn<Airport, String> columnCity;
    @FXML
    private TableColumn<Airport, String> columnCountry;
    @FXML
    private TableColumn<Airport, String> columnAltitude;
    @FXML
    private TableColumn<Airport, String> columnIATA;

    private Dataset currentData = null;

    private Map map;

    public void airportRawDataButton() {
        replaceSceneContent(SceneCode.AIRLINE_RAW_DATA);
    }
    public void flightSummaryButton() {
        replaceSceneContent(SceneCode.FLIGHT_SUMMARY);
    }
    public void routeSummaryButton() {
        replaceSceneContent(SceneCode.ROUTE_SUMMARY);
    }
    public void airlineSummaryButton() {
        replaceSceneContent(SceneCode.AIRLINE_SUMMARY);
    }

    public void load() {
        currentData = getParent().getCurrentDataset();
        columnName.setCellValueFactory(new PropertyValueFactory<Airport, String>("Name"));
        columnCity.setCellValueFactory(new PropertyValueFactory<Airport, String>("City"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<Airport, String>("Country"));
        columnIATA.setCellValueFactory(new PropertyValueFactory<Airport, String>("IATA_FFA"));
        columnAltitude.setCellValueFactory(new PropertyValueFactory<Airport, String>("Altitude"));
        currentData = getParent().getCurrentDataset();
        tableView.setItems(FXCollections.observableArrayList(currentData.getAirports()));
        map = new Map(mapView, new RoutePath());
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Airport>() {
            public void changed(ObservableValue<? extends Airport> observable, Airport oldValue, Airport newValue) {
                System.out.println("loading");
                Airport selectedAirport= currentData.getAirports().get(tableView.getSelectionModel().getSelectedIndices().get(0));
                map.displayAirport(new RoutePath( new Position(selectedAirport.getLatitude(), selectedAirport.getLongitude())));
            }
        });
    }
}