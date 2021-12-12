package bomberman.Sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;

public class Sound {

    private static final String BACKGROUND_SOUND_PATH = "/sound/BomberManMusic.mp3";
    private static final String EXPLOSION_SOUND_PATH = "/sound/explosionSound2.wav";
    private static final String LOSING_SOUND_PATH = "/sound/youLost.wav";
    private static Media backgroundSound;
    private static MediaPlayer backgroundPlayer;
    private static Media explosionSound;
    private static MediaPlayer explosionPlayer;
    private static Media losingSound;
    private static MediaPlayer losingSoundPlayer;
    private static boolean isMute = false;

    public static void init() {
        try {
            backgroundSound = new Media(Sound.class.getResource(BACKGROUND_SOUND_PATH).toURI().toString());
            explosionSound = new Media(Sound.class.getResource(EXPLOSION_SOUND_PATH).toURI().toString());
            losingSound = new Media(Sound.class.getResource(LOSING_SOUND_PATH).toURI().toString());
            backgroundPlayer = new MediaPlayer(backgroundSound);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void playBackgroundMusic() {
        backgroundPlayer = new MediaPlayer(backgroundSound);
        if (isMute) {
            backgroundPlayer.setMute(true);
        } else {
            backgroundPlayer.play();
        }
    }

    public static void playExplosionSound() {
        explosionPlayer = new MediaPlayer(explosionSound);
        if (isMute) {
            explosionPlayer.setMute(true);
        } else {
            explosionPlayer.play();
        }
    }

    public static void playLosingSound() {
        losingSoundPlayer = new MediaPlayer(losingSound);
        if (isMute) {
            losingSoundPlayer.setMute(true);
        } else {
            losingSoundPlayer.play();
        }
    }

    public static void mute() {
        backgroundPlayer.setMute(true);
    }

    public static void unmute() {
        backgroundPlayer.setMute(false);
    }

    public static void stop() {
        backgroundPlayer.setMute(true);
    }

    public static void setIsMute(boolean value) {
        isMute = value;
    }
}
