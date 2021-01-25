

public class Shift {

  private int s;

  public Shift(int shift) {
    s = shift;
  }

  private Character determineShiftBoundary(char context, char c) {
    if (Character.isLowerCase(c)) {
      return (Character.toLowerCase(context) == 'd') ? 'a' : 'z';
    } else if (Character.isUpperCase(c)) {
      return (Character.toLowerCase(context) == 'd') ? 'A' : 'Z';
    } else {
      return null;
    }
  }

  public String encrypt(String msg) {
    StringBuilder strBuilder = new StringBuilder();
    int len = msg.length();
    for(int x = 0; x < len; x++){
      char c = (char)(msg.charAt(x));
      Character start = determineShiftBoundary('E', c);
      if (start == null) {
        strBuilder.append(c);
        continue;
      }
      char encryptedChar = (char)(msg.charAt(x) + s);
      if (encryptedChar > start){
        encryptedChar = (char)(msg.charAt(x) - (26-s));
      }
      strBuilder.append(encryptedChar);
    }
    return strBuilder.toString();
  }

  public String decrypt(String msg) {
    StringBuilder strBuilder = new StringBuilder();
    int len = msg.length();
    for(int x = 0; x < len; x++){
      char c = (char)(msg.charAt(x));
      Character start = determineShiftBoundary('D', c);
      if (start == null) {
        strBuilder.append(c);
        continue;
      }
      char decryptedChar = (char)(msg.charAt(x) - s);
      if (decryptedChar < start) {
        decryptedChar = (char)(msg.charAt(x) + (26-s));
      }
      strBuilder.append(decryptedChar);
    }
    return strBuilder.toString();
  }
}
