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
 * The GUI controller class for airline_add_form.fxml.
 * Extends from the abstract class {@link Controller}.
 * Created by Sunguin
 */
public class AirlineAddController extends Controller {
    //Setting up text fields for adding data.
    @FXML
    private TextField airlNameAdd;
    @FXML
    private TextField airlAliasAdd;
    @FXML
    private TextField airlIATAAdd;
    @FXML
    private TextField airlICAOAdd;
    @FXML
    private TextField airlCallsignAdd;
    @FXML
    private TextField airlCountryAdd;
    @FXML
    private TextField airlActiveAdd;
    @FXML
    private Button addButton;
    @FXML
    private GridPane airlineContainer;

    //Set an empty Dataset to be assigned to the current dataset.
    private Dataset theDataSet = null;

    /**
     * Loads up the current dataset.
     */
    public void load() {
        theDataSet = getParent().getCurrentDataset();
        airlineContainer.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    addAirlineSingle();
                }
            }
        });
    }

    /**
     * Adds a single airline entry to the database.
     * Takes in values from the GUI the user has typed in.
     * @see Dataset
     */
    public void addAirlineSingle() {
        //Tries to add a new airline and clears the fields to their initial state if successful.
        //Otherwise an error message will pop up with what is wrong with the manual data.

        try {
            theDataSet.addAirline(
                    airlNameAdd.getText(),
                    airlAliasAdd.getText(),
                    airlIATAAdd.getText(),
                    airlICAOAdd.getText(),
                    airlCallsignAdd.getText(),
                    airlCountryAdd.getText(),
                    airlActiveAdd.getText());
            airlNameAdd.clear();
            airlAliasAdd.clear();
            airlIATAAdd.clear();
            airlICAOAdd.clear();
            airlCallsignAdd.clear();
            airlCountryAdd.clear();
            airlActiveAdd.clear();

            //Saying to the user that the airline has successfully added.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Airline Add Successful");
            alert.setHeaderText("New Airline added!");
            alert.setContentText("Your new airline has been successfully added into the database.");
            alert.showAndWait();

            //Closes the add form.
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            //Tells the user what and where the error is.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Airline Data Error");
            alert.setHeaderText("Error adding a custom airline entry.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
