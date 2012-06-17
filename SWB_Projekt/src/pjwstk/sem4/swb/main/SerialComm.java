package pjwstk.sem4.swb.main;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observable;

/**
 * Based on example from RxTx web site.
 */
public class SerialComm extends Observable implements SerialPortEventListener {
	private OutputStream out;
	private InputStream in;
	private byte[] buffer = new byte[1024];

	public SerialComm() {
		super();
	}

	void connect(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier
				.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			throw new Exception("Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open(this.getClass().getName(),
					2000);

			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

				in = serialPort.getInputStream();
				out = serialPort.getOutputStream();

				serialPort.addEventListener(this);
				serialPort.notifyOnDataAvailable(true);

			} else {
				throw new Exception(
						"Only serial ports are handled by this program");
			}
		}
	}

	public void serialEvent(SerialPortEvent arg0) {
		int data;

		try {
			int len = 0;
			while ((data = in.read()) > -1) {
				if (data == '\n') {
					break;
				}
				buffer[len++] = (byte) data;
			}

			setChanged();
			notifyObservers(new String(buffer, 0, len));
		} catch (IOException e) {
			setChanged();
			notifyObservers(e);
		}
	}
	
	public void print(String command) throws IOException {
		out.write(command.getBytes());
	}
	
	public void println(String command) throws IOException {
		print(command + '\n');
	}

	// while (true) {
	// for (int i = 0; i < 3; ++i)
	// try {
	// int in = System.in.read();
	// if (in == '\n') {
	// ++counter;
	// TwoWaySerialComm.out
	// .write((counter.toString() + "\n")
	// .getBytes());
	// System.out.println((counter).toString() + "\n");
	// Thread.sleep(100);
	// }
	// } catch (IOException | InterruptedException e) {
	// e.printStackTrace();
	// System.exit(-1);
	// }
	// try {
	// TwoWaySerialComm.out.write(("OVER\n").getBytes());
	// System.out.println("OVER");
	// TwoWaySerialComm.out.write(("You lose!!!\n").getBytes());
	// System.out.println("You lose!!!");
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
}