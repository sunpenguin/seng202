package seng202.group9.GUI;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;

/**
 * Created By Fan-Wu Yang (fwy13)
 */
public class MenuController extends Controller{
	/**
	 * import airports from file
	 */
	public void importAirports(){
		if (getParent().getCurrentDataset() == null){
			createPopUpStage(SceneCode.DATASET_CONTROLLER, 600, 400);
			if (getParent().getCurrentDataset() != null){
				Importer importer = new Importer(SceneCode.AIRPORT_RAW_DATA, getParent(), getParent().getPrimaryStage());
			}
		}else {
			changeDatasetPrompt();
			Importer importer = new Importer(SceneCode.AIRPORT_RAW_DATA, getParent(), getParent().getPrimaryStage());
		}
	}

	/**
	 * import airlines from file
	 */
	public void importAirlines(){
		if (getParent().getCurrentDataset() == null){
			createPopUpStage(SceneCode.DATASET_CONTROLLER, 600, 400);
			if (getParent().getCurrentDataset() != null){
				Importer importer = new Importer(SceneCode.AIRLINE_RAW_DATA, getParent(), getParent().getPrimaryStage());
			}
		}else {
			changeDatasetPrompt();
			Importer importer = new Importer(SceneCode.AIRLINE_RAW_DATA, getParent(), getParent().getPrimaryStage());
		}
	}

	/**
	 * Import routes from file.
	 */
	public void importRoutes(){
		if (getParent().getCurrentDataset() == null){
			createPopUpStage(SceneCode.DATASET_CONTROLLER, 600, 400);
			if (getParent().getCurrentDataset() != null){
				Importer importer = new Importer(SceneCode.ROUTE_RAW_DATA, getParent(), getParent().getPrimaryStage());
			}
		}else {
			changeDatasetPrompt();

			Importer importer = new Importer(SceneCode.ROUTE_RAW_DATA, getParent(), getParent().getPrimaryStage());
		}
	}

	/**
	 * Import flight data from file.
	 */
	public void importFlightData(){
		if (getParent().getCurrentDataset() == null){
			createPopUpStage(SceneCode.DATASET_CONTROLLER, 600, 400);
			if (getParent().getCurrentDataset() != null){
				Importer importer = new Importer(SceneCode.FLIGHT_RAW_DATA, getParent(), getParent().getPrimaryStage());
			}
		}else {
			changeDatasetPrompt();
			Importer importer = new Importer(SceneCode.FLIGHT_RAW_DATA, getParent(), getParent().getPrimaryStage());
		}
	}

	/**
	 * Load Airline Raw Data Function.
	 */
	public void viewAirlineRawData() {
		replaceSceneContent(SceneCode.AIRLINE_RAW_DATA);
	}

	/**
	 * view Airport Raw Data
	 */
	public void viewAirportRawData() {
		replaceSceneContent(SceneCode.AIRPORT_RAW_DATA);
	}

	/**
	 * view Routes Raw Datat
	 */
	public void viewRouteRawData() {
		replaceSceneContent(SceneCode.ROUTE_RAW_DATA);
	}

	/**
	 * View Airlines SUmmary Data
	 */
	public void viewAirlineSummary() {
		replaceSceneContent(SceneCode.AIRLINE_SUMMARY);
	}

	/**
	 * View Airport SUmmary Data
	 */
	public void viewAirportSummary() {
		replaceSceneContent(SceneCode.AIRPORT_SUMMARY);
	}

	/**
	 * View ROutes Summary Data
	 */
	public void viewRouteSummary() {
		replaceSceneContent(SceneCode.ROUTE_SUMMARY);
	}

	/**
	 * view Routes by the Destination / Arrival Airport
	 */
	public void viewRouteByAirport(){
		//check if there is internet connectivity
		if (!getParent().testInet("maps.google.com")){
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("No Internet Connection.");
			alert.setHeaderText("Unable to Connect to Google Maps");
			alert.setContentText("As we are unable to connect to Google Maps all applications which are supposed to display maps may not work as intended.");
			alert.showAndWait();
		}else {
			replaceSceneContent(SceneCode.ROUTE_BY_AIRPORT);
		}
	}
	/**
	 * Load Flight Summary Function.
	 */
	public void viewFlightSummary() {
		replaceSceneContent(SceneCode.FLIGHT_SUMMARY);
	}
	/**
	 * Load Flight Raw Data Function.
	 */
	public void viewFlightRawData() {
		replaceSceneContent(SceneCode.FLIGHT_RAW_DATA);
	}

	/**
	 * View Routes by Equipment navigation.
	 */
	public void viewRouteByEquipment(){
		//check if there is internet connectivity
		if (!getParent().testInet("maps.google.com")){
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("No Internet Connection.");
			alert.setHeaderText("Unable to Connect to Google Maps");
			alert.setContentText("As we are unable to connect to Google Maps all applications which are supposed to display maps may not work as intended.");
			alert.showAndWait();
		}else {
			replaceSceneContent(SceneCode.ROUTE_BY_EQUIP);
		}
	}

	/**
	 * open getting starting home page
	 */
	public void goToGettingStarted() {
		replaceSceneContent(SceneCode.INITIAL);
	}

	/**
	 * open help maneul
	 */
	public void goToHelp() {
        createPopUpStage(SceneCode.HELP, 600, 400);
    }

	/**
	 * initial loader
	 */
	public void load() {
		//nothing to load
	}

	/**
	 * open view distance calculator.
	 */
	public void viewDistCalc(){replaceSceneContent(SceneCode.AIRPORT_DIST_CALC);}

	/**
	 * view airport graphs
	 */
	public void viewAirportGraphs(){
		replaceSceneContent(SceneCode.AIRPORT_GRAPHS);
	}

	/**
	 * view airline graphs
	 */
	public void viewAirlineGraphs(){
		replaceSceneContent(SceneCode.AIRLINE_GRAPHS);
	}

	/**
	 * view routes graphs
	 */
	public void viewRouteGraphs(){
		replaceSceneContent(SceneCode.ROUTE_GRAPHS);
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * change dataset prompt Yes or No.
	 */
	public void changeDatasetPrompt(){
		ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
		ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", yes, no);
		alert.setTitle("Dataset Change?");
		alert.setHeaderText("You are about to import Data");
		alert.setContentText("Would you like to change Datasets?");
		//alert.showAndWait();
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == yes) {
			createPopUpStage(SceneCode.DATASET_CONTROLLER, 600, 400);
		}
	}

	/**
	 * Open Dataset Chooser popup.
	 */
	public void openDataset(){
		createPopUpStage(SceneCode.DATASET_CONTROLLER, 600, 400);
	}
	
}
