



public class Beauford {
    public static void main(String[] args)
    {
        try
        {
            Scanner S =new Scanner(System.in);
            boolean again = true;
            while(again)
            {
                System.out.print("\n1:Encryption\n2:Decryption\n3:Exit\nEnter your choice:");
                int choice = S.nextInt();
                switch (choice) {
                    case 1:
                        if (choice == 1) {
                            System.out.println("Enter plain text for Encryption");
                            String message = S.next().toUpperCase();
                            System.out.println("Enter secrete key");
                            String key = S.next().toUpperCase();
                            //converting string to character array
                            char[] array_message = message.toCharArray();
                            char[] array_key = key.toCharArray();
                            int[] cipher = new int[message.length()];
                            for (int i = 0, j = 0; i < array_message.length; i++, j++) {
                                j = j % array_key.length;
                                //encryption of each character
                                cipher[i] = ((((int) array_key[j] - 65) - ((int) array_message[i] - 65)) + 26) % 26;
                            }
                            System.out.print("Cipher text is:");
                            for (int i = 0; i < array_message.length; i++) {
                                System.out.print(Character.toString((char) (65 + cipher[i])));
                            }

                        }
                        break;

                    case 2:
                        if (choice == 2) {
                            System.out.println("\nEnter cipher text for Decryption");
                            String cipher = S.next().toUpperCase();
                            System.out.println("Enter secrete key");
                            String key = S.next().toUpperCase();
                            //converting string to character array
                            char[] array_cipher = cipher.toCharArray();
                            char[] array_key = key.toCharArray();
                            int[] message = new int[cipher.length()];
                            for (int i = 0, j = 0; i < array_cipher.length; i++, j++) {
                                j = j % array_key.length;
                                //decryption of each character
                                message[i] = ((((int) array_key[j] - 65) - ((int) array_cipher[i] - 65))+26) % 26;

                            }
                            System.out.print("plain text is:");
                            for (int i = 0; i < array_cipher.length; i++) {
                                System.out.print(Character.toString((char) (65 + message[i])));
                            }
                        }
                        break;
                    case 3:
                        System.exit(0);
                    default: System.out.println("Invalid choice");
                            break;
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

}
