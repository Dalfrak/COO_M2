package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import model.Adder;
import model.Block;
import model.Data;
import model.Step;
import view.chart.Chart;
import view.chart.ChartFrame;

public class ODE_1er_ordre {
	private ArrayList<Block> components; // List of components

	public ODE_1er_ordre() {
		this.components = new ArrayList<Block>();

		Block step1 = new Step("step1", null, null, 0.65, 1, -3);
		Block step2 = new Step("step2", null, null, 0.35, 0, 1);
		Block step3 = new Step("step3", null, null, 1, 0, 1);
		Block step4 = new Step("step4", null, null, 1.5, 0, 4);
		Block adder = new Adder("adder", null, null, 0);

		step1.setConnectedBlock(adder);
		step2.setConnectedBlock(adder);
		step3.setConnectedBlock(adder);
		step4.setConnectedBlock(adder);

		this.components.add(step1);
		this.components.add(step2);
		this.components.add(step3);
		this.components.add(step4);
		this.components.add(adder);
	}

	public void run() {

		double minTr;

		double t    = 0;
		double tEnd = 1.5;

		TreeMap<Block, TreeMap<String, Data>> ins    = new TreeMap<Block, TreeMap<String, Data>>();
		ArrayList<Block>                      imms   = new ArrayList<Block>();
		ArrayList<Double>                     trList = new ArrayList<Double>();

		for (Block c : components) {
			c.setCurrentState(1);
//			c.setTr(c.timeAdvancement());
		}

		ChartFrame cf = new ChartFrame("osef", "yolo");
		Chart      cq = new Chart("Total");

		cf.addToLineChartPane(cq);
		cq.setIsVisible(true);

		while (t <= tEnd) {
			// Get the minimum tr of each block
			for (Block c : components)
				trList.add(c.getTr());

			// Update Graph
			cq.addDataToSeries(t, ((Adder) components.get(4)).getTotal());

			minTr = Collections.min(trList);

			// Construct the list of immediate components
			for (Block c : components)
				if (c.getTr() == minTr)
					imms.add(c);

			System.out.println(t + " => " + ((Adder) components.get(4)).getTotal());
			t += minTr;

			// Update e and tr for each block
			for (Block c : components) {
				c.setE(t - c.getTl());
				c.setTr(c.timeAdvancement() - c.getE());
			}

			// Produce all outputs of all imminent components
			for (Block c : imms)
				c.output();

			// Build the input list ins affected by outputs, then transmit the output to the
			// input of the connected component
			for (Block c : components) {
				if (imms.contains(c) && c.getConnectedBlock() != null) {
					ins.put(c.getConnectedBlock(), c.getOutputEvents());
					c.getConnectedBlock().addInputEvents(c.getOutputEvents());

					System.out.println(c.getId() + " a passé " + c.getOutputEvents() + " à " + c.getConnectedBlock().getId());

					c.setOutputEvents(new TreeMap<String, Data>());
				}
			}

			// Execute all components
			for (Block c : components) {

				if (imms.contains(c) && !ins.containsKey(c)) {
					c.internal();
					c.setE(0);
					c.setTr(c.timeAdvancement() - c.getE());
					c.setTl(t - c.getE());
					c.setTn(t + c.getTr());
				} else if (!imms.contains(c) && ins.containsKey(c)) {
					c.external();
					c.setE(0);
					c.setTr(c.timeAdvancement() - c.getE());
					c.setTl(t - c.getE());
					c.setTn(t + c.getTr());
				} else if (imms.contains(c) && ins.containsKey(c)) {
					c.conflict();
					c.setE(0);
					c.setTr(c.timeAdvancement() - c.getE());
					c.setTl(t - c.getE());
					c.setTn(t + c.getTr());
				}
			}

			// So they're reset every loop
			ins.clear();
			imms.clear();
			trList.clear();
		}
	}
}
