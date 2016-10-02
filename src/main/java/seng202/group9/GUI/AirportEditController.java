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
 * Created by Sunguin on 2016/09/24.
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

    //Set an empty Dataset to be assigned later
    private Dataset theDataSet = null;

    private Session currentSession = null;

    private Airport toEdit = null;

    public void editAirport() {
        try {
            EntryParser parser = new EntryParser();
            parser.parseAirport(airpNameEdit.getText(), airpCityEdit.getText(), airpCountryEdit.getText(), airpIATAFAAEdit.getText(),
                    airpICAOEdit.getText(), airpLatitudeEdit.getText(), airpLongitudeEdit.getText(), airpAltitudeEdit.getText(),
                    airpTimezoneEdit.getText(), airpDSTEdit.getText(), airpTzEdit.getText());
            theDataSet.editAirport(toEdit, airpNameEdit.getText(), airpCityEdit.getText(), airpCountryEdit.getText(), airpIATAFAAEdit.getText(),
                    airpICAOEdit.getText(), airpLatitudeEdit.getText(), airpLongitudeEdit.getText(), airpAltitudeEdit.getText(),
                    airpTimezoneEdit.getText(), airpDSTEdit.getText(), airpTzEdit.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Airport Edit Successful");
            alert.setHeaderText("Airport data edited!");
            alert.setContentText("Your airport data has been successfully edited.");
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
        airportContainer.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    editAirport();
                }
            }
        });
    }
}
