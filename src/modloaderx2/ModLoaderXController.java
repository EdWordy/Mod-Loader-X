package modloaderx2;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;

public class ModLoaderXController extends Application {

    public ObservableList<String> rootVM;

    public ObservableList<String> branchesVM;

    int numOfModsLoaded;

    boolean cursorOnMods;

    public String selectedModVM;

    public String selectedModML;

    public ArrayList<String> selectedMods;

    public ArrayList<String> unloadedMods;

    public String selectedInfo;

    public static ArrayList<Path> loadedInfos;

    public String glob = "glob:**/info.xml";

    public static String path = "F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/";

    public String glob2 = "glob:**/************************/";  // mod name length max 24 characters

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
    public Label modCounterDialog;

    @FXML
    public Label modDetails;

    @FXML
    public Label selectedModDetails;

    public void addMod()
    {       
        // add the mod to the Mods Loaded list
        if (selectedModVM != null){
        listViewML.getItems().add(selectedModVM);

        // removes the item from the View Mods list
        listViewVM.getItems().remove(selectedModVM);
        System.out.println(selectedModVM + " added");
        
        // add mods to the Selected Mods list
        selectedMods.add(selectedModVM);

        System.out.println(selectedModVM);

        // remove mod from the Unloaded Mods list
        boolean remove = unloadedMods.remove(selectedModVM.replaceAll("[\\p{Ps}\\p{Pe}]", ""));

        // duplicate check  
        List<String> listWithoutDuplicates = selectedMods.stream().distinct().collect(Collectors.toList());
        System.out.println("List without duplicates (SELECTED): " + listWithoutDuplicates);
        selectedMods = (ArrayList<String>) listWithoutDuplicates;
        System.out.println("Mods selected (SELECTED): " + selectedMods);
        System.out.println("Mods selected (UNLOADED): " + unloadedMods.toString());
        listViewML.getItems().setAll(selectedMods);
        selectedModVM = null;

        // counter
        numOfModsLoaded += 1;

        // messages
        System.out.println("..Mod Added..");            
        System.out.println("Mods: " + numOfModsLoaded);
        modCounterDialog.setText("...Mod added in...");
        modCounterDialog.autosize();
        selectedModDetails.setText(selectedMods.toString());
        }
    }

    public void removeMod()
    {
        if (selectedModML != null){

        // remove the mod from the Mods Loaded list        
        listViewML.getItems().remove(selectedModML);
        System.out.println(selectedModML + " removed");

        // adds the mod to the View Mods list
        listViewVM.getItems().add(selectedModML);

        // removes the mod from the Selected Mods list
        selectedMods.remove(selectedModML.replaceAll("[\\p{Ps}\\p{Pe}]", ""));
        System.out.println("Mods Selected: " + selectedMods);

        // add the mod to the Unloaded Mods list
        unloadedMods.add(selectedModML);
        System.out.println("Mods Deselected: " + unloadedMods);

        // duplicate check  
        List<String> listWithoutDuplicates = unloadedMods.stream().distinct().collect(Collectors.toList());
        System.out.println("List without duplicates (UNLOADED): " + listWithoutDuplicates);
        unloadedMods = (ArrayList<String>) listWithoutDuplicates;
        System.out.println("Mods selected (UNLOADED): " + unloadedMods);
        System.out.println("Mods selected (SELECTED)" + selectedMods);
        listViewVM.getItems().clear();
        listViewVM.getItems().setAll(unloadedMods);
        selectedModML = null;

            // counter + check if numOfModsLoaded is equal to zero,
            // if it isn't, remove a mod from the counter
            if (numOfModsLoaded != 0)
            {
            numOfModsLoaded -= 1;

            // messages
            System.out.println("..Mod Removed..");
            System.out.println("Mods: " + numOfModsLoaded); 
            modCounterDialog.setText("...Mod removed...");
            modCounterDialog.autosize();
            selectedModDetails.setText(selectedMods.toString());
            }
        }
    }

