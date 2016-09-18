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
 * Created By Fan-Wu Yang (fwy13)
 */
public class App extends Application
{
	private ArrayList<Dataset> datasets = new ArrayList<Dataset>();
	private Dataset currentDataset = null;
	private Stage primaryStage = null;
	private VBox mainContainer = null;
	private Session session = null;
	private MenuController menuController = null;
	
    public static void main( String[] args )
    {
        launch(args);
    }

	public Stage getPrimaryStage() {
		return primaryStage;
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
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			menuController = (MenuController) loader.getController();
			menuController.setApp(this);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		primaryStage.show();

		//testing out dataset
		try {
			currentDataset = new Dataset("test's", Dataset.getExisting);
			datasets.add(currentDataset);
		}catch (DataException e){
			e.printStackTrace();
		}
		//after all loading then load the previous session
		try{
			FileInputStream fileIn = new FileInputStream("res/session.ser");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			session = (Session) objectIn.readObject();
			Controller controller = (Controller) replaceSceneContent(session.getSceneDisplayed());
			controller.setApp(this);
			controller.load();
			controller.loadOnce();
			objectIn.close();
			fileIn.close();
		}catch(IOException e){
			session = new Session();
			System.out.println("New Session File Created");
		}catch(ClassNotFoundException e){
			System.out.println("Missing Session Class");
			System.exit(1);
		} catch (Exception e) {
			session = new Session();
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

	/**
	 * Returns the Menu COntroller of the App.
	 * @return
	 */
	public MenuController getMenuController() {
		return menuController;
	}

	/**
	 * returns the current dataset that the user is using.
	 * @return
	 */
	public Dataset getCurrentDataset(){
		return currentDataset;
	}

	/**
	 * Creates new dataset.
	 * @param datasetName
	 * @throws DataException
	 */
	public void createDataset(String datasetName) throws DataException{
		Dataset newDataset = new Dataset(datasetName, Dataset.createNew);
		datasets.add(newDataset);
		currentDataset = newDataset;
	}

	/**
	 * gets the amount of datasets the user has.
	 * @return
	 */
	public ArrayList<Dataset> getDatasets() {
		return datasets;
	}

	/**
	 * deletes a dataset.
	 * @param dataset
	 */
	public void deleteDataset(Dataset dataset){
		dataset.deleteDataset();
		datasets.remove(dataset);
		if (dataset == currentDataset){
			if (datasets.size() > 0){
				currentDataset = datasets.get(0);
			}else{
				currentDataset = null;
			}
		}
	}
}
