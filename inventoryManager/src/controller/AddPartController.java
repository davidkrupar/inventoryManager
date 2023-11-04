package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class initializes exceptions.
 */
class BadInputException extends Exception {

    String s;


    BadInputException(String s) {
        this.s = s;

    }

    public String toString() {
        return s;
    }
}

/**
 * This class controls the add part fxml file.
 */
public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;
    String name;


    @FXML
    private Button addPartCancel;

    @FXML
    private TextField addPartCost;

    @FXML
    private TextField addPartId;

    @FXML
    private TextField addPartInv;

    @FXML
    private TextField addPartMahineId;

    @FXML
    private TextField addPartMax;

    @FXML
    private TextField addPartMin;

    @FXML
    private TextField addPartName;

    @FXML
    private Button addPartSave;

    @FXML
    private RadioButton inHouseButton;

    @FXML
    private RadioButton outsourceButton;

    @FXML
    private ToggleGroup tgroup;

    @FXML
    private Label partLabel;
    private int setId;


    @FXML
    void hello(ActionEvent event) {

    }

    @FXML
    void onAddPartCancel(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all test field values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }


    }

    /**
     * method for saving part on add part screen
     *
     * @param event button click on save within add part menu
     * @throws IOException catches bad input values
     *                     RUNTIME ERROR = Received runtime error for invalid data inputs
     *                     error corrected by creating custom regex arguments in multiple if loops.
     *                     The current regex arguments do provide a solid safety net for runtime errors of invalid data types.
     *                     It was interesting to learn about how to use regex, it seems like a very powerful tool that I
     *                     learned about only during this project.  It was very exciting to learn of such a useful tool.
     */
    @FXML
    void onAddPartSave(ActionEvent event) throws IOException {

        try {
            int id = 1;

            if (addPartName.getText().matches("^[1-9][0-9]*$") || addPartName.getText() == "") {
                throw new BadInputException("Use letters for name");
            }
            String name = addPartName.getText();

            if (addPartInv.getText().matches("[a-zA-Z]+") || addPartInv.getText() == "") {
                throw new BadInputException("Use numbers Inv");
            }
            int stock = Integer.parseInt(addPartInv.getText());

            if (addPartCost.getText().matches("[a-zA-Z]+") || addPartCost.getText() == "") {
                throw new BadInputException("Use numbers for Cost)");
            }
            double price = Double.parseDouble(addPartCost.getText());

            if (addPartMin.getText().matches("[a-zA-Z]+") || addPartMin.getText() == "") {
                throw new BadInputException("Use numbers for Min");
            }
            int min = Integer.parseInt(addPartMin.getText());

            if (addPartMax.getText().matches("[a-zA-Z]+") || addPartMax.getText() == "" || !addPartMax.getText().matches("^[1-9][0-9]*$")) {
                throw new BadInputException("Use numbers for Max");
            }
            int max = Integer.parseInt(addPartMax.getText());


            if (min > max) {
                throw new BadInputException("Min greater than Max");
            }
            if (max < min) {
                throw new BadInputException("Max less than Min");
            }
            if (!(min < stock) || !(stock < max)) {
                throw new BadInputException("Inv outside of min / max range");
            }


            if ((inHouseButton.isSelected()) && (addPartMahineId.getText().matches("^[1-9][0-9]*$"))) {
                int machineID = Integer.parseInt(addPartMahineId.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
            }
            if ((inHouseButton.isSelected()) && (addPartMahineId.getText() == "")) {
                int machineID = 0000000;
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
            }

            if (outsourceButton.isSelected() && addPartMahineId.getText().matches("[a-zA-Z]+")) {
                String companyName = addPartMahineId.getText();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }
            if (outsourceButton.isSelected() && addPartMahineId.getText().matches("^[1-9][0-9]*$")) {
                throw new BadInputException("Use letters for Company name");
            }
            if (inHouseButton.isSelected() && (addPartMahineId.getText().matches("[a-zA-Z]+"))) {
                throw new BadInputException("Use numbers for Machine ID");
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will save part, do you want to continue?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }


        } catch (
                BadInputException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText(ex.toString());
            alert.showAndWait();
        }

    }


    @FXML
    void onInHouseButton(ActionEvent event) {
        if (inHouseButton.isSelected()) {
            partLabel.setText("Machine ID");
        }

        if (outsourceButton.isSelected()) {
            partLabel.setText("Company Name");
        }


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPartId.setEditable(false);
        addPartId.setText("Auto-Disabled");
        addPartId.setDisable(true);

        if (partLabel.hasProperties()) {
            partLabel.setText("Machine ID");
        }
    }
}
