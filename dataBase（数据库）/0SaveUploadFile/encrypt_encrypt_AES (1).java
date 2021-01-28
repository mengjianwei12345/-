







public class AES {
        public static String initVector = "dec07f6e-3b47-42";
//        public static String key = "passwordpassword";
        public static String getKey(String sessionId){
            return sessionId.substring(0, 16);
        }

        public static String encrypt(String key, String initVector, String value) {
            try {
                IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
                SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

                byte[] encrypted = cipher.doFinal(value.getBytes());
                System.out.println("encrypted string: "+Base64.getEncoder().encodeToString(encrypted));

                return Base64.getEncoder().encodeToString(encrypted);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static byte[] encrypt(String key, String initVector, byte[] value) {
            try {
                IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
                SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

                byte[] encrypted = cipher.doFinal(value);

                return encrypted;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static String decrypt(String key, String initVector, String encrypted) {
            try {
                IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
                SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
                byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

                return new String(original);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static byte[] decrypt(String key, String initVector, byte[] encrypted) {
            try {
                IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
                SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
                byte[] original = cipher.doFinal(encrypted);

                return original;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static String realContent(String content){
            return content.substring(content.indexOf(" ") + 1, content.length());
        }

        public static String getRandomKey() {
            return getRandomStr(16);
        }

        public static String getRandomStr(int strLen){
            char[] chars = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxwz!@#$%^&*()=-".toCharArray();
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < strLen; i++) {
                char c = chars[random.nextInt(chars.length)];
                sb.append(c);
            }
            return  sb.toString();
        }
    }
