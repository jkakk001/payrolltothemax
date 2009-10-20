
package Modules;
import payroll.*;
import java.util.Scanner;

/**
 *  This module contains the main menu of the program
 *  @author Blake
 */
public class MainMenu {

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

        System.out.println("\n" + Globals.getDateTime(false) + "\n");
        
        //Print the menu options to the screen
        System.out.println("Please choose an option from below: ");
        System.out.println("1 - Clock");
        if (Globals.currentUser.getIsAdmin())
            System.out.println("2 - Administration");
        System.out.println("9 - Quit");
        System.out.print("Selection: ");
        if (in.hasNextInt())
            menuSelection = in.nextInt();
        System.out.println("");

        //Check the input
        switch(menuSelection)
        {

            //Clock in and out, view timesheet etc.
            case 1:
                Globals.currentState = Globals.State.TimeClock;
                return false;
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
            case 9:
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
