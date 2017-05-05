package CLI;

import Scenario.StealthSim;

public class MainCommand {

	private static StealthSim sim;
	public static void main(String[] args) {
		sim = new StealthSim(args);
	}

}
