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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.lookupPart;


class MPDException extends Exception {

    String s;


    MPDException(String s) {
        this.s = s;

    }

    public String toString() {
        return s;
    }
}

/**
 * This class controls the modify product fxml file.
 */
public class ModifyProductController implements Initializable {


    Stage stage;
    Parent scene;

    private Product currentProduct;
    private final ObservableList<Part> associatedPartTableViewHolder = FXCollections.observableArrayList();


    @FXML
    private Button addProduct;

    @FXML
    private TableColumn<Part, String> allInventoryLevelPart;

    @FXML
    private TableColumn<Part, String> allInventoryLevelPart1;

    @FXML
    private TableColumn<Part, String> allPartIdColumn;

    @FXML
    private TableColumn<Part, String> allPartIdColumn1;

    @FXML
    private TableColumn<Part, String> allPartName;

    @FXML
    private TableColumn<Part, String> allPartName1;

    @FXML
    private TableColumn<Part, String> allPriceCostPerUnitPart;

    @FXML
    private TableColumn<Part, String> allPriceCostPerUnitPart1;

    @FXML
    private Button cancelModifyProduct;

    @FXML
    private TextField idModifyProduct;

    @FXML
    private TextField invModifyProduct;

    @FXML
    private TextField maxModifyProduct;

    @FXML
    private TextField minModifyProduct;

    @FXML
    private Button modifyProductSave;

    @FXML
    private TextField nameModifyProduct;

    @FXML
    private TextField partSearch;

    @FXML
    private TableView<Part> partTable;

    @FXML
    private TableView<Part> partTable1;

    @FXML
    private TextField priceModifyProduct;

    @FXML
    private Button removeModifyProduct;


    @FXML
    void onAddProduct(ActionEvent event) {
        Part selectedAssociatedPart;
        selectedAssociatedPart = partTable.getSelectionModel().getSelectedItem();
        associatedPartTableViewHolder.add(selectedAssociatedPart);

        partTable1.setItems(associatedPartTableViewHolder);
    }

    @FXML
    void onIdModifyProduct(ActionEvent event) {

    }

    @FXML
    void onInvModifyProduct(ActionEvent event) {

    }

    @FXML
    void onMaxModifyProduct(ActionEvent event) {

    }

    @FXML
    void onMinModifyProduct(ActionEvent event) {

    }

    /**
     * This method arranges input data and saves to filesystem
     *
     * @param event mousclick from main menu
     * @throws IOException catches events of data type errors
     */
    @FXML
    public void onModifyProductSave(ActionEvent event) throws IOException {
        try {


            if (nameModifyProduct.getText().matches("^[1-9][0-9]*$") || nameModifyProduct.getText() == "") {
                throw new MPDException("Use letters for name");
            }


            if (invModifyProduct.getText().matches("[a-zA-Z]+") || invModifyProduct.getText() == "") {
                throw new MPDException("Use numbers Inv");
            }
            double stock = Double.parseDouble(invModifyProduct.getText());

            if (priceModifyProduct.getText().matches("[a-zA-Z]+") || priceModifyProduct.getText() == "") {
                throw new MPDException("Use numbers for Price");
            }


            if (minModifyProduct.getText().matches("[a-zA-Z]+") || minModifyProduct.getText() == "") {
                throw new MPDException("Use numbers for Min");

            }
            int min = Integer.parseInt(minModifyProduct.getText());

            if (maxModifyProduct.getText().matches("[a-zA-Z]+") || maxModifyProduct.getText() == "" || !maxModifyProduct.getText().matches("^[1-9][0-9]*$")) {
                throw new MPDException("Use numbers for Max");

            }
            int max = Integer.parseInt(maxModifyProduct.getText());

            if (max < min) {
                throw new MPDException("Max less than Min");
            }

            if (min > max) {
                throw new MPDException("Min greater than Max");
            }
            if (min > max) {
                throw new MPDException("Min greater than Max");
            }


            if (!(min < stock) || !(stock < max)) {
                throw new MPDException("Inv outside of min / max range");
            }


            Product modifiedProduct = new Product(
                    Integer.parseInt(idModifyProduct.getText()),
                    nameModifyProduct.getText(),
                    Double.parseDouble(priceModifyProduct.getText()),
                    Integer.parseInt(invModifyProduct.getText()),
                    Integer.parseInt(minModifyProduct.getText()),
                    Integer.parseInt(maxModifyProduct.getText()));


            Inventory.updateProduct(modifiedProduct.getId(), modifiedProduct);


            //calls on a temporaryHolder of associated views to add and uses an enhanced for loop to add the contents to the current product
            modifiedProduct.getAllAssociatedParts().removeAll();
            for (Part part : associatedPartTableViewHolder) {
                modifiedProduct.addAssociatedPart(part);
            }


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will save changes to product, do you want to continue?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        } catch (
                MPDException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText(ex.toString());
            alert.showAndWait();
        }
    }


    @FXML
    void onNameModifyProduct(ActionEvent event) {

    }

    @FXML
    void onPartSearch(ActionEvent event) {
        String searchInput = partSearch.getText();
        if (partSearch.getText() == null) {
            return;
        }

        ObservableList<Part> foundParts = lookupPart(searchInput);
        partTable.setItems(foundParts);

        //shows alert message if searchInput produced 0 results.
        if (partTable.getItems().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setTitle("Item not found");
            alert.setHeaderText("Search produced no results.");
            alert.setContentText("\"" + searchInput + "\"" + " found no results.");
            alert.showAndWait();
        }
        partSearch.setText("");

    }


    @FXML
    void onPriceModifyProduct(ActionEvent event) {

    }

    @FXML
    void onRemoveModifyProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Associated Parts");
        alert.setHeaderText("Remove");
        alert.setContentText("Do you want to remove this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            String name = partTable1.getSelectionModel().getSelectedItem().getName();
            associatedPartTableViewHolder.remove(partTable1.getSelectionModel().getSelectedItem());

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Associated Parts");
            alert.setHeaderText("Remove");
            alert.setContentText("Part " + name + " removed.");
            result = alert.showAndWait();

            partTable1.setItems(associatedPartTableViewHolder);
        }
    }

    /**
     * This method deals with the cancel button
     *
     * @param event mousclick on cancel activates method
     * @throws IOException handles any bad data types and errors
     */
    @FXML
    void onCancelModifyProduct(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all test field values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    public void sendProduct(Product product) {
        idModifyProduct.setText(String.valueOf(product.getId()));
        nameModifyProduct.setText(product.getName());
        invModifyProduct.setText(String.valueOf(product.getStock()));
        priceModifyProduct.setText(String.valueOf(product.getPrice()));
        maxModifyProduct.setText(String.valueOf(product.getMax()));
        minModifyProduct.setText(String.valueOf(product.getMin()));
        partTable1.setItems(product.getAllAssociatedParts());


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        idModifyProduct.setText("Auto-generated");
        idModifyProduct.setEditable(false);
        idModifyProduct.setDisable(true);


        allPartIdColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("id"));
        allPartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        allInventoryLevelPart.setCellValueFactory(new PropertyValueFactory<Part, String>("stock"));
        allPriceCostPerUnitPart.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));

        allPartIdColumn1.setCellValueFactory(new PropertyValueFactory<Part, String>("id"));
        allPartName1.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        allInventoryLevelPart1.setCellValueFactory(new PropertyValueFactory<Part, String>("stock"));
        allPriceCostPerUnitPart1.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));

        String searchInput = "";
        ObservableList<Part> foundParts = lookupPart(searchInput);
        partTable.setItems(foundParts);
    }

}
