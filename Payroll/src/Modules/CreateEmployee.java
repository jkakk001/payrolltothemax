
package Modules;
import payroll.*;
import java.util.Scanner;

/**
 * This module is used for creating new employees
 * @author bross
 */
public class CreateEmployee
{
    /**
     * This is the main employee creation loop
     * @return      false to stop looping
     */
    public boolean Update()
    {
        Scanner input = new Scanner(System.in);
        int choice = -1;

        //Menu
        PrintMenuChoices();

        if (input.hasNextInt())
            choice = input.nextInt();

        switch (choice)
        {
            //Hourly
            case 1:
                CreateHourlyEmployee();
                break;
            //Salary
            case 2:
                CreateSalaryEmployee();
                break;
            //Commission
            case 3:
                CreateCommissionEmployee();
                break;
            //Previous Menu
            case 99:
                Globals.currentState = Globals.State.AdminMenu;
                return false;
            default:
                System.out.println("\nInvalid choice.");
                return true;
        }

        Globals.currentState = Globals.State.AdminMenu;
        return false;
    }

    void CreateHourlyEmployee()
    {
        //TODO
    }

    void CreateSalaryEmployee()
    {
        //TODO
    }

    void CreateCommissionEmployee()
    {
        //TODO
    }

    void PrintMenuChoices()
    {
        System.out.println("\n**Create New Employee Menu**");
        System.out.println("Please select employee type: ");
        System.out.println("(1)  Hourly \n(2)  Salary \n(3)  Commission" +
                         "\n(99) Previous Menu");
        System.out.print("Choice: ");
    }
}
