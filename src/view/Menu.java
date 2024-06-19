// Saya Ilham AKbar dengan nim 2201017 mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
// Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
// tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.
package view;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TableScore;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

import viewmodel.Game;
import viewmodel.Sound;


// Kelas Menu yang mewarisi JFrame untuk tampilan menu utama
public class Menu extends javax.swing.JFrame {
    public Game game; // Objek permainan
    public Clip audio; // Objek untuk memainkan audio latar belakang
    private String username; // Variabel untuk menyimpan username

    // Konstruktor untuk inisialisasi komponen menu utama
    public Menu() {
        initComponents(); // Memanggil method untuk menginisialisasi komponen GUI
        this.username = ""; // Inisialisasi username
        try {
            // Membuat objek TableScore untuk mengakses dan menampilkan data dari database
            TableScore tscore = new TableScore();
            TableExperiencesUpDown.setModel(tscore.setTable()); // Menampilkan data pada tabel dari database
        } catch (Exception e) {
            System.out.println(e.getMessage()); // Menampilkan pesan error jika terjadi Exception
        }

        // Memutar suara latar belakang di menu awal
        Sound bgm = new Sound();
        audio = bgm.playSound(this.audio, "00_Game_Menu.wav", true);
    }

    // Kelas ImagePanel untuk menampilkan latar belakang gambar
    static class ImagePanel extends JPanel {
        private final Image backgroundImage;

