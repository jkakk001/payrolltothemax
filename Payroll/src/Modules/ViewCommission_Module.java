
package Modules;
import payroll.*;
import java.util.*;
import java.io.*;

/**
 * This module allows the user to view their commission information
 * @author Blake
 */
public class ViewCommission_Module
{
    //Commission sheet to open
    CommissionSheet commissionSheet;
    //List of commission sheets for the current pay period
    List<CommissionSheet> payPeriod = new ArrayList<CommissionSheet>();
    //Todays date
    String date;

    /**
     * The view commission main loop
     * @return  false to stop looping
     */
    public boolean Update()
    {
        ViewPayPeriod();
        Globals.currentState = Globals.State.MainMenu;
        return false;
    }

    /**
     * Loads the user's pay period information
     */
    private void LoadPayPeriod()
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
        float totalGrossAmt = 0;
        float totalNetAmt = 0;

        System.out.println("Records: " + payPeriod.size());

        //Print commission sheet information for each day
        if (payPeriod.size() > 0)
        {
            for (CommissionSheet c: payPeriod)
                {
                    for (CommissionRecord cR: c.commissionRecords)
                    {
                        System.out.println(cR.toString());
                        totalGrossAmt += cR.getAmount();
                        totalNetAmt += (cR.getAmount() * cR.getRate());
                    }
                }
        }

        System.out.printf( "Total Gross Amount: $%1.2f\n", totalGrossAmt);
        System.out.printf( "Total Net Amount:   $%1.2f\n", totalNetAmt);

        //Wait for user input
        System.out.print("Press enter to continue...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

}
