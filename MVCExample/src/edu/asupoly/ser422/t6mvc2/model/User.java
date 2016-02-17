package edu.asupoly.ser422.t6mvc2.model;
import java.util.List;

/**
 * User of the web site
 */
public class User {
    private String username;
    private String displayName;
    private List preferences;

    /**
     * Get method for username
     *
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set method for username
     *
     * @param username Username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get method for display name
     *
     * @return Display name of user
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Set method for display name
     *
     * @param displayName Display name of user
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Get method for user preferences
     *
     * @return List of user preferences
     */
    public List getPreferences() {
        return preferences;
    }

    /**
     * Set method for user preferences
     *
     * @param preferences List of user preferences
     */
    public void setPreferences(List preferences) {
        this.preferences = preferences;
    }
}
