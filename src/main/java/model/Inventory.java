package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Inventory Class
 * All methods are here for CRUD or Search Operations.
 * @author George
 *@version 1.0
 */
public class Inventory {

	private static ObservableList<Part> allParts = FXCollections.observableArrayList();
	private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

	/**
	 * Add new part in the part list
	 * @param newPart part to add in the list
	 */
	public static void addPart(Part newPart){
            allParts.add(newPart);
	}

	/**
	 * Add new product in the product list
	 * @param newProduct product to add
	 */
	public static void addProduct(Product newProduct){
            allProducts.add(newProduct);
	}

	/**
	 * Get part from the list from the give index.
	 * @param partId index of part
	 * @return Part
	 */
	public static Part lookupPart(int partId){
		return allParts.get(partId);
	}

	/**
	 * Get product from list
	 * @param productId index of product id
	 * @return Product
	 */
	public static Product lookupProduct(int productId){
		return allProducts.get(productId);
	}

	/**
	 * Get list of parts whose part's name or id contain the
	 * search text or pattern.if the search text exists in the
	 * name or id of any part. it will make a list of that parts
	 * and then return that list. if not any product exist then empty
	 * list will return. It works like Live-Search.
	 * @param partName Id or name to search
	 * @return The list of all parts whose id or name contains the search pattern
	 */
	public static ObservableList<Part> lookupPart(String partName){
		FilteredList<Part> filteredPartList = new FilteredList(allParts);
		filteredPartList.filtered(part-> part instanceof Part);
		filteredPartList.setPredicate(part->{

			String partFilter = partName.toLowerCase();
			// If filter text is empty, display all parts.
			if (partFilter.isEmpty()) {
				return true;
			}
			if (Integer.toString(part.getId()).toLowerCase().contains(partFilter)||
					part.getName().toLowerCase().contains(partFilter)) {
				return true; // Filter matches name.
			}
			return false; // Does not match.

		});

		return FXCollections.observableArrayList(filteredPartList);
	}//lookupPart

	/**
	 * Get list of products whose product's name contain the
	 * search text or pattern. if the search text exists in the
	 * name or id of any product. it will make a list of that products
	 * and then return that list if not any product exist then empty
	 * list will return. It works like Live-Search.
	 * @param productName Id or name to search
	 * @return The list of all products whose id or name contains the search pattern
	 */
	public static  ObservableList<Product> lookupProduct(String productName){
		FilteredList<Product> filteredProductList = new FilteredList(allProducts);
		filteredProductList.filtered(product-> product instanceof Product);
		filteredProductList.setPredicate(product->{

			String productFilter = productName.toLowerCase();
			// If filter text is empty, display all products.
			if (productFilter.isEmpty()) {
				return true;
			}
			if (product.getName().toLowerCase().contains(productFilter)||
				Integer.toString(product.getId()).toLowerCase().contains(productFilter)) {
				return true; // Filter matches name.
			}
			return false; // Does not match.

		});

		return FXCollections.observableArrayList(filteredProductList);
	}//lookupProduct

	/**
	 * Set the part at the given index of the list
	 * @param index At position to set part
	 * @param selectedPart Part to set
	 */
	public static void updatePart(int index, Part selectedPart){
		allParts.set(index, selectedPart);
	}

	/**
	 * Set product at given index in the product list
	 * @param index At position where product to set
	 * @param selectedProduct Product to set.
	 */
	public static void updateProduct(int index, Product selectedProduct){
		allProducts.set(index, selectedProduct);
	}

	/**
	 * Delete part form	the partlist. Then the part should be deleted
	 * in the associated products.
	 * @param selectedPart Part to delete
	 * @return Part is deleted or not
	 */
	public static boolean deletePart(Part selectedPart){
		for (Product p : Inventory.allProducts) {
			if (p.getAllAssociatedParts().contains(selectedPart))
				p.deleteAssociatedPart(selectedPart);
		}
		return allParts.remove(selectedPart);
	}

	/**
	 * Delete product from the product list.
	 * @param selectedProduct Product to delete
	 * @return Product deleted or not
	 */
	public static boolean deleteProduct(Product selectedProduct){
		return allProducts.remove(selectedProduct);
	}

	/**
	 * Get all parts
	 * @return part list
	 */
	public static ObservableList<Part> getAllParts(){
		return allParts;
	}

	/**
	 * Get all products
	 * @return product list
	 */
	public static ObservableList<Product> getAllProducts(){
		return allProducts;
	}


}//class
