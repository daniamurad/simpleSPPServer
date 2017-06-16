package gesturerecognition.segmentation;

import java.io.FileWriter;
import java.io.IOException;

import gesturerecognition.classifier.TestingFNN;
import gesturerecognition.features.Features;
import gesturerecognition.util.ParameterNameConstants;
import gesturerecognition.classifier.DTW;
import javax.swing.*;

import org.math.plot.*;

public class Segmentation implements ParameterNameConstants{

	static boolean flag = false;
	static int idxBuf = 0;
	static double[] combinedBuffer = new double[SIZE_OF_SEGMENT*NUMBER_OF_AXIS];

	static double[][] buffSegment = new double[SIZE_OF_SEGMENT][NUMBER_OF_AXIS];

	
	public static void segment(int idx, double[] eucledianAverage,double[][] historyAverage, String testFile) throws IOException
	{
		FileWriter fw = null;
		//FileWriter sw = null;
		
		//if (idx>(SIZE_OF_BUFFER+10)){
			{	if (eucledianAverage[SIZE_OF_BUFFER-1]>1.5 && flag == false && idxBuf == 0 && eucledianAverage[SIZE_OF_BUFFER-1]-eucledianAverage[SIZE_OF_BUFFER-3]>0)
			{
				
				flag = true;
			//	idxBuf++;
				//System.out.println(eucledianMean[N-1]);
			}
			
			else if (idxBuf == SIZE_OF_SEGMENT)
			{
				fw = new FileWriter("data.txt");
			//	sw = new FileWriter("segment.txt");
    			
    			
    		
    			//printSegmentedGes(buffSegment);
			
    		//	double mag = magnitudeSpectrum(buffSegment[0]);
    			//System.out.println(mag);
				
				
		/*		for (int i = 0; i<ParameterNameConstants.NUMBER_OF_AXIS; i++)
				{
					fw.append(Features.MAD(buffSegment)[i] + ",");
					fw.append(Features.RMS(buffSegment)[i] + ",");
					fw.append(Features.RSS(buffSegment)[i] + ",");
					fw.append(Features.STD(buffSegment)[i] + ",");
				}
				
			
				for (int i = 0; i<ParameterNameConstants.NUMBER_OF_AXIS; i++)
				{
					for (int j = 0; j<SIZE_OF_SEGMENT; j++)
						sw.append((buffSegment)[j][i] + ",");
				}
    			*/
    			for (int i = 0; i<ParameterNameConstants.NUMBER_OF_AXIS; i++)
    			{
    				fw.append(Features.calculateMean(buffSegment)[i] + ",");
    			//	System.out.println(Features.calculateMean(buffSegment)[i]);
    			//	System.out.println(Features.MAD(buffSegment)[i]);
    			}
    			
    			//	System.out.println(RMS[i]);
    			for (int i = 0; i<ParameterNameConstants.NUMBER_OF_AXIS; i++)
    				{
    				fw.append(Features.calculateSTD(buffSegment)[i] + ",");
    				//System.out.println(Features.calculateSTD(buffSegment)[i]);
    				}
    			//	System.out.println(STD[i]);
    			for (int i = 0; i<ParameterNameConstants.NUMBER_OF_AXIS; i++)
    				{
    				fw.append(Features.calculateVarience(buffSegment)[i] + ",");
    				//System.out.println(Features.calculateVarience(buffSegment)[i]);
    				}
    			//	System.out.println(RSS[i]);
    		/*	for (int i = 0; i<ParameterNameConstants.NUMBER_OF_AXIS; i++)
    				{
    				fw.append(Features.calculateSumOfSquares(buffSegment)[i] + ",");
    				//System.out.println(Features.calculateSumOfSquares(buffSegment)[i]);
    				}*/
    			
    			for (int i = 0; i<ParameterNameConstants.NUMBER_OF_AXIS; i++)
    			{
    				fw.append(Features.calculateMin(buffSegment)[i] + ",");
    				//System.out.println(Features.calculateMin(buffSegment)[i]);
    			}
    			
    			for (int i = 0; i<ParameterNameConstants.NUMBER_OF_AXIS; i++)
    				{
    				fw.append(Features.calculateMax(buffSegment)[i] + ",");
    				//System.out.println(Features.calculateMax(buffSegment)[i]);
    				}
    			
    			//UNCOMMENT
    			DTW[][] d  = Features.DTW(buffSegment);
    			
    			for (int i = 0; i<ParameterNameConstants.NUMBER_OF_GESTURES; i++)
				{
    				for (int j = 0; j<NUMBER_OF_AXIS; j++)
    				{
    					fw.append((d[i][j]) + ",");
				  //      System.out.println(d[i][j]);
    				}
				}
    			
    			
    			//fw.append(1 + "," + 0 + "," + 0 + "," + 0);
    			
    			//fw.append(RSS[0] + "," + RSS[1] + "," + RSS[2]);
    			fw.close();
    			//sw.close();
    			
    			//DTWW();
    			//plot();
    			
    			//trainNN(trainFile,inputsCount,outputsCount);
    			TestingFNN.testData(testFile);
    		//	player.play();
    		//	System.out.println("break");
				flag = false;
				idxBuf = 0;
			}
			}
			
			if (flag == true) //&& idxBuf<50)
			{
				buffSegment[idxBuf][0] = historyAverage[2][0];
				buffSegment[idxBuf][1] = historyAverage[2][1];
				buffSegment[idxBuf][2] = historyAverage[2][2];
				
				//System.out.println(buffSegment[idxBuf][0]);
				idxBuf++;
				//System.out.println(buffSegment[idxBuf-1][1]);
				
			}
			
			
	}
	
