package ch.heigvd.protocol;

public class MapSize {

	private final int id;
	private final String name;
	private final int width;
	private final int height;
	
	public MapSize(int id, String name, int width, int height) {
		this.id = id;
		this.name = name;
		this.width = width;
		this.height = height;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
