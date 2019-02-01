package places;

/**
 * This class represents a place (city or center) positioned by the user.
 * @author Andrea Mogavero
 *
 */
public abstract class AlgorithmPlace {
	
	public AlgorithmPlace(double x, double y, double centerX, double centerY, double shapeSize, int xSize, int ySize)
	{
		this.x = x;
		this.y = y;
		this.centerX = centerX;
		this.centerY = centerY;
		this.shapeSize = shapeSize;
		this.xSize = xSize;
		this.ySize = ySize;
	}
	
	/**
	 * @return true if this and otherObject overlap, otherwise false. 
	 */
	@Override
	public boolean equals(Object otherObject)
	{
		if (otherObject == null)
			return false;
		if (getClass() != otherObject.getClass())
			return false;
		AlgorithmPlace other = (AlgorithmPlace) otherObject;
		
		if (x == other.x && y == other.y) //Shape overlap
		{
			return true;
		}
		else if ((y > other.y + shapeSize) || (x > other.x + shapeSize))
		{
			return false; //Shape don't overlap
		}
		else if (x + shapeSize < other.x) //if (y > other.y + shapeSize) AND (x > other.x + shapeSize)
		{
			return false; //Shape don't overlap
		}
		else if (y + shapeSize < other.y) //if (y > other.y + shapeSize) AND (x > other.x + shapeSize) AND (x + shapeSize < other.x)
		{
			return false; //Shape don't overlap
		}
		else //Shape overlap
		{
			return true;
		}
	}
	
	/**
	 * This method checks if the place is contained in the drawing area.
	 * @return true if is contained, otherwise flae.
	 */
	public boolean areGoodCoordinates()
	{
		if ((x + shapeSize <= xSize) && (y + shapeSize <= ySize) && x >= 0 && y >= 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * @return the centerX
	 */
	public double getCenterX() {
		return centerX;
	}

	/**
	 * @param centerX the centerX to set
	 */
	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}

	/**
	 * @return the centerY
	 */
	public double getCenterY() {
		return centerY;
	}

	/**
	 * @param centerY the centerY to set
	 */
	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}

	/**
	 * @return the shapeSize
	 */
	public double getShapeSize() {
		return shapeSize;
	}

	/**
	 * @param shapeSize the shapeSize to set
	 */
	public void setShapeSize(double citySize) {
		this.shapeSize = citySize;
	}

	/**
	 * @return the xSize
	 */
	public int getxSize() {
		return xSize;
	}

	/**
	 * @param xSize the xSize to set
	 */
	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	/**
	 * @return the ySize
	 */
	public int getySize() {
		return ySize;
	}

	/**
	 * @param ySize the ySize to set
	 */
	public void setySize(int ySize) {
		this.ySize = ySize;
	}

	private double x;
	private double y;
	private double centerX;
	private double centerY;
	private double shapeSize;
	private int xSize;
	private int ySize;

}
