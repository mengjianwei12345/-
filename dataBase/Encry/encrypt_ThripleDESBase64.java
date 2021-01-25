








public class ThripleDESBase64 {
//        private static String key;
        public static String encrypt(String base64Key, String plainText) throws Exception {
            final MessageDigest md = MessageDigest.getInstance("md5");
//            final byte[] digestOfPassword = md.digest("HG58YZ3CR9"
//                    .getBytes("utf-8"));

            final SecretKey key = new SecretKeySpec(
                    Base64.getDecoder().decode(base64Key), "DESede");
            final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            final byte[] plainTextBytes = plainText.getBytes("utf-8");
            final byte[] cipherText = cipher.doFinal(plainTextBytes);
            String base64EncodedCipherText = Base64.getEncoder().encodeToString(cipherText);
            return base64EncodedCipherText;
        }

        public static String decrypt(String base64Key, String base64CipherText) throws Exception {
            byte[] cipherbytes = Base64.getDecoder().decode(base64CipherText);
            final MessageDigest md = MessageDigest.getInstance("md5");
//            ede
            final SecretKey key = new SecretKeySpec(
                    Base64.getDecoder().decode(base64Key),
                    "DESede"
            );
//            final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            final Cipher decipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
//            decipher.init(Cipher.DECRYPT_MODE, key, iv);
            decipher.init(Cipher.DECRYPT_MODE, key);
            final byte[] plainText = decipher.doFinal(cipherbytes);

            return new String(plainText, "UTF-8");
        }
    }