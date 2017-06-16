package gesturerecognition.util;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.util.TrainingSetImport;

public interface ParameterNameConstants {
	
	public static int NUMBER_OF_AXIS=3;
	public static int SIZE_OF_BUFFER = 5;
	public static int SIZE_OF_SEGMENT = 17;
	public static int NUMBER_OF_GESTURES = 4;
    static int inputsCount = 27;
	static int outputsCount = 4;
	NeuralNetwork loadedMlPerceptron = NeuralNetwork.createFromFile("myMlPerceptron.nnet");

}
