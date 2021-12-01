package model;

/**
 * InHouse Class
 * This is th child class of Part.
 * @author George
 *@version 1.0
 */
public class InHouse extends Part {

	private int machineId;

	/**
	 * In House overloaded constructor
	 * @param id Part ID
	 * @param name Part Name
	 * @param price Part Price
	 * @param stock Part Inventory Level
	 * @param min Part Minimum Inventory Level
	 * @param max Part maximum Inventory Level
	 * @param machineId Part Machine ID
	 */
	public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
		super(id, name, price, stock, min, max);
		this.machineId = machineId;
	}

	/**
	 * Get Part Machine ID
	 * @return Part Id
	 */
	public int getMachineId() {
		return machineId;
	}

	/**
	 * Set Part ID
	 * @param machineId Machine ID
	 */
	public void setMachineId(int machineId) {
		this.machineId = machineId;
	}

	
}//Class
