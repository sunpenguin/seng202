package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Core.FlightPath;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for the Flights Summary Scene.
 * Created by Liam Beckett on 13/09/2016.
 */
public class FlightSummaryController extends Controller {

    private Dataset theDataSet = null;

    @FXML
    private Button flightRawData;

    @FXML
    ListView<String> flightPathListView;
    final ObservableList<String> flightList = FXCollections.observableArrayList();

    /**
     * Changes to the Flight Raw Data Scene when the Raw Data Button is clicked
     */
    public void handleRawDataButton() {
        replaceSceneContent(SceneCode.FLIGHT_RAW_DATA);
    }

    public void airportSummaryButton() {
        replaceSceneContent(SceneCode.AIRPORT_SUMMARY);
    }
    public void routeSummaryButton() {
        replaceSceneContent(SceneCode.ROUTE_SUMMARY);
    }
    public void airlineSummaryButton() {
        replaceSceneContent(SceneCode.AIRLINE_SUMMARY);
    }

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
    }
}

