public class ShiftEncryptionAlgorithm  implements Encryptable{

    private String plainText;
    private String encryptedText;
    private int length;
    private int shift = 3;

    @Override
    public String encrypte(String text) {
        this.length = text.length();
        this.encryptedText = "";
        for(int i=0;i< this.length;i++){
            char ch = text.charAt(i);

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


}
