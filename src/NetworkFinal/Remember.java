package NetworkFinal;

import java.util.ArrayList;

public class Remember {
	private ArrayList<Float> memory = new ArrayList<Float>();
	private ArrayList<Float> memCur = new ArrayList<Float>();

	private ArrayList<Float> opMem = new ArrayList<Float>();
	private ArrayList<Float> opCur = new ArrayList<Float>();

	private int size;

	/**
	 * <h1>Remember</h1>Constructor, initializes memory management variables.
	 * 
	 * @param size
	 *            (int)
	 */
	public Remember(int size) {
		this.size = size;
		for (int i = 0; i < size; i++) {
			memory.add(new Float(0));
			opMem.add(new Float(0));
		}
		memCur = (ArrayList<Float>) memory.clone();
		opCur = (ArrayList<Float>) opMem.clone();
	}

	/**
	 * <h1>reset</h1>Resets the memory to default values.
	 */
	public void reset() {
		memory.clear();
		memCur.clear();
		opMem.clear();
		opCur.clear();
		for (int i = 0; i < size; i++) {
			memory.add(new Float(0));
			opMem.add(new Float(0));
		}
		memCur = (ArrayList<Float>) memory.clone();
		opCur = (ArrayList<Float>) opMem.clone();
	}

	/**
	 * <h1>getMem</h1>Returns memory as an arraylist.
	 * 
	 * @return ArrayList<Float>
	 */
	public ArrayList<Float> getMem() {
		ArrayList temp = new ArrayList<Float>();

		for (int i = 0; i < size; i++) {
			temp.add(memory.get(i));
		}
		for (int i = 0; i < size; i++) {
			temp.add(opMem.get(i));
		}
		return temp;
	}

	/**
	 * <h1>normalize</h1>Moves memory from overflow to current value.
	 */
	public void normalize() {
		memory = (ArrayList<Float>) memCur.clone();
		opMem = (ArrayList<Float>) opCur.clone();
	}

	/**
	 * <h1>getMem</h1>Returns a specific memory within the array. (Own memory
	 * only)
	 * 
	 * @param i
	 *            (int)
	 * @return Float
	 */
	public Float getMem(int i) {
		return memory.get(i);
	}

	/**
	 * <h1>save</h1>Saves the memory. (Saved as own memory)
	 * 
	 * @param mem
	 *            (Float)
	 */
	public void save(Float mem) {
		memCur.add(mem);
		memCur.remove(0);
	}

	/**
	 * <h1>saveOP</h1>Saves the memory. (Saved as opponent memory)
	 * 
	 * @param mem
	 */
	public void saveOP(Float mem) {
		opCur.add(mem);
		opCur.remove(0);
	}
}
