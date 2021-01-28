


public class DoubleMapCipher {

    private char[] alphas = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private HashMap<Character, Character> FirstCipherMap = new HashMap<Character, Character>();
    private HashMap<Character, Character> FirstDecryptionMap = new HashMap<Character, Character>();
    private HashMap<Character, Character> SecondCipherMap = new HashMap<Character, Character>();
    private HashMap<Character, Character> SecondDecryptionMap = new HashMap<Character, Character>();

    public void makeFirstMap(String keyword){
        FirstCipherMap.clear(); FirstDecryptionMap.clear();
        char[] keywordsArray = keyword.toCharArray();
        ArrayList<Character> keywords = new ArrayList<>();
        for(char item: keywordsArray){
            keywords.add(item);
        }
        for(char item: alphas) {
            if (!keyword.contains(String.valueOf(item))) {
                keywords.add(item);
            }
        }
        for(int i=0; i < 26; i++){
            FirstCipherMap.put(alphas[i], keywords.get(i));
        }
        for(int i=0; i < 26; i++){
            FirstDecryptionMap.put(keywords.get(i), alphas[i]);
        }
    }

    public void makeSecondMap(int key){
        SecondCipherMap.clear(); SecondDecryptionMap.clear();
        for(char item: alphas){
            SecondCipherMap.put(item, (int)item+key<=122?(char)((int)item+key):(char)((int)item+key-26));
        }
        for(char item: alphas){
            SecondDecryptionMap.put((int)item+key<=122?(char)((int)item+key):(char)((int)item+key-26), item);
        }
    }

    public String encryption(String message){
        char[] messages = message.toCharArray();
        String EncryptedStr = "";
        for(int i=0; i < message.length(); i++){
            if(i%2==0)
                EncryptedStr += FirstCipherMap.get(messages[i]);
            else
                EncryptedStr += SecondCipherMap.get(messages[i]);
        }
        return EncryptedStr;
    }

    public String decryption(String message){
        char[] messages = message.toCharArray();
        String DecryptedStr = "";
        for(int i=0; i < message.length(); i++){
            if(i%2==0)
                DecryptedStr += FirstDecryptionMap.get(messages[i]);
            else
                DecryptedStr += SecondDecryptionMap.get(messages[i]);
        }
        return DecryptedStr;
    }
}
