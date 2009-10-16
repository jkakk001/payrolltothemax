
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

            //Print the menu options to the screen
            System.out.println("Please choose an option from below: ");
            System.out.println("1 - Create Employee Record");
            System.out.println("2 - Edit/View Existing Employee Record");
            System.out.println("3 - Test");
            System.out.println("4 - View Employee List");
            System.out.println("9 - Quit");
            System.out.print("Selection: ");
            menuSelection = in.nextInt();
            System.out.println("");

            //Check the input
            switch(menuSelection)
            {
                //Create Employee Record
                case 1:
                    //TODO
                    break;
                //Edit/View Existing Employee Record
                case 2:
                    //TODO
                    break;
                //Test Reading/Writing
                case 3:
                    Globals.currentState = Globals.State.Test;
                    return false;
                //Print the employee list to the screen
                case 4:
                    Globals.currentState = Globals.State.EmployeeList;
                    return false;
                case 9:
                    Globals.currentState = Globals.State.Quit;
                    return false;
                //Invalid Entry
                default:
                    System.out.println("Invalid Choice.");
                    break;


            }
            //Close the input scanner
            in.close();

            return true;
        }

}
