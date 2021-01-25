





public class RSA extends helper {
   
    public static void main(String[] args) { 
              Rsa();
    }
    
    public static void Rsa(){
        boolean bo = true;
        
        while(bo){

                  System.out.print("\n 1- RSA encription (Confidentiality) \n" 
                         + " 2- RSA decription (Confidentiality) \n"
                          +" 3- RSA encription (Authenticity) \n"
                         + " 4- RSA decription (Authenticity) \n"
                         + " 5- Back \n"
                         + " 6- Exit \n"
                     );
                    
                System.out.print("Enter num option : ");
                int numOptionFromList =  in.nextInt();

                switch(numOptionFromList){
                    case 1 : 
                         System.out.print("Enter plaintxt  : ");
                         Scanner s = new Scanner(System.in);
                         String plain =  s.nextLine();
                         plain = plain.toLowerCase();
                         
                         boolean b = true;
                         int p = -1;
                         while(b){
                            System.out.print("Enter p (int) (prim) : ");
                              p =  in.nextInt(); 
                             if(isPrime(p)){
                                 b =false;
                             }else{
                                 System.out.println("p must be prime number try again !!");
                             }
                         }
                        
                      
                         
                         b = true;
                         int q =-1;
                         while(b){
                            System.out.print("Enter q (int) (prim) : ");
                              q =  in.nextInt(); 
                             if(isPrime(q)){
                                 b =false;
                             }else{
                                 System.out.println("q must be prime number try again !!");
                             }
                         }
                         
                         
                       
                        
                         System.out.print("Enter e (int) : ");
                         int e =  in.nextInt(); 
                         
                         String ciphertext = encriptionRSA(plain,p,q,e,true);
                         if(ciphertext != null){
                              System.out.println("\n************ Result ***********");
                              System.out.println("Ciphertext => " + ciphertext.toUpperCase());    
                              System.out.println("*******************************");
                         }
                        
                         
                        break;
                    case 2:
                        System.out.print("Enter number of ciphertext chars (int)  : ");
                          s = new Scanner(System.in);
                         int cipherNum =  s.nextInt();
                         
                         int cipher [] = new int[cipherNum];
                         for(int i = 0 ; i < cipherNum ; i++ ){
                             System.out.print("Enter ciphertext number " + (i+1) +" : ");
                             s = new Scanner(System.in);
                             cipher[i] =  s.nextInt();
                         }
                         
                          b = true;
                          p = -1;
                         while(b){
                            System.out.print("Enter p (int) (prim) : ");
                              p =  in.nextInt(); 
                             if(isPrime(p)){
                                 b =false;
                             }else{
                                 System.out.println("p must be prime number try again !!");
                             }
                         }
                        
                      
                         
                         b = true;
                          q =-1;
                         while(b){
                            System.out.print("Enter q (int) (prim) : ");
                              q =  in.nextInt(); 
                             if(isPrime(q)){
                                 b =false;
                             }else{
                                 System.out.println("q must be prime number try again !!");
                             }
                         }
                         
                         
                       
                        
                            System.out.print("Enter e (int) : ");
                              e =  in.nextInt(); 
                             
                         
                        
                         String plaintext = decriptionRSA(cipher,p,q,e,true);
                         System.out.println("\n************ Result ***********");
                         System.out.println("Ciphertext => " + plaintext.toUpperCase());    
                         System.out.println("*******************************");
                        break;
                        
                   case 3 : 
                         System.out.print("Enter plaintxt  : ");
                          s = new Scanner(System.in);
                          plain =  s.nextLine();
                         plain = plain.toLowerCase();
                         
                          b = true;
                          p = -1;
                         while(b){
                            System.out.print("Enter p (int) (prim) : ");
                              p =  in.nextInt(); 
                             if(isPrime(p)){
                                 b =false;
                             }else{
                                 System.out.println("p must be prime number try again !!");
                             }
                         }
                        
                      
                         
                         b = true;
                         q =-1;
                         while(b){
                            System.out.print("Enter q (int) (prim) : ");
                              q =  in.nextInt(); 
                             if(isPrime(q)){
                                 b =false;
                             }else{
                                 System.out.println("q must be prime number try again !!");
                             }
                         }
                         
                         
                       
                        
                         System.out.print("Enter e (int) : ");
                          e =  in.nextInt(); 
                         
                          ciphertext = encriptionRSA(plain,p,q,e,false);
                         if(ciphertext != null){
                              System.out.println("\n************ Result ***********");
                              System.out.println("Ciphertext => " + ciphertext.toUpperCase());    
                              System.out.println("*******************************");
                         }
                        
                         
                        break;
                    case 4:
                        System.out.print("Enter number of ciphertext chars (int)  : ");
                          s = new Scanner(System.in);
                          cipherNum =  s.nextInt();
                         
                         int cipher2 [] = new int[cipherNum];
                         for(int i = 0 ; i < cipherNum ; i++ ){
                             System.out.print("Enter ciphertext number " + (i+1) +" : ");
                             s = new Scanner(System.in);
                             cipher2[i] =  s.nextInt();
                         }
                         
                          b = true;
                          p = -1;
                         while(b){
                            System.out.print("Enter p (int) (prim) : ");
                              p =  in.nextInt(); 
                             if(isPrime(p)){
                                 b =false;
                             }else{
                                 System.out.println("p must be prime number try again !!");
                             }
                         }
                        
                      
                         
                         b = true;
                          q =-1;
                         while(b){
                            System.out.print("Enter q (int) (prim) : ");
                              q =  in.nextInt(); 
                             if(isPrime(q)){
                                 b =false;
                             }else{
                                 System.out.println("q must be prime number try again !!");
                             }
                         }
                         
                         
                       
                        
                            System.out.print("Enter e (int) : ");
                              e =  in.nextInt(); 
                             
                         
                        
                          plaintext = decriptionRSA(cipher2,p,q,e,false);
                         System.out.println("\n************ Result ***********");
                         System.out.println("Ciphertext => " + plaintext.toUpperCase());    
                         System.out.println("*******************************");
                        break;
                   case 5 : 
                      bo = false; 
                    
                      break;

                   case 6 : 
                      System.exit(0); 
                    
                      break;    
                      
                      
                }
        }
        
    }
    
