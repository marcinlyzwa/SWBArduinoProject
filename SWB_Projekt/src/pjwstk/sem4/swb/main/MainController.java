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
			// TODO: obs�uga komunikatu
		} else if (command instanceof Exception) {
			// TODO: obs�uga zerwania po��czenia
		} else {
			// TODO: co� innego? pewnie null - trzeba to obs�u�y�
		}
	}
}
