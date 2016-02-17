package com.foobar.service;

import edu.asupoly.ser422.t6mvc2.model.LoginCredentials;
import edu.asupoly.ser422.t6mvc2.model.User;
import edu.asupoly.ser422.t6mvc2.service.LoginService;
import junit.framework.Assert;
import junit.framework.TestCase;


/**
 * Unit test cases for LoginServiceTest
 */
public class LoginServiceTest extends TestCase {
    private LoginCredentials validLogin;
    private LoginCredentials invalidLogin;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp() {
        validLogin = new LoginCredentials("johndoe", "secret");
        invalidLogin = new LoginCredentials("janedoe", "secret");
    }

    /**
     * Tests LoginService.login() for successful login.
     */
    public void testLoginSuccess() {
        /* Positive test */
        User successfulLogin = LoginService.login(validLogin);
        Assert.assertNotNull(successfulLogin);

        Assert.assertEquals("Display name mismatch", "John J. Doe Jr.",
            successfulLogin.getDisplayName());
    }

    /**
     * Tests LoginService.login() for failed login.
     */
    public void testLoginFailure() {
        /* Negative test */
        User failedLogin = LoginService.login(invalidLogin);
        Assert.assertNull(failedLogin);
    }
}
