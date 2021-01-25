/**
 * Implementation of Shift encryption algorithm using
 * shift key = 3,
 * The class works both ways, encryption and decryption.
 *
 * It was developed as an delivering a Hiring Task for Robusta
 *
 * @author  Abdelrahman Amer
 * @version 1.0
 * @since   2019-12-1
 *
 * @license MIT
 */

public class ShiftEncryptionAlgorithm  implements Encryptable{

    private String plainText;
    private String encryptedText;
    private int length;
    // Shift param of the algorithm
    private int shift = 3;


    /**
     * This method perofrm the core for the encryption process
     *
     * param plainText     Original text from the user
     * @return String       encrypted Text
     */
    @Override
    public String encrypte(String text) {
        this.length = text.length();
        this.encryptedText = "";
        for(int i=0;i< this.length;i++){
            char ch = text.charAt(i);
            //Check for letter/Non-letter
            if(Character.isLetter(ch)){

                if(Character.isLowerCase(ch)){
                    char c = (char)(ch+this.shift);

                    // exceeds the upper bound of the ASCII code
                    if(c> 'z'){
                        this.encryptedText += (char)(ch - (26 - this.shift));
                    }else{
                        this.encryptedText += c;
                    }
                }else if(Character.isUpperCase(ch)){
                    char c = (char)(ch+this.shift);
                    // exceeds the upper bound of the ASCII code
                    if(c> 'Z'){
                        this.encryptedText += (char)(ch - (26 - this.shift));
                    }else{
                        this.encryptedText += c;
                    }
                }
                //Catch the Space
            }else if(ch == ' '){
                this.encryptedText += ch;
            }

        }
        return this.encryptedText;
    }

    /**
     * This method perofrm the core for the decryption process
     *
     * @param plaintext     encrypted text from the user
     * @return String       Original Text
     */
    @Override
    public String decrypte(String text) {

        this.length = text.length();
        this.encryptedText = "";

        for(int i=0;i< this.length;i++){
            char ch = text.charAt(i);
            //Check for letter/Non-letter
            if(Character.isLetter(ch)){

                if(Character.isLowerCase(ch)){
                    char c = (char)(ch-this.shift);

                    // exceeds the lower bound of the ASCII code
                    if(c < 'a'){
                        this.encryptedText += (char)(ch + (26 - this.shift));
                    }else{
                        this.encryptedText += c;
                    }

                }else if(Character.isUpperCase(ch)){
                    char c = (char)(ch-this.shift);
                    // exceeds the lower bound of the ASCII code
                    if(c < 'A'){
                        this.encryptedText += (char)(ch + (26 - this.shift));
                    }else{
                        this.encryptedText += c;
                    }
                }
                //Catch the Space
            }else if(ch == ' '){
                this.encryptedText += ch;
            }

        }
        return this.encryptedText;
    }




}
