package model;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Kelas abstrak GameObject sebagai cetak biru untuk objek dalam game.
 */
public abstract class GameObject {

    // Properti posisi objek
    protected float x;
    protected float y;

    // Properti ukuran objek
    protected int width;
    protected int height;

    // Box pendeteksi collision (tabrakan)
    protected Rectangle collisionBox;

    /**
     * Konstruktor untuk kelas GameObject.
     *
     * @param x posisi x objek
     * @param y posisi y objek
     * @param width lebar objek
     * @param height tinggi objek
     */
    public GameObject(float x, float y, int width, int height) {
        // Inisialisasi properti posisi dan ukuran
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        // Membuat kotak baru untuk pendeteksi collision
        this.collisionBox = new Rectangle((int)x, (int)y, width, height);
    }

    /**
     * Metode untuk mengupdate posisi kotak collision sesuai dengan posisi objek.
     */
    protected void updateCollisionBox(){
        // Mengupdate posisi kotak collision
        this.collisionBox.x = (int) x;
        this.collisionBox.y = (int) y;
    }

    /**
     * Mengambil kotak collision objek.
     *
     * @return kotak collision berbentuk Rectangle
     */
    public Rectangle getCollisionBox() {
        // Mengembalikan kotak collision
        return this.collisionBox;
    }

    /**
     * Metode abstrak untuk merender objek.
     * Harus diimplementasikan oleh kelas turunan.
     *
     * @param g objek Graphics untuk menggambar objek
     */
    public abstract void render(Graphics g);
}