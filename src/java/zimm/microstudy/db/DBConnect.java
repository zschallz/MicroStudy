/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zimm.microstudy.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zschallz
 */
public class DBConnect
{

    private Connection connection = null;
    private String username = "dev";
    private String password = "dev";
    private String server = "127.0.0.1";
    private String dbName = "study";
    private String url = "jdbc:mysql://" + server + "/" + dbName;
    
    private DBConnect() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        connect();
    }

    private void connect() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection (url, username, password);
    }


    public void close() throws SQLException
    {
        if (connection != null)
        {
            this.connection.close();
        }
    }

    public static ResultSet executeSP(String storedProcedureName) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        DBConnect dbConnect = new DBConnect();
        Statement statement;

        statement = dbConnect.getConnection().createStatement();
        statement.executeQuery("CALL " + storedProcedureName);

        return statement.getResultSet();
       
    }

    public static boolean parseDBInt(int intValue)
    {
        if( intValue == 1 )
            return true;
        else if( intValue == 0 )
            return false;
        else
            throw new IllegalArgumentException("A value of one or zero was expected, but received: " + intValue);
    }

    public static int parseDBBoolean(boolean booleanValue)
    {
        if( booleanValue == true )
            return 1;
        else
            return 0;
    }
    public static List<Calendar> parseDaysOfWeekString(String daysOfWeek)
    {
        List<Calendar> days = new ArrayList<Calendar>();
        Calendar calendar = null;
        /* TODO: Take into account timezones */
        for(char c : daysOfWeek.toCharArray())
        {
            calendar = Calendar.getInstance();
            switch( c )
            {
                case '1':
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                    break;
                case '2':
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    break;
                case '3':
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                    break;
                case '4':
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                    break;
                case '5':
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                    break;
                case '6':
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                    break;
                case '7':
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                    break;
            }
            if( calendar != null )
                days.add(calendar);
        }

        return days;
    }

    private Connection getConnection()
    {
        return connection;
    }

}
