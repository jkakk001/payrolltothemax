
package Modules;
import payroll.*;
import java.util.*;
import java.io.*;

/**
 * This module allows administrators to calculate employee pay and "print" paychecks.
 * @author Blake
 */
public class CalculatePay_Module
{
    float grossPay;     //The temporary gross pay
    float netPay;       //The temporary net pay

    /**
     * The Calculate Pay main loop
     * @return  false to stop looping
     */
    public boolean Update()
    {
        int menuSelection = 1;
        
        System.out.println(" " + Globals.getDateTime(false) + "\n");
        System.out.println("**CALCULATE PAY MENU**\n");

        PrintMenuChoices();

        //Make sure the input is an integer to prevent exceptions
        menuSelection = GetInt();
        System.out.println("");

        switch(menuSelection)
        {
            //Print Specific Paycheck
            case 1:
                System.out.println("Employee ID: ");
                if (PreparePayChecks(GetInt(), true))
                    Print();
                return false;
            //Print All Paychecks
            case 2:
                PreparePayChecks();
                Print();
                return false;
            //Previous menu
            case 99:
                Globals.currentState = Globals.State.AdminMenu;
                return false;
            default:
                System.out.println("Invalid choice.");
                break;
        }

        //Keep looping
        return true;
    }

    /**
     * Prints the menu choices
     */
    void PrintMenuChoices()
    {
        System.out.println("(1)  - Print Specific Paycheck");
        System.out.println("(2)  - Print All Paychecks");
        System.out.println("(99) - Back to Admin Menu");
    }

    /**
     * "Prints" the pay checks for distribution
     */
    void PreparePayChecks()
    {
        int count = 0;  //Employee count

        //Company totals
        float totalGross = 0;
        float totalNet = 0;
        
        for (Employee emp: Globals.Employees)
        {
            PreparePayChecks(emp.getEmployeeID(), false);

            System.out.println(emp.getEmployeeID() + " - " + emp.getLastName() + ", " + emp.getFirstName());

            //Add to company totals
            totalGross += grossPay;
            totalNet += netPay;
            count++;
        }

        System.out.println(count + " employees.");
        System.out.printf("Company-wide gross pay: $%1.2f\n", totalGross);
        System.out.printf("Company-wide net pay:   $%1.2f\n", totalNet);
    }

    /**
     * "Prints" a specific pay check for distribution
     */
    boolean PreparePayChecks(int empID, boolean details)
    {
        File f = new File("Database\\" + Integer.toString(empID) + "\\");

        //If the employee folder exists
        if (f.exists())
        {
            //Calculate the pay
            for (Employee e: Globals.Employees)
                if (e.getEmployeeID() == empID)  CalculatePay(e, details);

            return true;
        }
        else    //Employee folder does not exist
        {
            System.out.println("Cannot find employee. " 
                              + "Make sure you entered the ID correctly");
            return false;
        }
    }

