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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Alert;

public class MultiGameReport {

    private List<String> gameDates;
    private String outputFileName;

    public MultiGameReport(List<String> gameDates, String outputFileName) {
        this.gameDates = gameDates;
        this.outputFileName = outputFileName;
    }

    public void generateTotalStatisticsReport() {
        // Mapping between full text and abbreviations
        Map<String, String> abbreviationMap = new HashMap<>();
        abbreviationMap.put("Innings Pitched", "IP");
        abbreviationMap.put("Hits", "H");
        abbreviationMap.put("Runs", "R");
        abbreviationMap.put("Earned Runs", "ER");
        abbreviationMap.put("Walks", "BB");
        abbreviationMap.put("Strikeouts", "SO");
        abbreviationMap.put("At Bats", "AB");
        abbreviationMap.put("Batters Faced", "BF");
        abbreviationMap.put("Number of Pitches", "NP");
        abbreviationMap.put("Earned Run Average", "ERA"); // Added ERA abbreviation

        // Sort the legend entries alphabetically
        List<Map.Entry<String, String>> sortedAbbreviationList = new ArrayList<>(abbreviationMap.entrySet());
        sortedAbbreviationList.sort(Map.Entry.comparingByKey());

        // Legend explaining the abbreviations
        StringBuilder legendBuilder = new StringBuilder();
        legendBuilder.append("Legend\n");
        for (Map.Entry<String, String> entry : sortedAbbreviationList) {
            legendBuilder.append(entry.getValue()).append(": ").append(entry.getKey()).append("\n");
        }
        String legend = legendBuilder.toString();

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:pitcher_stats.sqlite")) {
            List<String> reportLines = new ArrayList<>();
            reportLines.add("Season Summary");
            reportLines.add("");
            reportLines.add(String.format("%-20s%-20s%-5s%-5s%-5s%-5s%-5s%-5s%-5s%-5s%-5s%-5s",
                    "Player", "Team Name", "IP", "H", "R", "ER", "BB", "SO", "AB", "BF", "NP", "ERA"));

            String selectSQL = "SELECT FirstName, LastName, TeamName, "
                    + "SUM(InningsPitched) AS TotalInningsPitched, "
                    + "SUM(Hits) AS TotalHits, "
                    + "SUM(Runs) AS TotalRuns, "
                    + "SUM(EarnedRuns) AS TotalEarnedRuns, "
                    + "SUM(Walks) AS TotalWalks, "
                    + "SUM(Strikeouts) AS TotalStrikeouts, "
                    + "SUM(AtBats) AS TotalAtBats, "
                    + "SUM(BattersFaced) AS TotalBattersFaced, "
                    + "SUM(NumberOfPitches) AS TotalNumberOfPitches "
                    + "FROM PitcherStats WHERE DateOfGame IN (";

            for (int i = 0; i < gameDates.size(); i++) {
                if (i > 0) {
                    selectSQL += ",";
                }
                selectSQL += "?";
            }
            selectSQL += ") GROUP BY FirstName, LastName, TeamName";

            try (PreparedStatement ps = connection.prepareStatement(selectSQL)) {
                for (int i = 0; i < gameDates.size(); i++) {
                    ps.setString(i + 1, gameDates.get(i));
                }

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        StringBuilder lineBuilder = new StringBuilder();
                        String firstName = rs.getString("FirstName");
                        String lastName = rs.getString("LastName");
                        String teamName = rs.getString("TeamName");
                        lineBuilder.append(String.format("%-20s", firstName + " " + lastName));
                        lineBuilder.append(String.format("%-20s", teamName)); // Add team name

                        double inningsPitched = rs.getDouble("TotalInningsPitched");
                        lineBuilder.append(String.format("%-5.1f", inningsPitched));

                        int hits = rs.getInt("TotalHits");
                        lineBuilder.append(String.format("%-5d", hits));

                        int runs = rs.getInt("TotalRuns");
                        lineBuilder.append(String.format("%-5d", runs));

                        int earnedRuns = rs.getInt("TotalEarnedRuns");
                        lineBuilder.append(String.format("%-5d", earnedRuns));

                        int walks = rs.getInt("TotalWalks");
                        lineBuilder.append(String.format("%-5d", walks));

                        int strikeouts = rs.getInt("TotalStrikeouts");
                        lineBuilder.append(String.format("%-5d", strikeouts));

                        int atBats = rs.getInt("TotalAtBats");
                        lineBuilder.append(String.format("%-5d", atBats));

                        int battersFaced = rs.getInt("TotalBattersFaced");
                        lineBuilder.append(String.format("%-5d", battersFaced));

                        int numberOfPitches = rs.getInt("TotalNumberOfPitches");
                        lineBuilder.append(String.format("%-5d", numberOfPitches));

                        // Create a Pitcher instance
                        Pitcher pitcher = new Pitcher(firstName, lastName, "", inningsPitched, hits, runs, earnedRuns, walks, strikeouts, atBats, battersFaced, numberOfPitches, "");

                        // Calculate ERA using the earnedRunAverage method from the Pitcher class
                        double era = pitcher.earnedRunAverage();

                        // Append ERA to the line
                        lineBuilder.append(String.format("%-5.2f", era));

                        // Replace full text with abbreviations
                        String line = lineBuilder.toString();
                        for (Map.Entry<String, String> entry : abbreviationMap.entrySet()) {
                            line = line.replaceAll(entry.getKey(), entry.getValue());
                        }

                        reportLines.add(line);
                    }
                }
            }

            // Add legend to the report
            reportLines.add("");
            reportLines.add(legend);

            writeReportToFile(reportLines);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void writeReportToFile(List<String> reportLines) throws IOException {
        try (FileWriter writer = new FileWriter(outputFileName)) { // Use outputFileName
            for (String line : reportLines) {
                writer.write(line + "\n");
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText("Summary Generated");
        alert.setContentText("Your season summary has been generated.\n"
                + "Please check your files for a file named \n" + outputFileName);
        alert.showAndWait();
    }
}

