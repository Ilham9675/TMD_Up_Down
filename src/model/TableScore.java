package model;

import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

// Inherit dari kelas DB yang sebelumnya dibuat, untuk mengakses database
public class TableScore extends DB {
    private final String tableName; // nama tabel yang digunakan

    // Konstruktor untuk inisialisasi koneksi database dan nama tabel
    public TableScore() throws Exception, SQLException {
        super(); // Memanggil konstruktor kelas induk (DB) untuk menginisialisasi koneksi
        this.tableName = "tscore"; // Inisialisasi nama tabel
    }

    // Method untuk mendapatkan semua data dari tabel tscore
    public void getTScore() {
        try {
            String query = "SELECT * from " + this.tableName;
            createQuery(query); // Memanggil method createQuery dari kelas DB untuk menjalankan query
        } catch (Exception e) {
            System.out.println(e.toString()); // Menampilkan pesan error jika terjadi Exception
        }
    }

    // Method untuk mendapatkan data berdasarkan username
    public void getDataTScore(String username) {
        try {
            String query = "SELECT * from " + this.tableName + " WHERE username='" + username + "'";
            createQuery(query); // Memanggil method createQuery dari kelas DB untuk menjalankan query
        } catch (Exception e) {
            System.err.println(e.toString()); // Menampilkan pesan error jika terjadi Exception
        }
    }

    // Method untuk memasukkan atau memperbarui data pada tabel tscore
    public void insertData(String username, int score, int up, int down) {
        boolean update = false; // Inisialisasi untuk menandai apakah data perlu di-update

        try {
            TableScore temp = new TableScore(); // Membuat objek sementara untuk mengakses data berdasarkan username
            temp.getDataTScore(username); // Mendapatkan data berdasarkan username
            update = temp.getResult().next(); // Memeriksa apakah username sudah ada di database
        } catch (Exception e) {
            System.out.println(e.getMessage()); // Menampilkan pesan error jika terjadi Exception
        }

        // Proses insert jika data belum ada, atau update jika data sudah ada
        if (!update) {
            try {
                String query = "INSERT INTO " + this.tableName + " VALUES('" + username + "', " + score + ", " + up + ", " + down + ")";
                createUpdate(query); // Memanggil method createUpdate dari kelas DB untuk menjalankan query
            } catch (Exception e) {
                System.out.println(e.toString()); // Menampilkan pesan error jika terjadi Exception
            }
        } else {
            try {
                String query = "UPDATE " + this.tableName + " SET score=" + score + ", up=" + up + ", down=" + down + " WHERE username='" + username + "'";
                createUpdate(query); // Memanggil method createUpdate dari kelas DB untuk menjalankan query
            } catch (Exception e) {
                System.out.println(e.getMessage()); // Menampilkan pesan error jika terjadi Exception
            }
        }
    }

    // Method untuk membuat DefaultTableModel untuk digunakan dalam JTable
    public DefaultTableModel setTable() {
        DefaultTableModel dataTable = null; // Inisialisasi objek DefaultTableModel

        try {
            // Membuat header kolom tabel
            Object[] column = {"Username", "Score", "Up", "Down"};
            dataTable = new DefaultTableModel(null,column){
                public boolean isCellEditable(int row, int column) {
                    return false; // Ini membuat semua sel tidak bisa diedit
                }
            }; // Inisialisasi DefaultTableModel dengan header kolom

            // Mendapatkan data dari tabel tscore untuk ditampilkan di JTable
            String query = "SELECT * from " + this.tableName;
            this.createQuery(query); // Memanggil method createQuery dari kelas DB untuk menjalankan query

            // Menambahkan data per baris ke DefaultTableModel
            while (this.getResult().next()) {
                Object[] row = new Object[4];
                row[0] = this.getResult().getString(1); // Kolom username
                row[1] = this.getResult().getInt(2);    // Kolom score
                row[2] = this.getResult().getInt(3);    // Kolom up
                row[3] = this.getResult().getInt(4);    // Kolom down
                dataTable.addRow(row); // Menambahkan baris data ke DefaultTableModel
            }
        } catch (Exception e) {
            System.err.println(e.getMessage()); // Menampilkan pesan error jika terjadi Exception
        }

        return dataTable; // Mengembalikan DefaultTableModel yang berisi data dari database
    }
}
