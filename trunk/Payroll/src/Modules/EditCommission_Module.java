
package Modules;
import payroll.*;
import java.util.*;
import java.io.*;

/**
 * This module allows administrators to edit the commission data of an employee.
 * @author Blake
 */
public class EditCommission_Module
{
    //Commission sheet to open
    CommissionSheet commissionSheet;
    //List of commission sheets for the current pay period
    List<CommissionSheet> payPeriod = new ArrayList<CommissionSheet>();
    //Todays date
    String date;

    /**
     * The Edit Commission main loop
     * @return  false to stop looping
     */
    public boolean Update()
    {
        LoadTimeSheet();

        //Menu selection variables
        int menuChoice = 1;
        Scanner in = new Scanner(System.in);

        System.out.println(Globals.getDateTime(false) + "\n");

        System.out.println("**COMMISSION RECORDS**\n");

        //Display the time sheet for today
        if (commissionSheet.commissionRecords.size() == 0)
            System.out.println("*There are no records for today*");
        else
        {
            System.out.println("Today's Time Sheet: ");
            for (CommissionRecord cR : commissionSheet.commissionRecords)
                cR.toString();
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
            //Create a Commission Record
            case 1:
                CreateCommissionRecord();
                break;
            //Delete a Commission Record
            case 2:
                DeleteCommissionRecord();
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
        System.out.println("(1)  - Create Commission Record");
        System.out.println("(2)  - Delete Commission Record");
        System.out.println("(3)  - View Pay Period Information");
        System.out.println("(99) - Back to Main Menu");
    }

    /**
     *  Loads the commission sheet for today
     */
    public void LoadTimeSheet()
    {
        //Get the directory and file names
        date = Globals.getDateTime(true);
        date = date.replaceAll("/", "");
        String directory = "Database\\" + Globals.currentEmployee.getEmployeeID() + "\\";
        String filename = Integer.parseInt(date) + ".xml";
        File file = new File(directory + filename);

        //Check to see if a time sheet exists for today
        if (file.exists())
            commissionSheet = (CommissionSheet) Serialize.LoadFromXML(directory, filename);
        else
            commissionSheet = new CommissionSheet();
    }

    /**
     * Allows the Admin to create a commission record
     */
    void CreateCommissionRecord()
    {
        
    }

    /**
     * Allows the Admin to delete a commission record
     */
    void DeleteCommissionRecord()
    {
        LoadPayPeriod();
        if (payPeriod.size() > 0)
        {
            for (CommissionSheet c: payPeriod)
                {
                    //Print out the choice number
                    System.out.println("(" + payPeriod.indexOf(c) + ")");
                    for (CommissionRecord cR: c.commissionRecords)
                        System.out.println(cR.toString());
                }
        }
    }

    private void LoadPayPeriod()
    {
        //Set up the directory
        String directory = "Database\\" + Globals.currentEmployee.getEmployeeID() + "\\";

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
        System.out.print("Pay Period Starting date: " + firstPayPeriodDate.get(Calendar.YEAR) + "/");
        if ( (firstPayPeriodDate.get(Calendar.MONTH)+1) < 10)
            System.out.print("0");
        System.out.print( (firstPayPeriodDate.get(Calendar.MONTH)+1) + "/");
        if ( (firstPayPeriodDate.get(Calendar.DATE)) < 10)
            System.out.print("0");
        System.out.println(firstPayPeriodDate.get(Calendar.DATE));

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

            //If the given file exists in the database, aka if there is a commission sheet for that day
            if (fullpath.exists())
            {
                //Load it from XML
                CommissionSheet c = (CommissionSheet) Serialize.LoadFromXML(directory, datesToGrab[i] + ".xml");
                //Add it to the payPeriod list
                payPeriod.add( c );
            }
        }
    }

    /**
     * Shows the hours worked/commission earned in the last pay period
     */
    private void ViewPayPeriod()
    {
        LoadPayPeriod();

        System.out.println("Days worked: " + payPeriod.size());

        //Print commission sheet information for each day
        if (payPeriod.size() > 0)
        {
            for (CommissionSheet c: payPeriod)
                {
                    for (CommissionRecord cR: c.commissionRecords)
                        System.out.println(cR.toString());
                }
        }

        //Wait for user input
        System.out.print("Press enter to continue...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }



}
