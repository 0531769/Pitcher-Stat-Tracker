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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Alert;

public class SingleGameReport {

    private String selectedGameDate;
    private String outputFileName;

    public SingleGameReport(String selectedGameDate, String outputFileName) {
        this.selectedGameDate = selectedGameDate;
        this.outputFileName = outputFileName;
    }

    public void generateReport() {
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
        legendBuilder.append("Legend:\n");
        for (Map.Entry<String, String> entry : sortedAbbreviationList) {
            legendBuilder.append(entry.getValue()).append(": ").append(entry.getKey()).append("\n");
        }
        String legend = legendBuilder.toString();

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:pitcher_stats.sqlite")) {
            List<String> reportLines = new ArrayList<>();
            reportLines.add("Game Summary:");
            reportLines.add("");
            reportLines.add(String.format("%-20s%-20s%-5s%-5s%-5s%-5s%-5s%-5s%-5s%-5s%-5s%-5s",
                    "Player", "Team Name", "IP", "H", "R", "ER", "BB", "SO", "AB", "BF", "NP", "ERA"));

            String selectSQL = "SELECT * FROM PitcherStats WHERE DateOfGame = ?";

            try (PreparedStatement ps = connection.prepareStatement(selectSQL)) {
                ps.setString(1, selectedGameDate);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        StringBuilder lineBuilder = new StringBuilder();
                        String firstName = rs.getString("FirstName");
                        String lastName = rs.getString("LastName");
                        String teamName = rs.getString("TeamName"); // Fetch team name from the database
                        double inningsPitched = rs.getDouble("InningsPitched");
                        int hits = rs.getInt("Hits");
                        int runs = rs.getInt("Runs");
                        int earnedRuns = rs.getInt("EarnedRuns");
                        int walks = rs.getInt("Walks");
                        int strikeouts = rs.getInt("Strikeouts");
                        int atBats = rs.getInt("AtBats");
                        int battersFaced = rs.getInt("BattersFaced");
                        int numberOfPitches = rs.getInt("NumberOfPitches");

                        // Create a Pitcher instance
                        Pitcher pitcher = new Pitcher(firstName, lastName, teamName, inningsPitched, hits, runs, earnedRuns, walks, strikeouts, atBats, battersFaced, numberOfPitches, selectedGameDate);

                        // Calculate ERA using the earnedRunAverage method from the Pitcher class
                        double era = pitcher.earnedRunAverage();

                        // Format the line with appropriate spacing
                        lineBuilder.append(String.format("%-20s", firstName + " " + lastName));
                        lineBuilder.append(String.format("%-20s", teamName)); // Add team name to the report
                        lineBuilder.append(String.format("%-5.1f", inningsPitched));
                        lineBuilder.append(String.format("%-5d", hits));
                        lineBuilder.append(String.format("%-5d", runs));
                        lineBuilder.append(String.format("%-5d", earnedRuns));
                        lineBuilder.append(String.format("%-5d", walks));
                        lineBuilder.append(String.format("%-5d", strikeouts));
                        lineBuilder.append(String.format("%-5d", atBats));
                        lineBuilder.append(String.format("%-5d", battersFaced));
                        lineBuilder.append(String.format("%-5d", numberOfPitches));
                        lineBuilder.append(String.format("%-5.2f", era)); // Append ERA to the line

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
        try (FileWriter writer = new FileWriter(outputFileName)) {
            for (String line : reportLines) {
                writer.write(line + "\n");
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText("Report Generated");
        alert.setContentText("Your single game report has been generated.\n"
                + "Please check your files for a file named \n" + outputFileName);
        alert.showAndWait();
    }
}

