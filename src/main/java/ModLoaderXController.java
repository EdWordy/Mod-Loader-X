import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javafx.application.Application;
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
import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

public class ModLoaderXController extends Application {

    public ObservableList<String> rootVM;

    public ObservableList<String> branchesVM;

    int numOfModsLoaded;

    boolean cursorOnMods;

    public String selectedModVM;

    public String selectedModML;

    public static ArrayList<String> selectedMods;

    public ArrayList<String> unloadedMods;

    public static String infoToReadVM;

    public static String infoToReadML;

    public String selectedInfo;

    public static ArrayList<Path> loadedInfos;

    public File[] modFolder;

    public File[] modFiles;

    public File[] modFolderContents;

    public String glob = "glob:**/info.xml";

    public static String path = "F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/";

    public static Path path2;

    public static Path path3;

    public static File jar;

    public static File source;

    public static File sourceFile;

    public static File outFilename;

    public String glob2 = "glob:**/********************************/";  // mod name length max 32 characters

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

    public void addMod()
    {       
        // null check
        if (selectedModVM != null){
        // add the mod to the Mods Loaded list
        listViewML.getItems().add(selectedModVM);

        // removes the item from the View Mods list
        listViewVM.getItems().remove(selectedModVM);
        System.out.println(selectedModVM + " added");
        
        // add mods to the Selected Mods list
        selectedMods.add(selectedModVM.replaceAll("[\\p{Ps}\\p{Pe}]", ""));

        // remove mod from the Unloaded Mods list
        unloadedMods.remove(selectedModVM.replaceAll("[\\p{Ps}\\p{Pe}]", ""));

        // duplicate check  
        List<String> listWithoutDuplicates = selectedMods.stream().distinct().collect(Collectors.toList());
        selectedMods = (ArrayList<String>) listWithoutDuplicates;
        listViewML.getItems().setAll(selectedMods);
        selectedModVM = null;

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
        // null check
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
        unloadedMods = (ArrayList<String>) listWithoutDuplicates;
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

    public void cursorCheckVM() throws IOException, JAXBException, SAXException
    {
        // sets cursorOnMods to true and prints cursor detected
        cursorOnMods = true;
        System.out.println("Cursor Detected VM");

        // sets up the selection model to get the selected item
        selectedModVM = listViewVM.getSelectionModel().getSelectedItem();
        System.out.println("Current Selection (VM) : " + selectedModVM);
         
        // null check
        if (selectedModVM != null) {

        // sets up the selected mods and selected infos
        selectedInfo = listViewVM.getSelectionModel().getSelectedItem();
        System.out.println("Current Selection (VM INFO) : " + selectedInfo.replaceAll("[\\p{Ps}\\p{Pe}]", "").concat("\\info.xml"));
        
        // calls read info for the View Mods listview
        readInfoVM();
        }
    }

    public void cursorCheckML() throws IOException, JAXBException, SAXException
    {
        // sets cursorOnMods to true and prints cursor detected
        cursorOnMods = true;
        System.out.println("Cursor Detected ML");

        // sets up the selection model to get the selected item
        selectedModML = listViewML.getSelectionModel().getSelectedItem();
        System.out.println("Current Selection (ML) : " + selectedModML);

        // null check (usually only needed once for before any mods are selected)
        if (selectedModML != null) {

        // sets up the selected mods and selected infos
        selectedInfo = listViewML.getSelectionModel().getSelectedItem();
        System.out.println("Current Selection (ML INFO) : " + selectedInfo.replaceAll("[\\p{Ps}\\p{Pe}]", "").concat("\\info.xml"));
        
        // calls read info for the Mods Loaded listview
        readInfoML();
        }
    }

    private void findModsAndBuildMenu(String glob, String location) throws IOException {
     
        // creates a new File and FilenameFilter
        File f = new File(location);
        FilenameFilter filter = new FilenameFilter() {
  
        public boolean accept(File f, String name)
            {
                // checks if its a directory, if it ends with .txt or if its the spacehaven asset folder
                if (!name.endsWith(".txt") && f.isDirectory() && !name.endsWith("spacehaven_0.14.1") && !name.endsWith(".zip") && !name.endsWith("source")){
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

    public void readInfoVM() throws JAXBException, IOException, SAXException
    {
        // sets the string infoToReadVM equal to selected item in the listViewVM
        infoToReadVM = listViewVM.getSelectionModel().getSelectedItem();

        // checks if the mod selected in the listview menu View Mods is null,
        // if not it runs a loop.
        if (cursorOnMods && infoToReadVM != null)
        { 
        // sees if loadedInfos contains the selected item and finds the xml file
        boolean contains = loadedInfos.toString().contains(infoToReadVM.replaceAll("[\\p{Ps}\\p{Pe}]", "").concat("\\info.xml"));
        System.out.println("Info found: " + contains);

        // loads the infos in the View Mods menu
        Load.loadInfoVM();

        // set modDetails label equal to Load string writer
        modDetails.setText(Load.sw.toString());
        }
    }
    public void readInfoML() throws JAXBException, IOException, SAXException
    {
        // sets the string infoToReadML equal to selected item in the listViewML
        infoToReadML = listViewML.getSelectionModel().getSelectedItem();

        // checks if the mod selected in the listview menu View Mods is null,
        // if not it runs a loop.
        if (cursorOnMods && infoToReadML != null)
        {
        // sees if loadedInfos contains the selected item and finds the xml file
        boolean contains = loadedInfos.toString().contains(infoToReadML.replaceAll("[\\p{Ps}\\p{Pe}]", "").concat("\\info.xml"));
        System.out.println("Info found: " + contains);

        // loads the infos in the Mods Loaded menu
        Load.loadInfoML();

        // set modDetails label equal to Load string writer
        modDetails.setText(Load.sw.toString());
        }
    }

    public void loadAndLaunch() throws IOException {
       
        // messages
        System.out.println("----- loading -----");
        modCounterDialog.setText("...Loading...");

        // checks if selectedMods is empty
        if (!selectedMods.isEmpty()){
 
           // setups the paths to unpack
           path2 = FileSystems.getDefault().getPath("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/spacehaven.jar");
           path3 = FileSystems.getDefault().getPath("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/source/");

           // setups the paths to unpack
           jar = new File("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/spacehaven.jar");
           source = new File("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/source/");
            
            // unpack jar
           modCounterDialog.setText("... .jar unpacking...");
           unzip(jar, source);
           System.out.println(".Jar Unpacked!");
           modCounterDialog.setText("... .jar unpacked...");
             
            System.out.println("...parsing mods....");
            modCounterDialog.setText("...parsing mods....");
            // parse selected mods
            // for each string f in selectedMods do such
            for (String f : selectedMods){

                // creates a new file and sets it equal to f
                File folder = new File(f);

                // checks if its a directory
                if (folder.isDirectory())
                {
                // creates a new FilenameFilter named filterMain
                FilenameFilter filterMain = new FilenameFilter() {
  
                    public boolean accept(File f, String name)
                    {
                    // checks if it ends with .txt, .png, or .xml
                    if (!name.endsWith(".txt") && !name.endsWith(".png") && !name.endsWith(".xml")){
                    return true;
                }
                    return false;
                }
                };

                // gets the folders
                modFiles = folder.listFiles(filterMain);
                modFolder = modFiles;

                // prints everything to console
                System.out.println("folders:");
                System.out.println(Arrays.toString(modFolder));

                // for each folder in mod files
                for (File g : modFiles) {

                    // directory double check
                    boolean directory = g.isDirectory();

                    // if true run loop
                    if (directory == true) {

                    // creates new FilenameFilter name filterContents
                    FilenameFilter filterContents = new FilenameFilter() {
  
                    public boolean accept(File f, String name)
                    {
                    // checks if it ends with .png, or .xml
                    if (name.endsWith(".png") | name.endsWith(".xml")){
                    return true;
                    }
                    return false;
                    }
                    };

                    // list the files and assign it to a variable named folderContents
                    modFolderContents = g.listFiles(filterContents);
                    System.out.println("folder contents:");
                    System.out.println(Arrays.toString(modFolderContents)); 

                    // for each file in modFolderContents do x
                    for (File m : modFolderContents){
                        System.out.println("file:");
                        System.out.println(m);


                        // write to jar




                    }


                    }
                }
                } else {
                    // error messages
                    System.err.println("can't read");
                }
            }

            System.out.println("...mods parsed...");
            modCounterDialog.setText("...Mods parsed...");

            // do more stuff here after parsing, such as repack
            
            System.out.println("...packing .jar...");
            modCounterDialog.setText("...Packing .jar...");

            // set up the paths
            sourceFile = new File("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/source/");
            outFilename = new File("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/spacehaven.jar");

            // pack the jar
            org.zeroturnaround.zip.ZipUtil.pack(new File(String.valueOf(sourceFile)), new File(String.valueOf(outFilename)));
            // messages
            System.out.println("... .jar packed...");
            modCounterDialog.setText("... .jar packed...");

            } else {
                // error messages
                System.err.println("No mods selected");
                modCounterDialog.setText("No mods selected");
            }
    }

    public static void unzip(File archive, File destDir) throws IOException {
        //creates a new buffer
        byte[] buffer = new byte[256 * 1024];
        //creates directory
        destDir.mkdirs();

            try (JarFile jar = new JarFile(archive)) {
            Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                JarEntry ent = entries.nextElement();
                File f = new File(destDir, ent.getName());
                if (ent.isDirectory()) {
                    f.mkdir();
                    continue;
                }
            try (InputStream is = jar.getInputStream(ent);
                 FileOutputStream os = new FileOutputStream(f)) {
                for (int r; (r = is.read(buffer)) > 0; ) {
                    os.write(buffer, 0, r);
                }
                }
                }
            }   
    }

    public void zip(){
    
        String srcFilename = sourceFile.toString();
        File zipFile = outFilename;

        if (sourceFile.canWrite() == true) {

        try {
            byte[] buffer = new byte[1024];
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);         
            File srcFile = new File(srcFilename);
            FileInputStream fis = new FileInputStream(srcFile);
            zos.putNextEntry(new ZipEntry(srcFile.getName()));          
            int length;
            while ((length = fis.read(buffer)) > 0) {
            zos.write(buffer, 0, length);
            
            }
            
            zos.closeEntry();
            fis.close();
            zos.close();            
        }
        catch (IOException ioe) {
            System.err.println("Error creating zip file" + ioe);
            }       
        } else {
            System.err.println("can't read source!");   
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
        alert.setTitle("Mod Loader X v0.2.9 help");
        alert.setHeaderText(null);
        alert.setContentText("Mod Loader X v0.2.9 was written in java 8 using javafx8 and was intended for use with the game Space Haven alpha 14.1.");
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

        // setup the selected mods mod
        Load.modVM = new mod();
        Load.modML = new mod();
        System.out.println("mods.class modVM and modML initalized!");

        // setup the labels
        modDetails.setWrapText(true);
        System.out.println("labels setup!");

        // check for info.xml
        Find.findInfos(glob, path);

        // find mods
        findModsAndBuildMenu(glob2, path);    

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
        // creates the root, sets it equal to the .fxml file and then sets the stage
        Parent root = FXMLLoader.load(getClass().getResource("ModLoaderUI.fxml"));
        primaryStage.setTitle("Mod Loader X v0.2.9");
        primaryStage.setScene(new Scene(root, 1400, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
