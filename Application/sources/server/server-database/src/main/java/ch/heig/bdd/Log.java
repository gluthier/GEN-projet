package ch.heig.bdd;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxime Guillod
 */
public class Log {

    private final String classe;
    private final String content;
    private final String type;
    private final String date;
    private final String uid;

    public Log(String classe, String content, String type, String date, String uid) {
        this.classe = classe;
        this.content = content;
        this.type = type;
        this.date = date;
        this.uid = uid;
    }

    public Log(ResultSet result) throws SQLException {
        this.classe = result.getString("class");
        this.content = result.getString("content");
        this.type = result.getString("type");
        this.date = result.getString("date");
        this.uid = result.getString("uid");
    }
    
    public String getUid() {
        return uid;
    }

    public String getClasse() {
        return classe;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "[" + classe + ", " + type + ", " + content + ", " + date + "]";
    }

}
