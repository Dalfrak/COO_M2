package model;

import java.util.TreeMap;

public class Buffer extends Block {

	private int q;

	public Buffer(String id, TreeMap<String, Data> X, TreeMap<String, Data> Y, double ta) { super(id, X, Y, ta); }

	public int getQ() { return this.q; }

	@Override
	public void external() {

		if (this.currentState == 1 && this.inputEvents.containsKey("job")) {
			this.inputEvents.remove("job");
			this.q++;
			this.currentState = 2;
		} else if (this.currentState == 3 && this.q > 0 && this.inputEvents.containsKey("done")) {
			this.inputEvents.remove("done");
			this.currentState = 2;
		} else if (this.currentState == 3 && this.q == 0 && this.inputEvents.containsKey("done")) {
			this.inputEvents.remove("done");
			this.currentState = 1;
		} else if (this.currentState == 3 && this.inputEvents.containsKey("job")) {
			this.inputEvents.remove("job");
			this.q++;
//			this.currentState = 3;
		}
	}

	@Override
	public void internal() {
		if (this.currentState == 2) {
			this.q--;
			this.currentState = 3;
		}
	}

	@Override
	public void output() {
		if (this.currentState == 2)
			this.outputEvents.put("req", new Data(true));
	}

	@Override
	public double timeAdvancement() {
		if (this.currentState == 2)
			return this.ta;
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public void conflict() { this.internal(); }

	@Override
	public void init() {}

}
