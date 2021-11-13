package View;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Model.Area;

/**
 * This class control Area 
 * @author Damien
 *
 */
public class ControlPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Area area;
	
	private JTextField tfCity = new JTextField(),
	tfdAnt = new JTextField(),
	tfPheromoneSensibility = new JTextField(),
	tfDistanceSensibility = new JTextField(),
	tfPheromoneQuantity= new JTextField(),
	tfEvaporationCoeff = new JTextField(),
	tfIteration = new JTextField(),
	tfHighlighter = new JTextField(),
	tfConvergence = new JTextField();	
	
	JCheckBox checkboxBestRoad = new JCheckBox("Afficher le chemin optimal"),
			checkboxCityName = new JCheckBox("Afficher les numéros des villes");

	double convergence;

	public ControlPanel(Area area){
		convergence = 95;
		
		this.area = area;
		this.setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 5;
		gc.weighty = 11;

		gc.ipadx = gc.anchor = GridBagConstraints.WEST;

		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 2;
		add(createTitle("Nombre de villes:"), gc);
		gc.gridx = 2;
		gc.gridwidth = 3;
		gc.fill = GridBagConstraints.HORIZONTAL;
		tfCity.setText(Integer.toString(area.getCites().size()));
		add(tfCity, gc);

		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.CENTER;
		add(createTitle("Nombre de fourmis:"), gc);
		gc.gridx = 2;
		gc.gridwidth = 3;
		gc.fill = GridBagConstraints.HORIZONTAL;
		tfdAnt.setText(Integer.toString(area.getColony().size()));
		add(tfdAnt, gc);

		gc.gridx = 0;
		gc.gridy = 2;
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.CENTER;
		add(createTitle("Sensibilité aux phéromones (a):"), gc);
		gc.gridx = 2;
		gc.gridwidth = 3;
		gc.fill = GridBagConstraints.HORIZONTAL;
		tfPheromoneSensibility.setText(Double.toString(area.getPheromoneSensibility()));
		add(tfPheromoneSensibility, gc);

		gc.gridx = 0;
		gc.gridy = 3;
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.CENTER;
		add(createTitle("Sensibilité à la distance (b):"), gc);
		gc.gridx = 2;
		gc.gridwidth = 3;
		gc.fill = GridBagConstraints.HORIZONTAL;
		tfDistanceSensibility.setText(Double.toString(area.getDistanceSensibility()));
		add(tfDistanceSensibility, gc);

		gc.gridx = 0;
		gc.gridy = 4;
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.CENTER;
		add(createTitle("Quantité de phéromones (Q):"), gc);
		gc.gridx = 2;
		gc.gridwidth = 3;
		gc.fill = GridBagConstraints.HORIZONTAL;
		tfPheromoneQuantity.setText(Double.toString(area.getPheromoneQuantity()));
		add(tfPheromoneQuantity, gc);

		gc.gridx = 0;
		gc.gridy = 5;
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.CENTER;
		add(createTitle("Coefficent d'évaporation:"), gc);
		gc.gridx = 2;
		gc.gridwidth = 3;
		gc.fill = GridBagConstraints.HORIZONTAL;
		tfEvaporationCoeff.setText(Double.toString(area.getEvaporationCoeff()));
		add(tfEvaporationCoeff, gc);

		gc.gridx = 0;
		gc.gridy = 6;
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.CENTER;
		add(createTitle("Convergence en %:"), gc);
		gc.gridx = 2;
		gc.gridwidth = 3;
		gc.fill = GridBagConstraints.HORIZONTAL;
		tfConvergence.setText(Double.toString(convergence));
		add(tfConvergence, gc);

		gc.gridx = 0;
		gc.gridy = 7;
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.CENTER;
		add(createTitle("Nombre d'itérations:"), gc);
		gc.gridx = 2;
		gc.gridwidth = 3;
		gc.fill = GridBagConstraints.HORIZONTAL;
		tfIteration.setText(Integer.toString(area.getNbIteration()));
		add(tfIteration, gc);

		gc.gridx = 0;
		gc.gridy = 8;
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.CENTER;
		add(createTitle("Visibilité des chemins optimaux:"), gc);
		gc.gridx = 2;
		gc.gridwidth = 3;
		gc.fill = GridBagConstraints.HORIZONTAL;
		tfHighlighter.setText(Double.toString(area.getHighlighter()));
		tfHighlighter.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
					area.setHighlither(Double.parseDouble(tfHighlighter.getText()));
			  }
			  public void removeUpdate(DocumentEvent e) {}
			  public void insertUpdate(DocumentEvent e) {
					area.setHighlither(Double.parseDouble(tfHighlighter.getText()));
			  }

			});
		add(tfHighlighter, gc);

			    
	    
		gc.gridx = 0;
		gc.gridy = 9;		
		gc.gridwidth = 3;
		gc.fill = GridBagConstraints.WEST;

		add(checkboxBestRoad, gc);
	     
		checkboxBestRoad.setSelected(true);
	    area.setShowBestPath(true);
		checkboxBestRoad.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {
	    	    if (checkboxBestRoad.isSelected()) {
	    	    	area.setShowBestPath(true);     
	    	    } else {     
	    	    	area.setShowBestPath(false);
	    	    }
	        }
	      });

		gc.gridx = 0;
		gc.gridy = 10;		
		gc.gridwidth = 3;
		gc.fill = GridBagConstraints.WEST;

		add(checkboxCityName, gc);
	     
		checkboxCityName.setSelected(false);
	    
		checkboxCityName.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {
	    	    if (checkboxCityName.isSelected()) {
	    	    	area.setShowCityName(true);     
	    	    } else {     
	    	    	area.setShowCityName(false);
	    	    }
	        }
	      });

		
	    
		gc.gridx = 0;
		gc.gridy = 11;		
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.WEST;
		JButton buttonChangeMap = new JButton("NEW MAP");
		buttonChangeMap.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	newMap();
	        	}
	    });			
		add(buttonChangeMap, gc);

		gc.gridx = 1;
		gc.fill = GridBagConstraints.WEST;
		JButton buttonReset = new JButton("RESET MAP");
		buttonReset.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	resetAction();
	        	}
	    });			
		add(buttonReset, gc);

		gc.gridx = 0; 
		gc.gridy = 12; 
		gc.fill = GridBagConstraints.EAST;
		add(createExploreButton("EXPLORER"), gc);

		gc.gridx = 1; 
		gc.fill = GridBagConstraints.CENTER;

	    int timerDelay = 100;
	    final Timer timer = new Timer(timerDelay , new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	        	 exploreAction();
	        	 }
	      });

	    
	    JButton buttonPlay = new JButton("PLAY");
	    final ButtonModel bModel = buttonPlay.getModel();
	    bModel.addChangeListener(new ChangeListener() {

	       @Override
	       public void stateChanged(ChangeEvent cEvt) {
	          if (bModel.isPressed() && !timer.isRunning()) {
	             timer.start();
	          } else if (!bModel.isPressed() && timer.isRunning()) {
	             timer.stop();
	          }
	       }
	    });

		add(buttonPlay, gc);
	
		gc.gridx = 0;
		gc.gridy = 13;		
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.WEST;
		JButton buttonConvergence = new JButton("Convergence");
		buttonConvergence.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	convergenceAction();
	        	}
	    });			
		add(buttonConvergence, gc);

		
	}
	
	private JLabel createTitle(String titre) {
		JLabel label = new JLabel(titre);
		label.setFont(new Font("Arial", Font.BOLD, 12));
		return label;
	}


	private JButton createExploreButton(String titre) {
		JButton button = new JButton(titre);
		button.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	exploreAction();
	        }
	    });			
	    return button;
	}


