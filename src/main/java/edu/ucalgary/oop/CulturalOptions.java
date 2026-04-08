package edu.ucalgary.oop;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;



public class CulturalOptions implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private HashMap<String, Set<String>> accommodations;

   

    public CulturalOptions() {
        this.accommodations = new HashMap<>();
    }
    
    public HashMap<String, Set<String>> getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(HashMap<String, Set<String>> accommodations) {
        this.accommodations = accommodations;
    }


}


