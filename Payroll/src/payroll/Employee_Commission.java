
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

    }

     /**
     *  Used for creating an example or blank commission based employee
     *  @param Empty     If false, a John Doe employee is created
     */
    public Employee_Commission(boolean Empty)
    {
        super(Empty);

        //Commission Rate - 6%
        rate = .06f;
    }


}
