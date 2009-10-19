
package Modules;
import payroll.*;
import java.util.Scanner;
import java.io.*;
/**
 * Used for employee authentication
 * @author bross
 */
public class Login {

    /**
     *  This module handles the user ID/password authentication.
     *  @return  false to stop looping
     */
    public static Boolean Update()
    {
        int loginID = 0;
        String loginPassword = "";
        
        //Make two scanners because if we use one, it causes a null pointer
        //exception because we're taking in an int then a string
        Scanner inputID = new Scanner(System.in);
        Scanner inputPass = new Scanner(System.in);

        //Prompt for ID number
        System.out.println("Please enter your employee ID number: ");
        //Make sure it's an Integer, otherwise it throws an exception
        if (inputID.hasNextInt())
            loginID = inputID.nextInt();

        //Prompt for Password
        System.out.println("Please enter your password: ");
        loginPassword = inputPass.nextLine();

        //Check each employee in the list for the login ID and password
        for (Employee emp : Globals.Employees)
        {
            if (emp.getEmployeeID() == loginID)
                if (emp.getPassword().equals(loginPassword))
                {
                    //If successful, welcome the user and change the program state
                    System.out.println("\n\n\n\n\n\n\n\n\n\n");
                    System.out.println("Welcome " + emp.getFirstName() + "!");
                    Globals.currentUser = emp;
                    //Stop looping
                    return false;
                }
        }

        //WRONG!!!
        System.out.println("Incorrect credentials.  " +
                           "Please make sure you typed them in correctly.");

        //Keep looping
        return true;
    }

}
