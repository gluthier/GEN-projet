package ch.heig.bdd;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxime Guillod
 */
public class Config {

    private int carteHeight;
    private int carteWidth;

    public Config(ResultSet result) throws SQLException {
        this.carteHeight = result.getInt("carteHeight");
        this.carteWidth = result.getInt("carteWidth");
    }

    public int getCarteHeight() {
        return carteHeight;
    }

    public int getCarteWidth() {
        return carteWidth;
    }

}
