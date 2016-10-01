package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import seng202.group9.Controller.DataException;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Controller.Session;
import seng202.group9.Core.FlightPath;
import seng202.group9.Core.FlightPoint;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

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
    private ObservableList<String> flightList = FXCollections.observableArrayList();

//    @FXML
//    private TextField flightNameBox;
//    @FXML
//    private TextField flightTypeBox;
//    @FXML
//    private TextField flightViaBox;
//    @FXML
//    private TextField flightAltitudeBox;
//    @FXML
//    private TextField flightLatitudeBox;
//    @FXML
//    private TextField flightLongitudeBox;
//    @FXML
//    private TextField flightHeadingBox;
//    @FXML
//    private TextField flightLegDistBox;
//    @FXML
//    private TextField flightTotDistBox;

    /**
     * Loads the Flight paths into the List View and waits for a mouse clicked event for which it will update the table
     * to display the selected Flight paths points. Called from the MenuController.
     */
    private void flightPathListView() {
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

                        ArrayList<FlightPath> flightPaths;
                        flightPaths = theDataSet.getFlightPaths();
                        ArrayList<FlightPoint> flightPoints = flightPaths.get(currentPathIndex).getFlight();
                        flightTableView.setItems(FXCollections.observableArrayList(flightPoints));
                    }

                }
            });

            flightPathListView.setItems(flightList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to load the table for the Flight points initially from the MenuController
     */
    public void load() {
        theDataSet = getParent().getCurrentDataset();
        if (theDataSet != null) {
            try {
                try {
                    currentPathId = theDataSet.getFlightPaths().get(0).getID(); //Sets the default to the 1st Path
                } catch (DataException e) {
                    e.printStackTrace();
                }
                flightIdCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("ID"));
                flightNameCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("Name"));
                flightTypeCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("Type"));
                flightAltitudeCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("Altitude"));
                flightLatCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("Latitude"));
                flightLongCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("Longitude"));
                flightHeadCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("Heading"));
                flightLegDisCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("LegDistance"));
                flightTotDisCol.setCellValueFactory(new PropertyValueFactory<FlightPoint, String>("totalDistance"));

                ArrayList<FlightPath> flightPaths;
                flightPaths = theDataSet.getFlightPaths();
                ArrayList<FlightPoint> flightPoints = flightPaths.get(0).getFlight();
                flightTableView.setItems(FXCollections.observableArrayList(flightPoints));
            }catch(IndexOutOfBoundsException e){
                System.out.println("There is no Paths to show");
            }
        }
    }

    /**
     *  Will take the inputs from the text fields and adds the point to the current flight path.
     */
    public void openAdd() {
        Session session = getParent().getSession();
        session.setCurrentFlightPathtID(currentPathId);
        createPopUpStage(SceneCode.FLIGHT_ADD, 600, 400);
        //flightTableView.setItems(FXCollections.observableArrayList(theDataSet.getAirports()));
        updateTable(currentPathIndex);
    }

    /**
     * Creates a pop up dialog which prompts the user for two ICAO airport codes which will use when creating a new path.
     */
    public void newPath() {
        NewPathPopUp dialogBox = new NewPathPopUp();
        dialogBox.display();
        String destAirport = dialogBox.getDestinationAirport();
        String sourceAirport = dialogBox.getSourceAirport();

        if (destAirport != null && sourceAirport != null){
            theDataSet.addFlightPath(sourceAirport, destAirport);
            flightPathListView.getItems().clear();
            flightPathListView();
        }
    }
    /**
     *  Removes the selected point from the table and database.
     */
    public void deletePoint() {
        FlightPoint toDelete = flightTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Flight Point Delete Confirmation");
        alert.setHeaderText("You are about to delete some data.");
        alert.setContentText("Are you sure you want to delete the selected flight point?");
        //alert.showAndWait();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int pathID;
            try {
                pathID = toDelete.getIndex();
            } catch (DataException e) {
                e.printStackTrace();
                System.err.println("Point is Undeletable as the Index ID is not set.");
                return;
            }
            LinkedHashMap<Integer, FlightPath> flightPathDict = theDataSet.getFlightPathDictionary();
            FlightPath toDeletesPath = flightPathDict.get(pathID);
            theDataSet.deleteFlightPoint(toDelete, toDeletesPath);

            currentPathIndex = theDataSet.getFlightPaths().indexOf(theDataSet.getFlightPathDictionary().get(pathID));

            updateTable(currentPathIndex);
            updatePaths();
        }
    }

    /**
     * Loads the pop up for the edit data scene and updates the table when the window is closed.
     */
    public void editPoint() {
        FlightPoint toEdit = flightTableView.getSelectionModel().getSelectedItem();
        try {
            Session session = getParent().getSession();
            session.setCurrentFlightPointID(toEdit.getID());
            session.setCurrentFlightPathtID(currentPathId);
        } catch (DataException e) {
            e.printStackTrace();
            System.err.println("Point is Uneditable as the Index ID is not set.");
            return;
        }
        createPopUpStage(SceneCode.FLIGHT_EDITOR, 600, 289);
        updateTable(currentPathIndex);
        updatePaths();

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
        updatePaths();
        updateTable(0);
    }

    /**
     * Function for the 'Move Up' right click option on the points in the flight table.
     */
    public void movePointUp(){
        FlightPoint toMove = flightTableView.getSelectionModel().getSelectedItem();
        int toMoveIndex = flightTableView.getSelectionModel().getSelectedIndex();
        try{
            if (toMoveIndex != 0) {
                theDataSet.moveFlightPoint(toMove, toMoveIndex - 1);
            }
        } catch (DataException e) {
            e.printStackTrace();
        }
        updateTable(currentPathIndex);
        updatePaths();
    }

    /**
     * Function for the 'Move Down' right click option on the points in the flight table.
     */
    public void movePointDown(){
        FlightPoint toMove = flightTableView.getSelectionModel().getSelectedItem();
        int toMoveIndex = flightTableView.getSelectionModel().getSelectedIndex();
        try{
            if (toMoveIndex != flightTableView.getItems().size()-1) {
                theDataSet.moveFlightPoint(toMove, toMoveIndex + 1);
            }
        } catch (DataException e) {
            e.printStackTrace();
        }
        updateTable(currentPathIndex);
        updatePaths();
    }

    /**
     * Updates the table so that when the database is changed (deleted or edited) it still shows the correct data values.
     * @param currentPathIndex The index of the current path in the Path array list.
     */
    private void updateTable(int currentPathIndex) {
        ArrayList<FlightPath> flightPaths;
        flightPaths = theDataSet.getFlightPaths();
        if (flightPaths.size() != 0) {
            ArrayList<FlightPoint> flightPoints = flightPaths.get(currentPathIndex).getFlight();
            flightTableView.setItems(FXCollections.observableArrayList(flightPoints));
            flightTableView.refresh();
        }else{
            flightTableView.getItems().clear();
            flightTableView.refresh();
        }
    }

    /**
     * Updates the flight path list view so that it displays the correct names for the paths
     */
    private void updatePaths(){
        try {
            flightPathListView.getItems().clear();
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
     * Will link to the flight analyser when implemented.
     */
    public void flightAnalyser(){
        JOptionPane.showMessageDialog(null, "This is not Implemented yet");
    }

    @Override
    public void loadOnce(){
        if (theDataSet != null) {
            flightPathListView();
        }
    }

    public void flightSummaryButton() {
        replaceSceneContent(SceneCode.FLIGHT_SUMMARY);
    }

}