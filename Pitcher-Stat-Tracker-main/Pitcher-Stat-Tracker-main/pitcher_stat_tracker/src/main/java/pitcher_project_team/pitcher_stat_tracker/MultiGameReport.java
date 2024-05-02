/*
 * Name: Julius Peterson
 * Date: 4/27/24
 * Team: Pitcher Team (Trevor Pence, Julius Peterson, Jay Lee)
 * Purpose: Generate a report for multiple games.
 */
package pitcher_project_team.pitcher_stat_tracker;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.scene.control.Alert;

public class MultiGameReport {

    private List<String> gameDates;
    private String outputFilePath;

    public MultiGameReport(List<String> gameDates, String outputFilePath) {
        this.gameDates = gameDates;
        this.outputFilePath = outputFilePath;
    }

   public void generateTotalStatisticsReport() {
    try (Connection connection = DriverManager.getConnection("jdbc:sqlite:pitcher_stats.sqlite")) {
        List<String> reportLines = new ArrayList<>();
        reportLines.add("Total Statistics Summary:");
        reportLines.add("");
        reportLines.add("Player\tInnings Pitched\tHits\tRuns\tEarned Runs\tWalks\tStrikeouts\tAt Bats\tBatters Faced\tNumber of Pitches");

        String selectSQL = "SELECT FirstName, LastName, " +
                "SUM(InningsPitched) AS TotalInningsPitched, " +
                "SUM(Hits) AS TotalHits, " +
                "SUM(Runs) AS TotalRuns, " +
                "SUM(EarnedRuns) AS TotalEarnedRuns, " +
                "SUM(Walks) AS TotalWalks, " +
                "SUM(Strikeouts) AS TotalStrikeouts, " +
                "SUM(AtBats) AS TotalAtBats, " +
                "SUM(BattersFaced) AS TotalBattersFaced, " +
                "SUM(NumberOfPitches) AS TotalNumberOfPitches " +
                "FROM PitcherStats WHERE DateOfGame IN (";

        for (int i = 0; i < gameDates.size(); i++) {
            if (i > 0) {
                selectSQL += ",";
            }
            selectSQL += "?";
        }
        selectSQL += ") GROUP BY FirstName, LastName";
        
        try (PreparedStatement ps = connection.prepareStatement(selectSQL)) {
            for (int i = 0; i < gameDates.size(); i++) {
                ps.setString(i + 1, gameDates.get(i));
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String firstName = rs.getString("FirstName");
                    String lastName = rs.getString("LastName");
                    double inningsPitched = rs.getDouble("TotalInningsPitched");
                    int hits = rs.getInt("TotalHits");
                    int runs = rs.getInt("TotalRuns");
                    int earnedRuns = rs.getInt("TotalEarnedRuns");
                    int walks = rs.getInt("TotalWalks");
                    int strikeouts = rs.getInt("TotalStrikeouts");
                    int atBats = rs.getInt("TotalAtBats");
                    int battersFaced = rs.getInt("TotalBattersFaced");
                    int numberOfPitches = rs.getInt("TotalNumberOfPitches");

                    String line = String.format("%s %s\t%.1f\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d",
                            firstName, lastName, inningsPitched, hits, runs, earnedRuns,
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

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Summary Generated");
                alert.setHeaderText("Summary Generated");
                alert.setContentText("Your season summary has been generated.\n"
                        + "Please check your files for a file named " + outputFilePath);
                alert.showAndWait();
    }
}

