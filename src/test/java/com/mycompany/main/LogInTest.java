package com.mycompany.main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * Unit tests for the LogIn class (see Main.java).
 * Covers username, password and cell phone validation,
 * plus registration and login flow behaviour.
 *
 * Uses JUnit 5 (Jupiter).
 */
public class LogInTest {

    private LogIn login;

    @BeforeEach
    void setUp() {
        login = new LogIn();
    }

    // ---------------------------------------------------------
    // checkUserName()
    // Rule: must contain an underscore AND be <= 5 characters
    // ---------------------------------------------------------

    @Test
    @DisplayName("Username with underscore and length <= 5 is valid")
    void testCheckUserName_ValidShortWithUnderscore() {
        login.userName = "kt_1"; // 4 chars, has underscore
        assertTrue(login.checkUserName());
    }

    @Test
    @DisplayName("Username with underscore at exactly 5 characters is valid")
    void testCheckUserName_ValidAtBoundaryLength() {
        login.userName = "ab_cd"; // exactly 5 chars
        assertTrue(login.checkUserName());
    }

    @Test
    @DisplayName("Username without underscore is invalid")
    void testCheckUserName_NoUnderscore() {
        login.userName = "abcde"; // 5 chars, no underscore
        assertFalse(login.checkUserName());
    }

    @Test
    @DisplayName("Username with underscore but too long is invalid")
    void testCheckUserName_TooLong() {
        login.userName = "kt_cyril"; // has underscore but > 5 chars
        assertFalse(login.checkUserName());
    }

    @Test
    @DisplayName("Empty username is invalid")
    void testCheckUserName_Empty() {
        login.userName = "";
        assertFalse(login.checkUserName());
    }

    @Test
    @DisplayName("Username that is only an underscore is valid (has underscore, length 1)")
    void testCheckUserName_OnlyUnderscore() {
        login.userName = "_";
        assertTrue(login.checkUserName());
    }

    // ---------------------------------------------------------
    // checkPasswordComplexity()
    // Rule: >= 8 chars, at least one uppercase, one digit,
    // one special character
    // ---------------------------------------------------------