    public static String encriptionRSA(String plaintext,int p,int q,int e,boolean state)
    {
        
       char [] plainTextCharArray = getAlphabeticToarray(plaintext);
       String cipherText = "";
       int n = -1 , euler = -1 , publicKey = -1 , privateKey = -1;
       int plainTextIntArray[] = new int [plainTextCharArray.length];
       
       
         for(int i = 0 ; i < plainTextCharArray.length ; i++ ){ // loop eche char in plain text
               int charNum = getNumCharFromAlphabetic(plainTextCharArray[i]); // get number of plain char from Alphabetic array
               if(charNum != -1){
                    plainTextIntArray[i] = charNum;
               }
         }
         
     
         n = q * p; 
         euler = euler(q,p);

         if(! checkE(e,euler)){
              System.out.println("\n************ Result ***********");
              System.out.println("Error ( e , euler ) must be coprime !! ");
              System.out.println("*******************************");
             return null;
         }
         
         if(state){
           for(int i = 0 ; i < plainTextIntArray.length ; i++ ){ // loop eche char in plain text              
               int c = power(plainTextIntArray[i] , e, n);
               cipherText += c+" , ";
          }   
         }else{
             int d  = inverses(euler,e); 
             for(int i = 0 ; i < plainTextIntArray.length ; i++ ){ // loop eche char in plain text              
               int c = power(plainTextIntArray[i] , d, n);
               cipherText += c+" , ";
            }
         }
         
         

         
         return cipherText;
    }    
    
    public static int checkIndex(int n){
            while(n > alphabeticArray.length){
               n = n % alphabeticArray.length;
            }
            
            while(n < 0){
               n = n + alphabeticArray.length;
            }
            return n;
   }
    
    public static String decriptionRSA(int ciphertext[],int p,int q,int e,boolean state)
    {
        
       String plainText = "";
       int n = -1 , euler = -1,d = -1;
       int cipherTextIntArray[] = ciphertext;
       
         n = q * p;
         
         euler = euler(q,p);

         d  = inverses(euler,e); 
        if(d == -1){
             if(! checkE(e,euler)){
               
                    System.out.println("\n************ Result ***********");
                    System.out.println("Error ( e , euler ) must be coprime !! ");
                    System.out.println("*******************************");
               }
             
             return null;
         }

          if(state){
              for(int i = 0 ; i < cipherTextIntArray.length ; i++ ){ // loop eche char in plain text              
               int c = power(cipherTextIntArray[i] , d , n);
               plainText += c+" , ";
         }
          }else{
              for(int i = 0 ; i < cipherTextIntArray.length ; i++ ){ // loop eche char in plain text              
               int c = power(cipherTextIntArray[i] , e , n);
               plainText += c+" , ";
            }
         }
         
         
         
         return plainText;
    }    
    
      public static boolean checkE(int e,int euler){
          if(e > 1 && e < euler){
              if ( GCD(e,euler) == 1 ){
                  return true;
              }
          }
          return false;
      }
            
         public static int  euler(int q,int p){  
             return ((p-1) * (q-1));
         }
            
            
      public static boolean isPrime(int n){  
              int i,m=0;      
              m=n/2;      
              if(n==0||n==1){  
               return false;      
              }else{  
               for(i=2;i<=m;i++){      
                if(n%i==0){      
                      return true;
                 }      
               }      
                 return true;
              }//end of else  
            }
    
      
 

	
	/* Iterative Function to calculate 
	(x^y)%p in O(log y) */
	static int power(int x, int y, int p) 
	{ 
		// Initialize result 
		int res = 1;	 
		
		// Update x if it is more 
		// than or equal to p 
		x = x % p; 

	if (x == 0) return 0; // In case x is divisible by p; 

		while (y > 0) 
		{ 
			// If y is odd, multiply x 
			// with result 
			if((y & 1)==1) 
				res = (res * x) % p; 
	
			// y must be even now 
			// y = y / 2 
			y = y >> 1; 
			x = (x * x) % p; 
		} 
		return res; 
	} 

	 


}
