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
import seng202.group9.Controller.*;
import seng202.group9.Core.Airline;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
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

    //Set an empty Dataset to be assigned later.
    private Dataset theDataSet = null;
    private Session currentSession = null;

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

        currentSession = getParent().getSession();
    }


    /**
     * Opens the Airline add form.
     */
    public void openAdd() {
        createPopUpStage(SceneCode.AIRLINE_ADD, 600, 370);
        tableViewAirlineRD.setItems(FXCollections.observableArrayList(theDataSet.getAirlines()));
    }

    /**
     * Opens the Airline Filter form.
     */
    public void openFilter() {
        createPopUpStage(SceneCode.AIRLINE_FILTER, 600, 370);
        ArrayList<Airline> d = new ArrayList();
        for(int i = 0; i < theDataSet.getAirlines().size(); i++) {
            if (currentSession.getFilteredAirlines().containsValue(theDataSet.getAirlines().get(i).getName())
                    && currentSession.getFilteredAirlines().containsKey(i)) {
                d.add(theDataSet.getAirlines().get(i));
            }
        }
        tableViewAirlineRD.setItems(FXCollections.observableArrayList(d));
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
