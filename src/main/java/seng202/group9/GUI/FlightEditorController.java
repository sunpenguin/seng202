package seng202.group9.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.FlightPoint;

/**
 * Created by Liam Beckett on 23/09/2016.
 */
public class FlightEditorController extends Controller{
    //Setting up text fields for adding data
    @FXML
    TextField fNameEdit;
    @FXML
    TextField fTypeEdit;
    @FXML
    TextField fAltitudeEdit;
    @FXML
    TextField fLatitudeEdit;
    @FXML
    TextField fLongitudeEdit;


    //Set an empty Dataset to be assigned later
    private Dataset theDataSet = null;

    /**
     * Adds a single airport entry in the database.
     * Takes in values from the GUI the user has typed in.
     * @see Dataset
     */
    public void editFlight(FlightPoint flightPoint) {
        //Tries to add a new airport and clears the fields to their initial state if successful.
        //Otherwise an error message will pop up with what is wrong with the manual data.
        try {
            fNameEdit.setText(flightPoint.getName());
            fTypeEdit.setText(flightPoint.getType());
            fAltitudeEdit.setText(Double.toString(flightPoint.getAltitude()));
            fLatitudeEdit.setText(Double.toString(flightPoint.getLatitude()));
            fLongitudeEdit.setText(Double.toString(flightPoint.getLongitude()));

            theDataSet.editFlightPoint(
                    flightPoint,
                    fNameEdit.getText(),
                    fTypeEdit.getText(),
                    fAltitudeEdit.getText(),
                    fLatitudeEdit.getText(),
                    fLongitudeEdit.getText()
            );
            fNameEdit.clear();
            fTypeEdit.clear();
            fAltitudeEdit.clear();
            fLatitudeEdit.clear();
            fLongitudeEdit.clear();
        } catch ( Exception e ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Flight Data Error");
            alert.setHeaderText("Error editing a flight point.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void load() {
        theDataSet = getParent().getCurrentDataset();
    }
}

