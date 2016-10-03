package seng202.group9.GUI;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.*;
import seng202.group9.Map.Map;

import java.util.*;

/**
 * Created by fwy13 on 1/10/16.
 */
public class AirportRouteMapController extends Controller{
    @FXML
    WebView mapView;
    @FXML
    TableView airportsTable;
    @FXML
    TableColumn<Airport, String> airportName;
    @FXML
    TableColumn<Airport, Integer> routes;
    ObservableList<Airport> airportsToDisplay;
    Dataset currentDataset;
    Map map;

    /**
     * load initial route map and table
     */
    @Override
    public void load() {
        if (!checkDataset()){
            return;
        }
        currentDataset = getParent().getCurrentDataset();
        //Sets up map.
        map = new Map(mapView, new RoutePath(), airportsTable);
        airportName.setCellValueFactory(new PropertyValueFactory<Airport, String>("Name"));
        routes.setCellValueFactory(new PropertyValueFactory<Airport, Integer>("TotalRoutes"));
        airportsToDisplay = FXCollections.observableArrayList();
        for (Airport airport: currentDataset.getAirports()){
            if (airport.getTotalRoutes() > 0) {
                airportsToDisplay.add(airport);
            }
        }
        airportsTable.setItems(airportsToDisplay);
        airportsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Airport>() {
            public void changed(ObservableValue<? extends Airport> observable, Airport oldValue, Airport newValue) {
                Airport selectedAirport= (Airport) airportsTable.getSelectionModel().getSelectedItems().get(0);

                ArrayList<RoutePath> routePaths = new ArrayList<RoutePath>();
                for (Route route:selectedAirport.getArrivalRoutes()){
                    routePaths.add(route.getRoutePath());
                }
                for (Route route:selectedAirport.getDepartureRoutes()){
                    routePaths.add(route.getRoutePath());
                }
                map.displayRoutes(routePaths);
            }
        });
    }
}
