package edu.ucalgary.oop;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Types;
import java.util.ArrayList;


public class MedicalRecordRepo {
    public ArrayList<MedicalRecord> getMedicalRecords(int disasterVictimId) {
        ArrayList<MedicalRecord> records = new ArrayList<>();
        String sql = "SELECT mr.id, mr.treatment_details, mr.treatment_date, "
            + "mr.location_id, l.name AS location_name, l.address AS location_address "
            + "FROM medicalrecord mr "
            + "LEFT JOIN location l ON mr.location_id = l.id "
            + "WHERE mr.victim_id = ? "
            + "ORDER BY mr.treatment_date DESC";
            
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, disasterVictimId);
            ResultSet rs = statement.executeQuery();
            
        
            while (rs.next()) {
                Location location = null;
                Object locationIdObject = rs.getObject("location_id");
                if (locationIdObject != null) {
                    String locationName = rs.getString("location_name");
                    String locationAddress = rs.getString("location_address");
                    location = new Location(locationName, locationAddress);
                    location.setLocationId(rs.getInt("location_id"));
                    
                }
                String treatmentDetails = rs.getString("treatment_details");
                LocalDate treatmentDate = rs.getDate("treatment_date").toLocalDate();
                MedicalRecord record = new MedicalRecord(location, treatmentDetails, treatmentDate);
                record.setMedicalRecordId(rs.getInt("id"));
                records.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }


    public void addMedicalRecord(int disasterVictimId, MedicalRecord record) {
        String sql = "INSERT INTO medicalrecord "
            + "(victim_id, treatment_details, treatment_date, location_id) "
            + "VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, disasterVictimId);
                statement.setString(2, record.getTreatmentDetails());
                statement.setDate(3, java.sql.Date.valueOf(record.getDateOfTreatment()));


                if (record.getLocation() == null) {
                    statement.setNull(4, java.sql.Types.INTEGER);
                } else {
                    statement.setInt(4, record.getLocation().getLocationId());
                }
                statement.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMedicalRecord(int MedicalRecordId, MedicalRecord record) {
        String sql = "UPDATE medicalrecord "
        + "SET treatment_details = ?, treatment_date = ?, location_id = ? "
        + "WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, record.getTreatmentDetails());
                statement.setDate(2, java.sql.Date.valueOf(record.getDateOfTreatment()));


                if (record.getLocation() == null) {
                    statement.setNull(3, java.sql.Types.INTEGER);
                } else {
                    statement.setInt(3, record.getLocation().getLocationId());
                }
                statement.setInt(4, MedicalRecordId);
                statement.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    
}