    void CalculatePay(Employee emp, boolean details)
    {
        grossPay = 0;
        netPay = 0;

        //If the employee is commission based
        if (emp instanceof Employee_Commission)
        {
            List<CommissionSheet> payPeriod = LoadCommissionPayPeriod(emp.getEmployeeID());

            //Go through the pay period and calculate the sales earnings
            if (payPeriod.size() > 0)
                for (CommissionSheet c: payPeriod)
                        for (CommissionRecord cR: c.commissionRecords)
                            grossPay += cR.getAmount() * cR.getRate();
                        
        }
        //If the employee is hourly
        else if (emp instanceof Employee_Hourly)
        {
            List<TimeSheet> payPeriod = LoadHourlyPayPeriod(emp.getEmployeeID());

            //Go through the pay period and calculate the sales earnings
            if (payPeriod.size() > 0)
            {
                float totalHours = 0;
                for (TimeSheet t: payPeriod)
                    {
                        String inTime = "";     //Time clocked in
                        String outTime = "";    //Time clocked out
                        float difference = 0;   //Used to get the hours worked
                        
                        for (TimePunch tR: t.getTimePunches())
                        {
                            //Clocked in
                            if (tR.getType() == 1)
                                inTime = tR.getTime().substring(11);
                            //Clocked out
                            else if (tR.getType() == 2)
                            {
                                outTime = tR.getTime().substring(11);

                                //Calculate the difference of hours, minutes, and seconds
                                difference = Integer.parseInt(outTime.substring(0,2)) - Integer.parseInt(inTime.substring(0,2));
                                difference += (Integer.parseInt(outTime.substring(3,5)) - Integer.parseInt(inTime.substring(3,5))) / 60f;
                                difference += (Integer.parseInt(outTime.substring(6)) - Integer.parseInt(inTime.substring(6))) / 3600f;

                                totalHours += difference;
                            }
                        }
                    }

                grossPay = totalHours * emp.getRate();
            }
        }
        //If the employee is salary based
        else if (emp instanceof Employee_Salary)
            grossPay = emp.getRate();

        netPay = grossPay;

        //Pre tax stuff
        netPay -= grossPay * Globals.federalTaxRate;
        netPay -= (grossPay * Globals.federalTaxRate) * Globals.stateTaxRate;
        netPay -= grossPay * Globals.healthInsuranceRate;
        netPay -= grossPay * Globals.lifeInsuranceRate;
        //Post tax stuff
        float originalNet = netPay;
        netPay -= originalNet * Globals.socialSecurityRate;
        netPay -= originalNet * Globals.medicareRate;

        //Show paycheck preview and deduction details.
        if (details)
        {
            System.out.println("Check preview: ");
            System.out.println(" ________________________________________________");
            System.out.println("|COMPANY NAME                               1234 |");
            System.out.println("|                                                |");
            System.out.println("|Pay to the                                      |");
            System.out.print("| order of:_" + emp.getFirstName() + " " + emp.getLastName());
            System.out.printf("_________ $___%1.2f___\n", netPay);
            System.out.println("|________________________________________________|");
            System.out.println("|Bank of America                                 |");
            System.out.println("|    Address                                     |");
            System.out.println("|Memo__________________        _______________   |");
            System.out.println("|________________________________________________|");
            System.out.println("       001731   121000358   19976   02302        \n");

            
            System.out.println("***DEDUCTIONS***");
            System.out.printf("Federal Taxes:    $%1.2f\n", grossPay * Globals.federalTaxRate);
            System.out.printf("State Taxes:      $%1.2f\n", (grossPay * Globals.federalTaxRate) * Globals.stateTaxRate);
            System.out.printf("Health Insurance: $%1.2f\n", grossPay * Globals.healthInsuranceRate);
            System.out.printf("Life Insurance:   $%1.2f\n", grossPay * Globals.lifeInsuranceRate);
            System.out.printf("Social Security:  $%1.2f\n", originalNet * Globals.socialSecurityRate);
            System.out.printf("Medicare:         $%1.2f\n", originalNet * Globals.medicareRate);
            System.out.println("_______________________________");
            System.out.printf("Gross pay: $%1.2f\n", grossPay);
            System.out.printf("Net pay:   $%1.2f\n", netPay);
        }
    }

