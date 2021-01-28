


  

  

public class Des extends helper {

  // test values
  // public static String key       =   "0001001100110100010101110111100110011011101111001101111111110001";
  // public static String plaintext =   "0000000100100011010001010110011110001001101010111100110111101111";
  // public static String cipherText =  "1000010111101000000100110101010000001111000010101011010000000101";
    
   // test value 2
   // public static String key       =   "1101111000010000100111000101100011101000101001001010011000110000";
   // public static String plaintext =   "0101011011101001100111101010110011011110010111111111010010110001";
   // public static String cipherText =  "1101100000011100001001001010111001110100000010110110011011000001";
    
    public static int pc_1[][] = { {57,49,41,33,25,17,9},{1,58,50,42,34,26,18},{10,2,59,51,43,35,27},{19,11,3,60,52,44,36},{63,55,47,39,31,23,15},{7,62,54,46,38,30,22},{14,6,61,53,45,37,29},{21,13,5,28,20,12,4}   }; 
    
    public static int shift_table [] = {1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};
       
    public static int pc_2[][] = { {14,17,11,24,1,5},{3,28,15,6,21,10},{23,19,12,4,26,8},{16,7,27,20,13,2},{41,52,31,37,47,55},{30,40,51,45,33,48},{44,49,39,56,34,53},{46,42,50,36,29,32}   }; 

    public static int ip[][] =   {  {58,50,42,34,26,18,10,2},
                                    {60,52,44,36,28,20,12,4},
                                    {62,54,46,38,30,22,14,6},
                                    {64,56,48,40,32,24,16,8},
                                    {57,49,41,33,25, 17,9,1},
                                    {59,51,43,35,27,19,11,3},
                                    {61,53,45,37,29,21,13,5},
                                    {63,55,47,39,31,23,15,7} };
    
    
    public static int E_Bit[][] = { 
    {  32   ,  1  ,  2   , 3   ,  4 ,    5},
    {   4   ,  5  ,  6   ,  7  ,   8 ,   9},
    {   8   ,  9  , 10   , 11  ,  12 ,  13},
    {  12   , 13  , 14   , 15  ,  16 ,  17},
    {  16   , 17  , 18   , 19  ,  20 ,  21},
    {  20   , 21  , 22   ,  23 ,  24  , 25},
    {  24   , 25  , 26   ,  27 ,  28  , 29},
    {  28   , 29  , 30   ,  31 ,  32  , 1}};
    
    
    
    
     public static int s1[][] = 
          {{14 , 4  , 13 , 1 ,  2 ,15 ,  11 , 8 ,  3 ,10  , 6  ,12 ,  5 , 9  ,  0,  7},
           {0  , 15 ,  7 , 4 , 14 , 2 , 13  , 1 , 10 , 6  , 12 ,11 ,  9 , 5  , 3 , 8},
           {4  , 1  , 14 , 8 , 13 , 6 ,  2  ,11 , 15 ,12  , 9  , 7 , 3  ,10  , 5 , 0},
           {15 , 12 ,  8 , 2 ,  4 , 9 ,  1  , 7 ,  5 , 11 , 3  ,14 , 10 , 0  , 6 , 13}};
    
     public static int s2[][] = {
     {  15 , 1  , 8  ,14  ,  6   , 11  , 3  , 4  , 9  , 7   ,2   ,13  ,12 , 0  , 5 ,10},
     {  3  ,13  , 4  , 7  ,  15  , 2   , 8  , 14 , 12 , 0   , 1  ,10  ,6  , 9  ,11 , 5},
     {  0  ,14  , 7  ,11  ,  10  , 4   , 13 , 1  , 5  , 8   , 12 , 6  , 9 , 3  , 2 ,15},
     { 13  , 8  , 10 , 1  ,  3   , 15  , 4  , 2  , 11 , 6   , 7  ,12  , 0 , 5  ,14 , 9}};
     
    
    
