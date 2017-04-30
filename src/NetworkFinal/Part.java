package NetworkFinal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

@SuppressWarnings("serial")
public class Part implements Serializable {
	private ArrayList<Part> next = new ArrayList<Part>();
	private ArrayList<Float> weights = new ArrayList<Float>();
	private Float input = new Float(0);

	/**
	 * <h1>pass</h1>For each connection, calculates the value and sends it.
	 */
	public void pass() {
		for (int i = 0; i < weights.size(); i++) {
			next.get(i).receive(output(i));
		}
		reset();
	}

	/**
	 * <h1>handle</h1>Triggers the pass-through function calculation.
	 */
	public void handle() {
		passThroughFunction();
	}

	/**
	 * <h1>receive</h1>Used to pass value into node. Adds input value to the
	 * node's total.
	 * 
	 * @param input
	 *            (Float)
	 * @see {@link NetworkFinal.Part#pass pass}
	 */
	public void receive(Float input) {
		this.input += input;
	}

	/**
	 * <h1>addNode</h1>Adds a connection to input node as well as a randomly
	 * generated connection weight.
	 * 
	 * @param part
	 *            ({@link NetworkFinal.Part Part})
	 */
	public void addNode(Part part) {
		Random r = new Random();
		next.add(part);
		weights.add(new Float(r.nextGaussian() / 2));
	}

	/**
	 * <h1>addPrepNode</h1>Adds a connection to a node but no connection weight.
	 * 
	 * @param part
	 *            ({@link NetworkFinal.Part Part})
	 */
	public void addPrepNode(Part part) {
		next.add(part);
	}

	/**
	 * <h1>addNode</h1>Adds a connection to the input node as well as the
	 * specified connection weight.
	 * 
	 * @param part
	 *            ({@link NetworkFinal.Part Part})
	 * @param con
	 *            (Float)
	 */
	public void addNode(Part part, Float con) {
		next.add(part);
		weights.add(con);
	}

	/**
	 * <h1>removeNode</h1>Removes specified node and appropriate connection
	 * weight.
	 * 
	 * @param part
	 *            ({@link NetworkFinal.Part Part})
	 */
	public void removeNode(Part part) {
		int ii = 0;
		for (int i = 0; i < next.size(); i++) {
			if (next.get(i).equals(part)) {
				ii = i;
				break;
			}
		}
		weights.remove(ii);
		next.remove(ii);
	}

	/**
	 * <h1>mutate</h1>Mutates the node on a per connection basis. Connections
	 * can not be added or removed.
	 * 
	 * @param mutationAmount
	 *            (Float) - Specifies the factor by which the mutation is
	 *            multiplied.
	 * @param mutationChance
	 *            (Float) - Specifies the chance of mutation occurring.
	 * @param conWeightAllowance
	 *            (Float) - Specifies the maximum spread of a connection weight.
	 * @see ({@link DataCenter.Bridge Bridge})
	 */
	public void mutate(Float mutationAmount, Float mutationChance, Float conWeightAllowance) {
		for (int i = 0; i < weights.size(); i++) {
			if (new Float(Math.random()) > mutationChance) {
				weights.set(i, weights.get(i) * mutationAmount);
			}
			if (weights.get(i) > conWeightAllowance || weights.get(i) < -conWeightAllowance) {
				weights.set(i, (weights.get(i) * 0.9f));
			}
		}
	}

	/**
	 * <h1>becomeDefector</h1>Doesn't really work as suggested. Instead it sets
	 * all connection weights to -1.
	 */
	public void becomeDefector() {
		for (int i = 0; i < weights.size(); i++) {
			weights.set(i, -1f);
		}
	}

	/**
	 * <h1>setNext</h1>Used to recreate neural network. No connection weights
	 * are initialized prior to or during this stage.
	 * 
	 * @param next
	 *            (ArrayList<{@link NetworkFinal.Part Part}>) - Connection
	 *            nodes.
	 */
	public void setNext(ArrayList<Part> next) {
		this.next.clear();
		this.next = (ArrayList<Part>) next.clone();
	}

	/**
	 * <h1>setWeights</h1>Sets connection weights.
	 * 
	 * @param weights
	 *            (ArrayList<Float>)
	 */
	public void setWeights(ArrayList<Float> weights) {
		this.weights.clear();
		this.weights = (ArrayList<Float>) weights.clone();
	}

	/**
	 * <h1>getVal</h1>Returns the pass-through function value of the sum of
	 * input. Used to get the output from output node.
	 * 
	 * @return Float
	 */
	public Float getVal() {
		passThroughFunction();
		return this.input;
	}

	// PRIVATE METHODS

	/**
	 * <h1>passThroughFunction</h1>Passes the sum of inputs through the
	 * function.
	 */
	private void passThroughFunction() {

		Float tel = new Float(2 * (Math.pow((1 + Math.exp(-input)), -1) - 0.5));

		input = tel;
	}

	/**
	 * <h1>reset</h1>Resets the sum of inputs to 0. Must be triggered every
	 * generation for correct simulation logic.
	 */
	private void reset() {
		input = 0f;
	}

	/**
	 * <h1>showWeights</h1>Displays connection weights.
	 */
	public void showWeights() {
		for (Float f : weights) {
			System.out.println(f);
		}
	}

	/**
	 * <h1>output</h1>Returns the output value for each connected node. Used in
	 * troubleshooting
	 * 
	 * @param i
	 *            (int) - Specifies node location in array.
	 * @return Float
	 */
	private Float output(int i) {
		return weights.get(i) * input;
	}

	/**
	 * <h1>mutate</h1>Performs the mutation of a given connection.
	 * 
	 * @param i
	 *            (int) - Specifies connection location in array.
	 * @param mutate
	 *            (Float) - Specifies factor by which mutation value is
	 *            multiplied.
	 * @return Float
	 */
	private Float mutate(int i, Float mutate) {
		Random r = new Random();
		Float tt = new Float(r.nextGaussian() * mutate);
		return weights.get(i) + tt;
	}

	/**
	 * <h1>getCon</h1>Returns connections.
	 * 
	 * @return ArrayList<Float>
	 */
	public ArrayList<Float> getCon() {
		return (ArrayList<Float>) this.weights.clone();
	}

	/**
	 * <h1>getCons</h1>Returns connections.
	 * 
	 * @return Float[]
	 */
	public Float[] getCons() {
		Float[] cons = new Float[weights.size()];
		for (int i = 0; i < cons.length; i++) {
			cons[i] = weights.get(i);
		}

		return cons;
	}

	/**
	 * <h1>sett</h1>
	 * 
	 * @param con
	 */
	public void sett(ArrayList<Float> con) {
		weights = (ArrayList<Float>) con.clone();
	}

	public void getWeights() {
		for (Float f : weights) {
			System.out.println(f);
		}
	}

	/**
	 * <h1>partialCopy</h1>New part is created but references are copied. Bad
	 * don't use unless you are certain thisis wanted.
	 * 
	 * @return Part
	 */
	public Part partialCopy() {
		Part part = new Part();
		part.setWeights(weights);
		return part;
	}
}
