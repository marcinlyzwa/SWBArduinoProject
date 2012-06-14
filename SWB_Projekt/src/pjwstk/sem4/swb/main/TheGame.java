package pjwstk.sem4.swb.main;

public class TheGame {
	
	
	
	
	public static void main(String[] args) {
		
		/*
		 * Creating new connection on selected port
		 */
		
//		try {
//			(new TwoWaySerialComm()).connect("COM20");
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.exit(-1);
//		}
		
		MainWindow window = new MainWindow();
		window.createHelloWindow();
		window.setVisible(true);
	}
	
}
