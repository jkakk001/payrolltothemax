
package payroll;
import java.io.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;

/**
 * Used for saving and loading XML files and settings.
 * @author bross
 */
public class Serialize {

    /**
     * Default Constructor
     */
    public Serialize()
    {
        
    }

    /**
     * Used for saving any application settings.
     */
    public static void SaveSettings()
    {
        //Set the settings directory
        File dir = new File("Settings");
    }

    /**
     * Loads the employees from the database folder
     * and throws them in Globals.Employees.
     */
    public static void RetrieveEmployees()
    {
        //Sets the database folder
        File dir = new File("Database\\");

        //Clears the list of employees
        Globals.Employees.clear();

        //List of files in the database
        String[] children = dir.list();

        //If there is at least one ID number
        if (children.length > 0)
        {
            //Load the employees into memory
            for (int i = 0; i < children.length; i++)
            {
                //System.out.println("Path:" + children[i]);
                //Make sure it doesn't interfere with .svn files
                if (!children[i].endsWith(".svn"))
                    Globals.Employees.add( (Employee) LoadFromXML("Database\\" + children[i] + "\\", "Employee.xml"));
            }

            System.out.println("Database loaded successfully. (" + (children.length - 1) + " Employees Total.)");
        }
        else
            System.out.println("There are no employees");
    }

    /**
     * Used for saving objects to XML files
     * @param folderName        Path of the directory where it'll be saved
     * @param fileName          Full filename with extension
     * @param object            Object to be saved to XML
     */
    public static void SaveToXML(String folderName, String fileName, Object object)
    {
        //Check to see if the directory exists.  If not, create it.
        if (!new File(folderName).exists()){
            System.out.println("Folder did not exist. Creating one now...");
            new File(folderName).mkdir();
        }

        try
        {
            //Create the output streams
            FileOutputStream fos = new FileOutputStream(folderName + fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            //Create an encoder
            XMLEncoder encoder = new XMLEncoder(bos);

            //Write the object
            encoder.writeObject(object);
            encoder.close();
            System.out.println("Saved Successfully.");
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Used for loading objects from XML
     * @param folderName        Path of the directory
     * @param fileName          Full filename with extension
     * @return                  The loaded Object
     */
    public static Object LoadFromXML(String folderName, String fileName)
    {
        //Check to see if the directory exists.  If not, create it.
        if (!new File(folderName).exists()){
            System.out.println("No folders with the name '" + folderName + "' exist in the path specified.  Please make sure it is correct.");
            return null;
        }
        else if (!new File(folderName + fileName).exists()){
            System.out.println("No files named '" + fileName + "' exist in the path specified.  Please make sure it is correct.");
            return null;
        }//end if the file itself does not exist

        //Temporary employee for to hold the file's data
        Object returnValue = new Object();

        try
        {
            //Create the input streams
            FileInputStream fis = new FileInputStream(folderName + fileName);

            //Create an XML Decoder
            XMLDecoder decoder = new XMLDecoder(fis);

            //Read the object to a file
            returnValue = decoder.readObject();
            decoder.close();
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            return null;
        }

        return returnValue;
    }
}
