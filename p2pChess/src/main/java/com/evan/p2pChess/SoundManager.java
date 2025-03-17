package com.evan.p2pChess;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.evan.p2pChess.Gui.Settings;

import java.net.URL;

public class SoundManager {
    public static void play(URL soundUrl) {
        if (!Settings.getIsSoundEnabled()) { //If sounds are not enabled don't play any audio
            return;
        }

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrl);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.err.println("Error" + e.getMessage());
        }
    }
}
