package gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import custom.events.ActiveChangeCentersNumberListener;
import gui.frames.ComputingFrame;
import places.City;
import places.UserCenter;
import places.exceptions.DuplicatePlaceException;
import places.exceptions.IllegalPositionException;

/**
 * This panel represents the drawing area where the user can place cities and centers.
 * @author Andrea Mogavero
 *
 */
public class DrawingArea extends JPanel {

	public DrawingArea(double citySize, ComputingFrame computingFrame, Color cityColor, Color centerColor,
			Color firstCenterColor, Color userCenterColor)
	{
		this.shapeSize = citySize;
		this.computingFrame = computingFrame;
		//
		setBackground(new Color(215, 215, 193));
		setBorder(BorderFactory.createLineBorder(Color.black));
		this.cityColor = cityColor;
		this.centerColor = centerColor;
		this.firstCenterColor = firstCenterColor;
		setCityNumberColor();
		this.userCenterColor = userCenterColor;
		userCenterNumberColor = new Color(0, 51, 0);
		
		X_SIZE = 0;
		Y_SIZE = 0;
		
		cities = new ArrayList<>();
		citiesCenters = new ArrayList<>();
		indexCityToMove = -1;
		draggedCity = null;
		
		userCenters = new ArrayList<>();
		numberRemaningUserCenter = 0;
		indexUserCenterToMove = -1;
		draggedUserCenter = null;
		
		vectorsDistance = new HashMap<>();
		vectorsDistanceUserCenter = new HashMap<>();
		
		lowerBoundOptimalValue = -1;
//		goodGap = -1;
		
		listeners = new ArrayList<>();
		
		setAlgorithmMouseListeners();
	}
	
	/**
	 * This method sets the color of the number of the cities according to shape size.
	 */
	private void setCityNumberColor()
	{
		if (shapeSize >= MINIMUM_SIZE_FOR_INTERNAL_NUMBER)
		{
			cityNumberColor = new Color(255, 255, 0);
			constantForNumberBasepointX = 2 * shapeSize / 9;
			constantForNumberBasepointY = 8 * shapeSize / 9;
		}
		else
		{
			cityNumberColor = new Color(0, 0, 0);
			constantForNumberBasepointX = 0;
			constantForNumberBasepointY = 0;
		}
	}
	
	/**
	 * This method clears the drawing area and the text area deleting each city and each center. 
	 */
	public void reset()
	{
		computingFrame.clear();
		
		cities = new ArrayList<>();
		citiesCenters = new ArrayList<>();
		indexCityToMove = -1;
		draggedCity = null;
		vectorsDistance = new HashMap<>();
		
		userCenters = new ArrayList<>();
		numberRemaningUserCenter = 0;
		indexUserCenterToMove = -1;
		draggedUserCenter = null;
		vectorsDistanceUserCenter = new HashMap<>();
		
		deleteMouseAndMouseMotionListeners();
		setAlgorithmMouseListeners();
		repaint();
	}
	
	/**
	 * This method deletes algorithm and user solution but doesn't delete/modify the cities.
	 */
	public void deleteSolution()
	{
		citiesCenters = new ArrayList<>();
		indexCityToMove = -1;
		draggedCity = null;
		
		userCenters = new ArrayList<>();
		numberRemaningUserCenter = 0;
		indexUserCenterToMove = -1;
		draggedUserCenter = null;
		vectorsDistanceUserCenter = new HashMap<>();
		
		lowerBoundOptimalValue = -1;
//		goodGap = -1;
		
		deleteMouseAndMouseMotionListeners();
		repaint();
	}
	