     public static int s3[][] = {
         {10 , 0  , 9 ,14  , 6 , 3 , 15 , 5  , 1 ,13  ,12,  7 , 11 , 4 ,  2 , 8},
         {13  ,7  , 0,  9  , 3 , 4  , 6 ,10 ,  2 , 8  , 5 ,14  ,12 ,11  ,15  ,1},
         {13 , 6 ,  4 , 9  , 8 ,15 ,  3 , 0 , 11 , 1  , 2, 12  , 5 ,10  ,14 , 7},
         {1, 10  ,13 , 0 ,  6  ,9   ,8 , 7 ,  4 ,15  ,14 , 3  ,11 , 5  , 2 ,12}};
    
    
    public static int s4[][] = {
        {7 ,13 , 14 , 3 ,  0  ,6  , 9 ,10  , 1 , 2  , 8  ,5, 11, 12 ,  4 ,15},
        {13 , 8 , 11 , 5  , 6, 15 ,  0 , 3 ,  4 , 7 ,  2 ,12  , 1 ,10 , 14 , 9},
        {10 , 6 ,  9 , 0 , 12, 11  , 7 ,13,  15,  1  , 3, 14  , 5  ,2 ,  8 , 4},
        {3 ,15  , 0 , 6  , 10 , 1 , 13 , 8  , 9 , 4 ,  5 ,11 , 12 , 7 ,  2 ,14}};
    
    
   public static int s5[][] = {
           {2 ,12 ,  4 , 1  , 7 ,10,  11 , 6 ,  8 , 5   ,3 ,15 , 13 , 0 , 14 , 9},
           {14 ,11  , 2 ,12,   4 , 7 , 13 , 1 ,  5 , 0 , 15 ,10 ,  3  ,9 ,  8,  6},
           {4 , 2,   1 ,11 , 10 ,13  , 7 , 8 , 15 , 9  ,12 , 5 ,  6  ,3  , 0, 14},
           {11 , 8 , 12,  7  , 1, 14  , 2 ,13 ,  6 ,15 ,  0 , 9 , 10 , 4  , 5  ,3}};
   
   
      public static int s6[][] = {
              { 12,  1,  10, 15 ,  9 ,2 ,  6 , 8 ,  0 ,13 ,  3 , 4 , 14 , 7  , 5, 11},
              {10 ,15  , 4 , 2  , 7 ,12 ,  9 , 5  , 6 , 1 , 13, 14  , 0 ,11 ,  3 , 8},
              { 9, 14,  15 , 5  , 2 , 8 , 12 , 3 ,  7,  0 ,  4 ,10  , 1, 13 , 11,  6},
              {4 , 3 ,  2 ,12  , 9 , 5,  15, 10 , 11, 14 ,  1 , 7 ,  6 , 0  , 8 ,13}};
   
     public static int s7[][] = {
        {  4 ,11 ,  2, 14 , 15 , 0  , 8, 13  , 3 ,12  , 9 , 7  , 5 ,10  , 6 , 1},
        {  13 , 0 , 11 , 7 ,  4 , 9  , 1, 10 , 14 , 3  , 5 ,12  , 2 ,15  , 8,  6},
        {  1 , 4 , 11, 13 , 12 , 3 ,  7 ,14 , 10, 15 ,  6 , 8 ,  0 , 5  , 9 , 2},
        {  6, 11,  13,  8 ,  1 , 4 , 10 , 7 ,  9 , 5 ,  0 ,15  ,14 , 2 ,  3 ,12}
     };

     public static int s8[][] = {
             {13 , 2 ,  8 , 4 ,  6, 15 , 11 , 1 , 10 , 9 ,  3, 14  , 5,  0 , 12 , 7},
             {1, 15,  13 , 8 , 10 , 3 ,  7 , 4 , 12 , 5 ,  6 ,11 ,  0 ,14 ,  9,  2},
             {7, 11  , 4 , 1 ,  9 ,12  ,14 , 2  , 0 , 6 , 10 ,13 , 15 , 3 ,  5 , 8},
             {2 , 1 , 14 , 7  , 4 ,10 ,  8, 13 , 15 ,12 ,  9 , 0 ,  3 , 5 ,  6, 11}
       }; 
     
