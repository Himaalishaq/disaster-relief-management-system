Disaster Relief Management System
A Java command-line prototype for organizing information during disaster response. The application tracks people affected by a disaster, supplies, locations, inquiries about missing people, and relevant skills. It was developed as a University of Calgary object-oriented programming team project.

Tech stack: Java, PostgreSQL, JDBC, JUnit 4

What it does
- People: Add, view, update, and remove disaster victim records; manage family relationships, medical records, and cultural requirements.
- Supplies: Record items, view expired supplies, and allocate supplies to people.
- Inquiries: Record and search inquiries about missing people, including a last-known location when available.
- Locations: Manage locations and view the people and supplies associated with each one.
- Skills: Add medical, language, and trade skills to people's records and search by skill category.
  
The interface starts with these five workflows:
---Main Menu---
1 - Disaster Victims
2 - Supplies
3 - Inquiries
4 - Locations
5 - Skills and Volunteers
6 - Exit

How it is organized
The *CLI classes handle menus and input. Domain classes such as DisasterVictim, Supply, Location, and ReliefService represent the records and their rules. *Repo classes use JDBC prepared statements to read and write PostgreSQL data. The Skill class is extended by MedicalSkill, LanguageSkill, and TradeSkill. An ActionLogger writes dated add, update, and delete events to a local log file.
See [the UML diagram](UML_DIAGRAM_FINAL1.drawio.pdf) for the class relationships.


Project layout
src/main/java/edu/ucalgary/oop/   Application code and JUnit 4 tests
src/main/resources/                Serialized cultural options and local database settings
data/                              Runtime action log directory
UML_DIAGRAM_FINAL1.drawio.pdf      Class diagram


Local setup
The supplied project export does not include a database schema, dependency manifest, or a single-command build. A new clone needs these prerequisites before it can run:
1. Install a JDK that supports Path.of (Java 11 or newer) and PostgreSQL.
2. Create the PostgreSQL tables used by the repository classes. The schema is not included in this export.
3. Add the PostgreSQL JDBC driver to the project classpath. Add JUnit 4 if you want to run the tests.
4. Create src/main/resources/db.properties locally with your own credentials. Keep this file out of Git:
   db.url=jdbc:postgresql://localhost:5432/YOUR_DATABASE
   db.user=YOUR_USERNAME
   db.password=YOUR_PASSWORD
5. Keep src/main/resources/available_requirements.ser in place and create the data/ directory for the action log.
6. Open the project root in a Java IDE, add the JDBC dependency, and run edu.ucalgary.oop.Main with the project root as the working directory.
The database schema and a reproducible build definition are the next setup improvements for this repository.

Testing
The export contains 206 JUnit 4 test methods across 14 test classes. They cover domain validation, relationships, and action logging. The test files currently sit beside application classes in src/main/java/edu/ucalgary/oop/. Moving them to src/test/java/edu/ucalgary/oop/ and adding a Maven or Gradle build file would make the suite easier to run from a clean clone and in CI. This README does not claim a passing test run on a clean checkout.

Scope
This is an academic prototype and command-line application. It has not been deployed as an operational disaster response service.
