package Model;

import java.util.ArrayList;
import java.util.Observable;

import Resource.Ant;
import Resource.City;
import Resource.Road;
import View.Frame;

/**
 * This is the model
 * 
 * 
 * 
 * @author Damien
 *
 */
public class Area extends Observable{

	private int maxX;
	private int maxY;
	private double pheromoneSensibility; //constant a
	private double distanceSensibility; //constant b
	private double pheromoneQuantity;  //constant Q
	private double evaporationCoeff;
	
	private ArrayList<City> cities = new ArrayList<City>();
	private ArrayList<Road> map = new ArrayList<Road>();
	private ArrayList<Ant> colony = new ArrayList<Ant>();
	private ArrayList<Road> bestPath = new ArrayList<Road>();
	
	
	private double highlighter; //road's precision
	private int nbIteration;
	private boolean beginning; //using for don't display the first roads
	private boolean showBestPath = false;
	private boolean showCityName = false;
	
	
//   CONSTRUCTOR
	
	/**
	 * Create the Area
	 * 
	 * @param nbCity 
	 * @param nbAnt
	 * @param pheromoneSensibility
	 * @param distanceSensibility
	 * @param pheromoneQuantity
	 * @param evaporationCoeff
	 * @param nbIteration
	 * @param highlighter
	 */
	public Area(int nbCity, int nbAnt, double pheromoneSensibility, double distanceSensibility, double pheromoneQuantity, double evaporationCoeff, int nbIteration, double highlighter) {
		this.pheromoneSensibility = pheromoneSensibility;
		this.distanceSensibility = distanceSensibility;
		this.pheromoneQuantity = pheromoneQuantity;
		this.evaporationCoeff = evaporationCoeff;
		this.nbIteration = nbIteration;
		this.highlighter = highlighter;
				
		initCities(nbCity);
		
		initMap(nbCity);
		
		initColony(nbAnt);
		
	}	
	
	public Area() {
		initCities(0);
		initMap(0);		
		initColony(0);
	}

// INITIALISATION 
	
	
		private void initCities(int nbCity) {
			maxX = Frame.AREA_WIDTH;
			maxY = Frame.AREA_HEIGHT;
			cities = new ArrayList<City>();
			for(int i=0; i<nbCity; i++) {
				int x = (int) (Math.random() * maxX);
				int y = (int) (Math.random() * maxY);
				cities.add(new City(i, x, y));
			}			
		}
		
		private void initMap(int nbCity) {
			map.clear();
			for (int i=0; i<nbCity; i++) {
				for (int j=i; j<nbCity; j++) {
					if (j != i) {
						map.add(new Road(cities.get(i), cities.get(j)));
					}
				}
			}
			beginning = true;
		}
		
		private void initColony(int nbAnt) {
			colony.clear();
			for(int i=0; i<nbAnt; i++) {
				colony.add(new Ant());
			}
		}
	
//  FUNCTIONS
	
