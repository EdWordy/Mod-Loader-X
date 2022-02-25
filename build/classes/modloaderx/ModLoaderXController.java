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
import javafx.geometry.Insets;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import java.io.FilenameFilter;
import java.util.Arrays;





public class ModLoaderXController extends Application
{ 
    int numOfModsLoaded;

    boolean cursorOnMods;

    public String CURRENT_PATH;

    @FXML
    public Label modCounterDialog;

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
    public Label modDetails;

    public File root = null;

    @FXML
    public void initialize() throws IOException{
        // default state
        modCounterDialog.setText("No mods loaded");
        modCounterDialog.setPadding(new Insets(1, 1, 1, 1));

        modDetails.setText("No info.");
        modDetails.setAlignment(Pos.CENTER);


        // create and populate treeViewVM with mods from path specified
        TreeItem<File> treeViewRoot = new SimpleFileTreeItem(new File("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/"));
        treeViewVM.setRoot(treeViewRoot);

        // view info
        // currently doesn't work
        //ListView<String> list = new ListView<>();
        //ObservableList<String> items = FXCollections.observableArrayList();
        //String itemsString = items.toString();
         //modDetails.setText(itemsString);

         // check for info.xml
        String glob = "glob:**/info.xml";
        String path = "F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/";
        findInfos.match(glob, path);

        getInfo();
 
    }      


    public void getInfo()
    {
        cursorOnMods = true;
        System.out.println("Mouse Detected");

    }    

    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        Parent root = FXMLLoader.load(getClass().getResource("ModLoaderUI.fxml"));
        primaryStage.setTitle("Mod Loader X v0.1.50");
        primaryStage.setScene(new Scene(root, 1200, 600));
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
        alert.setTitle("Mod Loader X v0.1.50 help");
        alert.setHeaderText(null);
        alert.setContentText("Mod Loader X v0.1.50 was written in java 8 using javafx8 and was intended for use with the game Space Haven alpha 14.1.");
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
    }

    public void launcher()
       {
           modCounterDialog.setText(".........Launching!"); 
           Path coreJarPath = Paths.get("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/spacehaven.jar");


       }

       //needs some janking with to implement greater hierarchy in file presentation

    public class SimpleFileTreeItem extends TreeItem<File> {
 
	/**
	 * Calling the constructor of super class in order to create a new
	 * TreeItem<File>.
	 * 
	 * @param f
	 *            an object of type File from which a tree should be build or
	 *            which children should be gotten.
	 */
    public SimpleFileTreeItem(File f) {
	super(f);
    }
 
	/*
	 * 
	 * 
	 * @see javafx.scene.control.TreeItem#getChildren()
	 */
    @Override
    public ObservableList<TreeItem<File>> getChildren() {
	if (isFirstTimeChildren == true) {
            isRoot = true;
            isFirstTimeChildren = false;
 
			/*
			 * First getChildren() call, so we actually go off and determine the
			 * children of the File contained in this TreeItem.
			 */
            super.getChildren().setAll(buildBranches(this));
        }
            return super.getChildren();
    }
 
	/*
	 * (
	 * 
	 * @see javafx.scene.control.TreeItem#isLeaf()
	 */

    public boolean isBranch() {
	if (isLeaf == false && isRoot == false) {
                File f = (File) getValue();
		isBranch = f.isFile();
        }
            return isBranch;
    }

    @Override
    public boolean isLeaf() {
	if (isBranch == false && isRoot == false) {
            isRoot = false;
            isFirstTimeLeaf = true;
            File f = (File) getValue();
            isLeaf = f.isFile();
        }
	return isLeaf;
    }

    public boolean isLeafTip() {
	if (isBranch == false && isRoot == false) {
            isRoot = false;
            isFirstTimeLeaf = false;
            File f = (File) getValue();
            isLeafTip = f.isFile();
	}
            return isLeafTip;
    }
	/**
	 * Returning a collection of type ObservableList containing TreeItems, which
	 * represent all children available in handed TreeItem.
	 * 
	 * @param TreeItem
	 *            the root node from which children a collection of TreeItem
	 *            should be created.
	 * @return an ObservableList<TreeItem<File>> containing TreeItems, which
	 *         represent all children available in handed TreeItem. If the
	 *         handed TreeItem is a leaf, an empty list is returned.
	 */
    public ObservableList<TreeItem<File>> buildBranches(TreeItem<File> TreeItem) {
	File f = TreeItem.getValue();
	if (f != null && f.isDirectory()) {
            isRoot = true;
            FilenameFilter filter = (dir, name) -> name.endsWith("info.xml");
            File[] branches = f.listFiles();
            File[] leaves = f.listFiles(filter);
            String StringLeaves = Arrays.toString(leaves);
            System.out.println("Info.xml found at: " + StringLeaves);
            modDetails.setText(StringLeaves);

            boolean isRootFolder = f.isDirectory();
            System.out.println("IsFolder: " + isRootFolder);
            System.out.println("IsFile: " + isFile);
            if (branches != null && isLeaf == false && isRoot == true) {
		ObservableList<TreeItem<File>> branchFile = FXCollections.observableArrayList();

                isBranch = true;
                isLeaf = false;
                boolean isFolder = f.isDirectory();
 
                if(isBranch == true && isFirstTimeLeaf == false && isLeafTip == false) {   
                    File g = TreeItem.getValue();
                    boolean directoryCheck = g.isDirectory();
                    isRoot = false;
                    isBranch = false;
                    isLeafTip = false;
                    CURRENT_PATH = f.getAbsolutePath();
                    isLeaf = true;

                                        
                    System.out.println("Current Path: " + CURRENT_PATH);
                                
                    for (File branch : branches) {
			branchFile.add(new SimpleFileTreeItem(branch));
                    }
                       
                        isLeafTip = true;
                        return branchFile;
                }

                isBranch = false;  
                isRoot = false;  
                isFile = true;          

                if (isBranch == false && isRoot == false && isFile == true){

                    File h = TreeItem.getValue();
                    System.out.println("h: " + h);
                    
                    System.out.println("Current Path: " + CURRENT_PATH);
                    System.out.println("IsFile: " + isFile);



                }          
            }
        }    
            return FXCollections.emptyObservableList();
    }
    private boolean isFirstTimeChildren = true;
    private boolean isRoot = true;
    private boolean isFirstTimeLeaf = false;
    private boolean isLeaf = false;
    private boolean isBranch = false;
    private boolean isLeafTip = false;
    private boolean isFile = false;

    }


}