/**
 * ProductForm is used for both functions of Add or Modify Product
 */
package controller;

import model.Inventory;
import model.Part;
import model.Product;
//import static controller.MainController.partsList;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * ProductFormController Class
 * @author George
 *@version 1.0
 */
public class ProductFormController implements Initializable {

    //All are fxml references
	
    @FXML private TextField id;
    @FXML private TextField name;
    @FXML private TextField price;
    @FXML private TextField stock;
    @FXML private TextField max;
    @FXML private TextField min;
    
    @FXML private TextField partSearchTextField;
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> idColumnPartTableView;
    @FXML private TableColumn<Part, String> nameColumnPartTableView;
    @FXML private TableColumn<Part, Double> priceColumnPartTableView;
    @FXML private TableColumn<Part, Integer> inventoryColumnPartTableView;

    @FXML private TableView<Part> assPartTableView;
    @FXML private TableColumn<Part, Integer> assIdColumnPartTableView;
    @FXML private TableColumn<Part, String> assNameColumnPartTableView;
    @FXML private TableColumn<Part, Double> assPriceColumnPartTableView;
    @FXML private TableColumn<Part, Integer> assInventoryColumnPartTableView;


    //private Driver driver;
    /**
     * Used to take reference of product that is passed
     * from the setProductToAdd or setProductToModify methods 
     */
    private Product product;
    
    /**
     * Used to take reference of ProductForm stage that is 
     * passed from the setProductAddNewStage method.
     */
    private Stage productStage;
    
    /**
     * Check if the save button clicked or not. It is used only 
     * for isSaveClicked. 
     */
    private boolean saveClicked = false;


    /**
     * This method will execute immediately after the constructor.
     * Then partlist and products will be set to the table.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
        //Part TableView 
        idColumnPartTableView.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        nameColumnPartTableView.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        priceColumnPartTableView.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        inventoryColumnPartTableView.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partSearchTextField.textProperty().addListener(new InvalidationListener() {
			
			@Override
			public void invalidated(Observable o) {
				partTableView.setItems(Inventory.lookupPart(partSearchTextField.getText()));
			}
		});
        
        //Associate Part TableView
        assIdColumnPartTableView.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        assNameColumnPartTableView.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        assPriceColumnPartTableView.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        assInventoryColumnPartTableView.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        
        

    }//initialize

    /**
     * Handle the add button whenever the event happen then
     * add the part which is selected from the part list
     * will be added to the associated list of the product. 
     * if the part is not selected from the part list then it display
     * an error message (Please select any part) in a dialogue.
     * if the the part is already in associated list then it display
     * an error message (This part is already associated)
     * @param event
     */
    @FXML
    private void handleAddButton(ActionEvent event) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
       
        if (selectedPart != null) {
            if (!this.product.getAllAssociatedParts().contains(selectedPart)) {
                this.product.getAllAssociatedParts().add(selectedPart);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Associated Part Error");
                alert.setContentText("This part is already associated");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Associated Part Error");
            alert.setContentText("Please Select any Part");
            alert.showAndWait();
        }
    }//handleAddButton

    /**
     * Cancel button close the ProductForm Stage
     * @param event
     */
    @FXML
    private void handleCancelButton(ActionEvent event) {
        this.productStage.close();
    }//handleCancelButton

    /**
     * Remove button will remove the part which is in 
     * part associated list ("Associated parts of the product")
     * if the associated part is empty then show an error
     * message("Please select a associated part") of Nothing to remove
     * in a dialogue.
     * @param event
     */
    @FXML
    private void handleRemoveAssButton(ActionEvent event) {
        Part removeAssPart = assPartTableView.getSelectionModel().getSelectedItem();
        if (removeAssPart != null) {
            this.product.getAllAssociatedParts().remove(removeAssPart);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Nothing to Remove");
            alert.setContentText("Please Select a associated Part");
            alert.showAndWait();
        }
    }//handleRemoveAssButton

