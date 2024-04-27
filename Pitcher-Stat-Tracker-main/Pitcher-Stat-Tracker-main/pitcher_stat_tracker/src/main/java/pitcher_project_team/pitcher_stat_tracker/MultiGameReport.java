package com.mycompany.finalproject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MultiGameReport {
    private List<String> gameDates;
    private String outputFilePath;

    public MultiGameReport(List<String> gameDates, String outputFilePath) {
        this.gameDates = gameDates;
        this.outputFilePath = outputFilePath;
    }

    public void generateReport() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:pitcher_stats.sqlite")) {
            List<String> reportLines = new ArrayList<>();
            reportLines.add("Multi-Game Summary Page:");
            reportLines.add("");
            reportLines.add("Game Date\tFirst Name\tLast Name\tTeam Name\tInnings Pitched\tHits\tRuns\t" +
                    "Earned Runs\tWalks\tStrikeouts\tAt Bats\tBatters Faced\tNumber of Pitches");

            String selectSQL = "SELECT * FROM PitcherStats WHERE DateOfGame IN (";
            for (int i = 0; i < gameDates.size(); i++) {
                if (i > 0) {
                    selectSQL += ",";
                }
                selectSQL += "?";
            }
            selectSQL += ")";
            
            try (PreparedStatement ps = connection.prepareStatement(selectSQL)) {
                for (int i = 0; i < gameDates.size(); i++) {
                    ps.setString(i + 1, gameDates.get(i));
                }
                
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String gameDate = rs.getString("DateOfGame");
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

                        String line = String.format("%s\t%s\t%s\t%.1f\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d",
                                gameDate, firstName, lastName, teamName, inningsPitched, hits, runs, earnedRuns,
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
