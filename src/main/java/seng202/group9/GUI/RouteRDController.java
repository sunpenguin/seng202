package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.Route;

/**
 * Created by Sunguin on 2016/09/14.
 */
public class RouteRDController extends Controller {

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
    private TextField rSourceBox;
    @FXML
    private TextField rDestBox;
    @FXML
    private ComboBox<String> rCodeshareCBox;
    @FXML
    private TextField rStopsBox;
    @FXML
    private TextField rEquipmentBox;

    private Dataset theDataSet = null;

    public void addRouteSingle() {
        try {
            theDataSet.addRoute(
                    rAirlineBox.getText(),
                    rSourceBox.getText(),
                    rDestBox.getText(),
                    rCodeshareCBox.getSelectionModel().getSelectedItem().toString(),
                    rStopsBox.getText(),
                    rEquipmentBox.getText()
            );
            rAirlineBox.clear();
            rSourceBox.clear();
            rDestBox.clear();
            rCodeshareCBox.getSelectionModel().clearSelection();
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

    public void load() {
        rAirlineCol.setCellValueFactory(new PropertyValueFactory<Route, String>("AirlineName"));
        rAirlineIDCol.setCellValueFactory(new PropertyValueFactory<Route, String>("AirlineID"));
        rSourceCol.setCellValueFactory(new PropertyValueFactory<Route, String>("DepartureAirport"));
        rSourceIDCol.setCellValueFactory(new PropertyValueFactory<Route, String>("SourceID"));
        rDestCol.setCellValueFactory(new PropertyValueFactory<Route, String>("ArrivalAirport"));
        rDestIDCol.setCellValueFactory(new PropertyValueFactory<Route, String>("DestID"));
        rCodeshareCol.setCellValueFactory(new PropertyValueFactory<Route, String>("Code"));
        rStopsCol.setCellValueFactory(new PropertyValueFactory<Route, String>("Stops"));
        rEquipmentCol.setCellValueFactory(new PropertyValueFactory<Route, String>("Equipment"));

        theDataSet = getParent().getCurrentDataset();
        tableViewRouteRD.setItems(FXCollections.observableArrayList(theDataSet.getRoutes()));

        rCodeshareCBox.getItems().addAll("Y", "");
    }
}
