#include <ezButton.h>
#include "Arduino.h"
#include <elapsedMillis.h>
#include <EEPROM.h>


#define BUTTON_PIN_2 13  //D7 The ESP8266 pin connected to the button 2

//elapsedMillis loginContor = 0; //declare global if you don't want it reset every time loop runs
elapsedMillis timeContor = 0; //declare global if you don't want it reset every time loop runs

//ezButton button2(BUTTON_PIN_2);  // create ezButton object for button 2
int EEaddress = 90;

int prev_button_state = LOW;  // The previous state from the input pin
int button_state;     // The current reading from the input pin
struct Button {
  const uint8_t PIN;
  uint32_t numberKeyPresses =0;
  bool pressed;
};

//variables to keep track of the timing of recent interrupts
unsigned long button_time = 0;  
unsigned long last_button_time = 0; 
Button coinPin = {0, 0, false};
long partialCoin = 0;


void setupButton(){
  
  pinMode(coinPin.PIN, INPUT_PULLUP);
  //button2.setDebounceTime(100);  // set debounce time to 100 milliseconds
  //button2.setCountMode(1);
  pinMode(BUTTON_PIN_2,INPUT_PULLUP);
}

void loopButton(){   
 // button2.loop();  // MUST call the loop() function first
  // get button state after debounce
  //int button2_state = button2.getState();  // the state after debounce
}

void ICACHE_RAM_ATTR isr() {
    button_time = millis();
if (button_time - last_button_time > 50)
{
        coinPin.numberKeyPresses++;
        partialCoin++;
        coinPin.pressed = true;
       last_button_time = button_time;
       timeContor=0;
        partialCoin =  partialCoin +EEPROMRead(EEaddress); //(EEPROM.read(EEaddress)<<8|EEPROM.read(EEaddress+1));
        //EEPROM.write(EEaddress,partialCoin+EEPROM.read(EEaddress)); // Writes the value 123 to EEPROM
       // EEPROM.write(EEaddress, (partialCoin >> 8) & 0xFF); // Scriem byte-ul cel mai semnificativ
       // EEPROM.write(EEaddress + 1, partialCoin & 0xFF); // Scriem byte-ul cel mai puțin semnificativ 
         EEPROMWrite(EEaddress, partialCoin);
        //EEPROM.commit();
        partialCoin = 0;
      display.clear();
}
}