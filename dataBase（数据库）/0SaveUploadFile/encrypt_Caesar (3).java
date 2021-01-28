




public class Caesar extends helper {
    
        public static void main(String[] args) { 
              Caesar();
        }
        
        
        private static void Caesar()
        {
                boolean bo = true;
                  
                while(bo){

                    System.out.print("\n 1- Caesar encription \n" 
                         + " 2- Caesar decription \n"
                         + " 3- Back \n"
                         + " 4- Exit \n"
                     );
                    
                System.out.print("Enter num option : ");
                int numOptionFromList =  in.nextInt();

                switch(numOptionFromList){
                    case 1 :
                         System.out.print("Enter plaintxt  : ");
                         Scanner s = new Scanner(System.in);
                         String p =  s.nextLine();
                         p = p.toLowerCase();
                         System.out.print("Enter key (int) : ");
                         int k =  in.nextInt();
                         System.out.print("Enter mod (int) : ");
                         int mod =  in.nextInt();
                         String c =  encriptionCaesar(p,k,mod);
                         System.out.println("\n************ Result ***********");
                         System.out.println("Ciphertext => " + c.toUpperCase());    
                         System.out.println("*******************************");
                        break;
                        
                    case 2 : 
                         System.out.print("Enter ciphertext  : ");
                             Scanner S = new Scanner(System.in);
                             String C =  S.nextLine();
                             C = C.toLowerCase();
                             System.out.print("Enter key (int) : ");
                             int K =  in.nextInt();
                             System.out.print("Enter mod (int) : ");
                             int Mod =  in.nextInt();
                             String P =  decriptionCaesar(C,K,Mod);
                         System.out.println("\n************ Result ***********");
                             System.out.println("Plaintext => " + P.toUpperCase());    
                             System.out.println("*******************************");
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
          
          
            
      public static String encriptionCaesar(String  p, int k,int mod)
      {
           char [] plainTextCharArray = getAlphabeticToarray(p);
           String cipherText = "";
           
           for(int i = 0 ; i < plainTextCharArray.length ; i++ ){ // loop eche char in plain text
               int charNum = getNumCharFromAlphabetic(plainTextCharArray[i]); // get number of plain char from Alphabetic array
               
                   if( charNum != -1){
                      cipherText += String.valueOf(alphabeticArray[ (charNum + k) % mod]);
                   } // end if
                   
            } // end for loop
           
           return cipherText; 
      }
      
      
       public static String decriptionCaesar(String  c, int k,int mod)
      {
           char [] ciphreTextCharArray = getAlphabeticToarray(c);
           String plainText = "";
           
           for(int i = 0 ; i < ciphreTextCharArray.length ; i++ ){
                  int charNum = getNumCharFromAlphabetic(ciphreTextCharArray[i]); // get number of cipher char from Alphabetic array

                   if(charNum != -1 ){
                       int n = charNum - k;
                       while (n < 0){
                           n += mod;
                      }
                      plainText += String.valueOf(alphabeticArray[ n % mod]);
               }
           }
           
           return plainText;
      }  
}
