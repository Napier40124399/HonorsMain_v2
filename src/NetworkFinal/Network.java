package NetworkFinal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("serial")
public class Network implements Serializable {
	private ArrayList<Part> parts;
	private ArrayList<ArrayList<Part>> fabric = new ArrayList<ArrayList<Part>>();
	private ArrayList<Float> biases = new ArrayList<Float>();
	private Float id;
	private Float biasWeight = 0.001f;
	private Double newNodeChance = 0.05;

	// NEW
	private Part outputNode;

	/**
	 * <h1>Network</h1>Constructor, takes in an Integer[] which is used to
	 * create the adequate topology.
	 * 
	 * @param size
	 *            (Integer[]) - Meant to be the topology, index is the layer and
	 *            value is the ndoes on the layer.
	 */
	public Network(Integer[] size) {
		setUp(size);
	}

	/**
	 * <h1>Network</h1>Alternate constructor, copies a network instead of
	 * creating one.
	 * 
	 * @param fabric
	 *            (ArrayList<ArrayList<Part>>)
	 * @param biases
	 *            (ArrayList<Float>)
	 */
	public Network(ArrayList<ArrayList<Part>> fabric, ArrayList<Float> biases) {
		this.fabric = fabric;
		this.biases = biases;
		doCons();
	}

	public void setUp(Integer[] size) {
		outputNode = new Part();

		Random r = new Random();
		parts = new ArrayList<Part>();
		for (int i = 0; i < size[0]; i++) {
			biases.add(new Float(r.nextGaussian() * biasWeight));
			parts.add(new Part());
		}
		fabric.add(parts);
		for (Part p : fabric.get(0)) {
			p.addNode(outputNode);
		}

		for (int i = 1; i < size.length; i++) {
			addLayer();
			for (int j = 0; j < size[i] - 1; j++) {
				addNodeEnd(i);
			}
		}
	}

	public Float think(ArrayList<Float> memory) {
		ArrayList<Float> temp = new ArrayList<Float>();
		for (int i = 0; i < memory.size(); i++) {
			temp.add(new Float(biases.get(i) + memory.get(i)));
		}
		int j = 0;
		for (Part p : fabric.get(0)) {
			p.receive(temp.get(j));
			p.pass();
			j++;
		}
		for (int i = 1; i < fabric.size(); i++) {
			for (Part p : fabric.get(i)) {
				p.handle();
				p.pass();
			}
		}
		Float res = outputNode.getVal();
		outputNode.pass();
		return res;
	}

	// A complicated method to use
	public void mutate(Float mutationChance, Float muationAmount, Float conWeightAllowance, Float nodeChangeChance,
			Float layerChangeChance, Boolean dynTop, int maxNodes) {
		// All initialized the same way, this is the difference
		for (ArrayList<Part> layer : fabric) {
			for (Part p : layer) {
				p.mutate(muationAmount, mutationChance, conWeightAllowance);
			}
		}

		// Dynamic topology logic
		if (dynTop && fabric.size() > 1) {
			dynTopLogic(nodeChangeChance, layerChangeChance);
		}
		/*
		if (false) {
			for (int i = 1; i < fabric.size() - 1; i++) {
				if (fabric.get(i).size() < maxNodes) {
					if (Math.random() < nodeChangeChance) {
						addNodeHot(i);
					}
				}
			}
			if(Math.random() < nodeChangeChance && fabric.size() > 1)
			{
				addNode(fabric.size()-1);
			}
			if (Math.random() < layerChangeChance) {
				addLayer();
			}
		}*/

		// Biases
		Random r = new Random();
		for (int i = 0; i < biases.size(); i++) {
			biases.set(i, new Float(biases.get(i) + (r.nextGaussian() * biasWeight)));
		}

		for (int i = 0; i < biases.size(); i++) {
			if (biases.get(i) >= biasWeight * 10) {
				biases.set(i, new Float(biasWeight * 10));
			} else if (biases.get(i) <= -biasWeight * 10) {
				biases.set(i, new Float(-biasWeight * 10));
			}
		}
	}

	private void dynTopLogic(Float nodeChangeChance, Float layerChangeChance) {
		int end = fabric.size()-1;
		for(int i = 1; i < end; i++)
		{
			if(Math.random() < nodeChangeChance)
			{
				addNodeHot(i);
			}
		}
		if(Math.random() < nodeChangeChance)
		{
			addNodeEnd(end);
		}
		if(Math.random() < layerChangeChance)
		{
			addLayerAlt();
		}
		ArrayList<Part> toBeRemoved = new ArrayList<Part>();
		ArrayList<Part> toBeRemovedLayer = new ArrayList<Part>();
		for (int i = 1; i < fabric.size(); i++) {
			for (int j = 0; j < fabric.get(i).size(); j++) {
				if (Math.random() < nodeChangeChance) {
					if (fabric.get(i).size() > 1) {
						fabric.get(i).get(j).setIndex(i);
						toBeRemoved.add(fabric.get(i).get(j));
					} else {
						fabric.get(i).get(j).setIndex(i);
						toBeRemovedLayer.add(fabric.get(i).get(j));
					}
				}
			}
		}
		removeNodes(toBeRemoved);
		removeLayers(toBeRemovedLayer);
	}
	
