package Vue;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class GeneralVue {
    public static void showOutput(ResultSet res) {
        ResultSetMetaData metaData;

        try {
            metaData = res.getMetaData();
        
            int columnCount = metaData.getColumnCount();
            while (res.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = res.getObject(i); // Works for any type
                    System.out.print(columnName + ": " + value + "  ");
                }
                System.out.println(); // New line for each row
            }
        }
        
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
