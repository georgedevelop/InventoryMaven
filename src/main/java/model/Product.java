package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product Class
 * @author George
 *@version 1.0
 */
public class Product {

	private ObservableList<Part> associatedParts;
	private int id;
	private String name;
	private double price;
	private int stock;
	private int min;
	private int max;

	/**
	 * Overloaded Constructor
	 * @param id Product ID
	 * @param name Product Name
	 * @param price Product cost/price per unit
	 * @param stock Product inventory level
	 * @param min Product minimum stock level
	 * @param max Product maximum stock level
	 */
	public Product(int id, String name, double price, int stock, int min, int max) {
		this.associatedParts = FXCollections.observableArrayList();
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.min = min;
		this.max = max;
	}
	// All getters and setters

	/**
	 * Set the ID of the product
	 * @param id Product iD
	 */
	public void setId(int id){
		this.id = id;
	}
	/**
	 * @return Product ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get Product name
	 * @return product name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set Product name
	 * @param name name of the product to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get product price
	 * @return Product price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Set product price
	 * @param price Product Price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Get Product Inventory Level
	 * @return Product Inventory level
	 */
	public int getStock() {
		return stock;
	}

	/**
	 * Set Product Stock
	 * @param stock Product inventory level
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}

	/**
	 * Get Minimum Inventory Level
	 * @return Product minimum inventory level
	 */
	public int getMin() {
		return min;
	}

	/**
	 * Set Product Minimum Inventory level
	 * @param min Product minimum inventory level
	 */
	public void setMin(int min) {
		this.min = min;
	}

	/**
	 * Get Maximum Inventory Level
	 * @return Product Maximum Inventory level
	 */
	public int getMax() {
		return max;
	}

	/**
	 * Set Product maximum inventory level
	 * @param max Product maximum inventory level
	 */
	public void setMax(int max) {
		this.max = max;
	}

	/**
	 * Associate new part with the product
	 * @param part Part to associate with the product
	 */
	public void addAssociatedPart(Part part){
		this.associatedParts.add(part);
	}

	/**
	 * Delete the associate part from the product
	 * @param selectedAssociatedPart Part to delete
	 * @return part is deleted or not
	 */
	public boolean deleteAssociatedPart(Part selectedAssociatedPart){
		return this.associatedParts.remove(selectedAssociatedPart);
	}

	/**
	 * Get all associated part
	 * @return List of all associated part.
	 */
	public ObservableList<Part> getAllAssociatedParts(){
		return this.associatedParts;
	}

	/**
	 * String Format of the product
	 * @return Product String format
	 */
	public String toString() {
		return "\n id: "+this.id+
				"\n Name: "+this.name+
				"\n price: "+this.price+
				"\n Stock: "+this.stock+
				"\n Min: "+this.min+
				"\n Max: "+this.stock;
				
	}
	

}//class
