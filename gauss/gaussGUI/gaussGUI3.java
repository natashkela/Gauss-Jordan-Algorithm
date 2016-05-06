import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;

public class gaussGUI3 {

	private JFrame frame;
	public static String fsolve;
	/**
	 * Launch the application.
	 */
	public static void newScreen1(String solution) {
		fsolve = solution;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gaussGUI3 window = new gaussGUI3();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public gaussGUI3() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea lblNewLabel = new JTextArea(fsolve);
		lblNewLabel.setBounds(6, 6, 400, 400);
		frame.getContentPane().add(lblNewLabel);
	}

}