	private void addLayerAlt()
	{
		Part part = new Part();
		for(Part p : fabric.get(fabric.size()-1))
		{
			p.removeNode(outputNode);
		}
		ArrayList<Part> newLayer = new ArrayList<Part>();
		newLayer.add(part);
		fabric.add(newLayer);
		for(Part p : fabric.get(fabric.size()-2))
		{
			p.addNode(part);
		}
		part.addNode(outputNode);
	}

	private void addLayer() {
		for (Part p : fabric.get(fabric.size() - 1)) {
			p.removeNode(outputNode);
		}
		fabric.add(new ArrayList<Part>());
		addNodeEnd(fabric.size() - 1);
	}

	private void removeNodes(ArrayList<Part> parts) {
		for (int i = parts.size() - 1; i > -1; i--) {
			Part pa = parts.get(i);
			parts.set(i, null);
			removeNode(pa.getIndex(), pa);
		}
	}

	private void removeLayers(ArrayList<Part> parts) {
		for (int i = parts.size() - 1; i > -1; i--) {
			Part pa = parts.get(i);
			int index = pa.getIndex();
			parts.set(i, null);
			removeNode(pa.getIndex(), pa);
			fabric.remove(index);
			if (index < fabric.size() - 1) {
				for (Part p1 : fabric.get(index - 1)) {
					for (Part p2 : fabric.get(index)) {
						p1.addNode(p2);
					}
				}
			} else {
				for (Part p : fabric.get(index - 1)) {
					p.addNode(outputNode);
				}
			}
		}
	}

	private String topState() {
		String top = "";
		for (int i = 0; i < fabric.size(); i++) {
			top += fabric.get(i).size() + "-";
		}
		return top;
	}

	private void verify() {
		ArrayList<Integer> layersToFix = new ArrayList<Integer>();
		for (int i = 1; i < fabric.size(); i++) {
			if (fabric.get(i).size() == 0) {
				layersToFix.add(i);
			}
		}
		fabric.removeAll(layersToFix);
	}

	private void removeNodes(ArrayList<Part> parts, int j) {
		for (int i = 0; i < parts.size(); i++) {
			Part pa = parts.get(i);
			parts.set(i, null);
			removeNode(j, pa);
		}
	}

	private void removeLayer(int i) {
		ArrayList<Part> toBeRemoved = new ArrayList<Part>();
		for (Part p : fabric.get(i)) {
			toBeRemoved.add(p);
		}

		removeNodes(toBeRemoved, i);

		fabric.remove(i);

		if (i == fabric.size() - 1) {
			for (Part p : fabric.get(i - 1)) {
				p.addNode(outputNode);
			}
		} else {
			for (Part p1 : fabric.get(i - 1)) {
				for (Part p2 : fabric.get(i)) {
					p1.addNode(p2);
				}
			}
		}
	}

	private void addNodeHot(int i)
	{
		Part ppp = new Part();
		fabric.get(i).add(ppp);
		for (Part p : fabric.get(i - 1)) {
			p.addNode(ppp);
		}
		for(Part p : fabric.get(i+1))
		{
			ppp.addNode(p);
		}
	}
	
	private void addNodeEnd(int i) {
		Part ppp = new Part();
		fabric.get(i).add(ppp);
		for (Part p : fabric.get(i - 1)) {
			p.addNode(ppp);
		}
		ppp.addNode(outputNode);
	}

	private void removeNode(int i, Part p) {
		for (Part previousP : fabric.get(i - 1)) {
			previousP.removeNode(p);
		}
		fabric.get(i).remove(p);
	}

	public Network makeCopy() {
		// Part 1
		// Create new Fabric
		ArrayList<ArrayList<Part>> fabric2 = new ArrayList<ArrayList<Part>>();
		for (int i = 0; i < fabric.size(); i++) {
			fabric2.add(new ArrayList<Part>());
			for (int j = 0; j < fabric.get(i).size(); j++) {
				fabric2.get(i).add(fabric.get(i).get(j).fullCopy());
			}
		}
		ArrayList<Float> biases2 = new ArrayList<Float>();
		for (int i = 0; i < biases.size(); i++) {
			biases2.add(new Float(biases.get(i)));
		}
		// Part 2
		// Place in new Network
		Network network = new Network(fabric2, biases2);
		return network;
	}

	public void doCons() {
		outputNode = new Part();
		for (int i = 0; i < fabric.size() - 1; i++) {
			for (int j = 0; j < fabric.get(i).size(); j++) {
				fabric.get(i).get(j).setNext(fabric.get(i + 1));
			}
		}
		for (int j = 0; j < fabric.get(fabric.size() - 1).size(); j++) {
			fabric.get(fabric.size() - 1).get(j).addPrepNode(outputNode);
		}
	}

	public String getTop() {
		String temp = "";

		for (ArrayList<Part> layer : fabric) {
			temp = temp + layer.size() + "-";
		}
		temp += "1";
		return temp;
	}

	public void showCons() {
		System.out.println("=====================================network");
		for (ArrayList<Part> layer : fabric) {
			System.out.println("=================== layer");
			for (Part p : layer) {
				System.out.println("========== node");
				p.getWeights();
			}
		}
		System.out.println("================== BIASES");
		for (Float f : biases) {
			System.out.println(f);
		}
	}

	public void defect() {
		for (ArrayList<Part> layer : fabric) {
			for (Part p : layer) {
				p.becomeDefector();
			}
		}
		for (int i = 0; i < biases.size(); i++) {
			biases.set(i, -1f);
		}
	}
}
