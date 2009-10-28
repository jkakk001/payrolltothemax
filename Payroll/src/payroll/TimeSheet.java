
package payroll;
import java.util.*;
import java.io.Serializable;

/**
 *  This class holds all the shift/commission information
 *  @author bross
 */
public class TimeSheet implements Serializable
{
    //Holds only today's date
    private String date = Globals.getDateTime(true);
    //List of todays "punches"
    public List<TimePunch> timePunches = new ArrayList<TimePunch>();

    private boolean isNull;

    //Gets and Sets
    public String getDate()
    {
        return date;
    }
    public void setDate(String value)
    {
        date = value;
    }
    public List<TimePunch> getTimePunches()
    {
        return timePunches;
    }
    public void setTimePunches(List<TimePunch> value)
    {
        timePunches = value;
    }
    
    /**
     * Default Constructor
     */
    public TimeSheet()
    {
        date = date.replace("/", "");
        isNull = true;
    }

}
