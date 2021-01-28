













 

 

public class DES_EncryptionAlgorithm {
 
   static LinkedList<String> l1=new LinkedList();
public static String sbox(String plain)
{
    int table=0;
       
       String x="";
       String formatedinput="";
  int [][] s1 = { { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 }, { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 }, { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 }, { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } };
	int [][]s2 = { { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10, }, { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 }, { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 }, { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9, } };
	int [][]s3 = { { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 }, { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 }, { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 }, { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } };
	int [][]s4 = { { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 }, { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 }, { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 }, { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } };
	int[][] s5 = { { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 }, { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 }, { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 }, { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3, } };
	int[][] s6 = { { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 }, { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 }, { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 }, { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } };
	int [][]s7 = { { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 }, { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 }, { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 }, { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } };
	int [][]s8= { { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 }, { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 }, { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 }, { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } };

      formatedinput=hexaToBinary(plain);
        String output="";
        
        for (int i = 0; i < formatedinput.length(); i=i+6)
        {
            String c=formatedinput.substring(i,i+6);
            table++;
            if(table==1)
            {
               StringBuilder d=new StringBuilder();
               d.append(c.charAt(0));
               d.append(c.charAt(c.length()-1));
               String f=d.toString();
               int row = Integer.parseInt(f, 2);
                 int column=Integer.parseInt(c.substring(1,5),2);
                 int value=s1[row][column];
               
               output=output+Integer.toHexString(value);
                
            }
            else if(table==2)
            {
               StringBuilder d=new StringBuilder();
               d.append(c.charAt(0));
               d.append(c.charAt(c.length()-1));
               String f=d.toString();
               int row = Integer.parseInt(f, 2);
                 int column=Integer.parseInt(c.substring(1,5),2);
                 int value=s2[row][column];
               output=output+Integer.toHexString(value);
                
            }
            else if(table==3)
            {
               StringBuilder d=new StringBuilder();
               d.append(c.charAt(0));
               d.append(c.charAt(c.length()-1));
               String f=d.toString();
               int row = Integer.parseInt(f, 2);
                 int column=Integer.parseInt(c.substring(1,5),2);
                 int value=s3[row][column];
               output=output+Integer.toHexString(value);
                
            }
            else if(table==4)
            {
               StringBuilder d=new StringBuilder();
               d.append(c.charAt(0));
               d.append(c.charAt(c.length()-1));
               String f=d.toString();
               int row = Integer.parseInt(f, 2);
                 int column=Integer.parseInt(c.substring(1,5),2);
                 int value=s4[row][column];
                output=output+Integer.toHexString(value);
                
            }
            else if(table==5)
            {
               StringBuilder d=new StringBuilder();
               d.append(c.charAt(0));
               d.append(c.charAt(c.length()-1));
               String f=d.toString();
               int row = Integer.parseInt(f, 2);
                 int column=Integer.parseInt(c.substring(1,5),2);
                 int value=s5[row][column];
                output=output+Integer.toHexString(value);
                
            }
            else if(table==6)
            {
               StringBuilder d=new StringBuilder();
               d.append(c.charAt(0));
               d.append(c.charAt(c.length()-1));
               String f=d.toString();
               int row = Integer.parseInt(f, 2);
                 int column=Integer.parseInt(c.substring(1,5),2);
                 int value=s6[row][column];
               output=output+Integer.toHexString(value);
                
            }
            else if(table==7)
            {
               StringBuilder d=new StringBuilder();
               d.append(c.charAt(0));
               d.append(c.charAt(c.length()-1));
               String f=d.toString();
               int row = Integer.parseInt(f, 2);
                 int column=Integer.parseInt(c.substring(1,5),2);
                 int value=s7[row][column];
               output=output+Integer.toHexString(value);
                
            }
            else if(table==8)
            {
               StringBuilder d=new StringBuilder();
               d.append(c.charAt(0));
               d.append(c.charAt(c.length()-1));
               String f=d.toString();
               int row = Integer.parseInt(f, 2);
                 int column=Integer.parseInt(c.substring(1,5),2);
                 int value=s8[row][column];
                output=output+Integer.toHexString(value);
                
            }
            
            
        }
        return output.toUpperCase();
}
public static String hexaToBinary(String hexa) {
        String binary = "";
        String formatedInput = "";
        for (int i = 0; i < hexa.length(); i++) {
            switch (hexa.charAt(i)) {

                case '0':
                    binary = "0000";
                    break;

                case '1':
                    binary = "0001";
                    break;

                case '2':
                    binary = "0010";
                    break;

                case '3':
                    binary = "0011";
                    break;

                case '4':
                    binary = "0100";
                    break;

                case '5':
                    binary = "0101";
                    break;

                case '6':
                    binary = "0110";
                    break;

                case '7':
                    binary = "0111";
                    break;

                case '8':
                    binary = "1000";
                    break;

                case '9':
                    binary = "1001";
                    break;

                case 'A':
                    binary = "1010";
                    break;

                case 'B':
                    binary = "1011";
                    break;

                case 'C':
                    binary = "1100";
                    break;

                case 'D':
                    binary = "1101";
                    break;

                case 'E':
                    binary = "1110";
                    break;

                case 'F':
                    binary = "1111";
                    break;
            }
            formatedInput += binary;

        }
        return formatedInput;

    }
public static String expansion(String plain,int []permbox)
{
  
   String output=new String();
   String hexStr=new String();
   String formatedinput=new String();
   String x="";
   
  formatedinput=hexaToBinary(plain);
  // String formatedinput=new BigInteger(plain, 16).toString(2);
   for(int k=0;k<permbox.length;k++)
   {
      output=output+formatedinput.charAt((permbox[k]-1)); 
   }
  for(int i=0; i<output.length();i=i+4)
  {
      String temp=output.substring(i, i+4);
  
    int decimal = Integer.parseInt(temp, 2);
hexStr = hexStr+ Integer.toHexString(decimal);
        
  }
   return hexStr.toUpperCase();
    
    
    
}
public static String expansionbin(String formatedinput,int []permbox)
{
  
   String output=new String();
   String hexStr=new String();

  try{
   for(int k=0;k<permbox.length;k++)
   {
      output=output+formatedinput.charAt((permbox[k]-1)); 
   }
  }
  catch(Exception e)
  {
      
  }
  try{
  for(int i=0; i<output.length();i=i+4)
  {
      String temp=output.substring(i, i+4);
  
    int decimal = Integer.parseInt(temp,2);
hexStr = hexStr+ Integer.toHexString(decimal);
        
  }
  }
  catch(Exception e)
  {
      
  }
   return hexStr.toUpperCase();
    
    
    
}
public static void keygenerate(String key)
{
     int []pc1 ={57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4 };
      int[] rot= { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };
      int[]pc2= { 14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32 };
     String plain=expansion(key,pc1);
      String formatedinput=new String();
   String x="";
     for(int p=0;p<plain.length();p++)
   {
      switch (plain.charAt(p))
      {
          case '0':
              x="0000";
              break;
              case'1':
                  x="0001";
                  break;
              case '2':
                  x="0010";
                  break;
              case '3':
                  x="0011";
                  break;
              case '4':
                  x="0100";
                  break;
              case '5':
                  x="0101";
                  break;
              case '6':
                  x="0110";
                  break;
              case '7':
                  x="0111";
                  break;
              case '8':
                  x="1000";
                  break;
              case '9':
                  x="1001";
                  break;
              case 'A':
                  x="1010";
                  break;
              case 'B':
                  x="1011";
                  break;
              case 'C':
                  x="1100";
                  break;
              case 'D':
                  x="1101";
                  break;
              case 'E':
                  x="1110";
                  break;
              case 'F':
                  x="1111";
                  break;
                  
              
      }
      formatedinput=formatedinput+x;
   }
     String hexStr="";
     for(int i=0;i<16;i++)
     {
         String s=rotate(formatedinput,rot[i]);
         String out=expansionbin(s,pc2);
       
   l1.add(out);
   formatedinput=s;
         
         
     }
     
     
     
     
     
}
public static String rotate(String plain,int rot)
{
    String new1="";
    String new2="";
    String part1=plain.substring(0, 28);
    String part2=plain.substring(28, plain.length());
    new1=part1.substring(rot)+part1.substring(0,rot);
    new2=part2.substring(rot)+part2.substring(0, rot);
    return new1+new2;
    
}
public static String performXOR(String key, String permulationTableOutput) {
        int res;
        String output = "";

        for (int i = 0; i < key.length(); i++) {
            int keyChar = (int) key.charAt(i);
            int permulationTableOutputChar = (int) permulationTableOutput.charAt(i);
            res = (int) (keyChar ^ permulationTableOutputChar);

            output += res;
        }

        return output;  
    }
public static String binaryToHexa(String input) {

        String result = "";
        String hexaOutput = "";

        for (int i = 0; i < input.length(); i = i + 4) {

            String tmp = input.substring(i, i + 4);

            int decimal = Integer.parseInt(tmp, 2);
            hexaOutput = hexaOutput + Integer.toHexString(decimal);

        }
        result = hexaOutput.toUpperCase();

        return result;
    }

