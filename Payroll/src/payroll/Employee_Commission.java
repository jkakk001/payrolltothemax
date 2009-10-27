
package payroll;

/**
 * Commission-based Employee
 * @author bross
 */
public class Employee_Commission extends Employee
{
    /**
     *  Default Constructor
     */
    public Employee_Commission()
    {
        payType = 3;
    }

     /**
     *  Used for creating an example or blank commission based employee
     *  @param Empty     If false, a John Doe employee is created
     */
    public Employee_Commission(Boolean Empty)
    {
        if (Empty == false)
        {
            firstName = "John";
            lastName = "Mills";
            address1 = "123 Birch Street";
            city = "Salt Lake City";
            state = "UT";
            zip = 84104;
            payType = 3;
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
            payType = 3;
            employeeID = 10000;
            password = "";
            isAdmin = false;
        }
    }
}
