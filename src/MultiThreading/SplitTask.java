package MultiThreading;

import java.util.ArrayList;

import Cells.Cell;
import DataCenter.Bridge;

/**
 * <h1>SplitTask</h1>Class manages multi0threading logic and instances.
 * 
 * @author James F. Taylor
 *
 */
public class SplitTask {
	private ArrayList<Threading> threads;
	private ArrayList<Thread> running;

	public void splitTasks(ArrayList<Cell> cells, int threadCount) {
		if (threadCount > 1) {
			Float f = new Float(cells.size() / threadCount);
			threads = new ArrayList<Threading>();
			int temp = 0;
			for (int i = 0; i < threadCount; i++) {
				int start = (int) (i * f);
				int end = (int) ((i + 1) * f);
				if (i == threadCount - 1) {
					end = cells.size();
				}
				threads.add(new Threading(start, end, cells));
			}
		}
	}

	public void threadTask(int step) {
		running = new ArrayList<Thread>();
		for (Threading t : threads) {
			running.add(t.runStep(step));
		}
		for (Thread t : running) {
			t.start();
		}
		for (Thread t : running) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
