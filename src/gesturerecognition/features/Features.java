package gesturerecognition.features;

import gesturerecognition.util.*;

public class Features implements ParameterNameConstants{
	
	static double[] MAD = new double [NUMBER_OF_AXIS];
	static double[] RMS = new double [NUMBER_OF_AXIS];
	static double[] RSS = new double [NUMBER_OF_AXIS];
	static double[] STD = new double [NUMBER_OF_AXIS];

	public static double[] RMS(double[][] arr){
	    double[] ms = new double[NUMBER_OF_AXIS];
	    
	    for (int i = 0; i < arr.length; i++)
	    {
	    	for (int j = 0; j<NUMBER_OF_AXIS; j++)
	    		ms[j] += arr[i][j] * arr[i][j];
	    }
	    
	    for (int j = 0; j<NUMBER_OF_AXIS; j++)
	    	ms[j] /= arr.length;
	    
	    for (int j = 0; j<NUMBER_OF_AXIS; j++)
	    	RMS[j] = (double) Math.sqrt(ms[j]);
	    
	    return RMS;
	}
	


	public static double[] RSS(double[][] arr){
		double[] rss = new double [NUMBER_OF_AXIS];
		
		for (int i = 0; i < arr.length; i++)
	    {
	    	for (int j = 0; j<NUMBER_OF_AXIS; j++)
	    		rss[j] += arr[i][j] * arr[i][j];
	    }
		
		for (int j = 0; j<NUMBER_OF_AXIS; j++)
	    	RSS[j] = (double) Math.sqrt(rss[j]);
		
		return RSS;
	}

	public static double[] MAD (double[][] arr){
		double [] mad   = new double [NUMBER_OF_AXIS];
		double [] mean  = new double [NUMBER_OF_AXIS];
		double [] total = new double [NUMBER_OF_AXIS];
		
		for(int i = 0; i < arr.length; i++){
			for (int j = 0; j < NUMBER_OF_AXIS; j++){
			   total[j] += arr[i][j];
				}// this is the calculation for summing up all the values
			}
		
		for (int i = 0; i< NUMBER_OF_AXIS; i++)
		{
			mean[i] = total[i] / arr.length;
		}
//		double mean = total[0] / arr.length;
		
		for(int i = 0; i < arr.length; i++){
			for (int j = 0; j < NUMBER_OF_AXIS; j++){
			   arr[i][j] = (double) Math.abs((arr[i][j]-mean[j]));
			}
		}
		
		for (int i = 0; i< NUMBER_OF_AXIS; i++)
		{
			total[i] = 0;
		}
		
		for(int i = 0; i < arr.length; i++){
			for (int j = 0; j < NUMBER_OF_AXIS; j++){
				   total[j] += arr[i][j];
					} // this is the calculation for summing up all the values
			}
		
		for (int i = 0; i< NUMBER_OF_AXIS; i++)
		{
			mad[i] = total[i] / arr.length;
		}

		for (int i = 0; i< NUMBER_OF_AXIS; i++)
		{
			MAD[i] = mad[i];
		}
		
		return MAD;
	}
	
	public static double[] STD(double[][] arr) {
		double [] std   = new double [NUMBER_OF_AXIS];
		double [] mean  = new double [NUMBER_OF_AXIS];
		double [] total = new double [NUMBER_OF_AXIS];
		
		for(int i = 0; i < arr.length; i++){
			for (int j = 0; j < NUMBER_OF_AXIS; j++){
			   total[j] += arr[i][j];
				}// this is the calculation for summing up all the values
			}
		
		for (int i = 0; i< NUMBER_OF_AXIS; i++)
		{
			mean[i] = total[i] / arr.length;
		}
		
		for (int i = 0; i< NUMBER_OF_AXIS; i++)
		{
			total[i] = 0;
		}
		
		for(int i = 0; i < arr.length; i++){
			for (int j = 0; j < NUMBER_OF_AXIS; j++){
				total[j] += (double) (arr[i][j]-mean[j]) * (arr[i][j]-mean[j]);
			}
		}
		
		for (int i = 0; i< NUMBER_OF_AXIS; i++)
		{
			total[i] = total[i]/arr.length;
		}
		
		for (int i = 0; i< NUMBER_OF_AXIS; i++)
		{
			STD[i] = (double) Math.sqrt(total[i]);
		}
		
		return STD;
	}
}