	/**
	 * each ant explore the area
	 * each ant have to find a path (road's list) for to connect each city one time
	 * after each ant have moved, update the roads' pheromone
	 * then, delete the memory ant for do the first step again
	 * do it nbIteration times
	 * update the view
	 */
	public void exploration() {
		beginning = false;
		for (int i = 0; i < colony.size(); i++) {
			Ant ant = colony.get(i);
			City startingCity = cities.get((int) (Math.random() * cities.size()));; //startingCity is the city where the ant is
			Road road = null;
				
			for (int j = 0; j < cities.size() - 1; j++) { //each ant have to travel each city one time
				road = getRoad(getPossibleRoad(ant, startingCity)); //get the road chosen by the ant between the road's list it can take
				ant.addRoad(road); //adding the road to ant's memory
					
				startingCity = goToNextCity(road, startingCity);//move the ant to the next city
			}
		}
		addingPheromone(); //add pheromone to each road
		clearAntMemory(); //delete all memory from ant for restart a new exploration
		
		foundBestPath(); // search the best 
		
		update();
		
	}
	
	
	/**
	 * 
	 * return to the ant all road it can take
	 * First step: take all road from the city
	 * Second step: remove the roads that lead to city already crossed by the ant
	 * 
	 * @param ant tested
	 * @param city where the ant are 
	 * @return list of road possible for the ant
	 */
	private ArrayList<Road> getPossibleRoad (Ant ant, City startingCity){
		ArrayList<Road> possibleRoad = new ArrayList<Road>();
		for (int i = 0; i < map.size(); i++) { //adding all road possible 
			Road road = map.get(i);
			if (road.containCity(startingCity)) {// if it's the same starting city
				possibleRoad.add(road);
			}
		}
		possibleRoad = ant.roadPossible(possibleRoad, startingCity); //delete all road go to city already traveled
		return possibleRoad;
	}
	
	
	/**
	 * return to the ant the choosen road among roadPossible
	 * 
	 * The choosen road is random, but more the road's pheromone rate is high and more the road's distance is close, the more chance the road will be chosen  
	 * 
	 * the probability is calculing by this formula:
	 *  ((Tij(p)^a)/(Tij(d)^b))/ sum(proba)
	 *  
	 *  where Tij is the road between city i and city j, and Tij(p) is his pheromone rate, Tij(d) is this distance
	 *  where a is the pheromoneSensibility of ants
	 *  where b is the distanceSensibility of ants
	 *  and sumProba is the sum of ((Tij(p)^a)/(Tij(d)^b)) for all road
	 * 
	 * @param roadPossible: list of road than the ant can take
	 * @return
	 */
	private Road getRoad(ArrayList<Road>roadPossible) {	
		Road chosenRoad = roadPossible.get(0);
		double sumProbaRoad = 0;
		for (int i = 0; i < roadPossible.size(); i++) { //sumProba
			Road road = roadPossible.get(i);
			double probaRoad = (Math.pow(road.getPheromone(), pheromoneSensibility))/(Math.pow(road.getLength(), distanceSensibility));
			sumProbaRoad += probaRoad;
		}

		double rand = Math.random();
		double probaRoadIncrement = 0;
		for(int i = 0; i < roadPossible.size(); i++) {
			Road road = roadPossible.get(i);
			double probaRoad = ((Math.pow(road.getPheromone(), pheromoneSensibility))/(Math.pow(road.getLength(), distanceSensibility)))/sumProbaRoad;
			probaRoadIncrement += probaRoad;
			if (rand < probaRoadIncrement) {
				chosenRoad = road;
				break;
			}
		}
		return chosenRoad;
	}
	
	//for each road, changing the quantity of pheromone
	private void addingPheromone() {
		for (int i = 0; i < map.size(); i++){
			Road road = map.get(i);
			double sumArcRenforcement = 0;
			for (int j = 0; j < colony.size(); j++){
				Ant ant = colony.get(j);
			    if(ant.antAsTraveledRoad(road)){ 
			    	sumArcRenforcement += pheromoneQuantity/ant.getLengthOfPath();
			    }
			}
			double pheromone = (road.getPheromone() * (1.0 - evaporationCoeff)) + sumArcRenforcement;
			map.get(i).setPheromone(pheromone);
		}
	}
	
	//each ant delete their path (memory)
	private void clearAntMemory() {
		for (int i = 0; i < colony.size(); i++) {
			colony.get(i).clearMemory();
		}
	}
	
	//return the city opposite than startingCity by the road
	private City goToNextCity (Road road, City startingCity) {
		City city = null;
		if (road.getCityA().getName() == startingCity.getName()) {
			city = road.getCityB();
		}
		else {
			city = road.getCityA();
		}
		return city;
	}
	
	//Found the best path	
	private void foundBestPath() {
		Ant ant = new Ant();
		
		Road bestRoad = map.get(0);
		for (int i = 1; i < map.size(); i++) {
			Road road = map.get(i);
			if (bestRoad.getPheromone() < road.getPheromone()) {
				bestRoad = road;
			}
		}
		
		City startingCity = bestRoad.getCityA();
		
		for (int i = 0; i < cities.size() -1; i++) {
			ArrayList<Road> listRoad = getPossibleRoad(ant, startingCity); //get the road chosen by the ant between the road's list it can take
			bestRoad = listRoad.get(0);
			for (int j = 1; j < listRoad.size(); j++) {
				Road road = listRoad.get(j);
				if (bestRoad.getPheromone() < road.getPheromone()) {
					bestRoad = road;
				}
			}
			ant.addRoad(bestRoad); //adding the road to ant's memory
			startingCity = goToNextCity(bestRoad, startingCity); //move to next city

		}	
		
		bestPath = ant.getPath();
	}

	
// GET -----> ControlPanel and AreaPanel
	
