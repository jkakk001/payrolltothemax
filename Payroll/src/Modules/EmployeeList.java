
package Modules;
import payroll.*;
import java.util.*;

/**
 *  A module meant for displaying the employee list.
 *  @author Blake
 */
public class EmployeeList {

    //Comparators.  Used to sort the list of employees
    static final Comparator<Employee> LASTNAME_ORDER = new Comparator<Employee>()
    {
        public int compare (Employee emp1, Employee emp2)
        {
            return emp1.getLastName().compareTo(emp2.getLastName());
        }
    };//Compares last names

    static final Comparator<Employee> FIRSTNAME_ORDER = new Comparator<Employee>()
    {
        public int compare (Employee emp1, Employee emp2)
        {
            return emp1.getFirstName().compareTo(emp2.getFirstName());
        }
    };//Compares first names

    /**
     *  Displays the list of employees in the database.
     *  @return      false to tell the Main Program to stop looping this module.
     */
    public Boolean Update()
    {
        Scanner input = new Scanner(System.in);
        int choice = -1;

        //This will hold the list of employees for sorting
        List<Employee> employeeList = Globals.Employees;

        //Ask about sorting the list
        System.out.println("(1) Sort by employee ID, (2) last name, or (3) first name?" + "\nChoice: ");
        while (choice == -1)
        {
            if (input.hasNextInt())
                choice = input.nextInt();
            if (!(choice >= 1 && choice <=3))
            {
                System.out.println("Invalid choice.  Please choose a valid one.");
                choice = -1;
            }
        }

        //Sort by last name
        if (choice == 2)
            Collections.sort(employeeList, LASTNAME_ORDER);
        //Sort by first name
        else if (choice == 3)
            Collections.sort(employeeList, FIRSTNAME_ORDER);

        if (choice >= 1 && choice <=3)
        {
            //Go through the list of employees and print their name and ID
            for (int i = 0; i < employeeList.size(); i++)
            {
                if (choice == 1)
                    System.out.format("%s  %s %s\n", employeeList.get(i).getEmployeeID(), employeeList.get(i).getFirstName(), employeeList.get(i).getLastName());
                else if (choice == 2)
                    System.out.format("Name: %s, %s                  %s\n", employeeList.get(i).getLastName(), employeeList.get(i).getFirstName(), employeeList.get(i).getEmployeeID());
                else if (choice == 3)
                    System.out.format("Name: %s %s                  %s\n", employeeList.get(i).getFirstName(), employeeList.get(i).getLastName(), employeeList.get(i).getEmployeeID());

                //Insert a blank line to keep it clean looking
                if (i == employeeList.size())
                    System.out.println("");
            }
        }

        //False so this doesn't keep looping
        return false;
    }

}
