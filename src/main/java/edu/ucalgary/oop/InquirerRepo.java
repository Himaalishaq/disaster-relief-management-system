package edu.ucalgary.oop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Types;
import java.util.ArrayList;



public class InquirerRepo {
    public ArrayList<Inquirer>getInquirers() {
        ArrayList<Inquirer> inquirers = new ArrayList<>();

        String sql = "SELECT DISTINCT p.id, p.first_name, p.last_name, p.comments "
            + "FROM person p "
            + "JOIN inquiry i ON p.id = i.inquirer_id "
            + "ORDER BY p.first_name, p.last_name";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()) {
                

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String comments = rs.getString("comments");

                Inquirer inquirer = new Inquirer(firstName, lastName, null, comments);
                inquirers.add(inquirer);


            }
        } catch (Exception e) {
                e.printStackTrace();
        }

        return inquirers;

    }

    public ArrayList<ReliefService> getInquiries() {
        ArrayList<ReliefService> inquiries = new ArrayList<>();
        String sql = "SELECT i.id, i.details, i.inquiry_date, "
            + "p.first_name AS inquirer_first_name, p.last_name AS inquirer_last_name, p.comments AS inquirer_comments, "
            + "s.first_name AS subject_first_name, s.last_name AS subject_last_name "
            + "FROM inquiry i "
            + "JOIN person p ON i.inquirer_id = p.id "
            + "LEFT JOIN person s ON i.subject_person_id = s.id "
            + "ORDER BY i.inquiry_date DESC";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()) {
                

            while (rs.next()) {
                String inquirerFirstName = rs.getString("inquirer_first_name");
                String inquirerLastName = rs.getString("inquirer_last_name");
                String inquirerComments = rs.getString("inquirer_comments");

                Inquirer inquirer = new Inquirer(inquirerFirstName, inquirerLastName, null,  inquirerComments);

                DisasterVictim subjectVictim = null;
                String subjectFirstName = rs.getString("subject_first_name");
                if (subjectFirstName != null) {
                    String subjectLastName = rs.getString("subject_last_name");
                    subjectVictim = new DisasterVictim(subjectFirstName, LocalDate.now());
                    subjectVictim.setLastName(subjectLastName);
                }
                String details = rs.getString("details");
                LocalDate inquiryDate = rs.getDate("inquiry_date").toLocalDate();
                
                
                ReliefService reliefService = new ReliefService(inquirer, subjectVictim, inquiryDate, details, null);
                inquiries.add(reliefService);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inquiries;          

    }

    public int addInquirer(Inquirer inquirer) {
        int newId = -1;

        String sql = "INSERT INTO person (first_name, last_name, comments) "
            + "VALUES (?, ?, ?) RETURNING id";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, inquirer.getFirstName());
            statement.setString(2, inquirer.getLastName());
            statement.setString(3, inquirer.getInfo());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                newId = rs.getInt("id");
            }


            
        } catch (Exception e) {
            e.printStackTrace();
        
        
        }
        return newId;
    }

    public void addInquiry(int inquirerId, int subjectPersonId, String details ) {
        String sql = "INSERT INTO inquiry (inquirer_id, subject_person_id, details, inquiry_date) "
            + "VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, inquirerId);
            if (subjectPersonId == -1) {
                statement.setNull(2, java.sql.Types.INTEGER);
            
            }  else {
                statement.setInt(2, subjectPersonId);
            }


            statement.setString(3, details);
            statement.executeUpdate();

            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }

    public int getInquirerId(Inquirer inquirer) {
        int id = -1;
        String sql = "SELECT p.id FROM person p "
            + "JOIN inquiry i ON p.id = i.inquirer_id "
            + "WHERE p.first_name = ? AND p.last_name = ? "
            + "LIMIT 1";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, inquirer.getFirstName());
            statement.setString(2, inquirer.getLastName());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
        
    }




    public ArrayList<ReliefService> searchInquiriesBySubjectName(String searchName) {
        ArrayList<ReliefService> results = new ArrayList<>();

        String sql = "SELECT i.id, i.details, i.inquiry_date, "
            + "p.first_name AS inquirer_first_name, p.last_name AS inquirer_last_name, p.comments AS inquirer_comments, "
            + "s.first_name AS subject_first_name, s.last_name AS subject_last_name "
            + "FROM inquiry i "
            + "JOIN person p ON i.inquirer_id = p.id "
            + "LEFT JOIN person s ON i.subject_person_id = s.id "
            + "WHERE LOWER(s.first_name) LIKE LOWER (?) "
            + "OR LOWER (s.last_name) LIKE LOWER (?) "
            + "OR LOWER(CONCAT(s.first_name, ' ', s.last_name)) LIKE LOWER(?) "
            + "ORDER BY i.inquiry_date DESC";
            
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
                String searchInq = "%" + searchName + "%";
                statement.setString(1, searchInq);
                statement.setString(2, searchInq);
                statement.setString(3, searchInq);

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    String inquirerFirstName = rs.getString("inquirer_first_name");
                    String inquirerLastName = rs.getString("inquirer_last_name");
                    String inquirerComments = rs.getString("inquirer_comments");
                    Inquirer inquirer = new Inquirer(inquirerFirstName, inquirerLastName, null, inquirerComments);
                    DisasterVictim subjDisasterVictim = null;
                    String subjectFirstName = rs.getString("subject_first_name");
                    
                    if (subjectFirstName != null) {
                        String subjectLastName = rs.getString("subject_last_name");
                        subjDisasterVictim = new DisasterVictim(subjectFirstName, LocalDate.now()); 
                        subjDisasterVictim.setLastName(subjectLastName);
                    }
                    String details = rs.getString("details");
                    LocalDate inquiryDate = rs.getDate("inquiry_date").toLocalDate();
                    ReliefService reliefService = new ReliefService(inquirer, subjDisasterVictim, inquiryDate, details, null);
                    results.add(reliefService);
                }
        

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}





    

