package main;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;

import gui.frames.ComputingFrame;
import gui.frames.MainFrame;

public class Main {

//	public static void main(String[] args)
//	{
//		DrawingArea drawingArea = new DrawingArea(CITY_SIZE);
//		ButtonPanel buttonPanel = new ButtonPanel(drawingArea);
////		ComputingPanel computingPanel = new ComputingPanel();
//		JFrame.setDefaultLookAndFeelDecorated(true);
//		JFrame frame = new JFrame("Place the cities");
////		DrawingArea drawingArea = new DrawingArea(CITY_SIZE, frame.getContentPane().getWidth(), frame.getContentPane().getHeight());
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().add(drawingArea, BorderLayout.CENTER);
////		frame.getContentPane().add(computingPanel, BorderLayout.EAST);
//		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
////		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
//		frame.setSize(1400, 700);
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
//	}
	
	public static void main(String[] args)
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		double screenWidth = screenSize.getWidth();
//		double screenHeight = screenSize.getHeight();
//		System.out.println(screenWidth + "," + screenHeight);
		
		Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		double screenWidth = r.getWidth();
		double screenHeight = r.getHeight();
		System.out.println(screenWidth + "," + screenHeight);
		
//		int mainFrameLocationX = (int) (screenWidth - (MAIN_FRAME_WIDTH + COMPUTING_FRAME_WIDTH));
//		if (mainFrameLocationX < 0)
//		{
//			mainFrameLocationX = 0;
//		}
//		
//		int framesLocationY = (int) ((screenHeight - MAIN_FRAME_HEIGHT) / 2);
//		if (framesLocationY < 0)
//		{
//			framesLocationY = 0;
//		}
		int computingFrameWidth = (int) (screenWidth / 5);
		int mainFrameWidth = (int) (screenWidth - computingFrameWidth);
		int framesHeight = (int) screenHeight;
		
//		ComputingFrame computingFrame = new ComputingFrame(SHAPE_SIZE, CITY_COLOR, CENTER_COLOR, FIRST_CENTER_COLOR, USER_CENTER_COLOR);
		ComputingFrame computingFrame = new ComputingFrame(CITY_COLOR, CENTER_COLOR, FIRST_CENTER_COLOR, USER_CENTER_COLOR);
		MainFrame mainFrame = new MainFrame(SHAPE_SIZE, computingFrame, CITY_COLOR, CENTER_COLOR, FIRST_CENTER_COLOR, USER_CENTER_COLOR);
		
		mainFrame.setTitle("k-center problem");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		mainFrame.setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
//		mainFrame.setLocation(mainFrameLocationX, framesLocationY);
		mainFrame.setSize(mainFrameWidth, framesHeight);
		mainFrame.setLocation(0, 0);
//		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		
		computingFrame.setTitle("Guidelines & Results");
		computingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		computingFrame.setSize(COMPUTING_FRAME_WIDTH, COMPUTING_FRAME_HEIGHT);
//		computingFrame.setLocation(mainFrameLocationX + MAIN_FRAME_WIDTH, framesLocationY);
		computingFrame.setSize(computingFrameWidth, framesHeight);
		computingFrame.setLocation(mainFrameWidth, 0);
//		computingFrame.setLocationRelativeTo(null);
		computingFrame.setVisible(true);
	}
	
	private static final double SHAPE_SIZE = 14;
//	private static final int MAIN_FRAME_WIDTH = 1000;
//	private static final int MAIN_FRAME_HEIGHT = 700;
//	private static final int COMPUTING_FRAME_WIDTH = 400;
//	private static final int COMPUTING_FRAME_HEIGHT = 700;
	private static final Color CITY_COLOR = new Color(0, 51, 102);;
	private static final Color CENTER_COLOR = new Color(179, 0, 0);
	private static final Color FIRST_CENTER_COLOR = new Color(255, 26, 26);
	private static final Color USER_CENTER_COLOR = new Color(255, 255, 0);

}
