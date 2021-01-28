









public class DES {
    /**
    * 加密
    * @param datasource byte[]
    * @param password String
    * @return byte[]
    */
    public static String encrypt(byte[] datasource, String password) { 
    try{
	    SecureRandom random = new SecureRandom();
	    DESKeySpec desKey = new DESKeySpec(password.getBytes());
	    //创建一个密匙工厂，然后用它把DESKeySpec转换成
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	    SecretKey securekey = keyFactory.generateSecret(desKey);
	    //Cipher对象实际完成加密操作
	    Cipher cipher = Cipher.getInstance("DES");
	    //用密匙初始化Cipher对象
	    cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
	    //现在，获取数据并加密
	    //正式执行加密操作
	    return byteArr2HexStr(cipher.doFinal(datasource));
	    }catch(Throwable e){
	    e.printStackTrace();
    }
    return null;
    }
    /**
    * 解密
    * @param src byte[]
    * @param password String
    * @return byte[]
    * @throws Exception
    */
    public static byte[] decrypt(byte[] src, String password) throws Exception {
    	// DES算法要求有一个可信任的随机数源
    	SecureRandom random = new SecureRandom();
	    // 创建一个DESKeySpec对象
	    DESKeySpec desKey = new DESKeySpec(password.getBytes());
	    // 创建一个密匙工厂
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	    // 将DESKeySpec对象转换成SecretKey对象
	    SecretKey securekey = keyFactory.generateSecret(desKey);
	    // Cipher对象实际完成解密操作
	    Cipher cipher = Cipher.getInstance("DES");
	    // 用密匙初始化Cipher对象
	    cipher.init(Cipher.DECRYPT_MODE, securekey, random);
	    // 真正开始解密操作
	    return cipher.doFinal(src);
    }

    /** 
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[] 
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程 

     *  
     * @param arrB 
     *            需要转换的byte数组 

     * @return 转换后的字符串 
     * @throws Exception 
     *             本方法不处理任何异常，所有异常全部抛出 
     */  
    private static String byteArr2HexStr(byte[] arrB) throws Exception {  
        int iLen = arrB.length;  
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍  

        StringBuffer sb = new StringBuffer(iLen * 2);  
        for (int i = 0; i < iLen; i++) {  
            int intTmp = arrB[i];  
            // 把负数转换为正数  
            while (intTmp < 0) {  
                intTmp = intTmp + 256;  
            }  
            // 小于0F的数需要在前面补0  
            if (intTmp < 16) {  
                sb.append("0");  
            }  
            sb.append(Integer.toString(intTmp, 16));  
        }  
        return sb.toString();  
    }  

    /** 
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB) 
     * 互为可逆的转换过程 

     *  
     * @param strIn 
     *            需要转换的字符串 
     * @return 转换后的byte数组 

     * @throws Exception 
     *             本方法不处理任何异常，所有异常全部抛出 
     */  
    private static byte[] hexStr2ByteArr(String strIn) throws Exception {  
        byte[] arrB = strIn.getBytes();  
        int iLen = arrB.length;  

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2  
        byte[] arrOut = new byte[iLen / 2];  
        for (int i = 0; i < iLen; i = i + 2) {  
            String strTmp = new String(arrB, i, 2);  
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);  
        }  
        return arrOut;  
    }  

    //随机生成密钥
    private static String getKey() throws Exception{
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 为我们选择的DES算法生成一个KeyGenerator对象
            KeyGenerator kg = KeyGenerator.getInstance("DES");
            kg.init(sr);
            // 生成密匙
            SecretKey key = kg.generateKey();
            // 获取密匙数据
            byte rawKeyData[] = key.getEncoded();

            //return new String(rawKeyData);

            return byteArr2HexStr(rawKeyData);
    }

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {

        //待加密内容
	        String str = "I AM HAPPY ,ANFYOU ?";
	        //密码，长度要是8的倍数
	        String key;
	
	        //随机生成密钥
	        key=getKey();
	
	        String result = DES.encrypt(str.getBytes("UTF8"),key);
	        System.out.println("加密后："+result);
	
	        //直接将如上内容解密
	        try {
		        byte[] decryResult = DES.decrypt(hexStr2ByteArr(result), key);
		        System.out.println("解密后："+new String(decryResult));
	        } catch (Exception e1) {
	        	e1.printStackTrace();
        }
    }

}