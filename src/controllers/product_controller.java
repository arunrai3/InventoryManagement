package controllers;

import utility.searching;
import utility.errorBoxes;
import utility.inputcheck;
import Main.Main;
import Inventory.Inventory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import Part.Part;
import Product.Product;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * This class handles all the actions that occur when a user interacts with the products.fxml file.
 * @author Arun Rai
 */

public class product_controller {
 
    @FXML
    private Button addPartinProductbutton;

    @FXML
    private TableView<Part> allPartsTableinProduct;

    @FXML
    private TableColumn<Part, Integer> associatedInvLevel;

    @FXML
    private TableColumn<Part, Integer> associatedPartId;

    @FXML
    private TableColumn<Part, String> associatedPartName;

    @FXML
    private TableView<Part> associatedParts;

    @FXML
    private Button cancelProductbutton;

    @FXML
    private TableColumn<Part, Integer> invLevelallPartsProducts;

    @FXML
    private TableColumn<Part, Integer> partIdAllparts_inproduct;

    @FXML
    private TableColumn<Part, String> partNameallParts_inProduct;

    @FXML
    private TableColumn<Part, Double> price_cost_allPartsinProduct;

    @FXML
    private TableColumn<Part, Double> price_cost_associated;

    @FXML
    private TextField productIdTextbox;

    @FXML
    private TextField productInvtextbox;

    @FXML
    private TextField productMaxtextbox;

    @FXML
    private TextField productMintextbox;

    @FXML
    private TextField productNametextbox;

    @FXML
    private TextField productpriceTextbox;

    @FXML
    private Button removeAssociatedPartButton;

    @FXML
    private Button saveProductbutton;

    @FXML
    private TextField searchPartinProductBar;
    
    @FXML
    private Label productLabel;
    
    @FXML
    private Label errorLabel1;

    @FXML
    private Label errorLabel2;

    @FXML
    private Label errorLabel3;

    @FXML
    private Label errorLabel4;

    @FXML
    private Label errorLabel5;

    @FXML
    private Label errorLabel6;
    
    @FXML
    private Label searchpartErrorproduct;
    
    @FXML
    private Label searchApartErrorproduct;
    
    private int listindex;  
    
    private boolean isErrorLabelsSetP = false;
    
    private List<Part> temp_partss;
    
    private Product productt;
    
