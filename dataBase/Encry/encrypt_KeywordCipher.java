

public class KeywordCipher {

    private char[] alphas = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private HashMap<Character, Character> CipherMap = new HashMap<Character, Character>();
    private HashMap<Character, Character> DecryptionMap = new HashMap<Character, Character>();

    public void makeMap(String keyword){
        CipherMap.clear(); DecryptionMap.clear();
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
            CipherMap.put(alphas[i], keywords.get(i));
        }
        for(int i=0; i < 26; i++){
            DecryptionMap.put(keywords.get(i), alphas[i]);
        }
    }

    public String encryption(String message){
        char[] messages = message.toCharArray();
        String EncryptedStr = "";
        for(char item: messages){
            EncryptedStr += CipherMap.get(item);
        }
        return EncryptedStr;
    }

    public String decryption(String message){
        char[] messages = message.toCharArray();
        String DecryptedStr = "";
        for(char item: messages){
            DecryptedStr += DecryptionMap.get(item);
        }
        return DecryptedStr;
    }
}
