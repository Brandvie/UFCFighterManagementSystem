package UFCFighterManagement.dao;

import UFCFighterManagement.dto.FighterDTO;
import UFCFighterManagement.filter.FighterFilter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FighterDAOMySQLImpl implements FighterDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/ufc_fighter_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    @Override
    public List<FighterDTO> getAllFighters() {
        List<FighterDTO> fighters = new ArrayList<>();
        String sql = "SELECT * FROM fighters";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                FighterDTO fighter = new FighterDTO(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("weight_class"),
                        rs.getString("record"),
                        rs.getString("stance"),
                        rs.getDouble("reach"),
                        rs.getString("coach"),
                        rs.getString("promotion"));
                fighters.add(fighter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fighters;
    }

    @Override
    public FighterDTO getFighterById(int id) {
        String sql = "SELECT * FROM fighters WHERE id = ?";
        FighterDTO fighter = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    fighter = new FighterDTO(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("weight_class"),
                            rs.getString("record"),
                            rs.getString("stance"),
                            rs.getDouble("reach"),
                            rs.getString("coach"),
                            rs.getString("promotion"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fighter;
    }

    @Override
    public boolean deleteFighterById(int id) {
        String sql = "DELETE FROM fighters WHERE id = ?";
        boolean rowDeleted = false;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            rowDeleted = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    @Override
    public FighterDTO insertFighter(FighterDTO fighter) {
        String sql = "INSERT INTO fighters (name, age, weight_class, record, stance, reach, coach, promotion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        ResultSet generatedKeys = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, fighter.getName());
            pstmt.setInt(2, fighter.getAge());
            pstmt.setString(3, fighter.getWeightClass());
            pstmt.setString(4, fighter.getRecord());
            pstmt.setString(5, fighter.getStance());
            pstmt.setDouble(6, fighter.getReach());
            pstmt.setString(7, fighter.getCoach());
            pstmt.setString(8, fighter.getPromotion());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating fighter failed, no rows affected.");
            }

            generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                fighter.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating fighter failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (generatedKeys != null) {
                try {
                    generatedKeys.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return fighter;
    }

    @Override
    public boolean updateFighter(int id, FighterDTO fighter) {
        String sql = "UPDATE fighters SET name = ?, age = ?, weight_class = ?, record = ?, stance = ?, reach = ?, coach = ?, promotion = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fighter.getName());
            pstmt.setInt(2, fighter.getAge());
            pstmt.setString(3, fighter.getWeightClass());
            pstmt.setString(4, fighter.getRecord());
            pstmt.setString(5, fighter.getStance());
            pstmt.setDouble(6, fighter.getReach());
            pstmt.setString(7, fighter.getCoach());
            pstmt.setString(8, fighter.getPromotion());
            pstmt.setInt(9, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating fighter: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<FighterDTO> findFightersApplyFilter(FighterFilter filter) {
        List<FighterDTO> allFighters = getAllFighters(); // Get all fighters first
        return allFighters.stream()
                .filter(filter::test)
                .collect(Collectors.toList());
    }
}

