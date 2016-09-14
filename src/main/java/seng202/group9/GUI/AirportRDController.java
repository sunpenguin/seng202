package seng202.group9.GUI;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.Airport;
import seng202.group9.Core.City;
import seng202.group9.Core.Country;


/**
 * Created by Sunguin on 2016/09/13.
 */
public class AirportRDController extends MenuController{

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
    private TableColumn<Airport, City> airpTimezonecol;
    @FXML
    private TableColumn<Airport, Country> airpDSTcol;
    @FXML
    private TableColumn<Airport, City> airpTzcol;

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
    private TextField airpDSTBox;
    @FXML
    private TextField airpTzBox;


    private Dataset theDataSet = null;

    App parent;

    public void setApp(App parent){
        this.parent = parent;
    }

    public void loadTables() {
        airpIDcol.setCellValueFactory(new PropertyValueFactory<Airport, String>("ID"));
        airpNamecol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Name"));
        airpCitycol.setCellValueFactory(new PropertyValueFactory<Airport, String>("City"));
        airpCountrycol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Country"));
        airpIATAFFAcol.setCellValueFactory(new PropertyValueFactory<Airport, String>("IATA_FFA"));
        airpICAOcol.setCellValueFactory(new PropertyValueFactory<Airport, String>("ICAO"));
        airpLatitudecol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Latitude"));
        airpLongitudecol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Longitude"));
        airpAltitudecol.setCellValueFactory(new PropertyValueFactory<Airport, String>("Altitude"));
        airpTimezonecol.setCellValueFactory(new PropertyValueFactory<Airport, City>("Timezone"));
        airpDSTcol.setCellValueFactory(new PropertyValueFactory<Airport, Country>("DST"));
        airpTzcol.setCellValueFactory(new PropertyValueFactory<Airport, City>("Tz"));

//        airpTimezonecol.setCellFactory(new Callback<TableColumn<Airport, String>, TableCell<Airport, City>>() {
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
        theDataSet = this.parent.getCurrentDataset();
        tableViewAirportRD.setItems(FXCollections.observableArrayList(theDataSet.getAirports()));
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
                    airpDSTBox.getText(),
                    airpTzBox.getText());
                airpCityBox.clear();
                airpCountryBox.clear();
                airpIATAFFABox.clear();
                airpICAOBox.clear();
                airpLatitudeBox.clear();
                airpLongitudeBox.clear();
                airpAltitudeBox.clear();
                airpTimezoneBox.clear();
                airpDSTBox.clear();
                airpTzBox.clear();
            tableViewAirportRD.setItems(FXCollections.observableArrayList(theDataSet.getAirports()));
        } catch ( Exception e ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Airline Data Error");
            alert.setHeaderText("Error adding a custom airport entry.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
