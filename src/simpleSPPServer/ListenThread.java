package simpleSPPServer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import javax.microedition.io.StreamConnection;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

import java.util.Arrays;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.Perceptron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import org.neuroph.core.learning.*;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TrainingSetImport;
import org.neuroph.util.TransferFunctionType;

import gesturerecognition.features.Features;
import gesturerecognition.segmentation.Eucledian;
import gesturerecognition.util.ParameterNameConstants;
import jAudio.org.oc.ocvolume.dsp.fft;
import jAudio.org.oc.ocvolume.dsp.fft;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.*;

//import org.math.plot.
import org.math.plot.*;
import javax.swing.*;

public class ListenThread implements Runnable{
    static btAudioPlayer player1 = new btAudioPlayer(5,"src/kick.mp3");
    static btAudioPlayer player2 = new btAudioPlayer(5,"src/Closed_04.mp3");
    private StreamConnection mConnection;
	double lastx=0., lasty=0., lastz=0.;
	int FLAG;
	long lasttime;
	
	private static final String FILENAME = "Down/Down1.txt";
	private String path;
	private boolean appendToFile = false;
	
	static String[] valuesArray; 
	static double ax = 0;
	static double ay = 0;
	static double az = 0;
	static int N = 5;
	static int buf = 15;
	static int N1 = 4;
	static int idx = 0;
	static int idxBuf = 0;
	static int dimensions = 3;
	static double eucledian = 0;
	static double[] arrayAcc = new double[dimensions];
	static double[][] history = new double[N][dimensions];
	static double[][] historyMean = new double[N][dimensions];
	static double[] eucledianArr = new double[N];
	static double[] eucledianMean = new double[N];
	static double[][] buffSegment = new double[buf][dimensions];
	static boolean flag = false;
	static String trainFile = "newFeatures.txt";
	static String testFile = "data.txt";
	static int inputsCount = 12;
	static int outputsCount = 2;
	
	/** features **/
	
	static double MADx = 0;
	static double MADy = 0;
	static double MADz = 0;
	
	/** features end**/
	
	
	public void collectTrainData(String trainingSetFileName)
	{
		
	}
	
	public void trainNN(String trainingSetFileName,int inputsCount,int outputsCount)
	{
		trainingSetFileName = "newFeatures.txt";
		String testingSetFileName = "data.txt";
        inputsCount = 12;
        outputsCount = 2;
        
		
		// create training set (logical AND function)
        System.out.println("Running Sample");
        System.out.println("Using training set " + trainingSetFileName);

        // create training set
        DataSet trainingSet = null;
        
        try {
            trainingSet = TrainingSetImport.importFromFile(trainingSetFileName, inputsCount, outputsCount, ",");
          //  testSet = TrainingSetImport.importFromFile(testingSetFileName, inputsCount, 0, ",");
        } catch (FileNotFoundException ex) {
            System.out.println("File not found!");
        } catch (IOException | NumberFormatException ex) {
            System.out.println("Error reading file or bad number format!");
        }
        
        // create multi layer Perceptron
        System.out.println("Creating neural network");
        MultiLayerPerceptron neuralNet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, inputsCount, 30, 2);

        // set learning Parametars
        MomentumBackpropagation learningRule = (MomentumBackpropagation) neuralNet.getLearningRule();
        learningRule.setLearningRate(0.4);
        learningRule.setMomentum(0.6);

