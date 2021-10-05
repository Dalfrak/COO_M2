package controller;

import java.util.TreeMap;

import model.Block;
import model.Data;

public class DEI extends Block {

	private double x, x_, hstep;

	public DEI(String id, TreeMap<String, Data> X, TreeMap<String, Data> Y, double ta) {
		super(id, X, Y, ta);
		this.hstep = this.ta;
		this.x_    = 1;      // Cheat!
	}

	@Override
	public void external() {
		System.out.println(this.id + ".external()");
		if (this.currentState == 1 && this.inputEvents.containsKey("addedVal")) {

			double tmp;

			this.currentState = 2;

			tmp     = Math.abs((this.inputEvents.get("addedVal").doubleValue() - this.x_) / this.inputEvents.get("addedVal").doubleValue());
			this.x_ = this.inputEvents.get("addedVal").doubleValue();

			this.hstep = (tmp != 0) ? tmp : this.hstep;
			this.hstep = (this.hstep > 0.25) ? 0.01 : this.hstep;

			this.outputEvents.put("integratedVal", new Data(this.x));
			this.inputEvents.remove("addedVal");
		}
	}

	@Override
	public void internal() {
		if (this.currentState == 2)
			this.currentState = 1;
		else if (this.currentState == 1) {
			this.currentState  = 1;
			this.x            += this.x_ * this.hstep;
		}
	}

	@Override
	public void output() { this.outputEvents.put("integratedVal", new Data(this.x)); }

	@Override
	public double timeAdvancement() {
		if (this.currentState == 1) {
			System.out.println(this.hstep);
			return this.hstep;
		}
		return 0;
	}

	@Override
	public void conflict() { this.external(); }

	@Override
	public void init() {}

	public double getX() { return x; }

	@Override
	public String toString() { return this.id; }
}
