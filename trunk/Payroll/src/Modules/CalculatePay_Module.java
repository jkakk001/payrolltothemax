
package Modules;
import payroll.*;

/**
 * This module allows administrators to calculate employee pay and "print" paychecks.
 * @author Blake
 */
public class CalculatePay_Module
{

    /**
     * The Calculate Pay main loop
     * @return  false to stop looping
     */
    public boolean Update()
    {
        System.out.println(" " + Globals.getDateTime(false) + "\n");
        System.out.println("**CALCULATE PAY MENU**\n");

        //Filler

        //Change the state back to the admin menu, stop looping.
        Globals.currentState = Globals.State.AdminMenu;
        return false;
    }

    /**
     * Prints the menu choices
     */
    void PrintMenuChoices()
    {
        System.out.println("(1)  - Print Specific Paycheck");
        System.out.println("(2)  - Print All Paychecks");
        System.out.println("(3)  - Display Payroll Report");
        System.out.println("(99) - Back to Admin Menu");
    }

    /**
     * "Prints" the pay checks for distribution
     */
    void PrintPayChecks()
    {

    }
    

}
