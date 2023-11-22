package controllers;

import utility.errorBoxes;
import Inventory.Inventory;
import Part.Part;
import Product.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This class handles all the actions that occur when a user interacts with the delete_error.fxml file.
 * @author Arun Rai
 */


public class delete_error_controller {

    
    @FXML
    private Button cancelButton;

    @FXML
    private Button okButton;

    @FXML
    private Label questionLabel;

    @FXML
    private Label titleLabel;
    
    private Part selectedPartFromMain;
    
    private TableView<Part> PopUpTable;
    
    private Product slectedProductFromMain;

    /**
     * Runs closeWindow method.
     * @param event 
     */
    @FXML
    private void clickedCancelbutton(ActionEvent event) {
        closeWindow();
    }
    
    /**
     * Runs closeWindow method.
     * @param event 
     */    
    @FXML
    public void closeRequestHandler(WindowEvent event) {
        closeWindow();
    }
    
    /**
     * The logic in this method is ran when the user confirms a delete/remove by clicking "OK". The program needs to identify what object the user wants to delete or remove, it does this by reading the text labels in the pop up, if word "part" is in text label 
     * than it knows what type of object to delete/remove. If it also has the word "remove" than it knows not to delete from whole inventory but only the associated parts table. 
     * <p>
     * LOGICAL ERROR:
     * <p> 
     * The logical error I had with this code was having "&lt;" 0 rather than "≤" in the conditional statement under "if(questionLabel.getText().contains("product")". This would cause the program to think even a product with zero parts had parts because the condition is only true for negative numbers. I had to
       include 0 to allow products to be deleted.
     * @param event 
     */
    @FXML
    private void clickedOkbutton(ActionEvent event) {
         if (questionLabel.getText().contains("part")){
            if (titleLabel.getText().equals("Delete")){
                Inventory.deletePart(selectedPartFromMain);
                errorBoxes.mainErrorboxes("parts","Part deleted");
            } else {
                PopUpTable.getItems().remove(selectedPartFromMain);
                errorBoxes.productErrorboxes("products","Part removed");
            }
         } else if(questionLabel.getText().contains("product")) {
            if(slectedProductFromMain.getAllAssociatedParts().size() <= 0){
                Inventory.deleteProduct(slectedProductFromMain);
                errorBoxes.mainErrorboxes("products","Product deleted");
            }else{
                errorBoxes.mainErrorboxes("product","Product has parts. Please delete associated parts first.");
            }
         }
         Stage stage = (Stage) okButton.getScene().getWindow();
         stage.close();
    }
    
    /**
     * The logic in this method ran when the user cancels a delete/remove and clicks "cancel" or the "X" in top right. The program needs to identify what object the user canceled on, it does this by reading the text labels in the pop up, if word "part" is in text
     * label than it knows what type of object the user canceled and can print the appropiate message to UI. If it also has the word "remove" than it knows to include the "not removed" in message to UI instead of "not deleted".
     * <p>
     * LOGICAL ERROR:
     * <p>   
     * Before I added the conditional if-else branch under "questionLabel.getText().contains("part")" the program would print "Part not deleted" only for the main menu and not for the products page if a part was removed. I solved this by adding an if-else branch for whether a part should be deleted/removed and from which page
       by reading the titleLabel of the Pop up. If titleLabel equals Delete, then I know we are on main page, because only main page can delete parts, else we are on products page.  
     */
    private void closeWindow(){
        if (questionLabel.getText().contains("part")){
            if (titleLabel.getText().equals("Delete")){
                errorBoxes.mainErrorboxes("parts","Part not deleted");
            } else {
                errorBoxes.productErrorboxes("product","Part not removed");
            }
        } else if(questionLabel.getText().contains("product")) {
            errorBoxes.mainErrorboxes("product","Product not deleted");
        }
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
    
    /**
     * This method is called when user is deleting a part from the main page, it will change the text on the pop up to "Do you want to delete this part?" and store the part that the user selected from the main page so when the user clicks "OK" to confirm the delete the program knows which part to delete.
     * @param selectedPart 
     */
    public void setPartinPopUp(Part selectedPart){
        questionLabel.setText("Do you want to delete this part?");
        selectedPartFromMain = selectedPart;       
    }
    
    /**
     * This method is called when user is removing a part from the product page, it will change the text on the pop up to "Do you want to remove this part?" and stores the table from the product page so when the user clicks "OK" to confirm the delete the program knows which part table to apply the changes too.
     * @param table 
     */
    public void setTableinPopUp(TableView<Part> table) {        
        PopUpTable = table;
        questionLabel.setText("Do you want to remove this part?");  
    }
    
    /**
     * This method is called when user is deleting a product from the main page, it will change the text on the pop up to "Do you want to delete this product?" and store the product that the user selected from the main page so when the user clicks "OK" to confirm the delete the program knows which product to delete.
     * @param selectedProduct 
     */
    public void setProductinPopUp(Product selectedProduct) {
        slectedProductFromMain = selectedProduct;
        questionLabel.setText("Do you want to delete this product?");  
    }        
}    
    