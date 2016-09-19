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
 * Controller for the Airport summary scene.
 * Created by michael on 14/09/2016.
 */
public class AirportSummaryController extends Controller{
    //links controller to fxml.
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

    //Stores required data.
    private Dataset currentData = null;
    private Map map;

    /**
     * Changes the scene to the Airport raw data scene.
     */
    public void airportRawDataButton() {
        replaceSceneContent(SceneCode.AIRPORT_RAW_DATA);
    }

    /**
     * Changes the scene to the flight summary scene.
     */
    public void flightSummaryButton() {
        replaceSceneContent(SceneCode.FLIGHT_SUMMARY);
    }

    /**
     * Changes the scene to the Route summary scene.
     */
    public void routeSummaryButton() {
        replaceSceneContent(SceneCode.ROUTE_SUMMARY);
    }

    /**
     * Changes the scene to the airline summary scene.
     */
    public void airlineSummaryButton() {
        replaceSceneContent(SceneCode.AIRLINE_SUMMARY);
    }

    /**
     * Loads initial state of the scene.
     */
    public void load() {
        currentData = getParent().getCurrentDataset();
        columnName.setCellValueFactory(new PropertyValueFactory<Airport, String>("Name"));
        columnCity.setCellValueFactory(new PropertyValueFactory<Airport, String>("CityName"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<Airport, String>("CountryName"));
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