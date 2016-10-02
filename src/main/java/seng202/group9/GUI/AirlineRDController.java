package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group9.Controller.*;
import seng202.group9.Core.Airline;

import java.util.ArrayList;
import java.util.Optional;


/**
 * The GUI controller class for airline_raw_data.fxml.
 * Extends from the abstract class {@link Controller}.
 * Created by Sunguin.
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

    private Dataset theDataSet = null;
    private Session currentSession = null;


    /**
     * Loads the initial airline data to the GUI table.
     */
    public void load() {
        if (!checkDataset()){
            return;
        }
        //Sets up the table columns to be ready for use for Airline data
        airlIDCol.setCellValueFactory(new PropertyValueFactory<Airline, String>("ID"));
        airlNameCol.setCellValueFactory(new PropertyValueFactory<Airline, String>("Name"));
        airlAliasCol.setCellValueFactory(new PropertyValueFactory<Airline, String>("Alias"));
        airlIATACol.setCellValueFactory(new PropertyValueFactory<Airline, String>("IATA"));
        airlICAOCol.setCellValueFactory(new PropertyValueFactory<Airline, String>("ICAO"));
        airlCallsignCol.setCellValueFactory(new PropertyValueFactory<Airline, String>("CallSign"));
        airlCountryCol.setCellValueFactory(new PropertyValueFactory<Airline, String>("CountryName"));
        airlActiveCol.setCellValueFactory(new PropertyValueFactory<Airline, String>("Active"));

        theDataSet = getParent().getCurrentDataset();
        currentSession = getParent().getSession();

        tableViewAirlineRD.setItems(FXCollections.observableArrayList(theDataSet.getAirlines()));
        //Allows the selection of multiple entries in the table.
        tableViewAirlineRD.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    /**
     * Opens the Airline Add form.
     * @see AirlineAddController
     */
    public void openAdd() {
        createPopUpStage(SceneCode.AIRLINE_ADD, 600, 370);
        tableViewAirlineRD.setItems(FXCollections.observableArrayList(theDataSet.getAirlines()));
    }


    /**
     * Opens the Airline Filter form.
     * @see AirlineFilterController
     */
    public void openFilter() {
        createPopUpStage(SceneCode.AIRLINE_FILTER, 600, 370);
        ArrayList<Airline> d = new ArrayList();
        for (int key: currentSession.getFilteredAirlines().keySet()){
            d.add(theDataSet.getAirlineDictionary().get(currentSession.getFilteredAirlines().get(key)));
        }
        tableViewAirlineRD.setItems(FXCollections.observableArrayList(d));
    }


    /**
     * Deletes a single selected airline entry from the database.
     * Updates the GUI accordingly.
     * @see Dataset
     */
    public void deleteAirline() {
        ObservableList<Airline> toDelete = tableViewAirlineRD.getSelectionModel().getSelectedItems();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Airline Delete Confirmation");
        alert.setHeaderText("You are about to delete some data.");
        alert.setContentText("Are you sure you want to delete the selected airline(s)?");
        Optional<ButtonType> result = alert.showAndWait();
        Airline air = null;
        if (result.isPresent() && result.get() == ButtonType.OK) {
            for (int i = 0; i < toDelete.size(); i++) {
                air = toDelete.get(i);
                theDataSet.deleteAirline(air);
            }
            tableViewAirlineRD.setItems(FXCollections.observableArrayList(theDataSet.getAirlines()));
        }
    }


    /**
     * Opens the Airline Edit form.
     * @see AirlineEditController
     */
    public void editAirline() {
        Airline toEdit = tableViewAirlineRD.getSelectionModel().getSelectedItem();
        currentSession.setAirlineToEdit(toEdit.getName());
        createPopUpStage(SceneCode.AIRLINE_EDIT, 600, 370);
        tableViewAirlineRD.refresh();
    }


    /**
     * Analyses the current data and creates a graph based on the data.
     * @see AirlineGraphController
     */
    public void analyse_Button() {
        replaceSceneContent(SceneCode.AIRLINE_GRAPHS);
    }


    /**
     * Goes to the airline summary page.
     * @see AirlineSummaryController
     */
    public void airlineSummaryButton() {
        replaceSceneContent(SceneCode.AIRLINE_SUMMARY);
    }
}
