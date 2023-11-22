package controllers;

import utility.inputcheck;
import Main.Main;
import Outsourced_Inhouse.Inhouse;
import Inventory.Inventory;
import Outsourced_Inhouse.Outsourced;
import Part.Part;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * This class handles all the actions that occur when a user interacts with the parts.fxml file.
 * @author Arun Rai
 */

public class parts_controller  {

    @FXML
    private Button cancelPartbutton;

    @FXML
    private TextField idTextbox;

    @FXML
    private RadioButton inHouseradiobutton;

    @FXML
    private TextField invTextbox;

    @FXML
    private TextField machineIdtextbox;

    @FXML
    private TextField maxTextbox;

    @FXML
    private TextField minTextbox;

    @FXML
    private TextField nameTextbox;

    @FXML
    private RadioButton outsourcedradiobutton;

    @FXML
    private TextField price_costTextbox;

    @FXML
    private Button savePartbutton;
    
    @FXML
    private Label partLabel;
    
    @FXML
    private Label machine_company;
    
    @FXML
    private Label error1;

    @FXML
    private Label error2;

    @FXML
    private Label error3;

    @FXML
    private Label error4;

    @FXML
    private Label error5;

    @FXML
    private Label error6;

    @FXML
    private Label error7;

    @FXML
    private Label error8;

    @FXML
    private Label error9;

    private int listindex;

    private ToggleGroup group = new ToggleGroup();    
    
    /**
     * Sets the InHouseradiobutton to selected when page is first loaded. Then puts radio buttons in a toggle group so one has to be selected at all times.
     * <p>
     * LOGICAL ERROR:
     * <p>   
     * When the FXML file associated with parts_controller was loaded, I noticed that none of the radio buttons were selected. For my program one radio button has to be selected at all times, so I added this code that adds both radio buttons to a toggle group which means one has to be selcted at all time, and chose
       which one to have selected at the start of the program.
     */
    @FXML
    private void initialize() {
        inHouseradiobutton.setSelected(true);
        inHouseradiobutton.setToggleGroup(group);
        outsourcedradiobutton.setToggleGroup(group);
    }
    
    /**
     * When user clicks "modify" on the parts pane from the main page, this method is called and will fill in data about the part the user selected from the main page. It is not in the initialize method because the FXML file associated with this class sometimes needs to be loaded with no data, for example clicking
       "Add" from the parts pane on the main page.
     * <p>
     * LOGICAL ERROR:
     * <p>   
     * The error with this method is that at first it was inside the initialize method, I found this would be a problem because I did not want the code in this method triggered every time its initialized (only want to load data on "Modify Part" not "Add Part", they both use same initialize method). And since the initialize method
       can not accept additional parameters, I decided to make a separate method that can be called to load data if required, nut not mandatory.
     * @param part 
     */
    public void loadPartData(Part part) {
        ObservableList<Part> thelist = Inventory.getAllParts();
        listindex = thelist.indexOf(part);        
        idTextbox.setPromptText(Integer.toString(part.getId()));
        nameTextbox.setText(part.getName());
        price_costTextbox.setText(Double.toString(part.getPrice()));
        invTextbox.setText(Integer.toString(part.getStock()));
        minTextbox.setText(Integer.toString(part.getMin()));
        maxTextbox.setText(Integer.toString(part.getMax()));
        if (part instanceof Inhouse) {
            inHouseradiobutton.setSelected(true);
            machineIdtextbox.setText(Integer.toString(((Inhouse)part).getMachineId()));
        } else if (part instanceof Outsourced) {
            outsourcedradiobutton.setSelected(true);
            machineIdtextbox.setText(((Outsourced)part).getCompanyName());
            machine_company.setText("Company Name");
        }
    }

    /**
     * When cancel button is clicked, program returns user to the main page and no data is saved.
     * @param event 
     */
    @FXML
    private void clickedCancelpart(ActionEvent event) {
        Main.handleSceneChange("main_form.fxml", "null", "null", cancelPartbutton,null,null, "no", null);
    }
    
    /**
     * When outsourcedradiobutton is selected, it will change the name of the text label next to the corresponding text box to "Company Name".
     * @param event 
     */
    @FXML
    void clickedOutsourced(ActionEvent event) {
         machine_company.setText("Company Name");
         
    }    

    /**
     *  When inHouseradiobutton is selected, it will change the name of the text label next to the corresponding text box to "Machine ID".
     * @param event 
     */
    @FXML
    void clickedinHouseradio(ActionEvent event) {
         machine_company.setText("Machine ID");
    }

    /**
     * Method is ran when "Save" button is clicked, it will create a class that accepts error boxes for where error messages will be placed if their invalid input data. This class also has a method that accepts all of the filled in text boxes and returns a part object that is not null if all of the input data is correct, or null if incorrect. After this a conditional statement is ran and it
       will check if part is not null, if true it will either add the valid part, or update the part that was loaded during the part.fxml file loading process and return to user to home page. Else, it will print errors to the user and remain on current page.
     * <p>
     * RUNTIME ERROR:
     * <p>   
     * The error for this method was on a macro scope of the program. When a user clicked "save" the program plenty of tasks to complete, such as verifying all the data, finding empty error boxes, and adding it the inventory class. It was too much code that was causing plenty of errors so I decided to break these tasks into 
       different classes and methods.
     * @param event 
     */
    @FXML
    void clickedSavepart(ActionEvent event) {
        inputcheck checking = new inputcheck();
        Label[] errorLabels = new Label[] {error1, error2, error3, error4, error5, error6, error7, error8, error9};
        checking.setErrorFields(errorLabels);
        Part part = (Part)checking.validatePart(idTextbox,nameTextbox,price_costTextbox,invTextbox,minTextbox,maxTextbox,machineIdtextbox,inHouseradiobutton,outsourcedradiobutton,"part",partLabel);
        if (part != null){
            if (partLabel.getText().equals("Add Part")){
               Inventory.addPart(part);
            }else if (partLabel.getText().equals("Modify Part")){
               Inventory.updatePart(listindex, part);
            }    
            Main.handleSceneChange("main_form.fxml", "null", "null", savePartbutton,null,null, "no", null);
        }                
    }    
}

