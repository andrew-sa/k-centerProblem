package gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import custom.event.ActiveChangeCentersNumberListener;
import gui.frames.ComputingFrame;
import places.City;
import places.UserCenter;

public class DrawingArea extends JPanel {

//	public DrawingArea(double citySize, ComputingFrame computingFrame) /* OLD CONSTRUCTOR */
//	{
//		this.shapeSize = citySize;
//		this.computingFrame = computingFrame;
//		
////		System.out.println(this.getWidth());
////		setBackground(new Color(245, 245, 240)); 	//OLD
////		setBackground(new Color(255, 255, 255)); 	//OLD
//		setBackground(new Color(215, 215, 193));
//		setBorder(BorderFactory.createLineBorder(Color.black));
//		cityColor = new Color(0, 51, 102);
////		centerColor = new Color(204, 0, 0); 	//OLD
//		centerColor = new Color(179, 0, 0);
////		firstCenterColor = new Color(255, 102, 102); 	//OLD
//		firstCenterColor = new Color(255, 26, 26);
//		setNumberColor();
//		userCenterColor = new Color(255, 255, 0);
//		numberUserCenterColor = new Color(0, 51, 0);
//		
//		X_SIZE = 0;
//		Y_SIZE = 0;
//		
////		this.citySize = citySize;
////		halfCitySize = this.citySize / 2;
//		cities = new ArrayList<>();
//		citiesCenters = new ArrayList<>();
////		numCities = 0;
////		calculateHalfCitySize(this.citySize);
//		userCenters = new ArrayList<>();
//		numberRemaningUserCenter = 0;
//		indexUserCenterToMove = -1;
//		
//		vectorsDistance = new HashMap<>();
//		vectorsDistanceUserCenter = new HashMap<>();
//		
//		lowerBoundOptimalValue = -1;
////		goodGap = -1;
//		
//		listeners = new ArrayList<>();
//		
//		setAlgorithmMouseListener();
//	}
	
	public DrawingArea(double citySize, ComputingFrame computingFrame, Color cityColor, Color centerColor,
			Color firstCenterColor, Color userCenterColor)
	{
		this.shapeSize = citySize;
		this.computingFrame = computingFrame;
		
		setBackground(new Color(215, 215, 193));
		setBorder(BorderFactory.createLineBorder(Color.black));
		this.cityColor = cityColor;
		this.centerColor = centerColor;
		this.firstCenterColor = firstCenterColor;
		setNumberColor();
		this.userCenterColor = userCenterColor;
		numberUserCenterColor = new Color(0, 51, 0);
		
		X_SIZE = 0;
		Y_SIZE = 0;
		
		cities = new ArrayList<>();
		citiesCenters = new ArrayList<>();
		userCenters = new ArrayList<>();
		numberRemaningUserCenter = 0;
		indexUserCenterToMove = -1;
		
		vectorsDistance = new HashMap<>();
		vectorsDistanceUserCenter = new HashMap<>();
		
		lowerBoundOptimalValue = -1;
//		goodGap = -1;
		
		listeners = new ArrayList<>();
		
		setAlgorithmMouseListener();
	}
	
	private void setNumberColor()
	{
		if (shapeSize >= MINIMUM_SIZE_FOR_INTERNAL_NUMBER)
		{
			numberCityColor = new Color(255, 255, 0);
			constantForNumberBasepointX = 2 * shapeSize / 9;
			constantForNumberBasepointY = 8 * shapeSize / 9;
		}
		else
		{
			numberCityColor = new Color(0, 0, 0);
			constantForNumberBasepointX = 0;
			constantForNumberBasepointY = 0;
		}
	}
	
	public void reset()
	{
		computingFrame.clear();
		
		cities = new ArrayList<>();
		citiesCenters = new ArrayList<>();
		vectorsDistance = new HashMap<>();
		
		userCenters = new ArrayList<>();
		numberRemaningUserCenter = 0;
		indexUserCenterToMove = -1;
		vectorsDistanceUserCenter = new HashMap<>();
		
		deleteMouseListeners();
		setAlgorithmMouseListener();
		repaint();
	}
	
	public void deleteSolution()
	{
		citiesCenters = new ArrayList<>();
		
		userCenters = new ArrayList<>();
		numberRemaningUserCenter = 0;
		indexUserCenterToMove = -1;
		vectorsDistanceUserCenter = new HashMap<>();
		
		lowerBoundOptimalValue = -1;
//		goodGap = -1;
		
		deleteMouseListeners();
		repaint();
	}
	
	private void deleteMouseListeners()
	{
		MouseListener[] mls = (MouseListener[]) (getListeners(MouseListener.class));
		for (int i = 0; i < mls.length; i++)
		{
			removeMouseListener(mls[i]);
		}
	}
	
