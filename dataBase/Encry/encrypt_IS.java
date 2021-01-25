




public class IS extends helper {

    public static void main(String[] args) {
        in =new Scanner(System.in);
        while(true){
            try{
              List();    
            }catch(Exception e){
                System.out.println("Error try again !!");
                System.exit(0);
            } 
        }
      
        
    }
    
    
        private static void List()
        {
            System.out.print("\n 1- GCD \n" 
                         + " 2- Inverse number over modulo \n"
                         + " 3- Caesar \n"
                         + " 4- Vigenere \n"
                         + " 5- One-Time-Pad (Vername) \n"
                         + " 6- Hill \n"
                         + " 7- Set alphabetic (String like => abcd...z/*-+ ) \n"
                         + " 8- Inverse matrix over modulo  \n"
                         + " 9- multi matrix \n"
                         + " 10-Determinant over modulo \n"
                         + " 11-Brute force \n"
                         + " 12- DES \n"
                         + " 13- RSA \n"
                         + " 14- Exit \n"
                         +"\n NOTE:  alphabetical set =>[ "+alphabetic+" ] (MOD "+alphabetic.length()+") If you want to modify it, click to Option No. 7 \n"
                     );
            
        System.out.print("\n Enter num option : ");
        int numOptionFromList =  in.nextInt();

        switch(numOptionFromList){
            case 1 :
                 GCDList();
                break;
            
            case 2 :
                 inverseList();
                break;
               
            case 3 : 
                 Caesar.main(null); 
                break;
               
            case 4 :
                Vigenere.main(null);
                break;
                
            case 5 :
                OneTimePad.main(null);
                break;
                
            case 6 : 
                  Hill.main(null);
                 break;     
            
            case 7 : 
                  setAlphabeticList();
                 break;
                
            case 8 : 
                  inverseMatrixList();
                  break;    
                 
            case 9 : 
                 multiList();
                break;      
                  
            case 10 : 
                 determinantMatrixModList();
                 break;
             
            case 11 :
                  burtForce();
                break;
            case 12 : 
                 Des.main(null);
                 break;  
            case 13 :
                RSA.main(null);
                break;
            case 14 : 
                  System.exit(0); 
                 break; 
            } //end switch
        
        } //end list function

        
                
        private static void setAlphabeticList()
        {
            System.out.print("Enter alphabetic (String like => abcd...z/*-+ ) : ");
            Scanner  alphaIn =new Scanner(System.in);
            alphabetic =  alphaIn.nextLine();
            alphabeticArray  =  getAlphabeticToarray(alphabetic);
            System.out.println("\n Operation accomplished successfully !! ");
        }
        
        
        
           private static void determinantMatrixModList()
         {
                System.out.print("Enter length of matrix (int) : ");
                int n =  in.nextInt();
                System.out.print("Enter mod (int) : ");
                 int MOD =  in.nextInt();
                  // set matrix in array  
                   int [][] matrix = new int[n][n];

                    for(int row = 0 ; row < n ; row++ ){
                        System.out.println("***************** Row "+ row+" *************************");
                        for(int col = 0 ; col < n ; col++){
                            System.out.print("Enter value (int) row "+row+ " col "+col+" : ");
                            Scanner sc = new Scanner(System.in);
                            matrix[row][col] =  sc.nextInt();
                        }
                    }
         
                    int result = determinantMatrixMod(matrix,n,MOD);
                  
                       
                  System.out.println("\n************ Result ***********");
                  System.out.println(" Determinant Matrix  => " + result );   
                  System.out.println("*******************************");
                  
         }
        
        
        
        
        private static void burtForce(){
              System.out.print("Enter plaintxt  : ");
               Scanner s = new Scanner(System.in);
               String p =  s.nextLine();
               p = p.toLowerCase();
            
            for(int i=0;i<26;i++){
                System.out.println("Key = " + i + " plaintext =  => " + Caesar.decriptionCaesar(p,i,26));
            }
        } 
        
        
        
        
        
        
        
