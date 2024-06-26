/**
 * Name: Duane Gandelot
 * Date: 4/23/24
 * Team: Pitcher Team (Trevor Pence, Julius Peterson, Jay Lee)
 * Purpose: Create GUI to collect data about each pitcher.
 */
package pitcher_project_team.pitcher_stat_tracker;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import java.util.Collections;
import java.util.ArrayList;
import javafx.scene.layout.VBox;
//add new imports
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App extends Application {

    private TextField firstNameField;
    private TextField lastNameField;
    private TextField teamNameField;
    private TextField inningsPitchedField;
    private TextField hitField;
    private TextField runField;
    private TextField earnedRunField;
    private TextField walkField;
    private TextField strikeoutField;
    private TextField atBatField;
    private TextField battersFacedField;
    private TextField numberOfPitchesField;
    private TextField DateOfGameField;
    
    @Override
    public void start(Stage primaryStage) {
        showMenu(primaryStage);
    }

    private void showMenu(Stage primaryStage) {
    primaryStage.setTitle("Pitcher Statistics");

    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setPadding(new Insets(25, 25, 25, 25));
    grid.setHgap(10);
    grid.setVgap(10);

    Scene scene = new Scene(grid, 600, 200);

    // add title
    Label title = new Label("***  Welcome to the Pitcher Statistics Tracker  ***\n"
            + "         Select a option from the menu below.");
    title.setLineSpacing(10);
    grid.add(title, 0, 0);
    
    // Create buttons
    Button submitButton = new Button("Enter Player Stats");
    submitButton.setOnAction(event -> openPitcherPage(primaryStage));

    Button reportButton = new Button("Generate Report(s)");
    reportButton.setOnAction(event -> generateReportMenu(primaryStage));

    Button exitButton = new Button("Exit");
    exitButton.setOnAction(event -> exitButtonClicked());

    // Create a VBox for button alignment
    VBox buttonBox = new VBox(10);
    buttonBox.getChildren().addAll(submitButton, reportButton, exitButton);
    buttonBox.setAlignment(Pos.CENTER);

    grid.add(buttonBox, 0, 1);

    primaryStage.setScene(scene);
    primaryStage.show();
}

    public void openPitcherPage(Stage primaryStage) {
        primaryStage.setTitle("Pitcher Statistics");

        // create grid, create scene -DG
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setHgap(10);
        grid.setVgap(10);

        Scene scene = new Scene(grid, 440, 590);

        // add instructions - DG
        Label label = new Label("Enter the stats for each pitcher");
        label.setLineSpacing(5);
        grid.add(label, 0, 0);

        // add labels and text fields to input the data from gameday stats -DG
        grid.add(new Label("First Name:"), 0, 1);
        firstNameField = new TextField();
        grid.add(firstNameField, 1, 1);

        grid.add(new Label("Last Name:"), 0, 2);
        lastNameField = new TextField();
        grid.add(lastNameField, 1, 2);

        grid.add(new Label("Team Name:"), 0, 3);
        teamNameField = new TextField();
        grid.add(teamNameField, 1, 3);

        grid.add(new Label("Innings Pitched (ip):"), 0, 4);
        inningsPitchedField = new TextField();
        grid.add(inningsPitchedField, 1, 4);

        grid.add(new Label("Hits Allowed (h):"), 0, 5);
        hitField = new TextField();
        grid.add(hitField, 1, 5);

        grid.add(new Label("Runs Allowed (r):"), 0, 6);
        runField = new TextField();
        grid.add(runField, 1, 6);

        grid.add(new Label("Earned Runs Allowed (er):"), 0, 7);
        earnedRunField = new TextField();
        grid.add(earnedRunField, 1, 7);

        grid.add(new Label("Walks (bb):"), 0, 8);
        walkField = new TextField();
        grid.add(walkField, 1, 8);

        grid.add(new Label("Strike Outs (so):"), 0, 9);
        strikeoutField = new TextField();
        grid.add(strikeoutField, 1, 9);

        grid.add(new Label("At Bats (ab):"), 0, 10);
        atBatField = new TextField();
        grid.add(atBatField, 1, 10);

        grid.add(new Label("Batters Faced (bf):"), 0, 11);
        battersFacedField = new TextField();
        grid.add(battersFacedField, 1, 11);

        grid.add(new Label("Number of Pitches (np):"), 0, 12);
        numberOfPitchesField = new TextField();
        grid.add(numberOfPitchesField, 1, 12);

        grid.add(new Label("Date of Game \n(MM-DD-YYYY):"), 0, 13);
        DateOfGameField = new TextField();
        grid.add(DateOfGameField, 1, 13);

        // Create Submit, Help, Exit, and Reset buttons - DG
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> submitButtonClicked());

        Button helpButton = new Button("Help");
        helpButton.setOnAction(event -> helpButtonClicked());

        Button menuButton = new Button("Menu");
        menuButton.setOnAction(event -> showMenu(primaryStage));

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(event -> {
        firstNameField.clear();
        lastNameField.clear();
        teamNameField.clear();
        inningsPitchedField.clear();
        hitField.clear();
        runField.clear();
        earnedRunField.clear();
        walkField.clear();
        strikeoutField.clear();
        atBatField.clear();
        battersFacedField.clear();
        numberOfPitchesField.clear();
        DateOfGameField.clear();
        });    

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().add(submitButton);
        buttonBox.getChildren().add(helpButton);
        buttonBox.getChildren().add(menuButton);
        buttonBox.getChildren().add(resetButton);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        grid.add(buttonBox, 0, 14, 3, 1);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // updated submit button to validate entry and clear the text fields when submit button in clicked.
    private void submitButtonClicked() {
        Validation v = new Validation();
        String errorMsg = "";
        // Validate all entry text fields
        errorMsg += v.isPresent(firstNameField.getText(), "First Name");
        errorMsg += v.isPresent(lastNameField.getText(), "Last Name");
        errorMsg += v.isPresent(teamNameField.getText(), "Team Name");
        errorMsg += v.isDouble(inningsPitchedField.getText(), "Innings Pitched");
        errorMsg += v.isInteger(hitField.getText(), "Hit");
        errorMsg += v.isInteger(runField.getText(), "Run");
        errorMsg += v.isInteger(earnedRunField.getText(), "Earned Run");
        errorMsg += v.isInteger(walkField.getText(), "Walk");
        errorMsg += v.isInteger(strikeoutField.getText(), "Strikeout");
        errorMsg += v.isInteger(atBatField.getText(), "At Bat");
        errorMsg += v.isInteger(battersFacedField.getText(), "Batters Faced");
        errorMsg += v.isInteger(numberOfPitchesField.getText(), "Number of Pitches");
        //errorMsg += v.isPresent(DateOfGameField.getText(), "Date of Game");
          
    // input date to strict format (MM-DD-YYYY) - DG
    String inputDateString = DateOfGameField.getText();
        
    try {
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM-dd-yyyy");
        inputFormat.setLenient(false); 
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy");

        Date inputDate = inputFormat.parse(inputDateString);
        String formattedDate = outputFormat.format(inputDate);

        DateOfGameField.setText(formattedDate);
    } catch (ParseException e) {
        errorMsg += "Invalid date format. Please enter a valid date in MM-DD-YYYY.";
        }
        
        // Retrieve data from the input fields - DG
        if (errorMsg.isEmpty()) {
            try {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String teamName = teamNameField.getText();
                double inningsPitched = Double.parseDouble(inningsPitchedField.getText());
                int hit = Integer.parseInt(hitField.getText());
                int run = Integer.parseInt(runField.getText());
                int earnedRuns = Integer.parseInt(earnedRunField.getText());
                int walk = Integer.parseInt(walkField.getText());
                int strikeout = Integer.parseInt(strikeoutField.getText());
                int atBat = Integer.parseInt(atBatField.getText());
                int battersFaced = Integer.parseInt(battersFacedField.getText());
                int numberOfPitches = Integer.parseInt(numberOfPitchesField.getText());
                String dateOfGame = DateOfGameField.getText();

                // create instance of DatabaseManager class to connect to DB
                DatabaseManager dbm = new DatabaseManager();
                dbm.insertPlayerStats(firstName, lastName, teamName,
                        inningsPitched, hit, run, earnedRuns, walk, strikeout,
                        atBat, battersFaced, numberOfPitches, dateOfGame);

                // Inform user that data has been entered into the database -TCP
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Submission Confirmation");
                alert.setHeaderText("Form Submitted");
                alert.setContentText("The form has been submitted.");
                alert.showAndWait();

                // clear the input fields
                firstNameField.clear();
                lastNameField.clear();
                teamNameField.clear();
                inningsPitchedField.clear();
                hitField.clear();
                runField.clear();
                earnedRunField.clear();
                walkField.clear();
                strikeoutField.clear();
                atBatField.clear();
                battersFacedField.clear();
                numberOfPitchesField.clear();
                DateOfGameField.clear();

            } catch (NumberFormatException e) {
                // Handle parsing error (invalid double input)
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid Data");
                alert.setContentText("Error parsing Innings Pitched. "
                        + "Please enter a valid number.");
                alert.showAndWait();
            } catch (SQLException e) { // catch SQLException - TCP
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Data entry error");
                alert.setContentText("There was an issue uploading the data\n"
                        + "to the database");
                alert.showAndWait();
            }
        } else {
            // Show error message(s) for invalid input
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Data");
            alert.setContentText(errorMsg);
            alert.showAndWait();
        }
    }

    // help button function -DG
    private void helpButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help Page");
        alert.setHeaderText("Help");
        alert.setContentText("Enter data for each pitcher whose stats you wish to record.\n"
                + "Press submit when finished to submit the stats.\n"
                + "If you wish to stop entering data, press 'menu' to return \n"
                + "to the main menu.");
        alert.showAndWait();
    }

    private void generateReportMenu(Stage primaryStage) {
    primaryStage.setTitle("Generate Report Menu");

    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setPadding(new Insets(25, 25, 25, 25));
    grid.setHgap(10);
    grid.setVgap(10);

    Scene scene = new Scene(grid, 600, 200);

    // add instructions
    Label instructions = new Label("                Select a Single Game Report to view stats for all pitchers in a game.\n"
            + "Select a Season Summary to view a summary of the stats of pitchers over multiple games.");
    instructions.setLineSpacing(10);
    grid.add(instructions, 0, 0);

    Button singleGameButton = new Button("Generate Single Game Report");
    singleGameButton.setOnAction(event -> openReportsPage(primaryStage));

    Button multiGameButton = new Button("Generate Season Summary");
    multiGameButton.setOnAction(event -> openSummaryPage(primaryStage));

    Button menuButton = new Button("Menu");
    menuButton.setOnAction(event -> showMenu(primaryStage));

    // Create a VBox for button alignment
    VBox buttonBox = new VBox(10);
    buttonBox.getChildren().addAll(singleGameButton, multiGameButton, menuButton);
    buttonBox.setAlignment(Pos.CENTER);

    grid.add(buttonBox, 0, 1);

    primaryStage.setScene(scene);
    primaryStage.show();
}

    private void openReportsPage(Stage primaryStage) {
        // create grid, create scene -TCP
        try {
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.TOP_CENTER);
            grid.setPadding(new Insets(25, 25, 25, 25));
            grid.setHgap(10);
            grid.setVgap(10);

            Scene scene = new Scene(grid, 400, 200);

            Label label = new Label("Generate Single Game Report");
            label.setLineSpacing(5);
            grid.add(label, 0, 0);

            Label gamesLabel = new Label("Select game by date: ");
            label.setLineSpacing(5);
            grid.add(gamesLabel, 0, 1);

            // create instance of DatabaseManager class to connect to DB
            DatabaseManager dbm = new DatabaseManager();

            // create sample combo box
            ArrayList<String> gameDates = dbm.getGameDates();
            ComboBox<String> gamesCombo = new ComboBox<>();
            gamesCombo.setPromptText("Select Game");
            for (String game : gameDates) {
                gamesCombo.getItems().add(game);
            }
            grid.add(gamesCombo, 1, 1);

            // Create Submit, Help, and Exit buttons - TCP
            Button submitButton = new Button("Generate");
            
            submitButton.setOnAction(event -> generateSingleGameReport(gamesCombo.getSelectionModel().getSelectedItem())); // Pass selected game date

            Button menuButton = new Button("Menu");
            menuButton.setOnAction(event -> showMenu(primaryStage));

            HBox buttonBox = new HBox(10);
            buttonBox.getChildren().add(submitButton);
            buttonBox.getChildren().add(menuButton);
            buttonBox.setAlignment(Pos.CENTER);
            grid.add(buttonBox, 0, 3, 3, 1);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void openSummaryPage(Stage primaryStage) {
        try {
            // create grid, create scene -TCP
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.TOP_CENTER);
            grid.setPadding(new Insets(25, 25, 25, 25));
            grid.setHgap(10);
            grid.setVgap(10);

            Scene scene = new Scene(grid, 400, 200);

            Label label = new Label("Generate Multiple-Game Summary");
            label.setLineSpacing(5);
            grid.add(label, 0, 0);

            Label gamesLabel = new Label("Select game(s) by date: ");
            label.setLineSpacing(5);
            grid.add(gamesLabel, 0, 1);

            // create instance of DatabaseManager class to connect to DB
            DatabaseManager dbm = new DatabaseManager();

            // create game dates combo box
            ArrayList<String> gameDates = dbm.getGameDates();
            ComboBox<String> gamesCombo = new ComboBox<>();
            gamesCombo.setPromptText("Select Game");
            for (String game : gameDates) {
                gamesCombo.getItems().add(game);
            }
            grid.add(gamesCombo, 2, 1);

            // Create Add, Generate, and Menu buttons - TCP
            ArrayList<String> selectedGames = new ArrayList<>();

            Button addButton = new Button("Add");
            addButton.setOnAction(event -> addToSummary(gamesCombo.getSelectionModel().getSelectedItem(), selectedGames));

            Button submitButton = new Button("Generate");
            submitButton.setOnAction(event -> generateSummary(selectedGames));

            Button menuButton = new Button("Menu");
            menuButton.setOnAction(event -> showMenu(primaryStage));

            HBox buttonBox = new HBox(10);
            buttonBox.getChildren().add(addButton);
            buttonBox.getChildren().add(submitButton);
            buttonBox.getChildren().add(menuButton);
            buttonBox.setAlignment(Pos.CENTER);
            grid.add(buttonBox, 0, 3, 3, 1);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void addToSummary(String gameDate, ArrayList<String> datesList) {
        // Added data validation-JP
        if (gameDate == null || gameDate.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Game Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a game before adding to summary.");
            alert.showAndWait();
        } else {
            if (datesList.contains(gameDate)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Submission");
                alert.setHeaderText(null);
                alert.setContentText("The selected game is already in the summary.");
                alert.showAndWait();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Submission Confirmation");
                alert.setHeaderText("Game added");
                alert.setContentText("Game has been added to summary.");
                alert.showAndWait();

                datesList.add(gameDate);
            }
        }
    }
    private void generateSingleGameReport(String selectedGameDate) {
    // Added method data validation-JP
    if (selectedGameDate == null || selectedGameDate.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("No Game Selected");
        alert.setHeaderText(null);
        alert.setContentText("Please select a game before generating the report.");
        alert.showAndWait();
    } else {
        try {
            String outputFileName = selectedGameDate + ".txt"; // Define the output file name
            SingleGameReport report = new SingleGameReport(selectedGameDate, outputFileName);
            report.generateReport();
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
    }
}

    private void generateSummary(ArrayList<String> datesList) {
    // Added data validation-JP
    if (datesList.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("No Game Selected");
        alert.setHeaderText(null);
        alert.setContentText("Please add at least one game before generating the summary.");
        alert.showAndWait();
    } else {
        boolean invalidSelection = false;
        for (String selectedGameDate : datesList) {
            if (selectedGameDate.equals("Select Game")) {
                invalidSelection = true;
            }
        }

        if (invalidSelection) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a valid game before generating the summary.");
            alert.showAndWait();
        } else {
            Collections.sort(datesList);
                // Get the first and last game dates
                String firstGame = datesList.get(0);
                String lastGame = datesList.get(datesList.size() - 1);

                // Generate the filename
                String filename = firstGame + "-" + lastGame + ".txt";
                MultiGameReport summary = new MultiGameReport(datesList, filename);
                summary.generateTotalStatisticsReport();
        }
    }
}

    private void exitButtonClicked() {
        System.exit(0);   // 0 indicates a normal exit
    }

    public static void main(String[] args) {
        launch(args);
    }
}
