



public class RepeatingSquare {
    public static void main(String[] args)
    {
        try {
            System.out.println("Enter credentials");
            Scanner keyboard =new Scanner(System.in);
            System.out.print("Enter a:");
            int a =keyboard.nextInt();
            System.out.print("Enter e:");
            int e =keyboard.nextInt();
            System.out.print("Enter p:");
            int p =keyboard.nextInt();
            String e_string = Integer.toString(e,2);
            char[] e_array =e_string.toCharArray();
            int x =1;
            int size = e_array.length;
            size--;
            int i =0;
            while(i<=size)
            {
                x = (int)Math.pow(x , 2) % p;
                if (e_array[i]=='1')
                {
                    x = (x*a) % p ;
                }
                i++;
            }
            System.out.println("reminder is:"+x);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