    /**
     * Save button set the input values that are given 
     * form the ProductForm to the productToAdd.
     * Check if the max is greater than the min otherwise display 
     * an error message("Max should be greater then min").
     * Check  is the inventory level is between the max and min
     * otherwise display an error message("Inventory should be between max and min").
     * And then close the ProductForm.
     * @param event
     */
    @FXML
    private void handleSaveButton(ActionEvent event) {

        System.out.println("Save is click");

        if (isInputValuesValidate()) {
            int tempProductId = generateProductId();
            String tempProductName = name.getText();
            Double tempProductPrice = Double.parseDouble(price.getText());
            int tempProductStock = Integer.parseInt(stock.getText());
            int tempProductMax = Integer.parseInt(max.getText());
            int tempProductMin = Integer.parseInt(min.getText());

            if (!(tempProductMax > tempProductMin)) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Product Error");
                alert.setContentText("Max should be greater than min");
                alert.showAndWait();

            } else if (!(tempProductStock >= tempProductMin)
                    || !(tempProductStock <= tempProductMax)) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Product Error");
                alert.setContentText("Inventory should be between max and min");
                alert.showAndWait();

            } else {
                product.setId(tempProductId);
                product.setName(tempProductName);
                product.setPrice(tempProductPrice);
                product.setStock(tempProductStock);
                product.setMax(tempProductMax);
                product.setMin(tempProductMin);
                

                saveClicked = true;
                productStage.close();
            }//ele

        }//IsInputValidate

    }//handleSaveButton

    /**
     * Check all input values are valid or not. It will
     * check if the required input fields are empty then display
     * an error message("Please fill the all fields").
     * Check if the price value is not valid then display
     * an error message("Enter numbers for price").
     * Check if the inventory level value is not valid then display
     * an error message("Enter numbers for inventory").
     * Check if the max value is not valid then display
     * an error message("Enter numbers for max").
     * Check if the min value is not valid then display
     * an error message("Enter numbers for min").
     * 
     * @return
     */
    private boolean isInputValuesValidate() {

        //empty Value Validation
        
        if (name.getText().isEmpty() || price.getText().isEmpty()
                || stock.getText().isEmpty() || max.getText().isEmpty()
                || min.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Product Error");
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
            return false;
        }//if

        //validate price
        try {
            Double.parseDouble(price.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Product Error");
            alert.setContentText("Enter numbers for price");
            alert.showAndWait();
            return false;
        }

        //validate inventory
        try {
            Integer.parseInt(stock.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Product Error");
            alert.setContentText("Enter numbers for inventory");
            alert.showAndWait();
            return false;
        }

        //validate Max
        try {
            Integer.parseInt(max.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Product Error");
            alert.setContentText("Enter numbers for max");
            alert.showAndWait();
            return false;
        }

        //validate Min
        try {
            Integer.parseInt(min.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Product Error");
            alert.setContentText("Enter numbers for min");
            alert.showAndWait();
            return false;
        }
        return true;
    }//isInputValuesValidate

    /**
     * @return max id in the productList by increment 1
     */
    private int generateProductId() {
        ObservableList<Product> list = Inventory.getAllProducts();
        int max = -1;
        if (!list.isEmpty()) {
            max = list.get(0).getId();
        }
        for (Product product : list) {
            if (product.getId() > max) {
                max = product.getId();
            }
        }
        return max + 1;
    }
    
    /**
     * @param s set productStage
     */
    public void setProductAddNewStage(Stage s) {
        this.productStage = s;
        partTableView.setItems(Inventory.getAllParts());
        assPartTableView.setItems(this.product.getAllAssociatedParts());
    }
    
    /**
     * @param product set product
     */
    public void setProductToAdd(Product product) {
        this.product = product;
    }

    /**
     * populate the data in ProductForm of the passed parameter
     * @param product set product
     */
    public void setProductToModify(Product product) {
        this.product = product;

        this.id.setText("" + product.getId());
        this.name.setText(product.getName());
        this.stock.setText("" + product.getStock());
        this.price.setText("" + product.getPrice());
        this.max.setText("" + product.getMax());
        this.min.setText("" + product.getMin());

        this.assPartTableView.setItems(product.getAllAssociatedParts());
    }

    /**
     * @return saveClicked
     */
    public boolean isSaveClicked() {
        return saveClicked;
    }

}//class
