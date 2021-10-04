package table;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import chart.TimeAndValue;

/**
 *
 *Modèle de table de valeur par rapport au temps t
 */
public class TableModelChart extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	public static final int Time = 0;
	public static final int Value = 1;

	protected String[] columnNames;
	protected Vector<TimeAndValue> dataVector;

	/**
	 * Constructeur du modèle de table
	 */
	public TableModelChart() {
		this.columnNames = new String[]{"Time","Value"};
		dataVector = new Vector<TimeAndValue>();
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}


	public Class<?> getColumnClass(int column) {
		switch (column) {
			case Time:
				 return String.class;
			case Value:
				 return String.class;
			default:
				 return Object.class;
		}
	}

	public Object getValueAt(int row, int column) {
		TimeAndValue tv = dataVector.get(row);
		switch (column) {
			case Time:
			   return tv.getT();
			case Value:
				return tv.getValue();
			default:
			   return new Object();
		}
	}

	public int getRowCount() {
		return dataVector.size();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public void addTimeAndValue(TimeAndValue tv){
		this.dataVector.add(tv);
		super.fireTableDataChanged();
	}

	public Vector<TimeAndValue> getDataVector(){
		return this.dataVector;
	}
}
