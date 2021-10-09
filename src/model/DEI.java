package model;

import java.util.TreeMap;

public class DEI extends Block {

	private double DX = 0.01;

	private double x, x_, hstep;

	public DEI(String id, TreeMap<String, Data> X, TreeMap<String, Data> Y, double ta) {
		super(id, X, Y, ta);
		this.hstep = this.ta;
	}

	public DEI(String id, TreeMap<String, Data> X, TreeMap<String, Data> Y, double ta, double deltaX) {
		super(id, X, Y, ta);
		this.hstep = this.ta;
		this.DX    = deltaX;
	}

	public DEI(String id, TreeMap<String, Data> X, TreeMap<String, Data> Y, double ta, double deltaX, double starting_point) {
		super(id, X, Y, ta);
		this.hstep = this.ta;
		this.x     = starting_point;
		this.DX    = deltaX;
	}

	@Override
	public void external() {
		System.out.println("\t" + this.id + ".external()");
		if (this.currentState == 1 && !this.inputEvents.isEmpty()) {
			this.currentState = 2;

			System.out.println("\n" + this.id + " <- " + this.inputEvents.firstEntry().getKey());

			if (this.inputEvents.firstEntry().getKey() == "coeff") {
				System.out.println("Coeff received");
				this.x *= this.inputEvents.firstEntry().getValue().doubleValue();
			} else {
				double newX = (this.x + this.x_ * this.e);
				this.hstep = Math.abs((newX - this.x) / this.inputEvents.firstEntry().getValue().doubleValue());
				this.x_    = this.inputEvents.firstEntry().getValue().doubleValue();
				this.x     = newX;
			}
			this.outputEvents.put("integratedVal", new Data(this.x));
			this.inputEvents.clear();
		}
		System.out.println("\t" + this.id + ".currentState = " + this.currentState);
	}

	@Override
	public void internal() {
		System.out.println("\t" + this.id + ".internal()");
		if (this.currentState == 2)
			this.currentState = 1;
		else if (this.currentState == 1) {
			this.currentState  = 1;
			this.hstep         = this.DX / Math.abs(x_);
			this.x            += this.DX * Math.signum(x_);
		}
		System.out.println("\t" + this.id + ".currentState = " + this.currentState);
	}

	@Override
	public void output() { this.outputEvents.put("integratedVal", new Data(this.x)); }

	@Override
	public double timeAdvancement() {
		if (this.currentState == 1) { return this.hstep; }
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
