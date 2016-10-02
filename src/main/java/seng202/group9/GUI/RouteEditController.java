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
 * The GUI controller class for route_edit_form.fxml.
 * Extends from the abstract class {@link Controller}.
 * Created by Sunguin.
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


    /**
     * Loads up the current dataset and current session.
     * Also gets the route to be edited from the table.
     * Sets the text fields as the route selected.
     */
    public void load() {
        if (!checkDataset()){
            return;
        }
        theDataSet = getParent().getCurrentDataset();
        currentSession = getParent().getSession();

        routeContainer.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    editRoute();
                }
            }
        });

        toEdit = theDataSet.getRouteDictionary().get(currentSession.getRouteToEdit());

        rAirlineEdit.setText(toEdit.getAirlineName());
        rSourceEdit.setText(toEdit.getDepartureAirport());
        rDestEdit.setText(toEdit.getArrivalAirport());
        rCodeshareEdit.setText(toEdit.getCode());
        rStopsEdit.setText(Integer.toString(toEdit.getStops()));
        rEquipmentEdit.setText(toEdit.getEquipment());

    }


    /**
     * Edits the current route and closes the popup window.
     * Takes in the values from the text fields.
     * @see Dataset
     */
    public void editRoute() {
        //Tries to edit a route and comes up with a popup if successful and exits the form
        try {
            EntryParser parser = new EntryParser();
            parser.parseRoute(rAirlineEdit.getText(), rSourceEdit.getText(), rDestEdit.getText(), rCodeshareEdit.getText(),
                    rStopsEdit.getText(), rEquipmentEdit.getText());
            theDataSet.editRoute(toEdit, rAirlineEdit.getText(), rSourceEdit.getText(), rDestEdit.getText(), rCodeshareEdit.getText(),
                    rStopsEdit.getText(), rEquipmentEdit.getText());

            //Saying to the user that the route has successfully edited.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Route Edit Successful");
            alert.setHeaderText("Route data edited!");
            alert.setContentText("Your route data has been successfully edited.");
            alert.showAndWait();

            //Close the edit form.
            Stage stage = (Stage) editButton.getScene().getWindow();
            stage.close();
        } catch (DataException e) {
            System.err.println(e.getMessage());
        }
    }
}
