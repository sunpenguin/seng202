package seng202.group9.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.EntryParser;

/**
 * The controller class for new_flight_path.fxml.
 * Created by Sunguin.
 */
public class NewPathController extends Controller {
    @FXML
    private TextField sourceAirport;
    @FXML
    private TextField destinationAirport;
    @FXML
    private Button addButton;

    private Dataset theDataSet = null;

    public void load() {
        theDataSet = getParent().getCurrentDataset();
    }

    /**
     * Attempts to add a new flight path. First uses the entry parser to check for valid ICAO codes.
     */
    public void addPath() {
        EntryParser airportCheck = new EntryParser();
        try {
            airportCheck.parsePointName(sourceAirport.getText());
            airportCheck.parsePointName(destinationAirport.getText());
            theDataSet.addFlightPath(sourceAirport.getText(), destinationAirport.getText());

            //Saying to the user that the flight path has successfully added.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Flight Path Add Successful");
            alert.setHeaderText("New Flight Path added!");
            alert.setContentText("Your new flight path has been successfully added into the database.");
            alert.showAndWait();

            //Closes the add form.
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            //Tells the user what and where the error is.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Flight Path Data Error");
            alert.setHeaderText("Error adding a custom flight path entry.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
