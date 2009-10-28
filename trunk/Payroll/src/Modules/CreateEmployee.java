
package Modules;
import payroll.*;
import java.util.Scanner;
import java.io.File;

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
        System.out.println("\n**CREATE NEW EMPLOYEE MENU**\n");
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

        return false;
    }

    /**
     * Creates a new hourly-based employee
     */
    void CreateHourlyEmployee()
    {
        Globals.currentEmployee = new Employee_Hourly(true);
        CreateDataFiles();
        Globals.currentState = Globals.State.EditEmployee;
    }

    /**
     * Creates a new salary-based employee
     */
    void CreateSalaryEmployee()
    {
        Globals.currentEmployee = new Employee_Salary(true);
        CreateDataFiles();
        Globals.currentState = Globals.State.EditEmployee;
    }

    /**
     * Creates a new commission-based employee
     */
    void CreateCommissionEmployee()
    {
        Globals.currentEmployee = new Employee_Commission(true);
        CreateDataFiles();
        Globals.currentState = Globals.State.EditEmployee;
    }

    /**
     * Creates the data files for the new employee
     */
    void CreateDataFiles()
    {
        String directory = "Database\\" + Globals.currentEmployee.getEmployeeID() + "\\";
        Serialize.SaveToXML(directory, "Employee.xml", Globals.currentEmployee);
    }

    /**
     * Prints the menu choices
     */
    void PrintMenuChoices()
    {
        System.out.println("Please select employee type: ");
        System.out.println("(1)  Hourly \n(2)  Salary \n(3)  Commission" +
                         "\n(99) Previous Menu");
        System.out.print("Choice: ");
    }
}
