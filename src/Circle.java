
public class Circle
{
   private int centerX;
   private int centerY;
   private int votes;
   
   
   public Circle (int inX, int inY, int max)
   {
	   centerX = inX;
	   centerY = inY;
	   votes = max;
	   
   }
public int getCenterX()
{
	return centerX;
}
public void setCenterX(int centerX)
{
	this.centerX = centerX;
}
public int getCenterY()
{
	return centerY;
}
public void setCenterY(int centerY)
{
	this.centerY = centerY;
}
@Override
public String toString()
{
	return "Circle [centerX=" + centerX + ", centerY=" + centerY + "] votes = " + votes;
}
   
   
}
