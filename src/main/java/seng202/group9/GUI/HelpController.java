package seng202.group9.GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * The GUI controller class for help.fxml.
 * Extends from the abstract class {@link Controller}.
 * Created by Sunguin.
 */
public class HelpController extends Controller {
    @FXML
    private TreeView treeView;
    @FXML
    private TextFlow textArea;

    Text text = new Text();

    //The TreeItems for the TreeView.
    TreeItem main_root = new TreeItem("Main Root");

    TreeItem importing = new TreeItem("Importing Data");
    TreeItem importing_start = new TreeItem("Importing on Startup");
    TreeItem importing_after = new TreeItem("Importing after Startup");

    TreeItem viewing = new TreeItem("Viewing Data");
    TreeItem summary_viewing = new TreeItem("Viewing Summary Data");
    TreeItem raw_viewing = new TreeItem("Viewing Raw Data");

    TreeItem manipulating = new TreeItem("Manipulating Data");
    TreeItem adding = new TreeItem("Adding Data");
    TreeItem filter = new TreeItem("Filtering Data");
    TreeItem edit = new TreeItem("Editing Data");
    TreeItem delete = new TreeItem("Deleting Data");

    TreeItem analysing = new TreeItem("Analysing Data");
    TreeItem graphing = new TreeItem("Graphs");
    TreeItem distance = new TreeItem("Distance Calculator");

    /**
     * Loads the TreeView and sets up the TreeView.
     */
    public void load() {
        treeView.setRoot(main_root);
        treeView.setShowRoot(false);

        main_root.getChildren().add(importing);
        importing.getChildren().add(importing_start);
        importing.getChildren().add(importing_after);

        main_root.getChildren().add(viewing);
        viewing.getChildren().add(summary_viewing);
        viewing.getChildren().add(raw_viewing);

        main_root.getChildren().add(manipulating);
        manipulating.getChildren().add(adding);
        manipulating.getChildren().add(filter);
        manipulating.getChildren().add(edit);
        manipulating.getChildren().add(delete);

        main_root.getChildren().add(analysing);
        analysing.getChildren().add(graphing);
        analysing.getChildren().add(distance);

        text = new Text("Please select an option on the left side menu to display its contents.");
        textArea.getChildren().add(text);
    }

    /**
     * Changes the text in the TextFlow depending on which option has been selected in the TreeView.
     */
    public void changeView() {
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            public void changed(ObservableValue observable, Object oldValue,
                                Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String menuValue = selectedItem.getValue();

                if ( menuValue != null ){
                textArea.getChildren().clear();
                    if (menuValue.equals("Importing on Startup")) {
                        text = new Text("Importing on Startup\n" + "You can import data from the first start up of the application." +
                                "\nTo import data, select the type of data you wish to import along the bottom of the screen." +
                                " Then select the file (.csv and .txt file) from the " +
                                "file selector. The data will be loaded into the program and taken to the " +
                                "corresponding summary page.");
                        textArea.getChildren().add(text);

                    } else if (menuValue.equals("Importing after Startup")) {
                        text = new Text("Importing after Startup\n" +
                                "You can import data from the 'File' menu on the top of the screen.\n" +
                                "To import data, select the type of data you wish to import. " +
                                "Then select the file (.csv and .txt file) from the file selector. " +
                                "The data will be loaded into the program and taken to the corresponding summary page.");
                        textArea.getChildren().add(text);

                    } else if (menuValue.equals("Viewing Summary Data")) {
                        text = new Text("Viewing Summary Data\n" +
                                "The summary data view consists of a reduced version of the data and a map of the currently selected value. " +
                                "The flight summary data also has a flight path selector to select which flight points you want to view.\n" +
                                "The summary views are accessible from the menu on the top of the screen under the " +
                                "'View' tab. You first choose which set of data you want to view and then you can select 'Summary'." +
                                "\nEach summary page also has buttons that lead to their corresponding raw data view and the other summary views.");
                        textArea.getChildren().add(text);

                    }  else if (menuValue.equals("Viewing Raw Data")) {
                        text = new Text("Viewing Raw Data\n" +
                                "The raw data view allows the user to view the full data table." +
                                "The user can also add, filter, edit and delete data values as well.\n" +
                                "These are accessible from the menu on the top of the screen under the " +
                                "'View' tab. You first choose which set of data you want to view and then you can select 'Raw Data'.\n" +
                                "The summary view does not have every column but provides a map of where the " +
                                "The raw data page also has buttons that lead to the analysis of that type of data and to go back to the" +
                                " corresponding summary page.");
                        textArea.getChildren().add(text);

                    } else if (menuValue.equals("Adding Data")) {
                        text = new Text("Adding Data\n" +
                                "To add a new entry, first go to the raw data view for that data type. Then click " +
                                "on the add button located on the bottom of the page. Then fill out the entries in the " +
                                "pop-up box and click add at the bottom of the screen. " +
                                "When the program detects an invalid field, a message will pop up and state where the error is.");
                        textArea.getChildren().add(text);

                    }  else if (menuValue.equals("Filtering Data")) {
                        text = new Text("Filtering Data\n" +
                                "To filter all current entries, click on the filter option and a pop " +
                                "up will appear. Then type in the fields the values you wish to filter by and press the filter button. " +
                                "The table should update with the fields specified.");
                        textArea.getChildren().add(text);

                    }  else if (menuValue.equals("Editing Data")) {
                        text = new Text("Editing Data\n" +
                                "The edit function can be accessed by right clicking on the entry you wish to edit and" +
                                " clicking the edit option. This will lead to a pop up where you can edit the current entry." +
                                " When the edit has been completed, you can press the apply button on the bottom of the pop up. " +
                                "When the program detects an invalid field, a message will pop up and state where the error is.");
                        textArea.getChildren().add(text);

                    }  else if (menuValue.equals("Deleting Data")) {
                        text = new Text("Deleting Data\n" +
                                "The delete function is accessed by right clicking an entry and pressing the delete option. " +
                                "This will come up with a pop up to confirm your delete. When you press OK, the entry will be deleted " +
                                "from the program. The program also allows multiple deletes.");
                        textArea.getChildren().add(text);

                    } else if (menuValue.equals("Graphs")) {
                        text = new Text("Graphs\n" + "The program has the ability to produce graphs according to the type of data.\n" +
                                "This is done by going to the raw data page for the data you wish to graph. Then press the analyse data button" +
                                " on the bottom of the screen. This will produce a graph specific for that type of data.");
                        textArea.getChildren().add(text);

                    } else if (menuValue.equals("Distance Calculator")) {
                        text = new Text("Distance Calculator\n" + "You can calculate the distance between two airports.\n" +
                                "First, go to the 'Analysis' tab on the top of the menu bar. You will be taken to a page that " +
                                "allows them to select two airports, one from each column and press 'Calculate' to get the distance between" +
                                " the two airports.");
                        textArea.getChildren().add(text);
                    } else {
                        text = new Text("Please select an option on the left side menu to display its contents.");
                        textArea.getChildren().add(text);
                    }
                }
            }
        });
    }

}