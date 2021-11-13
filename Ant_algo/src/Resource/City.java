package Resource;

/**
 * 
 * @author Damien
 *
 */
public class City {
	
	private int name;
	private int coord [] = new int[2];
	
	public City(int name, int x, int y) {
		this.name=name;
		coord[0] = x;
		coord[1] = y;
	}
	
	
	public int getName() {
		return name;
	}
	
	public int getX() {
		return coord[0];
	}

	public int getY() {
		return coord[1];
	}

	public String toString() {
		return Integer.toString(name);
	}
}
