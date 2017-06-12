package ch.heig.bdd;

import java.rmi.server.UID;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.heigvd.protocol.Difficulty;
import ch.heigvd.protocol.MapSize;

/**
 * Communicate with the database for loginInformation, stats and more
 *
 * @author Maxime Guillod
 * @author Tony Clavien
 */
//TODO add method to get Difficulties and mapSizes from the DB
public class BDD {

    static enum Type {
        INFO,
        ERROR,
        WARNING
    }

    private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://proteck.ch:3306/HEIG_GEN";
    private static final String USERNAME = "GEN";
    private static final String PASSWORD = "Jm0y2x1&";
    private static final String MAX_POOL = "250";

    private static BDD instance = null;

    private Connection connection = null;

    private BDD() {
        /*
        Connect the database on the first call of getInsatnce()
         */
        connect();
    }

    public static BDD getInstance() {
        if (instance == null) {
            instance = new BDD();
            instance.connect();
        }
        return instance;
    }

    private void connect() {
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, getProperties());
            } catch (SQLException e) {
                Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, e);
            } catch (ClassNotFoundException e) {
                Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("user", USERNAME);
        properties.setProperty("password", PASSWORD);
        properties.setProperty("MaxPooledStatements", MAX_POOL);
        return properties;
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isClosed() {
        try {
            return connection.isClosed();
        } catch (SQLException ex) {
            Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean testLogin(String login, String password, ILog obj) {
        try {
            logInfo(obj, "Try connect : " + login);
            Statement s = connection.createStatement();
            ResultSet res = s.executeQuery("SELECT * FROM Login WHERE login=\"" + login + "\"");

            if (res.next()) {
                System.out.println(res.getString("password"));
                if (res.getString("password").equals(password)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void log(Class c, UID uid, BDD.Type type, String content) {
        try {
            /*
            Log into our database
             */
            if (connection == null) {
                connect();
            }
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Log"
                    + " (class, uid, type, content) VALUES "
                    + " (\"" + c.getName() + "\","
                    + "\"" + uid.hashCode() + "\","
                    + "\"" + type + "\","
                    + "\"" + content + "\");");
            /*
            Output the log into the consol
             */
            System.out.println(Time.valueOf(LocalTime.now()));
            System.out.println(" - " + c.getName());
            System.out.println(" - " + uid.hashCode());
            System.out.println(" - " + type);
            System.out.println(" - " + content);
            System.out.println("");
        } catch (SQLException ex) {
            Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void logError(ILog object, Exception e) {
        log(object.getClass(), object.getUid(), BDD.Type.ERROR, e.getMessage());
    }

    public void logWarning(ILog object, String message) {
        log(object.getClass(), object.getUid(), BDD.Type.WARNING, message);
    }

    public void logInfo(ILog obj, String content) {
        log(obj.getClass(), obj.getUid(), BDD.Type.INFO, content);
    }

    public String getLogString() {
        String retour = "";
        try {
            Statement s = connection.createStatement();
            ResultSet result = s.executeQuery("SELECT * FROM Log ORDER BY id DESC LIMIT 20;");
            while (result.next()) {
                retour += result.getString("date") + "\n";
                retour += " - " + result.getString("class") + "\n";
                retour += " - " + result.getString("uid") + "\n";
                retour += " - " + result.getString("type") + "\n";
                retour += " - " + result.getString("content") + "\n";
                retour += "\n";
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retour;
    }
    
    public List<Difficulty> getDifficulties() {
    	List<Difficulty> ret = new ArrayList<>();
    	try {
            Statement s = connection.createStatement();
            ResultSet result = s.executeQuery("SELECT * FROM DifficultyLevel ORDER BY id;");
            while (result.next()) {
            	ret.add(new Difficulty(result.getInt("id"),
            			result.getString("name"),
            			result.getInt("manaRegenerationSpeed"),
            			result.getInt("playerMoveSpeed"),
            			result.getInt("obstacleMoveSpeed"),
            			result.getInt("obstacleWidth")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
        }
    	return ret;
    }
    
    public Difficulty getDifficultyById(int id) {
    	try {
            Statement s = connection.createStatement();
            ResultSet result = s.executeQuery("SELECT * FROM DifficultyLevel WHERE id="+id+" ;");
            while (result.next()) {
            	return new Difficulty(result.getInt("id"),
            			result.getString("name"),
            			result.getInt("manaRegenerationSpeed"),
            			result.getInt("playerMoveSpeed"),
            			result.getInt("obstacleMoveSpeed"),
            			result.getInt("obstacleWidth"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public MapSize getMapSizeById(int id) {
    	try {
            Statement s = connection.createStatement();
            ResultSet result = s.executeQuery("SELECT * FROM MapSize WHERE id="+ id +";");
            while (result.next()) {
            	return new MapSize(result.getInt("id"),
            			result.getString("name"),
            			result.getInt("width"),
            			result.getInt("height"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<MapSize> getMapSizes() {
    	List<MapSize> ret = new ArrayList<>();
    	try {
            Statement s = connection.createStatement();
            ResultSet result = s.executeQuery("SELECT * FROM MapSize ORDER BY id;");
            while (result.next()) {
            	ret.add(new MapSize(result.getInt("id"),
            			result.getString("name"),
            			result.getInt("width"),
            			result.getInt("height")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
        }
    	return ret;
    }

    public List<Log> getLog() {
        return getLog(20);
    }

    private List get(String request) {
        // TODO
        return null;
    }

    public List<Log> getLog(int limit) {
        List<Log> list = new ArrayList();
        try {
            Statement s = connection.createStatement();
            ResultSet result = s.executeQuery("SELECT * FROM Log ORDER BY id DESC LIMIT " + limit + ";");
            while (result.next()) {
                list.add(new Log(result));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public String getLastLogContent() {
        try {
            Statement s = connection.createStatement();
            ResultSet result = s.executeQuery("SELECT content FROM Log ORDER BY id DESC LIMIT 10;");
            if (result.next()) {
                return result.getNString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<User> getUsers(int limit) {
        List<User> list = new ArrayList();
        try {
            Statement s = connection.createStatement();
            ResultSet result = s.executeQuery("SELECT * FROM Login ORDER BY id ASC LIMIT " + limit + ";");
            while (result.next()) {
                list.add(new User(result));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Config getConfig() {
        try {
            int retour = -1;
            Statement s = connection.createStatement();
            ResultSet result = s.executeQuery("SELECT * FROM Config ORDER BY id DESC LIMIT 1");
            result.next();
            return new Config(result);
        } catch (SQLException ex) {
            Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void setConfig(Config config) {
        try {
            Statement s = connection.createStatement();
            s.executeUpdate("UPDATE Config SET "
                    + " num_raws=" + config.getNumRows()
                    + ", num_cols=" + config.getNumCols()
                    + ", player_speed=" + config.getPlayerSpeed()
                    + ", initial_player_x=" + config.getIntitialPlayerX()
                    + ", initial_player_y=" + config.getIntitialPlayerY()
                    + " WHERE id = 1;");
        } catch (SQLException ex) {
            Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
