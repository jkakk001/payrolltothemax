/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;
import payroll.*;
import java.util.Scanner;

/**
 * This module is used for editing employee records
 * @author Blake
 */
public class EditEmployee
{

    public boolean Update()
    {
        Scanner input = new Scanner(System.in);
        int choice = -1;
        
        System.out.println("**Edit Employee Menu**");
        Globals.currentEmployee.printInfo();

        while (choice != 9)
        {
            //Menu
            PrintMenuChoices();
            System.out.print("Choice: ");

            if (input.hasNextInt())
                choice = input.nextInt();

            //TODO
            switch (choice)
            {
                //Name
                case 1:
                    break;
                //Address 1
                case 2:
                    break;
                //Address 2
                case 3:
                    break;
                //City
                case 4:
                    break;
                //State
                case 5:
                    break;
                //Zip
                case 6:
                    break;
                //Password
                case 7:
                    break;
                //Is Administrator
                case 8:
                    break;
                //Previous Menu
                case 99:
                    Globals.currentState = Globals.State.AdminMenu;
                    return false;
                default:
                    System.out.println("\nInvalid choice.");
            }
        }

        return false;
    }

    void PrintMenuChoices()
    {
        System.out.println("(1)  Full Name: \t" + Globals.currentEmployee.getFirstName()
                        + " " + Globals.currentEmployee.getLastName());
        System.out.println("(2)  Address 1: \t" + Globals.currentEmployee.getAddress1());
        System.out.println("(3)  Address 2: \t" + Globals.currentEmployee.getAddress2());
        System.out.println("(4)  City:      \t" + Globals.currentEmployee.getCity());
        System.out.println("(5)  State:     \t" + Globals.currentEmployee.getState());
        System.out.println("(6)  Zip:       \t" + Globals.currentEmployee.getZip());
        System.out.println("(7)  Password:  \t" + Globals.currentEmployee.getPassword());
        System.out.println("(8)  Administrator:     " + Globals.currentEmployee.getIsAdmin());
        System.out.println("(99) Back");
    }

}
