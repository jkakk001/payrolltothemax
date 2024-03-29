
package Modules;
import payroll.*;
import java.util.Scanner;

/**
 * This module handles all the administrative actions such as editing
 * employee information, making time corrections, and printing out pay checks.
 * @author Blake
 */
public class AdminMenu_Module
{
    /**
     *  Loops through the Administrator Menu
     *  @return     false to stop looping
     */
    public boolean Update()
    {
        //Menu selection variables
        int menuSelection = 1;
        Scanner in = new Scanner(System.in);
        
        System.out.println(" " + Globals.getDateTime(false) + "\n");
        System.out.println("**ADMINISTRATOR MENU**\n");

        PrintMenuChoices();
        System.out.print("Choice: ");

        //Make sure the input is an integer to prevent exceptions
        if (in.hasNextInt())
            menuSelection = in.nextInt();
        System.out.println("");

        switch(menuSelection)
        {
            //Create Employee Record
            case 1:
                Globals.currentState = Globals.State.CreateEmployee;
                return false;
            //Edit/View Existing Employee Record
            case 2:
                Globals.currentState = Globals.State.SelectEmployee;
                return false;
            //Print the employee list to the screen
            case 3:
                Globals.currentState = Globals.State.EmployeeList;
                return false;
            //Calculate employee pay and print pay checks
            case 4:
                Globals.currentState = Globals.State.CalculatePay;
                return false;
            //Previous menu
            case 99:
                Globals.currentState = Globals.State.MainMenu;
                return false;
            default:
                System.out.println("Invalid choice.");
                break;
        }
            
        //Keep looping
        return true;
    }

    /**
     * Prints the menu choices
     */
    void PrintMenuChoices()
    {
        System.out.println("(1)  - Create Employee Record");
        System.out.println("(2)  - Edit/View Existing Employee Record");
        System.out.println("(3)  - View Employee List");
        System.out.println("(4)  - Calculate Employee Pay");
        System.out.println("(99) - Back to Main Menu");
    }

}
