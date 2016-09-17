package seng202.group9.GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Core.FlightPath;
import seng202.group9.Core.RoutePath;
import seng202.group9.Map.Map;
import seng202.group9.Core.FlightPoint;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for the Flights Summary Scene.
 * Created by Liam Beckett on 13/09/2016.
 */
public class FlightSummaryController extends Controller {

    private Dataset theDataSet = null;

    private int currentPathId = 0;
    private int currentPathIndex = 0;

    @FXML
    private Button flightRawData;
    private Map map;
    @FXML
    private WebView mapView;
    @FXML
    ListView<String> flightPathListView;
    final ObservableList<String> flightList = FXCollections.observableArrayList();

    /**
     * Changes to the Flight Raw Data Scene when the Raw Data Button is clicked.
     */
    public void handleRawDataButton() {
        replaceSceneContent(SceneCode.FLIGHT_RAW_DATA);
    }

    /**
     * Changes to the Airport Summary Scene when the Airport is clicked.
     */
    public void airportSummaryButton() { replaceSceneContent(SceneCode.AIRPORT_SUMMARY); }

    /**
     * Changes to the Route Summary Scene when the Route Button is clicked.
     */
    public void routeSummaryButton() {
        replaceSceneContent(SceneCode.ROUTE_SUMMARY);
    }

    /**
     * Changes to the Airline Summary Scene when the Airline Button is clicked.
     */
    public void airlineSummaryButton() {
        replaceSceneContent(SceneCode.AIRLINE_SUMMARY);
    }

    /**
     * Loads the Flight paths into the List View and waits for a mouse clicked event for which it will update the table
     * to display the selected Flight paths points. Called from the MenuController.
     */
    public void flightPathListView() {
        try {
            ArrayList<FlightPath> flightPaths;
            flightPaths = theDataSet.getFlightPaths();
            for(int i = 0; i<flightPaths.size(); i++ ) {
                int pathID = flightPaths.get(i).getID();
                String pathSource = flightPaths.get(i).departsFrom();
                String pathDestin = flightPaths.get(i).arrivesAt();
                String flightPathDisplayName = Integer.toString(pathID) + "_" + pathSource + "_" + pathDestin;
                flightList.add(flightPathDisplayName);
            }
            flightPathListView.setItems(flightList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Used to load the page from the MenuController.
     */
    public void load() {
        try {
            theDataSet = getParent().getCurrentDataset();
            ArrayList<FlightPath> flightPaths = new ArrayList();
            flightPaths = theDataSet.getFlightPaths();
            for(int i = 0; i<flightPaths.size(); i++ ){
                int pathID = flightPaths.get(i).getID();
                String pathSource = flightPaths.get(i).departsFrom();
                String pathDestin = flightPaths.get(i).arrivesAt();
                String flightPathDisplayName = Integer.toString(pathID) + "_" + pathSource + "_" + pathDestin;
                flightList.add(flightPathDisplayName);
            }
            flightPathListView.setItems(flightList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (theDataSet.getFlightPaths().size() > 0){
            map = new Map(mapView, theDataSet.getFlightPaths().get(0).getRoutePath());
        }else{
            map = new Map(mapView, new RoutePath());
        }
        flightPathListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                map.displayRoute(theDataSet.getFlightPaths().get(flightPathListView.getSelectionModel().getSelectedIndices().get(0)).getRoutePath());
            }
        });
    }

    /**
     *  Removes the selected path from the list view of paths and from the database.
     */
    public void deletePath() {
        String toDeleteStr = flightPathListView.getSelectionModel().getSelectedItem();
        String[] segments = toDeleteStr.split("_");
        String pathIdClicked = segments[0];

        int toDeleteIndex = theDataSet.getFlightPaths().indexOf(theDataSet.getFlightPathDictionary()
                .get(Integer.parseInt(pathIdClicked)));

        theDataSet.deleteFlightPath(toDeleteIndex);
        flightPathListView.getItems().clear();
        flightPathListView();
    }
}