     public static int p[][] = {
             {16 ,  7  ,20 , 21},
             {29 , 12 , 28 , 17},
             {1 , 15,  23 , 26},
             {5 , 18 , 31 , 10},
             {2  , 8 , 24 , 14},
             {32 , 27  , 3  , 9},
             {19  ,13,  30 ,  6},
             {22 , 11  , 4 , 25}
       }; 
     
     
      public static int ip_inverse[][]={
            {40   ,  8 ,  48 ,   16 ,   56 ,  24 ,   64,   32},
            {39   ,  7  , 47  ,  15  ,  55  , 23  ,  63  , 31},
            {38   ,  6 ,  46 ,   14 ,   54  , 22  ,  62 ,  30},
            {37   ,  5  , 45  ,  13 ,   53 ,  21  ,  61  , 29},
            {36   ,  4  , 44  ,  12 ,   52 ,  20  ,  60 ,  28},
            {35   ,  3  , 43  ,  11  ,  51 ,  19  ,  59  , 27},
            {34   ,  2  , 42  ,  10  ,  50 ,  18 ,   58 ,  26},
            {33   ,  1 ,  41   ,  9   , 49  , 17  ,  57  , 25}
   };
     
      
    public static void main(String[] args) { 
              Des();
     }
     
    
    
    protected static void Des()
     {
                  boolean bo = true;
                  
                  while(bo){
                    System.out.print("\n 1- DES encription \n" 
                         + " 2- DES decription \n"
                         + " 3- Back \n"
                         + " 4- Exit \n"
                     );
              
                 System.out.print("Enter num option : ");
                 int numOptionFromList =  in.nextInt();

                switch(numOptionFromList){
                    case 1 :
                        boolean bo2 = true;
                        String plaintext = "";
                        while(bo2){
                           System.out.print("Enter plaintext (binary 64 bit) : ");
                           Scanner  plainIn = new Scanner(System.in);
                            plaintext =  plainIn.nextLine().toLowerCase();  
                           if(plaintext.length() == 64 ){
                               bo2 = false;
                           }else{
                               System.out.println("Please enter plaintext length (64 bit) !! ");
                           }
                        }
                        
                        bo2 = true;
                        String key = "";
                        while(bo2){
                           System.out.print("Enter key (binary 64 bit) : ");
                           Scanner  keyIn = new Scanner(System.in);
                            key =  keyIn.nextLine().toLowerCase();  
                           if(key.length() == 64 ){
                               bo2 = false;
                           }else{
                               System.out.println("Please enter key length (64 bit) !! ");
                           }
                        }    
                        
                        
                        encriptionDes(plaintext,key);
                        
                        break;
                        
                    case 2 :  
                        boolean bo3 = true;
                        String ciphertext = "";
                        while(bo3){
                           System.out.print("Enter ciphertext (binary 64 bit) : ");
                           Scanner  cipherIn = new Scanner(System.in);
                            ciphertext =  cipherIn.nextLine().toLowerCase();  
                           if(ciphertext.length() == 64 ){
                               bo3 = false;
                           }else{
                               System.out.println("Please enter ciphertext length (64 bit) !! ");
                           }
                        }
                        
                        bo3 = true;
                        String Key = "";
                        while(bo3){
                           System.out.print("Enter key (binary 64 bit) : ");
                           Scanner  keyIn = new Scanner(System.in);
                            Key =  keyIn.nextLine().toLowerCase();  
                           if(Key.length() == 64 ){
                               bo3 = false;
                           }else{
                               System.out.println("Please enter key length (64 bit) !! ");
                           }
                        }    
                        
                        
                        decriptionDes(ciphertext,Key);
                        break;         
              case 3 : 
                  bo = false; 
                 break;
                 
                 case 4 : 
                  System.exit(0); 
                 break;
             }
            }
     }
    
    
    
    
    
    
    
