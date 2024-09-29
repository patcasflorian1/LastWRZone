#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>

const char* ssid = "ESP8266_AP";
const char* password = "";
ESP8266WebServer server(80);
String myStringPassword;
String myStringSSID ;
void handleRoot() {
  server.send(200, "text/html", "<h1>Configurare ESP8266</h1><h2>Introduceti SSID-ul , parola retelei WiFi</h2><br><h2> si numele Locatiei:</h2><form action='/config' method='get'><input type='text' name='ssid' placeholder='SSID'><br><input type='text' name='password' placeholder='Parola'><br><input type='text' name='locationname' placeholder='Nume Locatie'><br><input type='submit' value='Conectare'></form>");
}

void handleConfig() {
  String ssid = server.arg("ssid");
  String password = server.arg("password");
   String locationName = server.arg("locationname");
  myStringSSID = server.arg("ssid");
  myStringPassword = server.arg("password");

     Serial.print("ssidstartConfig ");
 Serial.println(myStringSSID );
 Serial.print("passwordstartConfig ");
 Serial.println(myStringPassword );
 //saveCredentials(myStringSSID, myStringPassword);
display.clear();
myStringSSID.trim();
myStringPassword.trim();
 writeWord(myStringSSID,indexEeprom.indexSsid);
writeWord(myStringPassword,indexEeprom.indexPassword);
 Serial.print("NumeLoc ");
 locationName.trim();
 Serial.println(locationName);
 writeWord(locationName,indexEeprom.indexLocation);
  WiFi.begin(ssid, password);
  server.send(200, "text/html", "<h1>Conexiune WiFi</h1><p>Conectare la reteaua WiFi...</p>");
ESP.restart();
}

void startAP(){
    WiFi.softAP(ssid, password);
  IPAddress ip = WiFi.softAPIP();
  Serial.print("Adresa IP AP: ");
  Serial.println(ip);

  server.on("/", handleRoot);
  server.on("/config", handleConfig);

  server.begin();
  Serial.println("Server pornit");
  
  
}