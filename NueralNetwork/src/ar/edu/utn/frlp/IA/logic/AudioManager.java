package ar.edu.utn.frlp.IA.logic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import ar.edu.utn.frlp.IA.neuralNetwork.HopfieldNetworkManager;

public class AudioManager extends Thread {

	private ByteArrayOutputStream out;
	private static final Logger LOGGER = Logger.getAnonymousLogger();

	@Override
	public synchronized void run() {
		FileOutputStream outPut = null;
		try {
			final AudioFormat format = getFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			final TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();
			int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
			byte buffer[] = new byte[bufferSize];

			out = new ByteArrayOutputStream();

			while (out.size() < 8000) {
				LOGGER.log(Level.INFO, "Size {0}",out.size());
				int count = line.read(buffer, 0, buffer.length);
				if (count > 0) {
					out.write(buffer, 0, count);
				}
			}
			LOGGER.log(Level.INFO, "Size {0}",out.size());
//			File file = new File("dataSet4");
//			outPut = new FileOutputStream(file);
//			out.toByteArray()[1800] = new Byte((byte) 00000001);
//			outPut.write(Arrays.copyOfRange(out.toByteArray(), 1800, 2300));
//			LOGGER.log(Level.INFO, "bits {0}", Arrays.copyOfRange(out.toByteArray(), 1800, 2300)[0]);
			notify();
		} catch (LineUnavailableException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			System.exit(-2);
//		} catch (IOException e) {
//			LOGGER.log(Level.SEVERE, e.getMessage(), e);
//			System.exit(-1);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
			try {
				if (outPut != null) {
					outPut.close();
				}
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	public void playAudio() {
//		try {
			//out.toByteArray();
			HopfieldNetworkManager.getInstance().calculate(FileUtil.byteArray2DoubleArray(Arrays.copyOfRange(out.toByteArray(), 1800, 2300)));
//			for (byte b : audio) {
//				System.out.println(b);
//			}
//			InputStream input = new ByteArrayInputStream(audio);
//			final AudioFormat format = getFormat();
//			final AudioInputStream ais = new AudioInputStream(input, format, audio.length / format.getFrameSize());
//			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
//			final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
//			line.open(format);
//			line.start();
//
//			Runnable runner = new Runnable() {
//				int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
//				byte buffer[] = new byte[bufferSize];
//
//				public void run() {
//					try {
//						int count;
//						while ((count = ais.read(buffer, 0, buffer.length)) != -1) {
//							if (count > 0) {
//								line.write(buffer, 0, count);
//							}
//						}
//						line.drain();
//						line.close();
//					} catch (IOException e) {
//						LOGGER.log(Level.SEVERE, e.getMessage(), e);
//						System.exit(-3);
//					}
//				}
//			};
//			Thread playThread = new Thread(runner);
//			playThread.start();
//		} catch (LineUnavailableException e) {
//			LOGGER.log(Level.SEVERE, e.getMessage(), e);
//			System.exit(-4);
//		}
	}

	private AudioFormat getFormat() {
		float sampleRate = 8000;
		int sampleSizeInBits = 8;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}
}
