package utility;


import Inventory.Inventory;
import java.util.function.BiConsumer;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * This class handles finding matching parts and products by using the data entered by user in search bar. 
 * @author Arun Rai
 */


public class searching {
    /**
     * This method accepts the table to be searched on, the search bar, a string indicating what type of table, and a method that will print an error in the appropiate location. First it will check if the data in search bar is an integer, if true it will search that ID in inventory and if a non-null value
       is returned than method knows match was found it will highlight that row in table. If the data is not an integer, it will first check if empty and if true it will return the whole table unchanged with no highlights. Else it will try and match the string and filter to only display rows with matching partial or full
       string. If no string, or integers are matched, errors will be printed to screen.
     * <p>
     * LOGICAL ERROR:
     * <p>  
     * Logical error is on the lines where I call the method "errorBoxMethod", I call this method because I want to display an error to the user if they pass data that is not in the table. The problem was that I skipped these lines for when a match was found and their
       was no errors. This created a problem because the errors from previous mistakes were still present on the user interface. To prevent this, I called the "errorBoxMethod" when their was a match but instead of passing an error I passed a blank space to remove all errors from the UI.
     * @param errorBoxMethod
     * @param table
     * @param searchBar
     * @param type 
     */
    public static void searchInTable(BiConsumer<String, String> errorBoxMethod, TableView table, TextField searchBar, String type){
        table.getSelectionModel().clearSelection();
        String searchTerm = searchBar.getText();
        ObservableList<?> wholelist = type.equals("parts") ? Inventory.getAllParts() : Inventory.getAllProducts();
        try {
            int searchInt = Integer.parseInt(searchTerm);
            Object matchInt = type.equals("parts") ? Inventory.lookupPart(searchInt) : Inventory.lookupProduct(searchInt);
            if (matchInt != null) {
                int matchIndex = wholelist.indexOf(matchInt);
                table.setItems(wholelist);
                table.getSelectionModel().select(matchIndex);
                table.scrollTo(matchIndex);
                errorBoxMethod.accept(type," ");
            } else {
                table.setItems(wholelist);
                errorBoxMethod.accept(type,"No " + type + " found with integer " + searchInt);
            }
        } catch (NumberFormatException e) {
            if (searchTerm.isEmpty()) {
                table.setItems(wholelist);
                errorBoxMethod.accept(type," ");
            }else{
                ObservableList<?> matchString = type.equals("parts") ? Inventory.lookupPart(searchTerm) : Inventory.lookupProduct(searchTerm);
                if (!matchString.isEmpty()){
                    table.setItems(matchString);
                    errorBoxMethod.accept(type," ");
                } else{
                    table.setItems(wholelist);
                    errorBoxMethod.accept(type,"No " + type + " found with string " + searchTerm);
                }
            }   
        }    
    }
}
