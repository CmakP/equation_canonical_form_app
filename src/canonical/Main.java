/**
 * Project: Siamak_Pourian_Test_Task_Solution
 * File: Main.java
 * Date: May 2, 2017
 * Time: 5:18:50 PM
 */
package canonical;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import canonical.ui.AppFrame;

/**
 * @author Siamak Pourian
 *
 * Main Class - Driver class for the application
 */
public class Main {

	/**
	 * Default Constructor
	 */
	public Main() {
	}

	/**
	 * Launch the Equation Transformer Application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new Main().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create a user interface for the application
	 */
	private void run() {
		createUI();
	}
	
	/**
	 * GUI components run on a different thread
	 */
	public void createUI() {
		setLookAndFeel();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppFrame frame = new AppFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Sets the Nimbus look and feel for the application GUI based on the environment
	 * it's ran
	 */
	private void setLookAndFeel() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, use the default.
		}
	}
}
