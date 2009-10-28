
package payroll;

/**
 * Hourly-based Employee
 * @author bross
 */
public class Employee_Hourly extends Employee
{

    /**
     *  Default Constructor
     */
    public Employee_Hourly()
    {
        payType = 1;

        int tempID = 10000;
        boolean okayID = false;
        boolean isDuplicate = false;

        while (!okayID)
        {
            isDuplicate = false;
            for (Employee e: Globals.Employees)
            {
                if (e.getEmployeeID() == tempID)
                {
                    isDuplicate = true;
                    break;
                }
            }

            if (isDuplicate)
                tempID++;
            else
                okayID = true;
        }

        employeeID = tempID;
    }

     /**
     *  Used for creating an example or blank hourly employee
     *  @param Empty     If false, a John Doe employee is created
     */
    public Employee_Hourly(Boolean Empty)
    {
        int tempID = 10000;
        boolean okayID = false;
        boolean isDuplicate = false;
        
        if (Empty == false)
        {
            firstName = "John";
            lastName = "Mills";
            address1 = "123 Birch Street";
            city = "Salt Lake City";
            state = "UT";
            zip = 84104;
            payType = 1;
            employeeID = 10002;
            password = "password";
            isAdmin = false;
        }
        else
        {
            firstName = "";
            lastName = "";
            address1 = "";
            city = "";
            state = "";
            zip = 0;
            payType = 1;
            while (!okayID)
            {
                isDuplicate = false;
                for (Employee e: Globals.Employees)
                {
                    if (e.getEmployeeID() == tempID)
                    {
                        isDuplicate = true;
                        break;
                    }
                }

                if (isDuplicate)
                    tempID++;
                else
                    okayID = true;
            }
            employeeID = tempID;
            password = "";
            isAdmin = false;
        }
    }

}
