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
import seng202.group9.Controller.Dataset;


/**
 * The GUI controller class for route_add_form.fxml.
 * Extends the abstract class {@link Controller}
 * Created by Sunguin.
 */
public class RouteAddController extends Controller {
    //Setting up text fields for adding data
    @FXML
    private TextField rAirlineAdd;
    @FXML
    private TextField rSourceAdd;
    @FXML
    private TextField rDestAdd;
    @FXML
    private TextField rCodeshareAdd;
    @FXML
    private TextField rStopsAdd;
    @FXML
    private TextField rEquipmentAdd;
    @FXML
    private Button addButton;
    @FXML
    private GridPane routeContainer;

    private Dataset theDataSet = null;


    /**
     * Loads the current dataset.
     */
    public void load() {
        if (!checkDataset()){
            return;
        }
        theDataSet = getParent().getCurrentDataset();
        routeContainer.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    addRouteSingle();
                }
            }
        });
    }


    /**
     * Adds a single route entry in the database.
     * Takes in values from the GUI the user has typed in.
     * @see Dataset
     */
    public void addRouteSingle() {
        //Tries to add a new route to the dataset.
        try {
            theDataSet.addRoute(
                    rAirlineAdd.getText(),
                    rSourceAdd.getText(),
                    rDestAdd.getText(),
                    rCodeshareAdd.getText(),
                    rStopsAdd.getText(),
                    rEquipmentAdd.getText()
            );
            rAirlineAdd.clear();
            rSourceAdd.clear();
            rDestAdd.clear();
            rCodeshareAdd.clear();
            rStopsAdd.clear();
            rEquipmentAdd.clear();

            //Saying to the user that the route has successfully added.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Route Add Successful");
            alert.setHeaderText("New Route added!");
            alert.setContentText("Your new route has been successfully added into the database.");
            alert.showAndWait();

            //Close the add form
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        } catch ( Exception e ) {
            //Tells the user what and where the error is.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Route Data Error");
            alert.setHeaderText("Error adding a custom route entry.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


}
