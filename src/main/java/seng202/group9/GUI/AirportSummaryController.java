package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.Airport;

/**
 * Created by michael on 14/09/2016.
 */
public class AirportSummaryController extends MenuController{
    @FXML
    private TableView<Airport> tableView;
    @FXML
    private TableColumn<Airport, String> columnName;
    @FXML
    private TableColumn<Airport, String> columnCity;
    @FXML
    private TableColumn<Airport, String> columnCountry;
    @FXML
    private TableColumn<Airport, String> columnAltitude;
    @FXML
    private TableColumn<Airport, String> columnIATA;

    private Dataset currentData = null;

    App parent;

    public void setApp(App parent){
        this.parent = parent;
    }

    public void loadTables() {
        currentData = this.parent.getCurrentDataset();
        columnName.setCellValueFactory(new PropertyValueFactory<Airport, String>("Name"));
        columnCity.setCellValueFactory(new PropertyValueFactory<Airport, String>("City"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<Airport, String>("Country"));
        columnIATA.setCellValueFactory(new PropertyValueFactory<Airport, String>("IATA_FFA"));
        columnAltitude.setCellValueFactory(new PropertyValueFactory<Airport, String>("Altitude"));
        currentData = this.parent.getCurrentDataset();
        tableView.setItems(FXCollections.observableArrayList(currentData.getAirports()));
    }

}