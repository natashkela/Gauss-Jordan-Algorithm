import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import gauss.Gauss;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class gaussGUI2 {

	private JFrame frame;
	JTextField fields [];
	public String finalSolution="";
	public static int Eq=1;
	public static int Var=2;
	public static double[] a = new double [Eq*(Var+1)];
	/**
	 * Launch the application.
	 */
	public static void newScreen(final int numEq,final int numVar) {
		 Eq = numEq;
		 Var = numVar;
		 a = new double [Eq*(Var+1)];
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gaussGUI2 window = new gaussGUI2();
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
	public gaussGUI2() {
		initialize(Eq,Var);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int numEq,int numVar) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblPleaseEnterEach = new JLabel("Please enter each coefficient in the boxes given");
		lblPleaseEnterEach.setBounds(6, 17, 312, 16);
		frame.getContentPane().add(lblPleaseEnterEach);
		
		/*for(int i=0;i<numEq*(numVar+1);i++){
				JTextField g = new JTextField();
				if(i<numVar+1){
					int n;
					g.setBounds(16+(i+1)*20	, 45, 30, 28);
				}
				else {
					g.setBounds(16+(i+1)*20	, 50, 30, 28);
				}
				frame.getContentPane().add(g);
				
		}*/
		final JPanel g = new JPanel();
		g.setLayout(null);
		g.setLayout(new GridLayout(Eq,Var+1));
		fields = new JTextField[numEq*(numVar+1)];
		
		for(int i=0;i<numEq*(numVar+1);i++){
			fields[i] = new JTextField(""+(i+1));
			g.add(fields[i]);
		}
		frame.setContentPane(g);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(int i=0;i<Eq*(Var+1);i++){
					String val = fields[i].getText();
					double d = 0;
					try {
						d = Double.parseDouble(val);
					} catch (Exception e) {
						System.err.println("Not a number:" + val);
					}
					
					System.out.println(i + ":" + d);
					//a[i] = (Integer)b;
					a[i]=d;
					System.out.println(i+":"+a[i]);
				}
				int count=0;
				double[] con = new double[Eq];
				double[][] array = new double [Eq][Var];
			    for(int z=0;z<Eq;z++){
			    	for(int j=0;j<Var+1;j++){
			    		if(Var-j!=0){
				    		array[z][j] = a[count];
				    		System.out.println(array[z][j]);
			    		}
			    		else{
			    			con[z]=a[count];
			    			System.out.println("Answers: "+con[z]);
			    		}
			    		count++;
			    	}
			    }
			    String finalSolution=Gauss.test(con, array,Eq,Var);
			    System.out.println(finalSolution);
			    gaussGUI3 ns1 = new gaussGUI3();
			    ns1.newScreen1(finalSolution);
			}
		});
		btnNewButton.setBounds(333, 249, 117, 29);
		g.add(btnNewButton);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	    
	}
}
