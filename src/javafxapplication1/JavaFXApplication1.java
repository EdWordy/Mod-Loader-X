/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package javafxapplication1;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;



public class JavaFXApplication1 extends Application { 

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ModLoaderUI.fxml"));
        primaryStage.setTitle("Mod Loader X v0.1");
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
        
        // TO DO
        System.out.println("..Mod Added..");
        // some code here
        }

    public void removeMod()
        {
        
        // TO DO
        System.out.println("..Mod Removed..");
        // some code here
        }

      



}