     protected static void encriptionDes(String plaintext , String key ){
        String keys [] = GenrateKey(key); // 16 key
        String ciphertext = plainText( plaintext,keys);  
        System.out.println("\n************ Result ***********");
        System.out.println("Ciphertext => " + ciphertext.toUpperCase());    
        System.out.println("*******************************");  
     }
    
    
      protected static void decriptionDes(String ciphertext , String key ){
        String keys [] = GenrateKey(key); // 16 key
        keys =reverseArray(keys); // inverse 16 keys 
        
        String plaintext = plainText(ciphertext,keys);  
        System.out.println("\n************ Result ***********");
        System.out.println("Plaintext => " + plaintext.toUpperCase());    
        System.out.println("*******************************");  
     }
      
    public static String [] reverseArray(String a[]) 
    { 
        String[] b = new String[a.length]; 
        int j = a.length; 
        for (int i = 0; i < a.length; i++) { 
            b[j - 1] = a[i]; 
            j = j - 1; 
        } 
  
       return b;
    } 
     
     
    protected static String  plainText(String plaintext,String keys[]){
                     char plainArr[] = plaintext.toCharArray(); // 64 bit
                     String plainAfterIp =  String.valueOf(order(plainArr,64,ip));

                     String left[] = new String[17]; 
                     String right[] = new String[17]; 
                     
                     left[0] = plainAfterIp.substring(0,32);
                     right[0] = plainAfterIp.substring(32);
                     
                   
                     for(int i = 1 ; i < right.length;i++ ){
                         left[i] = right[(i-1)];
                         right[i] = xor( left[i-1] , getF(right[(i-1)],keys[i-1]));
                     }
                     // swap r16l16
                     String swap = right[16] + left[16];
                     return String.valueOf(order(swap.toCharArray(),64,ip_inverse)); // 64 bit
       }
     
      protected static String  getF(String r,String key){
                   r = extendedR(r); // r= 48 bit
                   String Rxorkey = xor(r,key); // 48 bit
                   
                   
                   String s_box_arr []= new String[8];

                   for(int i=0;i < s_box_arr.length;i++){
                       s_box_arr[i] = Rxorkey.substring(i*6,(i*6)+6);
                   }
                   
                  
                  s_box_arr =  s_Box(s_box_arr);
                   
                   
                  String resultAfterBoxes = "";
                  for(int i = 0;i < s_box_arr.length;i++){
                      resultAfterBoxes += s_box_arr[i];
                  }
                  
                  return String.valueOf(order(resultAfterBoxes.toCharArray(),32,p));
          }
                
               
               
               
             protected static String []  s_Box(String boxArr[]){
                     // convert 48 bit to 32 bit          
               for(int i=0;i < boxArr.length;i++){
                   String s =  boxArr[i];
                   String start_end_char = String.valueOf( s.charAt(0)) + String.valueOf( s.charAt(5))   ;
                   String bettwen_start_end = s.substring(1,5);
                   
                 int row =   binaryToDecimal( Integer.parseInt(start_end_char) );
                 int col =   binaryToDecimal(Integer.parseInt(bettwen_start_end));
                   
                 int result =  search_in_S_Boxes( row, col, i);
                    boxArr[i] = decimalToBinary(result);
                    if(boxArr[i].length() < 4){
                        int size = (4 - boxArr[i].length());
                        for(int k = 0;k < size ;k++ ){
                            boxArr[i] = "0" + boxArr[i];
                        }
                    }
               }
                    return boxArr;
               }
             
             
             
             
              protected static int  search_in_S_Boxes(int row,int col,int s){
                              int result = 0;
                              switch(s){
                                    case 0 : 
                                         result = s1[row][col];
                                        break ;
                                        
                                    case 1:    
                                         result = s2[row][col];
                                        break ;
                                        
                                    case 2 : 
                                         result = s3[row][col];
                                        break ;
                                        
                                    case 3:    
                                         result = s4[row][col];
                                        break ;
                                        
                                    case 4 : 
                                         result = s5[row][col];
                                        break ;
                                        
                                    case 5:    
                                         result = s6[row][col];
                                        break ;
                                        
                                    case 6 : 
                                         result = s7[row][col];
                                        break ;
                                        
                                    case 7:    
                                         result = s8[row][col];
                                        break ;  
                                 }
                              return result;
               }
                          
              
              