	private void setAlgorithmMouseListener()
	{
		lowerBoundOptimalValue = -1;
//		goodGap = -1;
		computingFrame.writeln(" > Place cities..");
		computingFrame.writeln();
		addMouseListener(new MouseListener()
				{
			
					@Override
					public void mouseClicked(MouseEvent event)
					{
//						System.out.println(event.getX() + ", " + event.getY());
//						City city = new City(event.getX(), event.getY(), citySize, AREA_SIZE);
						double x = event.getX();
						double y = event.getY();
						City city = new City(x, y, shapeSize, X_SIZE, Y_SIZE);
						if (city.areGoodCoordinates() && !cities.contains(city))
						{
							cities.add(city);
							calculateNewestDistances(city);
							repaint();
						}
						
					}

					private void calculateNewestDistances(City newCity)
					{
						ArrayList<Double> newCityDist = new ArrayList<>();
						double x1 = newCity.getCenterX();
						double y1 = newCity.getCenterY();
						for (City c: cities)
						{
							double x2 = c.getCenterX();
							double y2 = c.getCenterY();
							double hypotenuse = Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
							newCityDist.add(hypotenuse);
							if (!c.equals(newCity))
							{
								vectorsDistance.get(c).add(hypotenuse);
							}
						}
						vectorsDistance.put(newCity, newCityDist);
						System.out.println(vectorsDistance);
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
				});
	}
	
	public void placeAlgorithmCenters(int k)
	{
		deleteMouseListeners();
		
		computingFrame.writeln();
		computingFrame.writeln();
		computingFrame.writeSeparator();
		computingFrame.writeln();
		computingFrame.writeln();
		computingFrame.writeln();
		computingFrame.writeln("SOLUTIONS FOR k=" + k);
		computingFrame.writeln();
		
		int n = getCitiesNumber();
		if (k == n)
		{
			citiesCenters = cities;
			System.out.println("Alghorithm solution: " + 0);
			computingFrame.writeln("Alghorithm solution: " + 0);
			computingFrame.writeln("Lower bound optimal value: " + 0);
			computingFrame.writeln();
			computingFrame.writeln();
			lowerBoundOptimalValue = 0;
//			goodGap = 5;
		}
		else
		{
			ArrayList<City> remainingCities = (ArrayList<City>) cities.clone();
			Random random = new Random();
			int indexFirstCenter = random.nextInt(n);
			System.out.println("Index of fisrt Center-City: " + indexFirstCenter + ", City: " + cities.get(indexFirstCenter));
			citiesCenters.add(cities.get(indexFirstCenter));
			remainingCities.remove(cities.get(indexFirstCenter));
//			System.out.println("CITIES:" + cities.size());
//			System.out.println("REMAINING CITIES:" + remainingCities.size());
			k--;
			while (k >= 0)
			{
				System.out.println("Iteration");
				City cityAtMaxDistance = null;
				double maxDistance = 0;
				for (City city: remainingCities)
				{
					ArrayList<Double> cityDistances = vectorsDistance.get(city);
					double nearestCenterDistance = Double.NaN;
					for (City center: citiesCenters)
					{
						int centerIndex = cities.indexOf(center);
						double distance = cityDistances.get(centerIndex);
						if (Double.isNaN(nearestCenterDistance))
						{
							nearestCenterDistance = distance;
						}
						else if (distance < nearestCenterDistance)
						{
							nearestCenterDistance = distance;
							System.out.println("City: " + city + ", nearestCenterDistance=" + nearestCenterDistance);
						}
					}
					if (nearestCenterDistance > maxDistance)
					{
						maxDistance = nearestCenterDistance;
						cityAtMaxDistance = city;
						System.out.println("City: " + cityAtMaxDistance + ", maxDistance=" + maxDistance);
					}
				}
				if (k > 0) //Add new center in the most distance city from the other center
				{
					citiesCenters.add(cityAtMaxDistance);
					remainingCities.remove(cityAtMaxDistance);
				}
				else //if k == 0 	/* Calculate algorithm solution value */
				{
					System.out.println("Alghorithm solution: " + maxDistance);
					computingFrame.writeln("Alghorithm solution: " + maxDistance);
					lowerBoundOptimalValue = maxDistance / 2;
//					goodGap = calculateGoodGap(lowerBoundOptimalValue);
					computingFrame.writeln("Lower bound optimal value: " + lowerBoundOptimalValue);
					computingFrame.writeln();
					computingFrame.writeln();
				}
				k--;
			}
		}
		repaint();
	}
	
//	private double calculateGoodGap(double lowerBoundOptimalValue)
//	{
//		return 2 * lowerBoundOptimalValue / 7;
//	}
	
	public void setDrawingAreaForUserCenters(int k)
	{
		numberRemaningUserCenter = k;
		indexUserCenterToMove = -1;
		computingFrame.writeln(" > Place your " + numberRemaningUserCenter + " centers");
		setUserMouseListener();
	}
	
	private void setUserMouseListener()
	{
		addMouseListener(new MouseListener()
				{

					@Override
					public void mouseClicked(MouseEvent event)
					{
						if (numberRemaningUserCenter > 0)
						{
							System.out.println("User center inserted, remaining " + numberRemaningUserCenter + " centers");
							int x = event.getX();
							int y = event.getY();
							UserCenter newUserCenter = new UserCenter(x, y, shapeSize, X_SIZE, Y_SIZE);
							double x1 = newUserCenter.getCenterX();
							double y1 = newUserCenter.getCenterY();
							if (newUserCenter.areGoodCoordinates() && !userCenters.contains(newUserCenter))
							{
								numberRemaningUserCenter--;
								userCenters.add(newUserCenter);
								double[] distancesFromNewCenter = new double[cities.size()];
								for (int i = 0; i < cities.size(); i++)
								{
									double x2 = cities.get(i).getCenterX();
									double y2 = cities.get(i).getCenterY();
									double hypotenuse = Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
									distancesFromNewCenter[i] = hypotenuse;
								}
								vectorsDistanceUserCenter.put(newUserCenter, distancesFromNewCenter);
								repaint();
							}
							if (numberRemaningUserCenter == 0)
							{
								computingFrame.writeln();
//								computingFrame.writeln("A \"good\" solution is a solution that moves away from the lower bound at most 2/7 of the value of this last one.");
								computingFrame.writeln();
								calculateUserSolutionValue();
//								computingFrame.writeln();
								computingFrame.writeln(" > Try to move centers to improve your solution");
								computingFrame.writeln();
//								computingFrame.writeln();
								activeChangeCentersNumber();
							}
							else
							{
								computingFrame.writeln(" > Place another " + numberRemaningUserCenter + " centers");
							}
						}
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent event)
					{
						int x = event.getX();
						int y = event.getY();
						System.out.println("PRESSED HERE: " + x + ", " + y);
//						boolean found = false;
						indexUserCenterToMove = -1;
						int i = 0;
						while (i < userCenters.size() && indexUserCenterToMove == -1)
						{
							if (userCenters.get(i).getShape().contains(x, y))
							{
								indexUserCenterToMove = i;
							}
							i++;
						}
					}

					@Override
					public void mouseReleased(MouseEvent event)
					{
						int x = event.getX();
						int y = event.getY();
						System.out.println("RELEASED HERE: " + x + ", " + y);
						if (indexUserCenterToMove >= 0)
						{
							ArrayList<UserCenter> otherUserCenters = (ArrayList<UserCenter>) userCenters.clone();
							otherUserCenters.remove(indexUserCenterToMove);
							boolean isChanged = userCenters.get(indexUserCenterToMove).changePosition(x, y, otherUserCenters);
							if (isChanged)
							{
								System.out.println("BEFORE MOVE, vectors distance size: " + vectorsDistanceUserCenter.size());
								UserCenter movedUserCenter = userCenters.get(indexUserCenterToMove);
								double x1 = movedUserCenter.getCenterX();
								double y1 = movedUserCenter.getCenterY();
								double[] distancesFromMovedCenter = new double[cities.size()];
								for (int i = 0; i < cities.size(); i++)
								{
									double x2 = cities.get(i).getCenterX();
									double y2 = cities.get(i).getCenterY();
									double hypotenuse = Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
									distancesFromMovedCenter[i] = hypotenuse;
								}
								vectorsDistanceUserCenter.put(movedUserCenter, distancesFromMovedCenter);
								System.out.println("AFTER MOVE, vectors distance size: " + vectorsDistanceUserCenter.size());
								repaint();
								if (numberRemaningUserCenter == 0)
								{
									calculateUserSolutionValue();
								}
							}
						}
					}
			
				});
	}
	
	private void calculateUserSolutionValue()
	{
		double solution = Double.NaN;
		double[] minCitiesDistances = new double[cities.size()];
		for (int i = 0; i < minCitiesDistances.length; i++)
		{
			minCitiesDistances[i] = Double.NaN;
		}
		int indexMaxCityDist = -1;
		for (UserCenter userCenter: userCenters)
		{
			double[] distances = vectorsDistanceUserCenter.get(userCenter);
			for (int i = 0; i < distances.length; i++)
			{
				if (Double.isNaN(minCitiesDistances[i]) || distances[i] < minCitiesDistances[i])
				{
					minCitiesDistances[i] = distances[i];
				}
			}
		}
		for (int i = 0; i < minCitiesDistances.length; i++)
		{
			if (Double.isNaN(solution) || minCitiesDistances[i] > solution)
			{
				solution = minCitiesDistances[i];
				indexMaxCityDist = i;
			}
		}
		System.out.println("User solution: " + solution);
		computingFrame.writeln("Your solution: " + solution);
		computingFrame.writeln();
//		if (solution <= lowerBoundOptimalValue + goodGap)
//		{
//			computingFrame.writeln("GREAT! You found a \"good\" solution.");
//			computingFrame.writeln();
//		}
	}

//	@Override
//	public Dimension getPreferredSize()
//	{
//		return isPreferredSizeSet() ? super.getPreferredSize() : new Dimension(X_SIZE, Y_SIZE);
//	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		System.out.println("BEFORE: " + X_SIZE + ", " + Y_SIZE);
		if (X_SIZE == 0 && Y_SIZE == 0)
		{
			X_SIZE = getWidth();
			Y_SIZE = getHeight();
		}
		System.out.println("AFTER: " + X_SIZE + ", " + Y_SIZE);
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
//		g2.setColor(cityColor);
		for (int i = 0; i < cities.size(); i++)
		{
			City c = cities.get(i);
			int index = -1;
			if ((index = citiesCenters.indexOf(c)) >= 0) //The city is a center
			{
				if (index == 0) //This is the first selected center
				{
					g2.setColor(firstCenterColor);
				}
				else
				{
					g2.setColor(centerColor);
				}
			}
			else //The city isn't a center
			{
				g2.setColor(cityColor);
			}
			g2.fill(c.getShape());
//			Ellipse2D.Double city = new Ellipse2D.Double(c.getX(), c.getY(), citySize, citySize);
//			g2.fill(city);
//			g2.draw(city);
			
			/* Draw city number */
			g2.setColor(numberCityColor);
			g2.setFont(NUMBER_FONT);
			g2.drawString(String.valueOf(i + 1), (int) (c.getX() + constantForNumberBasepointX), (int) (c.getY() + constantForNumberBasepointY));
		}
		
//		g2.setColor(centerColor);
//		for (int i = 0; i < citiesCenters.size(); i++)
//		{
//			City c = citiesCenters.get(i);
//			if (i == 0)
//			{
//				g2.setColor(firstCenterColor);
//			}
//			else
//			{
//				g2.setColor(centerColor);
//			}
//			Ellipse2D.Double center = new Ellipse2D.Double(c.getX(), c.getY(), citySize, citySize);
//			g2.fill(center);
//		}
		
		
		for (int i = 0; i < userCenters.size(); i++)
		{
			UserCenter uc = userCenters.get(i);
			g2.setColor(userCenterColor);
			g2.fill(uc.getShape());
			
			/* Draw user center number */
			g2.setColor(numberUserCenterColor);
			g2.setFont(NUMBER_FONT);
			g2.drawString(String.valueOf(i + 1), (int) (uc.getX() + constantForNumberBasepointX), (int) (uc.getY() + constantForNumberBasepointY));
		}
		
	}
	
