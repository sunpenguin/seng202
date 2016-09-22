package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seng202.group9.Controller.AirlineFilter;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Core.Airline;

import javax.swing.*;
import java.io.IOException;
//make a class for the scenes to get the data I guess
/**
 * The GUI controller class for airline_raw_data.fxml.
 * Extends from the abstract class {@link Controller}.
 * Created by Sunguin
 */
public class AirlineRDController extends Controller {
    //Setting up the table from the FXML file
    @FXML
    private TableView<Airline> tableViewAirlineRD;
    @FXML
    private TableColumn<Airline, String> airlIDCol;
    @FXML
    private TableColumn<Airline, String> airlNameCol;
    @FXML
    private TableColumn<Airline, String> airlAliasCol;
    @FXML
    private TableColumn<Airline, String> airlIATACol;
    @FXML
    private TableColumn<Airline, String> airlICAOCol;
    @FXML
    private TableColumn<Airline, String> airlCallsignCol;
    @FXML
    private TableColumn<Airline, String> airlCountryCol;
    @FXML
    private TableColumn<Airline, String> airlActiveCol;

    //Setting up text fields for adding data
    @FXML
    private TextField airlNameAdd;
    @FXML
    private TextField airlAliasAdd;
    @FXML
    private TextField airlIATAAdd;
    @FXML
    private TextField airlICAOAdd;
    @FXML
    private TextField airlCallsignAdd;
    @FXML
    private TextField airlCountryAdd;
    @FXML
    private TextField airlActiveAdd;

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

    //Set an empty Dataset to be assigned later.
    private Dataset theDataSet = null;

    /**
     * Loads the initial airline data to the GUI table.
     * Also sets up the dropdown menu options.
     */
    public void load() {
        //Sets up the table columns to be ready for use for Airline data
        airlIDCol.setCellValueFactory(new PropertyValueFactory<Airline, String>("ID"));
        airlNameCol.setCellValueFactory(new PropertyValueFactory<Airline, String>("Name"));
        airlAliasCol.setCellValueFactory(new PropertyValueFactory<Airline, String>("Alias"));
        airlIATACol.setCellValueFactory(new PropertyValueFactory<Airline, String>("IATA"));
        airlICAOCol.setCellValueFactory(new PropertyValueFactory<Airline, String>("ICAO"));
        airlCallsignCol.setCellValueFactory(new PropertyValueFactory<Airline, String>("CallSign"));
        airlCountryCol.setCellValueFactory(new PropertyValueFactory<Airline, String>("CountryName"));
        airlActiveCol.setCellValueFactory(new PropertyValueFactory<Airline, String>("Active"));

        //Assigning the Dataset to the current Dataset's airlines and displaying it in a table
        theDataSet = getParent().getCurrentDataset();
        tableViewAirlineRD.setItems(FXCollections.observableArrayList(theDataSet.getAirlines()));
    }

    /**
     * Opens the Airline add form.
     */
    public void openAdd() {
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getClassLoader().getResource("airline_add_form.fxml"));
            Stage filter = new Stage();
            filter.initModality(Modality.APPLICATION_MODAL);
            filter.setResizable(false);
            filter.setTitle("Add New Airline");
            filter.setScene(new Scene(root, 600, 370));
            filter.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Adds a single airline entry to the database.
     * Takes in values from the GUI the user has typed in.
     * @see Dataset
     */
    public void addAirlineSingle() {
        //Tries to add a new airline and clears the fields to their initial state if successful.
        //Otherwise an error message will pop up with what is wrong with the manual data.

        //How to get this dataset into here to show data?
        try {
            theDataSet.addAirline(
                    airlNameAdd.getText(),
                    airlAliasAdd.getText(),
                    airlIATAAdd.getText(),
                    airlICAOAdd.getText(),
                    airlCallsignAdd.getText(),
                    airlCountryAdd.getText(),
                    airlActiveAdd.getText());
            airlNameAdd.clear();
            airlAliasAdd.clear();
            airlIATAAdd.clear();
            airlICAOAdd.clear();
            airlCallsignAdd.clear();
            airlCountryAdd.clear();
            airlActiveAdd.getText();
            tableViewAirlineRD.setItems(FXCollections.observableArrayList(theDataSet.getAirlines()));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Airline Data Error");
            alert.setHeaderText("Error adding a custom airline entry.");
            System.out.println(e);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Filters airlines by any field.
     * These are specified by what the user has typed in the filter boxes.
     * Updates the GUI accordingly.
     * @see AirlineFilter
     */
    public void filterAirlines() {
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
        tableViewAirlineRD.setItems(FXCollections.<Airline>observableArrayList(filter.getFilteredData()));
    }

    /**
     * Deletes a single selected airline entry from the database.
     * Updates the GUI accordingly.
     * @see Dataset
     */
    public void deleteAirline() {
        //Gets an airline from the table and deletes it before updating the table
        Airline toDelete = tableViewAirlineRD.getSelectionModel().getSelectedItem();
        theDataSet.deleteAirline(toDelete);
        tableViewAirlineRD.setItems(FXCollections.observableArrayList(theDataSet.getAirlines()));
    }

    /**
     * Opens the Airline Filter form.
     */
    public void openFilter() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/airline_filter_form.fxml"));
            final Stage filter = new Stage();
            filter.initModality(Modality.APPLICATION_MODAL);
            filter.setResizable(false);
            filter.setTitle("Airline Filter");
            filter.setScene(new Scene(root, 600, 370));
            filter.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Analyses the current data and creates a graph based on the data.
     * Currently not implemented yet.
     */
    public void analyse_Button() {
        JOptionPane.showMessageDialog(null, "This is not Implemented yet");
    }

    public void airlineSummaryButton() {
        replaceSceneContent(SceneCode.AIRLINE_SUMMARY);
    }
}
