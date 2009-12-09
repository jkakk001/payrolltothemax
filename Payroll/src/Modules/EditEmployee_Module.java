
package Modules;
import payroll.*;
import java.util.Scanner;

/**
 * This module is used for editing employee information
 * @author Blake
 */
public class EditEmployee_Module
{
    Employee temporaryEmployee; //Holds the data that will be changed

    public boolean Update()
    {
        Scanner input = new Scanner(System.in);         //Gets integer input
        Scanner stringInput = new Scanner(System.in);   //Gets string input
        int choice = -1;              //Holds input for the number menus
        String stringChoice = "";     //Holds input for (y/n) menus
        boolean madeChanges = false;  //If true, it will ask to save

        //Load the current employee's data from xml
        temporaryEmployee = (Employee) Serialize.LoadFromXML("Database\\" + Globals.currentEmployee.getEmployeeID() + "\\", "Employee.xml");

        System.out.println("\n " + Globals.getDateTime(false) + "\n");
        System.out.println("**EDIT EMPLOYEE MENU**");
        //temporaryEmployee.printInfo();

        //Loop through the menu, act accordingly
        while (choice != 99)
        {
            //Menu
            PrintMenuChoices();
            System.out.print("Choice: ");

            //Make sure the input is an integer to prevent exceptions
            if (input.hasNextInt())
                choice = input.nextInt();

            //Edit information based on the user's choice
            switch (choice)
            {
                //Name
                case 1:
                    System.out.print("First name: ");
                    temporaryEmployee.setFirstName((String)GetReplacement());
                    System.out.print("Last name: ");
                    temporaryEmployee.setLastName((String)GetReplacement());
                    madeChanges = true;
                    break;
                //Address 1
                case 2:
                    System.out.print("Address 1: ");
                    temporaryEmployee.setAddress1((String)GetReplacement());
                    madeChanges = true;
                    break;
                //Address 2
                case 3:
                    System.out.print("Address 2: ");
                    temporaryEmployee.setAddress2((String)GetReplacement());
                    madeChanges = true;
                    break;
                //City
                case 4:
                    System.out.print("City: ");
                    temporaryEmployee.setCity((String)GetReplacement());
                    madeChanges = true;
                    break;
                //State
                case 5:
                    System.out.print("State: ");
                    temporaryEmployee.setState((String)GetReplacement());
                    madeChanges = true;
                    break;
                //Zip
                case 6:
                    System.out.print("Zip: ");
                    temporaryEmployee.setZip( Integer.parseInt((String)GetReplacement()) );
                    madeChanges = true;
                    break;
                //Password
                case 7:
                    System.out.print("Password: ");
                    temporaryEmployee.setPassword((String)GetReplacement());
                    madeChanges = true;
                    break;
                //Is Administrator
                case 8:
                    System.out.print("Is Administrator (y/n): ");
                    stringChoice = (String) GetReplacement();

                    if ( stringChoice.equals("y") || stringChoice.equals("Y") )
                        temporaryEmployee.setIsAdmin(true);
                    else if ( stringChoice.equals("n") || stringChoice.equals("n") )
                        temporaryEmployee.setIsAdmin(false);

                    madeChanges = true;
                    break;
                //Rate
                case 9:
                    System.out.print("Rate: ");
                    if (temporaryEmployee instanceof Employee_Hourly || temporaryEmployee instanceof Employee_Salary)
                        temporaryEmployee.setRate( Float.parseFloat((String)GetReplacement()) );
                    else if (temporaryEmployee instanceof Employee_Commission)
                        temporaryEmployee.setRate( Float.parseFloat((String)GetReplacement()) / 100 );
                    madeChanges = true;
                    break;
                //Edit Commission/Clock Data
                case 10:
                    if (temporaryEmployee instanceof Employee_Commission)
                        Globals.currentState = Globals.State.EditCommission;
                    //Set the choice to 99 so we can check for changes
                    //and save before going to the other menu
                    choice = 99;
                    break;
                //Previous Menu
                case 99:
                    Globals.currentState = Globals.State.AdminMenu;
                    break;
                default:
                    System.out.println("\nInvalid choice.");
            }
        }

        //Ask if the user wants to save.
        //If so, export to XML and reload employee list
        if (madeChanges)
        {
            stringChoice = "";
            while (!stringChoice.equals("y") && !stringChoice.equals("Y")
                && !stringChoice.equals("n") && !stringChoice.equals("N"))
            {
                System.out.println("Would you like to save the changes you have made? (y/n)");
                System.out.print("Choice: ");
                stringChoice = stringInput.next();
                if (!stringChoice.equals("y") && !stringChoice.equals("Y")
                    && !stringChoice.equals("n") && !stringChoice.equals("N"))
                    System.out.println("Invalid choice.");
            }

            //Export, reload the database
            if (stringChoice.equals("y") || stringChoice.equals("Y"))
            {
                Serialize.SaveToXML("Database\\" + temporaryEmployee.getEmployeeID() + "\\",  "Employee.xml", temporaryEmployee);
                Serialize.RetrieveEmployees();
            }

        }

        return false;
    }

    /**
     * Prints the menu choices
     */
    void PrintMenuChoices()
    {
        System.out.println("(1)  Full Name: \t" + temporaryEmployee.getFirstName()
                        + " " + temporaryEmployee.getLastName());
        System.out.println("(2)  Address 1: \t" + temporaryEmployee.getAddress1());
        System.out.println("(3)  Address 2: \t" + temporaryEmployee.getAddress2());
        System.out.println("(4)  City:      \t" + temporaryEmployee.getCity());
        System.out.println("(5)  State:     \t" + temporaryEmployee.getState());
        System.out.println("(6)  Zip:       \t" + temporaryEmployee.getZip());
        System.out.println("(7)  Password:  \t" + temporaryEmployee.getPassword());
        System.out.println("(8)  Administrator:     " + temporaryEmployee.getIsAdmin());
        if (temporaryEmployee instanceof Employee_Hourly || temporaryEmployee instanceof Employee_Salary)
            System.out.printf( "(9)  Rate:      \t$%1.2f\n", temporaryEmployee.getRate());
        else if (temporaryEmployee instanceof Employee_Commission)
            System.out.printf( "(9)  Rate:      \t%%%1.2f\n", temporaryEmployee.getRate() * 100);
        if (temporaryEmployee instanceof Employee_Commission)
            System.out.printf( "(10) *Enter Commission Data\n");        

        System.out.println("(99) Back");
    }

    /**
     * Gets user input and returns it
     * @return
     */
    Object GetReplacement()
    {
        Scanner input = new Scanner(System.in);
        String replacement = "";
       
        replacement = input.nextLine();

        return replacement;
    }


}
