package model;

import java.util.TreeMap;

public class Processor extends Block {

	public Processor(String id, TreeMap<String, Data> X, TreeMap<String, Data> Y, double ta) { super(id, X, Y, ta); }

	// currentState = 1 : free
	// currentState = 2 : busy

	@Override
	public void external() {
		if (this.currentState == 1 && this.inputEvents.containsKey("req")) {
			this.inputEvents.remove("req");
			this.currentState = 2;
		}
	}

	@Override
	public void internal() {
		if (this.currentState == 2)
			this.currentState = 1;
	}

	@Override
	public void output() {
		if (this.currentState == 2)
			this.outputEvents.put("done", new Data(true));
	}

	@Override
	public double timeAdvancement() {
		if (this.currentState == 2)
			return this.ta;
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public void conflict() { return; }

	@Override
	public void init() {}

}
