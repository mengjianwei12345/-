public class ColumnCipher {

    private int key;

    public ColumnCipher(int columnlength){
        this.key = columnlength;
    }

    public String encryption(String message){
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
                EncryptedStr += matrix[j][i];
            }
        }
        return EncryptedStr;
    }

    public String decryption(String message){
        char[] messages = message.toCharArray();
        String DecryptedStr = "";
        int rowLength = message.length()%key==0?((int)(message.length()/key)):((int)(message.length()/key)+1);
        char[][] matrix = new char[rowLength][key];
        for(int i=0; i < key; i++){
            for(int j=0; j < rowLength; j++){
                matrix[j][i] = messages[i*rowLength+j];
            }
        }
        for(int i=0; i < rowLength; i++){
            for(int j=0; j < key; j++) {
                if (matrix[i][j] != 'z')
                    DecryptedStr += matrix[i][j];
            }
        }
        return DecryptedStr;
    }
}
