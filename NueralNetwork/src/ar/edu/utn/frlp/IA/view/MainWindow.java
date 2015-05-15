package ar.edu.utn.frlp.IA.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;

import ar.edu.utn.frlp.IA.logic.AudioManager;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7608033323582201441L;
	private AudioManager audioManager;
	private static final Logger LOGGER = Logger.getAnonymousLogger();

	public MainWindow() {
		super("Inteligencia Artificial");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container content = getContentPane();

		final JButton record = new JButton("Record");
		final JButton play = new JButton("Play");

		record.setEnabled(Boolean.TRUE);
		play.setEnabled(Boolean.FALSE);

		ActionListener recordListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				audioManager = new AudioManager();
				record.setEnabled(Boolean.FALSE);
				play.setEnabled(Boolean.FALSE);
				audioManager.start();
				synchronized(audioManager){
		            try{
		            	LOGGER.log(Level.INFO, "waiting for recording");
		                audioManager.wait();
		                LOGGER.log(Level.INFO, "ok wait");
		            }catch(InterruptedException ex){
		                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
		                System.exit(-2);
		            }
		            play.setEnabled(Boolean.TRUE);
		            record.setEnabled(Boolean.TRUE);
		        }
			}
		};
		record.addActionListener(recordListener);
		content.add(record, BorderLayout.NORTH);

		ActionListener playListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				audioManager.playAudio();
			}
		};
		play.addActionListener(playListener);
		content.add(play, BorderLayout.SOUTH);
	}

}
