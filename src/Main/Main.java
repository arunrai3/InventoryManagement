package Main;

import Product.Product;
import Part.Part;
import controllers.delete_error_controller;
import controllers.parts_controller;
import controllers.product_controller;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
/**
 * 
 *This class is where the program starts by loading the first FXML file, and contains methods that handle loading different FXML files.
 *<p>
 *Main_form_controller:
 *<p>
 *FUTURE ENHANCEMENT: Something that can be added to this class is in the products table it can have an extra column that displays number of parts associated with each product. This can let users know if a part can be deleted before attempting and running into a pop up that says "product has parts". Also makes it
 *easier to visualize data without clicking on each product to see if it has parts.
 *<p>
 *delete_error_controller:
 *<p>
 *FUTURE ENHANCEMENT: If this class was updated I would have the pop-up display more information. For example if I was deleting the part "Seat", there would be a row displayed in the pop-up that gives all the information on "Seat". Sometimes the user can forget what object they are deleting and this display
 *the data clearly.
 *<p>
 *parts_controller:
 *<p>
 *FUTURE ENHANCEMENT: Another parameter could be added to this page such as type, this would make it easier to filter the parts table from main page and product page. Some types that a part could be added to include consumable, electronic, technology, and mechanical.
 *<p>
 *product_controller:
 *<p>
 *FUTURE ENHANCEMENT: When parts are added to the associated parts table the user can select how many parts they want to add(inventory), and this will fill in the inventory column for the associated parts table. It will also subtract how many parts were chosen for associated parts from the whole inventory parts table.
 *<p>
 *errorBoxes:
 *<p>
 *FUTURE ENHANCEMENT: An update that could be applied to error messages printed on the UI could be making text effects when the same error is printed twice in a row. For example if the Text field "name" does not accept integers but you enter "3" and click save. It will print "Name must a string", if you replace it with "4" and click
 *save the text that is displayed will flash three time notifying you that you repeated the same mistake. Instead of you the user thinking its an old error that wont go away.
 *<p>
 *inputcheck:
 *<p>
 *FUTURE ENHANCEMENT: On the products page the input check could also verify if the associated parts inventory isn't greater than the inventory in all parts table. It could also check the price of the product and make sure its greater than the price of the associated parts combined. Updates on checking the inventories
 *and prices similar to the ones I mentioned could all be applied.
 *<p>
 *searching:
 *<p>
 *FUTURE ENHANCEMENT: A good update for this class is to be able to search by different filters other than part ID and name. There could be a drop down next to search bar and you can select what you want to search by for example, inventory,price, etc. and you could select "&gt;" or "&lt;" if filtering by numeric values. 
 *<p>
 *Inventory:
 *<p>
 *FUTURE ENHANCEMENT: A method can be to this class that returns the parts and/or products with low or max inventory. This can then be displayed in the table and notify the user of what stocks need to be replaced and what parts and products are not selling.
 *<p>
 *Main:
 *<p>
 *FUTURE ENHANCEMENT: An update to this class would include optimizing the code in handleSceneChange method, currently many lines of code are being analyzed that don't always need to be. If these lines need to be read they can be called by a different method. For example the code for loading a pop up can be in its own method called "loadPopup"
 *<p>
 *Part:
 *<p>
 *FUTURE ENHANCEMENT: Another method can be added called updateStock that can accept an integer which will be passed in when an associated part is added. This integer will be subtracted from current inventory to give updated inventory amount.
 *<p>
 *Product:
 *<p>
 *FUTURE ENHANCEMENT: A method called updateAssociatedpart can be added. For example there can be a "modify" button next to "remove". It will serve the same purpose as modify part for main page, but instead for associated parts. After user clicks "save" on the associated part, this method will be ran and it will update the part
 *instead of adding a new one or deleting an old one. 
 *<p>
 *Inhouse:
 *<p>
 *FUTURE ENHANCEMENT: If the part is in house than that means a specific department made it within the company. Their can be methods and variable to add this data. Such as "getDepartment" and "setDepartment" to retrieve and set the variables "Department"
 *<p>
 *Outsourced:
 *<p>
 *FUTURE ENHANCEMENT: Adding methods and variables that accepts what city the Company is in, for example "setCity", and "getCity" to retrieve and set the variables "City".
 *<p>
 * @author Arun Rai
 */

public class Main extends Application {
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * 
     * Loads and displays "main_form.fxml" file, first thing user sees.
     * <p>
     * LOGICAL ERROR:
     * <p>  
     * At first did not add line "stage.show();" which would load the program correctly but now show interface to the user.
     * @param stage
     * @throws IOException 
     * 
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_form.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     *
     * Method is called every time the user switches to a different UI and a new FXML file is loaded. The necessary parameters that need to be passed in for this method to function are fxml file and button, but it also accept objects such as parts/products and TableViews. This is because when a new
       page is loaded it might require some data from the previous page in order to function properly. The data that is being passed cant be hard coded in the fxml file or corresponding controller because some pages share the same FXML file.
     * <p>
     * RUNTIME ERROR:
     * <p>  
     * The first logical error I had with this code was trying to pass a controller file as an argument into this method. This is because I wanted to change a FXML variable that was in the controller file, this did not work because when I passed different controller files it caused errors. Instead
       I added the line "Label labelVariable = (Label)root.lookup("#" + labelId);" which find the controller file associated with the label I was tying to change without having to pass it in. The second logical error was not making this method public, which caused errors because the other classes that called this method could
       not find the method because it was private.
     * @param fxmlFile
     * @param text
     * @param labelId
     * @param button
     * @param part
     * @param product
     * @param popUp
     * @param table 
     */
    public static void handleSceneChange(String fxmlFile, String text, String labelId, Button button, Part part, Product product, String popUp, TableView<Part> table) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/" + fxmlFile));
            Parent root = loader.load();
            Object controller = loader.getController();
            if (labelId != "null" && text != "null"){
            Label labelVariable = (Label)root.lookup("#" + labelId);
            labelVariable.setText(text);
            }
            if (part != null) {
                if (text.equals("Modify Part")){
                 ((parts_controller)controller).loadPartData(part);
                }else if (text.equals("Delete")){
                 ((delete_error_controller)controller).setPartinPopUp(part);        
                }else if (text.equals("Remove")){
                 ((delete_error_controller)controller).setPartinPopUp(part);
                 ((delete_error_controller)controller).setTableinPopUp(table);           
                }
            }
            if (product != null) {
                if (text.equals("Modify Product")){
                    ((product_controller)controller).loadProductData(product);
                }else if (text.equals("Delete")){
                    ((delete_error_controller)controller).setProductinPopUp(product);        
                }
            }
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            if (popUp.equals("yes")) {
                stage.setOnCloseRequest(e -> ((delete_error_controller)controller).closeRequestHandler(e));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(button.getScene().getWindow());
                stage.setScene(scene);
                stage.showAndWait();
            } else {
                Stage originalStage = (Stage) button.getScene().getWindow();
                originalStage.setScene(scene);
                originalStage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }
    
}
    
