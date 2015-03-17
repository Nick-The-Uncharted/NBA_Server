package utility;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.swing.table.DefaultTableModel;

public class DataBaseTableModel extends DefaultTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CachedRowSet rowset;
	ResultSetMetaData metadata;
	int numcols, numrows;

	public DataBaseTableModel(CachedRowSet rowSetArg)
		    throws SQLException {

		    this.rowset = rowSetArg;
		    this.metadata = this.rowset.getMetaData();
		    numcols = metadata.getColumnCount();

		    // Retrieve the number of rows.
		    this.rowset.beforeFirst();
		    this.numrows = 0;
		    while (this.rowset.next()) {
		        this.numrows++;
		    }
		    this.rowset.beforeFirst();
		}
	
	public int getColumnCount() {
	    return numcols;
	}

	public int getRowCount() {
	    return numrows;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Class getColumnClass(int column) {
	    return String.class;
	}
	
	public String getColumnName(int column) {
	    try {
	        return this.metadata.getColumnLabel(column + 1);
	    } catch (SQLException e) {
	        return e.toString();
	    }
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
	    try {
	        this.rowset.absolute(rowIndex + 1);
	        Object o = this.rowset.getObject(columnIndex + 1);
	        if (o == null)
	            return null;
	        else
	            return o.toString();
	    } catch (SQLException e) {
	        return e.toString();
	    }
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
	    return false;
	}
	
}
