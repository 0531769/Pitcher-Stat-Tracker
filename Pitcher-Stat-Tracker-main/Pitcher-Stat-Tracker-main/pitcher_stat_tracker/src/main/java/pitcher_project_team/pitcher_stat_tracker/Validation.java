/**
 * Name: Duane Gandelot
 * Date: 4/24/24
 * Team: Pitcher Team (Trevor Pence, Julius Peterson, Jay Lee)
 * Purpose: Create validation for program.
 */

package pitcher_project_team.pitcher_stat_tracker;

import javafx.application.Application;

public class Validation  {
    
    private final String lineEnd;
    
    public Validation() {
        this.lineEnd = "\n";
    }
    
    public Validation(String lineEnd) {
        this.lineEnd = lineEnd;
    }
    

    public String isPresent(String value, String name) {
        String msg = "";
        if (value.isEmpty()) {
            msg = name + " is required." + lineEnd;
            // make sure string begins and ends with letters
         } else if (!value.matches("^[A-Za-z,' ]+")) {
            msg = "Invalid " + name + ". Please enter a valid name with letters only." + lineEnd;
        }
        return msg;
    }
    
    
    public String isDouble (String value, String name) {
        String msg = "";
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException e){
            msg = name + " must be a valid number." + lineEnd;
        }
        return msg;
    }
    
    public String isInteger(String value, String name) {
        String msg = "";
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            msg = name + " must be an integer." + lineEnd;
        }
        return msg;
    }
    
}
