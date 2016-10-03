package seng202.group9.GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Core.Airline;
import seng202.group9.Core.Airport;
import seng202.group9.Core.Position;
import seng202.group9.Core.RoutePath;
import seng202.group9.Map.Map;

/**
 * Controller for the Airline summary scene.
 * Created by michael on 14/09/2016.
 */
public class AirlineSummaryController extends Controller{
    //links the fxml to the controller.
    @FXML
    private TableView<Airline> tableView;
    @FXML
    private WebView mapView;
    @FXML
    private TableColumn<Airline, String> columnName;
    @FXML
    private TableColumn<Airline, String> columnAlias;
    @FXML
    private TableColumn<Airline, String> columnCountry;
    @FXML
    private TableColumn<Airline, String> columnActive;
    @FXML
    private TableColumn<Airline, String> columnIATA;

    //Holds data used for the table.
    private Dataset currentData = null;

    private Map map;

    /**
     * Loads initial state of the scene.
     */
    public void load() {
        if (!checkDataset(SceneCode.AIRLINE_SUMMARY)){
            return;
        }
        //Fills the table.
        columnName.setCellValueFactory(new PropertyValueFactory<Airline, String>("Name"));
        columnAlias.setCellValueFactory(new PropertyValueFactory<Airline, String>("Alias"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<Airline, String>("CountryName"));
        columnIATA.setCellValueFactory(new PropertyValueFactory<Airline, String>("IATA"));
        columnActive.setCellValueFactory(new PropertyValueFactory<Airline, String>("Active"));
        currentData = getParent().getCurrentDataset();
        tableView.setItems(FXCollections.observableArrayList(currentData.getAirlines()));
        //Sets up map.
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Airline>() {
            public void changed(ObservableValue<? extends Airline> observable, Airline oldValue, Airline newValue) {
                Airline selectedAirline= tableView.getSelectionModel().getSelectedItems().get(0);
                for (int i = 0 ; i < currentData.getAirports().size(); i ++){
                    if (currentData.getAirports().get(i).getCountryName().equals(selectedAirline.getCountryName())){
                        map.displayAirport(new RoutePath(new Position(currentData.getAirports().get(i).getLatitude(), currentData.getAirports().get(i).getLongitude())));
                        break;
                    }
                }
            }
        });
        map = new Map(mapView, new RoutePath(), tableView);
    }

    /**
     * Changes the scene to the Airline raw data scene.
     */
    public void airlineRawDataButton() {
        replaceSceneContent(SceneCode.AIRLINE_RAW_DATA);
    }

    /**
     * Changes the scene to the Flight summary scene.
     */
    public void flightSummaryButton() {
        replaceSceneContent(SceneCode.FLIGHT_SUMMARY);
    }

    /**
     * Changes the scene to the Airport summary scene.
     */
    public void airportSummaryButton() {
        replaceSceneContent(SceneCode.AIRPORT_SUMMARY);
    }

    /**
     * Changes the scene to the Route summary scene.
     */
    public void routeSummaryButton() {
        replaceSceneContent(SceneCode.ROUTE_SUMMARY);
    }
}
