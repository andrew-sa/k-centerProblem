package gui.frames;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

import gui.components.ButtonPanel;
import gui.components.DrawingArea;

public class MainFrame extends JFrame {
	
//	public MainFrame(double shapeSize, ComputingFrame computingFrame) /* OLD CONSTRUCTOR */
//	{
////		this.computingFrame = computingFrame;
//		DrawingArea drawingArea = new DrawingArea(shapeSize, computingFrame);
//		ButtonPanel buttonPanel = new ButtonPanel(drawingArea);
//		drawingArea.addListener(buttonPanel);
////		drawingArea.addListener(computingFrame);
//		getContentPane().add(drawingArea, BorderLayout.CENTER);
//		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
//	}

	public MainFrame(double shapeSize, ComputingFrame computingFrame, Color cityColor, Color centerColor,
			Color firstCenterColor, Color userCenterColor)
	{
		DrawingArea drawingArea = new DrawingArea(shapeSize, computingFrame, cityColor, centerColor, firstCenterColor, userCenterColor);
		ButtonPanel buttonPanel = new ButtonPanel(drawingArea);
		drawingArea.addListener(buttonPanel);
//		drawingArea.addListener(computingFrame);
		getContentPane().add(drawingArea, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}
	
//	private static final double CITY_SIZE = 10;
//	private ComputingFrame computingFrame;

}
