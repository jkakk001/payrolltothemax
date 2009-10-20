
package payroll;
import java.util.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 *  This class contains all the global variables (reusable/important for all
 *  to see) for the application.
 *  @author bross
 */
public class Globals {

    //This enum lists the different types of States the app can be in.
    public static enum State
    { 
        Login,
        MainMenu,
        AdminMenu,
        CreateEmployee,
        EditEmployee,
        EmployeeList,
        TimeClock,
        Test,
        Quit,
    }

    //Create a State object
    public static State currentState = State.Login;
    //The user that is logged in
    public static Employee currentUser;
    //The current selected employee
    public static Employee currentEmployee;
    //Self explanitory.  If true, the program exits.
    public static Boolean ExitProgram = false;
    //Checks to see if an administrator is logged in.
    public static Boolean userIsAdmin = false;
    //List of all employees
    public static List<Employee> Employees = new ArrayList<Employee>();

    public static String getDateTime(boolean dateOnly)
    {
        DateFormat dateFormat;
        if (dateOnly)
            dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        else
            dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Date date = new Date();
        return dateFormat.format(date);
    }
}
