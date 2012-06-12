package pjwstk.sem4.swb.main;

public class Protocol {
	
	/*
	 * GAME PROTOCOL
	 */
	public final static String GREETINGS = "GREETINGS"; // rozpoczyna gre
	public final static String GAME_OVER = "GAME_OVER"; // zakancza gre && zakoncz prace w panelu && po odebraniu z arduino restartuje wszystko (reset).
	public final static String GAME_PAUSE = "GAME_PAUSE"; // pauzuje gre
	public final static String GAME_RETURN = "GAME_RETURN"; // powrot do rozpoczetej gry
	
	public final static String ADD_POINT = "ADD_POINT"; // dodaje punkt
	public final static String MINUS_POINT = "MINUS_POINT"; //odejmuje punkt
	
	/*
	 * CONTROL PANEL PROTOCOL
	 */
	public final static String CONTROL_ENTER = "CONTROL_ENTER"; // wejscie do panelu kontrolnego
	
	public final static String SERVO = "SERVO_"; // obreca servem !! UWAGA !! SERVO_15 - obraca servo o 15 stopni ;-)
	public final static String LED_ON = "LED_ON_"; // w³¹ccz odpowiedni kolor diody !! UWAGA !! LED_ON_RED - wlacza red led, analoicznie green
	public final static String LED_OF = "LED_OF_"; // analogicznie wylaczanie
	public final static String DETECT = "DETECT_"; // wlacza szukanie czegos przez wallyego na podanej odleglosci np. DETECT_15 szuka czegos w okolicach 15 cm.
	public final static String WRITE = "WRITE"; //bedzie czytal to co pisze sie na konsoli
	public final static String WRITE_END = "WRITE_END"; // konczy czytanie
	
	
	public final static String EXIT = "EXIT"; //jak zamkniemy program to arduino ????
}
