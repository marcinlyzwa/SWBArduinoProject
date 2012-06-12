package pjwstk.sem4.swb.main;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;


public class ControllPanel extends JPanel {
	
	/**
	 * 
	 */
	private Window window = null;
	
	private static final long serialVersionUID = 1L;
	private JSlider slider = null;
	private JLabel angleVerriable = null;
	private JTextArea textArea = null;
	private JTextField textField = null;
	/**
	 * Create the panel.
	 * @param window 
	 */
	public ControllPanel(final Window window) {
		this.window = window;
		
		JLabel lblConsol = new JLabel("consol:");
		
		this.textArea = new JTextArea();
		textArea.setEditable(false);
		this.textArea.setLineWrap(true);
		
		JButton btnSend = new JButton("send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textField.getText();
				if(!text.equals(" ")) {
					appendTextToConsol("<<" + text);
					textField.setText("");
					
					window.writeLine(text);
					
				}
			}
		});
		
		this.angleVerriable = new JLabel();
		this.setAngle(0);
		
		this.slider = new JSlider();
		
		this.slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(((JSlider) (e.getSource())).getValue() != 0)
				setAngle(((JSlider) (e.getSource())).getValue());
			}
		});
		
		this.slider.setMaximum(180);
		
		JLabel lblSerwo = new JLabel("serwo:");
		
		JLabel lblAngle = new JLabel("angle:");
		
		JButton btnTurn = new JButton("turn");
		btnTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendTextToConsol("Servo will turn about: " + getAngle());
				
				String line = Protocol.SERVO + getAngle();
				window.writeLine(line);
				
			}
		});
		
		JLabel lblLed = new JLabel("led:");
		
		JCheckBox chckbxRed = new JCheckBox("red");
		
		JCheckBox chckbxGreen = new JCheckBox("green");
		
		JButton btnOn = new JButton("on");
		
		JButton btnNewButton = new JButton("detect");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendTextToConsol("Detection: ON...");
				
				
				String line = Protocol.DETECT;
				window.writeLine(line);
			}
		});
		
		JButton button = new JButton("<<back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.back();
			}
		});
		
		this.textField = new JTextField();
		this.textField.setColumns(10);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(chckbxRed)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(chckbxGreen))
								.addComponent(btnOn)
								.addComponent(lblLed))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(144)
									.addComponent(lblSerwo)
									.addGap(76)
									.addComponent(lblAngle)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(angleVerriable, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnTurn)
										.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(25))))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblConsol)
							.addGap(156)
							.addComponent(button))
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(textField, Alignment.LEADING)
							.addComponent(textArea, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
							.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addComponent(btnNewButton)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSend))))
					.addGap(0))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblConsol))
						.addComponent(button))
					.addGap(11)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnSend))
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSerwo)
						.addComponent(lblLed)
						.addComponent(lblAngle)
						.addComponent(angleVerriable))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnTurn))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(chckbxRed)
								.addComponent(chckbxGreen))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnOn)))
					.addContainerGap(19, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
	
	
	public void setAngle(int value) {
		this.angleVerriable.setText(value + "");
	}
	
	public int getAngle() {
		return Integer.parseInt(angleVerriable.getText());
	}
	
	public void appendTextToConsol(String line) {
		this.textArea.append(line+"\n");
	}
}
