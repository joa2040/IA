package ar.edu.utn.frlp.IA.logic;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class FileUtil {

	private static final Logger LOGGER = Logger.getAnonymousLogger();

	private FileUtil() {};

	public static byte[] readFile(String pathname) {
		FileInputStream in = null;
		BufferedInputStream buffer = null;
		DataInputStream dataInput = null;
		try {
			File file = new File(pathname);
			byte bytes[] = new byte[(int) file.length()];
			in = new FileInputStream(file);
			buffer = new BufferedInputStream(in);
			dataInput = new DataInputStream(buffer);
			dataInput.read(bytes);
			return bytes;
		} catch (IOException e) {
			return null;
		} finally {
			try {
				if (dataInput != null) {
					dataInput.close();
				}
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
			try {
				if (buffer != null) {
					buffer.close();
				}
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}
	
	public static double[] byteArray2BitArray(byte[] bytes) {
		double[] bits = new double[bytes.length * 8];
		for (int i = 0; i < bytes.length * 8; i++) {
			bits[i] = (bytes[i / 8] & (1 << (7 - (i % 8)))) > 0 ? 1D : 0D;
		}
		System.out.println(bits.length);
		return bits;
	}
	
	/**
	 * Metodo que asigna el valor 1 o 0 segun la amplitud sea positiva o negativa, respectivamente
	 * 
	 * @param bytes
	 * @return
	 */
	public static double[] byteArray2DoubleArray(byte[] bytes) {
		double[] bits = new double[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			bits[i] = bytes[i] > 0 ? 1d : 0d;
		}
		System.out.println(bits.length);
		return bits;
	}
	
	public static byte[] BitArrayToByteArray(double[] bits, int startIndex)
    {
      // Get the size of bytes needed to store all bytes
      int bytesize = bits.length / 8;

      // Any bit left over another byte is necessary
      if (bits.length % 8 > 0)
        bytesize++;

      // For the result
      byte[] bytes = new byte[bytesize];

      // Must init to good value, all zero bit byte has value zero
      // Lowest significant bit has a place value of 1, each position to
      // to the left doubles the value
      byte value = 0;
      byte significance = 1;

      // Remember where in the input/output arrays
      int bytepos = 0;
      int bitpos = startIndex;

      while (bitpos - startIndex < bits.length)
      {
        // If the bit is set add its value to the byte
        if (bits[bitpos] == 1d)
          value += significance;

        bitpos++;

        if (bitpos % 8 == 0)
        {
          // A full byte has been processed, store it
          // increase output buffer index and reset work values
          bytes[bytepos] = value;
          bytepos++;
          value = 0;
          significance = 1;
        }
        else
        {
          // Another bit processed, next has doubled value
          significance *= 2;
        }
      }
      return bytes;
    }
}
