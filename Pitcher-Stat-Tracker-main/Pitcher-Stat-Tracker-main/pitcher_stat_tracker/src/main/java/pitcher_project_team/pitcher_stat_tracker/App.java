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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


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
        // create grid, create scene -TCP
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setHgap(10);
        grid.setVgap(10);
        
        Scene scene = new Scene(grid, 600, 200);
        
        // Create Submit, Help, and Exit buttons - TCP
        Button submitButton = new Button("Enter Player Stats");
        submitButton.setOnAction(event -> openPitcherPage(primaryStage));
        
        Button reportButton = new Button("Generate Report(s)");
        reportButton.setOnAction(event -> openReportsPage(primaryStage));
        
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> exitButtonClicked());
        
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().add(submitButton);
        buttonBox.getChildren().add(reportButton);
        buttonBox.getChildren().add(exitButton);
        buttonBox.setAlignment(Pos.CENTER);
        grid.add(buttonBox, 0, 7, 3, 1);

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
        
        Scene scene = new Scene(grid, 440, 570);
        
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
        
        grid.add(new Label("Number of Pitches (np):"), 0, 5);
        numberOfPitchesField = new TextField();
        grid.add(numberOfPitchesField, 1, 5);
        
        grid.add(new Label("Hits Allowed (h):"), 0, 6);
        hitField = new TextField();
        grid.add(hitField, 1, 6);
        
        grid.add(new Label("Runs Allowed (r):"), 0, 7);
        runField = new TextField();
        grid.add(runField, 1, 7);
        
        grid.add(new Label("Earned Runs Allowed (er):"), 0, 8);
        earnedRunField = new TextField();
        grid.add(earnedRunField, 1, 8);
        
        grid.add(new Label("Walks (bb):"), 0, 9);
        walkField = new TextField();
        grid.add(walkField, 1, 9);
        
        grid.add(new Label("Strike Outs (so):"), 0, 10);
        strikeoutField = new TextField();
        grid.add(strikeoutField, 1, 10);
        
        grid.add(new Label("At Bats (ab):"), 0, 11);
        atBatField = new TextField();
        grid.add(atBatField, 1, 11);
        
        grid.add(new Label("Batters Faced (bf):"), 0, 12);
        battersFacedField = new TextField();
        grid.add(battersFacedField, 1, 12);

        grid.add(new Label("Date of Game \n(MM/DD/YYYY):"), 0, 13);
        DateOfGameField = new TextField();
        grid.add(DateOfGameField, 1, 13);

        // Create Submit, Help, and Exit buttons - DG
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> submitButtonClicked());
       
        Button helpButton = new Button("Help");
        helpButton.setOnAction(event -> helpButtonClicked());
       
        Button menuButton = new Button("Menu");
        menuButton.setOnAction(event -> showMenu(primaryStage));
        
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().add(submitButton);
        buttonBox.getChildren().add(helpButton);
        buttonBox.getChildren().add(menuButton);
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
         errorMsg += v.isPresent(DateOfGameField.getText(), "Date of Game");
        
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

    // Inform user that data has been entered into the database -TCP
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Submission Confirmation");
            alert.setHeaderText("Form Submitted");
            alert.setContentText("The form has been submitted.");
            alert.showAndWait();
              
     // Clear text fields
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
    }

    
    // commented out - Not sure how this function will work with GUI -DG
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

    private void openReportsPage(Stage primaryStage) {
        // create grid, create scene -TCP
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
        
        // create sample combo box
        String[] sampleDates = {"04/27/2024", "05/04/2024", "05/11/2024", "05/18/2024"};
        ComboBox<String> gamesCombo = new ComboBox<>();
        gamesCombo.setPromptText("Select Game");
        for(String game : sampleDates) {
            gamesCombo.getItems().add(game);
        }
        grid.add(gamesCombo, 1, 1);
        
        // Create Submit, Help, and Exit buttons - TCP
        Button submitButton = new Button("Generate");
        // temp code
        submitButton.setOnAction(event -> showMenu(primaryStage));
       
        Button menuButton = new Button("Menu");
        menuButton.setOnAction(event -> showMenu(primaryStage));
        
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().add(submitButton);
        buttonBox.getChildren().add(menuButton);
        buttonBox.setAlignment(Pos.CENTER);
        grid.add(buttonBox, 0, 3, 3, 1);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openSummaryPage(Stage primaryStage) {
        // create grid, create scene -TCP
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setHgap(10);
        grid.setVgap(10);
        
        Scene scene = new Scene(grid, 400, 200);
        
        Label label = new Label("Generate Single Game Report");
        label.setLineSpacing(5);
        grid.add(label, 0, 0);
        
        Label gamesLabel = new Label("Select game(s) by date: ");
        label.setLineSpacing(5);
        grid.add(gamesLabel, 0, 1);
        
        // create sample combo box
        String[] sampleDates = {"04/27/2024", "05/04/2024", "05/11/2024", "05/18/2024"};
        ComboBox<String> gamesCombo = new ComboBox<>();
        gamesCombo.setPromptText("Select Game");
        for(String game : sampleDates) {
            gamesCombo.getItems().add(game);
        }
        grid.add(gamesCombo, 2, 1);
        
        // Create Add, Generate, and Menu buttons - TCP
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> addToSummary(gamesCombo.getSelectionModel().getSelectedItem()));
        
        Button submitButton = new Button("Generate");
        // temp code
        submitButton.setOnAction(event -> showMenu(primaryStage));
       
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
    }

    private void addToSummary(String gameDate) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Submission Confirmation");
            alert.setHeaderText("Game added");
            alert.setContentText("Game has been added to summary.");
            alert.showAndWait();
            
            
        // TO DO: create code to add the game to the summary
    }

    private void exitButtonClicked() {
        System.exit(0);   // 0 indicates a normal exit
    }
    
    public static void main(String[] args) {
        launch(args);
    }    
}