  public static String round(String l,String r)
  {
      int []pc1 ={ 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4 };
      int[] rot= { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };
      int[]pc2= { 14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32 };
       int[]E={32,1,2,3,4,5,4,5,6,7,8,9,8,9,10,11,12,13,12,13,14,15,16,17,16,17,18,19,20,21,20,21,22,23,24,25,24,25,26,27,28,29,28,29,30,31,32,1};
       int[]stbox={16,7,20,21,29,12,28,17,1,15,23,26,5,18,31,10,2,8,24,14,32,27,3,9,19,13,30,6,22,11,4,25}; 
      for (int i = 0; i < l1.size(); i++) 
          {
              String s=expansion(r,E);
             String u= hexaToBinary(s);
                       String key=hexaToBinary(l1.get(i));
                      String bin=performXOR(key,u);
                      String h=binaryToHexa(bin);
                      String hexa=sbox(h);
                     String d= expansion(hexa,stbox);
                     
        String r1=hexaToBinary(d);
        String l1=hexaToBinary(l);
        String in=performXOR(r1,l1);
        l=r;
        r=binaryToHexa(in);
          }
      return r+l;
  }

 

   
    public static void main(String[] args) {
        Scanner  sc1=new Scanner(System.in);
      int []pc1 ={ 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4 };
      int[] rot= { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };
      int[]pc2= { 14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32 };
       int[]E={32,1,2,3,4,5,4,5,6,7,8,9,8,9,10,11,12,13,12,13,14,15,16,17,16,17,18,19,20,21,20,21,22,23,24,25,24,25,26,27,28,29,28,29,30,31,32,1};
       int[]stbox={16,7,20,21,29,12,28,17,1,15,23,26,5,18,31,10,2,8,24,14,32,27,3,9,19,13,30,6,22,11,4,25}; 
       String key=sc1.next();
        int[]IP={58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9, 1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7};

      int []INP={40,8,48,16,56,24,64,32,
                                      39,7,47,15,55,23,63,31,
                                      38,6,46,14,54,22,62,30,
                                      37,5,45,13,53,21,61,29,
                                      36,4,44,12,52,20,60,28,
                                      35,3,43,11,51,19,59,27,
                                      34,2,42,10,50,18,58,26,
                                      33, 1,41, 9,49,17,57,25};

        
        
        
        String input=sc1.next();
        int n=sc1.nextInt();
         keygenerate(key);
            for (int i = 0; i < n; i++) {
            
      
    
       
       input= expansion(input,IP);
        String L=input.substring(0, 8);
        String R=input.substring(8, input.length());
        
        
        String j=round(L,R);
        String cipher=expansion(j,INP);
        
        input=cipher;
            }
            System.out.println(input);
    }  
}