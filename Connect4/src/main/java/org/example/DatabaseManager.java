package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
    private static final String DB_URL = "jdbc:sqlite:connect4.db";

    public DatabaseManager() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS players " +
                         "(name TEXT PRIMARY KEY, wins INTEGER)";
            stmt.execute(sql);

        } catch (SQLException e) {
            logger.error("Hiba az adatbázis inicializálása során", e);
        }
    }

    public void saveWin(String playerName) {
        String sql = "INSERT INTO players(name, wins) VALUES(?,1) " +
                     "ON CONFLICT(name) DO UPDATE SET wins = wins + 1";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, playerName);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("Hiba a győzelem mentése során", e);
        }
    }

    public Map<String, Integer> getHighScores() {
        Map<String, Integer> highScores = new HashMap<>();
        String sql = "SELECT name, wins FROM players ORDER BY wins DESC LIMIT 10";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                highScores.put(rs.getString("name"), rs.getInt("wins"));
            }

        } catch (SQLException e) {
            logger.error("Hiba a legmagasabb pontszámok lekérdezése során", e);
        }

        return highScores;
    }
}
