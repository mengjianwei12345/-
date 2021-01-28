
public class JavaApplication8 {
     
      
        static String string,sifre="",sifre1;
      static int[] tablo=new int[100];
       static int[] tabloters=new int[100];
      static int m;
    public static void main(String[] args) {
    
    Scanner scan = new Scanner(System.in);
      System.out.println("m giriniz (plain texti bölme miktarý):");
         m= scan.nextInt();
          System.out.println("Sifrelenecek Texti girin (NOT:sifrelenecek text m'in katý olmalýdýr..):");
          string=scan.next();
         for(int i=0;i<m;i++){
            System.out.println("" +(i+1)+ ". harfe karsilik kacinci harf gelsin:");
             tablo[i]= scan.nextInt();
         }
         int parcasayisi = string.length()/m;
         String [] parca =new String[parcasayisi];
   
       
        for(int j=0;j<parcasayisi;j++) {
       parca[j]=string.substring(j*m,(j*m)+m);
            
        }
        
       for(int j=0;j<parcasayisi;j++) {
           
           for(int i=0;i<m;i++){   
            sifre+=(char)parca[j].charAt(tablo[i]-1);
         }      
           
       }
       System.out.println("Textin Permutation Cipher ile Sifrelenmis Hali:"+sifre);
       
       for(int i=0;i<m;i++){
             for(int j=0;j<m;j++){
       if(tablo[j]==i+1)
       {
       tabloters[i]=j+1;
       break;
       }
    }
}
           for(int j=0;j<parcasayisi;j++) {
       parca[j]=sifre.substring(j*m,(j*m)+m);
            
        }
       
    String sifre2="";
          for(int j=0;j<parcasayisi;j++) {
           
           for(int i=0;i<m;i++){   
            sifre2+=(char)parca[j].charAt(tabloters[i]-1);
         }    
          }
               System.out.println("Girilen Textin Decrypt Hali:"+sifre2);
    }
}