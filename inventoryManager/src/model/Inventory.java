package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

/**
 * This class initializes Inventory class which holds many methods for app
 */
public class Inventory {

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static int uniqueProductID = 1;

    private static int uniquePartID = 1;


    /**
     * addpart method used to add part
     *
     * @param newPart the new part
     */
    public static void addPart(Part newPart) {
        newPart.setId(uniquePartID);
        uniquePartID++;
        allParts.add(newPart);
    }

    /**
     * addproduct method used to add product
     *
     * @param newProduct the new product
     */
    public static void addProduct(Product newProduct) {
        newProduct.setId(uniqueProductID);
        uniqueProductID++;
        allProducts.add(newProduct);
    }

    /**
     * lookupPart loop used for part searching per int ID
     *
     * @param partID the part identifier
     * @return foundPart for match
     */
    public static Part lookupPart(int partID) {
        Part foundPart = null;
        for (Part part : allParts) {
            if (part.getId() == partID) {
                foundPart = copyPart(part);
            }
        }
        return foundPart;
    }

    /**
     * lookupProduct used for product searching per int ID
     *
     * @param productID is product identifier
     * @return foundProduct returns if product is match
     */
    public static Product lookupProduct(int productID) {
        Product foundProduct = null;
        for (Product product : allProducts) {
            if (product.getId() == productID) {
                foundProduct = new Product(product);
            }
        }
        return foundProduct;
    }

    /**
     * lookuppart using string name
     *
     * @param partName string value for part
     * @return foundParts is a match if found
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        boolean isText = !partName.matches(".*\\d.*");

        if (isText) {
            for (Part foundPart : getAllParts()) {
                //conditional statement makes both the searchInput string and the partName String lowerCase so that it can disregard capitalization
                if (foundPart.getName().toLowerCase(Locale.ROOT).contains(partName.toLowerCase(Locale.ROOT))) {
                    foundParts.add(foundPart);
                } else if (foundPart.getName().equals("")) {
                    foundParts = getAllParts();
                }
            }
        } else {
            for (Part foundPart : getAllParts()) {
                if (foundPart.getId() == Integer.parseInt(partName)) {
                    foundParts.add(foundPart);
                } else if (foundPart.getName().equals("")) {
                    foundParts = getAllParts();
                }
            }
        }

        return foundParts;
    }

    /**
     * lookupproduct using string
     *
     * @param productName string value for product
     * @return foundProducts is a match of product
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        boolean isText = !productName.matches(".*\\d.*");

        if (isText) {
            for (Product foundProduct : getAllProducts()) {
                //conditional statement makes both the searchInput string and the partName String lowerCase so that it can disregard capitalization
                if (foundProduct.getName().toLowerCase(Locale.ROOT).contains(productName.toLowerCase(Locale.ROOT))) {
                    foundProducts.add(foundProduct);
                } else if (foundProduct.getName().equals("")) {
                    foundProducts = getAllProducts();
                }
            }
        } else {
            for (Product foundProduct : getAllProducts()) {
                if (foundProduct.getId() == Integer.parseInt(productName)) {
                    foundProducts.add(foundProduct);
                } else if (foundProduct.getName().equals("")) {
                    foundProducts = getAllProducts();
                }
            }
        }

        return foundProducts;
    }

    /**
     * updates part using for loop and index
     *
     * @param index        is array index for part
     * @param selectedPart is part used for update
     */
    public static void updatePart(int index, Part selectedPart) {
        int i = -1;
        for (Part p : getAllParts()) {
            i++;
            if (p.getId() == index) {
                getAllParts().set(i, selectedPart);
                return;
            }
        }
    }

    /**
     * updates product using for loop and index
     *
     * @param index      is array index of product
     * @param newProduct is product selected for updating
     */
    public static void updateProduct(int index, Product newProduct) {
        int i = -1;
        for (Product p : getAllProducts()) {
            i++;
            if (p.getId() == index) {
                getAllProducts().set(i, newProduct);
                return;
            }
        }
    }

    /**
     * delete part using for loop and if statement
     *
     * @param selectedPart part activated for delete
     * @return boolean for delete loop
     */
    public static boolean deletePart(Part selectedPart) {
        int i = 0;
        boolean deleted = false;
        for (int j = 0; j < allParts.size(); ++j) {
            if (selectedPart.getId() == allParts.get(j).getId()) {
                i = j;
                deleted = true;
                break;
            }
        }
        if (deleted) {
            allParts.remove(i);
        }
        return deleted;
    }

    /**
     * delete product using for loop and if statement
     *
     * @param selectedProduct product selected for delete
     * @return deleted boolean for delete and exit loop
     */
    public static boolean deleteProduct(Product selectedProduct) {
        int i = 0;
        boolean deleted = false;
        for (int j = 0; j < allProducts.size(); ++j) {
            if (selectedProduct.getId() == allProducts.get(j).getId()) {
                i = j;
                deleted = true;
                break;
            }
        }
        if (deleted) {
            allProducts.remove(i);
        }
        return deleted;
    }

    /**
     * getallparts method to retrieve parts
     *
     * @return allParts list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;

    }

    /**
     * getallproducts method to retrieve products
     *
     * @return allProducts list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;

    }

    /**
     * if else if loop to determine inhouse or outsource part
     *
     * @param partToCopy if else statement for instance of parts
     * @return newpart after sorting of inhouse and outsource
     */
    public static Part copyPart(Part partToCopy) {
        Part newPart = new InHouse();
        if (partToCopy instanceof InHouse) {
            newPart = new InHouse((InHouse) partToCopy);
        } else if (partToCopy instanceof Outsourced) {
            newPart = new Outsourced((Outsourced) partToCopy);
        }
        return newPart;
    }


}










