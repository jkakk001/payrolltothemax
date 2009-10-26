/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;
import payroll.*;
import java.util.*;

/**
 * This module is used for selecting an employee to edit.  The user can
 * either use a search function or enter the employee ID directly
 * @author bross
 */
public class SelectEmployee
{

    /**
     * Loops through the employee selection screen
     * @return      false to stop looping
     */
    public boolean Update()
    {
        Scanner inputInt = new Scanner(System.in);
        int choice = -1;

        System.out.println("**Employee Selection**");

        while (choice != 9)
        {
            //Print the menu
            PrintMenuChoices();

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
                    EnterEmployeeID();
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

        Scanner inputString = new Scanner(System.in);
        Scanner inputInt = new Scanner(System.in);
        String firstName = "";
        String lastName = "";
        int choice = -1;

        List<Employee> possibleMatches = new ArrayList<Employee>();

        System.out.print("Please Enter the FIRST name of the employee: ");
        firstName = inputString.next();
        System.out.print("Please Enter the LAST name of the employee: ");
        lastName = inputString.next();

        //Checks for first AND last name matches
        for (Employee e : Globals.Employees)
            if (e.getLastName().toLowerCase().contains(lastName.toLowerCase()) && e.getFirstName().toLowerCase().contains(firstName.toLowerCase()))
                possibleMatches.add(e);

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

        if (possibleMatches.size() > 0)
        {
            while (choice != 99)
            {
                for (Employee e : possibleMatches)
                    System.out.println("(" + possibleMatches.indexOf(e) + ") " +
                                    e.getFirstName() + " " + e.getLastName());

                System.out.println("(99) Previous Menu");
                System.out.println("Choice: ");
                if (inputInt.hasNextInt())
                    choice = inputInt.nextInt();

                
            }
        }

        return false;
    }

    /**
     * This gets user input for the employee ID then checks to make sure it exists
     */
    void EnterEmployeeID()
    {
        Scanner inputInt = new Scanner(System.in);
        int employeeID = -1;

        if (inputInt.hasNextInt())
            employeeID = inputInt.nextInt();

        
    }

}
