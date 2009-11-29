
package Modules;
import payroll.*;

/**
 * This module allows administrators to edit the commission data of an employee.
 * @author Blake
 */
public class EditCommission_Module
{

    /**
     * The Edit Commission main loop
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
        System.out.println("(1)  - ");
        System.out.println("(99) - Back to Main Menu");
    }

}
