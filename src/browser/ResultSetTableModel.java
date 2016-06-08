package browser;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.table.*;

public class ResultSetTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7483776745622916959L;
	private ArrayList columnHeaders;
	private List tableData;
	
	public ResultSetTableModel(ResultSet resultSet)
		  throws SQLException {
		ArrayList rowData;
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int count = rsmd.getColumnCount();
		columnHeaders = new ArrayList(count);
		tableData  new ArrayList(count);
		for (int i = 1; i <= count; i++) {
			columnHeaders.add(rsmd.getColumnName(i));
		}
		while (resultSet.next()) {
			rowData = new ArrayList(count);
			for (int i = 1; i <= count; i++) {
				rowData.add(resultSet.getObject(i));
			}
			tableData.add(rowData);
		}
	}

	@Override
	public int getColumnCount() {
		return columnHeaders.size();
	}

	@Override
	public int getRowCount() {
		return tableData.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		List rowData = (List)(tableData.get(row);)
		return rowData.get(column);
	}
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public String getColumnName(int column) {
		return columnHeaders.get(column);
	}

}
