
public class JavaApplication8 {
    
  static String sifre;
    /**
     * @param args the command line arguments
     */
    public static int TersBul(int d){
     int x1, x2, x3, y1, y2, y3,f=256;
            x1 = 1; x2 = 0; x3 = f; //p
            y1 = 0; y2 = 1; y3 = d; //d        

            int q = 0, i = 1;
            int t1 = 0, t2 = 0, t3 = 0;
            do
            {
                if (i == 1)
                {
                    q = x3 / y3;
                    t1 = x1 - (q * y1);
                    t2 = x2 - (q * y2);
                    t3 = x3 - (q * y3);
                }
                else
                {
                    x1 = y1; x2 = y2; x3 = y3;
                    y1 = t1; y2 = t2; y3 = t3;
                    q = x3 / y3;
                    t1 = x1 - (q * y1);
                    t2 = x2 - (q * y2);
                    t3 = x3 - (q * y3);
                }
                i++;
              

                if (y3 == 0)
                {
                    break;
                }

            } while (y3 != 1);
           if(y2<0){
               y2+=f;
           }
    return y2;
    }
    
    static int[] karakter=new int[100];
    public static String sifrele(String string, int key1,int key2){
        sifre="";
        for(int i=0;i<string.length();i++){
            
             karakter[i] = (((int)string.charAt(i)*key1)+key2)%256;
             sifre+=(char)karakter[i];
            
        }
        
        return sifre;
    
    }
    public static String sifrecoz(String string, int key1,int key2){
         sifre="";
         int k;
        for(int i=0;i<string.length();i++){
            k=(((int)string.charAt(i))*key1)-(key2*key1);
            
            while(k<0){k+=256;}
            
             karakter[i] = k%256;
             sifre+=(char)karakter[i];
            
        }
        
        return sifre;
    
    }
    public static void main(String[] args) {
    
        Scanner scan = new Scanner(System.in);
        System.out.println("Sifrelenecek Texti girin:");
        String string=scan.nextLine();
         System.out.println("Key girin:");
         int a =scan.nextInt();
         int b=scan.nextInt();
         String sifre1=sifrele(string,a,b);
         System.out.println("Þifrelenmiþ text : "+sifre1);
         System.out.println("Þifresi çözülmüþ text : "+sifrecoz(sifre1,TersBul(a),b));
         
         
    }

}