	public static void printSegmentedGes (double[][] arr)
	{
        
        double[] ax = new double[arr.length];
        double[] ay = new double[arr.length];
        double[] az = new double[arr.length];
        double[] time =  new double[arr.length];

        // create your PlotPanel (you can use it as a JPanel)

		System.out.println("X");
		for (int i =0; i<arr.length; i++ ){
			System.out.println(arr[i][0]);
			ax[i] = arr[i][0];
			time[i] = i;
			//sw.append(arr[i][0]+",");
		}
		
		System.out.println("Y");
		for (int i =0; i<arr.length; i++ ){
			System.out.println(arr[i][1]);
			ay[i] = arr[i][1];
		}
		
		System.out.println("Z");
		for (int i =0; i<arr.length; i++ ){
			System.out.println(arr[i][2]);
			az[i] = arr[i][2];
		}	
		
		Plot2DPanel plot = new Plot2DPanel();

        // define the legend position
        plot.addLegend("SOUTH");

        // add a line plot to the PlotPanel
        plot.addLinePlot("ax",time, ax);
        plot.addLinePlot("ay",time, ay);
        plot.addLinePlot("az",time, az);

        // put the PlotPanel in a JFrame like a JPanel
        JFrame frame = new JFrame("a plot panel");
        frame.setSize(600, 600);
        frame.setContentPane(plot);
        frame.setVisible(true);
        
		//System.out.println(arr.length);
	}
	
