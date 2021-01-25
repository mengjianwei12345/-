



/**
 * Created by ZicZac on 2/8/2017.
 */
public class CaesarCipher {
    private char[] alphabet = new char[26];
    private int SHIFT_RIGHT = 3;

    public CaesarCipher(){
        for (int i = 0; i < alphabet.length; i++){
            alphabet[i] = Character.toChars(0x0041 + i)[0];
        }
    }

    public void printAlphabet(){
        System.out.print("The alphabet is: ");
        for ( char e : alphabet) System.out.print(e + " ");
        System.out.println();
    }

    public void changeKey(int key){
        SHIFT_RIGHT = key;
    }

    public String encrypt(String str){
        char[] text = str.toUpperCase().toCharArray();
        for (int i = 0; i < text.length; i++){
            if (text[i] != ' ') {
                int index = Arrays.binarySearch(alphabet, text[i]);
                text[i] = alphabet[(index + SHIFT_RIGHT)%26];

            }
        }
        String s = new String(text);
        return s;
    }

    public String decrypt(String str, int key){
        char[] text = str.toUpperCase().toCharArray();
        for (int i = 0; i < text.length; i++){
            if (text[i] != ' ') {
                int index = Arrays.binarySearch(alphabet, text[i]);
                text[i] = alphabet[Math.floorMod((index - key),26)];

            }
        }

        String s = new String(text);
        return s;
    }
}
