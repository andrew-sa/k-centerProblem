package places;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import places.exceptions.DuplicatePlaceException;
import places.exceptions.IllegalPositionException;

public class City extends AlgorithmPlace {

	public City(double x, double y, double shapeSize, int xSize, int ySize)
	{
		super((x - shapeSize / 2), (y - shapeSize / 2), x, y, shapeSize, xSize, ySize);
		shape = new Ellipse2D.Double(super.getX(), super.getY(), shapeSize, shapeSize);
	}
	
	@Override
	public boolean equals(Object otherObject)
	{
		return super.equals(otherObject);
	}
	
	/**
	 * @return the shape
	 */
	public Ellipse2D.Double getShape() {
		return shape;
	}

	/**
	 * @param shape the shape to set
	 */
	public void setShape(Ellipse2D.Double shape) {
		this.shape = shape;
	}
	
	public void changePosition(double x, double y, ArrayList<City> otherCities) throws IllegalPositionException, DuplicatePlaceException
	{
		double prevX = super.getX();
		double prevY = super.getY();
		super.setX(x - getShapeSize() / 2);
		super.setY(y - getShapeSize() / 2);
		if (!areGoodCoordinates())
		{
			super.setX(prevX);
			super.setY(prevY);
			throw new IllegalPositionException();
		}
		else if (otherCities.contains(this))
		{
			super.setX(prevX);
			super.setY(prevY);
			throw new DuplicatePlaceException();
		}
		else
		{
			shape = new Ellipse2D.Double(super.getX(), super.getY(), getShapeSize(), getShapeSize());
			super.setCenterX(x);
			super.setCenterY(y);
		}
	}

	private Ellipse2D.Double shape;

}
