package pjwstk.sem4.swb.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import pjwstk.sem4.swb.main.TwoWaySerialComm.SerialWriter;

public class Window extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	protected final static String BUTTON_START_GAME = "Start game !";
	protected final static String BUTTON_INC_TEXT = "+";
	protected final static String BUTTON_CONTROL_PANEL_TEXT = "Control panel";
	protected final static String BUTTON_BACK = "<< back";
	
	private final static int SCORE_FONT_SIZE = 80;
	private final static int TIME_FONT_SIZE = 30;
	
	protected final static Dimension WINDOW_DIMENSION = new Dimension(600, 600);
	protected final static Dimension BUTTON_INC_DIMENSION = new Dimension(200, 200);
	protected final static Dimension GAME_SCORE_LABEL_DIMENSION = new Dimension(100, 100);
	protected final static Dimension TIME_LAABEL_DIMENSION = new Dimension(100, 100);
	protected final static Dimension BOX_LAYOUT_DIMENSION = new Dimension((int)BUTTON_INC_DIMENSION.getWidth(), (int)WINDOW_DIMENSION.getHeight());
	
	protected static boolean isVisible = true;
	 
	
	protected JLabel gameScoreLabel = null;
	protected JLabel timeGameLabel = null;
	
	protected ControlPanelController controlPanelController = null;
	private GameController gameController = null;
	
	public Window() {
		super("Game on !");
		
		this.gameScoreLabel = new JLabel();
		this.timeGameLabel = new JLabel();
		
		this.gameController = new GameController();
	}
	
	
	
	public void createHelloWindow() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(WINDOW_DIMENSION);
		setMinimumSize(WINDOW_DIMENSION);
		
		getContentPane().add(createStartPanel());
		
		setVisible(isVisible);
		pack();
	}
	
	private JPanel createStartPanel() {
		JPanel startPanel = new JPanel();
		startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
		
			JButton start_button = new JButton(BUTTON_START_GAME);
					start_button.setAlignmentX(Component.CENTER_ALIGNMENT);
					start_button.setName(BUTTON_START_GAME);
					start_button.addActionListener(this);
			
			JButton control_button = new JButton(BUTTON_CONTROL_PANEL_TEXT);
					control_button.setAlignmentX(Component.CENTER_ALIGNMENT);
					control_button.setName(BUTTON_CONTROL_PANEL_TEXT);
					control_button.addActionListener(this);
		
		startPanel.add(start_button);
		startPanel.add(control_button);
		
		return startPanel;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String sorce_name = ((JButton)e.getSource()).getName();
		
		if(sorce_name.equals(BUTTON_START_GAME)) {
			String[] options = {"yes", "no"};
			int n = JOptionPane.showOptionDialog(getContentPane(), "Are you ready ?", "Game start !", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			
			if(n == JOptionPane.YES_OPTION) {
				writeLine(Protocol.GREETINGS);
				setGameScore("0");
				setGamePanel();
			} else {
				return;
			}
		} else if(sorce_name.equals(BUTTON_CONTROL_PANEL_TEXT)) {
			setControlPanel();
		} else {
			JOptionPane.showMessageDialog(null, "Unknown event source !", "Error ocured", JOptionPane.ERROR_MESSAGE);
		}
			
	}
	
	
	/*
	 * 
	 * GAME SECTION
	 * 
	 */
	
	
	private void setGamePanel() {
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new BorderLayout());
		Box box = new Box(BoxLayout.Y_AXIS);
		
		box.setPreferredSize(BOX_LAYOUT_DIMENSION);
		
		gamePanel.setAlignmentX(CENTER_ALIGNMENT);
		
		JButton back_button = new JButton(BUTTON_BACK);
				back_button.setName(BUTTON_BACK);
				back_button.setAlignmentX(CENTER_ALIGNMENT);
				back_button.addActionListener(this.gameController);
				
		box.add(back_button);
		
		box.add(Box.createVerticalGlue());
		
			this.gameScoreLabel.setAlignmentX(CENTER_ALIGNMENT);
			this.gameScoreLabel.setFont(new Font("SansSerif", Font.BOLD, SCORE_FONT_SIZE));
			this.gameScoreLabel.setSize(GAME_SCORE_LABEL_DIMENSION);
		box.add(this.gameScoreLabel);
			
			JButton inc_button = new JButton(BUTTON_INC_TEXT);
					inc_button.setAlignmentX(CENTER_ALIGNMENT);
					inc_button.setName(BUTTON_INC_TEXT);
					inc_button.setPreferredSize(BUTTON_INC_DIMENSION);
					inc_button.addActionListener(this.gameController);
		box.add(inc_button);
			
			this.timeGameLabel.setAlignmentX(CENTER_ALIGNMENT);
			this.timeGameLabel.setFont(new Font("SansSerif", Font.BOLD, TIME_FONT_SIZE));
			this.timeGameLabel.setSize(TIME_LAABEL_DIMENSION);
			this.timeGameLabel.setText("0:30");
		box.add(this.timeGameLabel);	
		
		box.add(Box.createVerticalGlue());
		
		gamePanel.add(box, BorderLayout.CENTER);
		
		getContentPane().removeAll();
		getContentPane().add(gamePanel);
		validate();
		setVisible(true);
	}
	
	public void setGameScore(String score) {
		
		this.gameScoreLabel.setText(score);
		
	}
	
	public String getGameScore() {
		return this.gameScoreLabel.getText();
	}
	
	
	private class GameController implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String sorce_name = ((JButton)e.getSource()).getName();
			
			if(sorce_name.equals(BUTTON_INC_TEXT)) {
				int curScore = Integer.parseInt(getGameScore());
				
				writeLine(Protocol.ADD_POINT);
				setGameScore(curScore+1 + "");
				
			} else if(sorce_name.equals(BUTTON_BACK)) {
				String[] options = {"yes", "no"};
				int n = JOptionPane.showOptionDialog(getContentPane(), "Are you sure you want to leave ?", "Warning !", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
				
				if(n == JOptionPane.YES_OPTION)
					back();
				else
					return;
			} else {
				JOptionPane.showMessageDialog(null, "Unknown event source !", "Error ocured", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		
	}
	
	
	/*
	 * 
	 * CONTROL PANEL SECTION
	 * 
	 */
	
	
	private void setControlPanel() {
		writeLine(Protocol.CONTROL_ENTER);
		
		getContentPane().removeAll();
		ControllPanel cp = new  ControllPanel(this);
		getContentPane().add(cp);
		validate();
		setVisible(isVisible);
		
	}
	
	private class ControlPanelController implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String sorce_name = ((JButton)e.getSource()).getName();
		}
		
	}
	
	
	
	
	public void writeLine(String line) {
		SerialWriter.write(line);
	}
	
	
	public void back() {
		
		writeLine(Protocol.GAME_OVER);
		
		getContentPane().removeAll();
		getContentPane().add(createStartPanel());
		validate();
		setVisible(isVisible);
	}
	
}