// ACTION 	
	private void newMap() {
		int nbCity = area.getCites().size();
		if (!tfCity.getText().isEmpty()) {
			nbCity = Integer.parseInt(tfCity.getText());
		}
		int nbAnt = area.getColony().size();
		if (!tfdAnt.getText().isEmpty()) {
			nbAnt = Integer.parseInt(tfdAnt.getText());
		}
		double pheromoneSensibility = area.getPheromoneSensibility();
		if (!tfPheromoneSensibility.getText().isEmpty()) {
			pheromoneSensibility = Double.parseDouble(tfPheromoneSensibility.getText());
		}
		double distanceSensibility = area.getDistanceSensibility();
		if (!tfDistanceSensibility.getText().isEmpty()) {
			distanceSensibility = Double.parseDouble(tfDistanceSensibility.getText());
		}
		double pheromoneQuantity = area.getPheromoneQuantity();
		if (!tfPheromoneQuantity.getText().isEmpty()) {
			pheromoneQuantity = Double.parseDouble(tfPheromoneQuantity.getText());
		}
		double evaporationCoeff = area.getEvaporationCoeff();
		if (!tfEvaporationCoeff.getText().isEmpty()) {
			evaporationCoeff = Double.parseDouble(tfEvaporationCoeff.getText());
		}
		int nbIteration = area.getNbIteration();
		if (!tfIteration.getText().isEmpty()) {
			nbIteration = Integer.parseInt(tfIteration.getText());
		}
		
		boolean changeCities = true;
		if (nbCity == area.getCites().size()) {
			changeCities = false;
		}
		
		if (changedSetting(nbCity, nbAnt, pheromoneSensibility, distanceSensibility, pheromoneQuantity, evaporationCoeff, nbIteration)) {
			area.setNewArea(area.getCites(), nbCity, nbAnt, pheromoneSensibility, distanceSensibility, pheromoneQuantity, evaporationCoeff, changeCities, nbIteration);
		}
		else {
			area.makeNewMapWithSameSetting();
		}
	}

	/**
	 * This method do area.explore nbIteration times
	 * 
	 * Each time, it look of if the news structure of area give a better bestPasth
	 * if Yes, it memorise the area structure in bestArea
	 * 
	 * At the end, it give to area the bestArea 
	 * 
	 */
	private void exploreAction() {
		int nbIteration = area.getNbIteration();
		if (!tfIteration.getText().isEmpty()) {
			nbIteration = Integer.parseInt(tfIteration.getText());
			area.setNbIteration(nbIteration);
		}
		
		double bestLength = -1;
		Area bestArea = new Area();
		for (int i = 0; i < nbIteration; i++) {
			area.exploration();
			double length = area.getLengthOfPath(area.getBestPath());
			if (i == 0) { //for first round
				bestLength = length;
				bestArea.setArea(area);
			}
			else { 
				if (bestLength > length) {
					bestLength = length;
					bestArea.setArea(area);
				}
			}
		}
		area.setArea(bestArea);
	}
	
	/**
	 * This methode do area.explore until it found a convergence's path 
	 * 
	 * It verify if the next path converge to the last path and verify if it's same for the Iterations following path
	 * 
	 */
	private void convergenceAction() {
		if (verifyIfIsDouble(tfConvergence.getText())) {
			convergence = Double.parseDouble(tfConvergence.getText());
			double calculConvergence = 0;
			double lengthPath = area.getLengthOfPath(area.getBestPath());
			int verify = Integer.parseInt(tfIteration.getText());
			int timer = 4000;
			while (verify > 0 && timer > 0) {
				area.exploration();
				if (lengthPath != 0) {
					double newLengthPath = area.getLengthOfPath(area.getBestPath());
					if (newLengthPath > lengthPath) {
						calculConvergence = (lengthPath * 100)/newLengthPath;
					}
					else {
						calculConvergence = (newLengthPath * 100)/lengthPath;
					}
					if (calculConvergence >= convergence) {
						verify --;
					}
					else {
						verify = Integer.parseInt(tfIteration.getText());
					}
					lengthPath = newLengthPath;
				}
				else {
					lengthPath = area.getLengthOfPath(area.getBestPath());
				}
				timer --;
			}
			if (timer == 0) {
				JOptionPane.showMessageDialog(null, "Pas de réponse");
			}
		}
	}
	
	private boolean verifyIfIsDouble(String nb) {
	    try {
	         Double.parseDouble(nb);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
		return true;
	}
	
	
	private void resetAction() {
		area.resetMap();
	}
	
	private boolean changedSetting(int nbCity, int nbAnt, double pheromoneSensibility, double distanceSensibility, double pheromoneQuantity, double evaporationCoeff, int nbIteration) {
		boolean changed = true;
		if (nbCity == area.getCites().size() &&
				nbAnt == area.getColony().size() &&
				pheromoneSensibility == area.getPheromoneSensibility() &&
				distanceSensibility == area.getDistanceSensibility() &&
				pheromoneQuantity == area.getPheromoneQuantity() &&
				evaporationCoeff == area.getEvaporationCoeff() &&
				nbIteration == area.getNbIteration()) {
			changed = false;
		}
		return changed;
	}

}
