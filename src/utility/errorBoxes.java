package utility;

import javafx.scene.control.Label;

/**
 * This class places errors for invalid data inputs and actions in the correct location on the UI.
 * @author Arun Rai
 */

public class errorBoxes {
   private static Label PartsMain;
   private static Label ProductsMain;
   private static Label PartsinProduct;
   private static Label AssociatedPartsinProduct;   
   
   /**
    * Sets the labels where errors will be printed for "main_form.fxml".
    * <p>
    * LOGICAL ERROR:
    * <p>   
    * The reason this method was added is not because of an error specific in this method, because the logic in this method is very simple. It was because of an error in this class not knowing what FXML label to insert the error string that was passed in into. I added a parameter in the "mainErrorboxes" to accept
      a Label to edit, but then I noticed that I was consistently just passing in 1 of 4 labels over and over again. So instead this method that let me set the variables of the FXML labels one time (when code is first loaded) that I wanted to edit and I could use them anywhere in the class without issues and getting confused on names for simplicity.
    * @param partslabel
    * @param productslabel 
    */
   public static void setHomeLabels(Label partslabel, Label productslabel) {
      errorBoxes.PartsMain = partslabel;
      errorBoxes.ProductsMain = productslabel;
    } 

   /**
    * Sets the labels where errors will be printed for "products.fxml".
    * <p>
    * LOGICAL ERROR:
    * <p>   
    * Tried to combine "setHomeLabels" and "setProductLabels" but did not work because I can not pass in four labels at once without conditional statements to check if some of the labels are null. This is because the FXML variables are private and can only be used in that class, so I made two different methods
      that accept two labels each since the original method was going to be have called twice anyways. One for "main_form_controller" class, and one for "product_controller" class.
    * @param allpartslabel
    * @param associatedpartslabel 
    */
   public static void setProductLabels(Label allpartslabel, Label associatedpartslabel) {
      errorBoxes.PartsinProduct = allpartslabel;
      errorBoxes.AssociatedPartsinProduct = associatedpartslabel;
    } 
    
   /**
    * Accepts a string called errorMessage, and an array of Labels. It then iterates through all of the labels and finds one that does not have the string "null". When found it will place the string errorMessage in the label and set the visibility to "true". If the string errorMessage is "reset" than
      it iterates through the array of labels setting their values to "null" and visibility to false.
    * <p>
    * LOGICAL ERROR:
    * <p>   
    * One of the errors with this logic was that after the user clicked "Save" the new errors would display below the old errors, this could confuse the user in thinking something is wrong when nothing is wrong(its just posting old errors). To prevent this I added a conditional statement that check the string of the error, if it equals "reset" this
      is an indication to clear all old errors, so that when the next errors are sent it will start from the top and prevent confusion.
    * @param errorMessage
    * @param errorLabels 
    */
   public static void checkForEmptyErrorBox(String errorMessage, Label[] errorLabels) {
        for (Label errorLabel : errorLabels) {
            if (errorMessage.equals("reset")){
               errorLabel.setText("null");
               errorLabel.setVisible(false);
            }
            else if (errorLabel.getText().equals("null")) {
                errorLabel.setText(errorMessage);
                errorLabel.setVisible(true);
                break;
            }
        }
    }  
   
    /**
     * Method is called when program needs to print an error to "main_form.fxml". Accepts two strings, one to identify which label to change on main page and the other is the error message.
     * <p>
     * LOGICAL ERROR:
     * <p>   
     * This error also applies to "productErrorboxes". An initial error with this method was that I did not have the ".setVisiblle" lines, which would cause two errors two show up at the same time, even though their was really only one error. Since their are only two error boxes on each page for
       these type of errors and only one error can be present at once. Whenever one error is set, the code sets the other error box to invisible. This will help prevent confusion for the user.
     * @param label
     * @param error 
     */
    public static void mainErrorboxes(String label, String error){
        if (label.equals("parts")){
        PartsMain.setText(error);
        PartsMain.setVisible(true);
        ProductsMain.setVisible(false);        
        } else {
        ProductsMain.setText(error);
        ProductsMain.setVisible(true);
        PartsMain.setVisible(false);            
        }
    }

    /**
     * Method is called when program needs to print an error to "products.fxml". Accepts two strings, one to identify which label to change on product page and the other is the error message.
     * @param label
     * @param error 
     */
    public static void productErrorboxes(String label, String error){
        if (label.equals("parts")){
        PartsinProduct.setText(error);
        PartsinProduct.setVisible(true);
        AssociatedPartsinProduct.setVisible(false);        
        } else {
        AssociatedPartsinProduct.setText(error);
        AssociatedPartsinProduct.setVisible(true);
        PartsinProduct.setVisible(false);            
        }
    }    
}