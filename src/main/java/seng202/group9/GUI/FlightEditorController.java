package seng202.group9.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.Session;
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
    @FXML
    private Button flightEditButton;

    //Set an empty Dataset to be assigned later
    private Dataset theDataSet = null;

    /**
     * Adds a single airport entry in the database.
     * Takes in values from the GUI the user has typed in.
     * @see Dataset
     */
    public void editFlight() {
        //Tries to add a new airport and clears the fields to their initial state if successful.
        //Otherwise an error message will pop up with what is wrong with the manual data.
        try {
            Session session = getParent().getSession();
            int flightPointID = session.getCurrentFlightPointID();
            int flightPathID = session.getCurrentFlightPathID();

            theDataSet.editFlight(
                    theDataSet.getFlightPointDictionary().get(flightPointID),
                    fNameEdit.getText(),
                    fTypeEdit.getText(),
                    fAltitudeEdit.getText(),
                    fLatitudeEdit.getText(),
                    fLongitudeEdit.getText()
            );
            session.setCurrentFlightPointID(flightPointID);

            fNameEdit.clear();
            fTypeEdit.clear();
            fAltitudeEdit.clear();
            fLatitudeEdit.clear();
            fLongitudeEdit.clear();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Flight Path Edit Successful");
            alert.setHeaderText("Flight Point Edited!");
            alert.setContentText("Your flight point has been updated in the database.");
            alert.showAndWait();

            Stage stage = (Stage) flightEditButton.getScene().getWindow();
            stage.close();

        } catch ( Exception e ) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Flight Data Error");
            alert.setHeaderText("Error editing a flight point.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void loadValues(){

    }

    public void load() {
        theDataSet = getParent().getCurrentDataset();

        Session session = getParent().getSession();
        int flightPointID = session.getCurrentFlightPointID();
        FlightPoint flightPoint = theDataSet.getFlightPointDictionary().get(flightPointID);

        fNameEdit.setText(flightPoint.getName());
        fTypeEdit.setText(flightPoint.getType());
        fAltitudeEdit.setText(Double.toString(flightPoint.getAltitude()));
        fLatitudeEdit.setText(Double.toString(flightPoint.getLatitude()));
        fLongitudeEdit.setText(Double.toString(flightPoint.getLongitude()));
    }

}

