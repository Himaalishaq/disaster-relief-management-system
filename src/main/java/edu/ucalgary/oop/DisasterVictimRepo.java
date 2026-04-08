package edu.ucalgary.oop;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Types;
import java.util.ArrayList;


public class DisasterVictimRepo {

    public ArrayList<DisasterVictim> getDisasterVictims() {
        ArrayList<DisasterVictim> victims = new ArrayList<>();
        String sql = "SELECT p.id, p.first_name, p.last_name, p.comments, "
            + "d.date_of_birth, d.approximate_age, d.gender, d.entry_date "
            + "FROM person p "
            + "JOIN disastervictim d ON p.id = d.person_id "
            + "WHERE d.is_soft_deleted = FALSE "
            + "ORDER BY p.first_name, p.last_name";
          


        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()) {
                

            while (rs.next()) {
                DisasterVictim victim = mapVictim(rs);
                victims.add(victim);

            }
        } catch (Exception e) {
                e.printStackTrace();
        }

        return victims;

    }
 
    

    private DisasterVictim mapVictim(ResultSet rs) throws SQLException {
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String comments = rs.getString("comments");
        String gender = rs.getString("gender");
        
        
        LocalDate entryDate = rs.getDate("entry_date").toLocalDate();
        java.sql.Date dob = rs.getDate("date_of_birth");
        Integer approximateAge = rs.getInt("approximate_age");

    

        DisasterVictim victim;
        if (dob != null) {
            victim = new DisasterVictim(firstName, entryDate, dob.toLocalDate());
        } else {
            victim = new DisasterVictim(firstName, entryDate);
        }

        victim.setPersonId(rs.getInt("id"));
        victim.setCulturalRequirements(getCulturalRequirementsForVictim(victim.getPersonId()));
        victim.setLastName(lastName);
        victim.setComments(comments);
        
        try{
            victim.setGender(gender);

        } catch (IllegalArgumentException e) {
            victim.setGender("Please specify");
        }



        
        Object approxAgeObject = rs.getObject("approximate_age");
        if (approxAgeObject != null) {
            victim.setApproximateAge(approximateAge);
            
        } else {
            approximateAge = null;
        }
        
        return victim;

    }


    public void addDisasterVictim(DisasterVictim victim) {
        String insertPerson = "INSERT INTO person (first_name, last_name, comments) " 
            + "VALUES (?, ?, ?) RETURNING id";
        

        String insertVictim = "INSERT INTO disastervictim "  
            + "(person_id, date_of_birth, approximate_age, gender, entry_date, is_soft_deleted)"
            + "VALUES (?, ?, ?, ?, ?, FALSE)";
        
        try (Connection connection = DatabaseConnection.getConnection()) {
            int new_Id = -1;
            try (PreparedStatement ps = connection.prepareStatement(insertPerson)) {
                ps.setString(1, victim.getFirstName());
                ps.setString(2, victim.getLastName());
                ps.setString(3, victim.getComments());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    new_Id = rs.getInt("id");
                    victim.setPersonId(new_Id);
                }
            }

            if (new_Id != -1) {
                try (PreparedStatement ps = connection.prepareStatement(insertVictim)) {
                    ps.setInt(1, new_Id);
                    if (victim.getDateOfBirth() == null) {
                        ps.setNull(2, Types.DATE);
                    } else {
                        ps.setDate(2, java.sql.Date.valueOf(victim.getDateOfBirth()));
                    
                    }
                    if (victim.getApproximateAge() == null) {
                        ps.setNull(3, Types.INTEGER);
                    } else {
                        ps.setInt(3, victim.getApproximateAge());
                    }


                    ps.setString(4, victim.getGender());
                    ps.setDate(5, java.sql.Date.valueOf(victim.getEntryDate()));
                    ps.executeUpdate();

                    ActionLogger logger = ActionLogger.getInstance();
                    String addedEntity = "Disaster Victim: " + victim.getFirstName() + " " + victim.getLastName();
                    logger.logAdded(addedEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDisasterVictim(DisasterVictim victim) {
        String updatePerson = "UPDATE person SET first_name = ?, last_name = ?, comments = ? "
            + "WHERE id = ?";
        String updateVictim = "UPDATE disastervictim SET date_of_birth = ?, "
            + "approximate_age = ?, "
            + "gender = ? WHERE person_id = ?";

            try (Connection connection = DatabaseConnection.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement(updatePerson)) {
                    ps.setString(1, victim.getFirstName());
                    ps.setString(2, victim.getLastName());
                    ps.setString(3, victim.getComments());  
                    ps.setInt(4, victim.getPersonId());
                    ps.executeUpdate();

                }
                try (PreparedStatement ps = connection.prepareStatement(updateVictim)) {
                    if (victim.getDateOfBirth() == null) {
                        ps.setNull(1, java.sql.Types.DATE);
                    } else {
                        ps.setDate(1, java.sql.Date.valueOf(victim.getDateOfBirth()));
            
                    }
                    if (victim.getApproximateAge() == null) {
                        ps.setNull(2, Types.INTEGER);
                    } else {
                        ps.setInt(2, victim.getApproximateAge());
                    }


                    ps.setString(3, victim.getGender());
                    ps.setInt(4, victim.getPersonId());
                    ps.executeUpdate();

                    
                    ActionLogger logger = ActionLogger.getInstance();
                    String updatedEntity = "Disaster Victim " + victim.getPersonId() + " --- Name: " + victim.getFirstName() + " " + victim.getLastName();
                    logger.logUpdated(updatedEntity);





                }
            } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void softDeleteDisasterVictim(int personId) {
        String sql = "UPDATE disastervictim SET is_soft_deleted = TRUE WHERE person_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, personId);
                ps.executeUpdate();


                
                ActionLogger logger = ActionLogger.getInstance();
                String softDeletedEntity = "Disaster Victim " + personId; 
                logger.logAdded(softDeletedEntity);

        } catch (Exception e) {
            e.printStackTrace();
            }
    }

    public void hardDeletedisasterVictim(int personId) {
        String sql = "DELETE FROM person WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, personId);
                ps.executeUpdate();


                ActionLogger logger = ActionLogger.getInstance();
                String hardDeletedEntity = "Disaster Victim " + personId; 
                logger.logAdded(hardDeletedEntity);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        public void saveCulturalRequirements(DisasterVictim victim) {
        String deleteSql = "DELETE FROM culturalrequirement WHERE victim_id = ?";
        String insertSql = "INSERT INTO culturalrequirement "
            + "(victim_id, requirement_category, requirement_option) "
            + "VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(deleteSql)) {
                statement.setInt(1, victim.getPersonId());
                statement.executeUpdate();
            }
            ArrayList<CulturalRequirement> reqs = victim.getCulturalRequirements();
            int x = 0;
            while (x < reqs.size()) {
                CulturalRequirement req = reqs.get(x);
                try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
                    statement.setInt(1, victim.getPersonId());
                    statement.setString(2, req.getRequirementType());
                    statement.setString(3, req.getSelectedOption());
                    statement.executeUpdate();


                }
                x++;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<CulturalRequirement> getCulturalRequirementsForVictim(int victimId) {
        ArrayList<CulturalRequirement> requirements = new ArrayList<>();
        String sql = "SELECT requirement_category, requirement_option "
            + "FROM culturalrequirement "
            + "WHERE victim_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, victimId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String type = rs.getString("requirement_category");
                String option = rs.getString("requirement_option");

                CulturalRequirement requirement = new CulturalRequirement(type, option);
                requirements.add(requirement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requirements;

    }




}
























