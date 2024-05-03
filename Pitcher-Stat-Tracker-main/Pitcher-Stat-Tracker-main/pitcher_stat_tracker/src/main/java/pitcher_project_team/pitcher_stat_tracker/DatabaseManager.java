
/*
* Author: Jay Lee
* Date: 4/26/2024
* Purpose: Manage the sqlite file entries
*/


package pitcher_project_team.pitcher_stat_tracker;

import java.sql.*;

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

    public ArrayList<String> getGameDates() {
        
    }
}