         private static void inverseMatrixList()
         {
                System.out.print("Enter length of matrix (int) : ");
                int n =  in.nextInt();
                System.out.print("Enter mod (int) : ");
                 int MOD =  in.nextInt();
                  // set matrix in array  
                   int [][] matrix = new int[n][n];

                    for(int row = 0 ; row < n ; row++ ){
                        System.out.println("***************** Row "+ row+" *************************");
                        for(int col = 0 ; col < n ; col++){
                            System.out.print("Enter value (int) row "+row+ " col "+col+" : ");
                            Scanner sc = new Scanner(System.in);
                            matrix[row][col] =  sc.nextInt();
                        }
                    }
         
                    int arr[][] = inversesMatrixMod(matrix,n,MOD);
                  
                    for(int row = 0;row< arr.length;row++){
                       String str = "";
                       
                        for(int col = 0;col< arr.length;col++){
                            str = str + " "+ arr[row][col];
                        }
                     
                     if(row == 0){
                             System.out.println("|"+str+" |");
                    }else{
                             System.out.println("|"+str+" |");
                        }
                    }
         }
        
        
        
        private static void inverseList()
        {
                 System.out.print("Enter mod : ");
                 int mInverse =  in.nextInt();
                 System.out.print("Enter number : ");
                 int bInverse =  in.nextInt();
                 int rInverse =  inverses(mInverse,bInverse);
                 
                 if(rInverse == -1){
                      System.out.println("\n************ Result ***********");
                      System.out.println("It has no inverse !! ");   
                      System.out.println("*******************************");
                 }else{
                      System.out.println("\n************ Result ***********");
                      System.out.println("inverse  => ( " + bInverse +" ) => "+ rInverse);   
                      System.out.println("*******************************");
                 }
                 
                 
        }
        
        
         private static void GCDList()
         {
                 System.out.print("Enter a : ");
                 int aGCD =  in.nextInt();
                 System.out.print("Enter b : ");
                 int bGCD =  in.nextInt();
                 int rGCD =  GCD(aGCD,bGCD);
                 
                  System.out.println("\n************ Result ***********");
                  System.out.println("GCD => ( " + aGCD + " , " + bGCD +" ) = "+ rGCD);   
                  System.out.println("*******************************");
         }
         
        private static void multiList()
        {
             System.out.print("Enter rows num => matrix 1 : ");
              int rows1 = in.nextInt();
              System.out.print("Enter cols num => matrix 1 : ");
              int cols1 = in.nextInt();
              int [][] x = new int[rows1][cols1];
               
              System.out.print("Enter rows num => matrix 2 : ");
              int rows2 = in.nextInt();
              System.out.print("Enter cols num => matrix 2 : ");
              int cols2 = in.nextInt();
              int [][] y = new int[rows2][cols2];

               
              System.out.println("************************* Matrix 1 *************************");
                for(int row = 0 ; row < rows1 ; row++ ){
                    System.out.println("************************* Row "+ row+" *************************");
                    for(int col = 0 ; col < cols1 ; col++){
                        System.out.print("Enter value (int) row "+row+ " col "+col+" : ");
                        Scanner sc = new Scanner(System.in);
                        x[row][col] =  sc.nextInt();
                    }
                }
                
                
               System.out.println("************************* Matrix 2 *************************");
                for(int row = 0 ; row < rows2 ; row++ ){
                    System.out.println("************************* Row "+ row+" *************************");
                    for(int col = 0 ; col < cols2 ; col++){
                        System.out.print("Enter value (int) row "+row+ " col "+col+" : ");
                        Scanner sc = new Scanner(System.in);
                        y[row][col] =  sc.nextInt();
                    }
                }
              
              
                int resMultiMatrix[][] = multiMatrix(x,y);

                if(resMultiMatrix == null){
                    System.out.println("Error result multi matrix => null !!");
                }else{
                       for(int row = 0;row< resMultiMatrix.length;row++){
                       String str = "";
                       
                        for(int col = 0;col< resMultiMatrix[0].length;col++){
                            str = str + " "+ resMultiMatrix[row][col];
                        }
                     
                             System.out.println(str);
                    }
                }
                  
        }
}




