
















public class AESUtil {
	private static int length=128;  
    /** 
     * 加密 
     *  
     * @param content 
     *            需要加密的内容 
     * @param password 
     *            加密密码 
     * @return 
     * @throws NoSuchAlgorithmException 
     * @throws NoSuchPaddingException 
     * @throws UnsupportedEncodingException 
     * @throws InvalidKeyException 
     * @throws BadPaddingException 
     * @throws IllegalBlockSizeException 
     */  
    private static byte[] encrypt(String content, String password)  
            throws Exception {  
  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
                SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );   
                secureRandom.setSeed(password.getBytes());   
        kgen.init(length, secureRandom);  
        SecretKey secretKey = kgen.generateKey();  
        byte[] enCodeFormat = secretKey.getEncoded();  
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
        byte[] byteContent = content.getBytes("utf-8");  
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化  
        byte[] result = cipher.doFinal(byteContent);  
        return result; // 加密  
  
    }  
  
    /** 
     * 解密 
     *  
     * @param content 
     *            待解密内容 
     * @param password 
     *            解密密钥 
     * @return 
     */  
    private static byte[] decrypt(byte[] content, String password)  
            throws Exception {  
  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
                 SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );   
                  secureRandom.setSeed(password.getBytes());   
        kgen.init(length, secureRandom);  
        SecretKey secretKey = kgen.generateKey();  
        byte[] enCodeFormat = secretKey.getEncoded();  
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化  
        byte[] result = cipher.doFinal(content);  
        return result; // 加密  
                  
               
  
    }  
  
//  /**  
//   * 将二进制转换成16进制  
//   *   
//   * @param buf  
//   * @return  
//   */  
//  public static String parseByte2HexStr(byte buf[]) {  
//      StringBuffer sb = new StringBuffer();  
//      for (int i = 0; i < buf.length; i++) {  
//          String hex = Integer.toHexString(buf[i] & 0xFF);  
//          if (hex.length() == 1) {  
//              hex = '0' + hex;  
//          }  
//          sb.append(hex.toUpperCase());  
//      }  
//      return sb.toString();  
//  }  
//  
//  /**  
//   * 将16进制转换为二进制  
//   *   
//   * @param hexStr  
//   * @return  
//   */  
//  public static byte[] parseHexStr2Byte(String hexStr) {  
//      if (hexStr.length() < 1)  
//          return null;  
//      byte[] result = new byte[hexStr.length() / 2];  
//      for (int i = 0; i < hexStr.length() / 2; i++) {  
//          int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);  
//          int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),  
//                  16);  
//          result[i] = (byte) (high * 16 + low);  
//      }  
//      return result;  
//  }  
  
    /** 
     * 加密 
     *  
     * @param content 
     *            需要加密的内容 
     * @param password 
     *            加密密码 
     * @return 
     */  
    public static byte[] encrypt2(String content, String password) {  
        try {  
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");  
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");  
            byte[] byteContent = content.getBytes("utf-8");  
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化  
            byte[] result = cipher.doFinal(byteContent);  
            return result; // 加密  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    public static String encrypt2Str(String content, String password) throws Exception {  
        byte[] encryptResult = encrypt(content, password);  
        return Base64.encode(encryptResult);  
    }  
  
    public static String decrypt2Str(String content, String password) throws Exception {  
  
        byte[] decryptResult = decrypt(Base64.decode(content), password);  
        return new String(decryptResult,"UTF-8");  
    }  
   
}
