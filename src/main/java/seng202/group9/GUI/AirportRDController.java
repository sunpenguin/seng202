package seng202.group9.GUI;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import seng202.group9.Controller.AirportFilter;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.Airport;
import seng202.group9.Core.City;
import seng202.group9.Core.Country;


/**
 * Created by Sunguin on 2016/09/13.
 */
public class AirportRDController extends Controller{

    @FXML
    private TableView<Airport> tableViewAirportRD;
    @FXML
    private TableColumn<Airport, String> airpIDcol;
    @FXML
    private TableColumn<Airport, String> airpNamecol;
    @FXML
    private TableColumn<Airport, String> airpCitycol;
    @FXML
    private TableColumn<Airport, String> airpCountrycol;
    @FXML
    private TableColumn<Airport, String> airpIATAFFAcol;
    @FXML
    private TableColumn<Airport, String> airpICAOcol;
    @FXML
    private TableColumn<Airport, String> airpLatitudecol;
    @FXML
    private TableColumn<Airport, String> airpLongitudecol;
    @FXML
    private TableColumn<Airport, String> airpAltitudecol;
    @FXML
    private TableColumn<Airport, String> airpTimezonecol;
    @FXML
    private TableColumn<Airport, String> airpDSTcol;
    @FXML
    private TableColumn<Airport, String> airpTzcol;

    @FXML
    private TextField airpNameBox;
    @FXML
    private TextField airpCityBox;
    @FXML
    private TextField airpCountryBox;
    @FXML
    private TextField airpIATAFFABox;
    @FXML
    private TextField airpICAOBox;
    @FXML
    private TextField airpLatitudeBox;
    @FXML
    private TextField airpLongitudeBox;
    @FXML
    private TextField airpAltitudeBox;
    @FXML
    private TextField airpTimezoneBox;
    @FXML
    private ComboBox<String> airpDSTCBox;
    @FXML
    private TextField airpTzBox;

    @FXML
    private TextField airpNameFilter;
    @FXML
    private TextField airpCityFilter;
    @FXML
    private TextField airpCountryFilter;
    @FXML
    private TextField airpIATAFFAFilter;
    @FXML
    private TextField airpICAOFilter;
    @FXML
    private TextField airpLatitudeFilter;
    @FXML
    private TextField airpLongitudeFilter;
    @FXML
    private TextField airpAltitudeFilter;
    @FXML
    private TextField airpTimezoneFilter;
    @FXML
    private TextField airpDSTFilter;
    @FXML
    private TextField airpTzFilter;

    private Dataset theDataSet = null;

    public void load() {
        airpIDcol.setCellValueFactory(new PropertyValueFactory<Airport, String>("ID"));
        airpNamecol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Name"));
        airpCitycol.setCellValueFactory(new PropertyValueFactory<Airport, String>("CityName"));
        airpCountrycol.setCellValueFactory(new PropertyValueFactory<Airport, String>("CountryName"));
        airpIATAFFAcol.setCellValueFactory(new PropertyValueFactory<Airport, String>("IATA_FFA"));
        airpICAOcol.setCellValueFactory(new PropertyValueFactory<Airport, String>("ICAO"));
        airpLatitudecol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Latitude"));
        airpLongitudecol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Longitude"));
        airpAltitudecol.setCellValueFactory(new PropertyValueFactory<Airport, String> ("Altitude"));
        airpTimezonecol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Timezone"));
        airpDSTcol.setCellValueFactory(new PropertyValueFactory<Airport, String>("DST"));
        airpTzcol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Tz"));

        theDataSet = getParent().getCurrentDataset();
        tableViewAirportRD.setItems(FXCollections.observableArrayList(theDataSet.getAirports()));

        airpDSTCBox.setValue("E");
        airpDSTCBox.getItems().addAll("E", "A", "S", "O", "Z", "N", "U");
    }

    public void addAirportSingle() {
        try {
            theDataSet.addAirport(
                    airpNameBox.getText(),
                    airpCityBox.getText(),
                    airpCountryBox.getText(),
                    airpIATAFFABox.getText(),
                    airpICAOBox.getText(),
                    airpLatitudeBox.getText(),
                    airpLongitudeBox.getText(),
                    airpAltitudeBox.getText(),
                    airpTimezoneBox.getText(),
                    airpDSTCBox.getSelectionModel().getSelectedItem().toString(),
                    airpTzBox.getText());
                airpCityBox.clear();
                airpCountryBox.clear();
                airpIATAFFABox.clear();
                airpICAOBox.clear();
                airpLatitudeBox.clear();
                airpLongitudeBox.clear();
                airpAltitudeBox.clear();
                airpTimezoneBox.clear();
                airpDSTCBox.getSelectionModel().clearSelection();
                airpTzBox.clear();
            tableViewAirportRD.setItems(FXCollections.observableArrayList(theDataSet.getAirports()));
        } catch ( Exception e ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Airport Data Error");
            alert.setHeaderText("Error adding a custom airport entry.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void deleteAirport(){
        Airport toDelete = tableViewAirportRD.getSelectionModel().getSelectedItem();
        theDataSet.deleteAirport(toDelete);
        tableViewAirportRD.setItems(FXCollections.observableArrayList(theDataSet.getAirports()));
    }

    public void filterAirports() {
        AirportFilter filter = new AirportFilter(theDataSet.getAirports());
        if (airpNameFilter.getText() != null) {
            filter.filterName(airpNameFilter.getText());
        }
        if (airpCityFilter.getText() != null) {
            filter.filterCity(airpCityFilter.getText());
        }
        if (airpCountryFilter.getText() != null) {
            filter.filterCountry(airpCountryFilter.getText());
        }
        if (airpIATAFFAFilter.getText() != null) {
            filter.filterIATA_FFA(airpIATAFFAFilter.getText());
        }
        if (airpICAOFilter.getText() != null) {
            filter.filterICAO(airpICAOFilter.getText());
        }
        if (airpLatitudeFilter.getText() != null) {
            filter.filterLatitude(airpLatitudeFilter.getText());
        }
        if (airpLongitudeFilter.getText() != null) {
            filter.filterLongitude(airpLongitudeFilter.getText());
        }
        if (airpAltitudeFilter.getText() != null) {
            filter.filterAltitude(airpAltitudeFilter.getText());
        }
        if (airpTimezoneFilter.getText() != null) {
            filter.filterTimezone(airpTimezoneFilter.getText());
        }
        if (airpDSTFilter.getText() != null) {
            filter.filterDST(airpDSTFilter.getText());
        }
        if (airpTzFilter.getText() != null) {
            filter.filterOlson(airpTzFilter.getText());
        }
        tableViewAirportRD.setItems(FXCollections.<Airport>observableArrayList(filter.getFilteredData()));
    }
}
