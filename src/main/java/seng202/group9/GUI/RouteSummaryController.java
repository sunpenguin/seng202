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
import seng202.group9.Core.Position;
import seng202.group9.Core.Route;
import seng202.group9.Core.RoutePath;
import seng202.group9.Map.Map;

/**
 * Created by michael on 14/09/2016.
 */
public class RouteSummaryController extends Controller{
    @FXML
    private TableView<Route> tableView;
    @FXML
    private WebView mapView;
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

    private Map map;

    private Dataset currentData = null;

    public void load() {
        columnAirline.setCellValueFactory(new PropertyValueFactory<Route, String>("AirlineName"));
        columnDepart.setCellValueFactory(new PropertyValueFactory<Route, String>("DepartureAirport"));
        columnArrive.setCellValueFactory(new PropertyValueFactory<Route, String>("ArrivalAirport"));
        columnStops.setCellValueFactory(new PropertyValueFactory<Route, String>("Stops"));
        columnEquipment.setCellValueFactory(new PropertyValueFactory<Route, String>("Equipment"));
        currentData = getParent().getCurrentDataset();
        tableView.setItems(FXCollections.observableArrayList(currentData.getRoutes()));
        map = new Map(mapView, new RoutePath());
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Route>() {
            public void changed(ObservableValue<? extends Route> observable, Route oldValue, Route newValue) {
                System.out.println("loading");
                Route selectedRoute= currentData.getRoutes().get(tableView.getSelectionModel().getSelectedIndices().get(0));
                if (selectedRoute.getSourceAirport() != null && selectedRoute.getDestinationAirport() != null) {
                    map.displayRoute(new RoutePath(
                            new Position(selectedRoute.getSourceAirport().getLatitude(), selectedRoute.getSourceAirport().getLongitude()),
                            new Position(selectedRoute.getDestinationAirport().getLatitude(), selectedRoute.getDestinationAirport().getLongitude())
                    ));
                }else if (selectedRoute.getSourceAirport() == null && selectedRoute.getDestinationAirport() != null){
                    map.displayAirport(new RoutePath(
                            new Position(selectedRoute.getDestinationAirport().getLatitude(), selectedRoute.getDestinationAirport().getLongitude())
                    ));
                }else if (selectedRoute.getSourceAirport() != null && selectedRoute.getDestinationAirport() == null){
                    map.displayAirport(new RoutePath(
                            new Position(selectedRoute.getSourceAirport().getLatitude(), selectedRoute.getSourceAirport().getLongitude())
                    ));
                }
            }
        });
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