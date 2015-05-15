package ar.edu.utn.frlp.IA.main;

import javax.swing.JFrame;

import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.Hopfield;

import ar.edu.utn.frlp.IA.logic.FileUtil;
import ar.edu.utn.frlp.IA.neuralNetwork.HopfieldNetworkManager;
import ar.edu.utn.frlp.IA.view.MainWindow;

public class Main {

	public static void main(String args[]) {
		System.out.println("inicializando...");
		HopfieldNetworkManager.getInstance().setNetwork(new Hopfield(500));
		System.out.println("network created...");
		DataSet trainingSet = new DataSet(500);
		System.out.println("training");
		trainingSet.addRow(FileUtil.byteArray2DoubleArray(FileUtil.readFile("dataSet3")));//A
		trainingSet.addRow(FileUtil.byteArray2DoubleArray(FileUtil.readFile("dataSet4")));//I
		System.out.println("learning");
		HopfieldNetworkManager.getInstance().getNetwork().learn(trainingSet);	
		System.out.println("OK");
		JFrame frame = new MainWindow();
		frame.pack();
		frame.setVisible(Boolean.TRUE);
	}
}
