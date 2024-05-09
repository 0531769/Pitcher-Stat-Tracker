/*
* Author: Jay Lee
* Date: 4/26/2024
* Purpose: Manage the sqlite file entries
*/

package pitcher_project_team.pitcher_stat_tracker;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static Connection connection;
    
 public DatabaseManager() throws SQLException {
        // establish connection to the database
        String url = "jdbc:sqlite:pitcher_stats.sqlite";
        connection = DriverManager.getConnection(url);
 }
        
 public void insertPlayerStats(String firstName, String lastName, String teamName, double inningsPitched,
                                  int hits, int runs, int earnedRuns, int walks, int strikeouts,
                                  int atBats, int battersFaced, int numberOfPitches, String dateOfGame) throws SQLException {
        String insertSQL = "INSERT INTO PitcherStats (FirstName, LastName, TeamName, InningsPitched, " +
                           "Hits, Runs, EarnedRuns, Walks, Strikeouts, AtBats, BattersFaced, " +
                           "NumberOfPitches, DateOfGame) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, teamName);
            ps.setDouble(4, inningsPitched);
            ps.setInt(5, hits);
            ps.setInt(6, runs);
            ps.setInt(7, earnedRuns);
            ps.setInt(8, walks);
            ps.setInt(9, strikeouts);
            ps.setInt(10, atBats);
            ps.setInt(11, battersFaced);
            ps.setInt(12, numberOfPitches);
            ps.setString(13, dateOfGame);
            ps.executeUpdate();
        }
    }

    // retrieves game dates from database, stores in gameDates arraylist
    public ArrayList<String> getGameDates() throws SQLException {
        ArrayList<String> gameDates = new ArrayList<>();
        
        String selectSQL = "SELECT DISTINCT dateOfGame"
                            + " FROM PitcherStats";
        try (Statement cs = connection.createStatement();
             ResultSet resultSet = cs.executeQuery(selectSQL)) {
            while (resultSet.next()) {
                String dateOfGame = resultSet.getString("dateOfGame");
                gameDates.add(dateOfGame);
            }
        }
        return gameDates;
    }
    
    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
