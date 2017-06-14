package ch.heig.bdd;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxime Guillod
 */
public class User {

    private final String username;
    private final String password;
    private final String lastLogin;

    public User(String username, String password, String lastLogin) {
        this.username = username;
        this.password = password;
        this.lastLogin = lastLogin;
    }

    public User(ResultSet result) throws SQLException {
        this.username = result.getString("login");
        this.password = result.getString("password");
        this.lastLogin = result.getString("last_login");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLastLogin() {
        return lastLogin;
    }

}
