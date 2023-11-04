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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

class MPException extends Exception {

    String s;


    MPException(String s) {
        this.s = s;

    }

    public String toString() {
        return s;
    }
}

/**
 * This class initializes modify part menu in app.
 */
public class ModifyPartController implements Initializable {

    private static int partIndexNumber;
    Stage stage;
    Parent scene;
    private boolean isPartInHouse;
    private int setId;


    @FXML
    private Button cancelModifyPart;

    @FXML
    private TextField idModifyPart;

    @FXML
    private RadioButton inHouseButtonModify;

    @FXML
    private TextField invModifyPart;

    @FXML
    private TextField machineIdModifyPart;

    @FXML
    private TextField maxModifyPart;

    @FXML
    private TextField minModifyPart;

    @FXML
    private TextField nameModifyPart;

    @FXML
    private RadioButton outsourceButtonModify;

    @FXML
    private TextField priceModifyPart;

    @FXML
    private Button saveModifyPart;

    @FXML
    private ToggleGroup tgroup;
    @FXML
    private Label partLabelModify;

    @FXML
    void onCancelModifyPart(ActionEvent event) throws IOException {

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
    void onInHouseButtonModify(ActionEvent event) {


        if (inHouseButtonModify.isSelected()) {
            partLabelModify.setText("Machine ID");
        }


    }

    @FXML
    void onOutsourceButtonModify(ActionEvent event) {

        if (outsourceButtonModify.isSelected()) {
            partLabelModify.setText("Company Name");
        }

    }

    /**
     * method for try catch block and scanning input before save
     *
     * @param event button click on save
     * @throws IOException catching bad inputs
     */
    @FXML
    public void onSaveModifyPart(ActionEvent event) throws IOException {


        try {


            int id = Integer.parseInt(idModifyPart.getText());

            if (nameModifyPart.getText().matches("^[1-9][0-9]*$") || nameModifyPart.getText() == "") {
                throw new BadInputException("Use letters for name");
            }
            String name = nameModifyPart.getText();

            if (invModifyPart.getText().matches("[a-zA-Z]+") || invModifyPart.getText() == "") {
                throw new BadInputException("Use numbers Inv");
            }
            int stock = Integer.parseInt(invModifyPart.getText());

            if (priceModifyPart.getText().matches("[a-zA-Z]+") || priceModifyPart.getText() == "") {
                throw new BadInputException("Use numbers for Cost)");
            }
            double price = Double.parseDouble(priceModifyPart.getText());

            if (minModifyPart.getText().matches("[a-zA-Z]+") || minModifyPart.getText() == "") {
                throw new BadInputException("Use numbers for Min");
            }
            int min = Integer.parseInt(minModifyPart.getText());

            if (maxModifyPart.getText().matches("[a-zA-Z]+") || maxModifyPart.getText() == "" || !maxModifyPart.getText().matches("^[1-9][0-9]*$")) {
                throw new BadInputException("Use numbers for Max");
            }
            int max = Integer.parseInt(maxModifyPart.getText());


            if (min > max) {
                throw new BadInputException("Min greater than Max");
            }
            if (max < min) {
                throw new BadInputException("Max less than Min");
            }
            if (!(min < stock) || !(stock < max)) {
                throw new BadInputException("Inv outside of min / max range");
            }


            if (machineIdModifyPart.getText() == "") {
                throw new BadInputException("complete entire form");
            }
            if (outsourceButtonModify.isSelected() && machineIdModifyPart.getText().matches("^[1-9][0-9]*$")) {
                throw new BadInputException("Use letters for Company name");
            }
            if (inHouseButtonModify.isSelected() && (machineIdModifyPart.getText().matches("[a-zA-Z]+"))) {
                throw new BadInputException("Use numbers for Machine ID");
            }

            if (machineIdModifyPart.getText().matches("[a-zA-Z]+.*") && outsourceButtonModify.isSelected()) {
                Part selectedPart1 = new Outsourced(
                        Integer.parseInt(idModifyPart.getText()),
                        nameModifyPart.getText(),
                        Double.parseDouble(priceModifyPart.getText()),
                        Integer.parseInt(invModifyPart.getText()),
                        Integer.parseInt(minModifyPart.getText()),
                        Integer.parseInt(maxModifyPart.getText()),
                        machineIdModifyPart.getText());
                Inventory.updatePart(selectedPart1.getId(), selectedPart1);
            }

            if ((inHouseButtonModify.isSelected()) && (machineIdModifyPart.getText().matches("^[1-9][0-9]*$"))) {
                Part selectedPart = new InHouse(
                        Integer.parseInt(idModifyPart.getText()),
                        nameModifyPart.getText(),
                        Double.parseDouble(priceModifyPart.getText()),
                        Integer.parseInt(invModifyPart.getText()),
                        Integer.parseInt(minModifyPart.getText()),
                        Integer.parseInt(maxModifyPart.getText()),
                        Integer.parseInt(machineIdModifyPart.getText()));


                Inventory.updatePart(selectedPart.getId(), selectedPart);
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

    /**
     * method used for listing part in boxes
     *
     * @param part part to be listed
     */
    public void sendPart(Part part) {
        if (part.getClass() == InHouse.class) {
            idModifyPart.setText(String.valueOf(part.getId()));
            nameModifyPart.setText(part.getName());
            invModifyPart.setText(String.valueOf(part.getStock()));
            priceModifyPart.setText(String.valueOf(part.getPrice()));
            maxModifyPart.setText(String.valueOf(part.getMax()));
            minModifyPart.setText(String.valueOf(part.getMin()));
            machineIdModifyPart.setText(Integer.toString(((InHouse) part).getMachineId()));
            tgroup.selectToggle(inHouseButtonModify);
            partLabelModify.setText("Machine ID");

        }
        if (part.getClass() == Outsourced.class) {
            idModifyPart.setText(String.valueOf(part.getId()));
            nameModifyPart.setText(part.getName());
            invModifyPart.setText(String.valueOf(part.getStock()));
            priceModifyPart.setText(String.valueOf(part.getPrice()));
            maxModifyPart.setText(String.valueOf(part.getMax()));
            minModifyPart.setText(String.valueOf(part.getMin()));
            machineIdModifyPart.setText(((Outsourced) part).getCompanyName());
            tgroup.selectToggle(outsourceButtonModify);
            partLabelModify.setText("Company Name");


        }

    }

    /**
     * method that provides part index number
     *
     * @return port index number identifier for part
     */
    public static int getPartIndexNumber() {
        return partIndexNumber;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idModifyPart.setText("Auto-generated");
        idModifyPart.setEditable(false);


        if (inHouseButtonModify.isSelected()) {
            partLabelModify.setText("Machine ID");
        } else partLabelModify.setText("Company Name");


    }


}



