




public class Hill extends helper {
    int keyNumArray;
     public static void main(String[] args) { 
              Hill();
     }
     
        
     protected static void Hill()
     {
                  boolean bo = true;
                  
                  while(bo){
                    System.out.print("\n 1- Hill encription \n" 
                         + " 2- Hill decription \n"
                         + " 3- Back \n"
                         + " 4- Exit \n"
                     );
              
                 System.out.print("Enter num option : ");
                 int numOptionFromList =  in.nextInt();

                switch(numOptionFromList){
                    case 1 :
                        System.out.print("Enter plaintext : ");
                        Scanner  plainIn = new Scanner(System.in);
                        String plaintext =  plainIn.nextLine().toLowerCase();
                        
                        System.out.print("Enter m (length matrix) : ");
                        int m =  in.nextInt();
                        
                        System.out.print("Enter mod : ");
                        int mod =  in.nextInt();
                        
                        System.out.println("Is the key a matrix ? (yes or no) ");
                        Scanner  qus = new Scanner(System.in);
                        String ans =  qus.nextLine().toLowerCase();
                        
                        String res = null;
                        if(ans.equals("yes")){
                            System.out.println("Enter key matrix : ");
                             // set matrix in array  
                            int [][] matrix = new int[m][m];

                             for(int row = 0 ; row < m ; row++ ){
                                 System.out.println("***************** Row "+ row+" *************************");
                                 for(int col = 0 ; col < m ; col++){
                                     System.out.print("Enter value (int) row "+row+ " col "+col+" : ");
                                     Scanner sc = new Scanner(System.in);
                                     matrix[row][col] =  sc.nextInt();
                                 }
                             }
                             
                             res = encriptionHillWhenKeyIsMatrix( plaintext,matrix, mod,m);
                             
                        }else{
                            System.out.print("Enter key (Text) : ");
                            Scanner  keyIn = new Scanner(System.in);
                            String key =  keyIn.nextLine().toLowerCase();
                             res = encriptionHill(plaintext,key, mod ,m);
                        }
                        

                       
                       
                        
                       
                        if(res != null){
                             System.out.println("\n************ Result ***********");
                             System.out.println("Ciphertext => " + res.toUpperCase());    
                             System.out.println("*******************************");  
                        }
                      
                        break;
                        
                case 2 : 
                        System.out.print("Enter ciphertext : ");
                        Scanner  cipherIn = new Scanner(System.in);
                        String ciphertext =  cipherIn.nextLine().toLowerCase();

                         System.out.print("Enter m (length matrix) : ");
                         int M =  in.nextInt();
                        
                         
                          System.out.print("Enter mod : ");
                          int MOD =  in.nextInt();
                        
                       

                        
                        System.out.println("Is the key a matrix ? (yes or no) ");
                        Scanner  Qus = new Scanner(System.in);
                        String Ans =  Qus.nextLine().toLowerCase();
                        
                        String Res = null;
                        if(Ans.equals("yes")){
                            System.out.println("Enter key matrix : ");
                             // set matrix in array  
                            int [][] matrix = new int[M][M];

                             for(int row = 0 ; row < M ; row++ ){
                                 System.out.println("***************** Row "+ row+" *************************");
                                 for(int col = 0 ; col < M ; col++){
                                     System.out.print("Enter value (int) row "+row+ " col "+col+" : ");
                                     Scanner sc = new Scanner(System.in);
                                     matrix[row][col] =  sc.nextInt();
                                 }
                             }
                             
                             Res = decriptionHillWhenKeyIsMatrix( ciphertext,matrix, MOD,M);
                             
                        }else{
                            System.out.print("Enter key (Text) : ");
                            Scanner  keyIn = new Scanner(System.in);
                            String key =  keyIn.nextLine().toLowerCase();
                             Res = encriptionHill(ciphertext,key, MOD ,M);
                        }
                        

                       
                       
                        
                        if(Res != null){
                             System.out.println("\n************ Result ***********");
                             System.out.println("Plaintext => " + Res.toUpperCase());    
                             System.out.println("*******************************");  
                        }
    
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
     
     
     
        private static int[][] prepareHillPlainOrCipherText(int strLength,int AlpahNumArray[],int m)
        {
              int alpha2D[][] = new int[m][ (int)(strLength / m)];
              int count = 0;
              for(int i = 0;i < alpha2D[0].length;i++){
                for(int j = 0;j < alpha2D.length;j++){
                  alpha2D[j][i] = AlpahNumArray[count] ;
                  count++;
                }    
              }
              return alpha2D;
         }
          
        
         private static int[][] prepareHillKeyText(int strLength,int AlpahNumArray[],int m)
         {
              int alpha2D[][] = new int[m][ (int)(strLength / m)];
              int count = 0;
              for(int i = 0;i < alpha2D.length;i++){
                for(int j = 0;j < alpha2D[0].length;j++){
                  alpha2D[i][j] = AlpahNumArray[count] ;
                  count++;
                }    
              } 
              return alpha2D;
         }
          
          
          public static String encriptionHill(String p, String k,int mod,int m)
          { 
              int plainMod = p.length() % m;
              
              if(plainMod != 0){
                  plainMod = m - plainMod;
                  for(int i = 0 ; i < plainMod ; i++){
                      p += "x" ;
                  }
              }
              
             int plainNumArray[][] = searchInAlphaArray(p,m,"text");
             int keyNumArray  [][] = searchInAlphaArray(k,m,"key");
            
             if(! checkIfMatrixInverted(keyNumArray,m,mod)){
                 System.out.println("This matrix have not inverse !! ");
                 return null;
              } 

             
             String cipherText = "";
             String alpa[] = alphabetic.split("");
         
             for(int colP = 0 ; colP < plainNumArray[0].length ; colP++  ){
                 int r[][] = new int[plainNumArray.length][1];
                   for(int rowP = 0 ; rowP < plainNumArray.length ; rowP++ ){
                     r[rowP][0] = plainNumArray[rowP][colP];
                    }
                
                      int resMulti2D[][] =  multiMatrix(keyNumArray,r);
                 
                  for(int i = 0 ; i < resMulti2D.length ; i++){
                    cipherText += alpa[(int) (resMulti2D[i][0] % mod) ];
                  }
                       
             }
             
              return cipherText;  
          }
          
          
          
           public static String encriptionHillWhenKeyIsMatrix(String p, int k[][],int mod,int m)
          { 
              int plainMod = p.length() % m;
              
              if(plainMod != 0){
                  plainMod = m - plainMod;
                  for(int i = 0 ; i < plainMod ; i++){
                      p += "x" ;
                  }
              }
              
             int plainNumArray[][] = searchInAlphaArray(p,m,"text");
             int keyNumArray  [][] = k;
            
             if(! checkIfMatrixInverted(keyNumArray,m,mod)){
                 System.out.println("This matrix have not inverse !! ");
                 return null;
              } 

             
             String cipherText = "";
             String alpa[] = alphabetic.split("");
         
             for(int colP = 0 ; colP < plainNumArray[0].length ; colP++  ){
                 int r[][] = new int[plainNumArray.length][1];
                   for(int rowP = 0 ; rowP < plainNumArray.length ; rowP++ ){
                     r[rowP][0] = plainNumArray[rowP][colP];
                    }
                
                      int resMulti2D[][] =  multiMatrix(keyNumArray,r);
                 
                  for(int i = 0 ; i < resMulti2D.length ; i++){
                    cipherText += alpa[(int) (resMulti2D[i][0] % mod) ];
                  }
                       
             }
             
              return cipherText;  
          }
          
          
          
         public static String decriptionHill(String c, String k,int mod,int m)
         {
                    
              
              int cipherMod = c.length() % m;
              
              if(cipherMod != 0){
                  cipherMod = m - cipherMod;
                  for(int i = 0 ; i < cipherMod ; i++){
                      c += "x" ;
                  }
              }
              
              
             int cipherNumArray[][] = searchInAlphaArray(c,m,"text");
             int keyNumArray  [][] = searchInAlphaArray(k,m,"key");
             
             
             keyNumArray = inversesMatrixMod(keyNumArray,m ,mod);
             
             String plainText = "";
             String alpa[] = alphabetic.split("");
         
             for(int colP = 0 ; colP < cipherNumArray[0].length ; colP++  ){
                 int r[][] = new int[cipherNumArray.length][1];
                   for(int rowP = 0 ; rowP < cipherNumArray.length ; rowP++ ){
                     r[rowP][0] = cipherNumArray[rowP][colP];
                    }
                
                      int resMulti2D[][] =  multiMatrix(keyNumArray,r);
                 
                  for(int i = 0 ; i < resMulti2D.length ; i++){
                    plainText += alpa[(int) (resMulti2D[i][0] % mod) ];
                  }
                         
             }
             
              return plainText;  
          }
          
           
             public static String decriptionHillWhenKeyIsMatrix(String c, int k[][],int mod,int m){

                 int cipherMod = c.length() % m;
              
              if(cipherMod != 0){
                  cipherMod = m - cipherMod;
                  for(int i = 0 ; i < cipherMod ; i++){
                      c += "x" ;
                  }
              }
              
              
             int cipherNumArray[][] = searchInAlphaArray(c,m,"text");
             int keyNumArray  [][] = k;
             
             keyNumArray = inversesMatrixMod(keyNumArray,m ,mod);
             
             String plainText = "";
             String alpa[] = alphabetic.split("");
         
             for(int colP = 0 ; colP < cipherNumArray[0].length ; colP++  ){
                 int r[][] = new int[cipherNumArray.length][1];
                   for(int rowP = 0 ; rowP < cipherNumArray.length ; rowP++ ){
                     r[rowP][0] = cipherNumArray[rowP][colP];
                    }
                
                      int resMulti2D[][] =  multiMatrix(keyNumArray,r);
                 
                  for(int i = 0 ; i < resMulti2D.length ; i++){
                    plainText += alpa[(int) (resMulti2D[i][0] % mod) ];
                  }
                         
             }
             
              return plainText;  
            }
                    
                    
                    
        private static int[][] searchInAlphaArray(String s,int m,String flag)
        {
              String alpha[] = alphabetic.split("");
              String str[]   = s.split("");
              int AlpahNumArray[] = new int[str.length];
              int alpha2D[][] = new int[m][ (int)(str.length / m)];

              
              if(str.length % m == 0){
                  
              for(int j = 0 ; j < str.length ; j++){
                for( int i = 0 ;  i < alphabetic.length() ;i++ ){
                    if(alpha[i].equals(str[j]) ){
                        AlpahNumArray[j] = i;
                        break;
                    }   
                 }
             }
              
             
                if(flag.equals("key")){ 
                    return prepareHillKeyText(str.length,AlpahNumArray,m);

                }else{ // // plain or chipher
                   return  prepareHillPlainOrCipherText(str.length,AlpahNumArray,m);
                }
             
              }
              return alpha2D;
          }
        
        
      private static boolean checkIfMatrixInverted(int matrix[][],int n,int mod)
      {
         // check if matrix have inverse or not
        int determinant = determinantMatrixMod(matrix, n,mod);
          if( (determinant == 0) || (GCD(determinant,mod) != 1) ){
           return false;
          }    
           return true;
      }
        
}
