







class Rsa extends Thread {
    int counter = 0;
    static long time_second = 0;

    public static void main(String[] args) throws Exception {
        /* 
        * Generating a key-pair out of email to visualize a scenario where email
        * is used as a authenticity mechanism to validate a request using RSA. 
        * E.g.: Yash sends a request to Dhananjay's Computer to update the git
        database hosted on his computer.
        */
        String user_input = "Yash_dave@gmail.com";

        // Conversion to byte array for encryption
        byte[] value = user_input.getBytes();
        System.out.println("Input Array :"+value);

        // Generating a Public - Private key pair
        KeyPair key = generateKeyPair();
        PrivateKey private_key = key.getPrivate();
        System.out.println("Private key :" + private_key);

        // Creating a Signature
        byte[] sign = generatePkcs1Signature(private_key, value);
        System.out.println("Signature :"+sign);

        PublicKey public_key = key.getPublic();
        // Verifying the signature
        Boolean check = verifyPkcs1Signature(public_key, value, sign);
        if(check==true){
            System.out.println("Verified");
        }
        else{
            System.out.println("Not verified");
        }
    }

    // Function for KeyPair Generation, Returns a KeyPair Object which contains
    // public and private keys 
    public static KeyPair generateKeyPair() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleFipsProvider());
        KeyPairGenerator keyPair = KeyPairGenerator.getInstance("RSA", "BCFIPS");
        keyPair.initialize(new RSAKeyGenParameterSpec(3072, RSAKeyGenParameterSpec.F4));
        return keyPair.generateKeyPair();
    }

    // Function for GeneratingSignature, Returns Encrypted Signature  
    // in Byte Array
    public static byte[] generatePkcs1Signature(PrivateKey rsaPrivate, byte[] input) throws GeneralSecurityException {
        Thread u = new Rsa();
        u.start();
        Signature signature = Signature.getInstance("SHA384withRSA", "BCFIPS");
        signature.initSign(rsaPrivate);
        signature.update(input);
        return signature.sign();
    }

    // Function to verifySignature, Returns a Boolean 
    // To check wheather it is authencticated or not 
    public static boolean verifyPkcs1Signature(PublicKey rsaPublic, byte[] input, byte[] encSignature)
            throws GeneralSecurityException {
        Signature signature = Signature.getInstance("SHA384withRSA", "BCFIPS");
        signature.initVerify(rsaPublic);
        signature.update(input);
        System.out.println("Execution time in seconds :"+TimeUnit.MILLISECONDS.toHours(time_second));
        return signature.verify(encSignature);
    }

    // Thread run method for calculating time taken for execution in milliseconds
    public void run() {
        while (true) {

            time_second = counter++;

        }
    }
}