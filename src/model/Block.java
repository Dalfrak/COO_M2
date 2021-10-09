package model;

import java.util.TreeMap;

public abstract class Block implements Comparable<Block> {

	protected String id;
	protected double e, tl, tn, tr, ta;
	protected int    currentState;

	protected TreeMap<String, Data> inputEvents;  // Inputs
	protected TreeMap<String, Data> outputEvents; // Outputs

	protected Block connectedBlock;

	public Block(String id, TreeMap<String, Data> X, TreeMap<String, Data> Y, double ta) {
		if (X == null)
			this.inputEvents = new TreeMap<String, Data>();
		else
			this.inputEvents = X;

		if (Y == null)
			this.outputEvents = new TreeMap<String, Data>();
		else
			this.outputEvents = Y;

		this.ta = ta;
		this.id = id;
	}

	// =========================== Getters and Setters ===========================

	public double getE() { return e; }

	public String getId() { return id; }

	public double getTl() { return tl; }

	public double getTn() { return tn; }

	public double getTr() { return this.tr; }

	public int getCurrentState() { return currentState; }

	public Block getConnectedBlock() { return this.connectedBlock; }

	public TreeMap<String, Data> getInputEvents() { return this.inputEvents; }

	public TreeMap<String, Data> getOutputEvents() { return this.outputEvents; }

	public void setE(double e) { this.e = e; }

	public void setTl(double tl) { this.tl = tl; }

	public void setTn(double tn) { this.tn = tn; }

	public void setTr(double tr) { this.tr = tr; }

	public void setConnectedBlock(Block connectedBlocks) { this.connectedBlock = connectedBlocks; }

	public void setCurrentState(int currentState) { this.currentState = currentState; }

	public void setInputEvents(TreeMap<String, Data> inputEvents) { this.inputEvents = inputEvents; }

	public void setOutputEvents(TreeMap<String, Data> outputEvents) { this.outputEvents = outputEvents; }

	// =========================== Abstract methods ===========================

	public abstract void external();

	public abstract void internal();

	public abstract void output();

	public abstract double timeAdvancement();

	public abstract void conflict();

	public abstract void init();

	// =========================== Helpers methods ===========================

	public void addInputEvents(TreeMap<String, Data> inputEvents) { this.inputEvents.putAll(inputEvents); }

	@Override
	public int compareTo(Block b) {
		// We could do something here more sophisticated, but at least it's working
		// right now :D
		if (this.id == b.id)
			return 0;
		return 1;
	}

}
