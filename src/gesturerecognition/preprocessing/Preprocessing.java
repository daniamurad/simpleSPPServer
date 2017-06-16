package gesturerecognition.preprocessing;

import gesturerecognition.segmentation.Eucledian;
import gesturerecognition.util.ParameterNameConstants;

import net.sf.javaml.distance.fastdtw.*;


public class Preprocessing implements ParameterNameConstants {
	
	static double[][] sensorsHistory = new double[SIZE_OF_BUFFER][NUMBER_OF_AXIS];
	static double[][] historyAverage = new double[SIZE_OF_BUFFER][NUMBER_OF_AXIS];
	static double[] eucledianHistory = new double[SIZE_OF_BUFFER];
	static double[] eucledianAverage = new double[SIZE_OF_BUFFER];
	static double eucledian = 0;
	
	public static double[][] getSensorsHistory()
	{
		return sensorsHistory;
	}
	
	public static double[][] getHistoryAverage()
	{
		return historyAverage;
	}
	
	public static double[] getEucledianHistory()
	{
		return eucledianHistory;
	}
	
	public static double[] getEucledianAverage()
	{
		return eucledianAverage;
	}
	
	public static void preprocess(int idx, double[] arrayAcc)
	{
		if (idx<SIZE_OF_BUFFER)
			fillInitialBuffer(idx,arrayAcc);
		
		else if (idx>SIZE_OF_BUFFER)
		{
			fillBuffer(arrayAcc);
			historyAvrg();
			eucledian = Eucledian.calculateEucledian(historyAverage[SIZE_OF_BUFFER-1][0],historyAverage[SIZE_OF_BUFFER-1][1],historyAverage[SIZE_OF_BUFFER-1][2],historyAverage[SIZE_OF_BUFFER-2][0],historyAverage[SIZE_OF_BUFFER-2][1],historyAverage[SIZE_OF_BUFFER-2][2]);
			fillEucledianHistory();
			eucAvrg();
			
		}
		
		
	}
	
	public static void fillInitialBuffer(int idx, double[] arrayAcc){

			for (int i = 0; i< NUMBER_OF_AXIS; i++)
			{
				sensorsHistory[idx][i] = arrayAcc[i];
			}
			eucledianHistory[0] = 0;
			eucledianAverage[0] = eucledian;
		//	System.out.println(idx);
			
			if (idx>0)
			{
				eucledian = Eucledian.calculateEucledian(sensorsHistory[idx][0],sensorsHistory[idx][1],sensorsHistory[idx][2],sensorsHistory[idx-1][0],sensorsHistory[idx-1][1],sensorsHistory[idx-1][2]);
				eucledianHistory[idx] = eucledian;
				eucledianAverage[idx] = eucledian;
			//	System.out.println(eucledian);
			//	System.out.println(eucledianArr[idx]);
			}
		
	}
	
	public static void fillBuffer(double[] arrayAcc){
		for (int i = 0; i< SIZE_OF_BUFFER-1; i++)
		{
			sensorsHistory[i][0] = sensorsHistory[i+1][0];
			sensorsHistory[i][1] = sensorsHistory[i+1][1];
			sensorsHistory[i][2] = sensorsHistory[i+1][2];
		}
		
		sensorsHistory[SIZE_OF_BUFFER-1][0] = arrayAcc[0];
		sensorsHistory[SIZE_OF_BUFFER-1][1] = arrayAcc[1];
		sensorsHistory[SIZE_OF_BUFFER-1][2] = arrayAcc[2];
	}
		
		//eucAvrg();

public static void historyAvrg()
{
	double sumx = 0;
	double sumy = 0;
	double sumz = 0;
	
	for (int i = 0; i< SIZE_OF_BUFFER-1; i++)
	{
		historyAverage[i][0] = historyAverage[i+1][0];
		historyAverage[i][1] = historyAverage[i+1][1];
		historyAverage[i][2] = historyAverage[i+1][2];
	}
	
	for (int i = 0; i< SIZE_OF_BUFFER; i++)
	{
		sumx = sumx + sensorsHistory[i][0];
		sumy = sumy + sensorsHistory[i][1];
		sumz = sumz + sensorsHistory[i][2];
	}
	
	historyAverage[SIZE_OF_BUFFER-1][0] = sumx/SIZE_OF_BUFFER;
	historyAverage[SIZE_OF_BUFFER-1][1] = sumy/SIZE_OF_BUFFER;
	historyAverage[SIZE_OF_BUFFER-1][2] = sumz/SIZE_OF_BUFFER;
}

public static void fillEucledianHistory()
{
	for (int i = 0; i< SIZE_OF_BUFFER-1; i++)
	{
		eucledianHistory[i] = eucledianHistory[i+1];
	}
	
	eucledianHistory[SIZE_OF_BUFFER-1] = eucledian;
}

public static void eucAvrg()
{
	double sum = 0;
	
	for (int i = 0; i< SIZE_OF_BUFFER-1; i++)
	{
		eucledianAverage[i] = eucledianAverage[i+1];
	}
	
	for (int i = 0; i< SIZE_OF_BUFFER; i++)
	{
		sum = sum + eucledianHistory[i];
	}
	
	eucledianAverage[SIZE_OF_BUFFER-1] = sum/SIZE_OF_BUFFER;
}
}

