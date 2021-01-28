






/**
 * Very basic substitution cipher that simply replaces each character in a string with another
 * String.
 * 
 * At the moment this Cipher only supports messages with characters A-Z, space and period (.)
 * If a unkown character is found, it is replaced by empty string.
 * 
 * @author gurps.bassi
 *
 */
public class SubstitutionCipher {

  private static BidiMap substitutionMap = new DualHashBidiMap();
  
  static {
    substitutionMap.put("A", "2@@");
    substitutionMap.put("B", "3##");
    substitutionMap.put("C", "4$$");
    substitutionMap.put("D", "5%%");
    substitutionMap.put("E", "6^^");
    substitutionMap.put("F", "7&&");
    substitutionMap.put("G", "8**");
    substitutionMap.put("H", "9((");
    substitutionMap.put("I", "10)");
    substitutionMap.put("J", "11!");
    substitutionMap.put("K", "12@");
    substitutionMap.put("L", "13#");
    substitutionMap.put("M", "14$");
    substitutionMap.put("N", "15%");
    substitutionMap.put("O", "16^");
    substitutionMap.put("P", "17&");
    substitutionMap.put("Q", "18*");
    substitutionMap.put("R", "19(");
    substitutionMap.put("S", "20)");
    substitutionMap.put("T", "21!");
    substitutionMap.put("U", "22@");
    substitutionMap.put("V", "23#");
    substitutionMap.put("W", "24$");
    substitutionMap.put("X", "25%");
    substitutionMap.put("Y", "26^");
    substitutionMap.put("Z", "27&");
    substitutionMap.put(" ", "*91");
    substitutionMap.put(".", "*92");
}
      
  private String encryptChar(final char c){
    String cipherChar = (String)substitutionMap.get((c + "").toUpperCase());
    if(cipherChar == null){
      cipherChar = "";
    }
    
    return cipherChar;
  }
  
  private String decryptSubstitutetChar(final String encrypted){
    String plainTextChar = (String)substitutionMap.getKey(encrypted);
    if(plainTextChar == null){
      plainTextChar = "*";
    }
    
    return plainTextChar;
  }
  
  public String encrypt(final String message){
    if(message == null || message.length() == 0){
      return "";
    }
    
    StringBuilder output = new StringBuilder();
    
    message.chars().forEach(i -> output.append(encryptChar((char)i)));
    return output.toString();
  }
  
  public String decrypt(final String encrypted){
    
    StringBuilder output = new StringBuilder();
    
    //All substitutes are of length 3 - so regex splits in groups of 3.
    String[] substitutes = encrypted.split("(?<=\\G.{3})");
    Arrays.stream(substitutes).forEach(i -> output.append(decryptSubstitutetChar(i)));
    return output.toString();
  }
}
