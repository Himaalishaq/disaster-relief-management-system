package edu.ucalgary.oop;



import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;


import java.time.LocalDate;


public class MedicalRecordTest {
    private MedicalRecord record;
    private LocalDate treatmentDate;
    private Location location;




    @Before
    public void setUp() {
        location = new Location("Clinic A", "University Station");
        treatmentDate = LocalDate.of(2026, 4, 7);
        record = new MedicalRecord(location, "Broken leg", treatmentDate);

    }


    @Test
    public void testConstructorSetsLocation() {
        assertSame(location, record.getLocation());
    }

    @Test
    public void testConstructorSetsTreatmentDetails() {
        assertEquals("Broken leg", record.getTreatmentDetails());
    }



    @Test
    public void testConstructorSetsDateOfTreatment() {
        assertEquals(treatmentDate, record.getDateOfTreatment());
    }

    @Test
    public void testSetAndGetDateOfTreatment() {
        LocalDate newDate = LocalDate.of(2026, 4, 7);
        record.setDateOfTreatment(newDate);
        assertEquals(newDate, record.getDateOfTreatment());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfTreatmentNullThrowsException() {
        record.setDateOfTreatment(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfTreatmentinTheFutureThrowsException() {
        record.setDateOfTreatment(LocalDate.now().plusDays(1));
    }



    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullDateThrowsException() {
        new MedicalRecord(location,"Broken leg", null);
    }



    @Test
    public void testConstructorAllowsNullLocation() {
        MedicalRecord record = new MedicalRecord(null, "Broken leg", treatmentDate);
        assertNull(record.getLocation());

    }
    @Test
    public void testSetTreatmentDetailsAllowsNull() {
        record.setTreatmentDetails(null);
        assertNull(record.getTreatmentDetails());
    }
    @Test
    public void testSetTreatmentDetailsAllowsEmptyString() {
        record.setTreatmentDetails("");
        assertEquals("", record.getTreatmentDetails());
    }



    @Test(expected = IllegalArgumentException.class)
    public void testConstructorFutureDateThrowsException() {
        new MedicalRecord(location, "Broken leg", LocalDate.now().plusDays(1));
    }



    @Test
    public void testSetAndGetLocation() {
        Location newLocation = new Location("Clinic B", "Crowfoot Station");
        record.setLocation(newLocation);
        assertSame(newLocation, record.getLocation());
    }

    public void testSetLocationAllowsNull() {
        record.setLocation(null);
        assertNull(record.getLocation());
    }



    @Test
    public void testSetAndGetTreatmentDetails() {
        record.setTreatmentDetails("Updated treatment for broken leg");
        assertEquals("Updated treatment for broken leg", record.getTreatmentDetails());
    }

    @Test
    public void testLocationReferenceIsSameObject() {
        assertSame(location, record.getLocation());
    }



    @Test
    public void testSetAndGetMedicalRecordId() {
        record.setMedicalRecordId(13);
        assertEquals(13, record.getMedicalRecordId());
    }



}
