
package Modules;
import payroll.*;

/**
 * This module allows administrators to edit the time clock data of an employee.
 * @author Blake
 */
public class EditClock_Module
{

    /**
     * The Edit Clock main loop
     * @return  false to stop looping
     */
    public boolean Update()
    {
        //Filler
        Globals.currentState = Globals.State.AdminMenu;
        return false;
    }

    /**
     * Prints the menu choices
     */
    void PrintMenuChoices()
    {
        System.out.println("(1)  - Change Time Record");
        System.out.println("(2)  - Delete Time Record");
        System.out.println("(99) - Back to Main Menu");
    }

}
