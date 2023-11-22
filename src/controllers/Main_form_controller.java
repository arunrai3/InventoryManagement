package controllers;

import utility.searching;
import utility.errorBoxes;
import Main.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import Product.Product;
import Part.Part;
import javafx.stage.Stage;
import Inventory.Inventory;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * This class handles all the actions that occur when a user interacts with the main_form.fxml file.
 * @author Arun Rai
 */


public class Main_form_controller implements Initializable {

    @FXML
    private Button addPartButton;
    
    @FXML
    private Button addProductButton;

    @FXML
    private Button deletePartbutton;

    @FXML
    private Button deleteProductbutton;

    @FXML
    private TableColumn<Product, Integer> inventoryLevelinProduct;

    @FXML
    private TableColumn<Part, Integer> inventorylevelinParts;

    @FXML
    private Button mainExit;

    @FXML
    private Button modifyPartbutton;

    @FXML
    private Button modifyProductbutton;

    @FXML
    private TableColumn<Part, Integer> partIDinParts;

    @FXML
    private TableColumn<Part, String> partNameinParts;

    @FXML
    private TableView<Part> partTable;

    @FXML
    private TableColumn<Part, Double> price_costinParts;

    @FXML
    private TableColumn<Product, Double> price_costinproduct;

    @FXML
    private TableColumn<Product, Integer> productIDinproduct;

    @FXML
    private TableColumn<Product, String> productNameinProduct;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TextField searchPartBar;

    @FXML
    private TextField searchProductBar;
    
    @FXML
    private Label partsError;
    
    @FXML
    private Label productError;
    
    private boolean isErrorLabelsSet = false;
    
    /**
     * Loads data from list of parts and products in inventory, sets labels that will be used for errors in errorBoxes class.
     * <p>
     * LOGICAL ERROR:
     * <p>
     * One of the logical errors that this code was facing is that "errorBoxes.setHomeLabels(partsError,productError);"
       was being ran every time the initialize method was ran, this was unnecessary and taking up computer power for no reason, instead I added a conditional statement.
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        if (!isErrorLabelsSet) {
            errorBoxes.setHomeLabels(partsError,productError);
            isErrorLabelsSet = true;
        }   
        ObservableList<Part> parts = Inventory.getAllParts();
        ObservableList<Product> products = Inventory.getAllProducts();
        partTable.setItems(parts);
        partIDinParts.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameinParts.setCellValueFactory(new PropertyValueFactory<>("name"));
        price_costinParts.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventorylevelinParts.setCellValueFactory(new PropertyValueFactory<>("stock"));
        
        productsTable.setItems(products);
        productNameinProduct.setCellValueFactory(new PropertyValueFactory<>("name"));
        price_costinproduct.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryLevelinProduct.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productIDinproduct.setCellValueFactory(new PropertyValueFactory<>("id"));
        
    }
    
    /**
     * If Add button is clicked in parts pane it will load the parts.fxml file with no data in the text boxes.
     * <p>
     * LOGICAL ERROR:
     * <p>     
     * This method was giving me problems when I first added it because I am sharing one FXML file for two different buttons "Add" and "Modify", that open two different pages. The
       difference in the pages was minor, the title. so I added two parameters to the method that loads scenes to accept the label and text you want in the label
       so the loader method knows what to edit.
     */
    @FXML
    private void handleAddPartButtonAction() {
        Main.handleSceneChange("parts.fxml", "Add Part", "partLabel", addPartButton,null,null, "no", null);
    }   
    
    /**
     * If Add button is clicked in products pane it will load the products.fxml file with no data in the text boxes or associated parts table.
     * <p>
     * LOGICAL ERROR:
     * <p>
     * In this method I noticed that I was repeating the same code as the add part method to load this scene, the only things that changed was the file name. In order to make the code more efficient I added a method to "main" which loaded FXML files but you could pass any file you want.
     */
    @FXML
    private void handleAddProductButtonAction() {
        Main.handleSceneChange("products.fxml", "Add Product", "productLabel", addProductButton, null, null, "no", null);
    }
    
    /**
     * Checks if part is selected in parts table on main page. If true, loads parts.fxml with data filled in the text boxes, else it will notify user to select a part to modify.
     * <p>
     * LOGICAL ERROR:
     * <p>
     * This method is similar to "handleAddProductButtonAction" and "handleAddPartButtonAction" the only difference is making sure that a part is selected in order to load the modify part menu. At first the user would be able to click Modify part and it would load without any data. In order to fix this I added
       a conditional statement that verifies if a row in the part table is selected. If not it will indicate to the user to select a part.
     */
    @FXML
    private void handleModifyPartButtonAction() {
        if (partTable.getSelectionModel().getSelectedItem() != null) {
            Part selectedPart = partTable.getSelectionModel().getSelectedItem();
            Main.handleSceneChange("parts.fxml", "Modify Part","partLabel", modifyPartbutton, selectedPart, null, "no",null);
        } else {
            errorBoxes.mainErrorboxes("parts","Please select a part to modify.");
        }
    }  
    
