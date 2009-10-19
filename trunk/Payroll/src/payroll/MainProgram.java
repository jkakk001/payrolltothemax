/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package payroll;
import Modules.*;
import java.util.Scanner;

/**
 * The main program class.  This handles all the modules and determines what
 * to do depending on the value of Globals.currentState.
 * @author bross
 */
public class MainProgram {
    //Declare the modules
    MainMenu mainMenu;
    Test testing;
    EmployeeList employeeList;
    Login loginScreen;
    TimeClock timeClock;
    AdminMenu adminMenu;

    /**
     * Default constructor; Initializes the modules etc.
     */
    public MainProgram()
    {
        mainMenu = new MainMenu();
        testing = new Test();
        employeeList = new EmployeeList();
        loginScreen = new Login();
        timeClock = new TimeClock();
        adminMenu = new AdminMenu();

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
            //Display the menu
            case MainMenu: MainMenu(); break;
            //Display the menu
            case AdminMenu: AdminMenu(); break;
            //List of Employees
            case EmployeeList: EmployeeList(); break;
            //Runs the test module
            case Test: Testing(); break;
            //Exit the program...
            case Quit: exitProgram(); break;
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
     */
    public void LoginScreen()
    {
        while(loginScreen.Update())
        {}

        Globals.currentState = Globals.State.MainMenu;
    }

    /**
     * Loops through the employeeList update until false
     * @see Modules.EmployeeList
     */
    public void EmployeeList()
    {
        while (employeeList.Update())
        {}

        Globals.currentState = Globals.State.MainMenu;
    }

    /**
     * Loops through the mainMenu update until false
     * @see Modules.MainMenu
     */
    public void MainMenu()
    {
        while (mainMenu.Update())
        {}
    }

    /**
     * Loops through the adminMenu update until false
     * @see Modules.AdminMenu
     */
    public void AdminMenu()
    {
        while (adminMenu.Update())
        {}

    }

    /**
     * Loops through the timeClock update until false
     * @see Modules.TimeClock
     */
    public void TimeClock()
    {
        while (timeClock.Update())
        {}

        Globals.currentState = Globals.State.MainMenu;
    }

    /**
     * Loops through the testing module until false, then goes back to the menu.
     * @see Modules.Test
     */
    public void Testing()
    {
        while (testing.Update())
        {}

        Globals.currentState = Globals.State.MainMenu;
    }

    /**
     * Asks the user to confirm they want to exit, then exits.
     */
    public void exitProgram()
    {
        Scanner in = new Scanner(System.in);
        int confirm = 0;

        System.out.print("Please reenter 9 to quit: ");
        confirm = in.nextInt();
        if (confirm == 9)
            Globals.ExitProgram = true;
        else
            System.out.print("Returning to menu...");
    }


}
