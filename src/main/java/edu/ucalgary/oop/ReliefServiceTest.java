package edu.ucalgary.oop;



import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;


import java.time.LocalDate;


public class ReliefServiceTest {
    private ReliefService reliefService;
    private Inquirer inquirer;
    private DisasterVictim missingPerson;
    private Location lastKnownLocation;
    private LocalDate inquiryDate;

    @Before
    public void setUp() {
        inquirer = new Inquirer("Himaal", "Ishaq", "403-000-0000", "Looking for her little sister");
        missingPerson = new DisasterVictim("Dila", LocalDate.of(2026, 4, 7));
        lastKnownLocation = new Location("Shelter A", "University Station" );
        inquiryDate = LocalDate.of(2026, 4, 7);

        reliefService = new ReliefService (inquirer, missingPerson, inquiryDate, "has glasses and long hair", lastKnownLocation);
    }
    

    @Test
    public void testConstructorSetsInquirer() {
        assertSame(inquirer, reliefService.getInquirer());
    }


    @Test
    public void testConstructorSetsMissingPerson() {
        assertSame(missingPerson, reliefService.getMissingPerson());
    }
 
    

    @Test
    public void testConstructorSetsDateOfInquiry() {
        assertEquals(inquiryDate, reliefService.getDateOfInquiry());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullDateThrowsException() {
        new ReliefService(inquirer, missingPerson, null, "has glasses and long hair", lastKnownLocation);
    }

    @Test public void testConstructorSetsInfo() {
        assertEquals("has glasses and long hair", reliefService.getInfoProvided());
    }


    @Test
    public void testConstructorSetsLastKnownLocation() {
        assertSame(lastKnownLocation, reliefService.getLastKnownLocation());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorFutureDateThrowsException() {
        new ReliefService(inquirer, missingPerson, LocalDate.now().plusDays(10), "has glasses and long hair", lastKnownLocation);
    } 

    public void testSetAndGetInquirer() {
        Inquirer newInquirer = new Inquirer("Sally", "Smith", "403-111-1111", "Searching for her 15 year old daughter");
        reliefService.setInquirer(newInquirer);
        assertSame(newInquirer, reliefService.getInquirer());
    }


    @Test
    public void testSetInquirerAllowsNull() {
        reliefService.setInquirer(null);
        assertNull(reliefService.getInquirer());
    }


    public void testSetAndGetMissingPerson() {
        DisasterVictim newMissingPerson = new DisasterVictim("Sarah", LocalDate.of(2026, 4, 7));
        reliefService.setMissingPerson(newMissingPerson);
        assertSame(newMissingPerson, reliefService.getMissingPerson());
    }


    @Test
    public void testSetMissingPersonAllowsNull() {
        reliefService.setMissingPerson(null);
        assertNull(reliefService.getMissingPerson());
    }

    @Test
    public void testSetAndGetDateOfInquiry() {
        LocalDate newDate = LocalDate.of(2026, 4, 7);
        reliefService.setDateOfInquiry(newDate);
        assertEquals(newDate, reliefService.getDateOfInquiry());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfInquiryNullThrowsException() {
        reliefService.setDateOfInquiry(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfInquiryInFutureThrowsException() {
        reliefService.setDateOfInquiry(LocalDate.now().plusDays(1));
    }

    @Test
    public void testSetAndGetInfoProvided() {
        reliefService.setInfoProvided("10 children who match given description at shelter");
        assertEquals("10 children who match given description at shelter", reliefService.getInfoProvided());
    }
    @Test
    public void testSetInfoProvidedAllowsEmptyString() {
        reliefService.setInfoProvided("");
        assertEquals("", reliefService.getInfoProvided());
    }    
    @Test
    public void testSetInfoProvidedAllowsNull() {
        reliefService.setInfoProvided(null);
        assertNull(reliefService.getInfoProvided());
    }
    @Test
    public void testAndSetGetLastKnownLocation() {
        Location l = new Location("Shelter Z", "Maxwell Station");
        reliefService.setLastKnownLocation(l);
        assertSame(l, reliefService.getLastKnownLocation());
    }

    @Test
    public void testSetLastKnownLocationAllowsNull() {
        reliefService.setLastKnownLocation(null);
        assertNull(reliefService.getLastKnownLocation());
    }

    @Test public void testGetLogDetails() {
        String expected = "Inquirer: Himaal, Missing Person: Dila, Date of Inquiry: 2026-04-07, Info Provided: has glasses and long hair, Last Known Location: Shelter A";
        assertEquals(expected, reliefService.getLogDetails());
    }

    @Test(expected = NullPointerException.class)
    public void testGetLogDetailsWithNullInquirerThrowsException() {
        reliefService.setInquirer(null);
        reliefService.getLogDetails();
    }

    @Test(expected = NullPointerException.class)
    public void testGetLogDetailsWithNullMissingPersonCurrentlyThrowsException() {
        reliefService.setMissingPerson(null);
        reliefService.getLogDetails();
    }

    @Test(expected = NullPointerException.class)
    public void testGetLogDetailsWithNullLastKnownLocationCurrentlyThrowsException() {
        reliefService.setLastKnownLocation(null);
        reliefService.getLogDetails();
    }










 
}
