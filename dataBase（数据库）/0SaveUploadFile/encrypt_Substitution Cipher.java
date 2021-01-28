

public class substitution {

    //You can change these arrays..
public static char plainText[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w','x', 'y', 'z'};
   public static char cipherText[] = {'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X','Y', 'Z', 'A'};

    

 public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
         System.out.println("Sifrelenmesini istediginiz Texti girin:");
     
        String pt =sc.nextLine();
	String sifre= new String();

        for (int a = 0; a < pt.length(); a++) {
            for (int i = 0; i < plainText.length; i++) {
               if(plainText[i] == (pt.charAt(a))){
                   System.out.println(cipherText[i]);
                   sifre += String.valueOf(cipherText[i]);
               }
            }
        }
       System.out.println();
        System.out.println();
        Decrypt(sifre);
    }
  
  private static void Decrypt(String text)
 {
      for (int a = 0; a < text.length(); a++) {
            for (int i = 0; i < cipherText.length; i++) {
               if(cipherText[i] == (text.charAt(a))){
                   System.out.println(plainText[i]);
               }
                
            }
      }

 }
}
