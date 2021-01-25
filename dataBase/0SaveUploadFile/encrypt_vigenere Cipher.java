
public class VigenereCipher {

static String string,sifre="",sifre1;
    static String key="";
    static int[] karakter=new int[100];
  
     public static String sifrele(String string, String key){
        sifre="";
        int j=0;
        for(int i=0;i<string.length();i++){
            
             karakter[i] = ((int)string.charAt(i)+(int)key.charAt(j))%256;
             sifre+=(char)karakter[i];
             j++;                    
            
            if(j==key.length()){                
            j=0;
            }
        }
        
        return sifre;
    
    }
    public static String sifrecoz(String string,String key){
            sifre="";
            int j=0;
        for(int i=0;i<string.length();i++){
          
             karakter[i] = ((int)string.charAt(i)-(int)key.charAt(j))%256;
             sifre+=(char)karakter[i];
            j++;
           if(j==key.length()){                
            j=0;
            }
        }
        
        return sifre;      
    
    }
    public static void main(String[] args) {
       
          Scanner scan = new Scanner(System.in);
        System.out.println("Sifrelenecek Texti girin:");
        string=scan.next();
         System.out.println("Key girin:");
          key = scan.next();
       sifre1=sifrele(string,key);
        System.out.println(sifre1);
        System.out.println("sifrelenmis text");
    
          
            System.out.println(sifrecoz(sifre1,key));
            System.out.println("cozulmus text");
    }
}