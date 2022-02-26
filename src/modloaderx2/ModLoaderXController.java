package modloaderx2;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.MapValueFactory;


public class ModLoaderXController extends Application {



    public TreeItem<File> firstBranch;

    public TreeItem<File> branches;

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
    public TreeView<File> treeViewML;

    @FXML
    public TreeView<File> treeViewVM;
   
    @FXML
    public TreeItem<File> treeViewRootVM;

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

    private void findMods(String glob, String location) throws IOException {
     
        File f = new File(location);
        FilenameFilter filter = new FilenameFilter() {
  
        public boolean accept(File f, String name)
            {
                if (!name.toString().endsWith(".txt") && f.isDirectory() && !name.toString().endsWith("spacehaven_0.14.1") ){
                    return f.isDirectory();
                }
                    return false;
            }
        };
  
        // Get all the names of the mod folders
        // present in the given directory
        File[] files = f.listFiles(filter);

        // prints them
        for (File g : files)
        {
            firstBranch = new TreeItem<>(g);
            System.err.println("FirstBranch at : " + firstBranch);   
            try {
                branches = firstBranch;
            } catch (Exception e) {
            }
            System.err.println("Branches at: " + branches);
 
        }



      } 




    public void buildModTree(TreeItem<File> file)
    {
        // create new treeview for the View Mod panel
        TreeItem<File> treeViewRootVM = new TreeItem(new File("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/"));
        treeViewVM.setRoot(treeViewRootVM);


        // add children
        treeViewRootVM.getChildren().add(file);

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

    public void cursorCheck()
    {
        cursorOnMods = true;
        System.out.println("Cursor Detected");
    }

    @FXML
    public void initialize() throws IOException{

        // check for info.xml
        findInfos.find(glob, path);

        // find mods
        findMods(glob2, path);

        buildModTree(firstBranch);

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
