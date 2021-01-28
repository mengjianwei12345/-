


public class ColumnKeywordCipher {

    private String keyword;
    private int key;

    public void setKeywordAndColumnLength(String keyword, int columnLength){
        this.keyword = keyword;
        this.key = columnLength;
    }

    public String encryption(String message){
        char[] keywords = keyword.toCharArray();
        HashMap<Character, Integer> keyMap = new HashMap<>();
        for(int i=0; i<keyword.length(); i++){
            keyMap.put(keywords[i], i);
        }
        HashMap<Integer, Integer> finalMap = new HashMap<>();
        Arrays.sort(keywords);
        int k = 0;
        for(char item: keywords){
            finalMap.put(k, keyMap.get(item));
            k++;
        }
        char[] messages = message.toCharArray();
        String EncryptedStr = "";
        int rowLength = message.length()%key==0?((int)(message.length()/key)):((int)(message.length()/key)+1);
        char[][] matrix = new char[rowLength][key];
        for(int i=0; i < rowLength; i++){
            for(int j=0; j < key; j++){
                try {
                    matrix[i][j] = messages[i * key + j];
                } catch (ArrayIndexOutOfBoundsException e){
                    matrix[i][j] = 'z';
                }
            }
        }
        for(int i=0; i < key; i++){
            for(int j=0; j < rowLength; j++){
                EncryptedStr += matrix[j][finalMap.get(i)];
            }
        }
        return EncryptedStr;
    }

    public String decryption(String message){
        char[] keywords = keyword.toCharArray();
        HashMap<Character, Integer> keyMap = new HashMap<>();
        for(int i=0; i < keyword.length(); i++){
            keyMap.put(keywords[i], i);
        }
        HashMap<Integer, Integer> finalMap = new HashMap<>();
        Arrays.sort(keywords);
        int k = 0;
        for(char item: keywords){
            finalMap.put(k, keyMap.get(item));
            k++;
        }
        char[] messages = message.toCharArray();
        String DecryptedStr = "";
        int rowLength = message.length()%key==0?((int)(message.length()/key)):((int)(message.length()/key)+1);
        char[][] matrix = new char[rowLength][key];
        for(int i=0; i < key; i++){
            for(int j=0; j < rowLength; j++){
                try {
                    matrix[j][finalMap.get(i)] = messages[i * rowLength + j];
                } catch (ArrayIndexOutOfBoundsException e){
                    matrix[j][finalMap.get(i)] = 'z';
                }
            }
        }
        for(int i=0; i < rowLength; i++){
            for(int j=0; j < key; j++){
                if(matrix[i][j] != 'z'){
                    DecryptedStr += matrix[i][j];
                }
            }
        }
        return DecryptedStr;
    }
}
