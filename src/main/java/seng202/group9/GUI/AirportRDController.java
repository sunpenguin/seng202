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
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
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

//        airpTimezonecol.setCellFactory(new Callback<TableColumn<Airport, String>, TableCell<Airport, String>>() {
//
//            @Override
//            public TableCell<Airport, City> call(TableColumn<Airport, City> param) {
//                TableCell<Airport, City> timeZoneCell = new TableCell<Airport, City>() {
//                    @Override
//                    protected void updateItem(City timezone, boolean empty) {
//                        if (timezone != null) {
//                            Label timeZoneLabel = new Label(timezone.getTimeOlson());
//                            setGraphic(timeZoneLabel);
//                        }
//                    }
//                };
//
//                return timeZoneCell;
//            }
//        });
        theDataSet = getParent().getCurrentDataset();
        tableViewAirportRD.setItems(FXCollections.observableArrayList(theDataSet.getAirports()));

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
    public void airportAnalyserButton(){
        replaceSceneContent(SceneCode.AIRPORT_ANALYSER);
    }
}
