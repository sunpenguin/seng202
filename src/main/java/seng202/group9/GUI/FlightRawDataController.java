package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.FlightPath;
import seng202.group9.Core.FlightPoint;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for the Flights Raw Data Scene.
 * Created by Liam Beckett on 13/09/2016.
 */
public class FlightRawDataController  implements Initializable {


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

    private Dataset theDataSet = null;
    App parent;


    public void setApp(App parent){
        this.parent = parent;
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
//        try{
//            System.out.println(theDataSet.importAirline("res/Samples/Airlines.txt"));
//        } catch (DataException e){
//            e.printStackTrace();
//        }
        ArrayList<FlightPath> flightPaths = new ArrayList();
        flightPaths = theDataSet.getFlightPaths();
        int firstID = flightPaths.get(0).getID();
        ArrayList<FlightPoint> flightPoints = flightPaths.get(0).getFlight();
        flightTableView.setItems(FXCollections.observableArrayList(flightPoints));
    }


    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub

    }
}