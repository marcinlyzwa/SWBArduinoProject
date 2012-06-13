package pjwstk.sem4.swb.main;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This version of the TwoWaySerialComm example makes use of the
 * SerialPortEventListener to avoid polling.
 * 
 */
public class TwoWaySerialComm {
	static OutputStream out;
	static InputStream in;
	static Integer counter = 0;

	public TwoWaySerialComm() {
		super();
	}

	void connect(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier
				.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open(this.getClass().getName(),
					2000);

			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

				in = serialPort.getInputStream();
				out = serialPort.getOutputStream();

				(new Thread(new SerialWriter(out))).start();

				serialPort.addEventListener(new SerialReader(in));
				serialPort.notifyOnDataAvailable(true);

			} else {
				System.out
						.println("Error: Only serial ports are handled by this example.");
			}
		}
	}

	/**
	 * Handles the input coming from the serial port. A new line character is
	 * treated as the end of a block in this example.
	 */
	public static class SerialReader implements SerialPortEventListener {
		private InputStream in;
		private byte[] buffer = new byte[1024];

		public SerialReader(InputStream in) {
			this.in = in;
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

				String komunikat = new String(buffer, 0, len);
				//PARSOWANIE TEGO CO PRZYCHODZI.
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}

	}

	/** */
	public static class SerialWriter implements Runnable {

		public SerialWriter(OutputStream out) {
			TwoWaySerialComm.out = out;
		}

		public void run() {
//			while (true) {
//				for (int i = 0; i < 3; ++i)
//					try {
//						int in = System.in.read();
//						if (in == '\n') {
//							++counter;
//							TwoWaySerialComm.out
//									.write((counter.toString() + "\n")
//											.getBytes());
//							System.out.println((counter).toString() + "\n");
//							Thread.sleep(100);
//						}
//					} catch (IOException | InterruptedException e) {
//						e.printStackTrace();
//						System.exit(-1);
//					}
//				try {
//					TwoWaySerialComm.out.write(("OVER\n").getBytes());
//					System.out.println("OVER");
//					TwoWaySerialComm.out.write(("You lose!!!\n").getBytes());
//					System.out.println("You lose!!!");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
		}
		
		public static void write(String line) {
			System.out.println("##### wysylam: " + line + " #####");
//			try {
////				TwoWaySerialComm.out.write(line.getBytes());
//			} catch (IOException e) {
//				System.err.println("##### Error occured ! Sending message failed. #####");
//				e.printStackTrace();
//			}
		}
		
	}
	
}