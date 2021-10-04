package model;

import java.util.TreeMap;

public class Generator extends Block {

	public Generator(String id, TreeMap<String, Data> X, TreeMap<String, Data> Y, double ta) { super(id, X, Y, ta); }

	@Override
	public void internal() {
//		if (this.currentState == 1)
//			this.currentState = 1;
		return;
	}

	@Override
	public void output() {
		if (this.currentState == 1)
			this.outputEvents.put("job", new Data(true));
	}

	@Override
	public double timeAdvancement() { return this.ta; }

	@Override
	public void external() {}

	@Override
	public void conflict() { return; }

	@Override
	public void init() {}

}
