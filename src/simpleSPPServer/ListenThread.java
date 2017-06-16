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
import gesturerecognition.preprocessing.Preprocessing;
import gesturerecognition.segmentation.Eucledian;
import gesturerecognition.segmentation.Segmentation;
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
import org.apache.commons.math3.stat.descriptive.*;

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
	
	static int buf = 15;
	static int N1 = 4;
	static int idx = 0;
	static int idxBuf = 0;
	static int dimensions = 3;
	
	static double[] arrayAcc = new double[dimensions];

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
        int inputsCount = 21;
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
		
		//trainNN("newFeatures.txt",inputsCount,outputsCount);
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

	    				Preprocessing.preprocess(idx,arrayAcc);
	    				Segmentation.segment(idx, Preprocessing.getEucledianAverage(), Preprocessing.getHistoryAverage() , testFile);

	    				idx++;
	    				
	    			
	         
	            	}
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
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
	
}



