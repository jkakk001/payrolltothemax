
package payroll;
import java.util.*;

/**
 *  This class contains all the global variables (reusable/important for all
 *  to see) for the application.
 *  @author bross
 */
public class Globals {

    //This enum lists the different types of States the app can be in.
    public static enum State
    {
        FirstRun,
        Login,
        MainMenu,
        EmployeeList,
        Test,
        Quit,
    }

    //Create a State object
    public static State currentState = State.FirstRun;
    //The current selected employee
    public static Employee currentEmployee;
    //Self explanitory.  If true, the program exits.
    public static Boolean ExitProgram = false;
    //List of all employees
    public static List<Employee> Employees = new ArrayList<Employee>();
}
