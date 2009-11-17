
package payroll;
import java.util.*;
import java.io.Serializable;

/**
 *  This class holds all the commission information
 *  @author bross
 */
public class CommissionSheet implements Serializable
{
    //Holds only today's date
    private String date = Globals.getDateTime(true);
    //List of todays commission records
    public List<CommissionRecord> commissionRecords = new ArrayList<CommissionRecord>();

    //Gets and Sets
    public String getDate()
    {
        return date;
    }
    public void setDate(String value)
    {
        date = value;
    }
    public List<CommissionRecord> getCommissionRecords()
    {
        return commissionRecords;
    }
    public void setCommissionRecords(List<CommissionRecord> value)
    {
        commissionRecords = value;
    }
    
    /**
     * Default Constructor
     */
    public CommissionSheet()
    {
        date = date.replace("/", "");
    }

}