        // learn the training set
        System.out.println("Training neural network...");
        neuralNet.learn(trainingSet);
        System.out.println("Done!");

	}
	
	public void testNN(String testingSetFileName){
		  	testingSetFileName = "data.txt";
	        DataSet testSet = null;
	        
	        try {
	          //  trainingSet = TrainingSetImport.importFromFile(trainingSetFileName, inputsCount, outputsCount, ",");
	            testSet = TrainingSetImport.importFromFile(testingSetFileName, inputsCount, 0, ",");
	        } catch (FileNotFoundException ex) {
	            System.out.println("File not found!");
	        } catch (IOException | NumberFormatException ex) {
	            System.out.println("Error reading file or bad number format!");
	        }
		
		// load saved neural network
       NeuralNetwork loadedMlPerceptron = NeuralNetwork.createFromFile("myMlPerceptron.nnet");

       // test loaded neural network
       System.out.println("Testing loaded neural network");
       testWineClassification(loadedMlPerceptron, testSet);
	}
	
	public ListenThread(StreamConnection connection){		
		mConnection = connection;
	}
	
	@Override
	public void run() {
	//	plot();
		// TODO Auto-generated method stub
		//SwingWorkerRealTime swingWorkerRealTime = new SwingWorkerRealTime();
	    //swingWorkerRealTime.go();
		//String testingSetFileName = "data.txt";
        int inputsCount = 12;
        int outputsCount = 2;
		BufferedReader br = null;
		FileReader fr = null;
		FileWriter fw = null;
		
		/*try {
			fw = new FileWriter("data.txt");
			fw.write("");
			fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		trainNN(trainFile,inputsCount, outputsCount);
		
		 lasttime = System.currentTimeMillis();
		 try {
			 	fr = new FileReader(FILENAME);
				br = new BufferedReader(fr);

				String sCurrentLine;

				br = new BufferedReader(new FileReader(FILENAME));
			 
	            // prepare to receive data
	            InputStream inputStream = mConnection.openInputStream();
	            BufferedReader bReader=new BufferedReader(new InputStreamReader(inputStream));
	            System.out.println("ListenThread starting...");
	            while (true) {	            	
	            	String lineRead =bReader.readLine();
	            	if(lineRead.startsWith("/p")){
	            		String[] numbers = lineRead.split("/p");
	            		
	            	
	            		/** Dania Code **/
	            		
	            		arrayAcc[0] = Double.parseDouble(numbers[1]);
	    				arrayAcc[1] = Double.parseDouble(numbers[2]);
	    				arrayAcc[2] = Double.parseDouble(numbers[3]);
	            		
	            		/** Dania Code Ends**/
	    				if (idx < N)
	    				{
	    					for (int i = 0; i< dimensions; i++)
	    					{
	    						history[idx][i] = arrayAcc[i];
	    					}
	    					eucledianArr[0] = 0;
	    					eucledianMean[0] = eucledian;
	    				//	System.out.println(idx);
	    					
	    					if (idx>0)
	    					{
	    						eucledian = Eucledian.calculateEucledian(history[idx][0],history[idx][1],history[idx][2],history[idx-1][0],history[idx-1][1],history[idx-1][2]);
	    						eucledianArr[idx] = eucledian;
	    						eucledianMean[idx] = eucledian;
	    					//	System.out.println(eucledian);
	    					//	System.out.println(eucledianArr[idx]);
	    					}
	    				}
	    				
	    				else if (idx>N)
	    				{
	    					for (int i = 0; i< N-1; i++)
	    					{
	    						history[i][0] = history[i+1][0];
	    						history[i][1] = history[i+1][1];
	    						history[i][2] = history[i+1][2];
	    					}
	    					
	    					history[N-1][0] = arrayAcc[0];
	    					history[N-1][1] = arrayAcc[1];
	    					history[N-1][2] = arrayAcc[2];
	    					
	    					historyAvrg();
	    					eucledian = Eucledian.calculateEucledian(historyMean[N-1][0],historyMean[N-1][1],historyMean[N-1][2],historyMean[N-2][0],historyMean[N-2][1],historyMean[N-2][2]);
	    					
	    					for (int i = 0; i< N-1; i++)
	    					{
	    						eucledianArr[i] = eucledianArr[i+1];
	    					}
	    					
	    					eucledianArr[N-1] = eucledian;
	    					
	    					eucAvrg();
	    					if (idx>(N+10)){
	    					if (eucledianMean[N-1]>1.5 && flag == false && idxBuf == 0 && eucledianMean[N-1]-eucledianMean[N-3]>0)
	    					{
	    						
	    						flag = true;
	    					//	idxBuf++;
	    						//System.out.println(eucledianMean[N-1]);
	    					}
	    					
	    					if (flag == true) //&& idxBuf<50)
	    					{
	    						buffSegment[idxBuf][0] = historyMean[2][0];
	    						buffSegment[idxBuf][1] = historyMean[2][1];
	    						buffSegment[idxBuf][2] = historyMean[2][2];
	    						
	    						//System.out.println(buffSegment[idxBuf][0]);
	    						idxBuf++;
	    						//System.out.println(buffSegment[idxBuf-1][1]);
	    						
	    					}
	    					
	    					if (idxBuf == buf)
	    					{
	    						fw = new FileWriter("data.txt");
	    		    			
	    		    			//printSegmentedGes(buffSegment);
	    					
	    		    		//	double mag = magnitudeSpectrum(buffSegment[0]);
	    		    			//System.out.println(mag);
	    		    			
	    		    			for (int i = 0; i<ParameterNameConstants.NUMBER_OF_AXIS; i++)
	    		    			{
	    		    			//	System.out.println(MAD[i]);
	    		    				fw.append(Features.MAD(buffSegment)[i] + ",");
	    		    			//	System.out.println(RMS[i]);
	    		    				fw.append(Features.RMS(buffSegment)[i] + ",");
	    		    			//	System.out.println(STD[i]);
	    		    				fw.append(Features.STD(buffSegment)[i] + ",");
	    		    			//	System.out.println(RSS[i]);
	    		    				fw.append(Features.RSS(buffSegment)[i] + ",");
	    		    			}
	    		    			
	    		    			fw.append(0 + "," + 1);
	    		    			
	    		    			//fw.append(RSS[0] + "," + RSS[1] + "," + RSS[2]);
	    		    			fw.close();
	    		    			
	    		    			//plot();
	    		    			
	    		    			//trainNN(trainFile,inputsCount,outputsCount);
	    		    			testNN(testFile);
	    		    		//	player.play();
	    		    			System.out.println("break");
	    						flag = false;
	    						idxBuf = 0;
	    					}
	    					}
	    				
	    				}
	    				
	    				idx++;
	    			
	            		
	         /*** MATTS CODE ***/   
	    				
	         /*   		double x = Double.parseDouble(numbers[1]);
	            		double y = Double.parseDouble(numbers[2]);
	            		double z = Double.parseDouble(numbers[3]);
	            		
	          			double eudist =  Math.pow((x-lastx),2)+Math.pow((y-lasty),2)+Math.pow((z-lastz),2);
	            		if(eudist>140){
	            			long time = System.currentTimeMillis();
	            			if ((time-lasttime)>220){
		            			if( FLAG == 0){
		            			    FLAG = 1;
		            			    player.play();
		            			    lasttime = time;
		            			}
	            		    }
	            		}
	            		else{
	            			FLAG =0;
	            		}
	            		lastx = x;
	            		lasty = y;
	            		lastz = z;
	            		//swingWorkerRealTime.update(x, y, z);
	            	    System.out.println(lineRead);*/
	            	}
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	


	

	public static void historyAvrg()
	{
		double sumx = 0;
		double sumy = 0;
		double sumz = 0;
		
		for (int i = 0; i< N-1; i++)
		{
			historyMean[i][0] = historyMean[i+1][0];
			historyMean[i][1] = historyMean[i+1][1];
			historyMean[i][2] = historyMean[i+1][2];
		}
		
		for (int i = 0; i< N; i++)
		{
			sumx = sumx + history[i][0];
			sumy = sumy + history[i][1];
			sumz = sumz + history[i][2];
		}
		
		historyMean[N-1][0] = sumx/N;
		historyMean[N-1][1] = sumy/N;
		historyMean[N-1][2] = sumz/N;
	}

	public static void eucAvrg()
	{
		double sum = 0;
		
		for (int i = 0; i< N-1; i++)
		{
			eucledianMean[i] = eucledianMean[i+1];
		}
		
		for (int i = 0; i< N; i++)
		{
			sum = sum + eucledianArr[i];
		}
		
		eucledianMean[N-1] = sum/N;
	}
	
	public static void printSegmentedGes (double[][] arr)
	{
		System.out.println("X");
		for (int i =0; i<arr.length; i++ ){
			System.out.println(buffSegment[i][0]);	
		}
		
		System.out.println("Y");
		for (int i =0; i<arr.length; i++ ){
			System.out.println(buffSegment[i][1]);
			
		}
		
		System.out.println("Z");
		for (int i =0; i<arr.length; i++ ){
			System.out.println(buffSegment[i][2]);
		}	
		
		//System.out.println(arr.length);
	}
	

