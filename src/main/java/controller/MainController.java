package controller;

import java.net.URL;
import java.util.ResourceBundle;

//import com.sun.javafx.scene.control.skin.TableViewSkinBase;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import app.Driver;
import model.*;

/**
 * MainController Class
 * This class handle actions and manipulate data on main view
 * where product list and part list are showing int he tableview
 * @author George
 *@version 1.0
 */
public class MainController implements Initializable {

	/**
	 * these all are fxml references which belongs to fxid of each node of view.
	 */
	@FXML private TableView<Part> partTableView;
	@FXML private TableColumn<Part, Integer> partIdTableColumn;
	@FXML private TableColumn<Part, String> partNameTableColumn;
	@FXML private TableColumn<Part, Double> partPriceTableColumn;
	@FXML private TableColumn<Part, Integer> partInventoryTableColumn;

	@FXML private TableView<Product> productTableView;
	@FXML private TableColumn<Product, Integer> productIdTableColumn;
	@FXML private TableColumn<Product, String> productNameTableColumn;
	@FXML private TableColumn<Product, Double> productPriceTableColumn;
	@FXML private TableColumn<Product, Integer> productStockTableColumn;

	@FXML private TextField partSearchTextField;

	@FXML private TextField productSearchTextField;


	/**
	 * It is used to load all other fxml files.
	 */
	private Driver driver;


