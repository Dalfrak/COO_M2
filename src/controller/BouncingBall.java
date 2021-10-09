package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import model.Block;
import model.DEI;
import model.Data;
import model.Inv;
import model.Step;
import view.chart.Chart;
import view.chart.ChartFrame;

public class BouncingBall {
	private ArrayList<Block> components; // List of components

	public BouncingBall() {
		this.components = new ArrayList<Block>();

		Block constant = new Step("constant", null, null, 0, 0, -9.81);
		Block dei1     = new DEI("dei1", null, null, 0.01, 0.01);
		Block dei2     = new DEI("dei2", null, null, 0.01, 0.01, 10);
		Block inv      = new Inv("inv", null, null, 0.01, 0.8);

		constant.setConnectedBlock(dei1);
		dei1.setConnectedBlock(dei2);
		dei2.setConnectedBlock(inv);
		inv.setConnectedBlock(dei1);

		this.components.add(constant);
		this.components.add(dei1);
		this.components.add(dei2);
		this.components.add(inv);
	}

	public void run() {

		double minTr;

		double t    = 0;
		double tEnd = 5;

		TreeMap<Block, TreeMap<String, Data>> ins    = new TreeMap<Block, TreeMap<String, Data>>();
		ArrayList<Block>                      imms   = new ArrayList<Block>();
		ArrayList<Double>                     trList = new ArrayList<Double>();

		for (Block c : components) {
			c.setCurrentState(1);
			c.setTr(c.timeAdvancement());

			// The goal of this code is to permit the Adder to get all xi
			if (c.getId() != "adder" && c.getConnectedBlock() != null) {
				ins.put(c.getConnectedBlock(), c.getOutputEvents());
				c.getConnectedBlock().addInputEvents(c.getOutputEvents());
				c.setOutputEvents(new TreeMap<String, Data>());
			} else {
				c.external();
				c.internal();
			}

		}

		ChartFrame cf = new ChartFrame("osef", "yolo");
		Chart      cq = new Chart("dei1");
		Chart      ci = new Chart("dei2");

		cf.addToLineChartPane(cq);
//		cq.setIsVisible(true);

		cf.addToLineChartPane(ci);
//		ci.setIsVisible(true);

		while (t <= tEnd) {
			// Get the minimum tr of each block
			for (Block c : components)
				trList.add(c.getTr());

			// Update Graph
			cq.addDataToSeries(t, ((DEI) components.get(1)).getX());
			ci.addDataToSeries(t, ((DEI) components.get(2)).getX());

			minTr = Collections.min(trList);
			System.out.println(trList);

			// Construct the list of immediate components
			for (Block c : components)
				if (c.getTr() == minTr)
					imms.add(c);

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
			for (Block c : imms) {
				if (c.getConnectedBlock() != null) {
					System.out.println(c.getId() + " passe " + c.getOutputEvents() + " à " + c.getConnectedBlock());
					ins.put(c.getConnectedBlock(), c.getOutputEvents());
					c.getConnectedBlock().addInputEvents(c.getOutputEvents());
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
					c.external(); // IVI
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
