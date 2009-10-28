
package Modules;
import payroll.*;
import java.util.*;
import java.io.*;

/**
 * This module handles the viewing of employee hours and clocking in/out etc.
 * @author Blake
 */
public class TimeClock
{
    //Time card to open
    TimeSheet timeSheet;
    //List of time sheets for the current pay period
    List<TimeSheet> payPeriod = new ArrayList<TimeSheet>();
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

        System.out.println("\nCurrent Time: " + Globals.getDateTime(false) + "\n");

        System.out.println("**TIME CLOCK**\n");
        
        //Display the time sheet for today
        if (timeSheet.timePunches.size() == 0)
            System.out.println("*There are no records for today*");
        else
        {
            System.out.println("Today's Time Sheet: ");
            for (TimePunch tP : timeSheet.timePunches)
                tP.printInfo();
        }

        //Print the menu
        PrintMenuChoices();
        System.out.print("Choice: ");
        //Make sure input is an int to prevent exceptions
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
            case 99:
                Globals.currentState = Globals.State.MainMenu;
                return false;
            default:
                System.out.println("Invalid choice.");
                break;
        }
        
        return true;
    }

    /**
     * Prints the menu choices
     */
    void PrintMenuChoices()
    {
        System.out.println("");
        System.out.println("1 - Clock in");
        System.out.println("2 - Clock out");
        System.out.println("3 - View Pay Period");
        System.out.println("99 - Previous Menu");
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

        if (timeSheet.timePunches.size() == 0 || timeSheet.timePunches.get(timeSheet.timePunches.size()-1).getType() != 1)
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
        if (timeSheet.timePunches.size() == 0 || timeSheet.timePunches.get(timeSheet.timePunches.size()-1).getType() != 2)
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
        //Set up directory and file name
        date = Globals.getDateTime(true);
        date = date.replaceAll("/", "");
        String directory = "Database\\" + Globals.currentUser.getEmployeeID() + "\\";
        String filename = Integer.parseInt(date) + ".xml";
        System.out.println("Date: " + date);

        //Save to XML
        Serialize.SaveToXML(directory, filename, timeSheet);
    }

    /**
     * Shows the hours worked/commission earned in the last pay period
     */
    private void ViewPayPeriod()
    {
        //Set up the directory
        String directory = "Database\\" + Globals.currentUser.getEmployeeID() + "\\";

        //Used to figure out which day of the month is the first pay period day.
        int firstPayPeriodDay;
        //The actual calendar day of the first pay period day
        GregorianCalendar firstPayPeriodDate = new GregorianCalendar();

        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) > 1
                && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) <= 15)
            firstPayPeriodDay = 1;
        else
            firstPayPeriodDay = 16;

        //Set the firstPayPeriodDate object to the appropriate date
        firstPayPeriodDate.set(Calendar.getInstance().get(Calendar.YEAR),
                               Calendar.getInstance().get(Calendar.MONTH),
                               firstPayPeriodDay);

        //Testing stuff
        System.out.println("Today's date: " + Globals.getDateTime(true));
        System.out.println("Pay Period Starting date: " + firstPayPeriodDate.get(Calendar.YEAR)
                            + "/" + (firstPayPeriodDate.get(Calendar.MONTH)+1)
                            + "/" + firstPayPeriodDate.get(Calendar.DATE));
        //System.out.println("Difference between today and first pay period day: "
        //                  + (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - firstPayPeriodDay) );

        //An array that will hold all possible days in the pay period
        String[] datesToGrab = new String[(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - firstPayPeriodDay)+1];

        //Sets up the list of days in the pay period and formats the numbers accordingly
        for (int i = 0; i < datesToGrab.length; i++)
        {
            //If the month and day are less than 10 (so we can add a 0 in the front)
            if (Calendar.getInstance().get(Calendar.MONTH) + 1 < 10
                && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) < 10)
                datesToGrab[i] = Integer.toString(Calendar.getInstance().get(Calendar.YEAR))
                                + "0" + Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1)
                                + "0" + Integer.toString((Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - i));
            //If just the month is less than 10 (so we can add a 0 in the front)
            else if (Calendar.getInstance().get(Calendar.MONTH) + 1 < 10)
                datesToGrab[i] = Integer.toString(Calendar.getInstance().get(Calendar.YEAR))
                                + "0" + Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1)
                                + Integer.toString((Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - i));
            //If just the day is less than 10 (so we can add a 0 in the front)
            else if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) < 10)
                datesToGrab[i] = Integer.toString(Calendar.getInstance().get(Calendar.YEAR))
                                + Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1)
                                + "0" + Integer.toString((Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - i));
            //If the month and day are greater than 10
            else
                datesToGrab[i] = Integer.toString(Calendar.getInstance().get(Calendar.YEAR))
                                + Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1)
                                + Integer.toString((Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - i));
            ////Print out for testing purposes
            //System.out.println(datesToGrab[i]);
        }

        //Loop through the list of dates
        for (int i = datesToGrab.length - 1; i >= 0 ; i--)
        {
            File fullpath = new File(directory + datesToGrab[i] + ".xml");

            //If the given file exists in the database, aka if there is a time sheet for that day
            if (fullpath.exists())
            {
                //Load it from XML
                TimeSheet t = (TimeSheet) Serialize.LoadFromXML(directory, datesToGrab[i] + ".xml");
                //Add it to the payPeriod list
                payPeriod.add( t );
                //System.out.println("Found at Path: " + directory + datesToGrab[i] + ".xml");
            }
        }

        System.out.println("Days worked: " + payPeriod.size());

        //Print time sheet information for each day
        for (TimeSheet t: payPeriod)
        {
            for (TimePunch tP: t.timePunches)
                tP.printInfo();
        }

        //Wait for user input
        System.out.println("Press enter to continue...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }
}
