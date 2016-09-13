package seng202.group9.Controller;

import java.io.IOException;
import java.io.InputStream;
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
import seng202.group9.GUI.MenuController;

/**
 * Main Application frame of the Flight Data Analyser.
 *
 */
public class App extends Application
{
	private ArrayList<Dataset> Datasets = new ArrayList<Dataset>();
	private Dataset currentDataset = null;
	private Stage primaryStage = null;
	
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
		try {
			MenuController menuController = new MenuController();
			menuController.setApp(this);
			replaceSceneContent("menu.fxml");//replace this to check your fxml file
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
	}

	/**
	 * Replace Scene Content with fxml file code from oracle.
	 * @param fxml
	 * @return
	 * @throws Exception
	 */
	public Initializable replaceSceneContent(String fxml) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		InputStream in = getClass().getClassLoader().getResourceAsStream(fxml);
		InputStream menuIn = getClass().getClassLoader().getResourceAsStream("menu.fxml");
		VBox page;
		try {
			page = (VBox) loader.load(menuIn);
			Parent content = loader.load(getClass().getClassLoader().getResource(fxml));
			page.getChildren().add(content);
		} finally {
			in.close();
		}
		Scene scene = new Scene(page, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		return (Initializable) loader.getController();
	}

	public Dataset getCurrentDataset(){
		return currentDataset;
	}
}
