
package Modules;
import payroll.*;
/**
 *  A module meant for displaying the employee list.
 *  @author Blake
 */
public class EmployeeList {

/**
 *  Displays the list of employees in the database.
 *  @return      false to tell the Main Program to stop looping this module.
 */
    public Boolean Update()
    {
        //Go through the list of employees and print their name and ID
        for (int i = 0; i < Globals.Employees.size(); i++)
        {
            System.out.println("Name: " + Globals.Employees.get(i).getFirstName() + " "
                                + Globals.Employees.get(i).getLastName() + " "
                                + "\t\t\tID: " + Globals.Employees.get(i).getEmployeeID());
            //Insert a blank line to keep it clean looking
            if (i == Globals.Employees.size())
                System.out.println("");
        }

        //False so this doesn't keep looping
        return false;
    }

}