    /**
     * Loads the employees last pay period information
     * @return the employees pay period
     */
    private List<CommissionSheet> LoadCommissionPayPeriod(int empID)
    {
        //List of commission sheets for the current pay period
        List<CommissionSheet> payPeriod = new ArrayList<CommissionSheet>();
        //Set up the directory
        String directory = "Database\\" + empID + "\\";

        //Used to figure out which day of the month is the first pay period day.
        int firstPayPeriodDay;

        //Date variables used for the last pay period
        int yearToUse;
        int monthToUse;
        int lastDay;

        //Get the previous pay period
        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) >= 1
                && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) <= 15)
            firstPayPeriodDay = 16;
        else
            firstPayPeriodDay = 1;

        //If this month is January, set the date stuff to last month (aka
        //last year, December, the 16th-31st
        if (Calendar.MONTH == 0  && firstPayPeriodDay == 16)
        {
            yearToUse = Calendar.getInstance().get(Calendar.YEAR) - 1;
            monthToUse = 11;
            lastDay = 31;
        }
        //Set the pay period to the 16th through the 31st of last month
        else if (firstPayPeriodDay == 16)
        {
            yearToUse = Calendar.getInstance().get(Calendar.YEAR);
            monthToUse = Calendar.getInstance().get(Calendar.MONTH)-1;
            lastDay = 31;
        }
        //Set the pay period to the 1st through the 15th of this month
        else
        {
            yearToUse = Calendar.getInstance().get(Calendar.YEAR);
            monthToUse = Calendar.getInstance().get(Calendar.MONTH);
            lastDay = 15;
        }

        //An array that will hold all possible days in the pay period
        String[] datesToGrab = new String[(lastDay - firstPayPeriodDay) + 1];

        //Sets up the list of days in the pay period and formats the numbers accordingly
        for (int i = 0; i < datesToGrab.length; i++)
        {
            //If the month and day are less than 10 (so we can add a 0 in the front)
            if (monthToUse + 1 < 10 && lastDay < 10)
                datesToGrab[i] = Integer.toString(yearToUse)
                                + "0" + Integer.toString(monthToUse + 1)
                                + "0" + Integer.toString(lastDay - i);
            //If the month is less than 10 (so we can add a 0 in the front)
            else if (monthToUse + 1 < 10)
                datesToGrab[i] = Integer.toString(yearToUse)
                                + "0" + Integer.toString(monthToUse + 1)
                                + Integer.toString(lastDay - i);
            //If just the day is less than 10 (so we can add a 0 in the front)
            else if (lastDay < 10)
                datesToGrab[i] = Integer.toString(yearToUse)
                                + Integer.toString(monthToUse + 1)
                                + "0" + Integer.toString(lastDay - i);
            //If the month and day are greater than 10
            else
                datesToGrab[i] = Integer.toString(yearToUse)
                                + Integer.toString(monthToUse + 1)
                                + Integer.toString(lastDay - i);
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

        return payPeriod;
    }

    /**
     * Loads the employees hourly pay period information
     * @return the employees pay period
     */
    private List<TimeSheet> LoadHourlyPayPeriod(int empID)
    {
        //List of time sheets for the current pay period
        List<TimeSheet> payPeriod = new ArrayList<TimeSheet>();
        //Set up the directory
        String directory = "Database\\" + empID + "\\";

        //Used to figure out which day of the month is the first pay period day.
        int firstPayPeriodDay;

        //Date variables used for the last pay period
        int yearToUse;
        int monthToUse;
        int lastDay;

        //Get the previous pay period
        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) >= 1
                && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) <= 15)
            firstPayPeriodDay = 16;
        else
            firstPayPeriodDay = 1;

        //If this month is January, set the date stuff to last month (aka
        //last year, December, the 16th-31st
        if (Calendar.MONTH == 0  && firstPayPeriodDay == 16)
        {
            yearToUse = Calendar.getInstance().get(Calendar.YEAR) - 1;
            monthToUse = 11;
            lastDay = 31;
        }
        //Set the pay period to the 16th through the 31st of last month
        else if (firstPayPeriodDay == 16)
        {
            yearToUse = Calendar.getInstance().get(Calendar.YEAR);
            monthToUse = Calendar.getInstance().get(Calendar.MONTH)-1;
            lastDay = 31;
        }
        //Set the pay period to the 1st through the 15th of this month
        else
        {
            yearToUse = Calendar.getInstance().get(Calendar.YEAR);
            monthToUse = Calendar.getInstance().get(Calendar.MONTH);
            lastDay = 15;
        }

        //An array that will hold all possible days in the pay period
        String[] datesToGrab = new String[(lastDay - firstPayPeriodDay) + 1];

        //Sets up the list of days in the pay period and formats the numbers accordingly
        for (int i = 0; i < datesToGrab.length; i++)
        {
            //If the month and day are less than 10 (so we can add a 0 in the front)
            if (monthToUse + 1 < 10 && lastDay < 10)
                datesToGrab[i] = Integer.toString(yearToUse)
                                + "0" + Integer.toString(monthToUse + 1)
                                + "0" + Integer.toString(lastDay - i);
            //If the month is less than 10 (so we can add a 0 in the front)
            else if (monthToUse + 1 < 10)
                datesToGrab[i] = Integer.toString(yearToUse)
                                + "0" + Integer.toString(monthToUse + 1)
                                + Integer.toString(lastDay - i);
            //If just the day is less than 10 (so we can add a 0 in the front)
            else if (lastDay < 10)
                datesToGrab[i] = Integer.toString(yearToUse)
                                + Integer.toString(monthToUse + 1)
                                + "0" + Integer.toString(lastDay - i);
            //If the month and day are greater than 10
            else
                datesToGrab[i] = Integer.toString(yearToUse)
                                + Integer.toString(monthToUse + 1)
                                + Integer.toString(lastDay - i);

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

        return payPeriod;
    }

    /**
     * Gets user input and returns it
     * @return an integer
     */
    int GetInt()
    {
        Scanner input;
        int inInt = -99999;

        //Loop until the user inputs an int
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
     * "Prints" out the paychecks.
     */
    void Print()
    {
        System.out.print("*Sending to printer");

        //Add some progress dots
        for (int i = 0; i<10; i++)
        {
            Sleep(300);
            System.out.print(".");
        }

        System.out.println("*DONE*\n");
        Sleep(1000);
    }

    /**
     * Makes the program sleep for a specified amount of time.
     * @param length amount of time in milliseconds
     */
    void Sleep(int length)
    {
        try
        {
            Thread.sleep(length);
        }
        catch (Exception e)
        {}
    }

}
