package Outsourced_Inhouse;

import Part.Part;


/**
 * The class Inhouse extends the Part class and has a machineId attribute and associated methods to set and retrieve the machine ID.
 * @author Arun Rai
 */



public class Inhouse extends Part {
    private int machineId;
    public Inhouse(int id, String name, double price, int stock, int min, int max, int machineId){
       super(id, name, price, stock, min, max);
       this.machineId = machineId;     
    }
    
    /**
     * @param machineId the machine ID to set
     */        
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }
    
    /**
     * RUNTIME ERROR:
     * <p> 
     * In the method name had "public String" instead of "public int" because I was mistaking this variable for Company Name which accepts a string.
     * @return the machine ID
     */        
    public int getMachineId(){
       return machineId; 
    }
    
}
