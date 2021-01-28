




public class ChinesThm {
    public static void main(String[] args) throws IOException
    {

        try
        {
            //scanner class to read input
            Scanner S =new Scanner(System.in);
            System.out.println("Enter the number of congruence equation");
            int no_eqn = S.nextInt();
            int[] x=new int[no_eqn];
            int[] p=new int[no_eqn];
            System.out.println("please enter valid input(modulo must be co-prime each other)");
            //reading congruence equation
            for(int i=0;i<no_eqn;i++) {
                System.out.println("enter the " + (i+1) + " values of congruence equation(x,p)");
                //reading ith congruence equation values(X and P)
                x[i]=S.nextInt();
                p[i]=S.nextInt();
            }
            for(int i=0;(i+1)<no_eqn;i++)
            {
                    if((EuclidGCD.find_gcd(p[i],p[i+1]))!=1)
                    {
                        System.out.println("Modulo value should be co-prime number");
                        System.exit(0);
                    }
            }
            int N=1;
            for(int i=0;i<no_eqn;i++)
            {
                //calculating N i.e N=p1*p2*p3*....
                N=N*p[i];
            }
            int[] y=new int[no_eqn];
           for(int i=0;i<no_eqn;i++)
            {
                //Calculating y for each congruence equation
                y[i] = Inverse_byEuclid.find_modulo_inverse((N/p[i]),p[i]);
            }
            int common_X=0;
            for(int i=0;i<no_eqn;i++) {
                //calculating reminder without mod
                common_X += ((N / p[i]) * x[i] * y[i]);
            }
            //taking modulus of reminder with value N
            common_X=Find_modulo.find_modulo(common_X,N);
            //this is output line
            System.out.println("So common reminder for given "+no_eqn+" congruence equation is:"+common_X);
        }
        catch(Exception e)
        {
            //println("error:"+e);
            e.printStackTrace();
        }

    }
}
