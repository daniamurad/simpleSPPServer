package gesturerecognition.features;

import gesturerecognition.classifier.DTW;
import gesturerecognition.util.*;

import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Features implements ParameterNameConstants{
	
	static double[] MAD = new double [NUMBER_OF_AXIS];
	static double[] RMS = new double [NUMBER_OF_AXIS];
	static double[] RSS = new double [NUMBER_OF_AXIS];
	static double[] STD = new double [NUMBER_OF_AXIS];
	static double[] DTWDown = new double [NUMBER_OF_AXIS];
	static double[] DTWy = new double [NUMBER_OF_AXIS];
	static double[] DTWz = new double [NUMBER_OF_AXIS];

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
	
	public static double[] calculateMean(double[][] sample) {
		
	    //ArrayList<DescriptiveStatistics> list =  new  ArrayList<DescriptiveStatistics>(3);
		DescriptiveStatistics stats[] = new DescriptiveStatistics[NUMBER_OF_AXIS];
		double [] mean  = new double [NUMBER_OF_AXIS];
		
		for (int i = 0; i<NUMBER_OF_AXIS; i++){
			stats[i] = new DescriptiveStatistics();
		}

	    // Add the data from the series to stats
	    for (int i = 0; i < sample.length; i++) {
	    	for (int j = 0; j<NUMBER_OF_AXIS; j++){
	    		//.out.println(sample.length);
	    		stats[j].addValue(sample[i][j]);
	    	}
	    }
	    
	    for (int i = 0; i< NUMBER_OF_AXIS; i++)
	    {
	    	mean[i] = stats[i].getMean();
	    }

	    return mean;
	}
	
	
public static double[] calculateSTD(double[][] sample) {
		
	    //ArrayList<DescriptiveStatistics> list =  new  ArrayList<DescriptiveStatistics>(3);
		DescriptiveStatistics stats[] = new DescriptiveStatistics[NUMBER_OF_AXIS];
		double [] std  = new double [NUMBER_OF_AXIS];
		
		for (int i = 0; i<NUMBER_OF_AXIS; i++){
			stats[i] = new DescriptiveStatistics();
		}

	    // Add the data from the series to stats
	    for (int i = 0; i < sample.length; i++) {
	    	for (int j = 0; j<NUMBER_OF_AXIS; j++){
	    		//.out.println(sample.length);
	    		stats[j].addValue(sample[i][j]);
	    	}
	    }
	    
	    for (int i = 0; i< NUMBER_OF_AXIS; i++)
	    {
	    	std[i] = stats[i].getStandardDeviation();
	    }

	    return std;
	}

public static double[] calculateVarience(double[][] sample) {
	
    //ArrayList<DescriptiveStatistics> list =  new  ArrayList<DescriptiveStatistics>(3);
	DescriptiveStatistics stats[] = new DescriptiveStatistics[NUMBER_OF_AXIS];
	double [] var  = new double [NUMBER_OF_AXIS];
	
	for (int i = 0; i<NUMBER_OF_AXIS; i++){
		stats[i] = new DescriptiveStatistics();
	}

    // Add the data from the series to stats
    for (int i = 0; i < sample.length; i++) {
    	for (int j = 0; j<NUMBER_OF_AXIS; j++){
    		//.out.println(sample.length);
    		stats[j].addValue(sample[i][j]);
    	}
    }
    
    for (int i = 0; i< NUMBER_OF_AXIS; i++)
    {
    	var[i] = stats[i].getVariance();
    }

    return var;
}


public static double[] calculateMin(double[][] sample) {
	
    //ArrayList<DescriptiveStatistics> list =  new  ArrayList<DescriptiveStatistics>(3);
	DescriptiveStatistics stats[] = new DescriptiveStatistics[NUMBER_OF_AXIS];
	double [] min  = new double [NUMBER_OF_AXIS];
	
	for (int i = 0; i<NUMBER_OF_AXIS; i++){
		stats[i] = new DescriptiveStatistics();
	}

    // Add the data from the series to stats
    for (int i = 0; i < sample.length; i++) {
    	for (int j = 0; j<NUMBER_OF_AXIS; j++){
    		//.out.println(sample.length);
    		stats[j].addValue(sample[i][j]);
    	}
    }
    
    for (int i = 0; i< NUMBER_OF_AXIS; i++)
    {
    	min[i] = stats[i].getMin();
    }

    return min;
}

public static double[] calculateMax(double[][] sample) {
	
    //ArrayList<DescriptiveStatistics> list =  new  ArrayList<DescriptiveStatistics>(3);
	DescriptiveStatistics stats[] = new DescriptiveStatistics[NUMBER_OF_AXIS];
	double [] max  = new double [NUMBER_OF_AXIS];
	
	for (int i = 0; i<NUMBER_OF_AXIS; i++){
		stats[i] = new DescriptiveStatistics();
	}

    // Add the data from the series to stats
    for (int i = 0; i < sample.length; i++) {
    	for (int j = 0; j<NUMBER_OF_AXIS; j++){
    		//.out.println(sample.length);
    		stats[j].addValue(sample[i][j]);
    	}
    }
    
    for (int i = 0; i< NUMBER_OF_AXIS; i++)
    {
    	max[i] = stats[i].getMax();
    }

    return max;
}

public static double[] calculateSumOfSquares(double[][] sample) {
	
    //ArrayList<DescriptiveStatistics> list =  new  ArrayList<DescriptiveStatistics>(3);
	DescriptiveStatistics stats[] = new DescriptiveStatistics[NUMBER_OF_AXIS];
	double [] sumsq  = new double [NUMBER_OF_AXIS];
	
	for (int i = 0; i<NUMBER_OF_AXIS; i++){
		stats[i] = new DescriptiveStatistics();
	}

    // Add the data from the series to stats
    for (int i = 0; i < sample.length; i++) {
    	for (int j = 0; j<NUMBER_OF_AXIS; j++){
    		//.out.println(sample.length);
    		stats[j].addValue(sample[i][j]);
    	}
    }
    
    for (int i = 0; i< NUMBER_OF_AXIS; i++)
    {
    	sumsq[i] = stats[i].getSumsq();
    }

    return sumsq;
}

public static gesturerecognition.classifier.DTW[][] DTW(double[][] sample)
{
	DTW[][] dtw = new DTW[NUMBER_OF_GESTURES][NUMBER_OF_AXIS];
	DTW[] dtwDown = new DTW[NUMBER_OF_AXIS];
	DTW[] dtwRight = new DTW[NUMBER_OF_AXIS];
	DTW[] dtwLeft = new DTW[NUMBER_OF_AXIS];
	DTW[] dtwUp = new DTW[NUMBER_OF_AXIS];
	double[][] acc = new double[NUMBER_OF_AXIS][SIZE_OF_SEGMENT];
    double[] ax = new double[sample.length];
    double[] ay = new double[sample.length];
    double[] az = new double[sample.length];
	
    
	double[][] down1 = {
			{-4.1491544, -3.5059638, -2.4727430000000004, -0.02727820000000012, 2.0420347999999997, 4.4568714, 7.4665638, 8.435178400000002, 6.875058, 5.974399400000001, 3.9213576000000003, 0.8901298000000001, -0.09906299999999998, 0.3182454, 0.3728018, 0.9623932, 1.613241},
			{-4.286023800000001, -1.0265208000000001, 3.4514074, 7.72116, 9.809137199999999, 8.796494599999999, 3.4016368, -7.168417399999998, -16.1036956, -24.0052746, -27.831876599999998, -26.687150399999997, -21.165952, -16.775601, -11.793265799999999, -9.087461999999999, -8.551948199999998},
			{1.8826728, 0.9255437999999998, -0.8762516000000001, -0.7848458000000001, 0.07896319999999993, -0.798724, -1.7333606, -1.28016, -3.4097719999999994, -5.383850600000001, -5.294359200000001, -4.0433916000000005, -3.0302706, -1.4423936000000002, -0.9116656000000001, -0.7360324, -0.908794}
			};
	
	double[][] right1 = {
	{-0.9169296000000001, 0.3249454, 2.3454448, 4.8603014, 7.0650482, 8.385407599999999, 8.11454, 7.361758, 5.8863436, 4.0850266, 2.7029324000000003, 2.2325036, 1.292603, 0.5953344, 0.29336019999999996, -0.20673979999999997, -0.59677,},
	{-10.2642524, -10.5585696, -10.5011416, -10.193424400000001, -10.430792, -9.365986, -9.2702732, -10.6327464, -12.104332399999999, -11.4898556, -12.2747014, -13.1121892, -12.2431162, -10.6767744, -10.6992668, -10.788758399999999, -9.9541422,},
	{2.4636503999999997, 5.0689556, 9.2511312, 13.413685199999998, 16.3980136, 17.956698, 15.2992292, 7.900621199999999, -0.40630159999999976, -6.5984482, -11.6764964, -14.369378799999998, -13.5649118, -11.223774, -9.148717999999999, -6.8855862000000005, -4.271667,}
	};
	
	double[][] left1 = {
			{-4.1726038, -4.0864622, -4.572205200000001, -4.1807394, -3.3738796, -1.7903095999999998, -0.6604188, 1.4074583999999999, 2.4421150000000003, 2.7570103999999995, 2.1492334, 1.7907885999999997, 0.9542578, -0.011964200000000069, -0.9710076000000001, -1.9956142, -2.9924641999999997}, 
			{-8.577312, -9.134361199999999, -7.8934432, -5.722674400000001, -5.0359344, -4.3401016, -3.3293732000000005, -6.8645294, -10.801201, -12.9600054, -14.702937600000002, -15.762000800000001, -14.052568200000001, -12.6269248, -11.299386799999999, -10.116375199999998, -9.542576399999998},
			{-2.2253252, -6.429035799999999, -10.8318296, -15.7653514, -18.775522000000002, -17.3465282, -11.069676000000001, -3.224567600000001, 5.198168, 11.6353394, 14.451691599999998, 14.121482, 12.0167552, 9.677053400000002, 7.3957362, 5.4183076, 4.0922054}
			};
	
	double[][] up1 = {
			{0.47617180000000003, 0.2057828, 0.23353959999999999, -0.015314000000000005, 0.8240882, 1.3150954000000001, 1.8702302, 2.57037, 3.5136206000000003, 3.5160134, 3.6547970000000007, 3.3791438, 2.2459032, 1.1787044000000002, -0.00334999999999992, -1.0920842, -1.7955739999999998},
			{-12.3187294, -14.843636, -18.5367184, -22.0828816, -25.571138400000002, -27.531338600000005, -25.4768608, -19.89297, -12.9317704, -4.7483168000000004, 3.1858044, 7.566584000000001, 7.487621, 5.3551374, 1.9511077999999997, -2.1348762, -5.6379686},
			{-1.1696118, -2.0118853999999997, -3.2499318, -5.0325846, -7.259824, -9.357851, -10.7428168, -10.920842599999999, -9.849815199999998, -7.8747793999999995, -5.424529, -2.4933211999999996, 0.026320800000000234, 1.7166105999999999, 2.5741982, 2.9900708000000003, 2.54692}
			};

	for (int j = 0; j<NUMBER_OF_AXIS ; j++){
		for (int i =0; i<sample.length; i++ )
		
		{
			acc[j][i] = sample[i][j];
		}
	}
	
	//System.out.println("Up");
	for (int i = 0; i< NUMBER_OF_AXIS; i++ )
	{
		dtwUp[i] = new DTW(up1[i], acc[i]);
	//	System.out.println(dtwUp[i]);
		dtw[0][i] = dtwUp[i] ;
	}
	
	//System.out.println("Down");
	for (int i = 0; i< NUMBER_OF_AXIS; i++ )
	{
		dtwDown[i] = new DTW(down1[i], acc[i]);
	//	System.out.println(dtwDown[i]);
		dtw[1][i] = dtwDown[i];
	}
	
	//System.out.println("Right");
	for (int i = 0; i< NUMBER_OF_AXIS; i++ )
	{
		dtwRight[i] = new DTW(right1[i], acc[i]);
	//	System.out.println(dtwRight[i]);
		dtw[2][i] = dtwRight[i];
	}
	
	//System.out.println("Left");
	for (int i = 0; i< NUMBER_OF_AXIS; i++ )
	{
		dtwLeft[i] = new DTW(left1[i], acc[i]);
	//	System.out.println(dtwLeft[i]);
		dtw[3][i] = dtwLeft[i];
	}
	return dtw;
	//DTW dtw = new DTW(sample, templete)
}
}
