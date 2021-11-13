package Model;

import View.Frame;

/**
 * Create the area with default setting and create the Frame
 * @author Damien
 *
 */
public class Main {

	private static int nbCity = 20;
	private static int nbAnt = nbCity;
	private static double pheromoneSensibility = 1; //constant a
	private static double distanceSensibility = 1; //constant b
	private static double pheromoneQuantity = 1; 
	private static double evaporationCoeff = 0.7;

	public static int nbIteration = 10;
	
	public static double highlighter = 1;
	
	public static void main(String[] args) {
		Area area = new Area(nbCity, nbAnt, pheromoneSensibility, distanceSensibility, pheromoneQuantity, evaporationCoeff, nbIteration, highlighter);

		new Frame(area);
	}

}
