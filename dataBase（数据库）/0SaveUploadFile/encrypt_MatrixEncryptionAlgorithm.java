

/**
 * Implementation of Matrix encryption algorithm using
 * 16x16 matrix as key,
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
public class MatrixEncryptionAlgorithm implements Encryptable {
    // Inverse of Key Matrix provided as below [16]*[16]
    double[][] inverseMatrix = {
            {
                    0.087698730443907, 0.106508423872910 ,- 0.035837889522160 ,- 0.018212614575464,
                    0.029282205872323, 0.009768535961213, 0.051022092452304, -0.002600159007516
                    ,0.062599483798891, -0.020588660945658, -0.005731646667785 , - 0.109625191395912
                    ,-0.006932407372092, -0.056202668885562, -0.075318484801680 , 0.014708766520769},
            {
                    -0.022928256564172, -0.026683449299678, -0.011012588526442, 0.129942414512966 ,
                    - 0.122921871300045, - 0.043058741765130 ,- 0.117420500612853 ,- 0.140391873840202
                    ,- 0.071045583264847, - 0.034148665820787, - 0.095666764302039 ,0.234091140082102 ,
                    0.027357920334586 ,0.072726676711471, 0.065330793324812 ,0.030966972897445
            },
            {
                    -0.218268560384352, - 0.167198025185273 ,0.018741969126614 , 0.134524170075639
                    ,- 0.321465832541972, - 0.166586732147428 , 0.039385178742807 , - 0.113346935522171
                    , - 0.185692852729154 , - 0.043259247663190 , - 0.076549003088936 , 0.573616991323091
                    , 0.104219031977534, 0.246544562428294 , 0.177605218056100 , - 0.134494179895595
            },
            {
                    -0.074234710142859 ,- 0.152484831242658, 0.032888774006722, 0.126780472517150,
                    - 0.280349992738819 ,- 0.177762951540922 ,0.068628817353806 ,- 0.051217841640809,
                    - 0.180654642158176 ,- 0.011766502223790 ,- 0.080775821814310 ,0.379125349840351,
                    0.048682798429393 ,0.251118885929070 ,0.025753297544241 ,- 0.015741279938887
            },
            {
                    -0.107282379497940 ,- 0.076473612678836 ,0.048411952177752 ,0.128387746395734
                    ,- 0.218476883182669 ,- 0.182054566825948 ,0.015297614620229 ,- 0.071068523564674
                    ,- 0.173515463207588 ,- 0.004127099161250 ,- 0.106414518336578 ,0.363864949305460
                    ,0.024524217055173 ,0.165306494873899 ,0.163356977791568 ,- 0.035033674480292
            },
            {
                    0.277288909540715 ,0.191426836794975 ,0.115975544752238 ,- 0.300809424418599
                    ,0.353820685363440 ,0.206982195506894 ,0.135654125071293 ,0.381774054614345
                    ,0.313562376027531 ,0.069054897975148 ,- 0.011797886745698 ,- 0.853234848281320
                    ,0.086056587100296 ,- 0.364305268023283 ,- 0.618093105304007 ,0.253957528660826
            },
            {
                    0.038358445191113 ,- 0.000546407470896 ,- 0.073465777300804 ,- 0.033200515562292
                    ,- 0.020621719941806 ,0.046779969176516 ,- 0.057290779719178 ,- 0.095971425211909
                    ,- 0.009648470448360 ,0.006680795638773 ,0.001875819452453 ,0.062297723542825
                    ,- 0.005182609330966 ,0.040283770455199 ,0.144418033011726 ,- 0.055272938421632
            },
            {
                    0.241341634348029 ,0.173795889482842 ,- 0.037558951477139 ,- 0.142555330724481
                    ,0.384872718455622 ,0.238727072083511 ,- 0.012025720907547 ,0.205726531015318
                    ,0.215305804451694 ,0.050086219117152 ,0.058130045507594 ,- 0.741794720895175
                    ,0.050078370236749 ,- 0.310037624023072 ,- 0.430832649690117 ,0.179950027829876
            },
            {
                    0.171381216925833 ,0.086060780454874, - 0.042565007877466 ,- 0.078956143623505
                    ,0.327447146261980 ,0.206393272411101 ,- 0.099952932487210 ,0.087552045773402
                    ,0.173129037727576 ,0.018397910434386 ,0.086685394445941 ,- 0.429207905148962
                    ,- 0.134850123674970 ,- 0.238397133799728 ,- 0.059141482129532 ,0.042322030383679
            },
            {
                    -0.288768790079429 ,- 0.180939915747826 ,0.014729697120086 ,0.167827000966843
                    ,- 0.432864619931572 ,- 0.272139265354751 ,- 0.055798856798054 ,- 0.158129080251415
                    ,- 0.179029372582232 ,- 0.057409513342173 ,- 0.107043502580043 ,0.743098244369843
                    ,0.075665395931411 ,0.366662136199027 ,0.301325496735494 ,- 0.127959853885404
            },
            {
                    0.176761191401067 ,0.163276217872526 ,- 0.090326263481081 ,- 0.179551087083123
                    ,0.363256815341333 ,0.329970109430809 ,- 0.051879221066062 ,0.256728747731346
                    ,0.277043384431290 ,0.104113728292426 ,0.139981371677002 ,- 0.742130479625641
                    ,- 0.034524073033884 ,- 0.343286483781855 ,- 0.306301246917888 ,0.117604350114848
            },
            {
                    -0.145793544926800 ,- 0.103188494699912 ,0.058788410241278 ,0.051655038640426
                    ,- 0.264038008105031 ,- 0.203541647757149 ,0.090418018220672 ,- 0.064107759386820
                    ,- 0.219857426756124 ,- 0.016087922861208 ,- 0.104557302929551 ,0.488954502811962
                    ,0.057922132921064 ,0.258682714654372 ,0.111924825898909 ,- 0.052708836311705
            },
            {
                    0.347740013692901 ,0.186040292334817 ,0.031705891858216 ,- 0.245707324109423
                    ,0.427598565702216 ,0.270498331607873 ,0.111400473365868 ,0.327817668038880
                    ,0.302461445968564 ,0.027512957314741 ,- 0.060711514345127 ,- 0.925728053394680
                    ,- 0.033117806958892 ,- 0.380932346698436 ,- 0.493071060049599 ,0.328947236201250
            },
            {
                    -0.173318365340601 ,- 0.077142569984113 ,- 0.076310560438528 ,0.089225994308972
                    ,- 0.002018880643991 ,- 0.013163178758400 ,- 0.064330936170142 ,- 0.237122798059981
                    ,- 0.172523534080677 ,- 0.064784833527950, 0.277209132093551 ,0.334394267761285
                    ,- 0.120700446096625 ,0.092203859487363, 0.485868651300583 ,- 0.262678175365571
            },
            {
                    0.051245491573803 ,0.047735585799314 ,0.019889459157673 ,- 0.055381465402088
                    ,0.171358401700705 ,0.049314885561074 ,- 0.021834316287122 ,0.033901985721205
                    ,0.121062561797019 ,0.095099592970483 ,0.052994957136868 ,- 0.246427467822199
                    ,- 0.029773768604971 ,- 0.153276054566094 ,- 0.084954401825375 ,0.052024137604019
            },
            {
                    -0.153612088210597 ,- 0.058212221217499 ,0.096467473348521 ,0.129389831134947
                    ,- 0.166369564539914 ,- 0.163654572235491 ,- 0.019395649590667 ,- 0.140682220131554
                    ,- 0.071530405390647 ,- 0.016836777394500 ,- 0.063409311447827 ,0.372333201408698
                    ,- 0.061510980613705 ,0.172726336659603 ,0.223722169457708 ,- 0.120402896064248
            }
    };

    // Ket Matrix provided [16]*[16]
    private  double[][] keyMatrix = {
            {8.000, 4.000, 4.000 ,8.000 ,7.000, 8.000, 7.000 ,1.000 ,9.000 ,4.000 ,1.000 ,1.000, 1.000, 6.000, 3.000,0.000},
            {9.000,6.000,0.000,0.000,5.000,4.000,2.000,3.000,1.000,4.000,7.000,8.000,2.000,6.000,0.000,5.000},
            {0.000,7.000,2.000,4.000,4.000,9.000,4.000,0.000,1.000,1.000,3.000,1.000,2.000,7.000,0.000 ,7.000},
            {9.000,5.000,9.000,7.000,8.000,3.000,2.000,6.000,5.000,5.000,6.000,1.000,6.000,8.000,4.000,6.000},
            {5.000,1.000,7.000,1.000,1.000,0.000,2.000,9.000,8.000,6.000,0.000,9.000,5.000,5.000,6.000,3.000},
            {3.000,9.000,8.000,4.000,0.000,4.000,5.000,0.000,1.000,0.000,9.000,6.000,6.000,7.000,0.000,7.000},
            {7.000,1.000,8.000,3.000,1.000,0.000,2.000,1.000,0.000,1.000,3.000,3.000,7.000,5.000,5.000,3.000},
            {6.000,1.000,4.000,3.000,9.000,5.000,1.000,2.000,9.000,7.000,5.000,4.000,2.000,5.000,0.000,0.000},
            {9.000,3.000,7.000,5.000,0.000,4.000,4.000,2.000,5.000,9.000,3.000,1.000,6.000,6.000,7.000,4.000},
            {3.000,2.000,3.000,4.000,4.000,1.000,5.000,1.000,1.000,0.000,6.000,5.000,1.000,2.000,8.000,3.000},
            {6.000,6.000,0.000,7.000,2.000,7.000,1.000,0.000,2.000,6.000,4.000,2.000,2.000,9.000,4.000,0.000},
            {9.000,7.000,7.000,3.000,1.000,5.000,1.000,0.000,9.000,5.000,4.000,9.000,3.000,6.000,6.000,0.000},
            {6.000,4.000,9.000,3.000,9.000,7.000,8.000,8.000,3.000,8.000,2.000,1.000,5.000,9.000,5.000,2.000},
            {4.000,2.000,1.000,7.000,0.000,2.000,7.000,9.000,2.000,8.000,5.000,7.000,6.000,7.000,1.000,9.000},
            {4.000,2.000,4.000,2.000,9.000,4.000,6.000,2.000,2.000,6.000,3.000,0.000,6.000,7.000,3.000,2.000},
            {5.000,9.000,1.000,4.000,2.000,3.000,1.000,0.000,0.000,8.000,5.000,6.000,9.000,9.000,7.000,0.000}
    };
    private List<double[][]> MatrixList;
    private List<double[][]> encodedMatrixList;

    private List<String> encodedTextList;
    private String[] binaryTxtList;
    private double[] doubleMatrix;
    private double[][] firstMatrix;
    private double[][] secondMatrix;
    private String encodedText;

    /**
     * This method perofrm the core for the encryption process
     *
     * @param plainText     Original text from the user
     * @return String       encrypted Text
     */
    @Override
    public String encrypte(String plainText) {
        char letter;
        MatrixList = new ArrayList<>();
        binaryTxtList = new String[plainText.length()];
        doubleMatrix = new double[binaryTxtList.length];
        encodedText = "";
        // loop through the text and append the binary represenation of the ASCII code for each char at the plainText
        for(int i=0 ; i < plainText.length();i++){
            //Convert the current Char into Binary
            String binartText = getStringBinary(plainText.charAt(i));

            //append to the List
            this.binaryTxtList[i] = binartText;

            //multiply the binary matrix [1][16] for each char by the key matrix
            //append to the Matrix list
            this.MatrixList.add(multiplyMatrices(getMatrix(this.binaryTxtList[i]),this.keyMatrix));

            System.out.println(getCharacterfromMatrix((this.MatrixList.get(i))));

            //Convert the output matrices after multiplication by the key matrix into text
            this.encodedText +=  getCharacterfromMatrix((this.MatrixList.get(i)));
        }

        // print encodedText for debug purpose
        //System.out.println(this.encodedText);
        return this.encodedText;
    }


    /**
     * This method convert letter into the binary representation of its ASCII code
     *
     * @param letter     character of the originalText
     * @return String     Binary representation
     */
    private String getStringBinary(char letter){
        int aschiiNumber = letter;
        String binary = Integer.toBinaryString(aschiiNumber);
        //Debug purpose
        //System.out.println(aschiiNumber+" - "+"0000000000000000".substring(binary.length())+binary);
        return "0000000000000000".substring(binary.length())+binary;

    }

    /**
     * This method convert binary representation for character into [0][16] matrix
     *
     * @param binaryText     binary representation
     * @return double[][]     Matrix rows : 0 , cols : 16
     */
    private double[][] getMatrix(String binaryText){

        this.firstMatrix = new double[1][binaryText.length()];
        for(int i=0;i < binaryText.length();i++){
            this.firstMatrix[0][i] = Character.digit(binaryText.charAt(i),10);
        }
        return this.firstMatrix;
    }


    /**
     * This method convert binary representation for encoded character into [0][16] matrix
     *
     * @param binaryText     binary representation
     * @return double[][]     Matrix rows : 1 , cols : 16
     */
    private double[][] getdecrypteMatrix(String binaryText){

        this.firstMatrix = new double[1][binaryText.length()];
        for(int i=0;i < binaryText.length();i++){
            this.firstMatrix[0][i] = ((int) binaryText.charAt(i))-65;
        }
        return this.firstMatrix;
    }

    /**
     * This method perform the matrices multiplication operation
     * and add 65 for each cell so could be converted latter to encoded text
     *
     * @param firstMatrix     matrix for each char [1][16]
     * @param secondMatrix    key Matrix [16][16]
     * @return double[][]     Matrix rows : 1 , cols : 16
     */
    private double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix){
        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];

        for (int row = 0 ; row < result.length; row++){
            for (int col = 0 ; col < result[row].length;col++){
                result[row][col] = multiplyMatrixElement(firstMatrix,secondMatrix,row,col)+65;
            }
        }
        return  result;
    }


    /**
     * This method perform the matrices multiplication operation
     * without adding any numbers for the output cells
     *
     * @param firstMatrix     matrix for each char [1][16]
     * @param secondMatrix    key Matrix [16][16]
     * @return double[][]     Matrix rows : 1 , cols : 16
     */
    private double[][] multiplyEncodedMatrices(double[][] firstMatrix, double[][] secondMatrix){
        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];

        for (int row = 0 ; row < result.length; row++){
            for (int col = 0 ; col < result[row].length;col++){
                result[row][col] = multiplyMatrixElement(firstMatrix,secondMatrix,row,col);
            }
        }
        return  result;
    }


    /**
     * This method perform the matrices multiplication operation for each row with column same size
     *
     * @param firstMatrix     matrix for each char [1][16]
     * @param secondMatrix    key Matrix [16][16]
     * @param row    row number from the firstMatrix
     * @param col    col number from the Key Matrix
     * @return double     double value
     */
    private double multiplyMatrixElement(double[][] firstMatrix,double[][] secondMatrix, int row , int col){
        double value = 0;
        for (int i =0 ; i < secondMatrix.length;i++){
            value += (firstMatrix[row][i]*secondMatrix[i][col]);
        }

        return value;
    }


    /**
     * This method extract the Binary string equivalent to the matrix
     *
     * @param matrix     matrix for each char [1][16]
     * @return String   Binary of the matrix elements
     */
    private String getNumberfromBinary(double[][] matrix){
        String binaryOfMatrix = "";
        for (int i =0; i < matrix[0].length ; i++){
            binaryOfMatrix += (int)Math.round(matrix[0][i]);
        }
        return binaryOfMatrix;
    }

    /**
     * This method extract the original string equivalent to the binary matrix
     *
     * @param matrix     matrix for each char [1][16]
     * @return String   characters for each ASCII code
     */
    private String getCharacterfromMatrix(double[][] matrix){
        StringBuilder binaryOfMatrix = new StringBuilder();
        for (int i =0; i < matrix[0].length ; i++){
            binaryOfMatrix = binaryOfMatrix.append(Character.toChars((int)Math.round(matrix[0][i])));
        }
        return binaryOfMatrix.toString();
    }

    /**
     * This method build a list of matrices from the list of encrypted text
     *
     * @param //List     list of encrypted text
     * @return List   characters for each ASCII code
     */
    private List<double[][]> getMatrixfromEncryptedText(List<String> textList){
        for(int i = 0 ; i < textList.size();i++){
            this.encodedMatrixList.add(getdecrypteMatrix(textList.get(i)));
        }
        return  this.encodedMatrixList;
    }



    /**
     * This method build a list of String from the list of encrypted text , slice every 16 character
     *
     * @param encodedText     encryptedText
     * @return List   List of the encrypted Text
     */
    private List<String> getListForEncodedText(String encodedText){
        char letter;
        encodedTextList = new ArrayList<>();
        String oneElement = "";

        for(int i = 1 ; i <= encodedText.length();i++){
            letter = encodedText.charAt(i-1);
            if( i % 16 == 0 && i > 0){
                oneElement += letter;
                this.encodedTextList.add(oneElement);
                oneElement = "";
            }else{
                oneElement+= letter;
            }
        }

        return encodedTextList;
    }


    /**
     * This method perform the decryption process core
     *
     * @param //encoded_Text     encryptedText
     * @return String   decodedText ( original )
     */
    @Override
    public String decrypte(String text){

        String decodeText = "";
        MatrixList = new ArrayList<>();
        encodedMatrixList = new ArrayList<>();

        binaryTxtList = new String[text.length()];
        doubleMatrix = new double[binaryTxtList.length];

        //build a list of matrices from the list of encrypted text and assign to class variable ( encodedMatrixList )
        getMatrixfromEncryptedText(getListForEncodedText(text));

        for(int i=0;i < this.encodedMatrixList.size();i++){
            //Debug Purpose
            //System.out.println(Arrays.deepToString(this.encodedMatrixList.get(i)));
            this.MatrixList.add(multiplyEncodedMatrices(this.encodedMatrixList.get(i),this.inverseMatrix));
            decodeText += (char) Integer.parseInt(getNumberfromBinary(this.MatrixList.get(i)),2);
        }

        return decodeText ;
    }



}
