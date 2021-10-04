package view.chart;

import java.io.Serializable;

/**
 * Classe TimeAndValue définit une valeur associée à un temps t et son type de
 * valeur
 */

public class TimeAndValue implements Serializable {
	private static final long serialVersionUID = 1L;
	private String t;
	private String value;
	private String type;

	/**
	 * Constructeur de TimeAndValue
	 * 
	 * @param type le type de valeur
	 */
	public TimeAndValue(String type) {
		this.type = type;
		t = "";
		value = "";
	}

	/**
	 * Récupérer le temps t
	 * 
	 * @return
	 */
	public String getT() { return t; }

	/**
	 * Modifier le temps t
	 * 
	 * @param t le temps t
	 */
	public void setT(String t) { this.t = t; }

	/**
	 * Récupérer la valeur
	 * 
	 * @return la valeur
	 */
	public String getValue() { return value; }

	/**
	 * Modifier la valeur
	 * 
	 * @param value la valeur
	 */
	public void setValue(String value) { this.value = value; }

	/**
	 * Modifier le type de valeur
	 * 
	 * @param type le type de valeur
	 */
	public void setType(String type) { this.type = type; }

	/**
	 * Récupérer le type de valeur
	 */
	public String getType() { return type; }
}
