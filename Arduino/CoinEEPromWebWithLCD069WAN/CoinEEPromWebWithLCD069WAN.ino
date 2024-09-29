
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include "ReadWriteEeprom.h"
#include "lcd.h"
#include "APServer.h"
#include "WiFiConnect.h"
#include "phusButton.h"


//const char* serverAddress = "192.168.100.13";
//const int serverPort = 8080; // Portul HTTP este de obicei 80
const char* serverAddress = "electro-shopping.ro";
const int serverPort = 80; // Portul HTTP este de obicei 80
WiFiClient client;

elapsedMillis loginContor = 0; //declare global if you don't want it reset every time loop runs
elapsedMillis timeElapsedLoop = 0; //declare global if you don't want it reset every time loop runs
const int intervalConnectLoop = 500000;
const int intervalTime = 5000;
const int loginTime = 30000; //60000;



bool coinValidate = true;
long totalCoin = 0;
long maxCredit = 50;
bool isClear = true;
bool isConnect = true;
struct Machine{
  String SerialMachine;
  String contor;
  String macAdress;
};
Machine machine = {"","",""};

//Button button1 = {0, 0, false};
void setup() {
     Serial.begin(115200);
   EEPROM.begin(2048);
   setupButton();
  setupLcd();
  // Clear the buffer
  display.clear();
if(digitalRead(BUTTON_PIN_2) == LOW){
     EEPROMWrite(EEaddress, 0);
     EEPROMWrite(indexEeprom.indexTotalCoin, 0); 
       writeWord("aaa",indexEeprom.indexSsid); 
       EEPROM.commit();
        display.clear();  
        delay(100);
        setup();  
}
//coinValidate = EEPROM.read(indexEeprom.indexCoinValid);
coinPin.numberKeyPresses = 0;
 myStringSSID = readWord(indexEeprom.indexSsid);
 myStringPassword = readWord(indexEeprom.indexPassword);
   Serial.print("ssid ");
   Serial.println(myStringSSID );
 Serial.print("password ");
 Serial.println(myStringPassword );
 startWiFi( myStringSSID, myStringPassword); 
displayText(WiFi.softAPIP().toString(),1,0,0);
//delay(1000);
// WiFi.begin(myStringSSID, myStringPassword);
 // WiFi.setAutoReconnect(true);
  //WiFi.persistent(true);
  attachInterrupt(coinPin.PIN, isr, FALLING); 
   display.clear();
  // EEPROMWrite(EEaddress, 0);
//EEPROM.write(EEaddress, (0 >> 8) & 0xFF); // Scriem byte-ul cel mai semnificativ
 //EEPROM.write(EEaddress + 1, 0 & 0xFF); // Scriem byte-ul cel mai puțin semnificativ
       // EEPROM.commit();
     WiFi.setAutoReconnect(true);
  WiFi.persistent(true);   
}
void loop() {
  
   
   Serial.print("Ssd ");
 Serial.println(myStringSSID);
 Serial.print("Password ");
 Serial.println(myStringPassword);
  if(isConnect){
   //  myStringSSID = readWord(indexEeprom.indexSsid);
 //myStringPassword = readWord(indexEeprom.indexPassword);
  isConnect =false;
  WiFi.begin(myStringSSID, myStringPassword);
   WiFi.setAutoReconnect(true);
  WiFi.persistent(true);
  }

 
   while (WiFi.status() != WL_CONNECTED) {
    delay(10);
    displayText(WiFi.softAPIP().toString(),1,0,0);
    displayText("Nu exista conexiune WIFI",1,0,10);
    displayText("SSID: ",1,0,20);
    displayText(myStringSSID,1,30,20);
    displayText("Pass: ",1,0,40);
    displayText(myStringPassword,1,30,40);
    Serial.print(".");
   server.handleClient();
   if(isClear){
    display.clear();
    isClear = false;
   }
  if(timeElapsedLoop>intervalConnectLoop){
    ESP.restart();
   }
  }
  if(WiFi.status() == WL_CONNECTED){
    if(isClear == false){
      display.clear();
      isClear=true;
     
    }
  
  IPAddress ip = WiFi.localIP();
  String textDisplay = "WIFI: "+myStringSSID+" S: "+String(WiFi.RSSI());
  Serial.print("Adresa IP Locala: ");
  Serial.println(ip);
   displayText(textDisplay,1,0,0);
  displayText("Local IP: ",1,0,10);
  displayText(WiFi.localIP().toString(),1,40,10);
   WiFi.softAPdisconnect (true);
   
 }

loopButton();
if(digitalRead(BUTTON_PIN_2) == LOW){
     EEPROMWrite(EEaddress, 0);
     EEPROMWrite(indexEeprom.indexTotalCoin, 0); 
       writeWord("aaa",indexEeprom.indexSsid); 
       EEPROM.commit();
        display.clear();  
        delay(100);
        setup();  
}

  if (coinPin.pressed) {
      Serial.printf("Button has been pressed %u times\n", EEPROM.read(EEaddress));
      coinPin.pressed = false; 
       
     // Clear the buffer
  display.clear();
  }
  
  
  displayText("MAC: ",1,0,20);
   displayText(String(WiFi.macAddress()),1,30,20);
 displayText("Credit Netransmis: ",1,0,30);
 displayText(String(EEPROMRead(EEaddress)),1,85,30);
 displayText("Credit Partial: ",1,0,40);
 displayText(String(coinPin.numberKeyPresses),1,85,40);
 displayText("TotalCredit: ",1,0,50);
 displayText(String(EEPROMRead(indexEeprom.indexTotalCoin)),1,65,50);
Serial.print("Contor ");
Serial.println(EEPROMRead(EEaddress));
Serial.print("loginContor ");
Serial.println(loginContor);
  
  if(loginContor>loginTime){
  display.clear();
  loginContor = 0;
 if (client.connect(serverAddress, serverPort)) {
 Serial.print("PutereSemnal!"); Serial.println(WiFi.RSSI());
    Serial.println("Conectare realizată!");
    machine.contor = 0;
    machine.macAdress = String(WiFi.macAddress());
   totalCoin = EEPROMRead(indexEeprom.indexTotalCoin);
   // machine.SerialMachine = "Stei1";  
String nameMachine = readWord(indexEeprom.indexLocation);
 //String nameMachine = "IneuBihor";
  Serial.print("nameMachine ");
  Serial.println(nameMachine);
  Serial.print("length ");
  Serial.println(nameMachine.length());

 client.print(String("GET /getTcpData.htm?dayCont="+machine.contor+"&signalLevel="+String(WiFi.RSSI())+"&serialnr="+nameMachine+"&macAddress="
  +machine.macAdress+"&permanentCont="+String(totalCoin)+" HTTP/1.1\r\n") +
              "Host: " + serverAddress + "\r\n" +
              "Connection: close\r\n\r\n");
  } else {
    Serial.println("Conectare eșuată :(");
      
  }
}
if((timeContor > intervalTime)&&(coinPin.numberKeyPresses> 0)){
        Serial.print("Conectare la serverul HTTP: ");
        //totalCoin = (EEPROM.read(indexEeprom.indexTotalCoin)<<8|EEPROM.read(indexEeprom.indexTotalCoin+1));
        
        display.clear();
  if (client.connect(serverAddress, serverPort)) {
    int dayContor = EEPROMRead(EEaddress);
    //contNetransmis = EEPROMRead(EEaddress);
    Serial.println("Conectare realizată!");
     Serial.println(dayContor);
   // totalCoin = EEPROMRead(indexEeprom.indexTotalCoin);
        totalCoin = EEPROMRead(indexEeprom.indexTotalCoin)  + dayContor;
        coinPin.numberKeyPresses = 0;
        EEPROMWrite(indexEeprom.indexTotalCoin, totalCoin);
        EEPROM.commit(); 
    machine.contor = String(dayContor);
    machine.macAdress = String(WiFi.macAddress());
    //machine.SerialMachine = "Stei1";  
   String nameMachine = readWord(indexEeprom.indexLocation);
 client.print(String("GET /getDataHall.htm?dayCont="+machine.contor+"&macAddress="+String(WiFi.macAddress())+"&permanentCont="+String(totalCoin)+" HTTP/1.1\r\n") +
               "Host: " + serverAddress + "\r\n" +
               "Connection: close\r\n\r\n");
  // client.print(String("GET /AppNewEurovending/getDataHall.htm?dayCont="+machine.contor+"&macAddress="+String(WiFi.macAddress())+"&permanentCont="+String(totalCoin)+" HTTP/1.1\r\n") +
              //  "Host: " + serverAddress + "\r\n" +
              // "Connection: close\r\n\r\n");            
coinPin.numberKeyPresses = 0;  
 EEPROMWrite(EEaddress, 0);
      machine.contor="";
        EEPROM.commit();
             totalCoin = 0;
            dayContor = 0;
  } else {
    Serial.println("Conectare eșuată :(");
      
  }

   while (client.available()) {
    //char c = client.read();
    Serial.print((char)client.read());
   // Serial.print(c);
  }
 //client.stop();
 timeContor=0;
 
   if (!client.connected()) {
    Serial.println();
    Serial.println("Deconectat de la server HTTP");
    client.stop();
    while (true);
  }
}
if(timeContor>100000){
  timeContor=0;
}
  if (coinPin.numberKeyPresses>=maxCredit) {
  coinPin.numberKeyPresses=0;
  }

delay(1000);

}