    @Test
    @DisplayName("Password meeting all complexity rules is valid")
    void testCheckPassword_Valid() {
        login.passWord = "Ch&&sec4ke"; // upper, digit, special, length 10
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    @DisplayName("Password shorter than 8 characters is invalid")
    void testCheckPassword_TooShort() {
        login.passWord = "Ab1!"; // only 4 chars
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    @DisplayName("Password with no uppercase letter is invalid")
    void testCheckPassword_NoUppercase() {
        login.passWord = "lowercase1!";
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    @DisplayName("Password with no digit is invalid")
    void testCheckPassword_NoDigit() {
        login.passWord = "NoDigitsHere!";
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    @DisplayName("Password with no special character is invalid")
    void testCheckPassword_NoSpecialChar() {
        login.passWord = "NoSpecial123";
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    @DisplayName("Password exactly 8 characters with all requirements is valid")
    void testCheckPassword_ExactMinimumLength() {
        login.passWord = "Abcdef1!"; // exactly 8 chars
        assertTrue(login.checkPasswordComplexity());
    }

    // ---------------------------------------------------------
    // checkCellPhoneNumber()
    // Rule: must start with "+27" and be exactly 12 characters
    // ---------------------------------------------------------

    @Test
    @DisplayName("Cell number with +27 and correct length is valid")
    void testCheckCellPhone_Valid() {
        login.cellPhoneNumber = "+27821234567"; // 12 chars
        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    @DisplayName("Cell number without +27 prefix is invalid")
    void testCheckCellPhone_MissingCountryCode() {
        login.cellPhoneNumber = "0821234567";
        assertFalse(login.checkCellPhoneNumber());
    }

    @Test
    @DisplayName("Cell number with +27 but too short is invalid")
    void testCheckCellPhone_TooShort() {
        login.cellPhoneNumber = "+2782123456"; // 11 chars
        assertFalse(login.checkCellPhoneNumber());
    }

    @Test
    @DisplayName("Cell number with +27 but too long is invalid")
    void testCheckCellPhone_TooLong() {
        login.cellPhoneNumber = "+278212345678"; // 13 chars
        assertFalse(login.checkCellPhoneNumber());
    }

    @Test
    @DisplayName("Empty cell number is invalid")
    void testCheckCellPhone_Empty() {
        login.cellPhoneNumber = "";
        assertFalse(login.checkCellPhoneNumber());
    }

    // ---------------------------------------------------------
    // registerUser()
    // ---------------------------------------------------------

    @Test
    @DisplayName("Registration succeeds and stores details when all fields are valid")
    void testRegisterUser_Success() {
        login.userName = "kt_1";
        login.passWord = "Ch&&sec4ke";
        login.cellPhoneNumber = "+27821234567";

        String result = login.registerUser();

        assertEquals("User registered successfully. Welcome kt_1!", result);
        assertEquals("kt_1", login.registeredUserName);
        assertEquals("Ch&&sec4ke", login.registeredPassWord);
        assertEquals("+27821234567", login.registeredCellPhoneNumber);
    }

    @Test
    @DisplayName("Registration fails and stores nothing when username is invalid")
    void testRegisterUser_FailsOnInvalidUsername() {
        login.userName = "nounderscore"; // invalid
        login.passWord = "Ch&&sec4ke";
        login.cellPhoneNumber = "+27821234567";

        String result = login.registerUser();

        assertEquals("Registration failed, please check your details and try again.", result);
        assertNull(login.registeredUserName);
    }

    @Test
    @DisplayName("Registration fails when password is invalid")
    void testRegisterUser_FailsOnInvalidPassword() {
        login.userName = "kt_1";
        login.passWord = "weak"; // invalid
        login.cellPhoneNumber = "+27821234567";

        String result = login.registerUser();

        assertEquals("Registration failed, please check your details and try again.", result);
        assertNull(login.registeredPassWord);
    }

    @Test
    @DisplayName("Registration fails when cell number is invalid")
    void testRegisterUser_FailsOnInvalidCellNumber() {
        login.userName = "kt_1";
        login.passWord = "Ch&&sec4ke";
        login.cellPhoneNumber = "0821234567"; // invalid

        String result = login.registerUser();

        assertEquals("Registration failed, please check your details and try again.", result);
        assertNull(login.registeredCellPhoneNumber);
    }

    // ---------------------------------------------------------
    // loginUser()
    // ---------------------------------------------------------

    @Test
    @DisplayName("Login fails if no user has registered yet")
    void testLoginUser_NoRegisteredUser() {
        boolean result = login.loginUser("kt_1", "Ch&&sec4ke");
        assertFalse(result);
    }

    @Test
    @DisplayName("Login succeeds with correct credentials after registration")
    void testLoginUser_SuccessAfterRegistration() {
        login.userName = "kt_1";
        login.passWord = "Ch&&sec4ke";
        login.cellPhoneNumber = "+27821234567";
        login.registerUser();

        boolean result = login.loginUser("kt_1", "Ch&&sec4ke");
        assertTrue(result);
    }

    @Test
    @DisplayName("Login fails with wrong username after registration")
    void testLoginUser_WrongUsername() {
        login.userName = "kt_1";
        login.passWord = "Ch&&sec4ke";
        login.cellPhoneNumber = "+27821234567";
        login.registerUser();

        boolean result = login.loginUser("wrong_", "Ch&&sec4ke");
        assertFalse(result);
    }

    @Test
    @DisplayName("Login fails with wrong password after registration")
    void testLoginUser_WrongPassword() {
        login.userName = "kt_1";
        login.passWord = "Ch&&sec4ke";
        login.cellPhoneNumber = "+27821234567";
        login.registerUser();

        boolean result = login.loginUser("kt_1", "WrongPass1!");
        assertFalse(result);
    }

    // ---------------------------------------------------------
    // returnLoginStatus()
    // ---------------------------------------------------------

    @Test
    @DisplayName("Returns success message with registered username when login succeeded")
    void testReturnLoginStatus_Success() {
        login.userName = "kt_1";
        login.passWord = "Ch&&sec4ke";
        login.cellPhoneNumber = "+27821234567";
        login.registerUser();

        String message = login.returnLoginStatus(true);
        assertEquals("Login successful, welcome back kt_1, it is great to see you again.", message);
    }

    @Test
    @DisplayName("Returns failure message when login did not succeed")
    void testReturnLoginStatus_Failure() {
        String message = login.returnLoginStatus(false);
        assertEquals("Username or password incorrect, please try again.", message);
    }
}
