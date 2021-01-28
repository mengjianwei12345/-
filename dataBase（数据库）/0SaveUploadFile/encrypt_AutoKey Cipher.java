public class JavaApplication8 {
     
      
        static String string,sifre="",sifre1;
      static int[] karakter=new int[100];
  
    public static void main(String[] args) {
    
    Scanner scan = new Scanner(System.in);
         System.out.println("Sifrelenecek Texti girin:");
         string=scan.nextLine();
         System.out.println("Key girin:");
         int key = scan.nextInt();
         int firstkey=key;
           for(int i=0;i<string.length();i++){    
                    karakter[i] = ((int)string.charAt(i)+key)%256;
                    sifre+=(char)karakter[i];
                    key=(int)string.charAt(i);                 
           }
           
       System.out.println("Textin ASCII'de encrypt hali:" +sifre);
       string=sifre;
       sifre="";
       key=firstkey;
           for(int i=0;i<string.length();i++){    
                    karakter[i] = ((int)string.charAt(i)-key)%256;
                    sifre+=(char)karakter[i];
                    key=karakter[i];                 
           }
             System.out.println("Textin decrypt hali:"+sifre);
    }
}