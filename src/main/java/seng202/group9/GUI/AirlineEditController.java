package seng202.group9.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.Session;
import seng202.group9.Core.Airline;

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

    public void editAirline() {

    }

    public void load() {
        theDataSet = getParent().getCurrentDataset();
        currentSession = getParent().getSession();

        //System.out.println(currentSession.getAirlineToEdit());
        Airline toEdit = theDataSet.getAirlineDictionary().get(currentSession.getAirlineToEdit());

        airlNameEdit.setText(toEdit.getName());
        airlAliasEdit.setText(toEdit.getAlias());
        airlIATAEdit.setText(toEdit.getIATA());
        airlICAOEdit.setText(toEdit.getICAO());
        airlCallsignEdit.setText(toEdit.getCallSign());
        airlCountryEdit.setText(toEdit.getCountryName());
        airlActiveEdit.setText(toEdit.getActive());
    }
}
