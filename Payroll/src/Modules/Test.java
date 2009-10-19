
package Modules;
import payroll.*;
/**
 * A test module to make sure certain functions work, like file input/output etc.
 * @author Blake
 */
public class Test {


    /**
     * Creates a test employee, saves/loads it and prints it to the screen.
     * @return          False to stop looping
     */
    public Boolean Update()
    {
        //Create a test employee
        Globals.currentEmployee = new Employee(false);

        //Save and load employee information
        Serialize.SaveToXML("Database\\", Globals.currentEmployee.getEmployeeID() + ".xml", Globals.currentEmployee);

        Employee loadedEmployee = (Employee) Serialize.LoadFromXML("Database\\", Globals.currentEmployee.getEmployeeID() + ".xml");

        //Print loadedEmployee Information
        System.out.println("Loaded Employee Info: ");
        loadedEmployee.printInfo();

        Serialize.RetrieveEmployees();
        return false;
    }

}
