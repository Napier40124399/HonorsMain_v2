package Components;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * <h1>FileChooser</h1>This class was not made by James F. Taylor. It creates a
 * file chooser and returns paths.
 * 
 * @author Exterior
 *
 */
public class FileChooser {
	private JFrame frame;

	public FileChooser() {
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		BringToFront();
	}

	public File getFolder(String p) {
		JFileChooser fc = new JFileChooser(p);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)) {
			frame.setVisible(false);
			return fc.getSelectedFile();
		} else {
			System.out.println("Next time select a directory.");
			System.exit(1);
		}
		return null;
	}

	public File getFile(String p) {
		JFileChooser fc = new JFileChooser(p);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)) {
			frame.setVisible(false);
			return fc.getSelectedFile();
		} else {
			System.out.println("Next time select a file.");
			System.exit(1);
		}
		return null;
	}

	private void BringToFront() {
		frame.setExtendedState(JFrame.ICONIFIED);
		frame.setExtendedState(JFrame.NORMAL);

	}
}
