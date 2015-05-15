package ar.edu.utn.frlp.IA.neuralNetwork;

import javax.swing.JOptionPane;

import org.neuroph.nnet.Hopfield;

public class HopfieldNetworkManager {

	private static HopfieldNetworkManager instance;
	
	private Hopfield network;

	private HopfieldNetworkManager() {};

	public synchronized static HopfieldNetworkManager getInstance() {
		if (instance == null) {
			instance = new HopfieldNetworkManager();
		}
		return instance;
	}
	
	public double[] calculate(double[] input) {
		for (int i = 0; i < input.length; i++) {
			System.out.println(input[i]);
		}
		network.setInput(input);
		network.calculate();
		System.out.println(network.getOutput()[0]);
		if (network.getOutput()[0] == 0d) {
	        JOptionPane.showMessageDialog(null, "Vocal A", "Information", JOptionPane.INFORMATION_MESSAGE);
		} 
		if (network.getOutput()[0] == 1d) {
			JOptionPane.showMessageDialog(null, "Vocal I", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		return network.getOutput();
	}

	/**
	 * @return the network
	 */
	public Hopfield getNetwork() {
		return network;
	}

	/**
	 * @param network the network to set
	 */
	public void setNetwork(Hopfield network) {
		this.network = network;
	}
	
	
}
