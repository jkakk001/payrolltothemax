
package Modules;
import payroll.*;
import java.util.Scanner;
import java.io.*;
/**
 * Used for employee authentication
 * @author bross
 */
public class Login {

    public static Boolean Update()
    {
        //For now, just go to the menu
        Globals.currentState = Globals.State.MainMenu;
        
        return false;
    }

}
