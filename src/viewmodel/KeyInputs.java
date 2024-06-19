package viewmodel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
// Mengakses properti
import viewmodel.Game.STATE;
import static viewmodel.Constants.gameOption.GAME_SPEED;

public class KeyInputs implements KeyListener {
    // Mewarisi interface KeyListener agar bisa menerima input keyboard

    // Properti game
    private final Game game;

    // Konstruktor untuk KeyInputs
    public KeyInputs(Game game) {
        // Menginisialisasi properti game
        this.game = game; // Set properti game
    }

    // Method dari KeyListener yang dipanggil saat tombol ditekan dan dilepaskan
    @Override
    public void keyTyped(KeyEvent e) {
        // Tidak digunakan tetapi harus ada karena bagian dari interface KeyListener
    }

    /**
     * Method dari KeyListener yang dipanggil saat tombol ditekan
     *
     * @param e KeyEvent yang memuat informasi tombol yang ditekan
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Daftar key untuk bergerak 4 arah
            case KeyEvent.VK_UP:
                // Tombol atas ditekan
                game.getPlayer().setUp(true); // Set properti up pada player menjadi true
                GAME_SPEED = -2f; // Set kecepatan game menjadi -2f
                break;

            case KeyEvent.VK_LEFT:
                // Tombol kiri ditekan
                game.getPlayer().setLeft(true); // Set properti left pada player menjadi true
                GAME_SPEED = -2f; // Set kecepatan game menjadi -2f
                break;

            case KeyEvent.VK_DOWN:
                // Tombol bawah ditekan
                game.getPlayer().setDown(true); // Set properti down pada player menjadi true
                GAME_SPEED = -2f; // Set kecepatan game menjadi -2f
                break;

            case KeyEvent.VK_RIGHT:
                // Tombol kanan ditekan
                game.getPlayer().setRight(true); // Set properti right pada player menjadi true
                GAME_SPEED = -2f; // Set kecepatan game menjadi -2f
                break;
        }

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            // Tombol spasi ditekan
            game.gameState = STATE.GameOver; // Ubah state game menjadi GameOver
            // System.out.println("stop"); // Logging sederhana (dapat diaktifkan jika diperlukan)
        }
    }

    /**
     * Method dari KeyListener yang dipanggil saat tombol dilepaskan
     *
     * @param e KeyEvent yang memuat informasi tombol yang dilepaskan
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Daftar key untuk berhenti bergerak 4 arah
            case KeyEvent.VK_UP:
                game.getPlayer().setUp(false); // Set properti up pada player menjadi false
                break;
            case KeyEvent.VK_LEFT:
                game.getPlayer().setLeft(false); // Set properti left pada player menjadi false
                break;
            case KeyEvent.VK_DOWN:
                game.getPlayer().setDown(false); // Set properti down pada player menjadi false
                break;
            case KeyEvent.VK_RIGHT:
                game.getPlayer().setRight(false); // Set properti right pada player menjadi false
                break;
        }
    }
}
