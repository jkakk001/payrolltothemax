
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
     *  Loads the commission sheet for a specific date
     */
    public void LoadTimeSheet(String tDate)
    {
        //Get the directory and file names
        tDate = tDate.replaceAll("/", "");
        String directory = "Database\\" + Globals.currentEmployee.getEmployeeID() + "\\";
        String filename = Integer.parseInt(tDate) + ".xml";
        File file = new File(directory + filename);

        //Check to see if a time sheet exists for today
        if (file.exists())
            commissionSheet = (CommissionSheet) Serialize.LoadFromXML(directory, filename);
        else
            commissionSheet = new CommissionSheet();
    }

    /**
     * Saves the commission sheet to a file.
     */
    private void SaveCommissionSheet(String sDate)
    {
        //Set up directory and file name
        sDate = sDate.replaceAll("/", "");
        String directory = "Database\\" + Globals.currentEmployee.getEmployeeID() + "\\";
        String filename = Integer.parseInt(sDate) + ".xml";
        System.out.println("Date: " + sDate);

        //Save to XML
        Serialize.SaveToXML(directory, filename, commissionSheet);
    }


    /**
     * Allows the Admin to create a commission record
     */
    void CreateCommissionRecord()
    {
        int intInput = -1;
        float amount = 0f;
        String strInput;
        
        do
        {
        System.out.println("(1) Todays date.");
        System.out.println("(2) Specify a date.");
        System.out.println("(99) Back");
        intInput = GetInt();
        if (intInput != 1 && intInput != 2 && intInput != 99)
            {
                System.out.println("Invalid input.  Please enter a valid choice.");
            }
        } while (intInput != 1 && intInput != 2 && intInput != 99);

        //Set up directory and file name using the date
        if (intInput == 1)
        {
            date = Globals.getDateTime(true);
            date = date.replaceAll("/", "");
        }
        if (intInput == 2)
        {
            do
            {
                //Get the year
                do
                {
                    System.out.println("Which year?");
                    intInput = GetInt();
                    if (intInput <= 1900 || intInput > 3000)
                        {
                            System.out.println("Invalid input.  Please enter a valid year.");
                        }
                } while (intInput <= 1900 || intInput > 3000);
                date = Integer.toString(intInput);

                //Get the month
                do
                {
                    System.out.println("Which month? (1-12)");
                    intInput = GetInt();
                    if (intInput <= 0 || intInput > 12)
                        {
                            System.out.println("Invalid input.  Please enter a valid month.");
                        }
                } while (intInput <= 0 || intInput > 12);
                if (intInput < 10)
                    date += "0";
                date  += Integer.toString(intInput);

                //Get the day
                do
                {
                    System.out.println("Which day?");
                    intInput = GetInt();
                    if (intInput <= 0 || intInput > 31)
                        {
                            System.out.println("Invalid input.  Please enter a valid day.");
                        }
                } while (intInput <= 0 || intInput > 31);
                if (intInput < 10)
                    date += "0";
                date += Integer.toString(intInput);

                System.out.println("\nEntered date: " + date.substring(4, 6) + "/" + date.substring(6) + "/" + date.substring(0, 4));
                System.out.println("Is this correct?");
                strInput = GetString();
            } while (!strInput.equals("y") && !strInput.equals("Y"));
        }

        if (intInput != 99)
        {
            //Get the employee ID and set the directory and filename.
            String directory = "Database\\" + Globals.currentEmployee.getEmployeeID() + "\\";
            String filename = Integer.parseInt(date) + ".xml";

            System.out.println("Sale amount: ");
            amount = GetFloat();

            do
            {
                System.out.println("\nDate: " + date.substring(4, 6) + "/" + date.substring(6) + "/" + date.substring(0, 4));
                System.out.println("Sale amount: " + amount);
                System.out.println("Is this correct? (y/n)");
                strInput = GetString();
                if (!strInput.equals("y") && !strInput.equals("Y")
                        && !strInput.equals("n") && !strInput.equals("N"))
                    System.out.println("Invalid choice.");
            } while (!strInput.equals("y") && !strInput.equals("Y")
                        && !strInput.equals("n") && !strInput.equals("N"));

            //Save to XML
            if (strInput.equals("y") || strInput.equals("Y"))
            {
                String formattedDate = date.substring(4, 6) + "/" + date.substring(6) + "/" + date.substring(0, 4) + " 00:00:00";
                LoadTimeSheet(date);
                commissionSheet.commissionRecords.add(new CommissionRecord(Globals.getDateTime(false), formattedDate, amount, Globals.currentEmployee.getRate()));
                SaveCommissionSheet(date);
            }
        }
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

    /**
     * Loads the employees pay period information
     */
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

        System.out.println("Total Gross Amount: " + totalGrossAmt);
        System.out.println("Total Net Amount: " + totalNetAmt);

        //Wait for user input
        System.out.print("Press enter to continue...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

    /**
     * Gets user input and returns it
     * @return an integer
     */
    int GetInt()
    {
        Scanner input/* = new Scanner(System.in)*/;
        int inInt = -99999;

        do 
        {
            input = new Scanner(System.in);
            System.out.print("Choice: ");
            if (input.hasNextInt())
                inInt = input.nextInt();
            else
                System.out.println("Please enter a valid number.");
        } while (inInt == -99999);

        return inInt;
    }

    /**
     * Gets user input and returns it
     * @return a float
     */
    float GetFloat()
    {
        Scanner input/* = new Scanner(System.in)*/;
        float inFlt = -99999.99f;

        do
        {
            input = new Scanner(System.in);
            System.out.print("Choice: ");
            if (input.hasNextFloat())
                inFlt = input.nextFloat();
            else
                System.out.println("Please enter a valid number.");
        } while (inFlt == -99999.99f);

        return inFlt;
    }

    /**
     * Gets user input and returns it
     * @return a string
     */
    String GetString()
    {
        Scanner input = new Scanner(System.in);
        String inStr = "";

        System.out.print("Choice: ");
        inStr = input.nextLine();

        return inStr;
    }
    

}
