
#include <Wire.h>
#include "SSD1306Wire.h"

// Initialize the OLED display using Arduino Wire:
SSD1306Wire display(0x3c, 14, 12); // reversed!


void setupLcd(){
  // Initialising the UI will init the display too.
  display.init();

  //display.flipScreenVertically();
  display.setFont(ArialMT_Plain_10);
  display.setTextAlignment(TEXT_ALIGN_LEFT);
  display.drawString(0, 0, "Starting Display.." );
  display.display();
  // Clear the buffer
 // display.clear;
}


void displayText(String text,int sizeText,int xPosition,int yPosition) {
 
  display.setFont(ArialMT_Plain_10);
  display.setTextAlignment(TEXT_ALIGN_LEFT);
   display.drawString(xPosition, yPosition, text);
  display.display();
  
}

