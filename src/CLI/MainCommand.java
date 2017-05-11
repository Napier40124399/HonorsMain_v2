package CLI;

import Scenario.StealthSim;

public class MainCommand {

	private static StealthSim sim;
	private static String[] temp = { "true", "true", "false", "100", "8", "1", "150", "51", "100", "10", "0", "1.5",
			"1", "0", "0", "0.03", "C:\\Users\\James\\Documents\\SimulationResults\\dump", "6-2" };

	/**
	 * <h1>Main</h1>Launcher for command interface. Takes in simulation parameters as follows:
	 * <ul>
	 * <li>0) synch (bool) - true/false</li>
	 * <li>1) taurus (bool) - true/false</li>
	 * <li>2) dyn top (bool) - true/false</li>
	 * <li>3) cellQ (int) - numbers, no letters, no decimals</li>
	 * <li>4) threads (int) - numbers, no letters, no decimals</li>
	 * <li>5) distance (int) - numbers, no letters, no decimals</li>
	 * <li>6) gens (int) - numbers, no letters, no decimals</li>
	 * <li>7) iterations (int) - numbers, no letters, no decimals</li>
	 * <li>8) save every (int) - numbers, no letters, no decimals</li>
	 * <li>9) max nodes (int) - numbers, no letters, no decimals</li>
	 * <li>10) cell type (int) - numbers, no letters, no decimals</li>
	 * <li>11) T (Float) - numbers, no letters</li>
	 * <li>12) R (Float) - numbers, no letters</li>
	 * <li>13) P (Float) - numbers, no letters</li>
	 * <li>14) S (Float) - numbers, no letters</li>
	 * <li>15) mutation (Float) - numbers, no letters</li>
	 * <li>16) save location (String) - save path use / or \ it doesn't matter
	 * in this context</li>
	 * <li>17) start top (String) - topologies must be formatted as follows;
	 * number of nodes on the layer seperated by '-' to signal a new layer, the
	 * output layer is not to be specified. For example: 6-2</li>
	 * </ul>
	 * 
	 * @param args
	 *            (String[])
	 * @see {@link Scenario.StealthSim StealthSim}
	 */
	public static void main(String[] args) {
		sim = new StealthSim(temp);
	}

}