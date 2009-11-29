
package Modules;
import payroll.*;
import java.util.*;

/**
 * This module is used for selecting an employee to edit.  The user can
 * either use a search function or enter the employee ID directly
 * @author bross
 */
public class SelectEmployee_Module
{

    /**
     * Loops through the employee selection screen
     * @return      false to stop looping
     */
    public boolean Update()
    {
        //Used for input
        Scanner inputInt = new Scanner(System.in);
        int choice = -1;
        
        System.out.println("    " + Globals.getDateTime(false) + "\n");
        System.out.println("**EMPLOYEE SELECTION MENU**\n");

        while (choice != 99)
        {
            //Print the menu
            PrintMenuChoices();

            //Make sure the input is an integer to prevent exceptions
            if (inputInt.hasNextInt())
                choice = inputInt.nextInt();

            switch (choice)
            {
                //Search By Name
                case 1:
                    if (SearchByName())
                        return false;
                    else
                        break;
                //Enter Employee ID
                case 2:
                    if (EnterEmployeeID())
                        return false;
                    else
                        break;
                //List all employees
                case 3:
                    Globals.currentState = Globals.State.EmployeeList;
                    return false;
                case 99:
                    Globals.currentState = Globals.State.AdminMenu;
                    return false;
                default:
                    System.out.println("\nInvalid choice.");
            }
        }

        return false;
    }

    /**
     * Prints all the menu choices
     */
    void PrintMenuChoices()
    {
        System.out.println("(1)  Search by Name");
        System.out.println("(2)  Enter employee ID directly");
        System.out.println("(3)  List all employees");
        System.out.println("(99) Back");
        System.out.print("Choice: ");
    }

    /**
     * Allows the user to search for an employee by name
     * @return      true for match, false otherwise
     */
    boolean SearchByName()
    {
        //For user input
        Scanner inputString = new Scanner(System.in);
        Scanner inputInt = new Scanner(System.in);
        String firstName = "";
        String lastName = "";
        int choice = -1;

        //For holding possible matches
        List<Employee> possibleMatches = new ArrayList<Employee>();

        //Prompt for input
        System.out.print("Please Enter the FIRST name of the employee: ");
        firstName = inputString.next();
        System.out.print("Please Enter the LAST name of the employee: ");
        lastName = inputString.next();

        //Checks for first AND last name matches
        for (Employee e : Globals.Employees)
            if (e.getLastName().toLowerCase().contains(lastName.toLowerCase()) && e.getFirstName().toLowerCase().contains(firstName.toLowerCase()))
                possibleMatches.add(e);

        //If there's only one match
        if (possibleMatches.size() == 1)
        {
            Globals.currentEmployee = possibleMatches.get(0);
            Globals.currentState = Globals.State.EditEmployee;
            return true;
        }

        //Checks for only last name matches
        for (Employee e : Globals.Employees)
            if (e.getLastName().toLowerCase().contains(lastName.toLowerCase()))
                if (!possibleMatches.contains(e)) possibleMatches.add(e);

        //Checks for only first name matches
        for (Employee e : Globals.Employees)
            if (e.getFirstName().toLowerCase().contains(firstName.toLowerCase()))
                if (!possibleMatches.contains(e)) possibleMatches.add(e);

        //If there are ANY possible matches, print out the options
        if (possibleMatches.size() > 0)
        {
            while (choice != 99)
            {
                for (Employee e : possibleMatches)
                    System.out.println("(" + (possibleMatches.indexOf(e)+1) + ") " +
                                    e.getFirstName() + " " + e.getLastName());

                System.out.println("(99) Previous Menu");
                System.out.print("Choice: ");
                if (inputInt.hasNextInt())
                    choice = inputInt.nextInt();

                if (choice <= possibleMatches.size())
                {
                    Globals.currentEmployee = possibleMatches.get(choice - 1);
                    Globals.currentState = Globals.State.EditEmployee;
                    return true;
                }
                else
                    System.out.println("ERROR: Invalid choice.");

            }
        }
        else
        {
                System.out.println("\nCould not find any possible matches.  Please try again.\n");
        }
        
        return false;
    }

    /**
     * This gets user input for the employee ID then checks to make sure it exists
     * @return      true if a match was found
     */
    boolean EnterEmployeeID()
    {
        //User input stuff
        Scanner inputInt = new Scanner(System.in);
        int employeeID = -1;

        System.out.print("Please enter an employee ID number: ");
        //Grabs the user's input for the employee ID
        if (inputInt.hasNextInt())
            employeeID = inputInt.nextInt();

        //Goes through the list of Employees
        for (Employee e : Globals.Employees)
        {
            //If there is an employee with that ID number
            if (e.getEmployeeID() == employeeID)
            {
                Globals.currentEmployee = e;
                Globals.currentState = Globals.State.EditEmployee;
                return true;
            }
        }

        System.out.println("\nThere are no employees with that ID in the database.\n");
        return false;
    }

}
