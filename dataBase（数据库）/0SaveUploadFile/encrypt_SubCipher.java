public class SubCipher {

    public SubCipher () {
        
    }
        //Letters from A . . . Z to use for encryption
        String[] setOfLetters = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
                      
        String toEncrypt = "";
        String toDecrypt = "";
                
        String encrypted = encrypt(setOfLetters, toEncrypt);
         
        String decrypted = decrypt(setOfLetters, toDecrypt);
    
    //THE ENCRYPTION METHOD
    public String encrypt(String[] a, String q)  {
      
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int code = 0;
        String toCheck1 = "";
        String encrypted = "";
      
        for (int i = 0; i < q.length(); i++)   {
            toCheck1 = Character.toString(q.charAt(i));
            for (int j = 0; j < a.length; j++)  {
                String toCheck2 = a[j];
                if (toCheck1.equalsIgnoreCase(toCheck2)) {
               
                    code = ((19 * j) + 2) % 26;
               
                    String[] alphabet = a;
                    for (int k = 0; k < alphabet.length; k++)  {
                        if (code == k)    {
                        encrypted = encrypted + alphabet[k];
                        }
                    }
                }
            }
            String toCheck3 = toCheck1.toUpperCase();
            if (q.length() != encrypted.length())  {
                if (!(letters.contains(toCheck3))) {
                    encrypted = encrypted + toCheck1;
                }
            }
            code = 0;
        }
        return encrypted;
    }
   //THE DECRYPTION METHOD
    public String decrypt(String[] a, String q)  {
   
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int code = 0;
        String toCheck1 = "";
        String decrypted = "";
      
        for (int i = 0; i < q.length(); i++)   {
            toCheck1 = Character.toString(q.charAt(i));
            for (int j = 0; j < a.length; j++)  {
                String toCheck2 = a[j];
                if (toCheck1.equalsIgnoreCase(toCheck2)) {
               
                    code = (11 * (j - 2)) % 26;
                    if (code < 0)  {
                        code = 26 + code;
                    }
               
                    String[] alphabet = a;
                    for (int k = 0; k < alphabet.length; k++)  {
                        if (code == k)    {
                        decrypted = decrypted + alphabet[k];
                        }
                    }
                }
            }
            String toCheck3 = toCheck1.toUpperCase();
            if (q.length() != decrypted.length())  {
                if (!(letters.contains(toCheck3))) {
                    decrypted = decrypted + toCheck1;
                }
            }
            code = 0;
        }
        return decrypted;
    }

    public static void main(String[] args) {
        SubCipherGUI acf = new SubCipherGUI();
    }
}
