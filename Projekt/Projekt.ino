#include <LiquidCrystal.h>
#include <Servo.h>

LiquidCrystal lcd(8, 9, 4, 5, 6, 7);
Servo servo;
char napis[16];


void guzik() {
  Serial.println("GUZIK!");
}


void setup() {
  Serial.begin(9600);

  pinMode(11, OUTPUT);
  servo.attach(11);
  servo.write(0);

  pinMode(A0, INPUT);
  attachInterrupt(0, guzik, FALLING);

  lcd.begin(16,2);
  lcd.clear();
  lcd.print("    The Game    ");
  lcd.setCursor(0,1);
  lcd.print("    Welcome!    ");
  delay(2000);

  lcd.clear();
  startNewGame();
}

void loop() {
  readLine(napis);  

  if (isGameOver(napis)) {
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

boolean isGameOver(char * napis) {
  
  char napisOver[4] = {
    'O', 'V', 'E', 'R'  };
    
  boolean over = true;
  for (int i = 0; i<4; ++i)
    over = over && napis[i] == napisOver[i];

  return over;
}

boolean isSerwoOrder(char * napis) {
  
  char napisSerwo[6] = {
    'S', 'E', 'R', 'V', 'O', ' ' };
    
  boolean servo = true;
  for (int i = 0; i<6; ++i)
    servo = servo && napis[i] == napisSerwo[i];

  return servo;
}

int extractServoValue(char * napis) {
  int value = 0;
  
  int i = 6;
  while (napis[i] >= '0' && napis[i] <= '9') {
    value *= 10;
    value += napis[i] - '0';
    ++i;
  }
  
  return value;
}
