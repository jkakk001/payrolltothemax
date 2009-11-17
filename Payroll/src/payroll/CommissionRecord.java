
package payroll;
import java.io.Serializable;

/**
 *  For storing any commission records
 *  @author bross
 */
public class CommissionRecord implements Serializable
{
    //Holds the time that it was entered.
    private String timeEntered;
    //Holds the date of the sale
    private String date;
    //The dollar amount of the sale.
    private float amount;

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


    /**
     * Default Constructor
     */
    public CommissionRecord()
    {
        timeEntered = "";
        date = "";
        amount = 0;
    }

    /**
     * Prints out the commission info
     * @return  A string of all the commission record's info.
     */
    public String toString()
    {
        return date + " - Sale Amount: " + amount + "(" + (amount*.06) + ")"
                    + ",entered on " + timeEntered;
    }

}
