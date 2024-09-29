#include <EEPROM.h>

// Change the STORED_WORD is another string
//String STORED_WORD = "False";
//String answer = "";

struct IndexEeprom{
  int indexSsid;
  int indexPassword;
  int indexTestWifi;
  int indexCoinValid;
  int indexTotalCoin;
  int indexLocation;
};

IndexEeprom indexEeprom = {0,30,60,120,150,180};

//function to write to EEPROM
void EEPROMWrite(int address, long value) {

    /* size of long is 32-bits but each memory location in EEPROM
     * takes only 8-bits and then rolls over. Thus, it is taken into 4 individual bytes
     * and then stored in consecutive memory locations.
     */
    byte four = (value & 0xFF);
    byte three = ((value >> 8) & 0xFF);      // using bitwise operator here for shifting through bits
    byte two = ((value >> 16) & 0xFF);
    byte one = ((value >> 24) & 0xFF);
    
    EEPROM.put(address, four);
    EEPROM.put(address + 1, three);
    EEPROM.put(address + 2, two);
    EEPROM.put(address + 3, one);
    EEPROM.commit();
}

//function to read from EEPROM
long EEPROMRead(int address) {
    
    /* Reading individual bytes here from each memory location into 
     * different variables.
     */
    byte four = EEPROM.read(address);       // least LSB
    byte three = EEPROM.read(address + 1);       
    byte two = EEPROM.read(address + 2);
    byte one = EEPROM.read(address + 3);     // most MSB

    return (
          /*Combining all the four read outputs for retrieving the saved value from EEPROM
          * It's reverse procedure of what is being done in EEPROMWrite().
          * Here, long is stored as big endian 
          */
          ((four << 0) & 0xFF) +        // retreiving least LSB
          ((three << 8) & 0xFFFF) + 
          ((two << 16) & 0xFFFFFF) + 
          ((one << 24) & 0xFFFFFFFF)    // retreiving most MSB
      );
}


void  writeWord(String word,int index){
 char myCharword[30]={};
  delay(10);
strcpy(myCharword, word.c_str());
 // Serial.println(word.length());
  EEPROM.put(index, myCharword);
  EEPROM.commit();
  EEPROM.put(index+30, '\0');
  EEPROM.commit();
 // for(int i=index;i<word.length();i++){
  //EEPROM.write(index, myCharword[i]);
 // EEPROM.commit();
  //}
  //EEPROM.write(index+30, '\0');
 // EEPROM.commit();
}

String readWord(int index) {
  String word;
  char readChar='"';
  int i = 0;
  while (readChar != '\0') {
    readChar = char(EEPROM.get(index+i,readChar));
     //readChar =char( EEPROM.read(index+i));
    delay(10);
    i++;
 
    if (readChar != '\0') {
      word += readChar;
    }
  }
  return word;
}


/*
void saveCredentials(String ssid, String password) {
  // Salvare date in EEPROM
  EEPROM.begin(512);
  int ssidLength = ssid.length();
  int passwordLength = password.length();
int addr = 0;
char myCharPassword[20] =  "";
char myCharSSID[20] =  "";
strcpy(myCharPassword, password.c_str());
  strcpy(myCharSSID, ssid.c_str());
 EEPROM.put(0, myCharSSID );
  EEPROM.put(30, myCharPassword);
  EEPROM.commit();
}

void  writeWord(String word,int index){
   EEPROM.begin(512);
char myCharword[25] =  "";
  delay(10);
 // String originalString = "   Exemplu de string cu spatii goale   "; // String-ul original cu spații goale
  String wordNew =word;
strcpy(myCharword, word.c_str());
 Serial.println(word.length());
  EEPROM.put(index, myCharword);
  EEPROM.put(index+word.length()+1, '\0');
 //for(int i=index;i<wordNew.length();i++){
 //EEPROM.write(i, wordNew[i]); 
 // EEPROM.commit();
// }
// EEPROM.write(index+wordNew.length()+1, '\0');
  EEPROM.commit();
 // EEPROM.end();
}

String readWord(int index) {
  String word="";
  char readChar='"';
  int i = 0;
  while (readChar != '\0') {
  readChar= EEPROM.read(index+i);
    i++;
    if (readChar != '\0') {
      word += readChar;
    }
  }
   EEPROM.commit();
   EEPROM.end();
  return word;
}
/*
String readWord(int index) {
  String word;
  char readChar[30]="";
EEPROM.get(index, readChar);
word =readChar;
  return word;
}*/
