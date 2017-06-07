package ch.heig.bdd;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxime Guillod
 */
public class Config {

    private int numCols;
    private int numRows;
    private int playerSpeed;
    private int intitialPlayerX;
    private int intitialPlayerY;

    public Config(ResultSet result) throws SQLException {
        this.numCols = result.getInt("num_cols");
        this.numRows = result.getInt("num_raws");
        this.playerSpeed = result.getInt("player_speed");
        this.intitialPlayerX = result.getInt("initial_player_x");
        this.intitialPlayerY = result.getInt("initial_player_y");
    }

    public Config(int numCols, int numRaws, int playerSpeed, int initialPlayerX, int initialPlayerY) {
        this.numCols = numCols;
        this.numRows = numRaws;
        this.playerSpeed = playerSpeed;
        this.intitialPlayerX = initialPlayerX;
        this.intitialPlayerY = initialPlayerY;
    }

    public int getNumRows() {
        return numCols;
    }

    public int getNumCols() {
        return numRows;
    }

    public int getPlayerSpeed() {
        return playerSpeed;
    }

    public int getIntitialPlayerX() {
        return intitialPlayerX;
    }

    public int getIntitialPlayerY() {
        return intitialPlayerY;
    }
    
    

}
