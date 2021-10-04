package model;

import java.util.TreeMap;

public class Adder extends Block {

	private double total;

	public Adder(String id, TreeMap<String, Data> X, TreeMap<String, Data> Y, double ta) {
		super(id, X, Y, ta);
		this.total = 0;
	}

	@Override
	public void external() {
		System.out.println("Adder.external()");
		System.out.println("\t" + this.inputEvents);
		while (!this.inputEvents.isEmpty()) {
			if (this.inputEvents.containsKey("step1")) {
				System.out.println("\tstep1, got: " + this.inputEvents.get("step1").doubleValue());
				this.total += this.inputEvents.get("step1").doubleValue();
				this.currentState = 2;
				this.inputEvents.remove("step1");
			}
			if (this.inputEvents.containsKey("step2")) {
				System.out.println("\tstep2, got: " + this.inputEvents.get("step2").doubleValue());
				this.total += this.inputEvents.get("step2").doubleValue();
				this.currentState = 2;
				this.inputEvents.remove("step2");
			}
			if (this.inputEvents.containsKey("step3")) {
				System.out.println("\tstep3, got: " + this.inputEvents.get("step3").doubleValue());
				this.total += this.inputEvents.get("step3").doubleValue();
				this.currentState = 2;
				this.inputEvents.remove("step3");
			}
			if (this.inputEvents.containsKey("step4")) {
				System.out.println("\tstep4, got: " + this.inputEvents.get("step4").doubleValue());
				this.total += this.inputEvents.get("step4").doubleValue();
				this.inputEvents.remove("step4");
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

}
