package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group9.Controller.*;
import seng202.group9.Core.Airline;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Optional;


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

    //Set an empty Dataset to be assigned to the current dataset.
    private Dataset theDataSet = null;
    //Set an empty session to be assigned to the current session.
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
        currentSession = getParent().getSession();

        tableViewAirlineRD.setItems(FXCollections.observableArrayList(theDataSet.getAirlines()));
        tableViewAirlineRD.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    /**
     * Opens the Airline Add form.
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

        if (currentSession.getFilteredAirlines() != null) {
            ArrayList<Airline> d = new ArrayList();
            for (int i = 0; i < theDataSet.getAirlines().size(); i++) {
                if (currentSession.getFilteredAirlines().containsValue(theDataSet.getAirlines().get(i).getName())
                        && currentSession.getFilteredAirlines().containsKey(i)) {
                    d.add(theDataSet.getAirlines().get(i));
                }
            }
            tableViewAirlineRD.setItems(FXCollections.observableArrayList(d));
        }
    }


    /**
     * Deletes a single selected airline entry from the database.
     * Updates the GUI accordingly.
     * @see Dataset
     */
    public void deleteAirline() {
        //Gets an airline from the table and deletes it before updating the table
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
     */
    public void editAirline() {
        Airline toEdit = tableViewAirlineRD.getSelectionModel().getSelectedItem();
        currentSession.setAirlineToEdit(toEdit.getName());
        createPopUpStage(SceneCode.AIRLINE_EDIT, 600, 370);

        System.out.println(toEdit.getName() + "," + toEdit.getAlias() + "," + toEdit.getIATA() + "," + toEdit.getICAO()
        + "," + toEdit.getCallSign() + "," + toEdit.getCountryName() + "," + toEdit.getActive());

        tableViewAirlineRD.refresh();
    }

    /**
     * Analyses the current data and creates a graph based on the data.
     * Currently not implemented yet.
     */
    public void analyse_Button() {
        JOptionPane.showMessageDialog(null, "This is not Implemented yet");
    }

    /**
     * Goes to the airline summary page.
     */
    public void airlineSummaryButton() {
        replaceSceneContent(SceneCode.AIRLINE_SUMMARY);
    }
}
