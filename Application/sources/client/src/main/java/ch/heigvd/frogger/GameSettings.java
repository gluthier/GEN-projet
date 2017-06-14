package ch.heigvd.frogger;

import ch.heigvd.protocol.Difficulty;
import ch.heigvd.protocol.MapSize;
import ch.heigvd.protocol.Party;

import java.util.List;

/**
 * Created by lognaume on 5/30/17.
 */
public class GameSettings {
    private List<Difficulty> difficulties;
    private List<MapSize> mapSizes;
    private String username;
    private Party.FreeRole role;

    public List<MapSize> getMapSizes() {
        return mapSizes;
    }

    public void setMapSizes(List<MapSize> mapSizes) {
        this.mapSizes = mapSizes;
    }

    public List<Difficulty> getDifficulties() {
        return difficulties;
    }

    public Difficulty getDifficultyById(int id) {
        for (Difficulty d : difficulties) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    public void setDifficulties(List<Difficulty> difficulties) {
        this.difficulties = difficulties;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Party.FreeRole getRole() {
        return role;
    }

    public void setRole(Party.FreeRole role) {
        this.role = role;
    }
}
