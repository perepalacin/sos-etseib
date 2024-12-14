package entities;

import com.google.gson.Gson;
import org.junit.Test;
import services.BodyParser;

import static org.junit.Assert.*;

public class UserDto {

    @Test
    public void testValidUsername () {
        UserDao user = new UserDao("michelle.obama.test@estudiantat.upc.edu", "12345");
        UserDao user2 = new UserDao("peter.kavinsky.test@estudiantat.upc.edu", "12345");
        assertEquals("M. Obama", user.getUsername());
        assertEquals("P. Kavinsky", user2.getUsername());
    }

    @Test
    public void testInvalidUsername () {
        UserDao user = new UserDao("peterkavinsky@estudiantat.upc.edu", "12345");
        assertNotEquals("P. Kavinsky", user.getUsername());
    }

    @Test
    public void testInvalidUserEmail () {
        UserDao user = new UserDao("peterkavinsky@gmail.com", "12345");
        UserDao user2 = new UserDao("estudiantat.upc.edu@gmail.com", "12345");
        assertFalse(user.validateUserEmail());
        assertFalse(user2.validateUserEmail());
    }

    @Test
    public void testValidUserEmail () {
        UserDao user = new UserDao("peterkavinsky@estudiantat.upc.edu", "12345");
        assertTrue(user.validateUserEmail());
    }

    @Test
    public void testInvalidPassword() {
        UserDao user = new UserDao("peterkavinsky@estudiantat.upc.edu", "12345");
        UserDao user1 = new UserDao("peterkavinsky@estudiantat.upc.edu", "User123");
        UserDao user2 = new UserDao("peterkavinsky@estudiantat.upc.edu", "a");
        assertFalse(user.validatePassword());
        assertFalse(user1.validatePassword());
        assertFalse(user2.validatePassword());
    }

    @Test
    public void testValidPassword() {
        UserDao user = new UserDao("peterkavinsky@estudiantat.upc.edu", "User123*");
        UserDao user1 = new UserDao("peterkavinsky@estudiantat.upc.edu", "jhgrgjkeA2*");
        UserDao user2 = new UserDao("peterkavinsky@estudiantat.upc.edu", "*dvk2vjrA");
        assertTrue(user.validatePassword());
        assertTrue(user1.validatePassword());
        assertTrue(user2.validatePassword());
    }
}
