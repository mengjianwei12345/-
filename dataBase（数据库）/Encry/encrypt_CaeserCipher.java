




public class CaeserCipher {
    public static void main(String[] args) throws IOException {
        try {
            Scanner keyboard = new Scanner(System.in);
                 while (true){
                System.out.println("\n1.Encrypt\n2.Decrypt\n3.Exit");
                int choice =keyboard.nextInt();
                switch (choice) {
                    case 1:if(choice==1) {
                        System.out.println("Enter message:");
                        String message = keyboard.next().toUpperCase();
                        System.out.println("Enter encryption key:");
                        int key = keyboard.nextInt();
                        System.out.println("Encrypted message...");
                        System.out.println(encrypt(message, key));
                        break;
                    }
                    case 2:
                        System.out.println("Enter cipher text:");
                        String cipher = keyboard.next().toUpperCase();
                        System.out.println("Enter secrete key that you previously used for encryption:");
                        int key = keyboard.nextInt();
                        System.out.println("Decrypted message...");
                        System.out.println(decrypt(cipher, key));
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid option..");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String encrypt(String str, int keyLength) {
        String encrypted = "";
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            int c = (int) ch;
            c = c - 65;
            c = ((c + keyLength) % 26);
            c = c + 65;
            encrypted = encrypted + (char) c;
        }
        return encrypted;
    }

    public static String decrypt(String str, int keyLength) {
        String decrypted = "";
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            int c = (int) ch;
            c = c - 65;
            c = ((c - keyLength) % 26);
            c = c + 65;

            decrypted = decrypted + (char) c;
        }
        return decrypted;
    }
}





