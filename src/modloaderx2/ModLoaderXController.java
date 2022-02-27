package modloaderx2;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;


public class ModLoaderXController extends Application {

    public ObservableList<String> rootVM;

    public ObservableList<String> branchesVM;

    int numOfModsLoaded;

    boolean cursorOnMods;

    public String selectedModVM;

    public String selectedModML;

    public ArrayList<String> selectedMods;

    public String selectedInfo;

    public static List<Path> selectedInfos;

    public String glob = "glob:**/info.xml";

    public static String path = "F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/";

    public String glob2 = "glob:**/************************/";

    public String glob3 = "glob:**/mods/**";

    @FXML
    public AnchorPane anchorPaneML;

    @FXML
    public AnchorPane anchorPaneVM;

    @FXML
    public AnchorPane anchorPaneD;

    @FXML
    public ListView<String> listViewML;

    @FXML
    public ListView<String> listViewVM;

    @FXML
    public ListView<String> listViewSM;

    @FXML
    public Label modCounterDialog;

    @FXML
    public Label modDetails;

    @FXML
    public Label selectedModDetails;


    public void addMod() throws InvocationTargetException
    {       
        // duplicate check


        // add the mod to the Mods Loaded list
        if (selectedModVM != null){
        listViewML.getItems().add(selectedModVM);

        // removes the item from the View Mods list
        listViewVM.getItems().remove(selectedModVM);
        System.err.println(selectedModVM + " added");
        
        // add mods to the Selected Modss list
        selectedMods.add(selectedModVM);
        System.out.println("Mods selected:" + selectedMods);

        // counter
        numOfModsLoaded += 1;

        // messages
        System.out.println("..Mod Added..");            
        System.out.println("Mods: " + numOfModsLoaded);
        modCounterDialog.setText("...Mod added in...");
        modCounterDialog.autosize();
        }

    }

    public void removeMod()
    {
        if (selectedModML != null){

        // duplicate check






        // remove the mod from the Mods Loaded list        
        listViewML.getItems().remove(selectedModML);

        // adds the mod to the View Mods list
        listViewVM.getItems().add(selectedModML);
        System.err.println(selectedModML + " removed");

        // removes from the Selected Mods list
        selectedMods.remove(selectedModML);
        System.out.println("Mods Selected:" + selectedMods);

        // counter + check if numOfModsLoaded is equal to zero, if it isn't, remove a mod
        if (numOfModsLoaded != 0)
        {
        numOfModsLoaded -= 1;

        // messages
        System.out.println("..Mod Removed..");
        System.out.println("Mods: " + numOfModsLoaded); 
        modCounterDialog.setText("...Mod removed...");
        modCounterDialog.autosize();
        }
        }
    }

    public void clearMods() throws IOException
    {
        // rebuilds View Mods menu
        listViewVM.getItems().addAll(selectedMods);

        // clear mods from Mods Loaded list
        listViewML.getItems().clear();

        // counter
        numOfModsLoaded = 0;

        // messages
        System.out.println("...Mods cleared...");
        System.out.println("Mods: " + numOfModsLoaded);
        modCounterDialog.setText("...Mods cleared...");   
        modCounterDialog.autosize();  
    }

    public void cursorCheckVM()
    {
        // sets cursorOnMods to true and prints cursor detected
        cursorOnMods = true;
        System.out.println("Cursor Detected VM");

        // sets up the selection model to get the selected item
        selectedModVM = listViewVM.getSelectionModel().getSelectedItem();
        System.out.println("Current Selection (VM) : " + selectedModVM);

        //Setsup the selected mods and selected infos
        selectedInfo = listViewVM.getSelectionModel().getSelectedItem();
        System.out.println("Current Selection (VM INFO) : " + selectedInfo);
        readInfos();
    }

    public void cursorCheckML()
    {
        // sets cursorOnMods to true and prints cursor detected
        System.out.println("Cursor Detected ML");

        // sets up the selection model to get the selected item
        selectedModML = listViewML.getSelectionModel().getSelectedItem();
        System.out.println("Current Selection (ML) : " + selectedModML);

        //Setsup the selected mods and selected infos
        selectedInfo = listViewML.getSelectionModel().getSelectedItem();
        System.out.println("Current Selection (ML INFO) : " + selectedInfo);
        readInfos();
    }

    private void findModsAndBuildMenu(String glob, String location) throws IOException {
     
        // creates a new File and FilenameFilter
        File f = new File(location);
        FilenameFilter filter = new FilenameFilter() {
  
        public boolean accept(File f, String name)
            {
                // checks if its a directory, if it ends with .txt or if its the spacehaven asset folder
                if (!name.toString().endsWith(".txt") && f.isDirectory() && !name.toString().endsWith("spacehaven_0.14.1") ){
                    return f.isDirectory();
                }
                    return false;
            }
        };
  
        // Get all the names of the mod folders
        // present in the given directory
        File[] files = f.listFiles(filter);

        // create a root observablelist<String> object
        rootVM = FXCollections.observableArrayList(f.toString());
        System.out.println("Root mod folder found at:" + rootVM);
       
        // parses all the files in files, add them to branches, 
        // prints them and then adds them to the mod viewer
        for (File g : files) {
        branchesVM = FXCollections.observableArrayList(g.toString());
        System.out.println("Mod found at: " + branchesVM);
        getMods(branchesVM.toString());
        } 
    }

    public void getMods(String f)
    {
      // gets the mods and adds them to the listview named View Mods
      listViewVM.getItems().add(f);
    }

    public void readInfos()
    {
        // checks if the mod selected in the listview menu View Mods is null,
        // if not it runs a loop.
            if (selectedModVM != null)
        {
            System.err.println("read infos not null");

           // TO DO



        }
    }

    public void exitButtonClicked()
    {
        // exits the program
        System.exit(0);
    }

    public void helpButtonClicked()
    {
        // creates a new alert popup box when the help button is clicked
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mod Loader X v0.2.2 help");
        alert.setHeaderText(null);
        alert.setContentText("Mod Loader X v0.2.2 was written in java 8 using javafx8 and was intended for use with the game Space Haven alpha 14.1.");
        alert.showAndWait();
    }

    @FXML
    public void initialize() throws IOException{

        // header message
        System.out.println("----- BEGINNING TO INITALIZE -----");

        // check for info.xml
        Mods.findInfos(glob, path);

        // find mods
        findModsAndBuildMenu(glob2, path);    

        // find files
        Mods.findFiles(glob3, path);

        // setup the selected mods list
        selectedMods = new ArrayList<>();

        // footer message
        System.out.println("----- END INITALIZE -----");
    }      

    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        Parent root = FXMLLoader.load(getClass().getResource("ModLoaderUI.fxml"));
        primaryStage.setTitle("Mod Loader X v0.2.2");
        primaryStage.setScene(new Scene(root, 1400, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
