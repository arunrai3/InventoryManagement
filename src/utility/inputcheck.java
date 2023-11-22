package utility;

import Outsourced_Inhouse.Inhouse;
import Inventory.Inventory;
import Outsourced_Inhouse.Outsourced;
import Part.Part;
import Product.Product;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * This class verifies that the data a user entered is valid, or generates errors notifying whats wrong.
 * @author Arun Rai
 */


public class inputcheck {
    private static errorBoxes checkingboxes = new errorBoxes();
    private static Label[] errorfields;
    
    /**
     * Sets the array of labels where errors will be printed for "parts.fxml" or "products.fxml".
     * @param errorFields 
     */
    public static void setErrorFields(Label[] errorFields) {
        errorfields = errorFields;
    }
     
    /**
     * This method accept all of the text boxes with data the user filled in to be examined. The value in each text box will be passed to a method in this class where it will return its original value or a 0/-1 to notify the program that the data for this text box is invalid. After this a conditional statement
       will be evaluated in which all the values in the text boxes have to be greater than the values that are returned if false, such as 0 or -1. If true than this means all the data inputted is correct and the method will create the desired object (part/product) using the input values and return it back to where the method was called. If
       not true the method will return a null part/product and this lets the program where the method was called from know that the input data was incorrect.
     * <p>
     * LOGICAL ERROR:
     * <p>  
     * I will list two errors that apply to this method, one on a macro point of view of the whole program. And one that is involved within the logic of this method. The macro error was that this code was being repeated in two controller files, parts_controller and product_controller. When a user click "save"
       for either of these classes they both attempt to validate very similar data. Instead of copy and pasting the same code twice in those two different methods, I decided to create a new class to handle that logic. Within this method there was an error where the program would "checkNumber" for machine_id after the other
       parameters came back correct, this would still save the correct data but the error was that it did not indicate to the user if their was an error on machine_id because if it had an error on the conditional statement before it that code would never get executed. To prevent this I put this code before the conditional statement.
     * @param idfield
     * @param namefield
     * @param pricefield
     * @param inventoryfield
     * @param minfield
     * @param maxfield
     * @param partonly
     * @param Inhouse
     * @param Outsourced
     * @param type
     * @param label
     * @return null part/product if invalid data was entered, else it will return a product/part with the data the user entered.
     */
    public static Object validatePart(TextField idfield,TextField namefield, TextField pricefield, TextField inventoryfield, TextField minfield, TextField maxfield, TextField partonly, RadioButton Inhouse, RadioButton Outsourced, String type, Label label) {
        Product product = null;
        Part part = null;
        checkingboxes.checkForEmptyErrorBox("reset",errorfields);
        boolean invbetweenminmax = false;
        int id = returnID(label, idfield, type);
        double price = 0.00;
        int machine_id = -1;
        int min = -1;
        int max = 0;
        int stock = 0;
        String name = "false";
        String companyName = "false";
        name = checkString(namefield, name, "Name");
        price = checkNumber(pricefield, price, "Price");
        stock = checkNumber(inventoryfield,stock, "Inventory");
        min = checkNumber(minfield,min, "Min");
        max = checkNumber(maxfield,max, "Max");
        invbetweenminmax = checkInvBetweenMaxMin(min, max, stock);
        if (type.equals("part")){
            if (Inhouse.isSelected()) {
                machine_id = checkNumber(partonly, machine_id, "Machine ID");
            }
            else if (Outsourced.isSelected()){
                companyName = checkString(partonly, companyName, "Company Name");            
            }
            if (min >= 0 && machine_id >= 0 && stock > 0 && price > 0.00 && max > 0 && !name.equals("false1") && Inhouse.isSelected() && invbetweenminmax != false){
                part = new Inhouse(id, name, price, stock, min, max, machine_id);
            } else if (min >= 0 && !companyName.equals("false1") && stock > 0 && price > 0.00 && max > 0 && !name.equals("false1") && Outsourced.isSelected() && invbetweenminmax != false){
                part = new Outsourced(id, name, price, stock, min, max, companyName);
            }
            return part;
        } else {
            if (min >= 0 && stock > 0 && price > 0.00 && max > 0 && !name.equals("false1") && invbetweenminmax != false){
                product = new Product(id, name, price, stock, min, max);
            }
            return product;    
        }        
    }

    /**
     * Method accepts a TextField, a string variable, and a string of text to be outputted to screen if data is incorrect. First stores the data in the Text field as a string, than examines the string in a conditional statement checking that the string has at least 1 letter, it can also contain spaces
       but it is required to have at least 1 letter. If true, it will return the string as it was inputted, else it will print an error and return "false" notifying program their is an error.
     * <p>
     * LOGICAL ERROR:
     * <p>  
     * I had an error with the regex statement on this method, I did not include a space in "[a-zA-Z ]" so if a user typed a space it would come back incorrect even though that was not my intention since that would make the logic for adding a name/companyName too constraining in my opinion.
     * @param text
     * @param varname
     * @param outputtext
     * @return the string entered in text box or false indicating string was not valid.
     */
    private static String checkString (TextField text, String varname, String outputtext){
        varname = text.getText();
        if(varname.matches("^(?=.*[a-zA-Z])[a-zA-Z ]*$")) {
            return varname;
        }   
        else {
           checkingboxes.checkForEmptyErrorBox(outputtext + " must contain at least one letter. Only letters and spaces are allowed.",errorfields);
           return "false1";
        }
    }

