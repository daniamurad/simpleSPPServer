package gesturerecognition.classifier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import javax.microedition.io.StreamConnection;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.util.TrainingSetImport;

import gesturerecognition.util.ParameterNameConstants;
import simpleSPPServer.btAudioPlayer;

public class TestingFNN implements FNN,ParameterNameConstants{
	
    static btAudioPlayer player1 = new btAudioPlayer(5,"src/kick.mp3");
    static btAudioPlayer player2 = new btAudioPlayer(5,"src/Closed_04.mp3");
    static btAudioPlayer player3 = new btAudioPlayer(5,"src/BassB1.mp3");
    static btAudioPlayer player4 = new btAudioPlayer(5,"src/BassF1.mp3");

 
	public static void testData(String testingSetFileName){
	  	
        DataSet testSet = null;
        
        try {
          //  trainingSet = TrainingSetImport.importFromFile(trainingSetFileName, inputsCount, outputsCount, ",");
            testSet = TrainingSetImport.importFromFile(testingSetFileName, inputsCount, 0, ",");
        } catch (FileNotFoundException ex) {
            System.out.println("File not found!");
        } catch (IOException | NumberFormatException ex) {
           // System.out.println("Error reading file or bad number format!");
        }
	
	// load saved neural network
 //  NeuralNetwork loadedMlPerceptron = NeuralNetwork.createFromFile("myMlPerceptron.nnet");

   // test loaded neural network
 //  System.out.println("Testing loaded neural network");
   testClassification(loadedMlPerceptron, testSet);
}



	public static void testClassification(NeuralNetwork nnet, DataSet dset) {

	    for (DataSetRow trainingElement : dset.getRows()) {
	        nnet.setInput(trainingElement.getInput());
	        nnet.calculate();
	        double[] networkOutput = nnet.getOutput();
	   //     System.out.print("Input: " + Arrays.toString(trainingElement.getInput()));
	   //
	        //System.out.println(" Output: " + Arrays.toString(networkOutput));
	        
	        if (networkOutput[0] > 0.93)
	        {
	        	player1.play();
	        	//System.out.println("Down!");
	        }
	        
	        if (networkOutput[1] > 0.93)
	        {
	        	//System.out.println("Right!");
	        	player2.play();
	        }
	        
	        if (networkOutput[2] > 0.93)
	        {
	        	//System.out.println("Up!");
	        	player3.play();
	        }
	        
	        if (networkOutput[3] > 0.93)
	        {
	        	//System.out.println("Left");
	        	player4.play();
	        }
	        
	    	}
		}
}
