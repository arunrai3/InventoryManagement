package Inventory;
import Product.Product;
import Part.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class contains a list of objects generated by Part and Product classes as well as methods to retrieve and manipulate the list.
 * @author Arun Rai
 */

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();;
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();;
    
    
    
    /**
     * Adds part to list.
     * @param newPart 
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);    
    }
    
    /**
     * Adds product to list.
     * @param newProduct 
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
        
    }
    
    /**
     * Iterates through all part ID's in allParts and attempts to match the integer that was passed in.
     * <p>
     * RUNTIME ERROR:
     * <p> 
     * In the conditional statement I was accidentally using the ".equals()" operator instead of "==" because I thought if it worked on a string it would also work on integers.
     * @param partId
     * @return part with ID matching integer as one entered in search bar
     */
    public static Part lookupPart(int partId){
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;        
    }
    
    /**
     * Iterates through all product ID's in allProducts and attempts to match the integer that was passed in.
     * @param productId
     * @return product with ID matching integer as one entered in search bar
     */
    public static Product lookupProduct (int productId){
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;        
    }
    
    /**
     * Iterates through all part names in allParts and adds any part that contains the passed in string in the name to a list.
     * @param partName
     * @return list of parts with matching partial/full string
     */
    public static ObservableList<Part> lookupPart (String partName){
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
                foundParts.add(part);
            }
        }
        return foundParts;
    }
    
    /**
     * Iterates through all product names in allProducts and adds any product that contains the passed in string in the name to a list.
     * <p>
     * RUNTIME ERROR:
     * <p>
     * This method was initially set to private which caused unexpected errors when it was called from outside of this class. Solved by setting it to public
     * @param productName
     * @return list of products with matching partial/full string
     */
    public static ObservableList<Product> lookupProduct (String productName){
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }
    
    /**
     * Finds part with index that has same value as integer that was passed in, and replaces it with the part that was passed in.
     * <p>
     * RUNTIME ERROR:
     * <p> 
     * Initially this method accepted "Product" as a data type because I copied this method from "updateProduct". After the error occurred I changed the data type to "Part".
     * @param index
     * @param selectedPart 
     */
    public static void updatePart(int index, Part selectedPart){
        if (index >= 0 && index < allParts.size()) {
            allParts.set(index, selectedPart);
        }
    }
    
    /**
     * Finds product with index that has same value as integer that was passed in, and replaces it with the product that was passed in.
     * @param index
     * @param selectedProduct 
     */
    public static void updateProduct(int index, Product selectedProduct){
        if (index >= 0 && index < allProducts.size()) {
            allProducts.set(index, selectedProduct);
        }    
    }
    
    /**
     * Deletes part that was passed in from allParts list.
     * @param selectedPart
     * @return true if part deleted or false if part not found
     */
    public static boolean deletePart(Part selectedPart){
        if (allParts.remove(selectedPart)) {          
           return true;           
        } else {
           return false;
        }
    }    
    
    /**
     * Deletes product that was passed in from allProducts list.
     * @param selectedProduct
     * @return true if product deleted or false if product not found
     */
    public static boolean deleteProduct(Product selectedProduct){
        if (allProducts.remove(selectedProduct)) {          
           return true;           
        } else {
           return false;
        }
    }    
    
    /**
     * Returns a list of all parts.
     * @return All Parts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    /**
     * Returns a list of all products.
     * @return All products
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;        
    }
    
}