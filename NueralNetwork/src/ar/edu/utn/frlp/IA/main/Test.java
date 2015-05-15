package ar.edu.utn.frlp.IA.main;

import org.jtransforms.fft.DoubleFFT_1D;

import ar.edu.utn.frlp.IA.logic.FileUtil;

public class Test {

	public static void main(String[] args) {
		byte[] bytes = FileUtil.readFile("dataSet4");
		double[] doubles = new double[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			doubles[i] = (double) bytes[i];
			System.out.println(doubles[i]);
		}
		DoubleFFT_1D fft = new DoubleFFT_1D(doubles.length);
		fft.realForward(doubles);
		double sum = 0;
		for (int i = 0; i < doubles.length; i++) {
			sum += Math.abs(doubles[i]);
		}
		double mean = sum / doubles.length;
		System.out.println(mean);
		double threshold = 8*mean;
		System.out.println(threshold);
		for (int i = 0; i < doubles.length; i++) {
			doubles[i] = Math.abs(doubles[i]) < threshold ? 0 : doubles[i];
		}
		fft.realInverse(doubles, Boolean.TRUE);
		for (int i = 0; i < doubles.length; i++) {
			System.out.println((int)doubles[i]);
		}
		System.out.println("-------------");
		doubles = FileUtil.byteArray2DoubleArray(bytes);
		for (int i = 0; i < doubles.length; i++) {
			System.out.println((int)doubles[i]);
		}
//		System.out.println("");		
	}

}
