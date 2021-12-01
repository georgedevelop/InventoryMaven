/**
 * PartForm is used for both functions of Add or Modify Part
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;

/**
 * PartFormController Class
 * @author George
 *@version 1.0
 */
public class PartFormController implements Initializable {

    @FXML
    private ToggleGroup partFormToggleGroup;
    @FXML
    private RadioButton inHouseRadioButton;
    @FXML
    private RadioButton outSourcedRadioButton;

    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField stock;
    @FXML
    private TextField price;
    @FXML
    private TextField max;
    @FXML
    private TextField min;
    @FXML
    private TextField machineId;
    @FXML
    private Label machineIdLable;
    @FXML
    private TextField companyName;
    @FXML
    private Label companyNameLable;


    @FXML
    private HBox machineIdLableAndTextField;
    @FXML
    private HBox CompanyNameLableAndTextField;
    @FXML
    private VBox vbox;
    private int sizeOfVBox;

    private Part part;
    private Stage partStage;
    private boolean saveClicked = false;
    private boolean isPartForModify = false;

    /**
     * Initializable interface method which will auto execute
     * after the constructors. By default show In-House lable and textfield
     * @param location default
     * @param resources default
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        removeLastNodeInVBox();
        sizeOfVBox = vbox.getChildren().size();
        showMachineIdLableAndTextField();
    }//initialize

    /**
     * Replacing the hbox node of company name label and textfield with
     * the other hbox node which has machine id label and textfield.
     */
    private void showCompanyNameLableAndTextField() {
        vbox.getChildren().set(sizeOfVBox - 2, CompanyNameLableAndTextField);
    }

    /**
     * Replacing the hbox node of machine id label and textfield with
     * the other hbox node which has company name label and textfield.
     */
    private void showMachineIdLableAndTextField() {
        vbox.getChildren().set(sizeOfVBox - 2, machineIdLableAndTextField);
    }

    /**
     * Last node of hbox which has company name or machine id along textfield to remove
     * then new node can easily be set there
     */
    private void removeLastNodeInVBox() {
        vbox.getChildren().remove(vbox.getChildren().size() - 2);
    }

    /**
     * Handle In-House Radio button click.
     * If In-House then show machine-id label and textfield.
     * If company name is placed then we need to remove the last hbox which
     * has company name label along textfield.
     * @param event In-House Radio button check
     */
    @FXML
    void handleInHouseRadioButton(ActionEvent event) {
        showMachineIdLableAndTextField();
    }

    /**
     * Handle Out-Sourced Radio button click.
     * If Out-Sourced then show machine-id label and textfield.
     * If Machine ID is placed then we need to remove the last hbox which
     * has Machine label along textfield.
     * @param event Out-Sourced Radio button check
     */
    @FXML
    void handleOutSourcedRadioButton(ActionEvent event) {
        showCompanyNameLableAndTextField();
    }

    /**
     * Close the partFormStage
     *
     * @param event
     */
    @FXML
    private void handleCancelButton(ActionEvent event) {
        partStage.close();
    }//handleCancelButton

