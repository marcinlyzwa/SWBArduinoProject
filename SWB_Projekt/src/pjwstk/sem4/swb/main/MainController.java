package pjwstk.sem4.swb.main;

import java.io.IOException;

public class MainController {
	
	public MainController() throws IOException {
		SerialComm.out.write("GREETINGS".getBytes());
		
	}
	
}
