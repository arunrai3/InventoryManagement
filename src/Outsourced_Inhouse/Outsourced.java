package Outsourced_Inhouse;

import Part.Part;

/**
 * The class Outsourced extends the Part class and has a Company Name attribute and associated methods to set and retrieve the Company Name.
 * @author Arun Rai
 */



public class Outsourced extends Part {
    private String companyName;
    public Outsourced (int id, String name, double price, int stock, int min, int max, String companyName){
      super(id, name, price, stock, min, max);
      this.companyName = companyName;  
    }
    
    /**
     * RUNTIME ERROR:
     * <p>
     * Initially did not have "void" in method header declaration which caused unexpected errors because the method is not returning anything. 
     * @param companyName the company name to set
     */    
    public void setCompanyName(String companyName){
       this.companyName = companyName; 
    }

    /**
     * @return the company name
     */    
    public String getCompanyName(){
        return companyName;
    }
    
}
