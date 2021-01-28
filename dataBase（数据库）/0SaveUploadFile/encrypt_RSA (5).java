






public class RSA extends Algorithm {

    private static final int KEY_SIZE = 2048;

    /**
     * Constructor that initialises an algorithm depending on given name
     *
     */
    public RSA() {
        super("RSA/ECB/PKCS1Padding", false);
    }

    @Override
    public void init() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(KEY_SIZE);
        KeyPair kp = kpg.generateKeyPair();

        encryptor = (Cipher.getInstance(name));
        decryptor = (Cipher.getInstance(name));
        encryptor.init(Cipher.ENCRYPT_MODE, kp.getPublic());
        decryptor.init(Cipher.DECRYPT_MODE, kp.getPrivate());
    }

}
