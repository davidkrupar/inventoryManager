package model;

/**
 * class Outsourced.java
 */

 /**
  * This class initializes Outsourced class which holds details on the outsourced part
  */


public class Outsourced extends Part {

    /**
     * Private string as required by UML
     */
    private String companyName;

    /**
     * Constructor for OutsourcedParts
     *
     * @param companyName, id, name, price, stock, min, max
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * getCompanyName used for parts lists
     *
     * @return returns company name for outsourced part
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * setCompanyName used for setting companyName for Outsourced parts
     *
     * @param companyName company string for outsourced part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * added second constructor for associated part management for products
     *
     * @param partToCopy part selection for temp holding
     */
    public Outsourced(Outsourced partToCopy) {
        super(partToCopy.getId(), partToCopy.getName(), partToCopy.getPrice(), partToCopy.getStock(), partToCopy.getMin(), partToCopy.getMax());
        this.companyName = partToCopy.companyName;
    }

    /**
     * Filler constructor for tables
     */
    public Outsourced() {
        super(00, "Fill", 00, 00, 00, 00);
        this.companyName = "Dummy Company";
    }
}