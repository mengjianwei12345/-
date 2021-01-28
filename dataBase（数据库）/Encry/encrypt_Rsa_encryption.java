/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */








/**
 *
 * @author arnav
 */
public class Rsa_encryption {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args)throws IOException {
        // TODO code application logic here
        byte x;
        int a=0;
        rsa_keygen r = new rsa_keygen();
        BigInteger [][] key = r.generate();
        PrintWriter fw = new PrintWriter("Public_key.txt");
        fw.println(key[0][1].toString());
        fw.print(key[0][0].toString());
        fw.close();
        fw = new PrintWriter("Private_key.txt");
        fw.println(key[1][1].toString());
        fw.print(key[1][0].toString());
        fw.close();
        byte [] data = new byte [32];
        FileInputStream in = new FileInputStream("input.txt");
        x=(byte)in.read();
        while(x!=-1)
        {
            data[a++]=x;
            //System.out.println(data[a-1]);
            x=(byte)in.read();
            
        }
        in.close();
        for(int t=0;t<data.length;t++)
        {
            System.out.printf("%02x",data[t]);
        }
        System.out.println();
        
        BigInteger raw = new BigInteger(data);
        rsa_enc_dec e = new rsa_enc_dec();
        byte [] enc = e.encrypt_decrypt(raw,key[1][0],key[1][1]);
        FileOutputStream out = new FileOutputStream("Output.txt");
        out.write(enc);
        out.close();
        File file = new File("Output.txt");
        in = new FileInputStream(file);
        byte []u = new byte [(int)file.length()];
        in.read(u);
        BigInteger encr = new BigInteger(enc);
        byte [] dec = e.encrypt_decrypt(encr,key[0][0],key[0][1]);
        for(int t=0;t<dec.length;t++)
        {
            System.out.printf("%02x",dec[t]);
        }
        in.close();
    }
}

class keys
{
    public int[][] keygen()throws IOException
    {
        Random r = new Random();
        int p=make_prime(r.nextInt(100));
        int q=make_prime(r.nextInt(100));
        while(p==q)
            q=make_prime(r.nextInt(100));
        int n=p*q;
        int pn=n-(p+q-1);
        int e=make_prime(r.nextInt(pn));
        while(pn%e==0)
            e=make_prime(r.nextInt(pn));
        int d=inverse(e,pn);
        int [][] key = {{e,n},{d,n}};
        return key;
        /*System.out.println(p+"||"+q+"||"+e);
        //BufferedReader  br = new BufferedReader (new InputStreamReader(System.in));
        //System.out.print("Enter a character : ");
        char c = 'A';
        //c = (char) br.read();
        int enc;
        enc = exp((int)c,public_key[0],public_key[1]);
        System.out.println(enc+"--"+public_key[0]+"--"+public_key[1]);
        System.out.println(private_key[0]+"-ewlaw-"+private_key[1]);
        int dec = exp(enc,private_key[0],private_key[1]);
        System.out.println(dec);
        */
    }
    
    private int inverse(int a, int b)
    {
        int y=1;
        for(int x=1;x<=b;x++)
        {
            y=b*x;
            if(y%a==(a-1))
                break;
        }
        return (y+1)/a;
    }
    
    private int make_prime(int p)
    {
        if(p<0)
            p*=-1;
        else if(p<=6)
            p=7;
        int k=0;
        p=p-(p%6)+1;
        //System.out.print(".");
        while(!is_prime(p,561))
        {
            //System.out.print("|");
            if((++k%2)==1)
                p=p-2;
            else
                p+=8;
        }
        //if(is_prime(19,1))
        //is_prime(19,1);
        return p;
    }
    
    private boolean is_prime(int n, int s)
    {
        Random r = new Random();
        //System.out.print(witness(2,19));
        for(int x=0;x<s;x++)
        {
            int y=r.nextInt(n-1);
            if(y<0)
                y*=-1;
            int a=y+1;
            if(witness(a,n))
                return false;
        }
        //System.out.print(witness(2,19));
        return true;
    }
    
    @SuppressWarnings("empty-statement")
    private boolean witness(int a, int n)
    {
        int t=0;
        while(((n-1)&(1<<(t++)))==0);
        t--;
        //System.out.print(t);
        int u=(n-1)>>t;
        int x=exp(a,u,n);
        //System.out.print("{"+x+"}");
        for(int z=1;z<=t;z++)
        {
            int y=(x*x)%n;
            if(y==1 && x!=1 && x!=n-1)
                return true;
            x=y;
        }
        //System.out.print("-");
        return x!=1;
    }
    
    private int exp(int a, int b, int n)
    {
         int d=1;
         int c=b;
         int count=0;
         while(c!=0)
         {
             c>>=1;
             count++;
         }
         //System.out.print("["+count+"]");
         for(int x=count-1; x>=0; x--)
         {
             d=(d*d)%n;
             //System.out.print("\""+(1<<x)+"\"");
             if((b&(1<<x)) != 0)
                 d=(d*a)%n;
         }
         return d;
    }
    
}
