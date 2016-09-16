package seng202.group9.Controller;

import java.io.*;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seng202.group9.GUI.*;

/**
 * Main Application frame of the Flight Data Analyser.
 *
 */
public class App extends Application
{
	private ArrayList<Dataset> datasets = new ArrayList<Dataset>();
	private Dataset currentDataset = null;
	private Stage primaryStage = null;
	private VBox mainContainer;
	private Session session;
	
    public static void main( String[] args )
    {
        launch(args);
    }
    /**
     * Starts the application
     * @param primaryStage main "stage" of the program
     */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		//load the menu and the first container
		try {
			FXMLLoader loader = new FXMLLoader();
			InputStream in = getClass().getClassLoader().getResourceAsStream("menu.fxml");
			mainContainer = (VBox) loader.load(in);
			Scene scene = new Scene(mainContainer, 800, 600);
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			MenuController menuController = (MenuController) loader.getController();
			menuController.setApp(this);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		primaryStage.show();

		//testing out dataset
		try {
			currentDataset = new Dataset("test's", Dataset.getExisting);
		}catch (DataException e){
			e.printStackTrace();

		}
/*
		AirlineFilter filter = new AirlineFilter(currentDataset.getAirlines());
		filter.filterName("NZ");
		filter.filterAlias("flight");
		filter.printFilter();

		//testout single route adding
		try {
			currentDataset.addRoute("D2", "MAC", "WIN", "Y", "0", "NOW");
		}catch (DataException e){
			e.printStackTrace();
		}
		//testout single airport adding
		try {
			currentDataset.addAirport("Windows 10", "PC", "Windows", "WIN", "WIND", "0.0", "0.0", "0.0", "0.0", "U", "PC/Windows");
		}catch (DataException e){
			e.printStackTrace();
		}
		//testout single airline adding
		try {
			currentDataset.addAirline("Dota2", "Valve", "D2", "DOT", "Defence of the Ancients", "Steam", "Y");
		}catch (DataException e){
			e.printStackTrace();
		}
		//testing out airport parser
		try {
			System.out.println(currentDataset.importAirport("res/Samples/Airports.txt"));
		} catch (DataException e) {
			e.printStackTrace();
		}
		//testing out airline parser
		try{
			System.out.println(currentDataset.importAirline("res/Samples/Airlines.txt"));
		} catch (DataException e){
			e.printStackTrace();
		}
		//testing out route parser
		try {
			System.out.println(currentDataset.importRoute("res/Samples/Routes.txt"));
		} catch (DataException e) {
			e.printStackTrace();
		}

		//testing out flight parser
        try {
            System.out.println(currentDataset.importFlight("res/Samples/NZCH-WSSS.csv"));
        } catch (DataException e) {
            e.printStackTrace();
        }*/
		//after all loading then load the previous session
		try{
			FileInputStream fileIn = new FileInputStream("res/session.ser");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			session = (Session) objectIn.readObject();
			Controller controller = (Controller) replaceSceneContent(session.getSceneDisplayed());
			controller.setApp(this);
			controller.load();
			controller.loadOnce();/*
			if (session.getSceneDisplayed() == SceneCode.AIRLINE_RAW_DATA){
				Controller controller = (Controller) replaceSceneContent(session.getSceneDisplayed());
				controller.setApp(this);
				controller.load();
			}else if (session.getSceneDisplayed() == SceneCode.AIRLINE_SUMMARY){
				Controller controller = (Controller) replaceSceneContent(session.getSceneDisplayed());
				controller.setApp(this);
				controller.load();
			}else if (session.getSceneDisplayed() == SceneCode.AIRPORT_RAW_DATA){
				AirportRDController controller = (AirportRDController) replaceSceneContent(session.getSceneDisplayed());
				controller.setApp(this);
				controller.loadTables();
			}else if (session.getSceneDisplayed() == SceneCode.AIRPORT_SUMMARY){
				AirportSummaryController controller = (AirportSummaryController) replaceSceneContent(session.getSceneDisplayed());
				controller.setApp(this);
				controller.loadTables();
			}else if (session.getSceneDisplayed() == SceneCode.ROUTE_RAW_DATA){
				RouteRDController controller = (RouteRDController) replaceSceneContent(session.getSceneDisplayed());
				controller.setApp(this);
				controller.loadTables();
			}else if (session.getSceneDisplayed() == SceneCode.ROUTE_RAW_DATA){
				RouteSummaryController controller = (RouteSummaryController) replaceSceneContent(session.getSceneDisplayed());
				controller.setApp(this);
				controller.loadTables();
			}else if (session.getSceneDisplayed() == SceneCode.FLIGHT_RAW_DATA){
				FlightRawDataController controller = (FlightRawDataController) replaceSceneContent(session.getSceneDisplayed());
				controller.setApp(this);
				controller.loadTables();
			}else if (session.getSceneDisplayed() == SceneCode.FLIGHT_SUMMARY){
				FlightSummaryController controller = (FlightSummaryController) replaceSceneContent(session.getSceneDisplayed());
				controller.setApp(this);
				controller.flightPathListView();
			}*/
			objectIn.close();
			fileIn.close();
		}catch(IOException e){
			session = new Session();
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			System.out.println("Missing Session Class");
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Serialize the exiting session
	 */
	public void stop(){
		try{
			FileOutputStream fileOut = new FileOutputStream("res/session.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(session);
			out.close();
			fileOut.close();
			System.out.println("Session has been serialised for next load");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * Replace Scene Content with fxml file code from oracle.
	 * @param fxml
	 * @return
	 * @throws Exception
	 */
	public Initializable replaceSceneContent(SceneCode fxml) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		InputStream in = getClass().getClassLoader().getResourceAsStream(fxml.getFilePath());
		Parent page;
		try {
			page = (Parent) loader.load(in);
		} finally {
			in.close();
		}
		while(mainContainer.getChildren().size() > 1) {
			mainContainer.getChildren().remove(1);
		}
		mainContainer.getChildren().add(page);
		//change session code to fit with the serialisation
		session.setSceneDisplayed(fxml);
		return (Initializable) loader.getController();
	}

	public Dataset getCurrentDataset(){
		return currentDataset;
	}

	public void deleteDataset(Dataset dataset){
		dataset.deleteDataset();
		datasets.remove(dataset);
	}
}
