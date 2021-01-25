



/**
 * A simple example for a transposition cipher is columnar transposition cipher
 * where each character in the plain text is written horizontally with specified alphabet width.
 * The cipher is written vertically, which creates an entirely different cipher text.
 *
 * What about decryption phase?
 * read cells of matrix from left to right , (or horizontally)
 */
public class TranspositionCipher implements Serializable {
    private final int rowCount;
    private final int columnCount;
    private final char[][] matrix;

    public TranspositionCipher(int rowCount,int columnCount){
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        matrix = new char[rowCount][columnCount];
    }


    private  void setUpMatrixForEncrypt(String plainText){
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                //exit condition
                if(plainText.length() <= row*columnCount+col)break;

                matrix[row][col] = plainText.charAt(row*columnCount + col);
            }
        }
        //printMatrix();//debugging purpose
    }

    //debugging purpose method,to print out matrix
    private void printMatrix(){
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println("----");
    }

    public  String encrypt(String plainText){
        //fill in matrix with characters in some predefined order
        setUpMatrixForEncrypt(plainText);

        StringBuilder stringBuilder = new StringBuilder();

        //read matrix vertically and build string
        for (int col = 0; col < columnCount; col++) {
            for (int row = 0; row < rowCount; row++) {
                //when charachter not exist,meaning null or code = 0
                if(matrix[row][col] == '\u0000')matrix[row][col] = ' ';

                stringBuilder.append(matrix[row][col]);
            }
        }
        return stringBuilder.toString().trim();
    }

    private  void setUpMatrixForDecrypt(String cipherText){

        for (int col = 0; col < columnCount; col++) {
            for (int row = 0; row < rowCount; row++) {
                //exit condition
                if(cipherText.length() <= col*rowCount + row)break;

                matrix[row][col] = cipherText.charAt(col*rowCount + row);
            }
        }
        //printMatrix(); debugging purpose
    }

    public String decrypt(String cipherText){

        setUpMatrixForDecrypt(cipherText);

        StringBuilder stringBuilder = new StringBuilder();

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                //when charachter not exist,meaning null or code = 0
                if(matrix[row][col] == '\u0000')continue;
                stringBuilder.append(matrix[row][col]);
            }
        }
        return stringBuilder.toString().trim();
    }

}
