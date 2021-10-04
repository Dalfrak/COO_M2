package model;

import java.util.TreeMap;

public class Adder extends Block {

	private double total;

	public Adder(String id, TreeMap<String, Data> X, TreeMap<String, Data> Y, double ta) {
		super(id, X, Y, ta);
		this.total = 0;
		this.tr    = 0;
	}

	@Override
	public void external() {
		if (this.currentState == 1) {
			while (!this.inputEvents.isEmpty()) {
				if (this.inputEvents.containsKey("step1")) {
					this.total        += this.inputEvents.get("step1").doubleValue();
					this.currentState  = 2;
					this.inputEvents.remove("step1");
				}
				if (this.inputEvents.containsKey("step2")) {
					this.total        += this.inputEvents.get("step2").doubleValue();
					this.currentState  = 2;
					this.inputEvents.remove("step2");
				}
				if (this.inputEvents.containsKey("step3")) {
					this.total        += this.inputEvents.get("step3").doubleValue();
					this.currentState  = 2;
					this.inputEvents.remove("step3");
				}
				if (this.inputEvents.containsKey("step4")) {
					this.total += this.inputEvents.get("step4").doubleValue();
					this.inputEvents.remove("step4");
				}
			}
		}

//		System.out.println("\t");
	}

	@Override
	public void internal() {
		if (this.currentState == 2)
			this.currentState = 1;
	}

	@Override
	public void output() {
		if (this.currentState == 2)
			this.outputEvents.put("total", new Data(this.total));
	}

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

	public double getTotal() { return total; }

	@Override
	public String toString() { return this.id; }

}
