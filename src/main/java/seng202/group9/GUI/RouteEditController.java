package seng202.group9.GUI;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seng202.group9.Controller.DataException;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.EntryParser;
import seng202.group9.Controller.Session;
import seng202.group9.Core.Route;

/**
 * Created by Sunguin on 2016/09/24.
 */
public class RouteEditController extends Controller {
    @FXML
    private TextField rAirlineEdit;
    @FXML
    private TextField rSourceEdit;
    @FXML
    private TextField rDestEdit;
    @FXML
    private TextField rCodeshareEdit;
    @FXML
    private TextField rStopsEdit;
    @FXML
    private TextField rEquipmentEdit;
    @FXML
    private Button editButton;
    @FXML
    private GridPane routeContainer;

    private Dataset theDataSet = null;

    private Session currentSession = null;

    private Route toEdit = null;

    public void editRoute() {
        try {
            EntryParser parser = new EntryParser();
            parser.parseRoute(rAirlineEdit.getText(), rSourceEdit.getText(), rDestEdit.getText(), rCodeshareEdit.getText(),
                    rStopsEdit.getText(), rEquipmentEdit.getText());
            theDataSet.editRoute(toEdit, rAirlineEdit.getText(), rSourceEdit.getText(), rDestEdit.getText(), rCodeshareEdit.getText(),
                    rStopsEdit.getText(), rEquipmentEdit.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Route Edit Successful");
            alert.setHeaderText("Route data edited!");
            alert.setContentText("Your route data has been successfully edited.");
            alert.showAndWait();

            Stage stage = (Stage) editButton.getScene().getWindow();
            stage.close();
        } catch (DataException e) {
            System.err.println("RIP Harambe: " + e.getMessage() + "IT WAS TOO SOON");
        }
    }

    public void load() {
        if (!checkDataset()){
            return;
        }
        theDataSet = getParent().getCurrentDataset();
        currentSession = getParent().getSession();

        toEdit = theDataSet.getRouteDictionary().get(currentSession.getRouteToEdit());

        rAirlineEdit.setText(toEdit.getAirlineName());
        rSourceEdit.setText(toEdit.getDepartureAirport());
        rDestEdit.setText(toEdit.getArrivalAirport());
        rCodeshareEdit.setText(toEdit.getCode());
        rStopsEdit.setText(Integer.toString(toEdit.getStops()));
        rEquipmentEdit.setText(toEdit.getEquipment());
        routeContainer.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    editRoute();
                }
            }
        });
    }
}
