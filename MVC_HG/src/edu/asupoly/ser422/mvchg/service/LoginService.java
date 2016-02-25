package edu.asupoly.ser422.mvchg.service;
import edu.asupoly.ser422.mvchg.model.LoginCredentials;
import edu.asupoly.ser422.mvchg.model.User;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service for logging in user to website
 */
public class LoginService {
    private static Logger logger = Logger.getLogger("edu.asupoly.ser422.mvchg.service.LoginService");
    
    /**
     * Verify the user credentials
     *
     * @param loginCredentials Credentials presented by user to log in
     *
     * @return Null on failed login, corresponding User object for successful
     *         login.
     */
    public static User login(LoginCredentials loginCredentials) {
        if (loginCredentials == null) {
            return null;
        }
	
        logger.log(Level.INFO,
		   "Attempting to log in user:" + loginCredentials.getUsername());
	
	User loggedInUser = new User();
	loggedInUser.setUsername(loginCredentials.getUsername());
	loggedInUser.setDisplayName(loginCredentials.getUsername());
	loggedInUser.setPreferences(null);
	return loggedInUser;
    }
}
