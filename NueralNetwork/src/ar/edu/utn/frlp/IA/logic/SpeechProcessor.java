package ar.edu.utn.frlp.IA.logic;

import org.jtransforms.fft.DoubleFFT_1D;

public final class SpeechProcessor {

	private SpeechProcessor() {
	};

	public static byte[] processSpeech(byte[] bytes) {
		double[] doubles = new double[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			doubles[i] = (double) bytes[i];
		}
		DoubleFFT_1D fft = new DoubleFFT_1D(doubles.length);
		fft.realForward(doubles);
		double threshold = calculateThreshold(doubles);
		bandPassFilter(doubles, threshold);
		fft.realInverse(doubles, Boolean.TRUE);
		return bytesRecover(doubles);
	}

	private static byte[] bytesRecover(double[] doubles) {
		byte[] newbytes = new byte[doubles.length];
		for (int i = 0; i < doubles.length; i++) {
			newbytes[i] = (byte) doubles[i];
		}
		return newbytes;
	}

	private static void bandPassFilter(double[] doubles, double threshold) {
		for (int i = 0; i < doubles.length; i++) {
			doubles[i] = Math.abs(doubles[i]) < threshold ? 0 : doubles[i];
		}
	}

	private static double calculateThreshold(double[] doubles) {
		double sum = 0;
		for (int i = 0; i < doubles.length; i++) {
			sum += Math.abs(doubles[i]);
		}
		double mean = sum / doubles.length;
		double threshold = 1.1 * mean;
		return threshold;
	}
}
