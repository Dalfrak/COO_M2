package view.chart;

import java.util.Observable;

import org.jfree.data.xy.XYSeries;

import view.table.TableModelChart;

/**
 * Classe Chart
 *
 */
public class Chart extends Observable {

	private String name;
	private boolean isVisible;
	private XYSeries series;
	private TableModelChart tableModel;

	/**
	 * Constructeur Chart
	 * 
	 * @param name nom de la courbe
	 */
	public Chart(String name) {
		this.name = name;
		series = new XYSeries(name);
		tableModel = new TableModelChart();
	}

	/**
	 * Récupérer le nom de la courbe
	 * 
	 * @return nom de la courbe
	 */
	public String getName() { return name; }

	/**
	 * Affecter le nom de la courbe
	 * 
	 * @param name nom de la courbe
	 */
	public void setName(String name) { this.name = name; }

	/**
	 * Récupérer le flag de visibilité de la courbe
	 * 
	 * @return true si visible activé false sinon
	 */
	public boolean getIsVisible() { return isVisible; }

	/**
	 * Modifier le flag de visibilité
	 * 
	 * @param isVisible true si visible sinon false
	 */
	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
		setChanged();
		notifyObservers();
	}

	/**
	 * Retourner XYSeries utilisé par JFreeChart
	 * 
	 * @return XYSeries
	 */
	public XYSeries getSeries() { return series; }

	/**
	 * Ajouter nouveau point à la courbe
	 * 
	 * @param x
	 * @param y
	 */
	public void addDataToSeries(double x, double y) {
		series.add(x, y);
		TimeAndValue tv = new TimeAndValue(""); // à optimiser
		tv.setT(String.valueOf(x));
		tv.setValue(String.valueOf(y));
		tableModel.addTimeAndValue(tv);
	}

	/**
	 * Supprimer l'ensemble des points de la courbe
	 */
	public void clearSeries() {
		System.out.println("clear");
		series.clear();
		tableModel.getDataVector().clear();
	}

	/**
	 * Récupérer le modèle de table de la courbe
	 * 
	 * @return le modèle de table de la courbe
	 */
	public TableModelChart getTableModel() { return this.tableModel; }
}
