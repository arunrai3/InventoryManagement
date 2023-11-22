package Product;

import Part.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * This class contains a list of parts as well variables that stores additional Product information such as name, price, etc.
 * @author Arun Rai
 */


public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    public Product (int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;         
    }
    
    /**
     * @return the id
     */        
    public int getId() {
        return id;
    }

    /**
     * LOGICAL ERROR:
     * <p>
     * Did not add "this." in front of "id" which would only save id in the method but not the whole class. When I would use "getId" it would return null. Solved by adding "this."
     * @param id the id to set
     */    
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */    
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */    
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */    
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */    
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * @return the stock
     */    
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */    
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */    
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */        
    public void setMin(int min) {
        this.min = min;
    }
    
    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */    
    public void setMax(int max) {
        this.max = max;
    }
    
    /**
     * 
     * @param part Adds a part to to list of associated parts for specific product
     */
    public void addAssociatedPart (Part part){
       associatedParts.add(part);     
    }
    
    /**
     * RUNTIME ERROR:
     * <p>
     * No curly brackets around the else branch. This cause unexpected error not only in this method but also "getAllAssociatedParts" because its below this method. Added curly brackets after this.
     * @param selectedAssociatedPart
     * @return returns true if part removed, else returns false if part not found.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if (associatedParts.remove(selectedAssociatedPart)) {  
            return true;           
        } else {
            return false;
        }
    }
    
    /**
     * 
     * @return returns a list of associated parts for a specific product.
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
    
    
    

