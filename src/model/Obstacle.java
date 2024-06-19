package model;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.util.Objects;
import java.util.Random;
// Mengakses viewmodel
import viewmodel.Constants;


import javax.swing.ImageIcon;

import static viewmodel.Constants.gameOption.GAME_SPEED;

/**
 * Kelas Obstacle untuk mengatur rintangan dalam game.
 */
public class Obstacle extends GameObject {
    // Properti obstacle
    private final int obsType; // Penentu tipe obstacle (lantai/pilar)
    private final int skor; // Skor yang diperoleh saat melewati obstacle
    private int cek_skor_input; // Penanda apakah skor sudah dihitung

    // Inisialisasi library
    Random rand = new Random(); // Fungsi untuk menghasilkan nilai acak
    Color color; // Warna obstacle

    Image image; // Gambar untuk obstacle

    /**
     * Konstruktor untuk kelas Obstacle.
     *
     * @param x          posisi x obstacle
     * @param y          posisi y obstacle
     * @param width      lebar obstacle
     * @param height     tinggi obstacle
     * @param obsType    tipe obstacle (0 untuk lantai, 1 untuk pilar)
     * @param skor       skor yang diberikan oleh obstacle
     * @param imagepaths path gambar untuk obstacle
     */
    public Obstacle(float x, float y, int width, int height, int obsType, int skor, String imagepaths) {
        super(x, y, width, height);
        this.obsType = obsType;
        this.skor = skor;
        this.cek_skor_input = 0;
        this.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagepaths))).getImage();
        setColorObstacle();
    }

    /**
     * Metode untuk mengatur warna obstacle.
     * warnanya diacak dengan rentang lebih gelap.
     */
    private void setColorObstacle() {

        int r = ((Constants.gameOption.GAME_HEIGHT / 255) * height) % 128; // Rentang lebih gelap 0-127
        int g = rand.nextInt(128); // Rentang lebih gelap 0-127
        int b = rand.nextInt(128); // Rentang lebih gelap 0-127
        color = new Color(r, g, b); // Warna acak untuk pilar

    }

    /**
     * Metode untuk mengupdate posisi dan collisionBox obstacle.
     */
    public void update() {
        updatePos();
        updateCollisionBox();
    }

    /**
     * Metode untuk merender obstacle.
     *
     * @param g objek Graphics untuk menggambar obstacle
     */
    @Override
    public void render(Graphics g) {
        if (y == 0) {
            g.setColor(color); // Mengatur warna border
            int borderWidth = 3; // Setel lebar border
            for (int i = 0; i < borderWidth; i++) {
                g.drawRect((int) x - i, (int) y - i, width + 2 * i, height + 2 * i); // Menggambar border persegi panjang yang lebih lebar
            }
        } else {
            g.drawImage(image, (int) x, (int) y, width, height, null); // Menggambar gambar obstacle
        }
    }

    /**
     * Metode untuk mengupdate posisi obstacle.
     * Jika tipe obstacle lebih dari 0, posisinya bergerak.
     */
    private void updatePos() {
        if (obsType > 0) {
            x += GAME_SPEED;
        }
    }

    /**
     * Mengambil nilai x dari obstacle.
     *
     * @return posisi x obstacle
     */
    public float getX() {
        return x;
    }

    /**
     * Mengambil nilai y dari obstacle.
     *
     * @return posisi y obstacle
     */
    public float getY() {
        return y;
    }

    /**
     * Mengambil skor yang diberikan oleh obstacle.
     *
     * @return skor obstacle
     */
    public int getSkor() {
        return skor;
    }

    /**
     * Menandai bahwa skor sudah dihitung.
     */
    public void setCek() {
        this.cek_skor_input = 1;
    }

    /**
     * Mengecek apakah skor sudah dihitung.
     *
     * @return 1 jika skor sudah dihitung, 0 jika belum
     */
    public int getCek() {
        return cek_skor_input;
    }
}
