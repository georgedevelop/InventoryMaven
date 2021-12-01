package model;

/**
 * Outsourced Class
 * Child class of Part
 * @author George
 *@version 1.0
 */
public class OutSourced extends Part{

	private String companyName;

	/**
	 * Overloaded Constructor
	 * @param id Part ID
	 * @param name Part Name
	 * @param price Part Price
	 * @param stock Part Inventory Level
	 * @param min Part Minimum Inventory Level
	 * @param max Part Maximum Inventory Level
	 * @param companyName Part Company Name
	 */
	public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
		super(id, name, price, stock, min, max);
		this.companyName = companyName;
	}

	/**
	 * Get Part Company Name
	 * @return Part Company Name
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Set Part Company Name
	 * @param companyName Part Company Name
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	
	
	
}//class
