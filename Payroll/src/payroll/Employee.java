
package payroll;
import java.io.Serializable;

/**
 *  This class contains basic employee information for use in the database
 *  @author bross
 */
public class Employee implements Serializable
{
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private int zip;
    private int payType;
    private int employeeID;
    private Boolean isAdmin;
    private String password;

    //Get the first name
    public String getFirstName()
    {
        return firstName;
    }
    //Set the first name
    public void setFirstName(String value)
    {
        firstName = value;
    }

    //Get the last name
    public String getLastName()
    {
        return lastName;
    }
    //Set the last name
    public void setLastName(String value)
    {
        lastName = value;
    }

    //Get the first line of the address
    public String getAddress1()
    {
        return address1;
    }
    //Set the first line of the address
    public void setAddress1(String value)
    {
        address1 = value;
    }

    //Get the second line of the address
    public String getAddress2()
    {
        return address2;
    }
    //Set the second line of the address
    public void setAddress2(String value)
    {
        address2 = value;
    }

    //Get the city
    public String getCity()
    {
        return city;
    }
    //Set the city
    public void setCity(String value)
    {
        city = value;
    }

    //Get the state
    public String getState()
    {
        return state;
    }
    //Set the state
    public void setState(String value)
    {
        state = value;
    }

    //Get the zip
    public int getZip()
    {
        return zip;
    }
    //Set the zip
    public void setZip(int value)
    {
        zip = value;
    }
    //Just in case a string is passed to it
    public void setZip(String value)
    {
        zip = Integer.parseInt(value);
    }

    //Get the employee pay type
    public int getPayType()
    {
        return payType;
    }
    //Set the employee pay type
    public void setPayType(int value)
    {
        payType = value;
    }
    //Just in case a string is passed to it
    public void setPayType(String value)
    {
        payType = Integer.parseInt(value);
    }

    //Get the employee ID
    public int getEmployeeID()
    {
        return employeeID;
    }
    //Set the employee ID
    public void setEmployeeID(int value)
    {
        employeeID = value;
    }
    //Just in case a string is passed to it
    public void setEmployeeID(String value)
    {
        employeeID = Integer.parseInt(value);
    }

    //Get isAdmin
    public Boolean getIsAdmin()
    {
        return isAdmin;
    }
    //Set isAdmin
    public void setIsAdmin(Boolean value)
    {
        isAdmin = value;
    }
    //Just in case a string is passed to it
    public void setIsAdmin(String value)
    {
        isAdmin = Boolean.parseBoolean(value);
    }

    //Get the password
    public String getPassword()
    {
        return password;
    }
    //Set the password
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
    public Employee(Boolean Empty)
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
            payType = 0;
            employeeID = 10000;
            password = "";
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
