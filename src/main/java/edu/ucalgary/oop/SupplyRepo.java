package edu.ucalgary.oop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Types;
import java.util.ArrayList;


public class SupplyRepo {
    public ArrayList<Supply> getSupplies() {
        ArrayList<Supply> supplies = new ArrayList<>();
        String sql = "SELECT id, supply_type, location_id, victim_id, "
            + "expiry_date, allocation_date, description "
            + "FROM supply "
            + "ORDER BY supply_type";
        
            
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()) {
                

            while (rs.next()) {
                Supply supply = mapSupply(rs);
                supplies.add(supply);

            }
        } catch (Exception e) {
                e.printStackTrace();
        }

        return supplies;

    }


    private Supply mapSupply(ResultSet rs) throws SQLException {
        String type = rs.getString("supply_type");
        int id = rs.getInt("id");
        Supply supply = new Supply(type, 1);
        supply.setSupplyId(id);
        java.sql.Date expiryDate = rs.getDate("expiry_date");
        if (expiryDate != null && supply.isPerishable()) {
            supply.setExpirationDate(expiryDate.toLocalDate());
        }
        int victimId = rs.getInt("victim_id");
        if (!rs.wasNull()) {
            supply.setAllocatedId(victimId);
        }
        return supply;
    }






    public ArrayList<Supply> getExpiredSupplies() {
        ArrayList<Supply> expiredSupplies = new ArrayList<>();
        ArrayList<Supply> allSupplies = getSupplies();
    
        int i = 0;
        while (i < allSupplies.size()) {
            Supply supply = allSupplies.get(i);
            if (supply.isExpired()) {
                expiredSupplies.add(supply);
            }
            i++;
        }
        return expiredSupplies;
    }

    public void addSupply(Supply supply) {
        String sql = "INSERT INTO supply (supply_type, location_id, victim_id, expiry_date, allocation_date, description) "
            + "VALUES (?, NULL, NULL, ?, NULL, ?) RETURNING id";
    
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, supply.getType());
            if (supply.isPerishable() && supply.getExpirationDate() != null) {
                statement.setDate(2, java.sql.Date.valueOf(supply.getExpirationDate()));
            } else {
                statement.setNull(2, Types.DATE);
            }
            statement.setString(3, supply.getType());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                supply.setSupplyId(rs.getInt("id"));
            }
            
  
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void allocateSupply(int supplyId, int victimId) {
        String sql = "UPDATE supply SET victim_id = ?, allocation_date = ? "
            + "WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, victimId);
                statement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
                statement.setInt(3, supplyId);
                statement.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

        public void deallocateSupply(int supplyId) {
        String sql = "UPDATE supply SET victim_id = NULL, allocation_date = NULL "
            + "WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, supplyId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
    





    

