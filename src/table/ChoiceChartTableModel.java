package table;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import chart.Chart;

/**
 * Modèle de table pour le choix de courbes à afficher
 */
public class ChoiceChartTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	public static final int Name = 0;
	public static final int Visible = 1;

	protected String[] columnNames;
	protected Vector<Chart> dataVector;

	/**
	 * Constructeur du modèle de table
	 *
	 */
	public ChoiceChartTableModel() {
		this.columnNames = new String[]{"Charts","Visible"};
		dataVector = new Vector<Chart>();
	}


	public String getColumnName(int column) {
		return columnNames[column];
	}

	public boolean isCellEditable(int row, int column) {
		if(column==1)
			return true;
		return false;
	}


	public Class<?> getColumnClass(int column) {
		switch (column) {
			case Name:
				 return String.class;
			case Visible:
				 return Boolean.class;
			default:
				 return Object.class;
		}
	}

	public Object getValueAt(int row, int column) {
		Chart c = dataVector.get(row);
		switch (column) {
			case Name:
			   return c.getName();
			case Visible:
				return c.getIsVisible();
			default:
			   return new Object();
		}
	}

	public void setValueAt(Object value, int row, int column) {
		Chart c = dataVector.get(row);
		switch (column) {
			case Name:
				c.setName((String)value);
				break;
			case Visible:
				c.setIsVisible((Boolean)value);
				break;
			default:
		   //    Console.println("Invalid index");
		}
		fireTableCellUpdated(row, column);
	}

	public int getRowCount() {
		return dataVector.size();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public Chart getChart(int row){
		if(row>=0)
			return this.dataVector.get(row);
		else return null;
	}

	public void addChart(Chart c){
		this.dataVector.add(c);
		this.fireTableDataChanged();
	}

	public void clearAll(){
		this.dataVector.clear();
	}

}
