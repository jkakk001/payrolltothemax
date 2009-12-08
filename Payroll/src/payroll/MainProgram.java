/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package payroll;
import Modules.*;
import java.util.Scanner;

/**
 * The main program class.  This handles all the modules and determines what
 * to do depending on the value of Globals.currentState
 * @author bross
 */
public class MainProgram {
    //Declare the modules
    MainMenu_Module mainMenu;
    Test_Module testing;
    EmployeeList_Module employeeList;
    CreateEmployee_Module createEmployee;
    SelectEmployee_Module selectEmployee;
    EditEmployee_Module editEmployee;
    Login_Module loginScreen;
    TimeClock_Module timeClock;
    EditCommission_Module editCommission;
    ViewCommission_Module viewCommission;
    EditClock_Module editClock;
    AdminMenu_Module adminMenu;

    /**
     * Default constructor; Initializes the modules etc.
     */
    public MainProgram()
    {
        mainMenu = new MainMenu_Module();
        testing = new Test_Module();
        employeeList = new EmployeeList_Module();
        createEmployee = new CreateEmployee_Module();
        selectEmployee = new SelectEmployee_Module();
        editEmployee = new EditEmployee_Module();
        loginScreen = new Login_Module();
        timeClock = new TimeClock_Module();
        editCommission = new EditCommission_Module();
        viewCommission = new ViewCommission_Module();
        editClock = new EditClock_Module();
        adminMenu = new AdminMenu_Module();
        
        //Display the welcome message
        DisplayWelcome();
        //Load the employees from the database
        Serialize.RetrieveEmployees();
    }

    /**
     * Checks Globals.currentState to determine what to do next.
     */
    public void Update()
    {
        switch (Globals.currentState)
        {
            //Display the login screen
            case Login:  LoginScreen(); break;
            //Display the login screen
            case TimeClock:  TimeClock(); break;
            //Display the menu
            case MainMenu: MainMenu(); break;
            //Display the administrator menu
            case AdminMenu: AdminMenu(); break;
            //List of Employees
            case EmployeeList: EmployeeList(); break;
            //Edit an employee record
            case EditEmployee: EditEmployee(); break;
            //Search or select an employee
            case SelectEmployee: SelectEmployee(); break;
            //Create a new employee
            case CreateEmployee: CreateEmployee(); break;
            //Edit Employee Commission Data
            case EditCommission: EditCommission(); break;
            //View Employee Commission Data
            case ViewCommission: ViewCommission(); break;
            //Edit Employee Clock Data
            case EditClock: EditClock(); break;
            //Runs the test module
            case Test: Testing(); break;
            //Exit the program...
            case Quit: exitProgram(); break;
            default:
                System.out.println("Internal Error (Unknown Program State)");
                Globals.currentState = Globals.State.MainMenu;
                break;
        }
    }

    /**
     * Displays the program welcome screen.
     */
    public static void DisplayWelcome()
    {
        System.out.println("/|*****************************************************|");
        System.out.println("/|*****************************************************|");
        System.out.println("/|** PAYROLL TO THE MAX! (MAX) (max) (max) <--Echoes **|");
        System.out.println("/|*****************************************************|");
        System.out.println("/|*****************************************************|");
        System.out.println("/////////////////////////////////////////////////////// ");
    }

    /**
     * Loops the login module
     * @see Modules.Login_Module
     */
    public void LoginScreen()
    {
        while(loginScreen.Update())
        {}

    }

    /**
     * Loops through the employeeList update until false
     * @see Modules.EmployeeList_Module
     */
    public void EmployeeList()
    {
        while (employeeList.Update())
        {}

    }

    /**
     * Loops through the selectEmployee update.
     * @see Modules.SelectEmployee_Module
     */
    public void SelectEmployee()
    {
        while (selectEmployee.Update())
        {}

    }

    /**
     * Loops through the editEmployee update.
     * @see Modules.EditEmployee_Module
     */
    public void EditEmployee()
    {
        while (editEmployee.Update())
        {}
    }
    

    /**
     * Loops through the createEmployee update.
     * @see Modules.CreateEmployee_Module
     */
    public void CreateEmployee()
    {
        while (createEmployee.Update())
        {}

    }

    /**
     * Loops through the mainMenu update until false
     * @see Modules.MainMenu_Module
     */
    public void MainMenu()
    {
        while (mainMenu.Update())
        {}
    }

    /**
     * Loops through the adminMenu update until false
     * @see Modules.AdminMenu_Module
     */
    public void AdminMenu()
    {
        while (adminMenu.Update())
        {}

    }

    /**
     * Loops through the timeClock update until false
     * @see Modules.TimeClock_Module
     */
    public void TimeClock()
    {
        timeClock.LoadTimeSheet();
        while (timeClock.Update())
        {}

    }
    
    /**
     * Loops through the editClock update.
     * @see Modules.EditClock_Module
     */
    public void EditClock()
    {
        while (editClock.Update())
        {}
    }

    /**
     * Loops through the editCommission update until false
     */
    public void EditCommission()
    {
        while (editCommission.Update())
        {}
    }

    /**
     * Loops through the viewCommission update until false
     */
    public void ViewCommission()
    {
        while (viewCommission.Update())
        {}
    }

    /**
     * Loops through the testing module until false, then goes back to the menu.
     * @see Modules.Test_Module
     */
    public void Testing()
    {
        while (testing.Update())
        {}

    }

    /**
     * Asks the user to confirm they want to exit, then exits.
     */
    public void exitProgram()
    {
        //User input stuff
        Scanner in = new Scanner(System.in);
        int confirm = 0;

        System.out.print("Please reenter 99 to quit: ");
        confirm = in.nextInt();
        if (confirm == 99)
            Globals.ExitProgram = true;
        else
            System.out.print("Returning to menu...");
    }


}
