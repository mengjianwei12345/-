public class CeaserCipher {

    public String encryption(int key, String message){
        char[] charArray = message.toCharArray();
        String EncryptedStr = "";
        for(char item : charArray){
            int enInt = (int) item + key;
            if(enInt > 122 && enInt < 122 + key){
                enInt -= 26;
            }
            if(enInt > 90 && enInt < 97){
                enInt -= 26;
            }
            char enChar = (char) enInt;
            EncryptedStr += enChar;
        }
        return EncryptedStr;
    }

    public String decryption(int key, String message){
        char[] charArray = message.toCharArray();
        String DecryptedStr = "";
        for(char item : charArray){
            int enInt = (int) item - key;
            if(enInt < 65 && enInt > 65 - key){
                enInt += 26;
            }
            if(enInt > 90 && enInt < 97){
                enInt += 26;
            }
            char enChar = (char) enInt;
            DecryptedStr += enChar;
        }
        return DecryptedStr;
    }
}
