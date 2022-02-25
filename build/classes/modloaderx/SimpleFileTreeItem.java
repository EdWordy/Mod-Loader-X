package modloaderx;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;

    //needs some janking with to implement greater hierarchy in file presentation

    public class SimpleFileTreeItem extends TreeItem<File> {
 
    @FXML
    public Label modDetails;

    public String CURRENT_PATH;






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

        modDetails.setText("No info.");
        modDetails.setAlignment(Pos.CENTER);

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