	public int getCitiesNumber()
	{
		return cities.size();
	}
	
	/* EVENT: active change centers number */
	public void addListener(ActiveChangeCentersNumberListener toAdd)
	{
		listeners.add(toAdd);
	}
	
	public void removeListener(ActiveChangeCentersNumberListener toRemove)
	{
		listeners.remove(toRemove);
	}
	
	public void activeChangeCentersNumber()
	{
		for (ActiveChangeCentersNumberListener ls: listeners)
		{
			ls.activeChangeCentersNumber();
		}
	} //end EVENT

	private static final double MINIMUM_SIZE_FOR_INTERNAL_NUMBER = 14;
	private static final Font NUMBER_FONT = new Font("TimesRoman", Font.PLAIN, 10);
	private double constantForNumberBasepointX;
	private double constantForNumberBasepointY;
	
	private ComputingFrame computingFrame;
	
	private int X_SIZE;
	private int Y_SIZE;
	private Color cityColor;
	private Color centerColor;
	private Color firstCenterColor;
	private Color numberCityColor;
	private Color userCenterColor;
	private Color numberUserCenterColor;
	
	private double shapeSize;
//	private double halfCitySize;
	
	private ArrayList<City> cities;
	private ArrayList<City> citiesCenters;
//	private int numCities;
	
	private ArrayList<UserCenter> userCenters;
	private int numberRemaningUserCenter;
	private int indexUserCenterToMove;
	
	private HashMap<City, ArrayList<Double>> vectorsDistance;
	private HashMap<UserCenter, double[]> vectorsDistanceUserCenter;
	
	private double lowerBoundOptimalValue;
//	private double goodGap;
	
	private ArrayList<ActiveChangeCentersNumberListener> listeners;

}
