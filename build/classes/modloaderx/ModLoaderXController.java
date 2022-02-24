package modloaderx;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URL;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.Node;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModLoaderXController extends Application 
{ 
    int numOfModsLoaded;

    @FXML
    public Label modCounterDialog;

    @FXML
    public TreeView<String> treeViewML;

    @FXML
    public TreeView<String> treeViewVM;

    @FXML
    public TreeView<String> treeViewD;

    @FXML
    Path rootPath = Paths.get("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods");

    List<String> rootPaths = Arrays.asList("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods");

    @FXML
    public void initialize(){
        modCounterDialog.setText("No mods loaded");
        modCounterDialog.setPadding(new Insets(1, 1, 1, 1));
        
        TreeItem<String> root2 = new TreeItem<>("Mods");

 

    }

    public static void main(String[] args) 
        {
             launch(args);
        }

    @Override
    public void start(Stage primaryStage) throws Exception 
        {
            Parent root = FXMLLoader.load(getClass().getResource("ModLoaderUI.fxml"));
            primaryStage.setTitle("Mod Loader X v0.1.4");
            primaryStage.setScene(new Scene(root, 900, 600));
            primaryStage.setResizable(false);
            primaryStage.show();





        }

    public void exitButtonClicked()
        {
            System.exit(0);
        }

    public void helpButtonClicked()
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Mod Loader X v0.1 help");
            alert.setHeaderText(null);
            alert.setContentText("Mod Loader X v0.1 was written in java 8 using javafx8 and was intended for use with the game Space Haven alpha 14.1.");
            alert.showAndWait();
        }

    public void addMod()
        {
            // counter
            numOfModsLoaded += 1;

            // messages
            System.out.println("..Mod Added..");            
            System.out.println("Mods: " + numOfModsLoaded);
            modCounterDialog.setText("...Mod added in...");
            modCounterDialog.autosize();

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



       }

      public void launcher()
       {
           modCounterDialog.setText(".........Launching!"); 
           Path coreJarPath = Paths.get("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/spacehaven.jar");


       }

}