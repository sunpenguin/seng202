package seng202.group9.GUI;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Controller.Session;

import java.util.ArrayList;

/**
 * Created by michael on 24/09/2016.
 */
public class PieChooserController extends Controller{

    @FXML
    ChoiceBox datatypechooser;
    @FXML
    ListView graph_options;
    @FXML
    CheckBox usefilter;


    ObservableList airportOptions = FXCollections.observableArrayList("Name", "ICAO", "IATA FFA", "Altitude",
            "City", "Country");

    ObservableList airlineOptions = FXCollections.observableArrayList("Name", "ICAO", "IATA", "Active", "Country");

    ObservableList routeOptions = FXCollections.observableArrayList("Stops", "Codeshare", "Equipment", "Airline",
            "Departure Airport", "Arival airport");

    ArrayList<ObservableList> allOptions = new ArrayList<ObservableList>();


    public void toBarGraphChooser(){replaceSceneContent(SceneCode.BAR_GRAPH_CHOOSER);}

    public void buildGraph() {
        Session currentsession = this.getParent().getSession();
        ArrayList<String> temp = new ArrayList<String>();
        currentsession.setSelectedDataToGraph(datatypechooser.getSelectionModel().getSelectedItem().toString());
        currentsession.setSelectedgraphagainst(graph_options.getSelectionModel().getSelectedItem().toString());
        currentsession.setUsefilter(usefilter.isSelected());
        currentsession.setForceGraph(Boolean.FALSE);
        replaceSceneContent(SceneCode.AIRPORT_ANALYSER);
    }

    public void changeTables(){
        int temp = datatypechooser.getSelectionModel().getSelectedIndex();
        graph_options.setItems(allOptions.get(temp));
    }

    public void load(){
        datatypechooser.setItems(FXCollections.observableArrayList("Airports","Airlines","Routes"));
        datatypechooser.getSelectionModel().selectFirst();
        datatypechooser.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                changeTables();
            }
        });
        graph_options.setItems(airportOptions);
        graph_options.getSelectionModel().selectFirst();
        allOptions.add(airportOptions);
        allOptions.add(airlineOptions);
        allOptions.add(routeOptions);
    }

    public void newFilter(){
        if (datatypechooser.getSelectionModel().getSelectedItem().toString() == "Airports"){
            createPopUpStage(SceneCode.AIRPORT_FILTER, 600, 330);
        }
        else if (datatypechooser.getSelectionModel().getSelectedItem().toString() == "Airlines"){
            createPopUpStage(SceneCode.AIRLINE_FILTER, 600, 330);
        }
        else if (datatypechooser.getSelectionModel().getSelectedItem().toString() == "Routes"){
            createPopUpStage(SceneCode.ROUTE_FILTER, 600, 330);
        }
    }
}