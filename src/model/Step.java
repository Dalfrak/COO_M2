package model;

import java.util.TreeMap;

public class Step extends Block {

	private double initialValue;
	private double finalValue;

	public Step(String id, TreeMap<String, Data> X, TreeMap<String, Data> Y, double ta, double initialValue, double finalValue) {
		super(id, X, Y, ta);
		this.initialValue = initialValue;
		this.finalValue   = finalValue;
		this.tr           = 0;
		this.outputEvents.put(this.id, new Data(this.initialValue));
	}

	@Override
	public void external() {}

	@Override
	public void internal() {
		if (e != 0)
			this.currentState = 2;
	}

	@Override
	public void output() {
		System.out.println(this.id + ".output()");
		if (this.currentState == 2) {
			this.outputEvents.put(this.id, new Data(this.finalValue));
			System.out.println("\tPut " + this.finalValue + " into output events list");
		}
	}

	@Override
	public double timeAdvancement() {
		if (this.currentState == 1)
			return this.ta;
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public void conflict() {}

	@Override
	public void init() {}

}
