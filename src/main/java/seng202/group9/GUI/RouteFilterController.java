package seng202.group9.GUI;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.RouteFilter;
import seng202.group9.Controller.Session;
import seng202.group9.Core.Route;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * The GUI controller class for route_filter_form.fxml.
 * Extends from the abstract class {@link Controller}.
 * Created by Sunguin.
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
    private Session currentSession = null;
    //Sets up a session filter dictionary
    private HashMap<String, String> sesFilter;


    /**
     * Loads up the current dataset and current session.
     */
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

        //Saying to the user that the routes have been successfully filtered.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Route Filter Successful");
        alert.setHeaderText("Route data filtered!");
        alert.setContentText("Your route data has been successfully filtered.");
        alert.showAndWait();

        //Creates a new hashmap for routes and fills it with routes that fit the criteria specified by the user.
        //Saves it into the current session.
        HashMap<Integer, String> routesHM = new HashMap<Integer, String>();
        ArrayList<Route> routes = filter.getFilteredData();
        for (int index = 0; index < routes.size(); index++) {
            routesHM.put(index, routes.get(index).getAirlineName() + routes.get(index).getDepartureAirport() + routes.get(index).getArrivalAirport()
                    + routes.get(index).getCode() + routes.get(index).getStops() + routes.get(index).getEquipment());
        }
        currentSession.setFilteredRoutes(routesHM);

        //Close the popup.
        Stage stage = (Stage) applyButton.getScene().getWindow();
        stage.close();
    }


    /**
     * Resets all the fields in the form to an empty state.
     */
    public void resetForm() {
        rAirlineFilter.clear();
        rSourceFilter.clear();
        rDestFilter.clear();
        rCodeshareFilter.clear();
        rStopsFilter.clear();
        rEquipmentFilter.clear();
    }
}
