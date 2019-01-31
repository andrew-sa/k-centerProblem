package gui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class LegendPanel extends JPanel {
	
	public LegendPanel(double shapeSize, Color cityColor, Color centerColor, Color firstCenterColor,
			Color userCenterColor)
	{
		this.shapeSize = shapeSize;
		this.cityColor = cityColor;
		this.centerColor = centerColor;
		this.firstCenterColor = firstCenterColor;
		this.userCenterColor = userCenterColor;
		this.textColor = new Color(0, 0, 0);
	}
	
	public LegendPanel(Color cityColor, Color centerColor, Color firstCenterColor,
			Color userCenterColor)
	{
		this.shapeSize = DEFAULT_SHAPE_SIZE;
		this.cityColor = cityColor;
		this.centerColor = centerColor;
		this.firstCenterColor = firstCenterColor;
		this.userCenterColor = userCenterColor;
		this.textColor = new Color(0, 0, 0);
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		final int space = 15;
		final int fontSize = g2.getFont().getSize();
		String title = "Legend".toUpperCase();
		g2.setColor(textColor);
		g2.drawString(title, space, fontSize + space);
		g2.drawLine(space, fontSize + space + 2, space + getFontMetrics(getFont()).stringWidth(title), fontSize + space + 2);
		
		Ellipse2D.Double city = new Ellipse2D.Double(space, fontSize + 3 * space, shapeSize, shapeSize);
		g2.setColor(cityColor);
		g2.fill(city);
		g2.setColor(textColor);
		g2.drawString("= CITIES", (int) (city.getX() + shapeSize + space / 2), (int) (city.getY() + (7 * shapeSize / 8)));
		
		Ellipse2D.Double firstCityCenter = new Ellipse2D.Double(space, city.getY() + shapeSize + 2 * space, shapeSize, shapeSize);
		g2.setColor(firstCenterColor);
		g2.fill(firstCityCenter);
		g2.setColor(textColor);
		g2.drawString("= FIRST CITY SELECTED AS CENTER BY ALGO", (int) (firstCityCenter.getX() + shapeSize + space / 2), (int) (firstCityCenter.getY() + (7 * shapeSize / 8)));
		
		Ellipse2D.Double cityCenter = new Ellipse2D.Double(space, firstCityCenter.getY() + shapeSize + 2 * space, shapeSize, shapeSize);
		g2.setColor(centerColor);
		g2.fill(cityCenter);
		g2.setColor(textColor);
		g2.drawString("= CITIES SELECTED AS CENTER BY ALGO", (int) (cityCenter.getX() + shapeSize + space / 2), (int) (cityCenter.getY() + (7 * shapeSize / 8)));
		
		Rectangle2D.Double userCenter = new Rectangle2D.Double(space, cityCenter.getY() + shapeSize + 2 * space, shapeSize, shapeSize);
		g2.setColor(userCenterColor);
		g2.fill(userCenter);
		g2.setColor(textColor);
		g2.drawString("= CENTERS OF YOUR SOLUTION", (int) (userCenter.getX() + shapeSize + space / 2), (int) (userCenter.getY() + (7 * shapeSize / 8)));
	}
	
	private static final double DEFAULT_SHAPE_SIZE = 14;
	private double shapeSize;
	private Color cityColor;
	private Color centerColor;
	private Color firstCenterColor;
	private Color userCenterColor;
	private Color textColor;

}
