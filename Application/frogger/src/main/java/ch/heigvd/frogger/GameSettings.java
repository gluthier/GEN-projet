package ch.heigvd.frogger;

import ch.heigvd.protocol.Difficulty;
import ch.heigvd.protocol.MapSize;

import java.util.List;

/**
 * Created by lognaume on 5/30/17.
 */
public class GameSettings {
    private List<Difficulty> difficulties;
    private List<MapSize> mapSizes;

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
}
