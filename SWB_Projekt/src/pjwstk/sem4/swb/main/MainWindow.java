package pjwstk.sem4.swb.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pjwstk.sem4.swb.main.SerialComm.SerialWriter;

public class MainWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	protected final static String BUTTON_START_GAME = "Start game !";
	protected final static String BUTTON_INC_TEXT = "+";
	protected final static String BUTTON_CONTROL_PANEL_TEXT = "Control panel";
	protected final static String BUTTON_BACK = "<< back";

	private final static int SCORE_FONT_SIZE = 120;
	private final static int TIME_FONT_SIZE = 30;

	protected final static Dimension WINDOW_DIMENSION = new Dimension(600, 600);
	protected final static Dimension BUTTON_INC_DIMENSION = new Dimension(200,
			200);
	protected final static Dimension GAME_SCORE_LABEL_DIMENSION = new Dimension(
			100, 100);
	protected final static Dimension TIME_LAABEL_DIMENSION = new Dimension(100,
			100);
	protected final static Dimension BOX_LAYOUT_DIMENSION = new Dimension(
			(int) BUTTON_INC_DIMENSION.getWidth(),
			(int) WINDOW_DIMENSION.getHeight());

	protected static boolean isVisible = true;

	protected JLabel gameScoreLabel = null;
	protected JLabel timeGameLabel = null;

	protected ControlPanelController controlPanelController = null;
	private GameController gameController = null;

	public MainWindow() {
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
		String sorce_name = ((JButton) e.getSource()).getName();

		if (sorce_name.equals(BUTTON_START_GAME)) {
			String[] options = { "yes", "no" };
			int n = JOptionPane.showOptionDialog(getContentPane(),
					"Are you ready ?", "Game start !",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
					null, options, options[0]);

			if (n == JOptionPane.YES_OPTION) {
				writeLine(Protocol.GREETINGS);
				setGameScore("0");
				setGamePanel();
			} else {
				return;
			}
		} else if (sorce_name.equals(BUTTON_CONTROL_PANEL_TEXT)) {
			setControlPanel();
		} else {
			JOptionPane.showMessageDialog(null, "Unknown event source !",
					"Error ocured", JOptionPane.ERROR_MESSAGE);
		}

	}

	/*
	 * 
	 * GAME SECTION
	 */

	private void setGamePanel() {

		JButton backButton = new JButton(BUTTON_BACK);
		backButton.setName(BUTTON_BACK);
		backButton.addActionListener(this.gameController);

		gameScoreLabel
				.setFont(new Font("SansSerif", Font.BOLD, SCORE_FONT_SIZE));

		JButton incButton = new JButton(BUTTON_INC_TEXT);
		incButton.setAlignmentX(CENTER_ALIGNMENT);
		incButton.setName(BUTTON_INC_TEXT);
		incButton.setPreferredSize(BUTTON_INC_DIMENSION);
		incButton.addActionListener(this.gameController);

		timeGameLabel.setAlignmentX(CENTER_ALIGNMENT);
		timeGameLabel.setFont(new Font("SansSerif", Font.BOLD, TIME_FONT_SIZE));
		timeGameLabel.setText("0:30");

		Box buttonVBox = new Box(BoxLayout.Y_AXIS);
		buttonVBox.add(backButton);
		buttonVBox.add(Box.createVerticalGlue());

		Box northHorizontalBox = new Box(BoxLayout.X_AXIS);
		northHorizontalBox.add(buttonVBox);
		northHorizontalBox.add(Box.createHorizontalGlue());
		northHorizontalBox.add(gameScoreLabel);
		northHorizontalBox.add(Box.createHorizontalGlue());

		JPanel gamePanel = new JPanel(new BorderLayout());
		gamePanel.add(northHorizontalBox, BorderLayout.NORTH);
		gamePanel.add(incButton, BorderLayout.CENTER);
		gamePanel.add(timeGameLabel, BorderLayout.SOUTH);

		getContentPane().removeAll();
		getContentPane().add(gamePanel);
		validate();
		northHorizontalBox
				.add(Box.createHorizontalStrut(backButton.getWidth()));
		validate();
		setVisible(true);
	}

	public void setGameScore(String score) {

		this.gameScoreLabel.setText(score);

	}

	public String getGameScore() {
		return this.gameScoreLabel.getText();
	}

	

	/*
	 * 
	 * CONTROL PANEL SECTION
	 */

	private void setControlPanel() {
		writeLine(Protocol.CONTROL_ENTER);

		getContentPane().removeAll();
		ControllPanel cp = new ControllPanel(this);
		getContentPane().add(cp);
		validate();
		setVisible(isVisible);

	}

	private class ControlPanelController implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String sorce_name = ((JButton) e.getSource()).getName();
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
