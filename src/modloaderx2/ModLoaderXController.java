package modloaderx2;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.control.SelectionModel;


public class ModLoaderXController extends Application {

    public ObservableList<String> rootVM;

    public ObservableList<String> branchesVM;

    int numOfModsLoaded;

    boolean cursorOnMods;

    public String selectedModVM;

    public String selectedModML;

    public  ObservableList<String> selectedMods = null;

    private String glob = "glob:**/info.xml";

    private String path = "F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/";

    private String glob2 = "glob:**/************************/";

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
    public Label modCounterDialog;

    public void addMod()
    {

        // add the mod to the ML list
        if (selectedModVM != null){
        listViewML.getItems().add(selectedModVM);
        listViewVM.getItems().remove(selectedModVM);
        System.err.println(selectedModVM + " added");





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
        // remove the mod from the ML list        
        listViewML.getItems().remove(selectedModML);
        listViewVM.getItems().add(selectedModML);
        System.err.println(selectedModML + " removed");

        




           
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

    public void clearMods()
    {
        // counter
        numOfModsLoaded = 0;

        // messages
        System.out.println("...Mods cleared...");
        System.out.println("Mods: " + numOfModsLoaded);
        modCounterDialog.setText("...Mods cleared...");   
        modCounterDialog.autosize();  

        // clear mods from list
        listViewML.getItems().clear();
        //System.err.println(selectedMods.toString() + " cleared");
    }

    public void cursorCheckVM()
    {
        // sets cursorOnMods to true and prints cursor detected
        cursorOnMods = true;
        System.out.println("Cursor Detected VM");

        // sets up the selection model to get the selected item
        selectedModVM = listViewVM.getSelectionModel().getSelectedItem();
        System.out.println("Current Selection (VM) : " + selectedModVM);
    }

    public void cursorCheckML()
    {
        // sets cursorOnMods to true and prints cursor detected
        System.out.println("Cursor Detected ML");

        // sets up the selection model to get the selected item
        selectedModML = listViewML.getSelectionModel().getSelectedItem();
        System.out.println("Current Selection (ML) : " + selectedModML);
    }

    private void findMods(String glob, String location) throws IOException {
     
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
      listViewVM.getItems().add(f);
    }

    public void exitButtonClicked()
    {
        System.exit(0);
    }

    public void helpButtonClicked()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mod Loader X v0.2.1 help");
        alert.setHeaderText(null);
        alert.setContentText("Mod Loader X v0.2.1 was written in java 8 using javafx8 and was intended for use with the game Space Haven alpha 14.1.");
        alert.showAndWait();
    }

    @FXML
    public void initialize() throws IOException{

        // check for info.xml
        findInfos.find(glob, path);

        // find mods
        findMods(glob2, path);
    }      

    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        Parent root = FXMLLoader.load(getClass().getResource("ModLoaderUI.fxml"));
        primaryStage.setTitle("Mod Loader X v0.2.1");
        primaryStage.setScene(new Scene(root, 1400, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
