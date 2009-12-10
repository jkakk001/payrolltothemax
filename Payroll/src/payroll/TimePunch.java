
package payroll;
import java.io.Serializable;

/**
 *  This represents a single timecard "punch." In other words, when you clock
 *  in or out.  It holds the time of day and type of punch (in or out)
 * @author bross
 */
public class TimePunch implements Serializable
{
    private String time;    //Holds the time.
    private int type;       //The punch type.  1-In, 2-Out

    //Gets and Sets
    public String getTime()
    {
        return time;
    }
    public void setTime(String value)
    {
        time = value;
    }
    public int getType()
    {
        return type;
    }
    public void setType(int value)
    {
        type = value;
    }

    /**
     * Default Constructor
     */
    public TimePunch()
    {
        type = 0;
        time = "";
    }
    
    /**
     *  Overloaded Constructor
     *  @param punchType     1-In, 2-Out
     */
    public TimePunch(int punchType)
    {
        time = Globals.getDateTime(false);
        type = punchType;
    }

    /**
     * Prints out the time punch info
     * @return  The type of punch and the associated time
     */
    @Override
    public String toString()
    {
        if (type == 1)
            return "In :  " + time;
        else
            return "Out : " + time;
    }

}