	/**
	 * This method deletes every mouse listeners and mouse motion listeners of the drawing area.
	 */
	private void deleteMouseAndMouseMotionListeners()
	{
		MouseListener[] mls = (MouseListener[]) (getListeners(MouseListener.class));
		for (int i = 0; i < mls.length; i++)
		{
			removeMouseListener(mls[i]);
		}
		
		MouseMotionListener[] mmls = (MouseMotionListener[]) (getListeners(MouseMotionListener.class));
		for (int i = 0; i < mmls.length; i++)
		{
			removeMouseMotionListener(mmls[i]);
		}
	}
	
	
	/* SETTER LISTENER */
	/**
	 * This method sets the mouse listener and mouse motion listener to draw the initial cities.
	 */
	private void setAlgorithmMouseListeners()
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
//				System.out.println(event.getX() + ", " + event.getY());
				double x = event.getX();
				double y = event.getY();
				City city = new City(x, y, shapeSize, X_SIZE, Y_SIZE);
				try
				{
					addNewCity(city);
				}
				catch (IllegalPositionException e)
				{
//					e.printStackTrace();
					computingFrame.writeln(" > Illegal position.");
				}
				catch (DuplicatePlaceException e)
				{
//					e.printStackTrace();
					computingFrame.writeln(" > In this position a city is already present.");
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
				getCityToMove(x, y);
			}

