




/**
 * 异或对称加密
 * 
 * @author YQ
 *
 */
public class EncryptUtil {
	
	private static final String CHAR_SET = "UTF-8";
	
	/**
	 * 加解密方式方法，第一次调用为加密，第二次调用为解密
	 * @param b
	 * @param key
	 * @return
	 */
	public static byte[] encrypt(byte[] b, String key) throws IOException{
		byte[] keyData = (byte[]) null;
		keyData = key.getBytes(CHAR_SET);
		int keyIndex = 0;
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) (b[i] ^ keyData[keyIndex]);
			keyIndex++;
			if (keyIndex == keyData.length) {
				keyIndex = 0;
			}
		}
		return b;
	}
	
	/**
	 * 只加密
	 * @param b
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static String encrypt(String s, String key) throws IOException{
		byte[] result = encrypt(s.getBytes(CHAR_SET), key);
		return Base64.encode(result);
	}
	/**
	 * 只解密
	 * @param s
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static String decrypt(String s, String key) throws IOException{
		byte[] bytes = Base64.decode(s);
		byte[] result = encrypt(bytes, key);
		return new String(result);
	}
	 

	public static void main(String[] args)throws IOException {
		try {
			String s = "abs123中文sdf";
			String key = "testkey";
			byte[] result = encrypt(s.getBytes("UTF-8"), key);
			String code = Base64.encode(result);
			System.out.println("code:"+code);
			System.out.println(new String(encrypt(result, key)));
			
			code = encrypt(s, key);
			System.out.println("jiami:"+code );
			System.out.println("jiemi:"+decrypt(code, key));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