	public static void DTWW()
	{
		
		double[] sample1 = {2.331106962,1.972183362,0.946141162,2.038857358,0.3143975379999988,2.267611058,0.5138059420000003,2.581683342,0.6932679419999994,2.0383784580000004,1.1691141379999994,0.22283884199999981,1.254011358,1.4148091580000002,0.351438758,0.40392766200000096,0.23499446200000085,0.3273572620000005,0.364972442,0.2563384420000001,3.432676299999998,5.989646699999997,2.5633111299999998,0.22576286999999962,1.6384852700000012,0.31214372999999984,5.8409999300000015,0.415891400000004,2.8594944300000043,3.49344957,5.254088169999997,4.436699569999996,3.472536230000002,1.6873989999999965,2.679023800000004,1.4300175000000008,0.40636830000000135,1.2012641000000022,2.4680243000000006,2.5091326000000036,1.5623743180000003,0.24727911800000046,0.868350562,0.5984401620000002,0.6433294819999993,0.6448609620000003,0.37639583799999965,0.06997097800000063,0.25746282200000015,2.0525679779999995,1.218429978,0.8283904220000005,0.08364822199999944,0.31130156200000014,0.8051799620000002,0.45056356200000014,0.21641157800000066,0.3827512219999991,0.17457562199999987,0.3054247780000008};
		double[] sample2 = {2.1279442217000004,1.3043346217000011,0.039967221700000666,1.0528971763000006,0.6579377962999997,0.7593793583000001,1.0720870416999992,1.353004441700001,0.37030635829999947,0.9134913962999995,0.10345820370000025,0.8428071763000005,1.1194173763000006,0.1809523763000005,0.12673159629999953,0.1583167962999994,0.02210220370000071,0.1355222037000008,0.17701342369999962,0.1531961763000005,4.538562389999998,3.6916947900000006,0.761443990000001,1.4275102099999986,1.995566209999999,0.40778479,2.5788405899999987,1.6132891300000027,1.6494682499999973,3.3615809500000013,4.225868350000003,8.136499999968905E-4,4.499320249999992,2.011933130000002,1.0231232699999966,2.073572269999997,1.9699152100000026,1.72106141,1.9885792100000002,1.966373869999999,1.026607068,0.7571752679999999,0.0070923319999998125,0.634777412,0.2758540119999999,0.14006642800000013,0.17128777199999967,0.011045228000000185,0.18284982799999994,0.4068180280000002,0.36386197200000026,0.23381698800000006,0.02460781199999995,0.389274012,0.49407961199999995,0.4199787880000001,0.02455997199999982,0.29494897200000003,0.3260557719999999,0.17195797199999996};
		double[] sample3 = {0.6461464175999996,0.3821557296000002,0.7008836303999999,0.16332257759999957,1.4302974224,1.9705966223999998,1.4523112223999999,0.07574517759999977,0.3548814304000001,0.6156512703999997,0.021318129600000235,0.25964332960000047,0.6482377296000004,0.6831729296000002,0.6850873296000002,0.7152367296000004,0.7621359296000003,0.6343593296000003,0.44963332960000035,0.3984269296000005,0.29122545600000316,0.6362325439999965,0.43831710000000235,0.7356014999999962,0.9174560999999992,0.16515249999999804,0.7642199000000021,0.23969410400000135,0.023210460000001598,0.8506484600000024,0.13471593999999754,0.24191433999999723,1.1129017399999979,0.41532690400000205,0.24767645600000443,0.2465276959999978,0.18083050400000267,0.32261969599999873,0.24145485600000272,0.12257989599999797,0.3138616340000002,2.146916966,2.4788684499999993,0.4154897499999999,2.4197177499999993,2.7623701500000015,0.5593460499999994,1.0146333659999995,0.4710797860000002,2.3241966300000003,0.9214281699999978,1.7388163700000012,0.09925437000000148,2.9946654300000004,2.5466141859999984,0.9348087859999992,0.3731080140000005,1.5551624139999998,2.267745014,2.604176014};
		double[] sample4 = {0.8560645397000002,0.5942897397000002,0.3478291397000004,0.14300353970000046,0.6206109397000001,0.05846481369999945,0.2222128463000006,0.5270513997000008,1.7073664003,1.8648140003000002,0.18313820029999928,0.5544971536999994,0.5986039863000008,0.9398133397000001,0.8091653397000003,0.33555401369999927,0.7053237863000008,0.40406764630000036,0.0700296463000003,0.03078744630000041,2.198142724000002,0.6389797240000021,1.7772926759999974,5.215659510000002,1.285687310000002,2.2839252899999973,4.050307089999999,1.1923194899999974,2.914841275999998,0.5318288159999973,2.0147805700000028,3.128352229999999,3.8117420299999996,0.7039919700000041,5.246526769999999,1.7784892159999988,0.1367257840000029,1.084761984,1.4240645840000035,1.5197771840000023,0.20162417069999972,0.5772973706999998,0.9070333047000003,0.4547897047000007,1.0429455047000007,0.7400141047000004,0.3705625047000003,0.0472294293000004,0.4065935906999998,0.6398504847000004,1.2112371152999997,2.7249367152999993,1.2882859152999997,1.0526553906999998,0.6491200093000005,0.22094822930000024,0.4911559706999997,0.14898237069999953,0.2740688293000002,0.6176782293000003};
		
		DTW dtw = new DTW(sample1, sample3);
		//System.out.println(dtw);
		//DTW dtw = new DTW(sample, templete)
	}
	
	
}
