#include <elapsedMillis.h>
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
//const char* serverAddress = "192.168.100.13";
//const int serverPort = 8080; // Portul HTTP este de obicei 80
//WiFiClient client;

elapsedMillis timeElapsedWifi = 0; //declare global if you don't want it reset every time loop runs
// delay in milliseconds between blinks of the LED
unsigned int intervalConnect = 50000;

void startWiFi(String myStringSSID,String myStringPassword){
WiFi.mode(WIFI_STA);
WiFi.begin(myStringSSID, myStringPassword);
  Serial.println(WiFi.localIP());
 if(WiFi.status() == WL_CONNECTED) {
  WiFi.softAPdisconnect (true);
   Serial.print("ssid ");
   Serial.println(myStringSSID );
 Serial.print("password ");
 Serial.println(myStringPassword );
    delay(500);
     Serial.println("Conectat la rețea WiFi");
 WiFi.setAutoReconnect(true);
  WiFi.persistent(true);
    display.clear();
  }
 else{
  Serial.println("Neconectat la rețea WiFi");
  startAP();
 };
    delay(1000);
}