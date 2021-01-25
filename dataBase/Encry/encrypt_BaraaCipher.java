



public class BaraaCipher {
    public static void main(String[] args) {
        
        String password = JOptionPane.showInputDialog("Enter Your Password");
        String cipherPassword = "";
        for (int i=0 ; i<password.length() ; i++){
            char character = password.charAt(i);
            int ascii = (int) character;
            int cipherCharacter = ascii + 35 % 26;
            cipherPassword = cipherPassword + ((char) cipherCharacter);
        }
        JOptionPane.showMessageDialog(null,cipherPassword);
        
        String cipherPassword2 = JOptionPane.showInputDialog("Enter Your cipherPassword");
        String password2 = "";
        for (int i=0 ; i<cipherPassword2.length() ; i++){
            char character = cipherPassword2.charAt(i);
            int ascii = (int) character;
            int realCharacter = ascii - 35 % 26;
            password2 = password2 + ((char) realCharacter);
        }
        JOptionPane.showMessageDialog(null,password2);  
    }
    
}
