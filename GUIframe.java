import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Window.Type;

public class GUIframe {

	private JFrame frame;
	private JTextField hexInput;
	private JTextField binInput;
        Translation translation = new Translation();
        private JLabel Answer;
        private JLabel errorMsg;
        public String output = "";

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIframe window = new GUIframe();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUIframe() {
		initialize();
	}
        
         

	/**
	 * Initialize the contents of the frame.
         * 
         * 
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton CalculateButton = new JButton("Calculate");
		CalculateButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		CalculateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                            
                            String hex = hexInput.getText();
                            String bin = binInput.getText();
                            
                            if(hex.isEmpty() && bin.isEmpty()){
                                errorMsg.setText("Error: no input found");
                            }
                            else if(!hex.isEmpty()){
                                if(!bin.isEmpty()){
                                    errorMsg.setText("Error: multiple input found. Use Hex or Bin");
                                }
                                else if(hex.length() != 8){
                                    errorMsg.setText("Error: Only accepting 8 digit Hexadecimals");
                                }
                                else if(!hex.matches("^[0-9a-fA-F]+$")){
                                    errorMsg.setText("Error: Input is not a valid Hexadecimal");
                                }
                                else{
                                    bin = translation.convH2B(hex);
                                    output = translation.Convert(bin);
                                    Answer.setText(output);
                                    errorMsg.setText("");
                                }
                            }
                            else{
                                if(bin.length() != 32){
                                    errorMsg.setText("Error: Only accepting 32bit Binary");
                                }
                                else if (!bin.matches("^[0-1]+$")){
                                    errorMsg.setText("Error: Input is not a valid Binary");
                                }
                                else {
                                    output = translation.Convert(bin);
                                    Answer.setText(output);
                                    errorMsg.setText("");
                                }
                            }
                            
			}
                        
		});
		CalculateButton.setBounds(132, 209, 108, 21);
		frame.getContentPane().add(CalculateButton);
		
		JButton SavetoFileButton = new JButton("Save to File");
		SavetoFileButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		SavetoFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                            translation.SavetoFile(output);
			}
		});
		SavetoFileButton.setBounds(250, 209, 141, 21);
		frame.getContentPane().add(SavetoFileButton);
		
		JLabel HexLabel = new JLabel("Hex Input");
		HexLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		HexLabel.setBounds(47, 58, 85, 21);
		frame.getContentPane().add(HexLabel);
		
		JLabel BinLabel = new JLabel("Bin Input");
		BinLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		BinLabel.setBounds(47, 102, 85, 19);
		frame.getContentPane().add(BinLabel);
		
		JLabel ResultLabel = new JLabel("Result: ");
		ResultLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		ResultLabel.setBounds(47, 142, 85, 21);
		frame.getContentPane().add(ResultLabel);
		
		hexInput = new JTextField();
		hexInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		hexInput.setBounds(142, 59, 238, 19);
		frame.getContentPane().add(hexInput);
		hexInput.setColumns(10);
                
		
		binInput = new JTextField();
		binInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		binInput.setColumns(10);
		binInput.setBounds(142, 102, 238, 19);
		frame.getContentPane().add(binInput);
		
		Answer = new JLabel("");
                Answer.setFont(new Font("Tahoma", Font.PLAIN, 14));
                Answer.setBounds(142, 143, 400, 19);
                frame.getContentPane().add(Answer);
                
                errorMsg = new JLabel("");
                errorMsg.setFont(new Font("Tahoma", Font.PLAIN, 14));
                errorMsg.setForeground(Color.RED);
                errorMsg.setBounds(47, 176, 400, 19);
                frame.getContentPane().add(errorMsg);
                
                
	}
}