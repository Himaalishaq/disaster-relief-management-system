package edu.ucalgary.oop;


import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;




public class InquirerTest {
    
    private Inquirer inquirer;

    @Before
    public void setUp() {
        inquirer = new Inquirer("Himaal", "Ishaq", "403-000-0000", "Looking for her younger sister");

    }


    @Test
    public void testConstructorSetsFirstName() {
        assertEquals("Himaal", inquirer.getFirstName());
    }


    @Test
    public void testConstructorSetsLastName() {
        assertEquals("Ishaq", inquirer.getLastName());

    }


    @Test
    public void testConstructorSetsPhoneNumber() {
        assertEquals("403-000-0000", inquirer.getServicesPhoneNum());
    }

    @Test
    public void testConstructorSetsInfo() {
        assertEquals("Looking for her younger sister", inquirer.getInfo());
    }


    @Test
    public void testNullFirstNameAllowed() {
        Inquirer inquirer = new Inquirer(null, "Ishaq", "403-000-0000", "Looking for her younger sister");
        assertNull(inquirer.getFirstName());
    }


    @Test
    public void testNullLastNameAllowed() {
        Inquirer inquirer = new Inquirer("Himaal", null, "403-000-00000", "Looking for her younger sister");
        assertNull(inquirer.getLastName());
    }


    @Test
    public void testNullPhoneAllowed() {
        Inquirer inquirer = new Inquirer("Himaal", "ishaq", null, "Looking for her younger sister");
        assertNull(inquirer.getServicesPhoneNum());
    }


    @Test
    public void testNullInfoAllowed() {
        Inquirer inquirer = new Inquirer("Himaal", "Ishaq", "403-000-0000",null);
        assertNull(inquirer.getInfo());
    }

    @Test
    public void testGettersReturnSameStringReferences() {
        String firstName = "Himaal";
        String lastName = "Ishaq";
        String phone = "403-000-0000";
        String info = "Looking for her younger sister";

        Inquirer inquirer = new Inquirer(firstName, lastName, phone, info);

        assertSame(firstName, inquirer.getFirstName());
        assertSame(lastName, inquirer.getLastName());
        assertSame(phone, inquirer.getServicesPhoneNum());
        assertSame(info, inquirer.getInfo());
    }


    @Test
    public void testSpecialCharacters() {
        Inquirer inquirer = new Inquirer("Himaal-Jr.", "Ishaq-Khan", "+1 (403) 000-0000", "Needs to find her sister quickly!");
        assertEquals("Himaal-Jr.", inquirer.getFirstName());
        assertEquals("Ishaq-Khan", inquirer.getLastName());
        assertEquals("+1 (403) 000-0000", inquirer.getServicesPhoneNum());
        assertEquals("Needs to find her sister quickly!", inquirer.getInfo());
    }   


    @Test
    public void testEmptyStringsAllowed() {
        Inquirer inquirer = new Inquirer("", "", "", "");
        assertEquals("", inquirer.getFirstName());
        assertEquals("", inquirer.getLastName());
        assertEquals("", inquirer.getServicesPhoneNum());
        assertEquals("", inquirer.getInfo());
    }

    
}
