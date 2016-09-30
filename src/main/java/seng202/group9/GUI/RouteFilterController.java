package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.RouteFilter;
import seng202.group9.Controller.Session;
import seng202.group9.Core.Route;

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

    private Dataset theDataSet = null;
    //Set an empty session to be assigned to the current session.
    private Session currentSession = null;

    /**
     * Filters the routes table by any field.
     * These are specified by what the user has typed in the filter boxes.
     * Updates the GUI accordingly.
     * @see RouteFilter
     */
    public void filterRoutes(){
        //The filter function also operates like a search function
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Route Filter Successful");
        alert.setHeaderText("Route data filtered!");
        alert.setContentText("Your route data has been successfully filtered.");
        alert.showAndWait();

        //currentSession.setFilteredAirlines(FXCollections.observableArrayList(filter.getFilteredData()));
        //routeAirline + routeSourceAirport + routeArrvAirport + routeCodeShare + routeStops + routeEquip
        HashMap<Integer, String> routesHM = new HashMap<Integer, String>();
        ArrayList<Route> routes = filter.getFilteredData();
        for (int index = 0; index < routes.size(); index++) {
            routesHM.put(index, routes.get(index).getAirlineName() + routes.get(index).getDepartureAirport() + routes.get(index).getArrivalAirport()
                    + routes.get(index).getCode() + routes.get(index).getStops() + routes.get(index).getEquipment());
        }
        currentSession.setFilteredRoutes(routesHM);

        Stage stage = (Stage) applyButton.getScene().getWindow();
        stage.close();
    }

    public void load() {
        theDataSet = getParent().getCurrentDataset();
        currentSession = getParent().getSession();
    }
}
