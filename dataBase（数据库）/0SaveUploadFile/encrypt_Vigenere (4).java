




public class Vigenere extends helper {
    
     public static void main(String[] args) { 
              Vigenere();
     }
    
    
    protected static void Vigenere()
    {
           boolean bo = true;
                  
           while(bo){
                System.out.print("\n 1- Vigenere encription \n" 
                       + " 2- Vigenere decription \n"
                       + " 3- Back \n"
                       + " 4- Exit \n"
                   );
                      System.out.print("Enter num option : ");
                int numOptionFromList =  in.nextInt();

                switch(numOptionFromList){
                    case 1 :
                         System.out.print("Enter plaintxt  : ");
                         Scanner s = new Scanner(System.in);
                         String p =  s.nextLine().toLowerCase();
                         System.out.print("Enter key : ");
                         String k =  s.nextLine().toLowerCase();
                         System.out.print("Enter mod (int) : ");
                         int mod =  in.nextInt();
                         String c =  encriptionVigenere(p,k,mod);                         
                         System.out.println("\n************ Result ***********");
                         System.out.println("Ciphertext => " + c.toUpperCase());    
                         System.out.println("*******************************");
                        break;
                        
                case 2 : 
                     System.out.print("Enter ciphertext  : ");
                         Scanner S = new Scanner(System.in);
                         String C =  S.nextLine().toLowerCase();
                         System.out.print("Enter key (int) : ");
                         String K =  S.nextLine().toLowerCase();
                         System.out.print("Enter mod (int) : ");
                         int Mod =  in.nextInt();
                         String P =  decriptionVigenere(C,K,Mod);
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
             
     
        public static String encriptionVigenere(String  p, String k,int mod)
        {
              char keyArray [] = new char[p.length()]; // length key  = length p  
              char [] key =  getAlphabeticToarray(k);
              char [] plain =  getAlphabeticToarray(p);
              String cipherText = "";
              int countKey = 0 ;
              
              for(int i = 0 ; i < p.length() ; i++){
                   if(countKey == k.length()){
                       countKey = 0;
                   }
                   keyArray[i] = key[countKey];
                   countKey++;
              }
              
                 
              for(int i = 0 ; i < plain.length ; i++){
                   for(int j = 0 ; j < alphabeticArray.length ; j++ ){
                        if(alphabeticArray[j] == keyArray[i] ){ 
                          cipherText += Caesar.encriptionCaesar(String.valueOf(plain[i]),j,mod);
                          break;
                        }
                  }
              }
              return cipherText;
        }
             
             
             
       public static String decriptionVigenere(String  c, String k,int mod)
             {
              char keyArray [] = new char[c.length()]; // length key  = length p  
              char [] key =  getAlphabeticToarray(k);
              char [] cipher =  getAlphabeticToarray(c);

              String plainText = "";

              int countKey = 0 ;
              for(int i = 0 ; i < c.length() ; i++){
                   if(countKey == k.length()){
                       countKey = 0;
                   }
                   keyArray[i] = key[countKey];
                  
                  countKey++;
              }
              
            
              for(int i = 0 ; i < cipher.length ; i++){
                   for(int j = 0 ; j < alphabeticArray.length ; j++ ){
                        if(alphabeticArray[j] == keyArray[i] ){ 
                          plainText += Caesar.decriptionCaesar(String.valueOf(cipher[i]),j,mod);
                          break;
                        }
                  }
              }
              
              
              return plainText;
        }
      
             
}
