package Resource;

import java.util.ArrayList;

/**
 * A road is between two City (cityA, cityB)
 * @author Damien
 *
 */
public class Road {

	private City cityA, cityB;
	private double length;
	private double pheromone;
	private int coord [][] = new int[2][2];

	
//	CONSTUCTOR	
	public Road(City cityA, City cityB) {
		this.cityA = cityA;
		this.cityB = cityB;
		coord[0][0] = cityA.getX();
		coord[0][1] = cityA.getY();
		coord[1][0] = cityB.getX();
		coord[1][1] = cityB.getY();
		length = Math.sqrt( Math.pow(cityB.getX() - cityA.getX(), 2) + Math.pow(cityB.getY() - cityA.getY(), 2));
		pheromone = 10;
	}

// GET
	public double getLength() {
		return length;
	}
	
	public boolean containCity(City city) {
		boolean contain = false;
		if (cityA.getName() == city.getName() || cityB.getName() == city.getName()) {
			contain = true;
		}
		return contain;
	}

	public boolean containCityBInList(ArrayList<City> cities) {
		boolean contain = false;
		for (int i=0; i<cities.size(); i++) {
			if (cityB.getName() == cities.get(i).getName()) {
				contain = true;
			}			
		}
		return contain;
	}
	
	public boolean containArrivalCityInList(ArrayList<City> cities, City startingCity) {
		boolean contain = false;
		
		for (int i = 0; i < cities.size(); i++) {
			City cityArrival = cities.get(i);
			if((cityA.getName() == cityArrival.getName() || cityB.getName() == cityArrival.getName())
					&& cityArrival.getName() != startingCity.getName()){
				contain = true; 
				break;
			}
		}
		
		return contain;
	}

	public boolean sameRoad(Road road) {
		boolean sameRoad = false;
		if ((cityA.getName() == road.getCityA().getName() && cityB.getName() == road.getCityB().getName()) ||
				(cityA.getName() == road.getCityB().getName() && cityB.getName() == road.getCityA().getName()) ) {
			sameRoad = true;
		}
		return sameRoad;
	}
		
	public City getCityA() {
		return cityA;
	}
	
	public City getCityB() {
		return cityB;
	}
	
	public double getPheromone() {
		return pheromone;
	}
	
	public int[][] getCoord(){
		return coord;
	}
	
	public void setPheromone(double pheromone) {
		this.pheromone = pheromone;
	}
	
	public String toString() {
		return cityA.getName() + " -> " + cityB.getName();
	}
}
