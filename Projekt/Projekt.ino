#include <LiquidCrystal.h>
#include <Servo.h>

LiquidCrystal lcd(8, 9, 4, 5, 6, 7);
Servo servo;
char napis[16];
int vals[4] = {0, 0, 0, 0};


void guzik() {
  Serial.println("GUZIK!");
}


void setup() {
  // Hardware init
  // USB/COM
  Serial.begin(9600);
  // Diode
  pinMode(13, OUTPUT);
  // Button
  pinMode(A0, INPUT);
  // Servo timer
  pinMode(11, OUTPUT);
  servo.attach(11);
  servo.write(0);
  // LCD Screen
  lcd.begin(16,2);
  
  // Initiating connection with PC
  lcd.clear();
  lcd.print(" Waiting for a  ");
  lcd.setCursor(0,1);
  lcd.print(" USB connection ");
  
  readLine(napis);
  while(!isConnOpening(napis))
    readLine(napis);

  // Starting the main program
  lcd.clear();
  lcd.print("    The Game    ");
  lcd.setCursor(0,1);
  lcd.print("    Welcome!    ");
  delay(2000);

  attachInterrupt(0, guzik, FALLING);
  
  lcd.clear();
  startNewGame();
}

void loop() {
  readLine(napis);  

  if (isGameOver(napis)) {
    servo.write(0);

    lcd.clear();
    lcd.setCursor(0,0);
    readLine(napis);
    lcd.print(napis);

    lcd.setCursor(0,1);    
    startNewGame();
  } 
  else if (isSerwoOrder(napis)) {
    servo.write(extractServoValue(napis));
  } 
  else {
    vals[3] = vals[2];
    vals[2] = vals[1];
    vals[1] = vals[0];
    vals[0] = extractValue(napis);

    if (vals[0] < vals[1] && vals[1] < vals[2] && vals[2] < vals[3])
      digitalWrite(13, HIGH);
    else
      digitalWrite(13, LOW);

    lcd.setCursor(0,1);
    lcd.print(napis);
  }
}


void startNewGame() {
  lcd.print("Wave to start...");

  waitForWave();

  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("  Click fast!!  ");
}

void waitForWave(){
  int border = 500;
  float volts;

  boolean wave = false;
  while (!wave) {
    volts = analogRead(A5);
    if (volts > border)
      wave = true;
  }
  wave = false;
  while (!wave) {
    volts = analogRead(A5);
    if (volts < border)
      wave = true;
  }  
}

void readLine(char * napis) {
  int i;

  for (i = 0; i < 16; ++i)
    napis[i] = ' ';

  for (i = 0; i < 16; ++i) {
    while (!Serial.available() > 0) {
    };
    napis[i] = Serial.read();
    if (napis[i] < ' ') break;
  }

  if (i != 16)
    napis[i]=0;
}

boolean compareStrings(char * strA, char * strB, int length) {
  boolean equal = true;
  for (int i = 0; i<length; ++i)
    equal = equal && strA[i] == strB[i];
  return equal;
}

boolean isConnOpening(char * napis) {
  char napisOpening[9] = {
    'G', 'R', 'E', 'E', 'T', 'I', 'N', 'G', 'S'
  };
  return compareStrings(napisOpening, napis, 9);
}

boolean isGameOver(char * napis) {
  char napisOver[4] = {
    'G','A','M','E','_','O','V','E','R'
  };
  return compareStrings(napisOver, napis, 4);
}

boolean isSerwoOrder(char * napis) {
  char napisSerwo[6] = {
    'S', 'E', 'R', 'V', 'O', ' ' 
  };
  return compareStrings(napisSerwo, napis, 6);
}

int extractValue(char * napis) {
  int value = 0;
  int i = 0;
  while (i < 16 && napis[i] >= '0' && napis[i] <= '9') {
    value *= 10;
    value += napis[i] - '0';
    ++i;
  }
  return value;
}

int extractServoValue(char * napis) {
  return extractValue(& napis[6]);
}

