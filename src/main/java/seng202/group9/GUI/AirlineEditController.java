package seng202.group9.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.group9.Controller.DataException;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.Session;
import seng202.group9.Core.Airline;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sunguin on 2016/09/24.
 */
public class AirlineEditController extends Controller {
    //Setting up text fields for editing data
    @FXML
    private TextField airlNameEdit;
    @FXML
    private TextField airlAliasEdit;
    @FXML
    private TextField airlIATAEdit;
    @FXML
    private TextField airlICAOEdit;
    @FXML
    private TextField airlCallsignEdit;
    @FXML
    private TextField airlCountryEdit;
    @FXML
    private TextField airlActiveEdit;
    @FXML
    private Button applyButton;

    private Dataset theDataSet = null;
    private Session currentSession = null;

    private Airline toEdit = null;

    public void editAirline() {
        try {
            theDataSet.editAirline(toEdit, airlNameEdit.getText(), airlAliasEdit.getText(), airlIATAEdit.getText(),
                    airlICAOEdit.getText(), airlCallsignEdit.getText(), airlCountryEdit.getText(), airlActiveEdit.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Airline Edit Successful");
            alert.setHeaderText("Airline data edited!");
            alert.setContentText("Your airline data has been successfully edited.");
            alert.showAndWait();

            Stage stage = (Stage) applyButton.getScene().getWindow();
            stage.close();
        } catch (DataException e) {
            System.err.println("Harambe: " + e.getMessage());
        }

    }

    public void load() {
        theDataSet = getParent().getCurrentDataset();
        currentSession = getParent().getSession();

        toEdit = theDataSet.getAirlineDictionary().get(currentSession.getAirlineToEdit());

        airlNameEdit.setText(toEdit.getName());
        airlAliasEdit.setText(toEdit.getAlias());
        airlIATAEdit.setText(toEdit.getIATA());
        airlICAOEdit.setText(toEdit.getICAO());
        airlCallsignEdit.setText(toEdit.getCallSign());
        airlCountryEdit.setText(toEdit.getCountryName());
        airlActiveEdit.setText(toEdit.getActive());
    }
}