	/**
	 * Populate the partList data into the part table view.
	 * Populate the productList data into the product table view.
	 * Search is handle here for product and part. Search Product and part
	 * will get list from inventory class methods.
	 */


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		partIdTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
		partNameTableColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
		partPriceTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
		partInventoryTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
		partTableView.setItems(Inventory.getAllParts());
		//Part Search
		partSearchTextField.textProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable o) {
				partTableView.setItems(Inventory.lookupPart(partSearchTextField.getText()));
			}
		});

		productIdTableColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
		productNameTableColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		productPriceTableColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
		productStockTableColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
		productTableView.setItems(Inventory.getAllProducts());
		//Product search
		productSearchTextField.textProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable o) {
				productTableView.setItems(Inventory.lookupProduct(productSearchTextField.getText()));
			}
		});



	}//initialize


	/**
	 * If the returning product to add in the list
	 * ig not null then add in the list. The part to add may
	 * be In-House or Outsourced. When Part object in PartFormController
	 * successfully created then return back here otherwise a null reference
	 * will be back. If is null that's mean user not created any part object.
	 * @param event Add new part button action
	 */
	@FXML
	private void handlePartNewButton(ActionEvent event) {

		Part partToAdd = driver.showAddNewPartForm();
		if (partToAdd != null) {
			Inventory.addPart(partToAdd);
			partTableView.refresh();
		}//if
	}//handlePartNewButton

	/**
	 * The part (which is to be modify) pass to the PartFromController class
	 * through Driver class for modify the part. For this part should be 
	 * selected otherwise display and error message ("Please Select any Part").
	 * When the selected part pass to the partFormController through driver. Then on the
	 * basis of this object. The values will be loaded in that GUI for update.
	 * The part to update returning reference may change because if the selected
	 * part object is from In-House then user change its type to OutSourced.
	 * then the new reference will be back. Then the OutSourced object will be
	 * placed on In-House object.
	 * @param event
	 */
	@FXML
	private void handlePartModifyButton(ActionEvent event) {

		Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
		if (selectedPart != null) {
			Part modifiedPart = driver.showModifyPartForm(selectedPart);
			updateAssociatedPart(selectedPart, modifiedPart);
			partTableView.refresh();

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Nothing to Edit");
			alert.setContentText("Please Select any Part");
			alert.showAndWait();
		}//if
	}//handlePartModifyButton

	/**
	 * Handle to delete the part from the part list.
	 * First select a part to delete in partTableView otherwise display 
	 * an error message ("Please select a part"). 
	 * @param event
	 */
	@FXML
	private void handlePartDeleteButton(ActionEvent event) {

		Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
		if (selectedPart != null) {
			Inventory.deletePart(selectedPart);
			partTableView.refresh();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Noting to Delete");
			alert.setContentText("Please select a Part");
			alert.showAndWait();
		}//if
	}//handlePartDeleteButton

	/**
	 * Update the associated part.
	 * Part type may change during updating.
	 * Check if the the part to update or updated part are the same
	 * instance mean user only change the values not type. Then values change
	 * Other if the user change its type then we need to place the modified part
	 * in place of selected part. Also change in associated part list in each product
	 * @param selectedPart
	 * @param modifiedPart
	 */
	private void updateAssociatedPart(Part selectedPart, Part modifiedPart) {
		if((selectedPart instanceof InHouse)&& (modifiedPart instanceof InHouse)) {
			InHouse mp = (InHouse)modifiedPart;
			InHouse sp = (InHouse)selectedPart;
			sp.setName(mp.getName());
			sp.setPrice(mp.getPrice());
			sp.setStock(mp.getStock());
			sp.setMin(mp.getMin());
			sp.setMax(mp.getMax());
			sp.setMachineId(mp.getMachineId());
		}else if((selectedPart instanceof OutSourced)&& (modifiedPart instanceof OutSourced)) {
			OutSourced mp = (OutSourced)modifiedPart;
			OutSourced sp = (OutSourced)selectedPart;
			sp.setName(mp.getName());
			sp.setPrice(mp.getPrice());
			sp.setStock(mp.getStock());
			sp.setMin(mp.getMin());
			sp.setMax(mp.getMax());
			sp.setCompanyName(mp.getCompanyName());
		}else {

			for (Product product : Inventory.getAllProducts()) {
				if(product.getAllAssociatedParts().contains(selectedPart)) {
					int index = product.getAllAssociatedParts().indexOf(selectedPart);
					product.getAllAssociatedParts().set(index, modifiedPart);
				}
			}//for

			//set part in partlist
			int selectedPartIndexInList = Inventory.getAllParts().indexOf(selectedPart);
			Inventory.getAllParts().set(selectedPartIndexInList, modifiedPart);
			partTableView.setItems(Inventory.getAllParts());
		}//if

	}//updatePart

	//Methods For Product


	/**
	 * A temporary product object is created here.
	 * Then pass to the ProductFromController class.
	 * If the saveClicked is true then add this temporary product
	 * in the product list.
	 * the savedClicked will be true when user successfully add the
	 * values of the product. Then this reference will be add in the list
	 *
	 * @param event
	 */
	@FXML
	private void handleProductNewButton(ActionEvent event) {
		Product tempProduct = new Product(0, "", 0, 0, 0, 0);
		boolean isSaveClicked = driver.showAddNewProductForm(tempProduct);
		if (isSaveClicked) {
			Inventory.addProduct(tempProduct);
			productTableView.refresh();
		}//if
	}//handleProductNewButton

	/**
	 * First select a product to delete in productTableView otherwise display 
	 * an error message ("Please select a product").
	 * The selected product reference will be pass to the product controller.
	 * when product will be update mean it has the same reference the changes
	 * will be shown automatically. Refresh product tableview will updated list.
	 * @param event
	 */
	@FXML
	private void handleProductModifyButton(ActionEvent event) {
		Product productToModify = productTableView.getSelectionModel().getSelectedItem();
		if(productToModify != null){
			driver.showModifyProductForm(productToModify);
			productTableView.refresh();
		}else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Noting to Edit");
			alert.setContentText("Please select a product");
			alert.showAndWait();  
		}//if
	}//handleProductModifyButton

	/**
	 * First select a product to delete in productTableView otherwise display 
	 * an error message ("Please select a product").
	 * If selected then product will be deleted
	 * @param event
	 */
	@FXML
	private void handleProductDeleteButton(ActionEvent event) {
		Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
		if (selectedProduct != null) {
			Inventory.deleteProduct(selectedProduct);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Noting to Delete");
			alert.setContentText("Please select a product");
			alert.showAndWait();
		}//if
	}//handleProductDeleteButton

	/**
	 * Close the application
	 * @param event
	 */
	//SYSTEM EXIT
	@FXML
	private void handleExitButton(ActionEvent event) {
		Platform.exit();
	}//handleExitButton

	/**
	 * Get the driver reference for load all the GUIs from there
	 * @param driver set driver
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}//setDriver

}//class
