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
 * The GUI controller class for flight_add_form.fxml.
 * Extends the abstract class {@link Controller}
 * Created by Sunguin.
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

    private Dataset theDataSet = null;
    private Session currentSession = null;


    /**
     * Loads up the current dataset.
     */
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


    /**
     * Adds a single flight point entry to the current flight path selected in the database.
     * Takes in values from the GUI the user has typed in.
     * @see Dataset
     */
    public void addFlight() {
        //Tries to add a new flight point to the current flight path.
        try {
            theDataSet.addFlightPointToPath(currentSession.getCurrentFlightPathID(),
                    fNameAdd.getText(),
                    fTypeAdd.getText(),
                    fAltitudeAdd.getText(),
                    fLatitudeAdd.getText(),
                    fLongitudeAdd.getText());

            //Saying to the user that the flight point has successfully added.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Flight Point Add Successful");
            alert.setHeaderText("New Flight Point added!");
            alert.setContentText("Your new flight point has been successfully added into the database.");
            alert.showAndWait();

            //Close the add form
            Stage stage = (Stage) flightAddButton.getScene().getWindow();
            stage.close();
        } catch ( Exception e ) {
            //Tells the user what and where the error is.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Flight Point Data Error");
            alert.setHeaderText("Error adding a custom flight point entry.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
