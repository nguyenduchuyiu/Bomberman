package uet.oop.bomberman;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() {
		
		soundURL[0] = getClass().getResource("/sound/playgame1.mid");
		soundURL[1] = getClass().getResource("/sound/destroy.wav");
		soundURL[2] = getClass().getResource("/sound/newbomb.wav");
		soundURL[3] = getClass().getResource("/sound/bomb_bang.wav");
		soundURL[4] = getClass().getResource("/sound/startstage.wav");
		soundURL[5] = getClass().getResource("/sound/bomber_die.wav");
		soundURL[6] = getClass().getResource("/sound/item.wav");
		soundURL[7] = getClass().getResource("/sound/monster_die.wav");
		soundURL[8] = getClass().getResource("/sound/win.wav");
		soundURL[9] = getClass().getResource("/sound/lose.mid");
		
	}
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		}catch(Exception e) {
			
		}
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
}