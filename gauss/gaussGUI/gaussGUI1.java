import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.LineBorder;

public class gaussGUI1 extends JFrame {

	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gaussGUI1 frame = new gaussGUI1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public gaussGUI1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(251, 0, 44, 28);
		contentPane.add(formattedTextField);
		
		final JFormattedTextField formattedTextField_1 = new JFormattedTextField();
		formattedTextField_1.setBounds(251, 34, 44, 28);
		contentPane.add(formattedTextField_1);
		
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					final int numEq,numVar;
					numEq=Integer.parseInt(formattedTextField.getText());
					numVar=Integer.parseInt(formattedTextField_1.getText());
					/*int[][] a = new int[numEq][numVar+1];
					for(int i=0;i<numEq;i++){
						for(int j=0;j<numVar+1;j++){
							JFormattedTextField g=new JFormattedTextField();
							g.setBounds(251, 34, 44, 28);
							contentPane.add(g);
							a[i][j]=Integer.parseInt(g.getText());
						}
					}*/
					gaussGUI2 ns= new gaussGUI2();
					ns.newScreen(numEq, numVar);
				}
				catch(Exception e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(null,"Please enter valid number");
				}
			}
		});
		btnNewButton.setBounds(6, 62, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblPleaseEnterThe = new JLabel("Please enter the number of equations: ");
		lblPleaseEnterThe.setBounds(6, 6, 254, 16);
		contentPane.add(lblPleaseEnterThe);
		
		JLabel lblPleaseEnterThe_1 = new JLabel("Please enter the number of variables : ");
		lblPleaseEnterThe_1.setBounds(6, 34, 254, 16);
		contentPane.add(lblPleaseEnterThe_1);
		
		
	}
}