			@Override
			public void mouseReleased(MouseEvent event)
			{
				int x = event.getX();
				int y = event.getY();
				System.out.println("RELEASED HERE: " + x + ", " + y);
				try
				{
					moveCityTo(x, y);
				}
				catch (IllegalPositionException e)
				{
//					e.printStackTrace();
					computingFrame.writeln(" > Illegal position.");
					repaint();
				}
				catch (DuplicatePlaceException e)
				{
//					e.printStackTrace();
					computingFrame.writeln(" > In this position a city is already present.");
					repaint();
				}
				finally
				{
					indexCityToMove = -1;
				}
			}
			
		});
		
		addMouseMotionListener(new MouseMotionListener()
		{
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent event)
			{
				if (indexCityToMove != -1)
				{
					int x = event.getX();
					int y = event.getY();
					System.out.println("DRAGGED HERE: " + x + ", " + y);
					draggedCity = new City(x, y, shapeSize, X_SIZE, Y_SIZE);
					repaint();
				}
			}
		});
	}
	
	/**
	 * This method prepares the drawing area for insertion of user centers.
	 * @param k the number of user centers to be place.
	 */
	public void setDrawingAreaForUserCenters(int k)
	{
		deleteMouseAndMouseMotionListeners();
		
		numberRemaningUserCenter = k;
		indexUserCenterToMove = -1;
		computingFrame.writeln(" > Place your " + numberRemaningUserCenter + " centers");
		setUserCenterMouseListeners();
	}
	
	/**
	 * This method sets the mouse listener and mouse motion listener to draw the user solution.
	 */
	private void setUserCenterMouseListeners()
	{
		addMouseListener(new MouseListener()
		{

			@Override
			public void mouseClicked(MouseEvent event)
			{
				if (numberRemaningUserCenter > 0)
				{
//					System.out.println("User center inserted, remaining " + numberRemaningUserCenter + " centers");
					int x = event.getX();
					int y = event.getY();
					UserCenter newUserCenter = new UserCenter(x, y, shapeSize, X_SIZE, Y_SIZE);
					addNewUserCenter(newUserCenter);
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
				getUserCenterToMove(x, y);
			}

			@Override
			public void mouseReleased(MouseEvent event)
			{
				int x = event.getX();
				int y = event.getY();
				System.out.println("RELEASED HERE: " + x + ", " + y);
				try
				{
					moveUserCenterTo(x, y);
				}
				catch (IllegalPositionException e)
				{
//					e.printStackTrace();
					computingFrame.writeln(" > Illegal position.");
					repaint();
				}
				catch (DuplicatePlaceException e)
				{
//					e.printStackTrace();
					computingFrame.writeln(" > In this position a user center is already present.");
					repaint();
				}
				finally
				{
					indexUserCenterToMove = -1;
				}
			}
	
		});
		
		addMouseMotionListener(new MouseMotionListener()
		{
		
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent event)
			{
				if (indexUserCenterToMove != -1)
				{
					int x = event.getX();
					int y = event.getY();
					System.out.println("DRAGGED HERE: " + x + ", " + y);
					draggedUserCenter = new UserCenter(x, y, shapeSize, X_SIZE, Y_SIZE);
					repaint();
				}
			}
		
		});
	}
	
	/**
	 * After user solution, this method sets the mouse listener and mouse motion listener to draw new cities or to move existing cities and centers.
	 */
	private void setMoveCitiesAndCentersMouseListener()
	{
		addMouseListener(new MouseListener()
		{
		
			@Override
			public void mouseClicked(MouseEvent event) //Only city
			{
				double x = event.getX();
				double y = event.getY();
				if (!occupiedByUserCenter(x, y))
				{
					City city = new City(x, y, shapeSize, X_SIZE, Y_SIZE);
					try
					{
						addNewCity(city);
						placeAlgorithmCenters(citiesCenters.size());
						calculateUserSolutionValue();
						computingFrame.writeln(" > Try to move centers to improve your solution.\n" + "     Otherwise, you can move the cities or add others.");
					}
					catch (IllegalPositionException e)
					{
//						e.printStackTrace();
						computingFrame.writeln(" > Illegal position.");
					}
					catch (DuplicatePlaceException e)
					{
//						e.printStackTrace();
						computingFrame.writeln(" > In this position a city is already present.");
					}
				}
			}
			
			@Override
			public void mousePressed(MouseEvent event)
			{
				int x = event.getX();
				int y = event.getY();
				System.out.println("PRESSED HERE: " + x + ", " + y);
				boolean foundUserCenterToMove = getUserCenterToMove(x, y);
				if (!foundUserCenterToMove)
				{
					getCityToMove(x, y);
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
					try
					{
						moveUserCenterTo(x, y);
					}
					catch (IllegalPositionException e)
					{
//						e.printStackTrace();
						computingFrame.writeln(" > Illegal position.");
						repaint();
					}
					catch (DuplicatePlaceException e)
					{
//						e.printStackTrace();
						computingFrame.writeln(" > In this position a user center is already present.");
						repaint();
					}
					finally
					{
						indexUserCenterToMove = -1;
					}
				}
				else if (indexCityToMove >= 0)
				{
					try
					{
						moveCityTo(x, y);
						placeAlgorithmCenters(citiesCenters.size());
						calculateUserSolutionValue();
						computingFrame.writeln(" > Try to move centers to improve your solution.\n" + "     Otherwise, you can move the cities or add others.");
					}
					catch (IllegalPositionException e)
					{
//						e.printStackTrace();
						computingFrame.writeln(" > Illegal position.");
						repaint();
					}
					catch (DuplicatePlaceException e)
					{
//						e.printStackTrace();
						computingFrame.writeln(" > In this position a city is already present.");
						repaint();
					}
					finally
					{
						indexCityToMove = -1;
					}
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		addMouseMotionListener(new MouseMotionListener()
		{
		
			@Override
			public void mouseMoved(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent event)
			{
				if (indexCityToMove != -1)
				{
					int x = event.getX();
					int y = event.getY();
					System.out.println("DRAGGED HERE: " + x + ", " + y);
					draggedCity = new City(x, y, shapeSize, X_SIZE, Y_SIZE);
					repaint();
				}
				else if (indexUserCenterToMove != -1)
				{
					int x = event.getX();
					int y = event.getY();
					System.out.println("DRAGGED HERE: " + x + ", " + y);
					draggedUserCenter = new UserCenter(x, y, shapeSize, X_SIZE, Y_SIZE);
					repaint();
				}
			}
			
		});
	}
	
	private void setDeleteCitiesListener()
	{
		addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent event) //Only city
			{
				double x = event.getX();
				double y = event.getY();
				if (getCityToMove(x, y)) {
					for (int i=0; i<cities.size(); i++) {
						vectorsDistance.get(cities.get(i)).remove(indexCityToMove);
					}
					vectorsDistance.remove(cities.get(indexCityToMove));
					cities.remove(indexCityToMove);
					for (int i=0; i<userCenters.size(); i++) {
						vectorsDistanceUserCenter.get(userCenters.get(i)).remove(indexCityToMove);
					}
					indexCityToMove=-1;
					placeAlgorithmCenters(citiesCenters.size());
					calculateUserSolutionValue();
					computingFrame.writeln(" > City removed.");
				}
						
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void setDrawingDeleteCities()
	{
		deleteMouseAndMouseMotionListeners();
		setDeleteCitiesListener();
	}
	
	public void unsetDrawingDeleteCities()
	{
		deleteMouseAndMouseMotionListeners();
		setMoveCitiesAndCentersMouseListener();
	}
	//END SETTERS LISTENER
	
	/* METHODS USED BY MOUSE LISTENERS */
	/**
	 * This method adds a new city.
	 * @param city the city to add.
	 * @throws IllegalPositionException if the city steps outside of the drawing area.
	 * @throws DuplicatePlaceException if the city overlap another city.
	 */
	private void addNewCity(City city) throws IllegalPositionException, DuplicatePlaceException
	{
		if (!city.areGoodCoordinates())
		{
			throw new IllegalPositionException();
		}
		else if (cities.contains(city))
		{
			throw new DuplicatePlaceException();
		}
		else
		{
			cities.add(city);
			calculateNewestDistances(city);
			repaint();
		}
	}
	
	/**
	 * This method checks and gets the city that covers the specified coordinates.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @return true if there is a city that covers the specified coordinates, otherwise false.
	 */
	private boolean getCityToMove(double x, double y)
	{
		boolean found = false;
		indexCityToMove = -1;
		int i = 0;
		while (i < cities.size() && indexCityToMove == -1)
		{
			if (cities.get(i).getShape().contains(x, y))
			{
				indexCityToMove = i;
				found = true;
			}
			i++;
		}
		return found;
	}
	
	/**
	 * This method move the selected city at the specified coordinates.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @throws IllegalPositionException if the city steps outside of the drawing area.
	 * @throws DuplicatePlaceException if the city overlap another city.
	 */
	private void moveCityTo(double x, double y) throws IllegalPositionException, DuplicatePlaceException
	{
		draggedCity = null;
		if (indexCityToMove >= 0)
		{
			ArrayList<City> otherCities = (ArrayList<City>) cities.clone();
			otherCities.remove(indexCityToMove);
			cities.get(indexCityToMove).changePosition(x, y, otherCities);
			System.out.println("BEFORE MOVE, vectors distance size: " + vectorsDistance.size());
			calculateMovedCityDistances();
			System.out.println("AFTER MOVE, vectors distance size: " + vectorsDistance.size());
			repaint();
		}
	}
	
	/**
	 * This method calculates the distances between the new city and the other cities.
	 * @param newCity the new city.
	 */
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
		if (userCenters.size() > 0)
		{
			calculateDistancesBetweenNewCityAndUserCenters(newCity);
		}
	}
	
	/**
	 * This method calculates the distances between the new city and the user centers.
	 * @param newCity the new city.
	 */
	private void calculateDistancesBetweenNewCityAndUserCenters(City newCity)
	{
		double x1 = newCity.getCenterX();
		double y1 = newCity.getCenterY();
		for (UserCenter userCenter: userCenters)
		{
			double x2 = userCenter.getCenterX();
			double y2 = userCenter.getCenterY();
			double hypotenuse = Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
			vectorsDistanceUserCenter.get(userCenter).add(hypotenuse);
		}
	}
	
	/**
	 * This method updates the distances between the moved city and the other cities.
	 */
	private void calculateMovedCityDistances()
	{
		City movedCity = cities.get(indexCityToMove);
		ArrayList<Double> movedCityDist = new ArrayList<>();
		double x1 = movedCity.getCenterX();
		double y1 = movedCity.getCenterY();
		for (City c: cities)
		{
			if (!c.equals(movedCity))
			{
				double x2 = c.getCenterX();
				double y2 = c.getCenterY();
				double hypotenuse = Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
				movedCityDist.add(hypotenuse);
				vectorsDistance.get(c).remove(indexCityToMove);
				vectorsDistance.get(c).add(indexCityToMove, hypotenuse);
			}
			else
			{
				movedCityDist.add(Double.valueOf(0));
			}
		}
		vectorsDistance.put(movedCity, movedCityDist);
		System.out.println(vectorsDistance);
		if (userCenters.size() > 0)
		{
			calculateDistancesBetweenMovedCityAndUserCenters(movedCity, indexCityToMove);
		}
	}
	
	/**
	 * This method updates the distances between the moved city and the user centers.
	 * @param movedCity the moved city.
	 * @param indexCityToMove the index of the moved city in the ArrayList cities.
	 */
	private void calculateDistancesBetweenMovedCityAndUserCenters(City movedCity, int indexCityToMove)
	{
		double x1 = movedCity.getCenterX();
		double y1 = movedCity.getCenterY();
		for (UserCenter userCenter: userCenters)
		{
			double x2 = userCenter.getCenterX();
			double y2 = userCenter.getCenterY();
			double hypotenuse = Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
			vectorsDistanceUserCenter.get(userCenter).remove(indexCityToMove);
			vectorsDistanceUserCenter.get(userCenter).add(indexCityToMove, hypotenuse);
		}
	}
	
//	private double calculateGoodGap(double lowerBoundOptimalValue)
//	{
//		return 2 * lowerBoundOptimalValue / 7;
//	}
	
	/**
	 * This method adds a new user center.
	 * @param newUserCenter the new user center to add
	 */
	private void addNewUserCenter(UserCenter newUserCenter)
	{
		double x1 = newUserCenter.getCenterX();
		double y1 = newUserCenter.getCenterY();
		if (!newUserCenter.areGoodCoordinates())
		{
			computingFrame.writeln(" > Ilegal position. Place another " + numberRemaningUserCenter + " centers.");
		}
		else if (userCenters.contains(newUserCenter))
		{
			computingFrame.writeln(" > In this position a user center is already present. Place another " + numberRemaningUserCenter + " centers.");
		}
		else
		{
			numberRemaningUserCenter--;
			userCenters.add(newUserCenter);
			ArrayList<Double> distancesFromNewCenter = new ArrayList<>();
			for (int i = 0; i < cities.size(); i++)
			{
				double x2 = cities.get(i).getCenterX();
				double y2 = cities.get(i).getCenterY();
				double hypotenuse = Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
				distancesFromNewCenter.add(hypotenuse);
			}
			vectorsDistanceUserCenter.put(newUserCenter, distancesFromNewCenter);
			repaint();
			
			if (numberRemaningUserCenter == 0)
			{
				computingFrame.writeln();
//				computingFrame.writeln("A \"good\" solution is a solution that moves away from the lower bound at most 2/7 of the value of this last one.");
				computingFrame.writeln();
				calculateUserSolutionValue();
//				computingFrame.writeln();
				computingFrame.writeln(" > Try to move centers to improve your solution.\n" + "     Otherwise, you can move the cities or add others.");
//				computingFrame.writeln();
//				computingFrame.writeln();
				deleteMouseAndMouseMotionListeners();
				setMoveCitiesAndCentersMouseListener();
				activeChangeCentersNumber();
			}
			else
			{
				computingFrame.writeln(" > Place another " + numberRemaningUserCenter + " centers.");
			}
		}
	}
	
	/**
	 * This method checks and gets the user center that covers the specified coordinates.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @return true if there is a user center that covers the specified coordinates, otherwise false.
	 */
	private boolean getUserCenterToMove(double x, double y)
	{
		boolean found = false;
		indexUserCenterToMove = -1;
		int i = 0;
		while (i < userCenters.size() && indexUserCenterToMove == -1)
		{
			if (userCenters.get(i).getShape().contains(x, y))
			{
				indexUserCenterToMove = i;
				found = true;
			}
			i++;
		}
		return found;
	}
	
	/**
	 * This method move the selected user center at the specified coordinates.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @throws IllegalPositionException if the user center steps outside of the drawing area.
	 * @throws DuplicatePlaceException if the user center overlap another user center.
	 */
	private void moveUserCenterTo(double x, double y) throws IllegalPositionException, DuplicatePlaceException
	{
		draggedUserCenter = null;
		if (indexUserCenterToMove >= 0)
		{
			ArrayList<UserCenter> otherUserCenters = (ArrayList<UserCenter>) userCenters.clone();
			otherUserCenters.remove(indexUserCenterToMove);
			userCenters.get(indexUserCenterToMove).changePosition(x, y, otherUserCenters);
			System.out.println("BEFORE MOVE, vectors distance size: " + vectorsDistanceUserCenter.size());
			UserCenter movedUserCenter = userCenters.get(indexUserCenterToMove);
			double x1 = movedUserCenter.getCenterX();
			double y1 = movedUserCenter.getCenterY();
			ArrayList<Double> distancesFromMovedCenter = new ArrayList<>();
			for (int i = 0; i < cities.size(); i++)
			{
				double x2 = cities.get(i).getCenterX();
				double y2 = cities.get(i).getCenterY();
				double hypotenuse = Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
				distancesFromMovedCenter.add(hypotenuse);
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
	
	/**
	 * This method checks if there is a user center that covers the specified coordinates.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @return true if there is, otherwise false.
	 */
	private boolean occupiedByUserCenter(double x, double y)
	{
		Boolean isOccupied = getUserCenterToMove(x, y);
		indexUserCenterToMove = -1;
		return isOccupied;
	}
	//END METHODS USED BY MOUSE LISTENERS
	
	/* CALCULATE ALGORITHM SOLUTION */
	/**
	 * This method calculates the 2-approximate solution for k centers.
	 * @param k the number of centers to place.
	 */
	public void placeAlgorithmCenters(int k)
	{
//		deleteMouseAndMouseMotionListeners();
		
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
			if (!citiesCenters.isEmpty())
			{
				citiesCenters = new ArrayList<>();
			}
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
	
	/* CALCULTE USER SOLUTION */
	/**
	 * This method calculates the user solution according to the user centers placed.
	 */
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
			ArrayList<Double> distances = vectorsDistanceUserCenter.get(userCenter);
			for (int i = 0; i < distances.size(); i++)
			{
				if (Double.isNaN(minCitiesDistances[i]) || distances.get(i) < minCitiesDistances[i])
				{
					minCitiesDistances[i] = distances.get(i);
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
//		computingFrame.writeln();
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
			g2.setColor(cityNumberColor);
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
			g2.setColor(userCenterNumberColor);
			g2.setFont(NUMBER_FONT);
			g2.drawString(String.valueOf(i + 1), (int) (uc.getX() + constantForNumberBasepointX), (int) (uc.getY() + constantForNumberBasepointY));
		}
		
		
		
		if (draggedCity != null)
		{
			g2.setColor(cityColor);
			g2.fill(draggedCity.getShape());
			
			/* Draw city number */
			g2.setColor(cityNumberColor);
			g2.setFont(NUMBER_FONT);
			g2.drawString(String.valueOf(indexCityToMove + 1), (int) (draggedCity.getX() + constantForNumberBasepointX), (int) (draggedCity.getY() + constantForNumberBasepointY));
			
			draggedCity = null;
		}
		
		if (draggedUserCenter != null)
		{
			g2.setColor(userCenterColor);
			g2.fill(draggedUserCenter.getShape());
			
			/* Draw user center number */
			g2.setColor(userCenterNumberColor);
			g2.setFont(NUMBER_FONT);
			g2.drawString(String.valueOf(indexUserCenterToMove + 1), (int) (draggedUserCenter.getX() + constantForNumberBasepointX), (int) (draggedUserCenter.getY() + constantForNumberBasepointY));
			
			draggedUserCenter = null;
		}
		
	}
	
	/**
	 * 
	 * @return the number of cities placed.
	 */
	public int getCitiesNumber()
	{
		return cities.size();
	}
	
	/* EVENT: active change centers number */
	/**
	 * This method adds a new listener for the ActiveChangeCentersNumber event.
	 * @param toAdd the listener to add.
	 */
	public void addListener(ActiveChangeCentersNumberListener toAdd)
	{
		listeners.add(toAdd);
	}
	
	/**
	 * This method removes the specified listener from the listeners of the ActiveChangeCentersNumber event.
	 * @param toRemove the listener to remove.
	 */
	public void removeListener(ActiveChangeCentersNumberListener toRemove)
	{
		listeners.remove(toRemove);
	}
	
	/**
	 * This method trigger the event.
	 */
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
	private Color cityNumberColor;
	private Color userCenterColor;
	private Color userCenterNumberColor;
	
	private double shapeSize;
//	private double halfCitySize;
	
	private ArrayList<City> cities;
	private ArrayList<City> citiesCenters;
//	private int numCities;
	private int indexCityToMove;
	private City draggedCity;
	
	private ArrayList<UserCenter> userCenters;
	private int numberRemaningUserCenter;
	private int indexUserCenterToMove;
	private UserCenter draggedUserCenter;
	
	private HashMap<City, ArrayList<Double>> vectorsDistance;
	private HashMap<UserCenter, ArrayList<Double>> vectorsDistanceUserCenter;
	
	private double lowerBoundOptimalValue;
//	private double goodGap;
	
	private ArrayList<ActiveChangeCentersNumberListener> listeners;

}