        public ImagePanel(String fileName) {
            // Mengambil dan menetapkan gambar latar belakang dari file di resources
            backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(fileName))).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Menggambar gambar latar belakang sesuai ukuran panel
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }

    // Method untuk menginisialisasi komponen GUI
    private void initComponents() {
        // Panel untuk menampilkan latar belakang gambar
        ImagePanel jPanel1 = new ImagePanel("../assets/background.png");
        JLabel jLabel1 = new JLabel();
        JButton btnPlay = new JButton();
        JLabel lblMenuTitle = new JLabel();
        JLabel lblCopy = new JLabel();
        JButton btnQuit = new JButton();
        JScrollPane jScrollPane1 = new JScrollPane();
        TableExperiencesUpDown = new javax.swing.JTable();
        JLabel lblNewUsername = new JLabel();
        TFUsername = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Up Down | Menu"); // Judul jendela
        setName("Main Menu"); // Nama jendela
        setResizable(false); // Menonaktifkan kemampuan resize
        setSize(new java.awt.Dimension(800, 600)); // Ukuran jendela

        jPanel1.setBackground(new java.awt.Color(51, 153, 255)); // Warna latar belakang panel

        btnPlay.setBackground(new java.awt.Color(51, 255, 102)); // Warna tombol "Play"
        btnPlay.setFont(new java.awt.Font("Ubuntu", Font.BOLD, 14)); // Font dan gaya teks tombol "Play"
        btnPlay.setForeground(new java.awt.Color(255, 255, 255)); // Warna teks tombol "Play"
        btnPlay.setText("Play"); // Teks pada tombol "Play"
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt); // Handler untuk tombol "Play"
            }
        });

        lblMenuTitle.setFont(new java.awt.Font("Bunny Funny", Font.BOLD, 36));  // Font dan gaya teks judul menu
        lblMenuTitle.setForeground(Color.black);
        lblMenuTitle.setText("Up Down"); // Teks judul menu
        lblMenuTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER); // Posisi teks di tengah
        lblMenuTitle.setAlignmentY(0.0F); // Aligned di sumbu Y
        lblMenuTitle.setIconTextGap(0); // Gap antara ikon dan teks

        btnQuit.setBackground(new java.awt.Color(255, 102, 102)); // Warna tombol "Quit"
        btnQuit.setFont(new java.awt.Font("Ubuntu", Font.BOLD, 14)); // Font dan gaya teks tombol "Quit"
        btnQuit.setForeground(new java.awt.Color(255, 255, 255)); // Warna teks tombol "Quit"
        btnQuit.setText("Quit"); // Teks pada tombol "Quit"
        btnQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitActionPerformed(evt); // Handler untuk tombol "Quit"
            }
        });



        TableExperiencesUpDown.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableExperiencesMouseClicked(evt); // Handler untuk klik mouse pada tabel
            }
        });
        jScrollPane1.setViewportView(TableExperiencesUpDown); // Menampilkan tabel dalam JScrollPane

        lblNewUsername.setFont(new java.awt.Font("Ubuntu", Font.PLAIN, 12)); // Font dan gaya teks label "Input Username baru atau pilih dari tabel"
        lblNewUsername.setForeground(new java.awt.Color(0, 0, 0)); // Warna teks label
        lblNewUsername.setText("Input Username baru atau pilih dari tabel"); // Teks label

        TFUsername.setHorizontalAlignment(javax.swing.JTextField.CENTER); // Posisi teks di tengah untuk TextField "Username"
        TFUsername.setToolTipText("Username..."); // ToolTip untuk TextField "Username"
        TFUsername.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED)); // Border untuk TextField "Username"

        // Mengatur tata letak panel
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblCopy)
                                .addGap(16, 16, 16))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(33, 33, 33)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(36, 36, 36)
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(TFUsername)
                                                                        .addComponent(lblMenuTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(btnQuit, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(98, 98, 98)
                                                .addComponent(lblNewUsername)))
                                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(lblMenuTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(lblNewUsername)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TFUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnQuit, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(lblCopy)
                                .addContainerGap())
        );

        // Mengatur tata letak utama jendela
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack(); // Mem-packing komponen
        setLocationRelativeTo(null); // Mengatur posisi jendela ke tengah layar
    }


    // Handler untuk tombol "Quit"
    private void btnQuitActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose(); // Menutup jendela
        System.exit(0); // Keluar dari aplikasi
    }

    // Handler untuk tombol "Play"
    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {
        // Memeriksa jika username kosong dan TextField username tidak kosong
        if (this.username.isEmpty() && !TFUsername.getText().isEmpty()) {
            this.username = TFUsername.getText(); // Mengambil nilai dari TextField sebagai username
        }
        // Jika TextField username kosong dan nilai username masih kosong (tidak ada yang dipilih dari tabel)
        if (TFUsername.getText().isEmpty() && this.username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username tidak boleh kosong!\nPilih user atau masukkan username baru.");
        } else {
            // Memainkan suara untuk memulai permainan baru
            Sound bgm = new Sound();
            bgm.playSound(this.audio, "01_Game_Start.wav", false); // Memutar suara
            bgm.stopSound(this.audio); // Menghentikan suara latar belakang

            // Membuat objek permainan baru
            game = new Game();
            GameWindow gw = new GameWindow(game); // Membuat jendela permainan
            game.setUsername(this.username); // Mengatur username untuk permainan
            TableScore temp = null;
            try {
                temp = new TableScore();
            } catch (Exception ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex); // Log exception jika terjadi error
            }
            assert temp != null;
            temp.getDataTScore(this.username); // Mendapatkan data skor berdasarkan username dari database
            int c = 0, tscore = 0;
            try {
                while (temp.getResult().next()) {
                    tscore = Integer.parseInt(temp.getResult().getString(2)); // Mengambil skor dari hasil query
                    c++; // Menghitung jumlah data yang ditemukan
                }
            } catch (Exception ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex); // Log exception jika terjadi error
            }
            // Set skor awal permainan berdasarkan data dari database
            if (c == 0) {
                game.setScore(0, 0, 0, 0); // Jika user baru, set skor adaptasi dan jatuh ke 0
            } else {
                game.setScore(0, 0, 0, tscore); // Jika user lama, set skor adaptasi dan jatuh berdasarkan data
            }

            // Memulai permainan
            game.StartGame(gw);
            this.setVisible(false); // Menyembunyikan jendela menu
            this.dispose(); // Membersihkan jendela menu dari memori
        }
    }

    // Handler untuk klik pada tabel
    private void TableExperiencesMouseClicked(java.awt.event.MouseEvent evt) {
        int row = TableExperiencesUpDown.getSelectedRow(); // Mendapatkan baris yang dipilih dari tabel
        this.username = TableExperiencesUpDown.getModel().getValueAt(row, 0).toString(); // Mengambil username dari baris yang dipilih
    }

    // Method utama untuk menjalankan aplikasi
    public static void main(String[] args) {
        /* Set look and feel pada aplikasi */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Membuat dan menampilkan jendela menu utama
        java.awt.EventQueue.invokeLater(() -> {
            new Menu().setVisible(true);
        });
    }

    // Variabel deklarasi - tidak mengubah//GEN-BEGIN:variables
    private javax.swing.JTextField TFUsername;
    private javax.swing.JTable TableExperiencesUpDown;
    // End of variables declaration//GEN-END:variables
}
