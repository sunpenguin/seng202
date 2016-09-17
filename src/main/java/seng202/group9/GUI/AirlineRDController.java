package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group9.Controller.AirlineFilter;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.Airline;

/**
 * The GUI controller class for airline_raw_data.fxml.
 * Extends from the abstract class {@link Controller}.
 * Created by Sunguin on 2016/09/13.
 */
public class AirlineRDController extends Controller {

    @FXML
    private TableView<Airline> tableViewAirlineRD;
    @FXML
    private TableColumn<Airline, String> airlIDcol;
    @FXML
    private TableColumn<Airline, String> airlNamecol;
    @FXML
    private TableColumn<Airline, String> airlAliascol;
    @FXML
    private TableColumn<Airline, String> airlIATAcol;
    @FXML
    private TableColumn<Airline, String> airlICAOcol;
    @FXML
    private TableColumn<Airline, String> airlCallsigncol;
    @FXML
    private TableColumn<Airline, String> airlCountrycol;
    @FXML
    private TableColumn<Airline, String> airlActivecol;

    @FXML
    private TextField airlNameBox;
    @FXML
    private TextField airlAliasBox;
    @FXML
    private TextField airlIATABox;
    @FXML
    private TextField airlICAOBox;
    @FXML
    private TextField airlCallsignBox;
    @FXML
    private TextField airlCountryBox;
    @FXML
    private ComboBox<String> airlActiveCBox;

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

    private Dataset theDataSet = null;

    /**
     * Loads the initial airline data to the GUI table.
     * Also sets up the dropdown menu options.
     */
    public void load() {
        airlIDcol.setCellValueFactory(new PropertyValueFactory<Airline, String>("ID"));
        airlNamecol.setCellValueFactory(new PropertyValueFactory<Airline, String>("Name"));
        airlAliascol.setCellValueFactory(new PropertyValueFactory<Airline, String>("Alias"));
        airlIATAcol.setCellValueFactory(new PropertyValueFactory<Airline, String>("IATA"));
        airlICAOcol.setCellValueFactory(new PropertyValueFactory<Airline, String>("ICAO"));
        airlCallsigncol.setCellValueFactory(new PropertyValueFactory<Airline, String>("CallSign"));
        airlCountrycol.setCellValueFactory(new PropertyValueFactory<Airline, String>("CountryName"));
        airlActivecol.setCellValueFactory(new PropertyValueFactory<Airline, String>("Active"));

        theDataSet = getParent().getCurrentDataset();
        tableViewAirlineRD.setItems(FXCollections.observableArrayList(theDataSet.getAirlines()));

        airlActiveCBox.setValue("Y");
        airlActiveCBox.getItems().addAll("Y", "N");
    }

    /**
     * Adds a single airline entry to the database.
     * Takes in values from the GUI the user has typed in.
     * @see Dataset
     */
    public void addAirlineSingle() {
        try {
            theDataSet.addAirline(
                    airlNameBox.getText(),
                    airlAliasBox.getText(),
                    airlIATABox.getText(),
                    airlICAOBox.getText(),
                    airlCallsignBox.getText(),
                    airlCountryBox.getText(),
                    airlActiveCBox.getSelectionModel().getSelectedItem().toString());
            airlNameBox.clear();
            airlAliasBox.clear();
            airlIATABox.clear();
            airlICAOBox.clear();
            airlCallsignBox.clear();
            airlCountryBox.clear();
            airlActiveCBox.getSelectionModel().clearSelection();
            airlActiveCBox.setValue("Y");
            tableViewAirlineRD.setItems(FXCollections.observableArrayList(theDataSet.getAirlines()));
        } catch ( Exception e ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Airline Data Error");
            alert.setHeaderText("Error adding a custom airline entry.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Deletes a single selected airline entry from the database.
     * Updates the GUI accordingly.
     * @see Dataset
     */
    public void deleteAirline() {
        Airline toDelete = tableViewAirlineRD.getSelectionModel().getSelectedItem();
        theDataSet.deleteAirline(toDelete);
        tableViewAirlineRD.setItems(FXCollections.observableArrayList(theDataSet.getAirlines()));
    }

    /**
     * Filters airlines by any field.
     * These are specified by what the user has typed in the filter boxes.
     * Updates the GUI accordingly.
     * @see AirlineFilter
     */
    public void filterAirlines() {
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
        tableViewAirlineRD.setItems(FXCollections.<Airline>observableArrayList(filter.getFilteredData()));
    }
}
