package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.FlightPath;
import seng202.group9.Core.FlightPoint;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for the Flights Summary Scene.
 * Created by Liam Beckett on 13/09/2016.
 */
public class FlightSummaryController implements Initializable {

    private Dataset theDataSet = null;

    App parent;
    public void setApp(App parent){
        this.parent = parent;
    }

    @FXML
    private Button flightRawData;

    @FXML
    ListView<String> flightPathListView;
    final ObservableList<String> flightList = FXCollections.observableArrayList();

    /**
     * Loads the Flight paths into the List View and is called from the MenuController.
     */
    public void flightPathListView() {
        try {
            theDataSet = this.parent.getCurrentDataset();
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

    /**
     * Changes to the Flight Raw Data Scene when the Raw Data Button is clicked
     */
    public void handleRawDataButton() {
        try {
            FlightRawDataController rawDataController = (FlightRawDataController)
                    parent.replaceSceneContent("flight_raw_data.fxml");
            rawDataController.setApp(parent);
            rawDataController.loadTables();
            rawDataController.flightPathListView();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub

    }
}

