
package Modules;
import payroll.*;
import java.util.Scanner;
import java.util.List;
import java.io.*;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This module handles the viewing of employee hours and clocking in/out etc.
 * @author Blake
 */
public class TimeClock
{
    //Time card to open
    TimeSheet timeSheet;
    //List of time sheets for the current pay period
    List<TimeSheet> payPeriod;
    //Todays date
    String date;

    /**
     * Loops through the timeclock menu until it returns false
     * @return      false to end the loop
     */
    public boolean Update()
    {
        //Menu selection variables
        int menuChoice = 1;
        Scanner in = new Scanner(System.in);

        System.out.println("\n" + Globals.getDateTime(false) + "\n");
        //Display the time sheet for today
        for (TimePunch tP : timeSheet.timePunches)
        {
            if (tP.getType() == 1)
                System.out.print("In : ");
            else if (tP.getType() == 2)
                System.out.print("Out: ");
            System.out.println(tP.getTime());
        }
        if (timeSheet.timePunches.size() == 0)
        {
            System.out.println("*There are no records for today*");
        }
        System.out.println("");
        System.out.println("1 - Clock in");
        System.out.println("2 - Clock out");
        System.out.println("3 - View Pay Period");
        System.out.println("9 - Previous Menu");
        System.out.print("Choice: ");
        if (in.hasNextInt())
            menuChoice = in.nextInt();
        System.out.println("");

        switch(menuChoice)
        {
            //Clock In
            case 1:
                ClockIn();
                break;
            //Clock Out
            case 2:
                ClockOut();
                break;
            //View Pay Period
            case 3:
                ViewPayPeriod();
                break;
            //Previous Menu
            case 9:
                Globals.currentState = Globals.State.MainMenu;
                return false;
            default:
                System.out.println("Invalid choice.");
                break;
        }
        
        return true;
    }

    /**
     *  Loads the time sheet for today
     */
    public void LoadTimeSheet()
    {
        //Get the directory and file names
        date = Globals.getDateTime(true);
        date = date.replaceAll("/", "");
        String directory = "Database\\" + Globals.currentUser.getEmployeeID() + "\\";
        String filename = Integer.parseInt(date) + ".xml";
        File file = new File(directory + filename);

        //Check to see if a time sheet exists for today
        if (file.exists())
            timeSheet = (TimeSheet) Serialize.LoadFromXML(directory, filename);
        else
            timeSheet = new TimeSheet();
    }

    /**
     * Clocks the user in
     */
    private void ClockIn()
    {
        //Check to make sure you haven't already clocked in
        if (timeSheet.timePunches.get(timeSheet.timePunches.size()-1).getType() != 1)
        {
            timeSheet.timePunches.add(new TimePunch(1));
            SaveTimeSheet();
        }
        else
            System.out.println("*You already clocked in.*");
    }

    /**
     * Clocks the user out
     */
    private void ClockOut()
    {
        //Check to make sure you haven't already clocked out
        if (timeSheet.timePunches.get(timeSheet.timePunches.size()-1).getType() != 2)
        {
            timeSheet.timePunches.add(new TimePunch(2));
            SaveTimeSheet();
        }
        else
            System.out.println("*You already clocked out.*");
    }

    /**
     * Saves the time sheet to a file.
     */
    private void SaveTimeSheet()
    {
        date = Globals.getDateTime(true);
        date = date.replaceAll("/", "");
        String directory = "Database\\" + Globals.currentUser.getEmployeeID() + "\\";
        String filename = Integer.parseInt(date) + ".xml";
        System.out.println("Date: " + date);

        Serialize.SaveToXML(directory, filename, timeSheet);
    }

    /**
     * Shows the hours worked/commission earned in the last pay period
     */
    private void ViewPayPeriod()
    {
        //TODO
        String directory = "Database\\" + Globals.currentUser.getEmployeeID() + "\\";
        File file = new File(directory);
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date today = new Date();
        Date firstPayPeriodDay;
        
        //TODO - USE CALENDAR INSTEAD OF DATE - DATE IS DEPRECATED
        Calendar firstPayPeriodDay1;

        //Check to see which pay period it is in
        if (today.after(new Date(today.getYear(), today.getMonth(), 1)) 
                && today.before(new Date(today.getYear(), today.getMonth(), 15)))
            firstPayPeriodDay = new Date(today.getYear(), today.getMonth(), 1);
        else
            firstPayPeriodDay = new Date(today.getYear(), today.getMonth(), 16);

        //Testing stuff
        System.out.println("Today's date: " + today);
        System.out.println("Pay Period Starting date: " + firstPayPeriodDay);
        System.out.println("Difference between today and first pay period day: "
                            + (today.getDate() - firstPayPeriodDay.getDate()) );

        //System.out.println("Days worked: " + payPeriod.size());
    }
}
