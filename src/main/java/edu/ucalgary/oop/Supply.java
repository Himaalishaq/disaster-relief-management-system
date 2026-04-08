/*
Copyright Ann Barcomb and Khawla Shnaikat, 2024-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;

import java.time.LocalDate;

public class Supply {
    private String type;
    private int quantity;
    private int SupplyId;
    private Integer allocatedId;
    private boolean perishable;
    private LocalDate expirationDate;



    private static final String[] PERISHABLE_TYPES = {"water", "food ration", "food", "bottled water", "infant formula", "medication", "medicine"};



    public Supply(String type, int quantity) throws IllegalArgumentException {
        this.type = type;
        setQuantity(quantity); // Use setter for validation
        this.perishable = isPerishableType(type);
    }


    public void setType(String type) { this.type = type; }
    
    public void setQuantity(int quantity) throws IllegalArgumentException {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }
    
    public String getType() { return this.type; }
    public int getQuantity() { return this.quantity; }

    private boolean isPerishableType(String type) {
        if (type == null) {
            return false;
    
        }
        String lowerCaseType = type.toLowerCase().trim();
        int i = 0;
        while (i < PERISHABLE_TYPES.length) {
            if (lowerCaseType.equals(PERISHABLE_TYPES[i])) {
                return true;
            }
            i = i + 1;
            
        }
        return false;
    }

    public boolean isExpired() {
        if ((expirationDate == null) || (!perishable)) {
            return false;
        }
        LocalDate e = expirationDate;
        return e.isBefore(LocalDate.now());
    }



    
    public LocalDate getExpirationDate() {
        return expirationDate;
    }


  

  
    public void setExpirationDate(LocalDate expirationDate) throws IllegalArgumentException {
        if (!perishable) {
            throw new IllegalArgumentException("Can't set an expiration date for non-perishable items");
        }
        this.expirationDate = expirationDate;
    }
    

    public boolean isPerishable() {
        return perishable;
    }


    public int getSupplyId() {
        return SupplyId;
    }

    public void setSupplyId(int supplyId) {
        this.SupplyId = supplyId;
    }

    public Integer getAllocatedId(){
        return allocatedId;
    }

    public void setAllocatedId(Integer allocatedId) {
        this.allocatedId = allocatedId;
    }
}
