
package com.mycompany.pitcher;

/**
 * Name: Duane Gandelot
 * Date: 4/23/24
 * Team: Pitcher Team (Trevor Pence, Julius Peterson, Jay Lee)
 * Purpose: Create Pitcher class
 */

public class Pitcher {
    private String firstName;
    private String lastName;
    private String teamName;
    private double inningsPitched;
    private int hits;
    private int runs;
    private int earnedRuns;
    private int walks;
    private int strikeouts;
    private int atBats;
    private int battersFaced;
    private int numberOfPitches;
    private String dateOfGame;
    
    public Pitcher (String firstName, String lastName, String teamName,
            double inningsPitched, int hits, int runs, int earnedRuns,
            int walks, int strikeouts, int atBats, int battersFaced,
            int numberOfPitches, String dateOfGame){
  
       this.firstName = firstName;
       this.lastName = lastName;
       this.teamName = teamName;
       this.inningsPitched = inningsPitched;
       this.hits = hits;
       this.runs = runs;
       this.earnedRuns = earnedRuns;
       this.walks = walks; 
       this.strikeouts = strikeouts;
       this.atBats = atBats;
       this.battersFaced = battersFaced;
       this.numberOfPitches = numberOfPitches;
       this.dateOfGame = dateOfGame;
    }
    
    // get and set methods
   
    // get first name
    public String getFirstName() {
        return firstName;
    }
   
    // set first name
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
        
    // get last name
    public String getLastName() {
        return lastName;
    }
    
    // set last name
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    // get team name
    public String getTeamName() {
        return teamName;
    }
    
    // set team name
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    // get innings pitched
    public double getInningsPitched() {
        return inningsPitched;
    }
    
    // set innings pitched
    public void setInningPitched(double inningsPitched){
        this.inningsPitched = inningsPitched;
    }
    
    // get hits
    public int getHit() {
        return hits;
    }
    
    // set hits
    public void setHit(int hits){
        this.hits = hits;
    }
    
    // get runs
    public int getRun() {
        return runs;
    }
    
    // set runs
    public void setRun(int runs){
        this.runs = runs;
    } 
    
    // get earned runs
    public int getEarnedRuns() {
        return earnedRuns;
    }
    
    // set earned runs
    public void setEarnedRuns(int earnedRuns){
        this.earnedRuns = earnedRuns;
    } 
    
    // get walks
    public int getWalks() {
        return walks;
    }
    
    // set walks
    public void setWalks(int walks){
        this.walks = walks;
    } 
    
    // get strikeouts
    public int getStrikeout() {
        return strikeouts;
    }
    
    // set strikeouts
    public void setStrikeout(int strikeouts){
        this.strikeouts = strikeouts;
    } 
    
    // get at bats
    public int getAtBat() {
        return atBats;
    }
    
    // set at bats
    public void setAtBat(int atBats){
        this.atBats = atBats;
    } 
    
    // get at bats
    public int getBattersFaced() {
        return battersFaced;
    }
    
    // set at bats
    public void setBattersFaced(int battersFaced){
        this.battersFaced = battersFaced;
    } 
    
    // get number of pitches
    public int getNumberOfPitches() {
        return numberOfPitches;
    }
    
    // set number of pitches
    public void setNumberOfPitches(int numberOfPitches){
        this.numberOfPitches = numberOfPitches;
    } 
    
        // get date of game
    public String getDateOfGame() {
        return dateOfGame;
    }
    
    // set date of game
    public void setDateOfGame(String dateOfGame){
        this.dateOfGame = dateOfGame;
    } 


    // calculate the earned run average
    
    // ** Need to modify to take into account partial innings
    // (.1 = 1/3 of an inning) .333
    // (.2 = 2/3 of an inning) .666
    public double earnedRunAverage() {
    //
        if (inningsPitched > 0) {
            return (earnedRuns / inningsPitched) * 9;
        } else {
            // if inningsPitched is not valid
            return 0.0;
        }
    }
}
