package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import model.Block;
import model.Buffer;
import model.Data;
import model.Generator;
import model.Processor;
import view.chart.Chart;
import view.chart.ChartFrame;

public class Simulator {

	private ArrayList<Block> components; // List of components

	public Simulator() {
		this.components = new ArrayList<Block>();

		Block G = new Generator("G", null, null, 2);
		Block B = new Buffer("B", null, null, 0);
		Block P = new Processor("P", null, null, 3);

		G.setConnectedBlock(B);
		B.setConnectedBlock(P);
		P.setConnectedBlock(B);

		this.components.add(G);
		this.components.add(B);
		this.components.add(P);
	}

	public void run() {

		double minTr;

		double t    = 0;
		double tEnd = 20;

		TreeMap<Block, TreeMap<String, Data>> ins    = new TreeMap<Block, TreeMap<String, Data>>();
		ArrayList<Block>                      imms   = new ArrayList<Block>();
		ArrayList<Double>                     trList = new ArrayList<Double>();

		for (Block c : components) {
			c.setCurrentState(1);
			c.setTr(c.timeAdvancement());
		}

		ChartFrame cf = new ChartFrame("Gen-Buff-Proc", "Gen-Buff-Proc");
		Chart      cq = new Chart("q");

		cf.addToLineChartPane(cq);

		while (t <= tEnd) {

			// Get the minimum tr of each block
			for (Block c : components)
				trList.add(c.getTr());

			// Display values
//			System.out.println("\tt= " + t);
//			for (Block c : components) {
//				if (c.getId() == "G")
//					System.out.println("\tjob|" + c.getTr());
//				if (c.getId() == "P" && c.getCurrentState() == 2)
//					System.out.println("\tdone|" + c.getTr());
//				if (c.getId() == "B" && c.getCurrentState() == 2)
//					System.out.println("\treq|" + c.getTr());
//				if (c.getId() == "B")
//					System.out.println("\tq= " + ((Buffer) c).getQ());
//			}
//			System.out.println("\n=======================================\n");

			cq.addDataToSeries(t, ((Buffer) components.get(1)).getQ());

			minTr = Collections.min(trList);

			// Construct the list of immediate components
			for (Block c : components)
				if (c.getTr() == minTr)
					imms.add(c);

			t = t + minTr;

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
				if (imms.contains(c)) {
					ins.put(c.getConnectedBlock(), c.getOutputEvents());
					c.getConnectedBlock().addInputEvents(c.getOutputEvents());
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
