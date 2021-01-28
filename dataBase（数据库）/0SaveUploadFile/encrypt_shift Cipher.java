



public class shiftCipher { 
 
    static String string,sifre="",sifre1;
    static int[] karakter=new int[100];
    //char[] dizi= new char[100];

    public static String sifrele(String string, int key){
        sifre="";
        for(int i=0;i<string.length();i++){
            
             karakter[i] = ((int)string.charAt(i)+key)%256; //size 256, ASCII
             sifre+=(char)karakter[i];
            
        }
        
        return sifre;
    
    }

    public static String sifrecoz(String string,int key){
            sifre="";
        for(int i=0;i<string.length();i++){
        
             karakter[i] = ((int)string.charAt(i)-key)%256; //size 256, ASCII
             sifre+=(char)karakter[i];
            
        }
        
        return sifre;      
    
    }


    public static void main(String[] args) {
        

        Scanner scan = new Scanner(System.in);
        System.out.println("Sifrelenecek Texti girin:");
        string=scan.nextLine();
         System.out.println("Key girin:");
         int key = scan.nextInt();
       sifre1=sifrele(string,key);
        System.out.println(sifre1);
    
          
            System.out.println(sifrecoz(sifre1,key));
        
}
}