package edu.ucalgary.oop;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Types;
import java.util.ArrayList;



public class FamilyRelationRepo {
    public ArrayList<FamilyRelation> getRelations(int personId) {
        ArrayList<FamilyRelation> relations = new ArrayList<>();
        String sql = "SELECT fr.id, fr.relationship_type, "
            + "p1.id AS person_one_id, p1.first_name AS person_one_first, p1.last_name AS person_one_last, "
            + "p2.id AS person_two_id, p2.first_name AS person_two_first, p2.last_name AS person_two_last "
            + "FROM familyrelationship fr "
            + "JOIN person p1 ON fr.person_one_id = p1.id "
            + "JOIN person p2 ON fr.person_two_id = p2.id "
            + "WHERE fr.person_one_id = ? OR fr.person_two_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, personId);
            statement.setInt(2, personId);
            ResultSet rs = statement.executeQuery();
        
                

            while (rs.next()) {
                String person1FN = rs.getString("person_one_first");
                String person1LN = rs.getString("person_one_last");
                int person1Id = rs.getInt("person_one_id");

                String person2FN = rs.getString("person_two_first");
                String person2LN = rs.getString("person_two_last");
                int person2Id = rs.getInt("person_two_id");

                DisasterVictim person1 = new DisasterVictim(person1FN, java.time.LocalDate.now());
                person1.setLastName(person1LN);
                person1.setPersonId(person1Id);

                DisasterVictim person2 = new DisasterVictim(person2FN, java.time.LocalDate.now());
                person2.setLastName(person2LN);
                person2.setPersonId(person2Id);

                String rs_type = rs.getString("relationship_type");
                FamilyRelation r = new FamilyRelation(person1, rs_type, person2);
                relations.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return relations;


                
    }
    public boolean relationExists(int person1Id, int person2Id) {
        String sql = "SELECT id FROM familyrelationship "
            + "WHERE ((person_one_id = ? AND person_two_id = ? )) OR ((person_one_id = ? AND person_two_id = ? ))"; 
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, person1Id);
            statement.setInt(2, person2Id);
            statement.setInt(3, person2Id);
            statement.setInt(4, person1Id);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            } 

            

        } catch (Exception e) {
                e.printStackTrace();
        }
        return false;

    }














    public void addFamilyRelation(int person1Id, int person2Id, String rs_type) {
        String sql = "INSERT INTO familyrelationship "
            + "(person_one_id, person_two_id, relationship_type) "
            + "VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, person1Id);
            statement.setInt(2, person2Id);
            statement.setString(3, rs_type);
            statement.executeUpdate();
            
            
                

        } catch (Exception e) {
                e.printStackTrace();
        }

    }

    public void removeFamilyRelation(int person1Id, int person2Id) {
        String sql = "DELETE FROM familyrelationship "
            + " WHERE ((person_one_id = ? AND person_two_id = ? )) OR ((person_one_id = ? AND person_two_id = ? ))";
        

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, person1Id);
            statement.setInt(2, person2Id);
            statement.setInt(3, person2Id);
            statement.setInt(4, person1Id);

            statement.executeUpdate();
            
            
                

        } catch (Exception e) {
                e.printStackTrace();
        }

    }




}
    

