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
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.io.File;
import java.util.List;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.Node;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;

public class ModLoaderXController extends Application 
{ 
    int numOfModsLoaded;

    boolean cursorOnMod;

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

    @FXML
    public void initialize(){
       //default state
       modCounterDialog.setText("No mods loaded");
       modCounterDialog.setPadding(new Insets(1, 1, 1, 1));

       modDetails.setText("No info.");
       modDetails.setAlignment(Pos.CENTER);


       // create and populate treeViewVM with mods
       TreeItem<File> treeViewRoot = new SimpleFileTreeItem(new File("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/"));
       treeViewVM.setRoot(treeViewRoot);

       //currently doesn't work
       ListView<String> list = new ListView<>();
       ObservableList<String> items = FXCollections.observableArrayList();
       String itemsString = items.toString();
       modDetails.setText(itemsString);
            
    }

    @FXML
    public void getInfo()
    {
         
     

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
		if (isFirstTimeChildren) {
                        isRoot = true;
			isFirstTimeChildren = false;
 
			/*
			 * First getChildren() call, so we actually go off and determine the
			 * children of the File contained in this TreeItem.
			 */
			super.getChildren().setAll(buildChildren(this));
		}
		return super.getChildren();
	}
 
	/*
	 * (
	 * 
	 * @see javafx.scene.control.TreeItem#isLeaf()
	 */
	@Override
	public boolean isLeaf() {
		if (isFirstTimeLeaf == true) {
                        isRoot = false;
			isFirstTimeLeaf = false;
			File f = (File) getValue();
			isLeaf = f.isFile();
		}
 
		return isLeaf;
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
	private ObservableList<TreeItem<File>> buildChildren(TreeItem<File> TreeItem) {
		File f = TreeItem.getValue();
		if (f != null && f.isDirectory()) {
			File[] files = f.listFiles();
			if (files != null && isLeaf == false && isFirstTimeChildren == false) {
				ObservableList<TreeItem<File>> children = FXCollections.observableArrayList();
 
				for (File childFile : files) {
					children.add(new SimpleFileTreeItem(childFile));
				}
 
				return children;
			}
		}
 
		return FXCollections.emptyObservableList();
	}
 
	private boolean isFirstTimeChildren = true;
	private boolean isRoot = true;
	private boolean isFirstTimeLeaf = true;
	private boolean isLeaf;
}

}