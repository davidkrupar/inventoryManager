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

class APException extends Exception {

    String s;


    APException(String s) {
        this.s = s;

    }

    public String toString() {
        return s;
    }
}

/**
 * This class controls the add product fxml file.
 */
public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;
    final ObservableList<Part> associatedPartTableViewHolder = FXCollections.observableArrayList();
    private int productIndexNumber;


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
    private Button cancelAddProduct;

    @FXML
    private TextField idAddProduct;

    @FXML
    private TextField invAddProduct;

    @FXML
    private TextField maxAddProduct;

    @FXML
    private TextField minAddProduct;

    @FXML
    private TextField nameAddProduct;

    @FXML
    private TextField partSearch;

    @FXML
    private TableView<Part> partTableHighAddProduct;

    @FXML
    private TableView<Part> partTableLowAddProduct;

    @FXML
    private TextField priceAddProduct;

    @FXML
    private Button removeAddProduct;

    @FXML
    private Button saveAddProduct;
    private int setId;

    @FXML
    void onAddProduct(ActionEvent event) {
        Part selectedAssociatedPart;
        selectedAssociatedPart = partTableHighAddProduct.getSelectionModel().getSelectedItem();
        associatedPartTableViewHolder.add(selectedAssociatedPart);

        partTableLowAddProduct.setItems(associatedPartTableViewHolder);

    }

    @FXML
    void onCancelAddProduct(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all test field values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }


    @FXML
    void onPartSearch(ActionEvent event) {
        String searchInput = partSearch.getText();
        if (partSearch.getText() == null) {

            return;

        } else {

            searchInput = partSearch.getText();
            ObservableList<Part> foundParts = lookupPart(searchInput);
            partTableHighAddProduct.setItems(foundParts);
            //shows alert message if searchinput produced 0 results.
            if (partTableHighAddProduct.getItems().size() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setTitle("Part not found");
                alert.setHeaderText("Search produced no results.");
                alert.setContentText("\"" + partSearch.getText() + "\"" + " found no results.");
                alert.showAndWait();
            }
        }
        partSearch.setText("");

    }

    @FXML
    void onRemoveAddProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Associated Parts");
        alert.setHeaderText("Remove");
        alert.setContentText("Do you want to remove this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            String name = partTableLowAddProduct.getSelectionModel().getSelectedItem().getName();
            associatedPartTableViewHolder.remove(partTableLowAddProduct.getSelectionModel().getSelectedItem());

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Associated Parts");
            alert.setHeaderText("Remove");
            alert.setContentText("Part " + name + " removed.");
            result = alert.showAndWait();

            partTableLowAddProduct.setItems(associatedPartTableViewHolder);
        }

    }

    @FXML
    void onSaveAddProduct(ActionEvent event) throws IOException {

        try {


            if (nameAddProduct.getText().matches("^[1-9][0-9]*$") || nameAddProduct.getText() == "") {
                throw new APException("Use letters for name");
            }


            if (invAddProduct.getText().matches("[a-zA-Z]+") || invAddProduct.getText() == "") {
                throw new APException("Use numbers Inv");
            }


            if (priceAddProduct.getText().matches("[a-zA-Z]+") || priceAddProduct.getText() == "") {
                throw new APException("Use numbers for Price");
            }


            if (minAddProduct.getText().matches("[a-zA-Z]+") || minAddProduct.getText() == "") {
                throw new APException("Use numbers for Min");

            }


            if (maxAddProduct.getText().matches("[a-zA-Z]+") || maxAddProduct.getText() == "" || !maxAddProduct.getText().matches("^[1-9][0-9]*$")) {
                throw new APException("Use numbers for Max");

            }
            double stock = Double.parseDouble(invAddProduct.getText());
            int min = Integer.parseInt(minAddProduct.getText());
            int max = Integer.parseInt(maxAddProduct.getText());

            if (max < min) {
                throw new APException("Max less than Min");
            }

            if (min > max) {
                throw new APException("Min greater than Max");
            }


            if (!(min < stock) || !(stock < max)) {
                throw new APException("Inv outside of min / max range");
            }


            Product newProduct = new Product(
                    setId,
                    nameAddProduct.getText(),
                    Double.parseDouble(priceAddProduct.getText()),
                    Integer.parseInt(invAddProduct.getText()),
                    Integer.parseInt(minAddProduct.getText()),
                    Integer.parseInt(maxAddProduct.getText()));


            Inventory.addProduct(newProduct);


            //calls on a temporaryHolder of associated views to add and uses an enhanced for loop to add the contents to the current product
            newProduct.getAllAssociatedParts().removeAll();
            for (Part part : associatedPartTableViewHolder) {
                newProduct.addAssociatedPart(part);
            }


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will save product, do you want to continue?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (
                APException s) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText(s.toString());
            alert.showAndWait();
        }


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        idAddProduct.setText("Auto-Disabled");
        idAddProduct.setEditable(false);
        idAddProduct.setDisable(true);

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
        partTableHighAddProduct.setItems(foundParts);

    }
}
