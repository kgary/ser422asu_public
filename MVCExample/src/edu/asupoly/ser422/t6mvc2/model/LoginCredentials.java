package edu.asupoly.ser422.t6mvc2.model;

/**
 * Credentials for logging in user to website
 */
public class LoginCredentials {
    private String username;
    private String password;

    /**
     * Creates a new LoginCredentials object.
     */
    public LoginCredentials() {
    }

    /**
     * Creates a new LoginCredentials object.
     *
     * @param username Login username
     * @param password Login password
     */
    public LoginCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Get method for password
     *
     * @return Login password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set method for password
     *
     * @param password Login password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get method for username
     *
     * @return Login username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set method for username
     *
     * @param username Login username
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
