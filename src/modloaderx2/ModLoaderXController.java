package modloaderx2;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
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





    public ObservableList<String> root;

    public ObservableList<String> branches;

    int numOfModsLoaded;

    static boolean cursorOnMods;

    public Path CURRENT_PATH;

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
    public ListView<String> listViewRootVM;

    @FXML
    public Label modCounterDialog;

    public void addMod()
    {
        // counter
        numOfModsLoaded += 1;

        // messages
        System.out.println("..Mod Added..");            
        System.out.println("Mods: " + numOfModsLoaded);
        modCounterDialog.setText("...Mod added in...");
        modCounterDialog.autosize();

        // add the mod to the list



    }

    public void removeMod()
    {
        // counter + check if numOfModsLoaded is equal to zero, if it isn't, remove a mod
        if (numOfModsLoaded != 0)
        {
        numOfModsLoaded -= 1;

        // messages
        System.out.println("..Mod Removed..");
        System.out.println("Mods: " + numOfModsLoaded); 
        modCounterDialog.setText("...Mod removed...");
        modCounterDialog.autosize();

        // remove the mod from the list        


           

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

      
    }

    public void cursorCheck()
    {
        cursorOnMods = true;
        System.out.println("Cursor Detected");
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
        root = FXCollections.observableArrayList(f.toString());
        System.out.println("Root found at:" + root);
       
        // parses all the files in files, add them to branches, 
        // prints them and then adds them to the mod viewer
        for (File g : files) {
        branches = FXCollections.observableArrayList(g.toString());
        System.out.println("Mod found at: " + branches);
        getMods(branches.toString());

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
        alert.setTitle("Mod Loader X v0.1.8 help");
        alert.setHeaderText(null);
        alert.setContentText("Mod Loader X v0.1.8 was written in java 8 using javafx8 and was intended for use with the game Space Haven alpha 14.1.");
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
        primaryStage.setTitle("Mod Loader X v0.1.8");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