    /**
     * Accepts a Text field, a variable name, and output text if an error occurs. Checks if double or integer depending on where the method was called from. If data types does not match than an error is printed displaying data in text field is not integer/double. If data type is valid it will check if the number is valid(Some text fields cant have negative numbers or 0.)
       If initialized values are returned than data is invalid, else it will return the inputted data.
     * <p>
     * LOGICAL ERROR:
     * <p> 
     * When method was first created, I did not include a try-catch. At first I wanted to make sure that the number passed through was not negative but I did not account for a string being passed through which would crash the program every time. To prevent this I added a try-catch that would notify
       the user if an inappropriate data type was passed through.
     * @param <T>
     * @param text
     * @param varname
     * @param outputtext
     * @return The number entered in the specific text box or a 0/-1 indicating an invalid data type/number.
     */
    private static <T extends Number> T checkNumber(TextField text, T varname, String outputtext){
        String type = null;
        Number minimum = 0;
        if ((outputtext.equals("Min")) || (outputtext.equals("Machine ID"))) {
        minimum = -1;
        }
        try{
            if(varname instanceof Integer) {
                type = "an integer";
                varname = (T)Integer.valueOf(text.getText());
            } else if(varname instanceof Double) {
                type = "a double";
                varname = (T)Double.valueOf(text.getText());
            }
            if (varname.doubleValue() <= minimum.doubleValue()){
                if (minimum.doubleValue() == 0.00){
                checkingboxes.checkForEmptyErrorBox(outputtext + " has to be greater than 0",errorfields);
                }
                else if (minimum.doubleValue() == -1.00){
                checkingboxes.checkForEmptyErrorBox(outputtext + " has to be greater than or equal to 0",errorfields);    
                }
                return varname;
                }
        }catch (NumberFormatException e){
            checkingboxes.checkForEmptyErrorBox(outputtext + " is not " + type,errorfields);
            return varname;
        }
        return varname;
    }

    /**
     * Method accepts three integers min, max, and inv. It will first evaluate if the integers are in their initialized value states, if true this signals that the user did not enter valid integers into the Text fields and their is no need to examine further mistakes without correcting this one first. if the
       they are not in their initialized states than the method knows the data entered is valid and it can examine for further errors. If these errors are present it will print the error to screen and return false, else it will print nothing and return true to the program notifying that there were no errors.
     * <p>
     * LOGICAL ERROR:
     * <p>  
     * A logical error with this method was that I was checking if max was greater than min, inv greater than min, and inv less than max. Without knowing if max, min, and inv even had appropriate data. To prevent I added conditional statements that checked if the variables were greater
       than their initialized values which would prove valid data was passed, else their was no need to check further conditions.
     * @param min
     * @param max
     * @param inv
     * @return returns false if the min,max,inv are invalid data types or max less than min or inv not between min and max. Else, will return true.
     */    
    private static boolean checkInvBetweenMaxMin(int min, int max, int inv) {
        if (min >= 0 && max > 0){
          if (max > min){
            if (inv > 0){ 
               if (inv >=  min && inv <= max){
                return true;
               }
               checkingboxes.checkForEmptyErrorBox("Inventory has to be greater than or equal to Min and less than or equal to Max.",errorfields);
               return false;
            }
            return false;
          }
          checkingboxes.checkForEmptyErrorBox("Max has to be greater than min",errorfields);
          return false;    
        }
        return false;
    }

    /**
     * Method accepts a Label, text field, and string. First evaluates where the current page is an "Add" or "Modify" page, if the page is "Modify" the ID will be present in the ID text field and it will return that. if the page is "Add" a new ID is required so it will iterate through all the parts/products, store the highest ID, and add 1 to that number.
       This will be the unique ID returned.
     * <p>
     * LOGICAL ERROR:
     * <p>  
     * When I was generating a unique ID for adding a part, my first method was to count the list and add 1 which would give me a unique number that had not been used for. I found this would not work, because if their are 3 items with ID's 1,2,3. If row with ID 2 is deleted, and add a new part the method will count
       2 items in the list, than add 1 and the unique ID will be 3. But that is already in use so it will cause an error. Instead, I iterate through each ID, store the maximum. And then add 1 to that number. This will always give a unique number.
     * @param label
     * @param idfield
     * @param type
     * @return int the unique ID generated
     */
    private static int returnID(Label label, TextField idfield, String type){
       if (label.getText().contains("Add")){
            int maxID = 0;
            ObservableList<?> list; 
            if (type.equals("part")){
                list = Inventory.getAllParts();
            }else{
                list = Inventory.getAllProducts();
            }     
            for (Object object : list) {
                int currentID; 
                if (object instanceof Part) {
                    currentID = ((Part) object).getId();
                } else {
                    currentID = ((Product) object).getId();
                }
                if (currentID > maxID) {
                    maxID = currentID;
                }
            }
            return maxID + 1;
       } else if (label.getText().contains("Modify")){
          return Integer.parseInt(idfield.getPromptText());
       }
       return 0;
    }    
}
