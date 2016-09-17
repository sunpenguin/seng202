package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

public class FlightRDController extends Controller {

    private Dataset theDataSet = null;
    private int currentPathId = 0;
    private int currentPathIndex = 0;

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

    @FXML
    private TextField flightNameBox;
    @FXML
    private TextField flightTypeBox;
    @FXML
    private TextField flightViaBox;
    @FXML
    private TextField flightAltitudeBox;
    @FXML
    private TextField flightLatitudeBox;
    @FXML
    private TextField flightLongitudeBox;
    @FXML
    private TextField flightHeadingBox;
    @FXML
    private TextField flightLegDistBox;
    @FXML
    private TextField flightTotDistBox;

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
                    String[] segments = flightPathDisplayNameClicked.split("_");
                    String pathIdClicked = segments[0];

                    currentPathIndex = theDataSet.getFlightPaths().indexOf(theDataSet.getFlightPathDictionary()
                            .get(Integer.parseInt(pathIdClicked)));
                    currentPathId = Integer.parseInt(pathIdClicked);

                    ArrayList<FlightPath> flightPaths;
                    flightPaths = theDataSet.getFlightPaths();
                    ArrayList<FlightPoint> flightPoints = flightPaths.get(currentPathIndex).getFlight();
                    flightTableView.setItems(FXCollections.observableArrayList(flightPoints));

                }
            });

            flightPathListView.setItems(flightList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function used to load the table for the Flight points initially from the MenuController
     */
    public void load() {
        theDataSet = getParent().getCurrentDataset();
        currentPathId = theDataSet.getFlightPaths().get(0).getID(); //Sets the default to the 1st Path
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

        ArrayList<FlightPath> flightPaths;
        flightPaths = theDataSet.getFlightPaths();
        //int firstID = flightPaths.get(0).getID();
        ArrayList<FlightPoint> flightPoints = flightPaths.get(0).getFlight();
        flightTableView.setItems(FXCollections.observableArrayList(flightPoints));
    }

    public void addFlightPoint() {
        ArrayList<FlightPath> flightPaths;
        flightPaths = theDataSet.getFlightPaths();

            try {
                theDataSet.addFlightPointToPath(currentPathId,
                    flightNameBox.getText(),
                    flightTypeBox.getText(),
                    flightViaBox.getText(),
                    flightAltitudeBox.getText(),
                    flightLatitudeBox.getText(),
                    flightLongitudeBox.getText(),
                    flightHeadingBox.getText(),
                    flightLegDistBox.getText(),
                    flightTotDistBox.getText());
                flightNameBox.clear();
                flightTypeBox.clear();
                flightViaBox.clear();
                flightAltitudeBox.clear();
                flightLatitudeBox.clear();
                flightLongitudeBox.clear();
                flightHeadingBox.clear();
                flightLegDistBox.clear();
                flightTotDistBox.clear();

                ArrayList<FlightPoint> flightPoints = flightPaths.get(currentPathIndex).getFlight();
                flightTableView.setItems(FXCollections.observableArrayList(flightPoints));
        } catch ( Exception e ) {
            //e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Flight Point Data Error");
            alert.setHeaderText("Error adding a custom flight point entry.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void loadOnce(){
        flightPathListView();
    }

}