    public void clearMods() throws IOException
    {
        // rebuilds View Mods menu from mods selected
        listViewVM.getItems().addAll(selectedMods);
        selectedMods.clear();

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

    public void cursorCheckVM() throws IOException
    {
        // sets cursorOnMods to true and prints cursor detected
        cursorOnMods = true;
        System.out.println("Cursor Detected VM");

        // sets up the selection model to get the selected item
        selectedModVM = listViewVM.getSelectionModel().getSelectedItem();
        System.out.println("Current Selection (VM) : " + selectedModVM);

        // sets up the selected mods and selected infos
        selectedInfo = listViewVM.getSelectionModel().getSelectedItem();
        System.out.println("Current Selection (VM INFO) : " + selectedInfo);
        readInfoVM();

        // TO DO






    }

    public void cursorCheckML() throws IOException
    {
        // sets cursorOnMods to true and prints cursor detected
        cursorOnMods = true;
        System.out.println("Cursor Detected ML");

        // sets up the selection model to get the selected item
        selectedModML = listViewML.getSelectionModel().getSelectedItem();
        System.out.println("Current Selection (ML) : " + selectedModML);

        // sets up the selected mods and selected infos
        selectedInfo = listViewML.getSelectionModel().getSelectedItem();
        System.out.println("Current Selection (ML INFO) : " + selectedInfo);
        readInfoML();

        // TO DO






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
        System.out.println("Root mod folder found at: " + rootVM);
       
        // parses all the files in files, add them to branches, 
        // prints them and then adds them to the mod viewer
        for (File g : files) {
        branchesVM = FXCollections.observableArrayList(g.toString());
        System.out.println("Mod found at: " + branchesVM);
        getMods(branchesVM.toString());
        unloadedMods.addAll(branchesVM);
        } 
        System.out.println("Mods added to unloaded list: " + unloadedMods);
    }

    public void getMods(String f)
    {
        // gets the mods and adds them to the listview named View Mods
        listViewVM.getItems().add(f);
    }

    public void readInfoVM()
    {
        // sets the string infoToReadVM equal to selected item in the listViewVM
        String infoToReadVM = listViewVM.getSelectionModel().getSelectedItem();

        // checks if the mod selected in the listview menu View Mods is null,
        // if not it runs a loop.
        if (cursorOnMods && infoToReadVM != null)
        {
        System.out.println("read infos not null");
   
        // sees if loadedInfos contains the selected item and finds the xml file
        boolean contains = loadedInfos.toString().contains(infoToReadVM.replaceAll("[\\p{Ps}\\p{Pe}]", "").concat("\\info.xml"));
        System.err.println("Info found: " + contains);
        



        }
    }
    public void readInfoML()
    {
        // sets the string infoToReadML equal to selected item in the listViewML
        String infoToReadML = listViewML.getSelectionModel().getSelectedItem();

        // checks if the mod selected in the listview menu View Mods is null,
        // if not it runs a loop.
        if (cursorOnMods && infoToReadML != null)
        {
        System.out.println("read infos not null");

        // sees if loadedInfos contains the selected item and finds the xml file
        boolean contains = loadedInfos.toString().contains(infoToReadML.replaceAll("[\\p{Ps}\\p{Pe}]", "").concat("\\info.xml"));
        System.err.println("Info found: " + contains);





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
        alert.setTitle("Mod Loader X v0.2.5 help");
        alert.setHeaderText(null);
        alert.setContentText("Mod Loader X v0.2.5 was written in java 8 using javafx8 and was intended for use with the game Space Haven alpha 14.1.");
        alert.showAndWait();
    }

    @FXML
    public void initialize() throws IOException{

        // header message
        System.out.println("----- BEGINNING TO INITALIZE -----");

        // setup the selected mods lists
        selectedMods = new ArrayList<>();
        unloadedMods = new ArrayList<>();
        loadedInfos = new ArrayList<>();
        System.out.println("ArrayLists for mods initalized!");

        // setup the labels
        modDetails.setWrapText(true);
        selectedModDetails.setWrapText(true);

        // check for info.xml
        Find.findInfos(glob, path);

        // find mods
        findModsAndBuildMenu(glob2, path);    

        // find files
        Find.findFiles(glob3, path);

        // load info files



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
        // creates the root, sets it equal to my .fxml file and then sets the stage
        Parent root = FXMLLoader.load(getClass().getResource("ModLoaderUI.fxml"));
        primaryStage.setTitle("Mod Loader X v0.2.5");
        primaryStage.setScene(new Scene(root, 1400, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
