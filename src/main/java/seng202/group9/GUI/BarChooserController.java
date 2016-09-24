package seng202.group9.GUI;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import seng202.group9.Controller.SceneCode;

import java.util.ArrayList;

/**
 * Created by michael on 24/09/2016.
 */
public class BarChooserController extends Controller{

    @FXML
    ChoiceBox datatypechooser;
    @FXML
    ListView graph_against;
    @FXML
    ListView graph_options;

    ObservableList airportOptions = FXCollections.observableArrayList("ID", "Name", "ICAO", "IATA FFA", "Altitude",
            "Latitude", "Longitude", "City", "Country");

    ObservableList airlineOptions = FXCollections.observableArrayList("ID", "Name", "ICAO", "IATA", "Alias",
            "Call Sign", "Active", "Country");

    ObservableList routeOptions = FXCollections.observableArrayList("ID", "Stops", "Codeshare", "Equipment", "Airline",
            "Departure Airport", "Arival airport");

    ArrayList<ObservableList> allOptions = new ArrayList<ObservableList>();

    public void buildGraph() {

    }

    public void returnToSelection(){replaceSceneContent(SceneCode.ANALYSER_TAB);}

    public void changeTables(){
        int temp = datatypechooser.getSelectionModel().getSelectedIndex();
        graph_against.setItems(allOptions.get(temp));
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
        graph_against.setItems(airportOptions);
        graph_options.setItems(airportOptions);
        graph_options.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        allOptions.add(airportOptions);
        allOptions.add(airlineOptions);
        allOptions.add(routeOptions);
    }
}
