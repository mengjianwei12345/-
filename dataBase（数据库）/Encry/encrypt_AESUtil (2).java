








 
/**
 * AES对称加密
 * @author YQ
 *
 */
public class AESUtil {
	
	public static String encrypt(String strKey, String strIn) throws Exception {
        SecretKeySpec skeySpec = getKey(strKey);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(strIn.getBytes()); 
        return new Base64().encode(encrypted);
    }

    public static String decrypt(String strKey, String strIn) throws Exception {
        SecretKeySpec skeySpec = getKey(strKey);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] encrypted1 = new Base64().decode(strIn);

        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original);
        return originalString;
    }

    private static SecretKeySpec getKey(String strKey) throws Exception {
        byte[] arrBTmp = strKey.getBytes();
        byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0） 
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        } 
        SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES"); 
        return skeySpec;
    }
    
    public static String encrypt(String strIn) {
    	
    	String code = null;
		try {
			Random rand = new Random((new Date()).getTime()); 
	    	int num = rand.nextInt(10);
	    	String key = num + UUID.randomUUID().toString().replace("-", "").substring(0, num);
			code = AESUtil.encrypt(key, strIn);
			code = key +  code;
		} catch (Exception e) { 
			e.printStackTrace();
		}
    	return code;
    }
    
    public static String decrypt(String strIn) {
    	String code = null;
		try { 
	    	int num = Integer.valueOf(strIn.substring(0, 1)); 
	    	String key = strIn.substring(1,num+1);
	    	key = num + key;
	    	strIn = strIn.substring(num+1);
	    	code = AESUtil.decrypt(key, strIn);
		} catch (Exception e) { 
			e.printStackTrace();
		}
    	return code;
    }
}
