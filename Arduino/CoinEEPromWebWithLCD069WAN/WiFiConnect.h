
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
//const char* serverAddress = "192.168.100.13";
//const int serverPort = 8080; // Portul HTTP este de obicei 80
//WiFiClient client;
void startWiFi(String myStringSSID,String myStringPassword){
WiFi.begin(myStringSSID, myStringPassword);
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