package model;

import java.util.TreeMap;

public class Step extends Block {

	private double initialValue;
	private double finalValue;

	public Step(String id, TreeMap<String, Data> X, TreeMap<String, Data> Y, double ta, double initialValue,
			double finalValue) {
		super(id, X, Y, ta);
		this.initialValue = initialValue;
		this.finalValue = finalValue;
		this.outputEvents.put(this.id, new Data(this.initialValue));
	}

	@Override
	public void external() {}

	@Override
	public void internal() {
		this.currentState = 2;
		this.tr = Double.POSITIVE_INFINITY;
	}

	@Override
	public void output() {
		if (this.currentState == 2)
			this.outputEvents.put(this.id, new Data(this.finalValue));
	}

	@Override
	public double timeAdvancement() { return this.ta; }

	@Override
	public void conflict() {}

	@Override
	public void init() {}

}
