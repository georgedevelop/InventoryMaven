/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import controller.MainController;
import controller.PartFormController;
import controller.ProductFormController;
import model.Part;
import model.Product;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This is the main class where all fxml files will load
 * @author George
 *@version 1.0
 */
public class Driver extends Application {

    private Stage primaryStage; //Main window and other will open in modal window
    

    /**
     * Load the Main Form.  
     * Set this Driver class object to the Main Form controller 
     * to load other Forms.
     *   
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Main.fxml"));
        try {
            AnchorPane root = loader.load();
            MainController mc = loader.getController();
            mc.setDriver(this);
            Scene scene = new Scene(root);
            primaryStage.setTitle("Main");
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("MainFXml File not load: " + e.getMessage());
            e.printStackTrace();
        }

    }//start
    
    /**
     * Load the PartForm. Set new stage reference to PartForm view controller
     * @return	Part which needs to add
     */
    public Part showAddNewPartForm() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/PartForm.fxml"));
        try {
            AnchorPane addPart = loader.load();

            Stage stage = new Stage();
            stage.initOwner(primaryStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);

            PartFormController apc = loader.getController();
            apc.setPartAddNewStage(stage);

            Scene scene = new Scene(addPart);
            stage.setScene(scene);
            stage.setTitle("Add New Part");

            stage.showAndWait();
            
            return apc.getAddPart();

        } catch (IOException e) {
            System.out.println("Cannot open add Part fxml" + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }//showAddNewPartForm
    
    /**
     * Load the PartForm
     * The returning part may be changed its type from to Inhouse or Outsourced.
     * @param partToModify 	Part to modify
     * @return				Modified Part
     */
    public Part showModifyPartForm(Part partToModify) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/PartForm.fxml"));
        try {
            AnchorPane editPart = loader.load();

            Stage stage = new Stage();
            stage.initOwner(primaryStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false); 
            
            PartFormController apc = loader.getController();
            apc.setPartToModify(partToModify);
            apc.setPartAddNewStage(stage);
            
            Scene scene = new Scene(editPart);
            stage.setScene(scene);

            stage.showAndWait();

            return apc.getAddPart();

        } catch (IOException e) {
            System.out.println("Cannot open Modify Part fxml" + e.getMessage());

        }

        return null;
    }//showModifyPartForm
    /**
     * Load the ProductForm
     * @param productToAdd 	to set ProductForm Controller
     * @return				ProductForm save button is clicked or not
     */
    public boolean showAddNewProductForm(Product productToAdd) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ProductForm.fxml"));
        try {
            AnchorPane addProduct = loader.load();

            Stage stage = new Stage();
            stage.initOwner(primaryStage);
            stage.initModality(Modality.WINDOW_MODAL);

            ProductFormController apc = loader.getController();
            apc.setProductToAdd(productToAdd);
            apc.setProductAddNewStage(stage);

            Scene scene = new Scene(addProduct);
            stage.setScene(scene);
            stage.setTitle("Add New Product");

            stage.showAndWait();

            return apc.isSaveClicked();

        } catch (IOException e) {
            System.out.println("Cannot open add Product fxml" + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }//showAddNewPartForm
    
    /**
     * Load the ProductForm
     * @param productToModify	to set ProductFrom Controller
     * @return					ProductForm save button is clicked or not
     */
    public boolean showModifyProductForm(Product productToModify) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ProductForm.fxml"));
        try {
            AnchorPane editProduct = loader.load();

            Stage stage = new Stage();
            stage.initOwner(primaryStage);
            stage.initModality(Modality.WINDOW_MODAL);

            ProductFormController apc = loader.getController();
            apc.setProductToModify(productToModify);
            apc.setProductAddNewStage(stage);

            Scene scene = new Scene(editProduct);
            stage.setScene(scene);
            stage.setTitle("Edit Part");

            stage.showAndWait();

            return apc.isSaveClicked();

        } catch (IOException e) {
            System.out.println("Cannot open Modify Product fxml" + e.getMessage());
        }

        return false;
    }//showModifyPartForm

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }//main
}//class
