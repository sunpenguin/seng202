package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.Airline;

/**
 * Created by Sunguin on 2016/09/13.
 */
public class AirlineRDController extends MenuController {

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
    private TextField airlActiveBox;
    @FXML
    private TextField airlIDBox;


    private Dataset theDataSet = null;

    App parent;

    public void setApp(App parent){
        this.parent = parent;
    }

    public void loadTables() {
        airlIDcol.setCellValueFactory(new PropertyValueFactory<Airline, String>("ID"));
        airlNamecol.setCellValueFactory(new PropertyValueFactory<Airline, String>("Name"));
        airlAliascol.setCellValueFactory(new PropertyValueFactory<Airline, String>("Alias"));
        airlIATAcol.setCellValueFactory(new PropertyValueFactory<Airline, String>("IATA"));
        airlICAOcol.setCellValueFactory(new PropertyValueFactory<Airline, String>("ICAO"));
        airlCallsigncol.setCellValueFactory(new PropertyValueFactory<Airline, String>("CallSign"));
        airlCountrycol.setCellValueFactory(new PropertyValueFactory<Airline, String>("Country"));
        airlActivecol.setCellValueFactory(new PropertyValueFactory<Airline, String>("Active"));

        theDataSet = this.parent.getCurrentDataset();
//        try{
//            System.out.println(theDataSet.importAirline("res/Samples/Airlines.txt"));
//        } catch (DataException e){
//            e.printStackTrace();
//        }
        tableView.setItems(FXCollections.observableArrayList(theDataSet.getAirlines()));
    }

    //Dummy function to test the add button.
    //Will edit when ID is added automatically.
    public void addAirlineSingle() {
        theDataSet.getAirlines().add(new Airline(
                Integer.parseInt(airlIDBox.getText()),
                airlNameBox.getText(),
                airlAliasBox.getText(),
                airlIATABox.getText(),
                airlICAOBox.getText(),
                airlCallsignBox.getText(),
                airlCountryBox.getText(),
                airlActiveBox.getText()));
        airlIDBox.clear();
        airlNameBox.clear();
        airlAliasBox.clear();
        airlIATABox.clear();
        airlICAOBox.clear();
        airlCallsignBox.clear();
        airlCountryBox.clear();
        airlActiveBox.clear();
    }
}
