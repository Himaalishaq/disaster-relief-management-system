package edu.ucalgary.oop;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.time.LocalDate;

import java.util.ArrayList;




public class SkillRepo {

    public ArrayList<Skill> getSkillsForDisasterVictims(int disasterVictimId) {
        ArrayList<Skill> skills = new ArrayList<>();
        String sql = "SELECT vs.id AS victim_skill_id, s.id AS skill_id, "
            + "s.skill_name, s.category, vs.proficiency_level, vs.details, vs.language_capabilities, vs.certification_expiry "
            + "FROM victimskill vs "
            + "JOIN skill s ON vs.skill_id = s.id "
            + "WHERE vs.victim_id = ? "
            + "ORDER BY s.category, s.skill_name";
        
            
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            
                statement.setInt(1, disasterVictimId);
                ResultSet rs = statement.executeQuery();
            
                

            while (rs.next()) {
                Skill skill = mapSkill(rs);
                if (skills != null) {
                    skills.add(skill);
                }

            }
        } catch (Exception e) {
                e.printStackTrace();
        }

        return skills;

    }
    public ArrayList<DisasterVictim> getDisasterVictimsByCategory(String category) {
        ArrayList<DisasterVictim> disasterVictims = new ArrayList<>();

        String sql = "SELECT DISTINCT p.id, p.first_name, p.last_name, p.comments, "
            + "d.date_of_birth, d.approximate_age, d.gender, d.entry_date "
            + "FROM person p "
            + "JOIN disastervictim d ON p.id = d.person_id "
            + "JOIN victimskill vs ON d.person_id = vs.victim_id "
            + "JOIN skill s ON vs.skill_id = s.id "
            + "WHERE LOWER(s.category) = LOWER(?) "
            + "AND d.is_soft_deleted = FALSE "
            + "ORDER BY p.first_name, p.last_name";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            
                statement.setString(1, category);
                ResultSet rs = statement.executeQuery();
            
                

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String gender = rs.getString("gender");
                String comments = rs.getString("comments");
            
                LocalDate entryDate = rs.getDate("entry_date").toLocalDate();
                java.sql.Date dob = rs.getDate("date_of_birth");

                DisasterVictim disasterVictim;
                if (dob == null) {
                    disasterVictim = new DisasterVictim((firstName), entryDate);
                
                } else {
                    disasterVictim = new DisasterVictim((firstName), entryDate, dob.toLocalDate());
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

    public void addSkillToDisasterVictim(int disasterVictimId, Skill skill) {
        int skillId = getOrCreateSkillId(skill.getSkillName(), skill.getCategory());
    

    
        if (skillId == -1) {
            System.out.println("Error. Could not find/create skill in the database");
            return;
        }
        String sql = "INSERT INTO victimskill "
            + "(victim_id, skill_id, details, language_capabilities, certification_expiry, proficiency_level)"
            + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, disasterVictimId);
            statement.setInt(2, skillId);

            if (skill instanceof MedicalSkill) {
                MedicalSkill medSkill = (MedicalSkill) skill;
                statement.setString(3, medSkill.getCertificationInfo());

                if (medSkill.getCertificationExpirationDate() == null) {
                    statement.setNull(5, java.sql.Types.DATE);

                } else {
                    statement.setDate(5, java.sql.Date.valueOf(medSkill.getCertificationExpirationDate()));
                }


                statement.setNull(4, java.sql.Types.DATE);
            } else if (skill instanceof LanguageSkill) {
                LanguageSkill langSkill = (LanguageSkill) skill;
                statement.setNull(3, java.sql.Types.VARCHAR);
                statement.setString(4, langSkill.getStringCapabilities());
                statement.setNull(5, java.sql.Types.DATE);
            } else if (skill instanceof TradeSkill) {
                statement.setNull(3, java.sql.Types.VARCHAR);
                statement.setNull(4, java.sql.Types.VARCHAR);
                statement.setNull(5, java.sql.Types.DATE);
            }
            statement.setString(6, skill.getProficiencyLevel());
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeSkillFromDisasterVictim(int victim_skill_id) {
        String sql = "DELETE FROM victimskill WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, victim_skill_id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private int getOrCreateSkillId(String skillName, String category) {
        int skillId = -1;
        String selectSql = "SELECT id FROM skill "
            + "WHERE skill_name = ? AND category = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(selectSql)) {
            statement.setString(1, skillName);
            statement.setString(2, category);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                skillId = rs.getInt("id");
                return skillId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String insertSql = "INSERT INTO skill (skill_name, category)"
            + "VALUES (?, ?) RETURNING id";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(insertSql)) {
            statement.setString(1, skillName);
            statement.setString(2, category);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                skillId = rs.getInt("id");
                return skillId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skillId;

    }
    private Skill mapSkill(ResultSet rs) throws Exception {
        String skillName = rs.getString("skill_name");
        String category = rs.getString("category");
        String proficiency = rs.getString("proficiency_level");
        String details = rs.getString("details");
        String languageCapabilities = rs.getString("language_capabilities");
        java.sql.Date expiryDate = rs.getDate("certification_expiry");
        int skillId = rs.getInt("skill_id");
        int disasterVictimSkillId = rs.getInt("victim_skill_id");
        Skill skill = null;



        if (category.equals(Skill.CATEGORY_MEDICAL)) {
            LocalDate expiry = null;
            if (expiryDate !=null) {
                expiry = expiryDate.toLocalDate();
            }
            skill = new MedicalSkill(skillName, proficiency, details, expiry);




        } else if (category.equals(Skill.CATEGORY_LANGUAGE)) {
            ArrayList<String> capabilities = new ArrayList<>();
            if ((languageCapabilities != null) && (!languageCapabilities.trim().isEmpty())) {
                String[] parts = languageCapabilities.split(",");
                int i;
                for (i = 0; i< parts.length; i++) {
                    capabilities.add(parts[i].trim());
                }

            } else {
                capabilities.add((LanguageSkill.SPEAK_LISTEN_CAPABILITY));
            }
            skill = new LanguageSkill(skillName, proficiency, capabilities);

        } else if (category.equals(Skill.CATEGORY_TRADE)) {
            skill = new TradeSkill(skillName, proficiency);
        }
        if (skill!= null) {
            skill.setSkillId(skillId);
            skill.setDisasterVictimSkillId(disasterVictimSkillId);
        }
        return skill;
    }



    
    
}
