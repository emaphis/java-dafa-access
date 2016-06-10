package browser;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.table.*;

class ResultSetTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private ArrayList<String> columnHeaders;
	private ArrayList<ArrayList<Object>> tableData;

	  public ResultSetTableModel(ResultSet resultSet)
	      throws SQLException {
	    ArrayList<Object> rowData;
	    ResultSetMetaData rsmd = resultSet.getMetaData();
	    int count = rsmd.getColumnCount();
	    columnHeaders = new ArrayList<String>(count);
	    tableData = new ArrayList<ArrayList<Object>>();
	    for (int i = 1; i <= count; i++) {
	      columnHeaders.add(rsmd.getColumnName(i));
	    }
	    while (resultSet.next()) {
	      rowData = new ArrayList<Object>(count);
	      for (int i = 1; i <= count; i++) {
	        rowData.add(resultSet.getObject(i));
	      }
	      tableData.add(rowData);
	    }
	  }

	    public int getColumnCount() {
	      return columnHeaders.size();
	    }

	    public int getRowCount() {
	      return tableData.size();
	    }

	    public Object getValueAt(int row, int column) {
	      ArrayList<?> rowData = tableData.get(row);
	      return rowData.get(column);
	    }

	    public boolean isCellEditable(int row, int column) {
	      return false;
	    }

	    public String getColumnName(int column) {
	      return (String)(columnHeaders.get(column));
	    }

	  }