	//get list of city
	public ArrayList<City> getCites(){
		return cities;
	}
	
	//get list of ant
	public ArrayList<Ant> getColony(){
		return colony;
	}

	//get list of road
	public ArrayList<Road> getMap(){
		return map;
	}

	public double getPheromoneSensibility() {
		return pheromoneSensibility;
		
	}

	public double getDistanceSensibility() {
		return distanceSensibility;
		
	}

	public double getPheromoneQuantity() {
		return pheromoneQuantity;
		
	}

	public double getEvaporationCoeff() {
		return evaporationCoeff;
		
	}

	public int getNbIteration() {
		return nbIteration;
		
	}

	public double getHighlighter() {
		return highlighter;
	}
	
	public boolean getShowBestPath() {
		return showBestPath;
	}

	public boolean getShowCityName() {
		return showCityName;
	}

	public ArrayList<Road> getBestPath() {		
		return bestPath;
	}
	
	public double getLengthOfPath(ArrayList<Road> path) {
		int length = 0;
		for (int i = 0; i < path.size(); i++) {
			length += path.get(i).getLength();
		}
		return length;
	}
	
	public Area getArea() {
		return this;
	}
	
	//If it's the first time we create the are (or change area) we don't display the roads
	public boolean getBeginning() {
		return beginning;
	}
	
	//return the bigger value for the road's transparence 
	public double getBiggerPheromoneRate() {
		double biggerPheromoneRate = map.get(0).getPheromone();
		for (int i = 0; i < map.size(); i++) {
			double testRate = map.get(i).getPheromone();
			if (biggerPheromoneRate < testRate) {
				biggerPheromoneRate = testRate;
			}
		}
		return biggerPheromoneRate;
	}

	public String toString() {
		return "Area bp : "+getLengthOfPath(bestPath);
	}
	
// SET -----> ControlPanel
	
	//change area's setting (make a new area)
	public void setNewArea (ArrayList<City> cities, int nbCity, int nbAnt, double pheromoneSensibility, double distanceSensibility, double pheromoneQuantity,
			double evaporationCoeff, boolean changeCities, int nbIteration) {
		
		this.pheromoneSensibility = pheromoneSensibility;
		this.distanceSensibility = distanceSensibility;
		this.pheromoneQuantity = pheromoneQuantity;
		this.evaporationCoeff = evaporationCoeff;
		this.nbIteration = nbIteration;
		
		initColony(nbAnt);
		if (changeCities) {
			initCities(nbCity);
		}
		else {
			this.cities = cities;		
		}
		initMap(nbCity);
		update();
	}
	
	//change cities' structure
	public void makeNewMapWithSameSetting() {
		int nbAnt = colony.size();
		int nbCity = cities.size();
		initColony(nbAnt);
		initCities(nbCity);
		initMap(nbCity);		
		bestPath = new ArrayList<Road>();
		update();
	}
	
	public void setNbIteration(int nbIteration) {
		this.nbIteration = nbIteration;
	}
	
	public void setHighlither(double highlighter) {
		this.highlighter = highlighter;
		update();
	}

	//clear map
	public void resetMap() {		
		initMap(cities.size());
		bestPath = new ArrayList<Road>();
		update();
	}
	
	public void setShowBestPath (boolean b) {
		showBestPath = b;
		update();
	}

	public void setShowCityName (boolean b) {
		showCityName = b;
		update();
	}

	//give him a area structur (only colony and map needed)
	public void setArea(Area area) {
		colony = area.getColony();
		map = area.getMap();
		bestPath = area.getBestPath();
		update();
	}

// GRAPHE PART	
	
	//when this model changed
	public void update() {
		this.setChanged();
		this.notifyObservers();
	}
}
