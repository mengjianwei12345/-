

  
  
  
  
  
  
   
  
  
  
  

public class DESUtil {

	/** 
     * 根据参数生成 KEY 
     */  
   private static Key getKey(String strKey) {  
	   Key key = null ; 
      try {  
          KeyGenerator _generator = KeyGenerator.getInstance ( "DES" );  
          _generator.init( new SecureRandom(strKey.getBytes()));  
           key = _generator.generateKey();  
          _generator = null ;            
      } catch (Exception e) {  
          throw new RuntimeException(  
                 "Error initializing SqlMap class. Cause: " + e);  
      }  
      return key;
   }  
  
   /** 
     * 加密 String 明文输入 ,String 密文输出 
     */  
   public static String encrypt(String keyStr,String strMing) {  
      byte [] byteMi = null ;  
      byte [] byteMing = null ;  
      String strMi = "" ;  
      try {  
          byteMing = strMing.getBytes( "UTF8" );  
          byteMi = encryptByte(keyStr,byteMing);  
          strMi = Base64.encode(byteMi);  
      } catch (Exception e) {  
          throw new RuntimeException(  
                 "Error initializing SqlMap class. Cause: " + e);  
      } finally {  
          byteMing = null ;  
          byteMi = null ;  
      }  
      return strMi;  
   }  
  
   /** 
     * 解密 以 String 密文输入 ,String 明文输出 
     * 
     * @param strMi 
     * @return 
     */  
   public static String decrypt(String keyStr,String strMi) {  
      byte [] byteMing = null ;  
      byte [] byteMi = null ;  
      String strMing = "" ;  
      try {  
          byteMi = Base64.decode(strMi);  
          byteMing = decryptByte(keyStr,byteMi);  
          strMing = new String(byteMing, "UTF8" );  
      } catch (Exception e) {  
          throw new RuntimeException(  
                 "Error initializing SqlMap class. Cause: " + e);  
      } finally {  
          byteMing = null ;  
          byteMi = null ;  
      }  
      return strMing;  
   }  
  
   /** 
     * 加密以 byte[] 明文输入 ,byte[] 密文输出 
     * 
     * @param byteS 
     * @return 
     */  
   private static byte [] encryptByte( String keyStr,byte [] byteS) {  
      byte [] byteFina = null ;  
      Cipher cipher;  
      try {  
    	  Key key =getKey(keyStr);
          cipher = Cipher.getInstance ( "DES" );  
          cipher.init(Cipher. ENCRYPT_MODE , key );  
          byteFina = cipher.doFinal(byteS);  
      } catch (Exception e) {  
          throw new RuntimeException(  
                 "Error initializing SqlMap class. Cause: " + e);  
      } finally {  
          cipher = null ;  
      }  
      return byteFina;  
   }  
  
   /** 
     * 解密以 byte[] 密文输入 , 以 byte[] 明文输出 
     * 
     * @param byteD 
     * @return 
     */  
   private static byte [] decryptByte( String keyStr, byte [] byteD) {  
      Cipher cipher;  
      byte [] byteFina = null ;  
      try {  
    	  Key key =getKey(keyStr);
          cipher = Cipher.getInstance ( "DES" );  
          cipher.init(Cipher. DECRYPT_MODE , key );  
          byteFina = cipher.doFinal(byteD);  
      } catch (Exception e) {  
          throw new RuntimeException(  
                 "Error initializing SqlMap class. Cause: " + e);  
      } finally {  
          cipher = null ;  
      }  
      return byteFina;  
   }  
  
   /** 
     * 文件 file 进行加密并保存目标文件 destFile 中 
     * 
     * @param file 
     *             要加密的文件 如 c:/test/srcFile.txt 
     * @param destFile 
     *             加密后存放的文件名 如 c:/ 加密后文件 .txt 
     */  
   public void encryptFile( String keyStr,String file, String destFile) throws Exception {  
      Cipher cipher = Cipher.getInstance ( "DES" );  
      Key key =getKey(keyStr);
      cipher.init(Cipher. ENCRYPT_MODE ,  key );  
      InputStream is = new FileInputStream(file);  
      OutputStream out = new FileOutputStream(destFile);  
      CipherInputStream cis = new CipherInputStream(is, cipher);  
      byte [] buffer = new byte [1024];  
      int r;  
      while ((r = cis.read(buffer)) > 0) {  
          out.write(buffer, 0, r);  
      }  
      cis.close();  
      is.close();  
      out.close();  
   }  
  
   /** 
     * 文件采用 DES 算法解密文件 
     * 
     * @param file 
     *             已加密的文件 如 c:/ 加密后文件 .txt * 
     * @param destFile 
     *             解密后存放的文件名 如 c:/ test/ 解密后文件 .txt 
     */  
   public void decryptFile(String keyStr,String file, String dest) throws Exception {  
      Cipher cipher = Cipher.getInstance ( "DES" );  
      Key key =getKey(keyStr);
      cipher.init(Cipher. DECRYPT_MODE ,  key );  
      InputStream is = new FileInputStream(file);  
      OutputStream out = new FileOutputStream(dest);  
      CipherOutputStream cos = new CipherOutputStream(out, cipher);  
      byte [] buffer = new byte [1024];  
      int r;  
      while ((r = is.read(buffer)) >= 0) {  
          cos.write(buffer, 0, r);  
      }  
      cos.close();  
      out.close();  
      is.close();  
   }  
  
}
