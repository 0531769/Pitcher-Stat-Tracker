/*
 * Name: Julius Peterson
 * Date: 4/27/24
 * Team: Pitcher Team (Trevor Pence, Julius Peterson, Jay Lee)
 * Purpose: Generate a report for a single game.
 */
package pitcher_project_team.pitcher_stat_tracker;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SingleGameReport {

    private String selectedGameDate;
    private String outputFilePath;

    public SingleGameReport(String selectedGameDate, String outputFilePath) {
        this.selectedGameDate = selectedGameDate;
        this.outputFilePath = outputFilePath;
    }

    public void generateReport() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:pitcher_stats.sqlite")) {
            List<String> reportLines = new ArrayList<>();
            reportLines.add("Single Game Summary Page:");
            reportLines.add("");
            reportLines.add("First Name\tLast Name\tTeam Name\tInnings Pitched\tHits\tRuns\t" +
                    "Earned Runs\tWalks\tStrikeouts\tAt Bats\tBatters Faced\tNumber of Pitches");

            String selectSQL = "SELECT * FROM PitcherStats WHERE DateOfGame = ?";
            
            try (PreparedStatement ps = connection.prepareStatement(selectSQL)) {
                ps.setString(1, selectedGameDate);
                
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String firstName = rs.getString("FirstName");
                        String lastName = rs.getString("LastName");
                        String teamName = rs.getString("TeamName");
                        double inningsPitched = rs.getDouble("InningsPitched");
                        int hits = rs.getInt("Hits");
                        int runs = rs.getInt("Runs");
                        int earnedRuns = rs.getInt("EarnedRuns");
                        int walks = rs.getInt("Walks");
                        int strikeouts = rs.getInt("Strikeouts");
                        int atBats = rs.getInt("AtBats");
                        int battersFaced = rs.getInt("BattersFaced");
                        int numberOfPitches = rs.getInt("NumberOfPitches");

                        String line = String.format("%s\t%s\t%s\t%.1f\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d",
                                firstName, lastName, teamName, inningsPitched, hits, runs, earnedRuns,
                                walks, strikeouts, atBats, battersFaced, numberOfPitches);
                        reportLines.add(line);
                    }
                }
            }

            writeReportToFile(reportLines);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void writeReportToFile(List<String> reportLines) throws IOException {
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            for (String line : reportLines) {
                writer.write(line + "\n");
            }
        }
    }
}
