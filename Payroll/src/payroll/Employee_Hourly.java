/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
    }

     /**
     *  Used for creating an example or blank employee
     *  @param Empty     If false, a John Doe employee is created
     */
    public Employee_Hourly(Boolean Empty)
    {
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
            employeeID = 10000;
            password = "";
            isAdmin = false;
        }
    }

}