    /**
     * Conditional statement that sets the error boxes for this page, it is only ran once. Sets the all parts table to the same parts table on the home page.
     */
    @FXML
    private void initialize() {
        if (!isErrorLabelsSetP) {
            errorBoxes.setProductLabels(searchpartErrorproduct,searchApartErrorproduct);
            isErrorLabelsSetP = true;
        }           
        allPartsTableinProduct.setItems(Inventory.getAllParts());
        partIdAllparts_inproduct.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameallParts_inProduct.setCellValueFactory(new PropertyValueFactory<>("name"));
        price_cost_allPartsinProduct.setCellValueFactory(new PropertyValueFactory<>("price"));
        invLevelallPartsProducts.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
    
    /**
     * When user clicks "modify" on the product pane from the main page, this method is called and will fill in data about the product the user selected from the main page. It is not in the initialize method because the FXML file associated with this class sometimes needs to be loaded with no data, for example clicking
       "Add" from the product pane on the main page.
     * @param product 
     */
    public void loadProductData(Product product) {
        this.productt = product;
        ObservableList<Product> thelist = Inventory.getAllProducts();
        List<Part> temp_parts = new ArrayList<>(product.getAllAssociatedParts());
        this.temp_partss = temp_parts;
        listindex = thelist.indexOf(product);  
        productIdTextbox.setPromptText(Integer.toString(product.getId()));
        productNametextbox.setText(product.getName());
        productpriceTextbox.setText(Double.toString(product.getPrice()));
        productInvtextbox.setText(Integer.toString(product.getStock()));
        productMintextbox.setText(Integer.toString(product.getMin()));
        productMaxtextbox.setText(Integer.toString(product.getMax()));
        
        associatedParts.setItems(product.getAllAssociatedParts());
        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        price_cost_associated.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
    
    /**
     * When cancel button is clicked, program returns user to the main page and no data is saved.
     * @param event 
     */
    @FXML
    void cancelProduct(ActionEvent event) {
        if(productLabel.getText().equals("Modify Product")){
          List<Part> parts = new ArrayList<>(productt.getAllAssociatedParts());
          for(Part part : parts){
             productt.deleteAssociatedPart(part); 
          }
          for (Part part : temp_partss){
             productt.addAssociatedPart(part);
          }            
        }
        Main.handleSceneChange("main_form.fxml", "null", "null", cancelProductbutton,null,null, "no",null);
    }

    /**
     * When "Add" button is clicked, program will check if a part is selected from the "allPartsTableinProduct" table, if true it will add that part to the "associatedParts", else print an error to user telling them to select a part first.
     * <p>
     * LOGICAL ERROR:
     * <p>   
     * Not adding an else statement if the condition is not met. I do not want the user to be confused if he/she does not understand why a part is not being added. This error notifies them that a part is not selected. (Most of the other errors in this class have already been covered in parts_controller).
     * @param event 
     */
    @FXML
    void clickAddinProductWindow(ActionEvent event) {
        if (allPartsTableinProduct.getSelectionModel().getSelectedItem() != null) {
            errorBoxes.productErrorboxes("products"," ");
            Part selectedPart = allPartsTableinProduct.getSelectionModel().getSelectedItem();
            associatedParts.getItems().add(selectedPart);
            associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
            associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
            price_cost_associated.setCellValueFactory(new PropertyValueFactory<>("price"));
            associatedInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        } else {
            errorBoxes.productErrorboxes("parts","Please select a part to Add!");
        }
    }    
    
    /**
     * When "Remove" button is clicked, program will check if a part is selected from the "associatedParts" table, if true it will load the "delete_error.fxml" pop up and will verify if the user wants to remove the part. Else print an error to user telling them to select a part first.
     * <p>
     * LOGICAL ERROR:
     * <p>   
     * At first, when I made the pop up that verified if the user wanted to remove a part it had the word "delete" in the pop up because I was using the same FXML file that is used in the delete pop for the main menu. The user could get confused with this because they might think they are deleting a part for from the whole inventory
       but instead they are only removing from that product. To prevent this, I added a parameter that can be sent to handleSceneChange which will accept a string of what I want the title text of that pop up too be. This edits the FXML file for the appropiate scenario.
     * @param event 
     */
    @FXML
    void removeAssociatedPart(ActionEvent event) {
        if (associatedParts.getSelectionModel().getSelectedItem() != null) {
            errorBoxes.productErrorboxes("products"," ");
            Part selectedPart = associatedParts.getSelectionModel().getSelectedItem();
            Main.handleSceneChange("delete_error.fxml", "Remove", "titleLabel", removeAssociatedPartButton, selectedPart, null,"yes", associatedParts);            
        } else {
            errorBoxes.productErrorboxes("products","Please select a part to Remove!");
        }

    }

    /**
     * Search box above for "allPartsTableinProduct", it will search for parts in whole inventory, by partial/full name or ID number.
     */
    @FXML
    private void handleSearchPartActioninProduct(){
        searching search = new searching();
        search.searchInTable(errorBoxes::productErrorboxes, allPartsTableinProduct,searchPartinProductBar, "parts");
    }

    /**
     * Method is ran when "Save" button is clicked, it will create a class that accepts error boxes for where error messages will be placed if their invalid input data. This class also has a method that accepts all of the filled in text boxes and returns a product object that is not null if all of the input data is correct, or null if incorrect. After this a conditional statement is ran and it
       will check if product is not null, if true it will either add the valid product and its associatedParts, or update the product and its associated parts that were loaded during the products.fxml file loading process and return to user to home page. Else, it will print errors to the user and remain on current page.
     * @param event 
     */    
    @FXML
    void saveProduct(ActionEvent event) {
        errorBoxes.productErrorboxes("products"," ");
        inputcheck checking = new inputcheck();
        Label[] errorLabels = new Label[] {errorLabel1, errorLabel2, errorLabel3, errorLabel4, errorLabel5, errorLabel6};
        checking.setErrorFields(errorLabels);
        Product product = (Product)checking.validatePart(productIdTextbox,productNametextbox,productpriceTextbox,productInvtextbox,productMintextbox,productMaxtextbox,null,null,null,"product",productLabel);
        if (product != null){
            ObservableList<Part> parts = associatedParts.getItems();
            for (Part part : parts) {
                product.addAssociatedPart(part);
            }            
            if (productLabel.getText().equals("Add Product")){
               Inventory.addProduct(product);
            }else if (productLabel.getText().equals("Modify Product")){
               Inventory.updateProduct(listindex, product);
            }    
            Main.handleSceneChange("main_form.fxml", "null", "null", saveProductbutton,null,null, "no", null);
        }                
    }    
}


