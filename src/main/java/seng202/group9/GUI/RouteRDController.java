package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.Airline;
import seng202.group9.Core.Route;

/**
 * Created by Sunguin on 2016/09/14.
 */
public class RouteRDController extends MenuController {

    @FXML
    private TableView<Route> tableViewRouteRD;
    @FXML
    private TableColumn<Route, String> rAirlineCol;
    @FXML
    private TableColumn<Route, String> rAirlineIDCol;
    @FXML
    private TableColumn<Route, String> rSourceCol;
    @FXML
    private TableColumn<Route, String> rSourceIDCol;
    @FXML
    private TableColumn<Route, String> rDestCol;
    @FXML
    private TableColumn<Route, String> rDestIDCol;
    @FXML
    private TableColumn<Route, String> rCodeshareCol;
    @FXML
    private TableColumn<Route, String> rStopsCol;
    @FXML
    private TableColumn<Route, String> rEquipmentCol;

    @FXML
    private TextField rAirlineBox;
    @FXML
    private TextField rAirlineIDBox;
    @FXML
    private TextField rSourceBox;
    @FXML
    private TextField rSourceIDBox;
    @FXML
    private TextField rDestBox;
    @FXML
    private TextField rDestIDBox;
    @FXML
    private TextField rCodeshareBox;
    @FXML
    private TextField rStopsBox;
    @FXML
    private TextField rEquipmentBox;


    App parent;

    public void setApp(App parent){
        this.parent = parent;
    }

    private Dataset theDataSet = null;

    public void loadTables() {
        rAirlineCol.setCellValueFactory(new PropertyValueFactory<Route, String>("Airline"));
        //rAirlineIDCol.setCellValueFactory(new PropertyValueFactory<Route, String>("AirlineID"));
        rSourceCol.setCellValueFactory(new PropertyValueFactory<Route, String>("DepartureAirport"));
        //rSourceIDCol.setCellValueFactory(new PropertyValueFactory<Route, String>("SourceID"));
        rDestCol.setCellValueFactory(new PropertyValueFactory<Route, String>("ArrivalAirport"));
        //rDestIDCol.setCellValueFactory(new PropertyValueFactory<Route, String>("DestID"));
        rCodeshareCol.setCellValueFactory(new PropertyValueFactory<Route, String>("Code"));
        rStopsCol.setCellValueFactory(new PropertyValueFactory<Route, String>("Stops"));
        rEquipmentCol.setCellValueFactory(new PropertyValueFactory<Route, String>("Equipment"));

        theDataSet = this.parent.getCurrentDataset();
        tableViewRouteRD.setItems(FXCollections.observableArrayList(theDataSet.getRoutes()));
    }

    public void addRouteSingle() {
        try {
            theDataSet.addRoute(
                    rAirlineBox.getText(),
                    rSourceBox.getText(),
                    rDestBox.getText(),
                    rCodeshareBox.getText(),
                    rStopsBox.getText(),
                    rEquipmentBox.getText()
            );
            rAirlineBox.clear();
            rSourceBox.clear();
            rDestBox.clear();
            rCodeshareBox.clear();
            rStopsBox.clear();
            rEquipmentBox.clear();
            tableViewRouteRD.setItems(FXCollections.observableArrayList(theDataSet.getRoutes()));
        } catch ( Exception e ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Route Data Error");
            alert.setHeaderText("Error adding a custom route entry.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
