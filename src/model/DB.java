package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DB adalah kelas untuk mengelola koneksi basis data dan menjalankan kueri SQL.
 */
public class DB {
    private Statement stmt = null; // Untuk menjalankan pernyataan SQL
    private Connection conn = null; // Untuk koneksi basis data
    private ResultSet rs = null; // Untuk menyimpan hasil dari kueri

    /**
     * Konstruktor untuk DB.
     * Menginisialisasi koneksi ke basis data MySQL.
     * @throws SQLException jika terjadi kesalahan dalam membangun koneksi basis data.
     */
    public DB() throws SQLException {
        try {
            // Daftarkan driver MySQL JDBC (opsional untuk versi terbaru)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // String koneksi untuk basis data MySQL
            String connAddress = "jdbc:mysql://localhost/updown_game?user=root&password=";
            conn = DriverManager.getConnection(connAddress);

            // Mengatur tingkat isolasi transaksi
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Kesalahan dalam membangun koneksi basis data: " + e.getMessage());
        }
    }

    /**
     * Menjalankan kueri SELECT.
     * @param Query kueri SQL yang akan dijalankan.
     * @throws SQLException jika terjadi kesalahan dalam menjalankan kueri.
     */
    public void createQuery(String Query) throws SQLException {
        stmt = conn.createStatement();
        rs = stmt.executeQuery(Query);
        if (stmt.execute(Query)) {
            rs = stmt.getResultSet();
        }
    }

    /**
     * Menjalankan kueri INSERT, UPDATE, atau DELETE.
     * @param Query kueri SQL yang akan dijalankan.
     * @throws SQLException jika terjadi kesalahan dalam menjalankan kueri.
     */
    public void createUpdate(String Query) throws SQLException {
        stmt = conn.createStatement();
        stmt.executeUpdate(Query);
    }

    /**
     * Mengembalikan hasil dari kueri yang dijalankan.
     * @return ResultSet dari kueri.
     * @throws SQLException jika terjadi kesalahan dalam mengambil hasil.
     */
    public ResultSet getResult() throws SQLException {
        return rs;
    }

    /**
     * Menutup ResultSet dan Statement.
     * @throws SQLException jika terjadi kesalahan dalam menutup sumber daya.
     */
    public void closeResult() throws SQLException {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException es) {
                rs = null;
                throw es;
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException es) {
                stmt = null;
                throw es;
            }
        }
    }

    /**
     * Menutup koneksi basis data.
     * @throws SQLException jika terjadi kesalahan dalam menutup koneksi.
     */
    public void closeConnection() throws SQLException {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException es) {
                conn = null;
                throw es;
            }
        }
    }
}
