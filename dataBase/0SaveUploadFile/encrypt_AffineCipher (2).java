


public class AffineCipher {
    public static void main(String[] args)
    {
        Scanner keyboard =new Scanner(System.in);
        try {
            while(true) {
                System.out.println("\n1.Encrypt\n2.Decrypt\n3.Exit");
                int choice = keyboard.nextInt();
                switch (choice)
                {
                    case 1:if(choice==1) {
                        System.out.println("Enter plain text");
                        String message = keyboard.next().toUpperCase();
                        System.out.print("Enter 'a' value:");
                        int a = keyboard.nextInt();
                        System.out.print("Enter 'b' value:");
                        int b = keyboard.nextInt();
                        char[] array_message = message.toCharArray();
                        int[] cipher = new int[array_message.length];

                        for (int i = 0; i < array_message.length; i++) {
                            cipher[i] = (a * ((int) array_message[i] - 65) + b) % 26;
                        }
                        for (int i = 0; i < array_message.length; i++) {
                            System.out.print((char) (cipher[i] + 65));
                        }
                        System.out.println();
                    }
                        break;
                    case 2:
                        System.out.println("Enter cipher text");
                        String cipher =keyboard.next().toUpperCase();
                        System.out.print("Enter 'a' value:");
                        int a =keyboard.nextInt();
                        System.out.print("Enter 'b' value:");
                        int b =keyboard.nextInt();
                        char[] array_cipher =cipher.toCharArray();
                        int[] message  =new int[array_cipher.length];

                        for(int i=0;i<array_cipher.length;i++)
                        {
                            int temp = (((int)array_cipher[i]-65) - b) % 26 ;
                            int value =Inverse_byEuclid.find_modulo_inverse(a,26);
                            message[i] =(temp * value) % 26;
                        }
                        for (int i = 0; i < array_cipher.length; i++) {
                            System.out.print((char) (message[i] + 65));
                        }
                    case 3:
                        System.exit(0);
                    default: System.out.println("Invalid choice");
                        break;

                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

}

