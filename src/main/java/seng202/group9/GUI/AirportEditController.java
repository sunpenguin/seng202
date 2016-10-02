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
import seng202.group9.Core.Airport;


/**
 * The GUI controller class for airport_edit_form.fxml.
 * Extends from the abstract class {@link Controller}.
 * Created by Sunguin.
 */
public class AirportEditController extends Controller {
    //Setting up text fields for adding data
    @FXML
    private TextField airpNameEdit;
    @FXML
    private TextField airpCityEdit;
    @FXML
    private TextField airpCountryEdit;
    @FXML
    private TextField airpIATAFAAEdit;
    @FXML
    private TextField airpICAOEdit;
    @FXML
    private TextField airpLatitudeEdit;
    @FXML
    private TextField airpLongitudeEdit;
    @FXML
    private TextField airpAltitudeEdit;
    @FXML
    private TextField airpTimezoneEdit;
    @FXML
    private TextField airpDSTEdit;
    @FXML
    private TextField airpTzEdit;
    @FXML
    private Button editButton;
    @FXML
    private GridPane airportContainer;

    private Dataset theDataSet = null;
    private Session currentSession = null;
    private Airport toEdit = null;


    /**
     * Loads up the current dataset and current session.
     * Also gets the airport to be edited from the table.
     * Sets the text fields as the airport selected.
     */
    public void load() {
        if (!checkDataset()){
            return;
        }
        theDataSet = getParent().getCurrentDataset();
        currentSession = getParent().getSession();

        airportContainer.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    editAirport();
                }
            }
        });

        toEdit = theDataSet.getAirportDictionary().get(currentSession.getAirportToEdit());

        airpNameEdit.setText(toEdit.getName());
        airpCityEdit.setText(toEdit.getCityName());
        airpCountryEdit.setText(toEdit.getCountryName());
        airpIATAFAAEdit.setText(toEdit.getIATA_FFA());
        airpICAOEdit.setText(toEdit.getICAO());
        airpLatitudeEdit.setText(Double.toString(toEdit.getLatitude()));
        airpLongitudeEdit.setText(Double.toString(toEdit.getLongitude()));
        airpAltitudeEdit.setText(Double.toString(toEdit.getAltitude()));
        airpTimezoneEdit.setText(Double.toString(toEdit.getTimezone()));
        airpDSTEdit.setText(toEdit.getDST());
        airpTzEdit.setText(toEdit.getTz());

    }


    /**
     * Edits the current airport and closes the popup window.
     * Takes in the values from the text fields.
     * @see Dataset
     */
    public void editAirport() {
        //Tries to edit an airport and comes up with a popup if successful and exits the form
        try {
            EntryParser parser = new EntryParser();
            parser.parseAirport(airpNameEdit.getText(), airpCityEdit.getText(), airpCountryEdit.getText(), airpIATAFAAEdit.getText(),
                    airpICAOEdit.getText(), airpLatitudeEdit.getText(), airpLongitudeEdit.getText(), airpAltitudeEdit.getText(),
                    airpTimezoneEdit.getText(), airpDSTEdit.getText(), airpTzEdit.getText());
            theDataSet.editAirport(toEdit, airpNameEdit.getText(), airpCityEdit.getText(), airpCountryEdit.getText(), airpIATAFAAEdit.getText(),
                    airpICAOEdit.getText(), airpLatitudeEdit.getText(), airpLongitudeEdit.getText(), airpAltitudeEdit.getText(),
                    airpTimezoneEdit.getText(), airpDSTEdit.getText(), airpTzEdit.getText());

            //Saying to the user that the airport has successfully edited.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Airport Edit Successful");
            alert.setHeaderText("Airport data edited!");
            alert.setContentText("Your airport data has been successfully edited.");
            alert.showAndWait();

            //Close the edit form.
            Stage stage = (Stage) editButton.getScene().getWindow();
            stage.close();
        } catch (DataException e) {
            System.err.println(e.getMessage());
        }
    }
}
