package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.Airline;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Sunguin on 2016/09/13.
 */
public class AirlineRDController extends Controller {

    @FXML
    private TableView<Airline> tableView;
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
    //private TextField airlActiveBox;
    private ComboBox<String> airlActiveCBox;

    private Dataset theDataSet = null;

    //Dummy function to test the add button.
    //Will edit when ID is added automatically.
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
            tableView.setItems(FXCollections.observableArrayList(theDataSet.getAirlines()));
        } catch ( Exception e ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Airline Data Error");
            alert.setHeaderText("Error adding a custom airline entry.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void load() {
        airlIDcol.setCellValueFactory(new PropertyValueFactory<Airline, String>("ID"));
        airlNamecol.setCellValueFactory(new PropertyValueFactory<Airline, String>("Name"));
        airlAliascol.setCellValueFactory(new PropertyValueFactory<Airline, String>("Alias"));
        //Need to check IATA and ICAO
        airlIATAcol.setCellValueFactory(new PropertyValueFactory<Airline, String>("IATA"));
        airlICAOcol.setCellValueFactory(new PropertyValueFactory<Airline, String>("ICAO"));
        airlCallsigncol.setCellValueFactory(new PropertyValueFactory<Airline, String>("CallSign"));
        airlCountrycol.setCellValueFactory(new PropertyValueFactory<Airline, String>("CountryName"));
        airlActivecol.setCellValueFactory(new PropertyValueFactory<Airline, String>("Active"));

        theDataSet = getParent().getCurrentDataset();
        tableView.setItems(FXCollections.observableArrayList(theDataSet.getAirlines()));
        //ObservableList<String> activeOptions= FXCollections.observableArrayList("Y", "N");
        airlActiveCBox.getItems().addAll("Y", "N");
    }

}
