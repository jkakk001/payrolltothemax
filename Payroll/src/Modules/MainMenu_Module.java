
package Modules;
import payroll.*;
import java.util.Scanner;

/**
 *  This module contains the main menu of the program
 *  @author Blake
 */
public class MainMenu_Module {

    /**
     *  Displays the main menu and directs the
     *  user to the corresponding functions.
     * @return      false to stop looping.
     */
    public Boolean Update()
    {
        //Menu selection variables
        int menuSelection = 1;
        Scanner in = new Scanner(System.in);

        System.out.println(Globals.getDateTime(false) + "\n");

        System.out.println("**MAIN MENU**\n");

        //Print the menu options to the screen
        System.out.println("Please choose an option from below: ");
        if (Globals.currentUser instanceof Employee_Hourly)
            System.out.println("(1)  - Clock");
        else if (Globals.currentUser instanceof Employee_Commission)
            System.out.println("(1)  - View Commission");
        //Shows the administrator menu option if the user has admin rights
        if (Globals.currentUser.getIsAdmin())
            System.out.println("(2)  - Administrator Menu");
        System.out.println("(99) - Quit");
        System.out.print("Choice: ");
        if (in.hasNextInt())
            menuSelection = in.nextInt();
        System.out.println("");

        //Check the input
        switch(menuSelection)
        {

            //Clock in and out, view timesheet etc.
            case 1:
                if (Globals.currentUser instanceof Employee_Hourly)
                {
                    Globals.currentState = Globals.State.TimeClock;
                    return false;
                }
                else if (Globals.currentUser instanceof Employee_Commission)
                {
                    Globals.currentState = Globals.State.ViewCommission;
                    return false;
                }
            //Admin menu
            case 2:
                //Check for Admin access
                if (Globals.currentUser.getIsAdmin())
                {
                    Globals.currentState = Globals.State.AdminMenu;
                    return false;
                }
                else
                {
                    System.out.println("You do not have administrative access.");
                    return true;
                }
            //Quit the program
            case 99:
                Globals.currentState = Globals.State.Quit;
                return false;
            //Invalid Entry
            default:
                System.out.println("Invalid Choice.");
                break;

        }

        return true;
    }

}
