package edu.ucalgary.oop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Types;
import java.util.ArrayList;



public class LocationRepo {
    public ArrayList<Location> getLocations() {
        ArrayList<Location> locations = new ArrayList<>();
        String sql = "SELECT id, name, address FROM location ORDER BY name";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()) {
                    

            while (rs.next()) {
                Location location= mapLocation(rs);
                locations.add(location);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return locations;

    }
    



    public Location getLocationById(int id) {
        Location location = null;
        String sql = "SELECT id, name, address FROM location WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                location = mapLocation(rs);
            }
        

            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
        

    }

    public void addLocation(Location location) {
        String sql = "INSERT INTO location (name, address) VALUES (?, ?) RETURNING id";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, location.getName());
            statement.setString(2, location.getAddress());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int newId = rs.getInt("id");
                location.setLocationId(newId);
            }
            

            
        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }

    public boolean updateLocation(Location location) {
        String sql = "UPDATE location SET name = ?, address = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, location.getName());
            statement.setString(2, location.getAddress());
            statement.setInt(3, location.getLocationId());
            int rowsUpdated = statement.executeUpdate();
            
            return rowsUpdated > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
        

    }

        
    public ArrayList<DisasterVictim> getDisasterVictimsAtLocation(int locationId) {
        DisasterVictim disasterVictim;
        ArrayList<DisasterVictim> disasterVictims = new ArrayList<>();
        String sql = "SELECT p.first_name, p.last_name, p.id, p.comments, "
            + "d.date_of_birth, d.approximate_age, d.gender, d.entry_date "
            + "FROM person p "
            + "JOIN disastervictim d ON p.id = d.person_id "
            + "WHERE d.location_id = ? "
            + "AND d.is_soft_deleted = FALSE "
            + "ORDER BY p.first_name, p.last_name";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, locationId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String comments = rs.getString("comments");
                String gender = rs.getString("gender");
                
                LocalDate entryDate = rs.getDate("entry_date").toLocalDate();
                java.sql.Date dob = rs.getDate("date_of_birth");

                if (dob== null) {
                    disasterVictim = new DisasterVictim((firstName), entryDate);

                } else {
                    disasterVictim = new DisasterVictim(firstName, entryDate, dob.toLocalDate());

                }
                disasterVictim.setPersonId(rs.getInt("id"));
                disasterVictim.setLastName(lastName);
                disasterVictim.setComments(comments);

                try {
                    disasterVictim.setGender(gender);
                } catch (IllegalArgumentException e) {
                    disasterVictim.setGender("Please specify");
                }


                Object approxAge = rs.getObject("approximate_age");
                if (approxAge != null ) {
                    disasterVictim.setApproximateAge(rs.getInt("approximate_age"));
                }
                disasterVictims.add(disasterVictim);
            }
        


                
        } catch (Exception e) {
            e.printStackTrace();
        }
        return disasterVictims;
        
    }




        

    public ArrayList<Supply> getSuppliesAtLocation(int locationId) {
        ArrayList<Supply> supplies = new ArrayList<>();

        String sql = "SELECT id, supply_type, expiry_date, victim_id "
            + "FROM supply "
            + "WHERE location_id = ? "
            + "ORDER BY supply_type";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, locationId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String type = rs.getString("supply_type");
                Supply supply = new Supply(type, 1);
                supply.setSupplyId(rs.getInt("id"));
                
                


                java.sql.Date expiry_date = rs.getDate("expiry_date");
                
                if (expiry_date != null) {
                    supply.setExpirationDate(expiry_date.toLocalDate());
                }
                int disasterVictimId = rs.getInt("victim_id");

                if (!rs.wasNull()) {
                    supply.setAllocatedId(disasterVictimId);
                    
                }
                supplies.add(supply);
            }
               
        } catch (Exception e) {
            e.printStackTrace();

        }
        return supplies;

    }



    private Location mapLocation(ResultSet rs) throws SQLException{
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String address = rs.getString("address");
        Location location = new Location(name, address);
        location.setLocationId(id);
        return location;
    }



}