package utility;

import java.sql.SQLException;

public class DataBaseUtility {
	public static void printSQLException(SQLException ex) {
	    for (Throwable e : ex) {
	        if (e instanceof SQLException) {
	        	 e.printStackTrace(System.err);
	                System.err.println("SQLState: " +
	                    ((SQLException)e).getSQLState());

	                System.err.println("Error Code: " +
	                    ((SQLException)e).getErrorCode());

	                System.err.println("Message: " + e.getMessage());

	                Throwable t = ex.getCause();
	                while(t != null) {
	                    System.out.println("Cause: " + t);
	                    t = t.getCause();
	                }
	            }  
	        }
	    }
	
	  public static boolean tableAlreadyExists(SQLException e) {
	        boolean exists;
	        if(e.getSQLState().equals("X0Y32")) {
	            exists = true;
	        } else {
	            exists = false;
	        }
	        return exists;
	    }
	  
	  public static boolean tableNotExists(SQLException e){
		  return e.getSQLState().equals("42Y55");
	  }
}
