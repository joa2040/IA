package ar.edu.utn.frlp.IA.neuralNetwork;

import java.util.Arrays;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.Hopfield;

/**
 * This sample shows how to create and train Hopfield neural network
 */
public class HopfieldNetwork {

	public static void main(String args[]) {

		// create training set (H and T letter in 3x3 grid)
		DataSet trainingSet = new DataSet(9);
		trainingSet.addRow(new DataSetRow(new double[] { 8, 0, 1, 1, 1, 1, 1, 0, 1 })); // H
																							// letter
		trainingSet.addRow(new DataSetRow(new double[] { 1, 1, 1, 0, 1, 0, 0, 1, 0 })); // T
																							// letter

		// create hopfield network
		Hopfield myHopfield = new Hopfield(9);
		System.out.println("Testing network");
		myHopfield.save("HopfieldNetwork.nnet");
		System.out.println("Testing network");
//		 learn the training set
		myHopfield.learn(trainingSet);

		// test hopfield network
		System.out.println("Testing network");

		// add one more 'incomplete' H pattern for testing - it will be
		// recognized as H
		trainingSet.addRow(new DataSetRow(new double[] { 8, 0, 0, 1, 0, 1, 1, 0, 1 })); // incomplete
																							// H
																							// letter

		for (DataSetRow dataRow : trainingSet.getRows()) {

			myHopfield.setInput(dataRow.getInput());
			myHopfield.calculate();
			myHopfield.calculate();
			double[] networkOutput = myHopfield.getOutput();

			System.out.print("Input: " + Arrays.toString(dataRow.getInput()));
			System.out.println(" Output: " + Arrays.toString(networkOutput));

		}

	}

}
