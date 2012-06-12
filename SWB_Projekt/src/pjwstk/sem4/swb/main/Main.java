package pjwstk.sem4.swb.main;

public class Main {
	
	
	
	
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
		
		Window window = new Window();
		window.createHelloWindow();
		window.setVisible(true);
	}
	
}