    /**
     * Checks if product is selected in product table on main page. If true, loads products.fxml with data filled in the text boxes and associated parts table, else it will notify user to select a product to modify.
     * <p>
     * LOGICAL ERROR:
     * <p>
     * This error is a second part to "handleModifyPartButtonAction", in "handleModifyPartButtonAction" I sent an additional parameter to the method "handleSceneChange" so the scene loader would know what part to load. In this modify I was attempting to pass a product but the method did not accept that object, instead
       I added an extra parameter that accepted products and left part null. Leaving it null also allowed "handleSceneChange" to examine if part or product was null which indicated which page to load.
     */
    @FXML
    private void handleModifyProductButtonAction() {
        if (productsTable.getSelectionModel().getSelectedItem() != null) {
            Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
            Main.handleSceneChange("products.fxml", "Modify Product", "productLabel", modifyProductbutton,null, selectedProduct, "no", null);
        } else {
            errorBoxes.mainErrorboxes("productside","Please select a product to modify.");
        }
    }    
    
    /**
     * If exit button clicked on main page it will close program.
     */
    @FXML
    private void handleExitPartButtonAction() {
        ((Stage) mainExit.getScene().getWindow()).close();
    }
    
    /**
     * Checks if part is selected in parts table on main page. If true, loads a delete pop up that will verify if user wants to delete part, else it will notify user to select a part to delete.
     * <p>
     * LOGICAL ERROR:
     * <p>
     * One logical error in this code was that when a user clicked delete while a part was selected, it would automatically delete the part without waiting for the user to click anything in the delete UI pop up. To prevent this I removed the line "Inventory.deletePart(selectedPart);" and instead sent selectedPart to 
       the handleSceneChange method which sent it to the controller of the delete pop up.
     */
    @FXML
    private void handleDeletePartButtonAction() {
        if (partTable.getSelectionModel().getSelectedItem() != null) {
           errorBoxes.mainErrorboxes("productside"," ");
           Part selectedPart = partTable.getSelectionModel().getSelectedItem();
           Main.handleSceneChange("delete_error.fxml", "Delete", "titleLabel", deletePartbutton, selectedPart, null,"yes", null);
        }else{
           errorBoxes.mainErrorboxes("parts","Please select a part to delete.");
        }
    }

    /**
     * Checks if product is selected in product table on main page. If true, loads a delete pop up that will verify is user wants to delete product, else it will notify user to select a product to delete.
     * <p>
     * LOGICAL ERROR:
     * <p>
     * This error was also applicable to "handleDeletePartButtonAction". The error was that after a part/product was selected and the user clicked delete, the main page would disappear and only the pop up would appear. I feel like this would of confused the user because maybe they forgot what they selected. To fix this I added
       a simple string where the developer can type "yes" or "no" if they want the loaded scene to be a pop up or not. The string will be used as a conditional statement in the handleSceneChange method to indicated how the stage should be loaded.
     */
    @FXML
    private void deleteProductbuttonAction() {
        if (productsTable.getSelectionModel().getSelectedItem() != null) {
           errorBoxes.mainErrorboxes("productside"," ");
           Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
           Main.handleSceneChange("delete_error.fxml", "Delete", "titleLabel", deletePartbutton, null, selectedProduct,"yes", null);
        }else{
           errorBoxes.mainErrorboxes("productside","Please select a product to delete.");
        }
    }
    
    /**
     * Search box above parts table, it will search for parts in whole inventory, by partial/full name or ID number. 
     * <p>
     * RUNTIME ERROR:
     * <p>
     * The error for this method also applies to the method "handleSearchProductAction". The specific logic for how the search bar find and filters parts/products will be explained in the "searching" and "inputcheck" class. Their are two search bars in the main page and one search bar in the modify product page,
       at first the logic for filtering and searching was so long that it made the code cause errors and was slowing down the run time. To prevent this, I created two classes "inputcheck" and "searching" that could be called if a search was required instead of repeating code.
     */
    @FXML
    private void handleSearchPartAction() {
        searching search = new searching();
        search.searchInTable(errorBoxes::mainErrorboxes,partTable,searchPartBar, "parts");
    }
    
    /**
     * Search box above products table, it will search for products in whole inventory, by partial/full name or ID number.
     */
    @FXML
    private void handleSearchProductAction() {
        searching search = new searching();
        search.searchInTable(errorBoxes::mainErrorboxes,productsTable,searchProductBar, "product");
    }
    
}