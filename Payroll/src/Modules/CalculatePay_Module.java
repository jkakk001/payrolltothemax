
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
    float grossPay;
    float netPay;

    /**
     * The Calculate Pay main loop
     * @return  false to stop looping
     */
    public boolean Update()
    {
        System.out.println(" " + Globals.getDateTime(false) + "\n");
        System.out.println("**CALCULATE PAY MENU**\n");

        //Placeholder code for testing
        System.out.println("ID: " + Globals.currentUser.getEmployeeID());
        PrintPayChecks(Globals.currentUser.getEmployeeID());
        System.out.printf("Gross pay: $%1.2f\n", grossPay);
        System.out.printf("Net pay:   $%1.2f\n", netPay);

        System.out.println("ID: 10006");
        PrintPayChecks(10006);
        System.out.printf("Gross pay: $%1.2f\n", grossPay);
        System.out.printf("Net pay:   $%1.2f\n", netPay);

        //Change the state back to the admin menu, stop looping.
        Globals.currentState = Globals.State.AdminMenu;
        return false;
    }

    /**
     * Prints the menu choices
     */
    void PrintMenuChoices()
    {
        System.out.println("(1)  - Print Specific Paycheck");
        System.out.println("(2)  - Print All Paychecks");
        System.out.println("(3)  - Display Payroll Report");
        System.out.println("(99) - Back to Admin Menu");
    }

    /**
     * "Prints" the pay checks for distribution
     */
    void PrintPayChecks()
    {
        
    }

    /**
     * "Prints" a specific pay check for distribution
     */
    void PrintPayChecks(int empID)
    {
        for (Employee e: Globals.Employees)
            if (e.getEmployeeID() == empID)  CalculatePay(e);
    }

    void CalculatePay(Employee emp)
    {
        grossPay = 0;
        netPay = 0;

        if (emp instanceof Employee_Commission)
        {
            List<CommissionSheet> payPeriod = LoadCommissionPayPeriod(emp.getEmployeeID());

            //Go through the pay period and calculate the sales earnings
            if (payPeriod.size() > 0)
            {
                for (CommissionSheet c: payPeriod)
                    {
                        for (CommissionRecord cR: c.commissionRecords)
                        {
                            grossPay += cR.getAmount() * cR.getRate();
                        }
                    }
            }
        }
        else if (emp instanceof Employee_Hourly)
        {
            List<TimeSheet> payPeriod = LoadHourlyPayPeriod(emp.getEmployeeID());

            //Go through the pay period and calculate the sales earnings
            if (payPeriod.size() > 0)
            {
                float totalHours = 0;
                for (TimeSheet t: payPeriod)
                    {
                        String inTime = "";
                        String outTime = "";
                        float difference = 0;
                        for (TimePunch tR: t.getTimePunches())
                        {
                            if (tR.getType() == 1)
                                inTime = tR.getTime().substring(11);
                            else if (tR.getType() == 2)
                            {
                                outTime = tR.getTime().substring(11);

                                difference = Integer.parseInt(outTime.substring(0,2)) - Integer.parseInt(inTime.substring(0,2));
                                difference += (Integer.parseInt(outTime.substring(3,5)) - Integer.parseInt(inTime.substring(3,5))) / 60f;
                                difference += (Integer.parseInt(outTime.substring(6)) - Integer.parseInt(inTime.substring(6))) / 3600f;

                                totalHours += difference;

                                //Debugging stuff
                                /*
                                System.out.println("In Time: " + inTime);
                                System.out.println("Out Time: " + outTime);
                                float temp = Integer.parseInt(outTime.substring(0,2)) - Integer.parseInt(inTime.substring(0,2));
                                System.out.println("Hours: " + temp);
                                temp = (Integer.parseInt(outTime.substring(3,5)) - Integer.parseInt(inTime.substring(3,5))) / 60f;
                                System.out.println("Minutes: " + temp);
                                temp = (Integer.parseInt(outTime.substring(6)) - Integer.parseInt(inTime.substring(6))) / 3600f;
                                System.out.println("Seconds: " + temp);
                                System.out.println("Total Difference: " + difference);
                                */
                            }
                        }
                    }

                grossPay = totalHours * emp.getRate();
            }
        }
        else if (emp instanceof Employee_Salary)
        {
            grossPay = emp.getRate();
        }

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

        //The actual calendar day of the first pay period day
        GregorianCalendar firstPayPeriodDate = new GregorianCalendar();

        //Get the previous pay period
        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) >= 1
                && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) <= 15)
            firstPayPeriodDay = 16;
        else
            firstPayPeriodDay = 1;

        if (Calendar.MONTH == 0  && firstPayPeriodDay == 16)
        {
            yearToUse = Calendar.getInstance().get(Calendar.YEAR) - 1;
            monthToUse = 11;
            lastDay = 31;
        }
        else if (firstPayPeriodDay == 16)
        {
            yearToUse = Calendar.getInstance().get(Calendar.YEAR);
            monthToUse = Calendar.getInstance().get(Calendar.MONTH)-1;
            lastDay = 31;
        }
        else
        {
            yearToUse = Calendar.getInstance().get(Calendar.YEAR);
            monthToUse = Calendar.getInstance().get(Calendar.MONTH);
            lastDay = 15;
        }

        //Set the firstPayPeriodDate object to the appropriate date
        firstPayPeriodDate.set(yearToUse, monthToUse, firstPayPeriodDay);

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

                System.out.println("Info: " + c.getDate());
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

        //The actual calendar day of the first pay period day
        GregorianCalendar firstPayPeriodDate = new GregorianCalendar();

        //Get the previous pay period
        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) >= 1
                && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) <= 15)
            firstPayPeriodDay = 16;
        else
            firstPayPeriodDay = 1;

        if (Calendar.MONTH == 0  && firstPayPeriodDay == 16)
        {
            yearToUse = Calendar.getInstance().get(Calendar.YEAR) - 1;
            monthToUse = 11;
            lastDay = 31;
        }
        else if (firstPayPeriodDay == 16)
        {
            yearToUse = Calendar.getInstance().get(Calendar.YEAR);
            monthToUse = Calendar.getInstance().get(Calendar.MONTH)-1;
            lastDay = 31;
        }
        else
        {
            yearToUse = Calendar.getInstance().get(Calendar.YEAR);
            monthToUse = Calendar.getInstance().get(Calendar.MONTH);
            lastDay = 15;
        }

        //Set the firstPayPeriodDate object to the appropriate date
        firstPayPeriodDate.set(yearToUse, monthToUse, firstPayPeriodDay);

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

                System.out.println("Info: " + t.getDate());
                payPeriod.add( t );
                //System.out.println("Found at Path: " + directory + datesToGrab[i] + ".xml");
            }
        }

        return payPeriod;
    }

}
