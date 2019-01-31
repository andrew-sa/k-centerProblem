package places;

import java.awt.geom.Ellipse2D;

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

	private Ellipse2D.Double shape;

}
