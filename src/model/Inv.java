package model;

import java.util.TreeMap;

public class Inv extends Block {

	private double d, coeff;

	public Inv(String id, TreeMap<String, Data> X, TreeMap<String, Data> Y, double ta, double coeff) {
		super(id, X, Y, ta);
		this.d     = 1;
		this.coeff = coeff;
	}

	@Override
	public void external() {
		if (this.currentState == 1 && !this.inputEvents.isEmpty()) {
			double in  = this.inputEvents.firstEntry().getValue().doubleValue();
			double tmp = Math.signum(in);
			this.currentState = 2;
			this.d            = tmp * this.coeff;
			System.out.println("\tin: + " + in + "\td: " + this.d);
			if (tmp < 0)
				this.outputEvents.put("coeff", new Data(this.d));
			this.inputEvents.clear();
		}
	}

	@Override
	public void internal() {
		if (this.currentState == 2)
			this.currentState = 1;
	}

	@Override
	public void output() { this.outputEvents.put("coeff", new Data(this.d)); }

	@Override
	public double timeAdvancement() {
		if (this.currentState == 1)
			return Double.POSITIVE_INFINITY;
		return 0;
	}

	@Override
	public void conflict() {}

	@Override
	public void init() {}

	@Override
	public String toString() { return this.id; }

}
