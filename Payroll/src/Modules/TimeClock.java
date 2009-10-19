
package Modules;
import payroll.*;

/**
 * This module handles the viewing of employee hours and clocking in/out etc.
 * @author Blake
 */
public class TimeClock
{
    /**
     * This module loops through the timeclock menu until it returns false
     * @return      false to end the loop
     */
    public boolean Update()
    {
        Globals.currentState = Globals.State.MainMenu;
        //Return false for now until it actuall does something.
        return false;
    }

}
