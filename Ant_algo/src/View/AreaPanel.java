package View;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import Model.Area;
import Resource.City;
import Resource.Road;
/**
 * This class display Area
 * @author Damien
 *
 */

public class AreaPanel extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static int CITY_WIDTH = 20,
			ROAD_WIDTH = 5,
			CITY_TEXT = CITY_WIDTH/2,
			ROAD_TEXT = CITY_WIDTH;

	
	
	public static final Color CITY = new Color(0,0,0),
			TEXT_COLOR_CITY = new Color(255, 255, 255),
			ROAD =  new Color(255, 0, 0),
			BEST_ROAD = new Color(0, 0, 255),
			BACKGROUND = new Color(255, 255, 255),
			TEXT = CITY;

	private Area area;
	private Label label = new Label();
	
	
	public AreaPanel (Area area) {
		this.area = area;
		area.addObserver(this);
		setLayout(new BorderLayout());
	}

	public void paint(Graphics g) {
		super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
		ArrayList<City> cities = area.getCites();
		ArrayList<Road> map = area.getMap();
		if (!area.getBeginning()) {
			paintRoad(map, g2);
			if (area.getShowBestPath()) {
				ArrayList<Road> bestPath = area.getBestPath();
				paintBestRoad(bestPath, g2);
			}
		}
		paintCities(cities, g2);
	}
	
	private void paintRoad(ArrayList<Road> map, Graphics2D g2){
		double biggerPheromoneRate = area.getBiggerPheromoneRate();
		double highlighter = area.getHighlighter();
			
		for (int i = 0; i < map.size(); i++) {
			Road road = map.get(i);
			int coord[][] = road.getCoord();
				
			//the bigger rate worth 255, and the less worth 0 is the function ax^2+b
			int transparence = (int) (Math.pow((road.getPheromone()),highlighter) * (255 / Math.pow((biggerPheromoneRate),highlighter)));
			Color crosssedColor = new Color(ROAD.getRed(), ROAD.getGreen(), ROAD.getBlue(), transparence);
			g2.setColor(crosssedColor);
			drawLine(coord, g2);
		}		
	}
	
	private void paintBestRoad(ArrayList<Road> bestPath, Graphics2D g2) {
		g2.setColor(BEST_ROAD);
		for (int i = 0; i < bestPath.size(); i++) {
			Road road = bestPath.get(i);
			int coord[][] = road.getCoord();	
			drawLine(coord, g2);
		}	
	}
	
	private void drawLine(int coord[][], Graphics2D g2) {
		g2.setStroke(new BasicStroke(ROAD_WIDTH));
		g2.drawLine(coord[0][0]+CITY_WIDTH/2,
				coord[0][1]+CITY_WIDTH/2,
				coord[1][0]+CITY_WIDTH/2,
				coord[1][1]+CITY_WIDTH/2);							
	}
	
	
	private void paintCities(ArrayList<City> cities, Graphics2D g2) {
		Font font = new Font("TimesRoman ",Font.BOLD, CITY_TEXT);
		for (int i = 0; i < cities.size(); i++) { 
			City city = cities.get(i);
			int x = city.getX();
			int y = city.getY();
			g2.setColor(CITY);
			g2.fillOval(x, y,CITY_WIDTH,CITY_WIDTH);
			g2.setColor(TEXT_COLOR_CITY);
			g2.setFont(font);
			if (area.getShowCityName()) {
				g2.drawString(city.toString(), x+CITY_WIDTH/3, y+CITY_WIDTH-CITY_WIDTH/3);
			}
		}
	}
	
	private void displayLenghtOfBestRoad() {
		Font font = new Font("TimesRoman ",Font.BOLD, ROAD_TEXT);
		String s = "Longeur du chemin optimal : " + Integer.toString((int)area.getLengthOfPath(area.getBestPath()));
		label.setText(s);
		label.setFont(font);
		add(label,BorderLayout.SOUTH);
		//revalidate();

	}
	
	@Override
	public void update(Observable o, Object arg) {
		repaint();
		displayLenghtOfBestRoad();
	}
}
