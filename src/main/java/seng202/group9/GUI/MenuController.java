package seng202.group9.GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;
import seng202.group9.Controller.App;

public class MenuController implements Initializable{

	App parent;

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
		try {
            AirlineRDController summaryController = (AirlineRDController) parent.replaceSceneContent("airline_raw_data.fxml");
            summaryController.setApp(parent);
            summaryController.loadTables();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewAirportRawData() {
		try {
            AirportRDController summaryController = (AirportRDController) parent.replaceSceneContent("airport_raw_data.fxml");
            summaryController.setApp(parent);
            summaryController.loadTables();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewRouteRawData() {
		try {
			RouteRDController summaryController = (RouteRDController) parent.replaceSceneContent("route_raw_data.fxml");
			summaryController.setApp(parent);
			//summaryController.loadTables();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewAirlineSummary() {
		try {
			AirlineSummaryController summaryController = (AirlineSummaryController) parent.replaceSceneContent("airline_summary.fxml");
			summaryController.setApp(parent);
			summaryController.loadTables();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewAirportSummary() {
		try {
			AirlineSummaryController summaryController = (AirportSummaryController) parent.replaceSceneContent("airport_summary.fxml");
			summaryController.setApp(parent);
			summaryController.loadTables();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setApp(App parent){
		this.parent = parent;
	}

	/**
	 * Load Flight Summary Function.
	 */
	public void viewFlightSummary() {
		try {
			FlightSummaryController summaryController = (FlightSummaryController) parent.replaceSceneContent("flight_data_summary.fxml");
			summaryController.setApp(parent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load Flight Raw Data Function.
	 */
	public void viewFlightRawData() {
		try {
			FlightRawDataController rawDataController = (FlightRawDataController)
					parent.replaceSceneContent("flight_raw_data.fxml");
			rawDataController.setApp(parent);
			rawDataController.loadTables();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