    /**
     * This method set the input values to the part which is passed from
     * partToAdd or partToModify method. Perform input validation. Check if max
     * greater than min otherwise display and error message("Max should be
     * greater than min"). Check if the inventory/stock level is between max and
     * min otherwise display and error message("Inventory should be between max
     * and min").
     *
     * @param event
     */
    @FXML
    private void handleSaveButton(ActionEvent event) {

        if (isInputValuesValidate()) {
        	int tempPartId = generatePartId();
        	if(isPartForModify)
        		tempPartId = this.part.getId();
        	
            Double tempPartPrice = Double.parseDouble(price.getText());
            int tempPartStock = Integer.parseInt(stock.getText());
            int tempPartMax = Integer.parseInt(max.getText());
            int tempPartMin = Integer.parseInt(min.getText());

            if (!(tempPartMax > tempPartMin)) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("");
                alert.setContentText("Max should be greater than min");
                alert.showAndWait();

            } else if (!(tempPartStock >= tempPartMin)
                    || !(tempPartStock <= tempPartMax)) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("");
                alert.setContentText("Inventory should be between max and min");
                alert.showAndWait();

            } else {
                
                if (outSourcedRadioButton.isSelected()) {
                    part = new OutSourced(tempPartId, name.getText(), tempPartPrice,
                            tempPartStock, tempPartMin, tempPartMax, companyName.getText());
                } else {

                    part = new InHouse(tempPartId, name.getText(), tempPartPrice, tempPartStock, tempPartMin, tempPartMax,
                            Integer.parseInt(machineId.getText()));
                }
                saveClicked = true;
                partStage.close();
            }//if
        }//IsInputValidate

    }//handleSaveButton

    /**
     * Check all input values are valid or not. It will check if the required
     * input fields are empty then display an error message("Please fill the all
     * fields"). Check if the price value is not valid then display an error
     * message("Enter numbers for price"). Check if the inventory level value is
     * not valid then display an error message("Enter numbers for inventory").
     * Check if the max value is not valid then display an error message("Enter
     * numbers for max"). Check if the min value is not valid then display an
     * error message("Enter numbers for min").
     *
     * @return
     */
    private boolean isInputValuesValidate() {

        //empty Value Validate
        if (name.getText().isEmpty() || price.getText().isEmpty()
                || stock.getText().isEmpty() || max.getText().isEmpty()
                || min.getText().isEmpty() || (companyName.getText().isEmpty()
                && machineId.getText().isEmpty())) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("");
            alert.setContentText("Please fill all the fields a Part");
            alert.showAndWait();
            return false;
        }//if

        //validate price
        try {
            Double.parseDouble(price.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Part Error");
            alert.setContentText("Enter numbers for price");
            alert.showAndWait();
            return false;
        }

        //validate inventory
        try {
            Integer.parseInt(stock.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Part Error");
            alert.setContentText("Enter numbers for inventory");
            alert.showAndWait();
            return false;
        }

        //validate Max
        try {
            Integer.parseInt(max.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Part Error");
            alert.setContentText("Enter numbers for max");
            alert.showAndWait();
            return false;
        }

        //validate Min
        try {
            Integer.parseInt(min.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Part Error");
            alert.setContentText("Enter numbers for min");
            alert.showAndWait();
            return false;
        }

        //validate MachineId
        if (inHouseRadioButton.isSelected()) {
            try {
                Integer.parseInt(machineId.getText());
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Part Error");
                alert.setContentText("Enter numbers for Machine ID");
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }//isInputValuesValidate

    /**
     * @return max id in the partList by increment 1
     */
    private int generatePartId() {
        //ObservableList<Part> list = this.driver.getInventory().getAllParts();
        ObservableList<Part> list = Inventory.getAllParts();
        int max = -1;
        if (!list.isEmpty()) {
            max = list.get(0).getId();
        }
        for (Part part : list) {
            if (part.getId() > max) {
                max = part.getId();
            }
        }
        return max + 1;
    }

    /**
     * Part Stage for to close when product is added or updated successfully
     * @param s set partAddNewStage
     */
    public void setPartAddNewStage(Stage s) {
        this.partStage = s;
    }

    /**
     * @param part set part
     */
    public void setPartToAdd(Part part) {
        this.part = part;
    }

    /**
     * populate the data in partForm of the passed parameter
     *
     * @param part set part
     */
    public void setPartToModify(Part part) {
        this.part = part;
        this.isPartForModify = true;
        if (this.part instanceof OutSourced) {
            OutSourced osp = (OutSourced) this.part;
            outSourcedRadioButton.setSelected(true);
            showCompanyNameLableAndTextField();
            this.companyName.setText("" + osp.getCompanyName());
        } else {
            InHouse ihp = (InHouse) this.part;
            inHouseRadioButton.setSelected(true);
            showMachineIdLableAndTextField();
            this.machineId.setText("" + ihp.getMachineId());
        }
        System.out.println("ID: " + part.getId()
                + "\nName: " + part.getName()
                + "\nPice: " + part.getPrice()
                + "\nStock: " + part.getStock()
                + "\nMin: " + part.getMin()
                + "\nSMax: " + part.getMax()
        );
        this.id.setText("" + part.getId());
        this.name.setText(part.getName());
        this.stock.setText("" + part.getStock());
        this.price.setText("" + part.getPrice());
        this.max.setText("" + part.getMax());
        this.min.setText("" + part.getMin());
    }


    /**
     * Get the updated or new part
     * @return
     */
    public Part getAddPart() {
        return this.part;
    }


}//class
