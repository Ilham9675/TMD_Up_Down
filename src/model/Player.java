
package model;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Objects;
// Mengakses konstanta
import static viewmodel.Constants.gameOption.GAME_WIDTH;
import static viewmodel.Constants.gameOption.GAME_HEIGHT;
import static viewmodel.Constants.gameOption.GAME_SPEED;
/**
 * Kelas Player untuk mengatur pemain dalam game.
 */
public class Player extends GameObject {
    private boolean left;
    private boolean up;
    private boolean right;
    private boolean inAir = true;

    private float airSpeed = 0;
    private float xSpeed = 0;

    private int skor = 0;

    private int skorup = 0;

    private int skordown = 0;

    private int count = 0;
    private int tempY = 0;

    private final Image backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("../assets/background.png"))).getImage();

    /**
     * Konstruktor untuk kelas Player.
     *
     * @param x posisi x pemain
     * @param y posisi y pemain
     */
    public Player(int x, int y) {
        // Konstruktor, set properti parent
        super(x, y, 50, 50);
    }

    /**
     * Mengupdate posisi pemain berdasarkan obstacle.
     *
     * @param ob daftar obstacle dalam game
     */
    public void update(ArrayList<Obstacle> ob) {
        updatePos(ob);
        updateCollisionBox();
    }

    /**
     * Merender pemain dan latar belakang.
     *
     * @param g objek Graphics untuk menggambar pemain dan latar belakang
     */
    @Override
    public void render(Graphics g) {
        // Membuat background
        g.drawImage(backgroundImage, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);

        // Membuat player
        g.setColor(new Color(242, 242, 2));
        g.fillOval((int) x, (int) y, 50, 50);

        // Menggambar wajah player
        g.setColor(Color.BLACK);
        g.fillOval((int) x + 10, (int) y + 12, 7, 7);
        g.fillOval((int) x + 30, (int) y + 12, 7, 7);
        g.drawArc((int) x + 12, (int) y + 5, 25, 30, -30, -120);

        // Menampilkan skor, up, dan down
        g.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 13));
        g.setColor(Color.WHITE);
        g.fillRoundRect(15, 5, 100, 20, 15, 15);
        g.fillRoundRect(15, 35, 100, 20, 15, 15);
        g.fillRoundRect(15, 65, 100, 20, 15, 15);
        g.setColor(Color.decode("#3A1070"));
        g.drawString("Skor : " + this.skor, 20, 20);
        g.drawString("Up : " + this.skorup, 20, 50);
        g.drawString("Down : " + this.skordown, 20, 80);
    }

    /**
     * Mengupdate posisi pemain berdasarkan input dan collision dengan obstacle.
     *
     * @param AOb daftar obstacle dalam game
     */
    public void updatePos(ArrayList<Obstacle> AOb) {
        float playerSpeed = 5.0f;
        if (left && right || !left && !right) {
            // Jika klik kanan dan kiri atau tidak klik kanan dan kiri
            // kecepatan player biasa (mengikuti game)
            xSpeed = GAME_SPEED;
        } else if (left) {
            // Jika klik kiri
            // kecepatan player berkurang
            xSpeed -= playerSpeed;
        } else if (right) {
            // Jika klik kanan
            // kecepatan player bertambah
            xSpeed += playerSpeed + 1;
        }

        if (xSpeed > 4) {
            // Kecepatan maks 4
            xSpeed = 4;
        } else if (xSpeed < -4) {
            // Kecepatan min -4
            xSpeed = -4;
        }

        // Lompat
        if (up && !inAir) {
            inAir = true;
            float jumpStrength = 15.0f;
            airSpeed -= jumpStrength;
        }

        // Di lantai
        if (!inAir && !isOnFloor(AOb)) {
            inAir = true;
        }

        // Di udara
        if (inAir) {
            float gravity = 0.3f;
            airSpeed += gravity;
        }

        for (Obstacle ob : AOb) {
            // Untuk setiap obstacle
            if (getBoundBottom().intersects(ob.getCollisionBox())) {
                // Jika bound bawah player beririsan dengan bound collisionBox
                inAir = false; // Berarti tidak di udara
                airSpeed = 0; // Kecepatan udara 0
                if (ob.getCollisionBox().y != 0) {
                    
                    y = ob.getCollisionBox().y - height; // y tempat collision
                }

                if (count == 0) {
                    // Jika game baru dimulai
                    tempY = (int) y;
                    count++;
                }

                if (tempY != y) {
                    // Jika y player bertabrakan sebelumnya tidak sama dengan y sekarang
                    if (y < 500) {
                        // Jika y < 500 berarti collision dengan pilar
                        if (ob.getY() != 0 && ob.getCek() == 0) {
                            ob.setCek();
                            skordown += ob.getSkor();
                        } else if(ob.getCek() == 0) {
                            ob.setCek();
                            skorup += ob.getSkor();
                        }

                        skor = skorup + skordown;
                    }
                    tempY = (int) y;
                }
            }

            // Jika player nabrak, kembalikan ke speed normal
            if (getBoundRight().intersects(ob.getCollisionBox()) && ob.getY() != 0) {
                // Jika nabrak kanan

                x = ob.getCollisionBox().x - width - 1;

            }


            if (getBoundLeft().intersects(ob.getCollisionBox()) && ob.getY() != 0) {
                // Jika nabrak kiri

                x = ob.getCollisionBox().x + ob.getCollisionBox().width + 1;
            }

            if (getBoundTop().y < 0) {
                // Jika nabrak atas
                y = 0;
                airSpeed = 0;

            }
        }
        x += xSpeed;
        y += airSpeed;
    }

    /**
     * Mengecek apakah pemain berada di lantai.
     *
     * @param AOb daftar obstacle dalam game
     * @return true jika berada di lantai, false jika tidak
     */
    public boolean isOnFloor(ArrayList<Obstacle> AOb) {
        for (Obstacle ob : AOb) {
            // Jika batas bawah player beririsan dengan collision box
            if (getBoundBottom().intersects(ob.getCollisionBox())) return true;
        }
        return false;
    }

    /**
     * Membuat batas bawah pemain.
     *
     * @return batas bawah sebagai Rectangle
     */
    public Rectangle getBoundBottom() {
        return new Rectangle((int) (x + (width / 2) - (width / 4)), (int) (y + (height / 2)), width / 2, height / 2);
    }

    /**
     * Membuat batas atas pemain.
     *
     * @return batas atas sebagai Rectangle
     */
    public Rectangle getBoundTop() {
        return new Rectangle((int) (x + (width / 2) - (width / 4)), (int) (y), width / 2, height / 2);
    }

    /**
     * Membuat batas kanan pemain.
     *
     * @return batas kanan sebagai Rectangle
     */
    public Rectangle getBoundRight() {
        return new Rectangle((int) x + width - 5, (int) y + 5, 5, height - 10);
    }

    /**
     * Membuat batas kiri pemain.
     *
     * @return batas kiri sebagai Rectangle
     */
    public Rectangle getBoundLeft() {
        return new Rectangle((int) x, (int) y + 5, 5, height - 10);
    }

    /**
     * Mengatur gerakan pemain ke kiri.
     *
     * @param left true jika bergerak ke kiri, false jika tidak
     */
    public void setLeft(boolean left) {
        this.left = left;
    }

    /**
     * Mengatur gerakan pemain ke atas.
     *
     * @param up true jika bergerak ke atas, false jika tidak
     */
    public void setUp(boolean up) {
        this.up = up;
    }

    /**
     * Mengatur gerakan pemain ke kanan.
     *
     * @param right true jika bergerak ke kanan, false jika tidak
     */
    public void setRight(boolean right) {
        this.right = right;
    }

    /**
     * Mengatur gerakan pemain ke bawah.
     *
     * @param down true jika bergerak ke bawah, false jika tidak
     */
    public void setDown(boolean down) {
        // Implementasi kosong karena tidak ada logika untuk gerakan ke bawah
    }

    /**
     * Mengambil skor pemain.
     *
     * @return skor pemain
     */
    public int getSkor() {
        return skor;
    }

    /**
     * Mengatur skor pemain.
     *
     * @param skor skor baru pemain
     */
    public void setSkor(int skor) {
        this.skor = skor;
    }

    /**
     * Mengambil skor naik pemain.
     *
     * @return skor naik pemain
     */
    public int getSkorup() {
        return skorup;
    }

    /**
     * Mengatur skor naik pemain.
     *
     * @param skorup skor naik baru pemain
     */
    public void setSkorup(int skorup) {
        this.skorup = skorup;
    }

    /**
     * Mengambil skor turun pemain.
     *
     * @return skor turun pemain
     */
    public int getSkordown() {
        return skordown;
    }

    /**
     * Mengatur skor turun pemain.
     *
     * @param skordown skor turun baru pemain
     */
    public void setSkordown(int skordown) {
        this.skordown = skordown;
    }
}
