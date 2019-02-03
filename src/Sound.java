import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	private Clip sound[];
	private int maxNbSounds;
	private int maxNbType;
	private int nbSounds[];
	private int indSounds[];
	/* type: 0: jump carac, 
	 * 1: land, 
	 * 2:TP carac, 
	 * 3: void carac, 
	 * 4: trampo, 
	 * 5: tir1, simple shot, machine gun, jack yellow 3
	 * 6: tir2, bomb
	 * 7: tir22, bomb explosion
	 * 8: tir3, sniper
	 * 9: tir4, fire
	 * 10: tir7, shotgun
	 * 11: EnnemyHit,
	 * 12: EnnemyDie,
	 * 13: SlugJump, 
	 * 14: SlugHit,
	 * 15: SlugDie*/
	
	
	public Sound() {
		
		int type = 0;
		int ind = 0;
		maxNbSounds = 100;
		maxNbType = 20;
		nbSounds = new int[maxNbType];
		indSounds = new int[maxNbType];
		sound = new Clip[maxNbSounds];

		// Jump charac
		nbSounds[type] = 3;
		for(int i = 0; i<nbSounds[type]; i++) {
			File file = new File("files/sounds/jump.wav");
			try {
				sound[ind] = AudioSystem.getClip();
				sound[ind].open(AudioSystem.getAudioInputStream(file));
				ind = ind + 1;
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		type = type + 1;
		
		// land
		nbSounds[type] = 3;
		for(int i = 0; i<nbSounds[type]; i++) {
			File file = new File("files/sounds/land.wav");
			try {
				sound[ind] = AudioSystem.getClip();
				sound[ind].open(AudioSystem.getAudioInputStream(file));
				ind = ind + 1;
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		type = type + 1;
		
		// TP charac
		nbSounds[type] = 1;
		for(int i = 0; i<nbSounds[type]; i++) {
			File file = new File("files/sounds/tp.wav");
			try {
				sound[ind] = AudioSystem.getClip();
				sound[ind].open(AudioSystem.getAudioInputStream(file));
				ind = ind + 1;
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		type = type + 1;	
		
		// void charac
		nbSounds[type] = 1;
		for(int i = 0; i<nbSounds[type]; i++) {
			File file = new File("files/sounds/void.wav");
			try {
				sound[ind] = AudioSystem.getClip();
				sound[ind].open(AudioSystem.getAudioInputStream(file));
				ind = ind + 1;
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		type = type + 1;
		
		// trampo
		nbSounds[type] = 6;
		for(int i = 0; i<nbSounds[type]; i++) {
			File file = new File("files/sounds/trampo.wav");
			try {
				sound[ind] = AudioSystem.getClip();
				sound[ind].open(AudioSystem.getAudioInputStream(file));
				ind = ind + 1;
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		type = type + 1;
		
		// tir1
		nbSounds[type] = 10;
		for(int i = 0; i<nbSounds[type]; i++) {
			File file = new File("files/sounds/tir1.wav");
			try {
				sound[ind] = AudioSystem.getClip();
				sound[ind].open(AudioSystem.getAudioInputStream(file));
				ind = ind + 1;
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		type = type + 1;
		
		// tir2
		nbSounds[type] = 2;
		for(int i = 0; i<nbSounds[type]; i++) {
			File file = new File("files/sounds/tir2.wav");
			try {
				sound[ind] = AudioSystem.getClip();
				sound[ind].open(AudioSystem.getAudioInputStream(file));
				ind = ind + 1;
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		type = type + 1;
		
		// tir22
		nbSounds[type] = 2;
		for(int i = 0; i<nbSounds[type]; i++) {
			File file = new File("files/sounds/tir22.wav");
			try {
				sound[ind] = AudioSystem.getClip();
				sound[ind].open(AudioSystem.getAudioInputStream(file));
				ind = ind + 1;
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		type = type + 1;
		
		// tir3
		nbSounds[type] = 2;
		for(int i = 0; i<nbSounds[type]; i++) {
			File file = new File("files/sounds/tir3.wav");
			try {
				sound[ind] = AudioSystem.getClip();
				sound[ind].open(AudioSystem.getAudioInputStream(file));
				ind = ind + 1;
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		type = type + 1;
		
		// tir4
		nbSounds[type] = 6;
		for(int i = 0; i<nbSounds[type]; i++) {
			File file = new File("files/sounds/tir4.wav");
			try {
				sound[ind] = AudioSystem.getClip();
				sound[ind].open(AudioSystem.getAudioInputStream(file));
				ind = ind + 1;
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		type = type + 1;
		
		// tir7
		nbSounds[type] = 4;
		for(int i = 0; i<nbSounds[type]; i++) {
			File file = new File("files/sounds/tir7.wav");
			try {
				sound[ind] = AudioSystem.getClip();
				sound[ind].open(AudioSystem.getAudioInputStream(file));
				ind = ind + 1;
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		type = type + 1;
		
		// EnnemyHit
		nbSounds[type] = 2;
		for(int i = 0; i<nbSounds[type]; i++) {
			File file = new File("files/sounds/EnnemyHit.wav");
			try {
				sound[ind] = AudioSystem.getClip();
				sound[ind].open(AudioSystem.getAudioInputStream(file));
				ind = ind + 1;
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		type = type + 1;
		
		// EnnemyDie
		nbSounds[type] = 2;
		for(int i = 0; i<nbSounds[type]; i++) {
			File file = new File("files/sounds/EnnemyDie.wav");
			try {
				sound[ind] = AudioSystem.getClip();
				sound[ind].open(AudioSystem.getAudioInputStream(file));
				ind = ind + 1;
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		type = type + 1;
		
		// SlugJump
		nbSounds[type] = 10;
		for(int i = 0; i<nbSounds[type]; i++) {
			File file = new File("files/sounds/SlugJump.wav");
			try {
				sound[ind] = AudioSystem.getClip();
				sound[ind].open(AudioSystem.getAudioInputStream(file));
				ind = ind + 1;
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		type = type + 1;
		
		// SlugHit
		nbSounds[type] = 2;
		for(int i = 0; i<nbSounds[type]; i++) {
			File file = new File("files/sounds/SlugHit.wav");
			try {
				sound[ind] = AudioSystem.getClip();
				sound[ind].open(AudioSystem.getAudioInputStream(file));
				ind = ind + 1;
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		type = type + 1;
		
		// SlugDie
		nbSounds[type] = 2;
		for(int i = 0; i<nbSounds[type]; i++) {
			File file = new File("files/sounds/SlugDie.wav");
			try {
				sound[ind] = AudioSystem.getClip();
				sound[ind].open(AudioSystem.getAudioInputStream(file));
				ind = ind + 1;
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		type = type + 1;
	}
	
	public void play (int type) {
		int ind = getInd(type);
		sound[ind].stop();
		sound[ind].setMicrosecondPosition(0);
		sound[ind].start();
		indSounds[type] = (indSounds[type] + 1) % nbSounds[type];
	}
	
	private int getInd(int type) {
		int ind = 0;
		for(int i=0; i<type; i++) {
			ind = ind + nbSounds[i];
		}
		ind = ind + indSounds[type];
		return ind;
	}
}