public ListenThread( String file_path , boolean append_value ) {

	path = file_path;
	appendToFile = append_value;

	}


	public static void testWineClassification(NeuralNetwork nnet, DataSet dset) {

	    for (DataSetRow trainingElement : dset.getRows()) {
	        nnet.setInput(trainingElement.getInput());
	        nnet.calculate();
	        double[] networkOutput = nnet.getOutput();
	   //     System.out.print("Input: " + Arrays.toString(trainingElement.getInput()));
	   //
	        System.out.println(" Output: " + Arrays.toString(networkOutput));
	        
	        if (networkOutput[0] > 0.93)
	        	player1.play();
	        
	        else if (networkOutput[1] > 0.93)
	        	player2.play();
	        
	    	}
		}
	
	public static void plot(){
        // define your data
        double[] y0 = buffSegment[0];
        double[] y1 = buffSegment[1];
        double[] y2 = buffSegment[2];
        double[] x = new double[buf];
        
        for (int i=0; i<buf; i++)
        {
        	x[i] = i;
        	System.out.println(y0[i]);
        }
        
        // create your PlotPanel (you can use it as a JPanel)
        Plot2DPanel plot = new Plot2DPanel();

        // define the legend position
        plot.addLegend("SOUTH");

        // add a line plot to the PlotPanel
        plot.addLinePlot("my plot x0", y0, x);
      //  plot.addLinePlot("my plot x1", x, y1);
      //  plot.addLinePlot("my plot x2", x, y2);

        // put the PlotPanel in a JFrame like a JPanel
        JFrame frame = new JFrame("a plot panel");
        frame.setSize(600, 600);
        frame.setContentPane(plot);
        frame.setVisible(true);
	}
	
	public static void DTWW()
	{
		
		double[] n2 = {1.5f, 3.9f, 4.1f, 3.3f};
		double[] n1 = {2.1f, 2.45f, 3.673f, 4.32f, 2.05f, 1.93f, 5.67f, 6.01f};
		DTW dtw = new DTW(n1, n2);
		System.out.println(dtw);
		//DTW dtw = new DTW(sample, templete)
	}
}



