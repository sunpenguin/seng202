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
    private TableColumn<Route, String> columnAirline;
    @FXML
    private TableColumn<Route, String> columnDepart;
    @FXML
    private TableColumn<Route, String> columnArrive;
    @FXML
    private TableColumn<Route, String> columnStops;
    @FXML
    private TableColumn<Route, String> columnEquipment;

    private Dataset currentData = null;

    App parent;

    public void setApp(App parent){
        this.parent = parent;
    }

    public void loadTables() {
        columnAirline.setCellValueFactory(new PropertyValueFactory<Route, String>("Airline"));
        columnDepart.setCellValueFactory(new PropertyValueFactory<Route, String>("DepartureAirport"));
        columnArrive.setCellValueFactory(new PropertyValueFactory<Route, String>("ArrivalAirport"));
        columnStops.setCellValueFactory(new PropertyValueFactory<Route, String>("Stops"));
        columnEquipment.setCellValueFactory(new PropertyValueFactory<Route, String>("Equipment"));
        currentData = this.parent.getCurrentDataset();
        tableView.setItems(FXCollections.observableArrayList(currentData.getRoutes()));
    }

}