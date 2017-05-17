package ch.heigvd.protocol;

import org.json.JSONObject;

public abstract class Sendable {
	Sendable(JSONObject json) ;
	//TODO find a way
	abstract JSONObject toJson();
}
