package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.Route;

/**
 * Created by michael on 14/09/2016.
 */
public class RouteSummaryController extends MenuController{
    @FXML
    private TableView<Route> tableView;
    @FXML
    private TableColumn<Route, String> columnName;
    @FXML
    private TableColumn<Route, String> columnCity;
    @FXML
    private TableColumn<Route, String> columnCountry;
    @FXML
    private TableColumn<Route, String> columnAltitude;
    @FXML
    private TableColumn<Route, String> columnIATA;

    private Dataset currentData = null;

    App parent;

    public void setApp(App parent){
        this.parent = parent;
    }

    public void loadTables() {
        currentData = this.parent.getCurrentDataset();
        columnName.setCellValueFactory(new PropertyValueFactory<Route, String>("Name"));
        columnCity.setCellValueFactory(new PropertyValueFactory<Route, String>("City"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<Route, String>("Country"));
        columnIATA.setCellValueFactory(new PropertyValueFactory<Route, String>("IATA_FFA"));
        columnAltitude.setCellValueFactory(new PropertyValueFactory<Route, String>("Altitude"));
        currentData = this.parent.getCurrentDataset();
        tableView.setItems(FXCollections.observableArrayList(currentData.getRoutes()));
    }

}