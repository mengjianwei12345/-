



public class Cipher {

    public static void main(String[] args){

        Scanner input = new Scanner(System.in);

        char[][] encryptionKeys  = keyGen();

        System.out.println("Write your message here (English alphabet only): ");
        String message = input.nextLine();

        String encryptedMessage = encryptMessage(message, encryptionKeys);

        System.out.println("Encrypted message: "+encryptedMessage);

        String decryptedMessage = decryptMessage(encryptedMessage, encryptionKeys);

        System.out.println("Decrypted message: "+decryptedMessage);

    }


   //Generates the encryption keys
    public static char[][] keyGen(){

        char[] keys = new char[26];

        Random r = new Random();
        char item = 0;
        boolean isPresent= true;


        for (int i=0; i < 26; i++) {
            while(isPresent){
                isPresent= false;
                item = (char)(r.nextInt(26) + 'a');

                for (char letter : keys) {
                    if (letter == item)
                        isPresent = true;
                }
            }
            if(!isPresent)
                keys[i] = item;
            isPresent= true;
        }

        char[] key1= new char[13];
        char[] key2 = new char[13];

        System.arraycopy(keys, 0, key1, 0,13);
        System.arraycopy(keys, 13, key2, 0,13);
        char [][] encryptionKeys = {key1, key2};

        return encryptionKeys;
    }



    //Algorithm that encrypts message according to the kama sutra system
    public static String encryptMessage(String message, char[][] keys) {

        char[] key1 = keys[0];
        char[] key2 = keys[1];

        char[] encryptMessage = new char[message.length()];

        for (int i = 0; i < message.length(); i++) {
            char letter = message.charAt(i);

            for (int j = 0; j < key1.length; j++) {
                if (letter == key1[j])
                    encryptMessage[i] = key2[j];
            }
            for (int j = 0; j < key2.length; j++) {
                if (letter == key2[j])
                    encryptMessage[i] = key1[j];
            }

            if(encryptMessage[i]==0)
                encryptMessage[i]=32;
        }

        String encryptedMessage = String.valueOf(encryptMessage);

        return encryptedMessage;
    }


    //Takes the encrypted message, return original message
    public static String decryptMessage(String message, char[][] keys) {

        char[] key1 = keys[0];
        char[] key2 = keys[1];

        char[] decryptMessage = new char[message.length()];

        for (int i = 0; i < message.length(); i++) {

            char letter = message.charAt(i);
            for (int j = 0; j < key1.length; j++) {
                if (letter == key1[j])
                    decryptMessage[i] = key2[j];
            }
            for (int j = 0; j < key2.length; j++) {
                if (letter == key2[j]) decryptMessage[i] = key1[j];
            }

            if(decryptMessage[i]==0)
                decryptMessage[i]=32;
        }

        String decryptedMessage = String.valueOf(decryptMessage);

        return decryptedMessage;
    }
}

