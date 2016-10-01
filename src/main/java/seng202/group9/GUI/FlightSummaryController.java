package seng202.group9.GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import seng202.group9.Controller.App;
import seng202.group9.Controller.DataException;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Core.Airport;
import seng202.group9.Core.FlightPath;
import seng202.group9.Core.RoutePath;
import seng202.group9.Map.Map;
import seng202.group9.Core.FlightPoint;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

/**
 * Controller for the Flights Summary Scene.
 * Created by Liam Beckett on 13/09/2016.
 */
public class FlightSummaryController extends Controller {

    private Dataset theDataSet = null;
    private int currentPathId = 0;
    private int currentPathIndex  = 0;

    private Map map;
    @FXML
    private WebView mapView;
    @FXML
    ListView<String> flightPathListView;
    final ObservableList<String> flightList = FXCollections.observableArrayList();
    @FXML
    ListView<String> flightSummaryListView;
    final ObservableList<String> infoList = FXCollections.observableArrayList();

    /**
     * Changes to the Flight Raw Data Scene when the Raw Data Button is clicked.
     */
    public void handleRawDataButton() {
        replaceSceneContent(SceneCode.FLIGHT_RAW_DATA);
    }

    /**
     * Changes to the Airport Summary Scene when the Airport is clicked.
     */
    public void airportSummaryButton() {
        replaceSceneContent(SceneCode.AIRPORT_SUMMARY);
    }

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
     * Loads the current flight paths summary information into the ListView panel. The summary data includes the distance
     * and the name of the source and destination airports pulled from the Airport array list.
     */
    public void flightSummaryListView() {
        try {
            currentPathId = theDataSet.getFlightPaths().get(0).getID(); //Sets the default to the 1st Path

            FlightPath currentPath = theDataSet.getFlightPathDictionary().get(currentPathId);
            ArrayList<FlightPoint> flightPoints = currentPath.getFlightPoints();
            FlightPoint firstPoint = flightPoints.get(0);
            String firstPointICAO = firstPoint.getName();
            FlightPoint lastPoint = flightPoints.get(flightPoints.size()-1);
            String lastPointICAO = lastPoint.getName();

            ArrayList<Airport> airportList = theDataSet.getAirports();
            Airport sourceAirport = null;
            Airport destinationAirport = null;

            for (int i=0; i < airportList.size(); i++){
                Airport current = airportList.get(i);
                if(current.getICAO().equals(firstPointICAO)){
                    sourceAirport = current;
                }
                if(current.getICAO().equals(lastPointICAO)){
                    destinationAirport = current;
                }
            }

            String source = "Not Available";
            String destination = "Not Available";
            double distance = 0.0;
            if(sourceAirport != null){
                source = sourceAirport.getName();
            }
            if(destinationAirport != null){
                destination = destinationAirport.getName();
            }
            if(destination != "Not Available" && source != "Not Available"){
                distance = sourceAirport.calculateDistance(destinationAirport);
            }

            infoList.add("           Flight Path Summary Information");
            infoList.add("");
            infoList.add("Total Distance of Flight:");
            infoList.add(Double.toString(distance));
            infoList.add("Source Airport:");
            infoList.add(source);
            infoList.add("Destination Airport:");
            infoList.add(destination);
            if(sourceAirport == null || destinationAirport == null){
                infoList.add("");
                infoList.add("Missing Data is due to first or last points");
                infoList.add("ICAO codes not being present in the Airline");
                infoList.add("Database!");
            }
            flightSummaryListView.setItems(infoList);
        } catch(Exception e) {
            e.printStackTrace();
        }
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
            flightPathListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                 public void handle(MouseEvent event) {
                     String flightPathDisplayNameClicked = flightPathListView.getSelectionModel().getSelectedItem();
                     if (flightPathDisplayNameClicked!=null) {
                         String[] segments = flightPathDisplayNameClicked.split("_");
                         String pathIdClicked = segments[0];

                         currentPathIndex = theDataSet.getFlightPaths().indexOf(theDataSet.getFlightPathDictionary()
                                 .get(Integer.parseInt(pathIdClicked)));
                         currentPathId = Integer.parseInt(pathIdClicked);
                     }
                 }
            });
            flightPathListView.setItems(flightList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to load the page from the MenuController.
     */
    public void load() {
        if (!checkDataset()){
            return;
        }
        theDataSet = getParent().getCurrentDataset();
        if (theDataSet != null) {
            try {
                ArrayList<FlightPath> flightPaths;
                flightPaths = theDataSet.getFlightPaths();
                for (int i = 0; i < flightPaths.size(); i++) {
                    int pathID = flightPaths.get(i).getID();
                    String pathSource = flightPaths.get(i).departsFrom();
                    String pathDestin = flightPaths.get(i).arrivesAt();
                    String flightPathDisplayName = Integer.toString(pathID) + "_" + pathSource + "_" + pathDestin;
                    flightList.add(flightPathDisplayName);
                }
                flightPathListView.setItems(flightList);
                flightSummaryListView();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (theDataSet.getFlightPaths().size() > 0) {
                map = new Map(mapView, theDataSet.getFlightPaths().get(0).getRoutePath());
            } else {
                map = new Map(mapView, new RoutePath());
            }
            flightPathListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    int index = flightPathListView.getSelectionModel().getSelectedIndices().get(0);
                    if (index != -1) {
                        map.displayRoute(theDataSet.getFlightPaths().get(index).getRoutePath());
                    }
                }
            });
        }
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

