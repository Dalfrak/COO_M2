package model;

public class Data {
// Classe générique
	private Integer i;
	private Double d;
	private Boolean b;

	public Data(int i) { this.i = i; }

	public Data(double d) { this.d = d; }

	public Data(boolean b) { this.b = b; }

	public int intValue() { return this.i; }

	public double doubleValue() { return this.d; }

	public boolean boolValue() { return this.b; }

}
