/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;
import payroll.*;

/**
 * This module is used for editing employee records
 * @author Blake
 */
public class EditEmployee
{

    public boolean Update()
    {
        System.out.println("Edit employee menu.");
        Globals.currentState = Globals.State.AdminMenu;
        return false;
    }

}
