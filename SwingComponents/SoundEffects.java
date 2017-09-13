package SwingComponents;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffects {
	private static final String BACKGROUND_MUSIC = "/background.wav";
	private static final String NOSOUNDFX = "/NOsoundFX.wav";
	private static final String CLICKFX = "/ClicksoundFX.wav";
	private Clip backgroundClip;

	/**
	 * plays the background music
	 */

	public void playBackgroundMusic() {
		try {
			backgroundClip = AudioSystem.getClip();
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(this.getClass().getResource(BACKGROUND_MUSIC));
			backgroundClip.open(audioInputStream);
			backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stops the background music
	 */

	public void stopBackgroundMusic() {
		backgroundClip.stop();
	}

	/**
	 * Plays the 'NO' sound
	 */

	public void playNo() {
		try {
			Clip invalidMove = AudioSystem.getClip();
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(NOSOUNDFX));
			invalidMove.open(audioInputStream);
			invalidMove.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Plays a clicking sound
	 */

	public void playButtonClick() {
		try {
			Clip buttonClick = AudioSystem.getClip();
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(CLICKFX));
			buttonClick.open(audioInputStream);
			buttonClick.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

}
