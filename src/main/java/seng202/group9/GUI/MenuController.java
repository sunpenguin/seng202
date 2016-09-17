package seng202.group9.GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;

public class MenuController extends Controller{

	public void importAirports(){
		Importer importer = new Importer(SceneCode.AIRPORT_RAW_DATA, getParent(), getParent().getPrimaryStage());
	}
	
	public void importAirlines(){
		Importer importer = new Importer(SceneCode.AIRLINE_RAW_DATA, getParent(), getParent().getPrimaryStage());
	}

	public void importRoutes(){
		Importer importer = new Importer(SceneCode.ROUTE_RAW_DATA, getParent(), getParent().getPrimaryStage());
	}
	
	public void importFlightData(){
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

	public void load() {
		//nothing to load
	}

	public void veiwDistCalc(){replaceSceneContent(SceneCode.AIRPORT_DIST_CALC);}

	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
