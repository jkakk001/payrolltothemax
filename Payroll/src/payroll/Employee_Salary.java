
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

    }

     /**
     *  Used for creating an example or blank salary employee
     *  @param Empty     If false, a John Doe employee is created
     */
    public Employee_Salary(boolean Empty)
    {
        super(Empty);

        //Salary - $600
        rate = 600.00f;
    }

}
