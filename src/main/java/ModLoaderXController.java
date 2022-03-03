import com.ximpleware.ModifyException;
import com.ximpleware.NavException;
import com.ximpleware.TranscodeException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ModLoaderXController extends Application {

    public ObservableList<String> rootVM;

    public ObservableList<String> branchesVM;

    int numOfModsLoaded;

    boolean cursorOnMods;

    public String selectedModVM;

    public String selectedModML;

    public static ArrayList<String> selectedModsUI;

    public ArrayList<String> unloadedMods;

    public static String infoToReadVM;

    public static String infoToReadML;

    public String selectedInfo;

    public static ArrayList<Path> loadedInfos;

    public File[] modFolder;

    public File[] modFiles;

    public File[] modFolderContents;

    private Object currentModID;

    public String glob = "glob:**/info.xml";

    public String glob2 = "glob:**/********************************/";  // mod name length max 32 characters

    // (TODO: make default directory selectable)

    public String glob3 = "glob:F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/source/library/animations_****************";  // mod asset name length max 5 + 16 characters

    public String glob4 = "glob:F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/source/library/haven_****************";  // mod asset name length max 5 + 16 characters

    public String glob5 = "glob:F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/source/library/texts_****************";  // mod asset name length max 5 + 16 characters

    public String glob6 = "glob:F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/source/library/animations";

    public String glob7 = "glob:F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/source/library/haven";

    public String glob8 = "glob:F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/source/library/texts";

    public static String path = "F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/";

    public static Path path2;

    public static Path path3;

    public static File jar;

    public static File source;

    public File outFileName;

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

    public void addMod() {
        // null check
        if (selectedModVM != null) {
            // add the mod to the Mods Loaded listview
            listViewML.getItems().add(selectedModVM);

            // removes the item from the View Mods listview
            listViewVM.getItems().remove(selectedModVM);
            System.out.println(selectedModVM + " added");

            // add mods to the Selected Mods list
            selectedModsUI.add(selectedModVM.replaceAll("[\\p{Ps}\\p{Pe}]", ""));

            // remove mod from the Unloaded Mods list
            unloadedMods.remove(selectedModVM.replaceAll("[\\p{Ps}\\p{Pe}]", ""));

            // duplicate check
            List<String> listWithoutDuplicates = selectedModsUI.stream().distinct().collect(Collectors.toList());
            selectedModsUI = (ArrayList<String>) listWithoutDuplicates;
            listViewML.getItems().setAll(selectedModsUI);
            selectedModVM = null;

            // counter
            numOfModsLoaded += 1;

            // messages
            System.out.println("..Mod Added..");
            System.out.println("# of Mods loaded: " + numOfModsLoaded);
            modCounterDialog.setText("...Mod added in...");
            modCounterDialog.autosize();
        }
    }

    public void removeMod() {
        // null check
        if (selectedModML != null) {

            // remove the mod from the Mods Loaded listview
            listViewML.getItems().remove(selectedModML);
            System.out.println(selectedModML + " removed");

            // adds the mod to the View Mods listview
            listViewVM.getItems().add(selectedModML);

            // removes the mod from the Selected Mods list
            selectedModsUI.remove(selectedModML.replaceAll("[\\p{Ps}\\p{Pe}]", ""));
            System.out.println("Mods Selected: " + selectedModsUI);

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
            if (numOfModsLoaded != 0) {
                numOfModsLoaded -= 1;

                // messages
                System.out.println("..Mod Removed..");
                System.out.println("# of Mods loaded: " + numOfModsLoaded);
                modCounterDialog.setText("...Mod removed...");
                modCounterDialog.autosize();
            }
        }
    }

    public void clearMods() throws IOException {
        // rebuilds View Mods menu from mods selected UI
        listViewVM.getItems().addAll(selectedModsUI);

        // clear mod from selects mods
        selectedModsUI.clear();

        // clear mods from Mods Loaded listview
        listViewML.getItems().clear();

        // counter
        numOfModsLoaded = 0;

        // messages
        System.out.println("...Mods cleared...");
        System.out.println("# of Mods loaded: " + numOfModsLoaded);
        modCounterDialog.setText("...Mods cleared...");
        modCounterDialog.autosize();
    }

    public void cursorCheckVM() throws IOException, JAXBException, SAXException {
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

    public void cursorCheckML() throws IOException, JAXBException, SAXException {
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

            public boolean accept(File f, String name) {
                // checks if its a directory, if it ends with .txt or if its the spacehaven asset folder
                if (!name.endsWith(".txt") && f.isDirectory() && !name.endsWith("spacehaven_0.14.1") && !name.endsWith(".zip") && !name.endsWith("source")) {
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

    public void getMods(String f) {
        // gets the mods and adds them to the listview named View Mods
        listViewVM.getItems().add(f);
    }

    public void readInfoVM() throws JAXBException, IOException, SAXException {
        // sets the string infoToReadVM equal to selected item in the listViewVM
        infoToReadVM = listViewVM.getSelectionModel().getSelectedItem();

        // checks if the mod selected in the listview menu View Mods is null,
        // if not it runs a loop.
        if (cursorOnMods && infoToReadVM != null) {
            // sees if loadedInfos contains the selected item and finds the xml file
            boolean contains = loadedInfos.toString().contains(infoToReadVM.replaceAll("[\\p{Ps}\\p{Pe}]", "").concat("\\info.xml"));
            System.out.println("Info found: " + contains);

            // loads the infos in the View Mods menu
            Load.loadInfoVM();

            // set modDetails label equal to Load string writer
            modDetails.setText(Load.sw.toString());
        }
    }

    public void readInfoML() throws JAXBException, IOException, SAXException {
        // sets the string infoToReadML equal to selected item in the listViewML
        infoToReadML = listViewML.getSelectionModel().getSelectedItem();

        // checks if the mod selected in the listview menu View Mods is null,
        // if not it runs a loop.
        if (cursorOnMods && infoToReadML != null) {
            // sees if loadedInfos contains the selected item and finds the xml file
            boolean contains = loadedInfos.toString().contains(infoToReadML.replaceAll("[\\p{Ps}\\p{Pe}]", "").concat("\\info.xml"));
            System.out.println("Info found: " + contains);

            // loads the infos in the Mods Loaded menu
            Load.loadInfoML();

            // set modDetails label equal to Load string writer
            modDetails.setText(Load.sw.toString());
        }
    }

    public void loadAndLaunch() throws IOException, NavException, ModifyException, TranscodeException, ParserConfigurationException, TransformerException, SAXException, XMLStreamException {

        // messages
        System.out.println("----- loading -----");
        modCounterDialog.setText("...Loading...");

        // checks if selectedModsUI is not empty
        if (!selectedModsUI.isEmpty()) {

            // (TODO: make default paths selectable (2))
            // setups the paths to unpack
            path2 = FileSystems.getDefault().getPath("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/spacehaven.jar");
            path3 = FileSystems.getDefault().getPath("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/source/");

            // (TODO: make default paths selectable (3))
            // setups the paths to unpack
            jar = new File("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/spacehaven.jar");
            source = new File("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/source/");

            // unpack jar
            //modCounterDialog.setText("... .jar unpacking...");
            //unzip(jar, source);
            org.zeroturnaround.zip.ZipUtil.unpack(jar, source);

            // messages
            //System.out.println(".Jar Unpacked!");
            //modCounterDialog.setText("... .jar unpacked...");

            // messages
            System.out.println("...parsing mods....");
            modCounterDialog.setText("...parsing mods....");

            // parse selectedModsUI
            // for each string f in selectedModsUI do such
            for (String f : selectedModsUI) {

                // creates a new file named folder and sets it equal to f
                File folder = new File(f);


                // checks if it's a directory
                if (folder.isDirectory()) {
                    // creates a new FilenameFilter named filterMain
                    FilenameFilter filterMain = new FilenameFilter() {

                        public boolean accept(File f, String name) {
                            // checks if it ends with .txt, .png, or .xml
                            if (!name.endsWith(".txt") && !name.endsWith(".png") && !name.endsWith(".xml")) {
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

                    // for each folder in mod files do x
                    for (File g : modFiles) {

                        // directory double check
                        boolean directory = g.isDirectory();

                        // if true run loop
                        if (directory == true) {

                            // writes the library folder for each mod
                            if (g.toString().endsWith("library")) {
                                FileUtils.copyDirectory(g, new File(source + "/library"));
                                System.out.println("library directory copied: " + g);
                            }

                            // writes the textures folder for each mod
                            if (g.toString().endsWith("textures")) {
                                FileUtils.copyDirectory(g, new File(source + "/textures"));
                                System.out.println("textures directory copied: " + g);
                            }

                            // creates new FilenameFilter named filterContents
                            FilenameFilter filterContents = new FilenameFilter() {

                                public boolean accept(File f, String name) {
                                    // checks if it ends with .png, or .xml
                                    if (name.endsWith(".png") | name.endsWith(".xml")) {
                                        return true;
                                    }
                                    return false;
                                }
                            };
                            // list the files and assign it to a variable named folderContents
                            modFolderContents = g.listFiles(filterContents);
                        }
                    }
                } else {
                    // error messages
                    System.err.println("can't read");
                }
            }
            // messages
            System.out.println("...mods parsed...");
            modCounterDialog.setText("...Mods parsed...");

            // messages
            System.out.println("...merging data...");
            modCounterDialog.setText("...merging data...");

            // creates a list of files equal to source's current directory listing
            File[] sourceFolders = source.listFiles();


            // for each File f in source folders do such
            for (File f : sourceFolders) {

                // checks if it's the library folder
                if (f.toString().endsWith("library") == true) {

                    // creates a new file list for the library files
                    File[] libraryFiles = f.listFiles();

                    // checks if the animations.xml file exists, if not makes one
                    if (!f.toString().startsWith(source + "\\library\\animations")) {

                        // creates a new file named animations-temp.xml in the library and a new java object for that file
                        PrintWriter pw = new PrintWriter(source + "\\library\\animations.xml");
                        File h = new File(source + "\\library\\animations.xml");

                        System.out.println("writing data to new animations file");
                        // if it is, write to it.
                        FileUtils.write(h, "<AllAnimations> </AllAnimations>");

                    }

                    // checks if the haven.xml file exists, if not makes one
                    if (!f.toString().startsWith(source + "\\library\\haven")) {

                        // creates a new file named haven-temp.xml in the library and a new java object for that file
                        PrintWriter pw2 = new PrintWriter(source + "\\library\\haven.xml");
                        File i = new File(source + "\\library\\haven.xml");

                        System.out.println("writing data to new haven file");
                        // if it is, write to it.
                        FileUtils.write(i, "<data> </data>");

                    }

                    // checks if the texts.xml file exists, if not makes one
                    if (!f.toString().startsWith(source + "\\library\\texts")) {

                        //creates a new file named texts-temp.xml in the library and a new java object for that file
                        PrintWriter pw3 = new PrintWriter(source + "\\library\\texts.xml");
                        File j = new File(source + "\\library\\texts.xml");

                        System.out.println("writing data to new texts file");
                        // if it is, write to it.
                        FileUtils.write(j, "<t> </t>");

                    }

                    // for each file g in library files do such
                    for (File g : libraryFiles) {

                        // checks for the animations files and merges
                        if (g.toString().startsWith(source + "\\library\\animations")) {
                            System.out.println("animations found at: " + g);

                            // merge
                            System.out.print("Merging " + g + " ");
                            Merge.mergeAnimationsTemp(g.toString(), source + "/library/animations.xml");

                            // messages
                            System.out.print("Merging complete ");

                            // creates a path matcher
                            final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(glob3);

                            // if g matches the glob3
                            if (pathMatcher.matches(g.toPath())) {
                                // messages
                                System.out.println("...deleting animations file...");

                                // delete
                                Files.delete(g.toPath());

                                // messages
                                System.out.println("deleting animations file complete");
                            }

                            // creates a path matcher
                            final PathMatcher pathMatcher2 = FileSystems.getDefault().getPathMatcher(glob6);

                            // if g matches the og animations.file
                            if (pathMatcher2.matches(g.toPath())) {
                                // messages
                                System.out.println("...deleting og animations file...");

                                // delete
                                Files.delete(g.toPath());

                                // messages
                                System.out.println("deleting og animations file complete");
                            }

                        }

                        // checks for the haven files and merges
                        if (g.toString().startsWith(source + "\\library\\haven")) {

                            // merge
                            System.out.print("Merging " + g + " ");
                            Merge.mergeHavenTemp(g.toString(), source + "/library/haven.xml");

                            // messages
                            System.out.print("Merging complete ");

                            // creates a path matcher
                            final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(glob4);

                            // if g matches the glob4
                            if (pathMatcher.matches(g.toPath())) {

                                // messages
                                System.out.println("...deleting haven file...");

                                // delete
                                Files.delete(g.toPath());

                                // messages
                                System.out.println("deleting haven file complete");
                            }

                            // creates a path matcher
                            final PathMatcher pathMatcher2 = FileSystems.getDefault().getPathMatcher(glob7);

                            // if g matches the og haven.file delete it
                            if (pathMatcher2.matches(g.toPath())) {

                                // messages
                                System.out.println("...deleting og haven file...");

                                // delete
                                Files.delete(g.toPath());

                                // messages
                                System.out.println("deleting og haven file complete");
                            }


                        }


                        // replace the fucking ampersands because VTD can't handle them
                        if (g.toString().startsWith(source + "\\library\\texts") && !g.toString().endsWith(".xml")) {

                            System.err.println("file to replace found at " + g);

                            //replace ampersands
                            replaceInFile(g);

                            //replace the escape key (fuck this key)
                            replaceInFile2(g);

                         }

                        // checks for the texts files and merges
                        if (g.toString().contains(source + "\\library\\texts")) {
                            System.out.println("texts found at: " + g);

                            // merge
                            System.out.print("Merging " + g + " ");
                            Merge.mergeTextsTemp(g.toString(), source + "\\library\\texts.xml");

                            // messages
                            System.out.print("Merging complete ");

                            // creates a path matcher
                            final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(glob5);

                            // if g matches the glob5
                            if (pathMatcher.matches(g.toPath())) {

                                // messages
                                System.out.println("...deleting texts file...");

                                // delete
                                Files.delete(g.toPath());

                                // messages
                                System.out.println("deleting texts file complete");
                            }

                            // creates a path matcher
                            final PathMatcher pathMatcher2 = FileSystems.getDefault().getPathMatcher(glob8);

                            // if g matches the og texts file delete it
                            if (pathMatcher2.matches(g.toPath())) {

                                // messages
                                System.out.println("...deleting og texts file...");

                                // delete
                                Files.delete(g.toPath());

                                // messages
                                System.out.println("deleting og texts file complete");
                            }

                        }
                    }
                }

                // check if it's the texture folder
                if (f.toString().endsWith("textures") == true) {

                    // creates a new file list for the texture files
                    File[] texturesFiles = f.listFiles();

                    for (File g : texturesFiles ){


                        //System.err.println(g.toString().replace(String.valueOf(source + "\\textures\\"), ""));
                    }


                }
            }

            // messages
            System.out.println("...source folders merged...");
            modCounterDialog.setText("...source folders merged...");


            // messages
            //System.out.println("...packing .jar...");
            //modCounterDialog.setText("...Packing .jar...");

            // set up the paths (TODO: make default directory selectable (4))
            source = new File("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/source/");
            outFileName = new File("F:/Games/SteamLibrary/steamapps/common/SpaceHaven/mods/jarPacked.jar");

            // repack the jar
            //org.zeroturnaround.zip.ZipUtil.pack(new File(String.valueOf(source)), new File(String.valueOf(outFilename)));

            // messages
            //System.out.println("... .jar packed...");
            //modCounterDialog.setText("... .jar packed...");

            // messages
            System.out.println("----- loaded ------");
            modCounterDialog.setText("... loaded ...");

            // run the game
            //

            //
            //

        } else {
            // error messages
            System.err.println("No mods selected");
            modCounterDialog.setText("No mods selected");
        }
    }

    public void replaceInFile(File file) throws IOException {

        //
        System.err.println("removing ampersand");

        // creates a new
        File temp = File.createTempFile("newfile", ".txt");
        FileWriter fw = new FileWriter(String.valueOf(temp));
        BufferedReader br = new BufferedReader(new FileReader(file));

        // while the br is ready do find and replace
        while (br.ready()) {
            fw.write(br.readLine().replace("&", "and"));
        }
        // close the streams
        fw.close();
        br.close();

        Files.copy(temp.toPath(), file.toPath(), REPLACE_EXISTING);

        //
        System.err.println("ampersand removed");
    }

    public void replaceInFile2(File file) throws IOException {

        //
        System.err.println("removing special character");

        // creates a new
        File temp = File.createTempFile("newfile", ".txt");
        FileWriter fw = new FileWriter(String.valueOf(temp));
        BufferedReader br = new BufferedReader(new FileReader(file));

        while (br.ready()) {
            // while the br is ready do find and replace
            fw.write(br.readLine().replaceAll("\\u001B", ""));
        }

        // close the streams
        fw.close();
        br.close();

        Files.copy(temp.toPath(), file.toPath(), REPLACE_EXISTING);

        //
        System.err.println("special character removed");
    }

    public static void unzip(File archive, File destDir) throws IOException {
        //creates a new buffer
        byte[] buffer = new byte[256 * 1024];
        //creates directory
        destDir.mkdirs();

        // create a new jar file set it equal to archive, then go thru the entries
        // and for each entry create a new file, check if it's a directory and make one.
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

    public void exitButtonClicked() {
        // exits the program
        System.exit(0);
    }

    public void helpButtonClicked() {
        // creates a new alert popup box when the help button is clicked
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mod Loader X v0.3.1 help");
        alert.setHeaderText(null);
        alert.setContentText("Mod Loader X v0.3.1 was written in java 8 using javafx8 and was intended for use with the game Space Haven alpha 14.1.");
        alert.showAndWait();
    }


    @FXML
    public void initialize() throws IOException {

        // header message
        System.out.println("----- BEGINNING TO INITIALIZE -----");

        // setup the selected mods lists
        selectedModsUI = new ArrayList<>();
        unloadedMods = new ArrayList<>();
        loadedInfos = new ArrayList<>();
        System.out.println("ArrayLists for mods initialized!");

        // setup the selected mods mod
        Load.modInfoVM = new info();
        Load.modInfoML = new info();
        System.out.println("mods.class modVM and modML initialized!");

        // setup the labels
        modDetails.setWrapText(true);
        System.out.println("labels setup!");

        // check for info.xml
        Find.findInfos(glob, path);

        // find mods
        findModsAndBuildMenu(glob2, path);

        // footer message
        System.out.println("----- END INITIALIZE -----");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // creates the root, sets it equal to the .fxml file and then sets the stage
        Parent root = FXMLLoader.load(getClass().getResource("ModLoaderUI.fxml"));
        primaryStage.setTitle("Mod Loader X v0.3.1");
        primaryStage.setScene(new Scene(root, 1400, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}