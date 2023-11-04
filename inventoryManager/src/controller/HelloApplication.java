package controller;

import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is our main class, used to initialize the data input and start the app
 */
public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/MainScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1200, 500);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method inputs test data into app
     *
     * @param args passes the arguments to the method
     *             <p>
     *             RUNTIME ERROR LOCATION: ADDPARTCONTROLLER CLASS LINE 108
     *             <p>
     *             FUTURE ENHANCEMENT : I have discussed with course instructors on how to
     *             write clean looking code structures.  I was able to meet with an instructor who taught me how
     *             to create a clean looking file structure as seen in this project.  However, I know it will take time to
     *             get such a clean look throughout writing an entire program, and I would like to make that the future enchancement
     *             plan for this project.
     *
     *             JAVADOC FOLDER LOCATION ...... C482_009479098_Desktop / Javadoc
     *
     *             THANK YOU FOR REVIEWING MY FIRST JAVA PROJECT!
     */
    public static void main(String[] args) {

        Outsourced nail = new Outsourced(1, "nail", 15, 15, 10, 20, "WGU");
        Outsourced screw = new Outsourced(2, "screw", 15, 15, 10, 20, "WGU");

        InHouse bolt = new InHouse(1, "bolt", 15, 15, 10, 20, 1);
        InHouse hinge = new InHouse(2, "hinge", 15, 15, 10, 20, 2);
        Product car = new Product(1, "car", 15, 15, 10, 20);
        Product bus = new Product(2, "bus", 15, 15, 10, 20);
        Product airplane = new Product(3, "airplane", 15, 15, 10, 20);

        Inventory.addPart(nail);
        Inventory.addPart(bolt);
        Inventory.addProduct(car);
        Inventory.addProduct(bus);
        Inventory.addProduct(airplane);
        Inventory.addPart(hinge);
        Inventory.addPart(screw);


        launch();
    }
}