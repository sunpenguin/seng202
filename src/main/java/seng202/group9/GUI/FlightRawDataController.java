package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.FlightPath;
import seng202.group9.Core.FlightPoint;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Controller for the Flights Raw Data Scene.
 * Created by Liam Beckett on 13/09/2016.
 */
public class FlightRawDataController  implements Initializable {

    private Dataset theDataSet = null;

    App parent;
    public void setApp(App parent){
        this.parent = parent;
    }

    @FXML
    private TableView<FlightPoint> flightTableView;
    @FXML
    private TableColumn<FlightPoint, String> flightIdCol;
    @FXML
    private TableColumn<FlightPoint, String> flightNameCol;
    @FXML
    private TableColumn<FlightPoint, String> flightTypeCol;
    @FXML
    private TableColumn<FlightPoint, String> flightViaCol;
    @FXML
    private TableColumn<FlightPoint, String> flightAltitudeCol;
    @FXML
    private TableColumn<FlightPoint, String> flightLatCol;
    @FXML
    private TableColumn<FlightPoint, String> flightLongCol;
    @FXML
    private TableColumn<FlightPoint, String> flightHeadCol;
    @FXML
    private TableColumn<FlightPoint, String> flightLegDisCol;
    @FXML
    private TableColumn<FlightPoint, String> flightTotDisCol;

    @FXML
    ListView<String> flightPathListView;
    final ObservableList<String> flightList = FXCollections.observableArrayList();


    public void flightPathListView() {
        try {
            ArrayList<FlightPath> flightPaths = new ArrayList();
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
                    String[] segments = flightPathDisplayNameClicked.split("_");
                    String pathIdClicked = segments[0];

                    ArrayList<FlightPath> flightPaths;
                    flightPaths = theDataSet.getFlightPaths();
                    ArrayList<FlightPoint> flightPoints = flightPaths.get(Integer.parseInt(pathIdClicked)-1).getFlight();
                    flightTableView.setItems(FXCollections.observableArrayList(flightPoints));

                }
            });

            flightPathListView.setItems(flightList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadTables() {
        flightIdCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("ID"));
        flightNameCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("Name"));
        flightTypeCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("Type"));
        flightViaCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("Via"));
        flightAltitudeCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("Altitude"));
        flightLatCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("Latitude"));
        flightLongCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("Longitude"));
        flightHeadCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("Heading"));
        flightLegDisCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("Leg_Dist"));
        flightTotDisCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("Tot_Dist"));

        theDataSet = this.parent.getCurrentDataset();

        ArrayList<FlightPath> flightPaths;
        flightPaths = theDataSet.getFlightPaths();
        int firstID = flightPaths.get(0).getID();
        ArrayList<FlightPoint> flightPoints = flightPaths.get(0).getFlight();
        flightTableView.setItems(FXCollections.observableArrayList(flightPoints));
    }

    public static void updateTable(int pathID) {

    }


    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub

    }
}