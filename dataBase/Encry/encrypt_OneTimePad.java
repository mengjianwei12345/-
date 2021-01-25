




public class OneTimePad extends helper {
    
       public static void main(String[] args) { 
              oneTimePad();
       }
             
             
              
       private static void oneTimePad()
       {
                  boolean bo = true;
                  
                  while(bo){
                    System.out.print("\n 1- one Time Pad encription \n" 
                         + " 2- one Time Pad decription \n"
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

                        System.out.print("Enter key : ");
                        Scanner  keyIn = new Scanner(System.in);
                        String key =  keyIn.nextLine().toLowerCase();

                        System.out.print("Enter mod : ");
                        int mod =  in.nextInt();
                       
                          System.out.println("\n************ Result ***********");
                          System.out.println("Ciphertext => " + encriptionOneTimePad(plaintext,key, mod).toUpperCase());    
                          System.out.println("*******************************"); 
                        
                        break;
                        
                case 2 : 
                        System.out.print("Enter ciphertext : ");
                        Scanner  cipherIn = new Scanner(System.in);
                        String ciphertext =  cipherIn.nextLine().toLowerCase();

                        System.out.print("Enter key : ");
                        Scanner  KEYIn = new Scanner(System.in);
                        String KEY =  KEYIn.nextLine().toLowerCase();

                        System.out.print("Enter mod : ");
                        int MOD =  in.nextInt();
                        
                          System.out.println("\n************ Result ***********");
                          System.out.println("Plaintext => " + decriptionOneTimePad(ciphertext,KEY, MOD).toUpperCase());    
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
              
              
       
        public static String encriptionOneTimePad(String  p, String k,int mod)
        {
                       char plain[] = getAlphabeticToarray(p);
                       char  key[]  = getAlphabeticToarray(k);
                       char  [] alphabeticArray  =  getAlphabeticToarray(alphabetic);
                       String cipherText = "";

                       if(plain.length != key.length)
                       {
                           return "Error => length of plaintext must = length of key !!";
                       }
                       
                       for(int i = 0 ; i < plain.length ; i++){
                           int plainChar = -1;
                           for(int j = 0 ; j < alphabeticArray.length ; j++ ){
                               if(alphabeticArray[j] == plain[i]){
                                   plainChar = j; 
                               }
                           }
                             
                           int keyChar = -1;
                             for(int j = 0 ; j < alphabeticArray.length ; j++ ){
                               if(alphabeticArray[j] == key[i]){
                                   keyChar = j; 
                               }
                           }
                             if(plainChar != -1 && keyChar != -1){
                                 int sum = plainChar + keyChar;
                                while(sum < 0){
                                     sum += mod;
                                 }
                                 
                                 while(sum > mod){
                                     sum = sum % mod;
                                 }
                                 
                                 cipherText += alphabeticArray[sum];
                             }else{
                                 return "Enter plaintext or key correctly !! ";
                             }
                             
                       }
              
                   return cipherText;
        }
      
                   
        public static String decriptionOneTimePad(String  c, String k,int mod)
        {
                       char cipher[] = getAlphabeticToarray(c);
                       char  key[]  = getAlphabeticToarray(k);
                       char  [] alphabeticArray  =  getAlphabeticToarray(alphabetic);
                       String plainText = "";

                       if(cipher.length != key.length)
                       {
                           return "Error => length of plaintext must = length of key !!";
                       }
                       
                       for(int i = 0 ; i < cipher.length ; i++){
                           int cipherChar = -1;
                           for(int j = 0 ; j < alphabeticArray.length ; j++ ){
                               if(alphabeticArray[j] == cipher[i]){
                                   cipherChar = j; 
                               }
                           }
                             
                           int keyChar = -1;
                             for(int j = 0 ; j < alphabeticArray.length ; j++ ){
                               if(alphabeticArray[j] == key[i]){
                                   keyChar = j; 
                               }
                           }
                             if(cipherChar != -1 && keyChar != -1){
                                 int sum = cipherChar - keyChar ;
                                 while(sum < 0){
                                     sum += mod;
                                 }
                                 
                                 while(sum > mod){
                                     sum = sum % mod;
                                 }
                                 
                                 plainText += alphabeticArray[sum]; 
                             }else{
                                 return "Enter plaintext or key correctly !! ";
                             }
                             
                       }
              
                   return plainText;
            }
      
     

       
       
       
       
       
        
      
          
        
}
