package viewmodel;

import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.Random;
import javax.sound.sampled.Clip;
// Mengakses model
import model.Player;
import model.TableScore;
// Mengakses konstanta
import static viewmodel.Constants.gameOption.GAME_HEIGHT;
import static viewmodel.Constants.gameOption.GAME_SPEED;
// Mengakses view
import view.GameWindow;
import view.Menu;

public class Game extends JPanel implements Runnable {
    // Mengimplementasikan Runnable untuk membuat thread

    // Properti thread
    private Thread gameThread; // Thread untuk menjalankan game
    private boolean running = false; // Status apakah game berjalan

    private GameWindow window; // Window game
    private Clip audio; // Backsound

    // Properti objek dalam game
    private final Player player; // Objek player
    private final ObstacleHandler obs_handler; // Obstacle handler
    private String username; // Username pemain
    private int skor; // Skor Total
    private int skorup; // Skor untuk obstacle atas
    private int skordown; // Skor untuk obstacle bawah
    private int skorawal; // Skor total awal

    // Enum untuk menentukan status game
    public enum STATE {
        Game, GameOver
    }

    public Game() {
        // Konstruktor

        // Membuat player dengan posisi random
        Random rand = new Random();
        int playerPos = rand.nextInt(1200 - 800) + 800;
        this.player = new Player(100, 200);
        GAME_SPEED = -0f;

        // Membuat obstacle handler
        this.obs_handler = new ObstacleHandler();

        // Membuat backsound
        Sound bgm = new Sound();
        audio = bgm.playSound(this.audio, "02_Main_BGM.wav", true);
    }

    // Mengeset status game
    public STATE gameState = STATE.Game;

    public synchronized void StartGame(GameWindow gw) {
        // Memulai menjalankan game
        gameThread = new Thread(this); // Buat thread baru
        gameThread.start(); // Jalankan thread
        this.window = gw; // Set window
        running = true; // Set status running
    }

    @Override
    public void paint(Graphics g) {
        // Membuat komponen
        super.paint(g); // Panggil method parent
        player.render(g); // Render objek player
        obs_handler.renderObstacle(g); // Render objek obstacle
    }

    @Override
    public void run() {
        // Override method run dari Runnable
        while (true) {
            // Loop game
            try {
                updateGame(); // Update objek game
                repaint(); // Update komponen (memanggil paint())
                Thread.sleep(1000L / 60L); // Pause thread (60 FPS)
                this.skor = player.getSkor(); // Update skor dari player
                this.skorup = player.getSkorup(); // Update skor obstacle atas dari player
                this.skordown = player.getSkordown(); // Update skor obstacle bawah dari player
                if (this.player.getBoundRight().x < 0 || this.player.getBoundBottom().y > GAME_HEIGHT) {
                    // Jika posisi tabrakan player di luar frame kanan atau bawah
                    this.gameState = STATE.GameOver; // Ubah status game menjadi GameOver
                }
                if (gameState == STATE.GameOver) {
                    // Jika state saat ini GameOver
                    Sound bgm = new Sound();
                    bgm.stopSound(this.audio); // Stop backsound
                    saveScore(); // Simpan skor adaptasi dan penurunan
                    close(); // Tutup window
                    new Menu().setVisible(true); // Tampilkan menu
                    stopGame(); // Hentikan game
                }
            } catch (InterruptedException ex) {
                // Log error
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateGame() {
        // Update objek dalam game
        obs_handler.addObstacle(); // Tambah obstacle
        obs_handler.updateObstacle(); // Update kondisi obstacle
        player.update(obs_handler.getObstacles()); // Update kondisi player
    }

    public synchronized void stopGame() {
        // Menghentikan game
        try {
            gameThread.join(); // Hentikan thread
            running = false; // Set status tidak berjalan
        } catch (Exception e) {
            e.printStackTrace(); // Tangani exception
        }
    }

    void close() {
        // Menutup window
        window.CloseWindow(); // Panggil metode CloseWindow pada objek window
    }

    public Player getPlayer() {
        // Mengambil player
        return this.player; // Mengembalikan objek player
    }

    public void setUsername(String username) {
        // Mengeset username game
        this.username = username; // Set properti username
    }

    public void setScore(int skor, int skorup, int skordown, int skorawal) {
        // Mengeset skor player di awal game
        this.skorawal = skorawal; // Set skor awal
        this.player.setSkor(skor); // Set skor player
        this.player.setSkorup(skorup); // Set skor obstacle atas player
        this.player.setSkordown(skordown); // Set skor obstacle bawah player
    }

    public void saveScore() {
        // Menyimpan skor saat game over
        // Mainkan backsound game over
        Sound gobgm = new Sound();
        audio = gobgm.playSound(this.audio, "03_Game_Over.wav", false);
        if (skorawal < skor) {
            try {
                TableScore tscore = new TableScore();
                tscore.insertData(this.username, this.skor, this.skorup, this.skordown); // Simpan skor ke database
            } catch (Exception e) {
                System.out.println(e.getMessage()); // Tampilkan pesan error
            }
        }

        // Menampilkan panel game over
        JOptionPane.showMessageDialog(null, "Username : " + this.username + "\nSkor : " + this.skor + "\nUp : " + this.skorup + "\nDown : " + this.skordown, "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
        // Hentikan sound saat panel game over hilang
        gobgm.stopSound(this.audio);
    }
}
