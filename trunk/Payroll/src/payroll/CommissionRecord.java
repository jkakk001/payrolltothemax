
package payroll;
import java.io.Serializable;

/**
 *  For storing any commission records
 *  @author bross
 */
public class CommissionRecord implements Serializable
{
    private String timeEntered;  //Holds the time that it was entered.
    private String date;         //Holds the date of the sale
    private float amount;        //The dollar amount of the sale.
    private float rate;          //The commission rate at the time of sale.

    //Gets and Sets
    public String getTimeEntered()
    {
        return timeEntered;
    }
    public void setTimeEntered(String value)
    {
        timeEntered = value;
    }
    public String getDate()
    {
        return date;
    }
    public void setDate(String value)
    {
        date = value;
    }
    public float getAmount()
    {
        return amount;
    }
    public void setAmount(float value)
    {
        amount = value;
    }
    public float getRate()
    {
        return rate;
    }
    public void setRate(float value)
    {
        rate = value;
    }

    /**
     * Default Constructor
     */
    public CommissionRecord()
    {
        timeEntered = "";
        date = "";
        amount = 0;
        rate = .06f;
    }

    /**
     * Overloaded Constructor (pass in: time entered, date, amount, rate)
     */
    public CommissionRecord(String tE, String d, float amt, float rt)
    {
        timeEntered = tE;
        date = d;
        amount = amt;
        rate = rt;
    }

    /**
     * Prints out the commission info
     * @return  A string of all the commission record's info.
     */
    @Override
    public String toString()
    {
        return date.substring(0, 10) + " - Sale Amount: " + amount + " (" + (amount*.06) + ")"
                    + ",entered on " + timeEntered;
    }

}
