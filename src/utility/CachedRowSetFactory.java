package utility;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

@SuppressWarnings("restriction")
public class CachedRowSetFactory {
	public static CachedRowSet getCachedRowSet(String tableName){
		CachedRowSet crs = null;
		try {
			crs = new CachedRowSetImpl();
	        crs.setUsername("Nick");
	        crs.setPassword("19950809");
	        crs.setUrl("jdbc:derby://localhost:1527/NBA_DB");
	        crs.setCommand("select * from " + tableName);
	        crs.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return crs;
	}
}
