package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import seng202.group9.Controller.AirlineFilter;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.Airline;

/**
 * Created by Sunguin on 2016/09/22.
 */
public class AirlineFilterController extends AirlineRDController{

    //Setting up text fields for filtering data
    @FXML
    private TextField airlNameFilter;
    @FXML
    private TextField airlAliasFilter;
    @FXML
    private TextField airlIATAFilter;
    @FXML
    private TextField airlICAOFilter;
    @FXML
    private TextField airlCallsignFilter;
    @FXML
    private TextField airlCountryFilter;
    @FXML
    private TextField airlActiveFilter;

    Dataset theDataSet = null;


    /**
     * Filters airlines by any field.
     * These are specified by what the user has typed in the filter boxes.
     * Updates the GUI accordingly.
     * @see AirlineFilter
     */
    public void filterAirlines() {
        //System.out.println(getData());
        //The filter function also operates like a search function
        //theDataSet = getParent().getCurrentDataset();
        AirlineFilter filter = new AirlineFilter(theDataSet.getAirlines());
        if (airlNameFilter.getText() != null) {
            filter.filterName(airlNameFilter.getText());
        }
        if (airlAliasFilter.getText() != null) {
            filter.filterAlias(airlAliasFilter.getText());
        }
        if (airlIATAFilter.getText() != null) {
            filter.filterIATA(airlIATAFilter.getText());
        }
        if (airlICAOFilter.getText() != null) {
            filter.filterICAO(airlICAOFilter.getText());
        }
        if (airlCallsignFilter.getText() != null) {
            filter.filterCallsign(airlCallsignFilter.getText());
        }
        if (airlCountryFilter.getText() != null) {
            filter.filterCountry(airlCountryFilter.getText());
        }
        if (airlActiveFilter.getText() != null) {
            filter.filterActive(airlActiveFilter.getText());
        }
        //Sets the data according to the criteria specified by the user.
        //setDataL(FXCollections.<Airline>observableArrayList(filter.getFilteredData()));
    }
}
