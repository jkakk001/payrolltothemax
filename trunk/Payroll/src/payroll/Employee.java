
package payroll;
import java.io.Serializable;

/**
 *  This class contains basic employee information for use in the database
 *  @author bross
 */
public class Employee implements Serializable
{
    protected String firstName;
    protected String lastName;
    protected String address1;
    protected String address2;
    protected String city;
    protected String state;
    protected int zip;
    protected int payType;
    protected int employeeID;
    protected Boolean isAdmin;
    protected String password;

    public String getFirstName()
    {
        if (firstName != null)
            return firstName;
        else
            return "(Empty)";
    }
    public void setFirstName(String value)
    {
        firstName = value;
    }

    public String getLastName()
    {
        if (lastName != null)
            return lastName;
        else
            return "(Empty)";
    }
    public void setLastName(String value)
    {
        lastName = value;
    }

    public String getAddress1()
    {
        if (address1 != null)
            return address1;
        else
            return "(Empty)";
    }
    public void setAddress1(String value)
    {
        address1 = value;
    }

    public String getAddress2()
    {
        if (address2 != null)
            return address2;
        else
            return "(Empty)";
    }
    public void setAddress2(String value)
    {
        address2 = value;
    }

    public String getCity()
    {
        if (city != null)
            return city;
        else
            return "(Empty)";
    }
    public void setCity(String value)
    {
        city = value;
    }

    public String getState()
    {
        if (state != null)
            return state;
        else
            return "(Empty)";
    }
    public void setState(String value)
    {
        state = value;
    }

    public int getZip()
    {
        return zip;
    }
    public void setZip(int value)
    {
        zip = value;
    }
//    //Just in case a string is passed to it
//    public void setZip(String value)
//    {
//        zip = Integer.parseInt(value);
//    }

    public int getPayType()
    {
        return payType;
    }
    public void setPayType(int value)
    {
        payType = value;
    }
//    //Just in case a string is passed to it
//    public void setPayType(String value)
//    {
//        payType = Integer.parseInt(value);
//    }

    public int getEmployeeID()
    {
        return employeeID;
    }
    public void setEmployeeID(int value)
    {
        employeeID = value;
    }
//    //Just in case a string is passed to it
//    public void setEmployeeID(String value)
//    {
//        employeeID = Integer.parseInt(value);
//    }

    public Boolean getIsAdmin()
    {
        return isAdmin;
    }
    public void setIsAdmin(Boolean value)
    {
        isAdmin = value;
    }
//    //Just in case a string is passed to it
//    public void setIsAdmin(String value)
//    {
//        isAdmin = Boolean.parseBoolean(value);
//    }

    public String getPassword()
    {
        return password;
    }
    public void setPassword(String value)
    {
        password = value;
    }

     /**
     *  Default Constructor
     */
    public Employee()
    {

    }

     /**
     *  Used for creating an example or blank employee
     *  @param Empty     If false, a John Doe employee is created
     */
    public Employee(boolean Empty)
    {
        int tempID = 10000;
        boolean okayID = false;
        boolean isDuplicate = false;

        //Find the next available employee ID
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

        if (Empty == false)
        {
            firstName = "John";
            lastName = "Mills";
            address1 = "123 Birch Street";
            address2 = "(Empty)";
            city = "Salt Lake City";
            state = "UT";
            zip = 84104;
            password = "password";
            isAdmin = false;
        }
        else
        {
            firstName = "(Empty)";
            lastName = "(Empty)";
            address1 = "(Empty)";
            address2 = "(Empty)";
            city = "(Empty)";
            state = "(Empty)";
            zip = 0;
            password = "(Empty)";
            isAdmin = false;
        }
    }

    /**
    *   Prints out the basic employee information
    */
    public void printInfo()
    {
        if (firstName != null && !firstName.equals("null"))
            System.out.println(firstName + " " + lastName);
        if (lastName != null && !lastName.equals("null"))
            System.out.println(address1);
        if (address2 != null && !address2.equals("null"))
            System.out.println(address2);
        if (city != null && !city.equals("null"))
            System.out.print(city + ", ");
        if (state != null && !state.equals("null"))
            System.out.print(state + " ");

        System.out.println(zip + "\n");
        System.out.println("Pay Type: " + payType);
        System.out.println("Employee ID: " + employeeID + "\n");
    }



}
