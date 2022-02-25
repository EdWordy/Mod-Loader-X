package modloaderx2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;



public class ModLoaderXController extends Application {

    int numOfModsLoaded;

    static boolean cursorOnMods;

    public static Path CURRENT_PATH;

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
    public TreeView<File> treeViewML;

    @FXML
    public TreeView<File> treeViewVM;

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

    public void exitButtonClicked()
    {
        System.exit(0);
    }

    public void helpButtonClicked()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mod Loader X v0.1.6 help");
        alert.setHeaderText(null);
        alert.setContentText("Mod Loader X v0.1.6 was written in java 8 using javafx8 and was intended for use with the game Space Haven alpha 14.1.");
        alert.showAndWait();
    }








    
    @FXML
    public void initialize() throws IOException{

        // create and populate treeViewVM with mods from path specified
        TreeItem<File> treeViewRootVM = new TreeItem(new File("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/"));
        treeViewVM.setRoot(treeViewRootVM);
        TreeItem<File> branch = new TreeItem<>();
        treeViewRootVM.getChildren().addAll(branch);

        System.out.println("Branches at: " + branch);



         // check for info.xml
        findInfos.find(glob, path);

        // find mods
        findMods.find(glob2, path);
 
        // load mods
        // loadMods.

    }      

    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        Parent root = FXMLLoader.load(getClass().getResource("ModLoaderUI.fxml"));
        primaryStage.setTitle("Mod Loader X v0.1.6");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
