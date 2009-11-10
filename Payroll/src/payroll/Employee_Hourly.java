
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

    }

     /**
     *  Used for creating an example or blank hourly employee
     *  @param Empty     If false, a John Doe employee is created
     */
    public Employee_Hourly(boolean Empty)
    {
        super(Empty);

        rate = 8.00f;
    }

}
