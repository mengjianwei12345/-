







/**
 * @author Chotek
 * @author JGorny
 *
 * Triple DES encryption process works by taking three 56-bit keys (K1, K2 and K3),
 * and encrypting first with K1, decrypting next with K2 and encrypting a last time with K3.
 * 3DES has two-key and three-key versions. In the two-key version, the same algorithm runs three times,
 * but uses K1 for the first and last steps.
 *
 */


public class DESDESDES extends Algorithm {

    public DESDESDES(){
        super("DESede/ECB/PKCS5Padding", false);
    }

    @Override
    public void init() throws Exception {
        final byte[] keyBytes = Arrays.copyOf(MessageDigest.getInstance("md5").digest("HG58YZ3CR9".getBytes(StandardCharsets.UTF_8)), 24);
        for (int j = 0, k = 16; j < 8;) {
            keyBytes[k++] = keyBytes[j++];
        }

        key = (new SecretKeySpec(keyBytes, "DESede"));
        encryptor = (Cipher.getInstance(getName()));
        decryptor = (Cipher.getInstance(getName()));
        encryptor.init(Cipher.ENCRYPT_MODE, key);
        decryptor.init(Cipher.DECRYPT_MODE, key);
    }

    @Override
    public String getName() {
        return "DESede";
    }
}
