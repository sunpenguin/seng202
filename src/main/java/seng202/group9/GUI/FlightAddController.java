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
import seng202.group9.Controller.Session;

/**
 * Created by Sunguin on 2016/10/01.
 */
public class FlightAddController extends Controller {
    //Set up text fields for adding data
    @FXML
    private TextField fNameAdd;
    @FXML
    private TextField fTypeAdd;
    @FXML
    private TextField fAltitudeAdd;
    @FXML
    private TextField fLatitudeAdd;
    @FXML
    private TextField fLongitudeAdd;
    @FXML
    private Button flightAddButton;
    @FXML
    private GridPane flightContainer;

    //Set an empty Dataset to be assigned later
    private Dataset theDataSet = null;

    private Session currentSession = null;

    public void load() {
        if (!checkDataset()){
            return;
        }
        theDataSet = getParent().getCurrentDataset();
        currentSession = getParent().getSession();
        flightContainer.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    addFlight();
                }
            }
        });
    }

    public void addFlight() {
        try {
            theDataSet.addFlightPointToPath(currentSession.getCurrentFlightPathID(),
                    fNameAdd.getText(),
                    fTypeAdd.getText(),
                    fAltitudeAdd.getText(),
                    fLatitudeAdd.getText(),
                    fLongitudeAdd.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Flight Point Add Successful");
            alert.setHeaderText("New Flight Point added!");
            alert.setContentText("Your new flight point has been successfully added into the database.");
            alert.showAndWait();

            Stage stage = (Stage) flightAddButton.getScene().getWindow();
            stage.close();
        } catch ( Exception e ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Flight Point Data Error");
            alert.setHeaderText("Error adding a custom flight point entry.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
