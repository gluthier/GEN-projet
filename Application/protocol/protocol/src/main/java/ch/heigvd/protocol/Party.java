package ch.heigvd.protocol;

import org.json.JSONObject;

public class Party implements Sendable {

    public static enum FreeRole {
        skier("Skieur"),
        defender("Défendeur");

        private String value;

        public static FreeRole fromString(String in) {
            if (in.equals("skier")) {
                return skier;
            } else if (in.equals("defender")) {
                return FreeRole.defender;
            } else {
                //TODO throw exception ?
                return null;
            }
        }
        FreeRole(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private final int id;
    private final String playerName;
    private final Difficulty difficulty;
    private final MapSize mapSize;
    private final FreeRole freeRole;

    public Party(int id, String playerName, Difficulty difficulty, MapSize mapSize, FreeRole freeRole) {
        this.id = id;
        this.playerName = playerName;
        this.difficulty = difficulty;
        this.mapSize = mapSize;
        this.freeRole = freeRole;
    }

    public Party(JSONObject json) {
        this.id = json.getInt("id");
        this.playerName = json.getString("playerName");
        this.difficulty = new Difficulty(json.getJSONObject("difficulty"));
        this.mapSize = new MapSize(json.getJSONObject("mapSize"));
        System.out.println(json.toString());
        this.freeRole = FreeRole.fromString(json.getString("freeRole"));
    }

    public int getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public MapSize getMapSize() {
        return mapSize;
    }

    public FreeRole getFreeRole() {
        return freeRole;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("playerName", playerName);
        json.put("difficulty", difficulty.toJson());
        json.put("mapSize", mapSize.toJson());
        System.out.println("FREEROLE : " + freeRole);
        json.put("freeRole", freeRole);

        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Party party = (Party) o;

        if (getId() != party.getId()) {
            return false;
        }
        if (!getMapSize().equals(party.getMapSize())) {
            return false;
        }
        if (!getPlayerName().equals(party.getPlayerName())) {
            return false;
        }
        if (!getDifficulty().equals((party.getDifficulty()))) {
            return false;
        }
        return getFreeRole().equals(party.getFreeRole());
    }
}
