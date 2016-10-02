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
import seng202.group9.Core.Airline;


/**
 * The GUI controller class for airline_edit_form.fxml.
 * Extends from the abstract class {@link Controller}.
 * Created by Sunguin.
 */
public class AirlineEditController extends Controller {
    //Setting up text fields for editing data.
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
    @FXML
    private GridPane airlineContainer;

    private Dataset theDataSet = null;
    private Session currentSession = null;
    private Airline toEdit = null;


    /**
     * Loads up the current dataset and current session.
     * Also gets the airline to be edited from the table.
     * Sets the text fields as the airline selected.
     */
    public void load() {
        theDataSet = getParent().getCurrentDataset();
        currentSession = getParent().getSession();

        airlineContainer.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    editAirline();
                }
            }
        });

        toEdit = theDataSet.getAirlineDictionary().get(currentSession.getAirlineToEdit());

        airlNameEdit.setText(toEdit.getName());
        airlAliasEdit.setText(toEdit.getAlias());
        airlIATAEdit.setText(toEdit.getIATA());
        airlICAOEdit.setText(toEdit.getICAO());
        airlCallsignEdit.setText(toEdit.getCallSign());
        airlCountryEdit.setText(toEdit.getCountryName());
        airlActiveEdit.setText(toEdit.getActive());
    }


    /**
     * Edits the current airline and closes the popup window.
     * Takes in the values from the text fields.
     * @see Dataset
     */
    public void editAirline() {
        //Tries to edit an airline and comes up with a popup if successful and exits the form
        try {
            EntryParser parser = new EntryParser();
            parser.parseAirline(airlNameEdit.getText(), airlAliasEdit.getText(), airlIATAEdit.getText(),
                    airlICAOEdit.getText(), airlCallsignEdit.getText(), airlCountryEdit.getText(), airlActiveEdit.getText());
            theDataSet.editAirline(toEdit, airlNameEdit.getText(), airlAliasEdit.getText(), airlIATAEdit.getText(),
                    airlICAOEdit.getText(), airlCallsignEdit.getText(), airlCountryEdit.getText(), airlActiveEdit.getText());

            //Saying to the user that the airline has successfully edited.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Airline Edit Successful");
            alert.setHeaderText("Airline data edited!");
            alert.setContentText("Your airline data has been successfully edited.");
            alert.showAndWait();

            //Close the edit form.
            Stage stage = (Stage) applyButton.getScene().getWindow();
            stage.close();
        } catch (DataException e) {
            System.err.println(e.getMessage());
        }
    }
}
