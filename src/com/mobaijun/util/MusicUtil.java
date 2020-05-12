package com.mobaijun.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MusicUtil {
    private static Clip clip;
    static {
        File bgMusicFile = new File("music/bg.wav");
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(bgMusicFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2.播放
    public static void playBackground(){
        //循环播放
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
