package viewmodel;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

    /**
     * Memainkan suara dari file audio.
     *
     * @param clip Clip audio yang akan dimainkan.
     * @param filename Nama file audio yang akan dimainkan.
     * @param loop Menentukan apakah audio akan di-loop secara terus menerus.
     * @return Clip audio yang dimainkan.
     */
    public Clip playSound(Clip clip, String filename, boolean loop) {
        // Konstruktor
        try {
            // Mengambil audio input stream dari file yang ditentukan
            AudioInputStream audioIn = null;
            try {
                audioIn = AudioSystem.getAudioInputStream(new File("resources/" + filename).getAbsoluteFile());

            } catch (UnsupportedAudioFileException | IOException e) {
                audioIn = AudioSystem.getAudioInputStream(new File("src/resources/" + filename).getAbsoluteFile());
            }
            // Mendapatkan resource clip audio
            clip = AudioSystem.getClip();
            // Membuka clip audio dan memuat sampel dari audio input stream
            clip.open(audioIn);
            // Memulai pemutaran audio
            clip.start();
            if (loop) {
                // Jika parameter loop adalah true, loop audio secara terus menerus
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            // Menangani kesalahan jika line audio tidak tersedia
            e.printStackTrace();
        }
        // Mengembalikan clip audio yang dimainkan
        return clip;
    }

    /**
     * Menghentikan pemutaran suara.
     *
     * @param clip Clip audio yang akan dihentikan.
     */
    public void stopSound(Clip clip) {
        // Menghentikan pemutaran musik
        clip.stop();
    }
}
