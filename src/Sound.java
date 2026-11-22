// Name: Emilee Zhang, Nevetha Arullinkam
// Date: 5/28/2025
// Description: Fortune's Fast Food is a game where the player works at a burger shop, taking and fulfilling orders as quickly and accurately as possible

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import javax.sound.sampled.*;

public class Sound {
        public static Clip clip;

        public Sound() {
                try {
                        clip = AudioSystem.getClip();
                } catch (Exception e) {
                        System.out.println("Error initializing audio: " + e);
                }
        }

        // play music
        // CREDIT TO THE YOUTUBE VIDEO LINKED ABOVE
        // Allows for the program to have music looping continuously
        public static void playMusic() {
                try {
                        File file = new File("background.wav");

                        if (file.exists()) {
                                AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
                                clip.open(audioInput);
                                clip.start();
                                clip.loop(Clip.LOOP_CONTINUOUSLY);

                        } else {
                                System.out.println("Can't find music file");
                        }
                } catch (Exception e) {
                        System.out.println(e);
                }
        }

        // stop the background music
        public static void stopMusic() {
                if (clip != null && clip.isRunning()) {
                        clip.stop();
                        clip.close();
                }
        }

        // plays background music
        // references above, just creates a new clip instead each time so that new sound
        // effects can play each time
        public void playSoundEffect(String filepath) {
                try {
                        File file = new File(filepath);
                        if (file.exists()) {
                                AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
                                Clip effectClip = AudioSystem.getClip(); // New clip for each sound effect
                                effectClip.open(audioInput);
                                effectClip.start();
                        } else {
                                System.out.println("Can't find sound effect file");
                        }
                } catch (Exception e) {
                        System.out.println("Error playing sound effect: " + e);
                }
        }
}
