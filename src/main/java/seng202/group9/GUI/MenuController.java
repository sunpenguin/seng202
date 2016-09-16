package seng202.group9.GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;
import seng202.group9.Controller.App;
import seng202.group9.Controller.SceneCode;

public class MenuController extends Controller{

	public void importAirports(){
		JOptionPane.showMessageDialog(null, "This is not Implemented yet");
	}
	
	public void importAirlines(){
		JOptionPane.showMessageDialog(null, "This is not Implemented yet");
	}

	public void importRoutes(){
		JOptionPane.showMessageDialog(null, "This is not Implemented yet");
	}
	
	public void importFlightData(){
		JOptionPane.showMessageDialog(null, "This is not Implemented yet");
	}

	/**
	 * Load Airline Raw Data Function.
	 */
	public void viewAirlineRawData() {
		replaceSceneContent(SceneCode.AIRPORT_RAW_DATA);
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

	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
