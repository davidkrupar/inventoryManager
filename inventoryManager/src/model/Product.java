package model;

/**
 * Class Product.java
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *   This class initializes Product class which holds details and a few methods required by uml
  */

public class Product {

    /**
     * Initialize delete associated part method
     */
    public static Object deleteAssociatedPart;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * constructor used for product instances
     *
     * @param id    product id int
     * @param name  string name of product
     * @param price price as a double data type for product
     * @param stock stock for inventory purposes
     * @param min   minimum inventory set levels
     * @param max   maximum inventory set levels
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

        associatedParts = FXCollections.observableArrayList();
    }


    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = productCounter++;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param part selected part to add to associated part array
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * @param selectedAssociatedPart selected part to be deleted from associated array
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        int i = 0;
        boolean deleted = false;
        for (int j = 0; j < associatedParts.size(); ++j) {
            if (selectedAssociatedPart.getId() == associatedParts.get(j).getId()) {
                i = j;
                deleted = true;
                break;
            }
        }
        if (deleted) {
            associatedParts.remove(i);
        }
        return deleted;
    }

    /**
     * @return getAllAssociatedPArts per requirement of UML
     */
    public ObservableList<Part> getAllAssociatedParts() {
        ObservableList<Part> newAssociatedParts = FXCollections.observableArrayList();
        for (Part part : associatedParts) {
            newAssociatedParts.add(copyPart(part));
        }
        return newAssociatedParts;
    }

    /**
     * if else statement, decides on inhouse or outsource
     */
    private Part copyPart(Part partToCopy) {
        Part newPart = new InHouse();
        if (partToCopy instanceof InHouse) {
            newPart = new InHouse((InHouse) partToCopy);
        } else if (partToCopy instanceof Outsourced) {
            newPart = new Outsourced((Outsourced) partToCopy);
        }
        return newPart;
    }

    /**
     * constructor for temp usage involving associated parts
     *
     * @param productToCopy selected product for temporary storage to manage datatype
     */
    public Product(Product productToCopy) {
        this.id = productToCopy.id;
        this.name = productToCopy.name;
        this.price = productToCopy.price;
        this.stock = productToCopy.stock;
        this.min = productToCopy.min;
        this.max = productToCopy.max;

        this.associatedParts = FXCollections.observableArrayList();

/** method used for associated product temp storage
 *
 */
        for (Part part : productToCopy.associatedParts) {
            this.associatedParts.add(copyPart(part));
        }
    }

    public static int productCounter = 1;


}








