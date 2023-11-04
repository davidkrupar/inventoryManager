package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.*;

/**
 * This class initializes main menu of app.
 */
public class MainScreenController implements Initializable {

    Stage stage;
    Parent scene;

    private final ObservableList<Part> displayParts = FXCollections.observableArrayList();
    private final ObservableList<Product> displayProducts = FXCollections.observableArrayList();


    @FXML
    private Button addPart;

    @FXML
    private Button addProduct;

    @FXML
    private TableColumn<Part, Integer> allInventoryLevelPart;

    @FXML
    private TableColumn<?, ?> allInventoryLevelProduct;

    @FXML
    private TableColumn<Part, Integer> allPartIdColumn;

    @FXML
    private TableColumn<Part, String> allPartName;

    @FXML
    private TableColumn<Part, Double> allPriceCostPerUnitPart;

    @FXML
    private TableColumn<?, ?> allPriceCostPerUnitProduct;

    @FXML
    private TableColumn<?, ?> allProductId;

    @FXML
    private TableColumn<?, ?> allProductName;

    @FXML
    private Button deletePart;

    @FXML
    private Button deleteProduct;

    @FXML
    private Button exitMain;

    @FXML
    private AnchorPane mainFormView;

    @FXML
    private Button modifyPart;

    @FXML
    private Button modifyProduct;

    @FXML
    private TextField partSearch;

    @FXML
    private TableView<Part> partTable;

    @FXML
    private TextField productSearch;

    @FXML
    private TableView<Product> productTable;

    //Click Add under part, takes to add part screen
    @FXML
    void onAddPart(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    //Click Add under product, takes to add product screen
    @FXML
    void onAddProduct(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    //Select part in pane, Delete button removes from pane
    @FXML
    void onDeletePart(ActionEvent event) {
        Part deletePart = partTable.getSelectionModel().getSelectedItem();


        if (deletePart == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Parts");
            alert.setHeaderText("Delete");
            alert.setContentText("Select Part from Part Table");
            Optional<ButtonType> result1 = alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Parts");
            alert.setHeaderText("Delete");
            alert.setContentText("Do you want to delete this part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                displayParts.remove(deletePart);
                Inventory.deletePart(deletePart);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Parts");
                alert.setHeaderText("Delete");
                alert.setContentText("Part " + deletePart.getName() + " deleted.");
                result = alert.showAndWait();
                partTable.setItems(Inventory.getAllParts());
            } else if (result.get() == ButtonType.CANCEL) {

            }


        }
    }

    //Select product in pane, Delete button removes from pane
    @FXML
    void onDeleteProduct(ActionEvent event) {
        Product deleteProduct = productTable.getSelectionModel().getSelectedItem();
        if (deleteProduct == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Products");
            alert.setHeaderText("Delete");
            alert.setContentText("Please select product to delete");
            Optional<ButtonType> result = alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Products");
            alert.setHeaderText("Delete");
            alert.setContentText("Do you want to delete this product?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (deleteProduct.getAllAssociatedParts().isEmpty()) {
                    displayProducts.remove(deleteProduct);
                    Inventory.deleteProduct(deleteProduct);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Products");
                    alert.setHeaderText("Delete");
                    alert.setContentText("Product " + deleteProduct.getName() + " deleted.");
                    result = alert.showAndWait();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Products");
                    alert.setContentText("This product has parts and can not be deleted.");
                    result = alert.showAndWait();
                }
            }
            productTable.setItems(Inventory.getAllProducts());


        }
    }

    // Click exit button, application closes
    @FXML
    void onExitMain(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will exit program, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    //Select part from pane, click modify under part, sends to modify part populated with info
    @FXML
    void onModifyPart(ActionEvent event) throws IOException {
        Part modifyPart = partTable.getSelectionModel().getSelectedItem();

        if (modifyPart == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Parts");
            alert.setHeaderText("Modify");
            alert.setContentText("Select Part from Part Table");
            Optional<ButtonType> result1 = alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            loader.load();

            ModifyPartController MPController = loader.getController();
            MPController.sendPart(partTable.getSelectionModel().getSelectedItem());


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }
    }

    //Select product from pane, click modify under product, sends to modify product populated with info
    @FXML
    void onModifyProduct(ActionEvent event) throws IOException {
        Product modifyProduct = productTable.getSelectionModel().getSelectedItem();
        if (modifyProduct == null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Please Select Product to Modify");
            alert.setHeaderText("Modify");
            alert.setContentText("Please Select Product to Modify");
            Optional<ButtonType> result2 = alert.showAndWait();
        } else {


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController ProdController = loader.getController();
            ProdController.sendProduct(productTable.getSelectionModel().getSelectedItem());


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }
    }

    @FXML
    void onPartSearch(ActionEvent event) {
        String searchInput = partSearch.getText();

        ObservableList<Part> foundParts = lookupPart(searchInput);
        partTable.setItems(foundParts);

        //shows alert message if searchinput produced 0 results.
        if (partTable.getItems().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setTitle("Part not found");
            alert.setHeaderText("Search produced no results.");
            alert.setContentText("\"" + searchInput + "\"" + " found no results.");
            alert.showAndWait();
        }
        partSearch.setText("");


    }


    @FXML
    void onProductSearch(ActionEvent event) {
        String searchInput = productSearch.getText();

        ObservableList<Product> foundProducts = lookupProduct(searchInput);
        productTable.setItems(foundProducts);

        //shows alert message if searchinput produced 0 results.
        if (productTable.getItems().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setTitle("Part not found");
            alert.setHeaderText("Search produced no results.");
            alert.setContentText("\"" + searchInput + "\"" + " found no results.");
            alert.showAndWait();
        }
        partSearch.setText("");


    }

    /**
     * This method provides boolean search based on int id
     *
     * @param id id of part
     * @return boolean based on search
     */
    public boolean search(int id) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == id)
                return true;

            System.out.println("Found");
        }
        return false;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        partTable.setItems(Inventory.getAllParts());


        allPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allInventoryLevelPart.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPriceCostPerUnitPart.setCellValueFactory(new PropertyValueFactory<>("price"));


        productTable.setItems(Inventory.getAllProducts());

        allProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        allProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allInventoryLevelProduct.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPriceCostPerUnitProduct.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

}