          public static int binaryToDecimal(int n)
          {
                int num = n;
                int dec_value = 0;

                
                int base = 1;

                int temp = num;
                while (temp > 0) {
                    int last_digit = temp % 10;
                    temp = temp / 10;

                    dec_value += last_digit * base;

                    base = base * 2;
                }

                return dec_value;
          }
          
          
        public static String decimalToBinary(int decimal){    
            int binary[] = new int[40];    
            int index = 0;    
            while(decimal > 0){    
              binary[index++] = decimal%2;    
              decimal = decimal/2;    
            }    
            String result = "";
            for(int i = index-1;i >= 0;i--){    
                result +=  binary[i];
            }    
           return result;
       }
               
        
        
        protected static String  extendedR(String r){
                   String x =  String.valueOf(order(r.toCharArray(),48,E_Bit));
                   return x;
        }
     
        
     
       protected static String  xor (String n1,String n2){
                    StringBuilder sb = new StringBuilder();
                    for(int i = 0; i < n1.length(); i++)
                        sb.append((n1.charAt(i) - '0' ^ n2.charAt(i) - '0' ));

                      return  sb.toString();
       }
     
     
     
     
     
        protected static String [] GenrateKey(String key){
         char keyArr[] = key.toCharArray(); // 64 bit
         char keyAfterPc1[] = pc_1(keyArr); // 56 bit  
         String keysAfterShift[] = left_shift(keyAfterPc1); // 16 key 
         String keyAfterPc2[] = pc_2(keysAfterShift); // finally 16 key !!    
         return keyAfterPc2;
        }
      
        
          
          protected static char[] pc_1(char keyArr[]){
               return order(keyArr,56,pc_1);    
          }
          
          
          protected static String [] pc_2(String keysArr[]){
              for(int i = 1;i<=16;i++){
                  keysArr[i] =  String.valueOf(order(keysArr[i].toCharArray(),48,pc_2));    
              }
                  return Arrays.copyOfRange(keysArr, 1, keysArr.length);   
          }
          
          
          protected static char[] order(char keyArr[],int lengthResult,int pArray[][]){
                    char after_PC_1[] = new char[lengthResult];
                    int i = 0;
                    for(int row = 0; row <  pArray.length  ; row++){
                        for(int col = 0; col < pArray[0].length ; col++){
                            int indexNum = pArray[row][col] -1 ;
                            after_PC_1[i] = keyArr[indexNum];
                            i++;
                        }
                    }

                    return after_PC_1;
          }
               
               
                       
             protected static String[] left_decription_shift(char keyAfterPc1[]){
             // shift left
                String keys[] = new String[17];  
                keys[0] = String.valueOf(keyAfterPc1);
  
                int count = 1;
                for(int i=16;i >= 1 ; i-- ){
                    int shiftNum = shift_table[(i-1)];
                    
                   String keyBeforShift = keys[(count-1)];
                   
                  String left = keyBeforShift.substring(0,28);
                  String right = keyBeforShift.substring(28,56);

                  String leftShift = left.substring(0,shiftNum);
                  String rightShift = right.substring(0,shiftNum);
                   
                  keys[count] = (left.substring(shiftNum)+leftShift) + (right.substring(shiftNum)+rightShift);   
                  count++;
                }
              return keys;
          }             
             
             
           protected static String[] left_shift(char keyAfterPc1[]){
             // shift left
                String keys[] = new String[17];  
                keys[0] = String.valueOf(keyAfterPc1);
  
                for(int i=1;i <= 16 ; i++ ){
                    int shiftNum = shift_table[(i-1)];
                   String keyBeforShift = keys[(i-1)];
                   
                  String left = keyBeforShift.substring(0,28);
                  String right = keyBeforShift.substring(28,56);

                  String leftShift = left.substring(0,shiftNum);
                  String rightShift = right.substring(0,shiftNum);
                   
                  keys[i] = (left.substring(shiftNum)+leftShift) + (right.substring(shiftNum)+rightShift);   
                }
              return keys;
          }  
     
     
}
