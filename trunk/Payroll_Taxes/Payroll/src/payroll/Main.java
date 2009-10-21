/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package payroll;
/**
 *  This class loops the mainProgram's Update() method
 *  @author bross
 */
public class Main {

    public static MainProgram mainProgram = new MainProgram();

    /**
     *  Loops mainProgram.Update() until ExitProgram is false
     *  @param args the command line arguments
     */
    public static void main(String[] args) {

        //Call the main program loop
        while (Globals.ExitProgram == false)
        {
            mainProgram.Update();
        }

        //Thank you!
        System.out.println("Thank you for using Payroll to the Max!!");
    }



}
