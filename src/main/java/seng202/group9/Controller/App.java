package seng202.group9.Controller;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group9.Core.FlightPath;
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
		//after all loading then load the previous session
		try{
			FileInputStream fileIn = new FileInputStream("res/session.ser");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			session = (Session) objectIn.readObject();
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
		//load all datasets
		try{
			loadAllDatasets();
		} catch (Exception e){
			e.printStackTrace();
		}
		//testing out dataset
		try {
			if (session.getCurrentDataset() != null) {
				currentDataset = new Dataset(session.getCurrentDataset(), Dataset.getExisting);
			}else{
				createPopUpStage(SceneCode.DATASET_CONTROLLER, 600, 400);
			}
		}catch (DataException e){
			createPopUpStage(SceneCode.DATASET_CONTROLLER, 600, 400);
		}catch (NullPointerException e){
			createPopUpStage(SceneCode.DATASET_CONTROLLER, 600, 400);
		}catch (Exception e){
			createPopUpStage(SceneCode.DATASET_CONTROLLER, 600, 400);
		}
		//after all loading then load the previous session
		if (session.getSceneDisplayed() != null) {
			menuController.replaceSceneContent(session.getSceneDisplayed());
		}else{
			menuController.replaceSceneContent(SceneCode.INITIAL);
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
	 * Loads all dataset in the current User Database.
	 */
	public void loadAllDatasets(){
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:res/userdb.db");
			stmt = c.createStatement();
			String loadAllDatasetsQuery = "SELECT * FROM `Datasets`";
			ResultSet datasetsLoaded = stmt.executeQuery(loadAllDatasetsQuery);
			while (datasetsLoaded.next()){
				Dataset newDataset = new Dataset(datasetsLoaded.getString("Dataset_Name"), Dataset.getExisting);
				System.out.println("Loaded Dataset "+ datasetsLoaded.getString("Dataset_Name"));
				datasets.add(newDataset);
			}
			datasetsLoaded.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
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
     * Gets the current session.
     * @return
     */
	public Session getSession() {
		return this.session;
	}

	/**
	 * Returns the Menu Controller of the App.
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
	 * Sets the current Dataset to another Dataset by its index in the datasets arraylist
	 * @param index
     */
	public void setCurrentDataset(int index){
		currentDataset = datasets.get(index);
		session.setCurrentDataset(currentDataset.getName());
	}

	/**
	 * Sets the current Dataset to another Dataset.
	 * @param dataset
     */
	public void setCurrentDataset(Dataset dataset){
		currentDataset = dataset;
		session.setCurrentDataset(currentDataset.getName());
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

	public Stage createPopUpStage(SceneCode scene, int width, int height) {
		FXMLLoader loader = new FXMLLoader();
		InputStream in = getClass().getClassLoader().getResourceAsStream(scene.getFilePath());
		Parent page = null;
		try {
			page = loader.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//set contorller and call default calls
		Controller controller = (Controller) loader.getController();
		controller.setApp(this);
		controller.load();
		controller.loadOnce();
		//create a new stage to popup
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.WINDOW_MODAL);
		//inner layout constraints
		VBox container = new VBox();
		container.getChildren().add(page);
		Scene popupScene = new Scene(container, width, height);
		//show
		popupStage.setScene(popupScene);
		popupStage.showAndWait();
		return popupStage;
	}
}
