package pjwstk.sem4.swb.main;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

	protected final static String BUTTON_BACK = "<< back";
	protected final static String BUTTON_INC_TEXT = "+";

	public GamePanel() {
		JButton backButton = new JButton(BUTTON_BACK);
		backButton.setName(BUTTON_BACK);
		backButton.addActionListener(gameController);

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

	private ActionListener gameController = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			String sorce_name = ((JButton) e.getSource()).getName();

			if (sorce_name.equals(BUTTON_INC_TEXT)) {
				int curScore = Integer.parseInt(getGameScore());

				writeLine(Protocol.ADD_POINT);
				setGameScore(curScore + 1 + "");

			} else if (sorce_name.equals(BUTTON_BACK)) {
				String[] options = { "yes", "no" };
				int n = JOptionPane.showOptionDialog(getContentPane(),
						"Are you sure you want to leave ?", "Warning !",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
						null, options, options[1]);

				if (n == JOptionPane.YES_OPTION)
					back();
				else
					return;
			} else {
				JOptionPane.showMessageDialog(null, "Unknown event source !",
						"Error ocured", JOptionPane.ERROR_MESSAGE);
			}
		}

	};
}
