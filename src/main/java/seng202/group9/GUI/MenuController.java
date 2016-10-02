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

	public void importAirports(){
		changeDatasetPrompt();
		Importer importer = new Importer(SceneCode.AIRPORT_RAW_DATA, getParent(), getParent().getPrimaryStage());
	}
	
	public void importAirlines(){
		changeDatasetPrompt();
		Importer importer = new Importer(SceneCode.AIRLINE_RAW_DATA, getParent(), getParent().getPrimaryStage());
	}

	public void importRoutes(){
		changeDatasetPrompt();
		Importer importer = new Importer(SceneCode.ROUTE_RAW_DATA, getParent(), getParent().getPrimaryStage());
	}
	
	public void importFlightData(){
		changeDatasetPrompt();
		Importer importer = new Importer(SceneCode.FLIGHT_RAW_DATA, getParent(), getParent().getPrimaryStage());
	}

	/**
	 * Load Airline Raw Data Function.
	 */
	public void viewAirlineRawData() {
		replaceSceneContent(SceneCode.AIRLINE_RAW_DATA);
	}

	public void viewAirportRawData() {
		replaceSceneContent(SceneCode.AIRPORT_RAW_DATA);
	}

	public void viewRouteRawData() {
		replaceSceneContent(SceneCode.ROUTE_RAW_DATA);
	}

	public void viewAirlineSummary() {
		replaceSceneContent(SceneCode.AIRLINE_SUMMARY);
	}

	public void viewAirportSummary() {
		replaceSceneContent(SceneCode.AIRPORT_SUMMARY);
	}

	public void viewRouteSummary() {
		replaceSceneContent(SceneCode.ROUTE_SUMMARY);
	}

	public void viewAnalyserMain() { replaceSceneContent(SceneCode.ANALYSER_TAB);}

	/**
	 * view Routes by the Destination / Arrival Airport
	 */
	public void viewRouteByAirport(){
		replaceSceneContent(SceneCode.ROUTE_BY_AIRPORT);
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

	public void viewRouteByEquipment(){
		replaceSceneContent(SceneCode.ROUTE_BY_EQUIP);
	}

	public void goToGettingStarted() {
		replaceSceneContent(SceneCode.INITIAL);
	}

	public void goToHelp() {
        createPopUpStage(SceneCode.HELP, 600, 400);
    }

	public void load() {
		//nothing to load
	}

	public void veiwDistCalc(){replaceSceneContent(SceneCode.AIRPORT_DIST_CALC);}

	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

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

	public void openDataset(){
		createPopUpStage(SceneCode.DATASET_CONTROLLER, 600, 400);
	}
	
}
