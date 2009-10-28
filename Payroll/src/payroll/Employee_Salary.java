
package payroll;

/**
 * Salary-based Employee
 * @author bross
 */
public class Employee_Salary extends Employee
{
    /**
     *  Default Constructor
     */
    public Employee_Salary()
    {
        payType = 2;

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
     *  Used for creating an example or blank salary employee
     *  @param Empty     If false, a John Doe employee is created
     */
    public Employee_Salary(Boolean Empty)
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
            payType = 2;
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
            payType = 2;

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
