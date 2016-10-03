package seng202.group9.GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Core.Airport;
import seng202.group9.Core.Equipment;
import seng202.group9.Core.Route;
import seng202.group9.Core.RoutePath;
import seng202.group9.Map.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by fwy13 on 2/10/16.
 */
public class EquipByRouteController extends Controller{
    @FXML
    WebView mapView;
    @FXML
    TableView<Equipment> equipTable;
    @FXML
    TableColumn<Airport, String> equipName;
    @FXML
    TableColumn<Airport, Integer> routes;
    ObservableList<Equipment> equipToDisplay;
    Dataset currentDataset;
    Map map;

    @Override
    public void load() {
        if (!checkDataset(SceneCode.ROUTE_BY_EQUIP)){
            return;
        }
        currentDataset = getParent().getCurrentDataset();
        //Sets up map.
        map = new Map(mapView, new RoutePath(), equipTable);
        equipName.setCellValueFactory(new PropertyValueFactory<Airport, String>("Name"));
        routes.setCellValueFactory(new PropertyValueFactory<Airport, Integer>("RouteNum"));
        equipToDisplay = FXCollections.observableArrayList();
        ArrayList<String> keys = new ArrayList<>(currentDataset.getEquipmentDictionary().keySet());
        for (int i = 0; i < currentDataset.getEquipmentDictionary().size(); i ++){
            if (currentDataset.getEquipmentDictionary().get(keys.get(i)).getRouteNum() > 0){
                equipToDisplay.add(currentDataset.getEquipmentDictionary().get(keys.get(i)));
            }
        }
        equipTable.setItems(equipToDisplay);
        equipTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Equipment>() {
            public void changed(ObservableValue<? extends Equipment> observable, Equipment oldValue, Equipment newValue) {
                Equipment selectedEquip= (Equipment) equipTable.getSelectionModel().getSelectedItems().get(0);
                ArrayList<RoutePath> routePaths = new ArrayList<RoutePath>();
                HashMap<Integer, Route> routes = selectedEquip.getRoutesUsed();
                for (int i = 0; i < routes.size(); i ++){
                    routePaths.add(routes.get(i).getRoutePath());
                }
                map.displayRoutes(routePaths);
            }
        });
    }
}
