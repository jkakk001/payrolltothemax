
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
    }

     /**
     *  Used for creating an example or blank salary employee
     *  @param Empty     If false, a John Doe employee is created
     */
    public Employee_Salary(Boolean Empty)
    {
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
            employeeID = 10000;
            password = "";
            isAdmin = false;
        }
    }
}
