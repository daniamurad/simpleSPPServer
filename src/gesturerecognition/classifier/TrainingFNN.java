package gesturerecognition.classifier;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TrainingSetImport;
import org.neuroph.util.TransferFunctionType;

import gesturerecognition.util.ParameterNameConstants;

public class TrainingFNN implements FNN, ParameterNameConstants{

	
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
}
