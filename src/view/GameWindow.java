// Saya Ilham AKbar dengan nim 2201017 mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
// Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
// tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

package view;

import java.awt.Canvas;
import javax.swing.JFrame;
import viewmodel.Game;
import viewmodel.KeyInputs;
import static viewmodel.Constants.gameOption.GAME_HEIGHT;
import static viewmodel.Constants.gameOption.GAME_WIDTH;

// Kelas GameWindow yang meng-extend Canvas untuk digambar
public class GameWindow extends Canvas {
    // Properti JFrame untuk menampung frame utama
    JFrame frame;

    // Konstruktor untuk membuat jendela permainan
    public GameWindow(Game game) {
        String title = "Up Down | Playing"; // Judul jendela permainan
        frame = new JFrame(title); // Membuat objek JFrame dengan judul
        frame.addKeyListener(new KeyInputs(game)); // Menambahkan key listener yang terhubung dengan permainan
        frame.add(game); // Menambahkan objek permainan ke dalam frame
        frame.setSize(GAME_WIDTH, GAME_HEIGHT); // Mengatur ukuran frame sesuai konstanta yang ditentukan
        frame.setLocationRelativeTo(null); // Mengatur lokasi frame ke tengah layar
        frame.setResizable(false); // Mengatur agar ukuran frame tidak bisa diubah oleh pengguna
        frame.setVisible(true); // Menampilkan frame agar terlihat oleh pengguna
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Mengatur operasi default ketika tombol close ditekan
    }

    // Method untuk menutup jendela permainan
    public void CloseWindow() {
        frame.setVisible(false); // Mengatur agar frame menjadi tidak terlihat
        frame.dispose(); // Membersihkan sumber daya yang terkait dengan frame
    }
}
