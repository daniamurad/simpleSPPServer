package gesturerecognition.segmentation;

public class Eucledian {

	public static double calculateEucledian(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		double euc = 0;
		double xDif = 0;
		double yDif = 0;
		double zDif = 0;

		zDif = Math.abs (z1 - z2);
		yDif = Math.abs (y1 - y2);
		xDif = Math.abs (x1 - x2);    
		euc = (double) Math.sqrt((xDif)*(xDif) +(yDif)*(yDif) + (zDif)*(zDif));
		return euc;
	}
}
