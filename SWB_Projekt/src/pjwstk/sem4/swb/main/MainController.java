package pjwstk.sem4.swb.main;

import java.util.Observable;
import java.util.Observer;

public class MainController implements Observer {

	private SerialComm comm;

	public MainController(SerialComm comm) {
		this.comm = comm;
		comm.addObserver(this);
		
//		initiateMainState();
		
	}

	@Override
	public void update(Observable arg0, Object command) {
		if (command instanceof String) {
			// TODO: obs³uga komunikatu
		} else if (command instanceof Exception) {
			// TODO: obs³uga zerwania po³¹czenia
		} else {
			// TODO: coœ innego? pewnie null - trzeba to obs³u¿yæ
		}
	}
}
