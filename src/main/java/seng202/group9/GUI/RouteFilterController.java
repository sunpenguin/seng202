package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seng202.group9.Controller.DataException;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.RouteFilter;
import seng202.group9.Controller.Session;
import seng202.group9.Core.Route;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sunguin on 2016/09/23.
 */
public class RouteFilterController extends Controller {
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
    @FXML
    private Button applyButton;
    @FXML
    private GridPane routeContainer;

    private Dataset theDataSet = null;
    //Set an empty session to be assigned to the current session.
    private Session currentSession = null;
    private HashMap<String, String> sesFilter;

    /**
     * Filters the routes table by any field.
     * These are specified by what the user has typed in the filter boxes.
     * Updates the GUI accordingly.
     * @see RouteFilter
     */
    public void filterRoutes(){
        //The filter function also operates like a search function
        RouteFilter filter = new RouteFilter(theDataSet.getRoutes());
        currentSession.setRouteFilter(new HashMap<String, String>());
        if (rAirlineFilter.getText() != null) {
            filter.filterAirline(rAirlineFilter.getText());
            sesFilter.put("Airline", rAirlineFilter.getText());
        }
        if (rSourceFilter.getText() != null) {
            filter.filterSourceAirport(rSourceFilter.getText());
            sesFilter.put("Airline", rSourceFilter.getText());
        }
        if (rDestFilter.getText() != null) {
            filter.filterDestinationAirport(rDestFilter.getText());
            sesFilter.put("Airline", rDestFilter.getText());
        }
        if (rCodeshareFilter.getText() != null) {
            filter.filterCodeshare(rCodeshareFilter.getText());
            sesFilter.put("Airline", rCodeshareFilter.getText());
        }
        if (rStopsFilter.getText() != null) {
            filter.filterDestinationStops(rStopsFilter.getText());
            sesFilter.put("Airline", rStopsFilter.getText());
        }
        if (rEquipmentFilter.getText() != null) {
            filter.filterEquipment(rEquipmentFilter.getText());
            sesFilter.put("Airline", rEquipmentFilter.getText());
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Route Filter Successful");
        alert.setHeaderText("Route data filtered!");
        alert.setContentText("Your route data has been successfully filtered.");
        alert.showAndWait();

        //currentSession.setFilteredAirlines(FXCollections.observableArrayList(filter.getFilteredData()));
        //routeAirline + routeSourceAirport + routeArrvAirport + routeCodeShare + routeStops + routeEquip
        HashMap<Integer, Integer> routesHM = new HashMap<Integer, Integer>();
        ArrayList<Route> routes = filter.getFilteredData();
        for (int index = 0; index < routes.size(); index++) {
            try {
                routesHM.put(index, routes.get(index).getID());
            } catch (DataException e) {
                e.printStackTrace();
            }
        }
        currentSession.setFilteredRoutes(routesHM);

        Stage stage = (Stage) applyButton.getScene().getWindow();
        stage.close();
    }

    public void load() {
        if (!checkDataset()){
            return;
        }
        theDataSet = getParent().getCurrentDataset();
        currentSession = getParent().getSession();
        sesFilter = currentSession.getRouteFilter();
        rAirlineFilter.setText(sesFilter.get("Airline"));
        rSourceFilter.setText(sesFilter.get("Source"));
        rDestFilter.setText(sesFilter.get("Destination"));
        rCodeshareFilter.setText(sesFilter.get("Codeshare"));
        rStopsFilter.setText(sesFilter.get("Stops"));
        rEquipmentFilter.setText(sesFilter.get("Equipment"));
        routeContainer.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    filterRoutes();
                }
            }
        });
    }

    public void resetForm() {
        rAirlineFilter.clear();
        rSourceFilter.clear();
        rDestFilter.clear();
        rCodeshareFilter.clear();
        rStopsFilter.clear();
        rEquipmentFilter.clear();
    }
}
