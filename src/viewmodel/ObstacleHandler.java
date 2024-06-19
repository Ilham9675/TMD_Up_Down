package viewmodel;

import java.util.Random;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
// Mengakses konstanta
import static viewmodel.Constants.gameOption.GAME_HEIGHT;
import static viewmodel.Constants.gameOption.GAME_WIDTH;
// Mengakses model
import model.Obstacle;

public class ObstacleHandler {
    // Konstanta untuk posisi y minimal dan maksimal
    private final int MIN_Y = GAME_HEIGHT / 2; // Posisi y minimal untuk obstacle
    private final int MAX_Y = GAME_HEIGHT; // Posisi y maksimal (batasan bawah) untuk obstacle
    private final Random rand = new Random(); // Inisialisasi library Random untuk posisi acak

    // Konstanta untuk jumlah dan ukuran obstacle
    private final int MAX_OBSTACLE = 15; // Jumlah maksimum obstacle dalam satu frame
    private final int MIN_GAP = 52; // Lebar gap minimum antar obstacle
    private int OBS_WIDTH = 50; // Lebar obstacle
    private int obstacleNumberfloor = 0; // Jumlah obstacle di lantai
    private int obstacleNumbertop = 0; // Jumlah obstacle di atas

    private final ArrayList<Obstacle> obstacles = new ArrayList<>(); // List untuk menyimpan obstacle

    // Konstruktor
    public ObstacleHandler() {
        // Inisialisasi handler obstacle
        addObstacle(); // Menambahkan obstacle saat inisialisasi
    }

    /**
     * Metode untuk mengupdate posisi dan status obstacle.
     * Menggunakan iterator untuk menghindari ConcurrentModificationException
     * saat menghapus obstacle dari list.
     */
    public void updateObstacle() {
        Iterator<Obstacle> itr = obstacles.iterator(); // Menggunakan iterator untuk menghindari ConcurrentModificationException
        while (itr.hasNext()) {
            Obstacle ob = itr.next(); // Mendapatkan obstacle berikutnya
            if (ob.getX() < -50) {
                // Jika obstacle keluar dari layar (posisi x < -50)
                System.out.println("Removing obstacle at x: " + ob.getX()); // Logging penghapusan obstacle
                if (ob.getY() == 0) {
                    // Jika obstacle berada di atas
                    obstacleNumbertop--; // Kurangi jumlah obstacle di atas
                } else {
                    // Jika obstacle berada di bawah
                    obstacleNumberfloor--; // Kurangi jumlah obstacle di lantai
                }
                itr.remove(); // Hapus obstacle dari list
            } else {
                ob.update(); // Update posisi obstacle
            }
        }
    }

    /**
     * Metode untuk merender obstacle.
     * Menggambar setiap obstacle pada grafik yang diberikan.
     *
     * @param g Grafik yang digunakan untuk merender obstacle
     */
    public void renderObstacle(Graphics g) {
        for (Obstacle ob : obstacles) {
            // Untuk setiap obstacle
            ob.render(g); // Gambar obstacle pada grafik
        }
    }

    /**
     * Metode untuk menambahkan obstacle baru.
     * Membuat obstacle baru dan menambahkannya ke list obstacle
     * baik untuk obstacle di lantai maupun di atas dan di bawah.
     */
    public void addObstacle() {
        if (obstacleNumberfloor < MAX_OBSTACLE) {
            // Jika jumlah obstacle di lantai kurang dari maksimum
            float x = 100; // Posisi x awal obstacle
            float y = rand.nextInt((MAX_Y - 50) - MIN_Y) + MIN_Y; // Posisi y acak antara MIN_Y dan MAX_Y - 50
            if (obstacleNumberfloor > 0) {
                // Jika sudah ada obstacle di lantai
                x = obstacles.get(obstacles.size() - 1).getX() + ((rand.nextInt(4 - 1) + 1) * MIN_GAP); // Set posisi x berdasarkan obstacle sebelumnya
            }
            Obstacle obstacle = new Obstacle(x, y, OBS_WIDTH, (int) (MAX_Y - y), 1, (int) (y + (MAX_Y - y)), "../assets/pillarv3.png"); // Buat obstacle baru
            obstacles.add(obstacle); // Tambahkan obstacle ke list
            obstacleNumberfloor++; // Tambahkan jumlah obstacle di lantai
        }

        if (obstacleNumbertop < MAX_OBSTACLE) {
            // Jika jumlah obstacle di atas kurang dari maksimum
            float x = 0; // Posisi x awal obstacle
            float y = rand.nextInt((MAX_Y - 50) - MIN_Y) + MIN_Y; // Posisi y acak antara MIN_Y dan MAX_Y - 50
            if (obstacleNumbertop > 0) {
                // Jika sudah ada obstacle di atas
                x = obstacles.get(obstacles.size() - 1).getX() + ((rand.nextInt(4 - 1) + 1) * MIN_GAP); // Set posisi x berdasarkan obstacle sebelumnya
            }
            Obstacle obstacle = new Obstacle(x, 0, OBS_WIDTH, (int) (MAX_Y - y), 1, (int) (MAX_Y - y), "../assets/pillarv1.png"); // Buat obstacle baru
            obstacles.add(obstacle); // Tambahkan obstacle ke list
            obstacleNumbertop++; // Tambahkan jumlah obstacle di atas
        }
    }

    /**
     * Metode untuk mengambil list obstacle.
     *
     * @return List obstacle saat ini.
     */
    public ArrayList<Obstacle> getObstacles() {
        return obstacles; // Mengembalikan list obstacle
    }
}
