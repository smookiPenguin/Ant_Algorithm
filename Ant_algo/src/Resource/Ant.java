package Resource;

import java.util.ArrayList;

/**
 * Ant have a path and now all cities it travelled
 * 
 * 
 * @author Damien
 *
 */
public class Ant {

	private ArrayList<Road> path;
	private ArrayList<City> citiesTravelled;

//	CONSTRUCTOR	
	public Ant () {
		path = new ArrayList<Road>();
		citiesTravelled = new ArrayList<City>();
	}
	
	
// GET	
	//return all road without ant's roads are already travelled
	public ArrayList<Road> roadPossible(ArrayList<Road> listRoad, City startingCity){
		ArrayList<Road> roadPossible = new ArrayList<Road>();
		
		for (int i=0; i<listRoad.size(); i++) {
			if (!listRoad.get(i).containArrivalCityInList(citiesTravelled, startingCity)) {
				roadPossible.add(listRoad.get(i));
			}
		}		
		return roadPossible;
	}

	//return true if the ant have already take the road
	public boolean antAsTraveledRoad(Road road) {
		boolean roadTraveled = false;
		for (int i = 0; i < path.size(); i++) {
			if (path.get(i).sameRoad(road)) {
				roadTraveled = true;
				break;
			}
		}
		return roadTraveled;
	}
	
	public double getLengthOfPath() {
		double lenth = 0;
		for (int i = 0; i < path.size(); i++) {
			lenth += path.get(i).getLength();
		}
		return lenth;
	}
	
	public ArrayList<Road> getPath(){
		return path;
	}
	
	//return the path (1->2, 2->3, etc)
	public String toString() {
		String path = new String();
		for (int i = 0; i < this.path.size(); i++) {
			path += this.path +" / ";
		}
		return path;
	}
	
// SET	
	public void addRoad(Road road) {
		path.add(road);
		addCity(road);
	}
		
	//adding the next city to ant's memory
	private void addCity(Road road) {
		boolean canAddCityA = true, canAddCityB = true;
		for (int i = 0; i < citiesTravelled.size(); i++) {
			if (road.getCityA().getName() == citiesTravelled.get(i).getName()) {
				canAddCityA = false;
			}
			if (road.getCityB().getName() == citiesTravelled.get(i).getName()) {
				canAddCityB = false;
			}
		}
		if (canAddCityA) citiesTravelled.add(road.getCityA());
		if (canAddCityB) citiesTravelled.add(road.getCityB());
	}

	public void clearMemory() {
		clearCitiesTravelled();
		clearPath();
	}
		
	private void clearCitiesTravelled() {
		citiesTravelled.clear();
	}
		
	private void clearPath() {
		path.clear();
	}

}
