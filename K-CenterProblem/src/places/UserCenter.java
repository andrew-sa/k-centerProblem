package places;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import places.exceptions.DuplicatePlaceException;
import places.exceptions.IllegalPositionException;

/**
 * This class represents a center.
 * @author Andrea Mogavero
 *
 */
public class UserCenter extends AlgorithmPlace {

	public UserCenter(double x, double y, double shapeSize, int xSize, int ySize)
	{
		super((x - shapeSize / 2), (y - shapeSize / 2), x, y, shapeSize, xSize, ySize);
		shape = new Rectangle2D.Double(super.getX(), super.getY(), shapeSize, shapeSize);
	}
	
	/**
	 * @return true if this and otherObject overlap, otherwise false. 
	 */
	@Override
	public boolean equals(Object otherObject)
	{
		return super.equals(otherObject);
	}
	
	/**
	 * @return the shape
	 */
	public Rectangle2D.Double getShape() {
		return shape;
	}

	/**
	 * @param shape the shape to set
	 */
	public void setShape(Rectangle2D.Double shape) {
		this.shape = shape;
	}

	/**
	 * This method changes the coordinates of the user center.
	 * @param x the new x-coordinate.
	 * @param y the new y-coordinate
	 * @param otherUserCenters The other user center located in the drawing area.
	 * @throws IllegalPositionException if the moved user center steps outside of the drawing area.
	 * @throws DuplicatePlaceException if the moved user center overlap another user center.
	 */
	public void changePosition(double x, double y, ArrayList<UserCenter> otherUserCenters) throws IllegalPositionException, DuplicatePlaceException
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
		else if (otherUserCenters.contains(this))
		{
			super.setX(prevX);
			super.setY(prevY);
			throw new DuplicatePlaceException();
		}
		else
		{
			shape = new Rectangle2D.Double(super.getX(), super.getY(), getShapeSize(), getShapeSize());
			super.setCenterX(x);
			super.setCenterY(y);
		}
	}
	
	private Rectangle2D.Double shape;

}
