






















/**
 * 
 * @author mead
 * 
 */
public final class RSAEncrypt {
	// 默认公钥和私钥，用于测试
	public static String DEFAULT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMw9jllsLVz7uWEeEA0jJvw+FiZ4sGmgcliUaBu0ggJtaLu0DarOufxjRFENEe/lRa4/fUkcFg5TqYWsRTQOZ6oWgads0DdTlkYgMwnZBbUZJFmCGRg0ibkF2hx9uUf/UqyJEj4y93v9KWgyba+qBJTitcjYBEobS/rxBiIMlNBwIDAQAB";
	public static String DEFAULT_PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMzD2OWWwtXPu5YR4QDSMm/D4WJniwaaByWJRoG7SCAm1ou7QNqs65/GNEUQ0R7+VFrj99SRwWDlOphaxFNA5nqhaBp2zQN1OWRiAzCdkFtRkkWYIZGDSJuQXaHH25R/9SrIkSPjL3e/0paDJtr6oElOK1yNgEShtL+vEGIgyU0HAgMBAAECgYEArBVnYcNqqE+1QXBzHMKJ+p2u+4kzMHAf027jn0FqYwLgyXusDFrIhnUHFogEhzyMXfo6HB4GW00qCkI1vhXL2wA/LCLzVpkQx864vo/YK/gqwM+e45HZgihKpDE4MUzhIGvP9VFKLlOGfA5yRQmU0xq/uEPFj1dYdggq9nt1zlECQQDu6PVUGBIrDY4VhaWdCtc1yctGv2FSodom8ERlZJhQOLqFj/OM0rKslaz9s0OiEIjHwdKzOSL36u0NUJbE3F35AkEA22mb4hGot9ETUN71u3GSeotBArBizcJlub3ZFDCmCOUlB2RMX4fYUJMHQU9NPQIzwHxUl9ghlHuREE2E8tnC/wJBANQwZcw1wNUqKdqlo80SFsh4HKoOSkMY62eQGD8xlDIT4F3F15toezbbjRSbBePH6aP5WsjBY3Rr2/Tqca4QxwkCQQCvGy60EiHTaYF/5iC5Ep+zOxWMHtcLnVxwCyqG6Xho2u9dYddt2k1xqJV+stpSmUJSGSet82iBRshz+VnkMpRjAkAuIm5o0IG31mUWi22ifZZr2tw2OHIV+aSt8bjK7X+/YwHYoKCMRzi+pMSTb0ZV3IDDmFu2i9bQe1y52h14pqht";

	/**
	 * 私钥
	 */
	private RSAPrivateKey privateKey;

	/**
	 * 公钥
	 */
	private RSAPublicKey publicKey;

	/**
	 * 字节数据转字符串专用集合
	 */
	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 获取私钥
	 * 
	 * @return 当前的私钥对象
	 */
	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}

	/**
	 * 获取公钥
	 * 
	 * @return 当前的公钥对象
	 */
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * 随机生成密钥对
	 */
	public void genKeyPair() {
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keyPairGen.initialize(1024, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
		this.publicKey = (RSAPublicKey) keyPair.getPublic();
	}

	/**
	 * 从文件中输入流中加载公钥
	 * 
	 * @param in
	 *            公钥输入流
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public void loadPublicKey(InputStream in) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			this.loadPublicKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥输入流为空");
		}
	}

	/**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public void loadPublicKey(String publicKeyStr) throws Exception {
		try {
			// BASE64Decoder base64Decoder = new BASE64Decoder();
			// byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
			byte[] buffer = org.apache.commons.codec.binary.Base64
					.decodeBase64(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			this.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据内容读取错误");
		} catch (Exception e) {
			throw new Exception("公钥数据为空");
		}
	}

	/**
	 * the base64 encoded key from file
	 * 
	 * @param in
	 * @throws Exception
	 */
	public void loadPrivateKey(InputStream in) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			this.loadPrivateKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("私钥数据读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥输入流为空");
		}
	}

	/**
	 * the base64 encoded key
	 * 
	 * @param privateKeyStr
	 * @throws Exception
	 */
	public void loadPrivateKey(String privateKeyStr) throws Exception {
		try {
			// BASE64Decoder base64Decoder = new BASE64Decoder();
			// byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
			byte[] buffer = org.apache.commons.codec.binary.Base64
					.decodeBase64(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			this.privateKey = (RSAPrivateKey) keyFactory
					.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据内容读取错误");
		} catch (Exception e) {
			throw new Exception("私钥数据为空");
		}
	}

	/**
	 * 加密过程
	 * 
	 * @param publicKey
	 *            公钥
	 * @param plainTextData
	 *            明文数据
	 * @return
	 * @throws Exception
	 *             加密过程中的异常信息
	 */
	public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)
			throws Exception {
		if (publicKey == null) {
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA");// , new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}

	/**
	 * 解密过程
	 * 
	 * @param privateKey
	 *            私钥
	 * @param cipherData
	 *            密文数据
	 * @return 明文
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)
			throws Exception {
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA");// , new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}

	/**
	 * 字节数据转十六进制字符串
	 * 
	 * @param data
	 *            输入数据
	 * @return 十六进制内容
	 */
	public static String byteArrayToString(byte[] data) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			// 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
			// 取出字节的低四位 作为索引得到相应的十六进制标识符
			stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
			if (i < data.length - 1) {
				stringBuilder.append(' ');
			}
		}
		return stringBuilder.toString();
	}

	public static void main(String[] args) throws Exception {

		RSAEncrypt rsaEncrypt = new RSAEncrypt();
		// System.out.println("/n##DEFAULT_PUBLIC_KEY##########\n"
		// + RSAEncrypt.DEFAULT_PUBLIC_KEY);
		// System.out.println("/n##DEFAULT_PRIVATE_KEY##########\n"
		// + RSAEncrypt.DEFAULT_PRIVATE_KEY);

		// 加载公钥
		try {
			rsaEncrypt.loadPublicKey(RSAEncrypt.DEFAULT_PUBLIC_KEY);
			System.out.println("加载公钥成功");
			String key = RSAKeyUtil.getPEMkey(rsaEncrypt.getPublicKey());
			System.out.println("key=\n" + key);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("加载公钥失败");
		}

		// 加载私钥
		try {
			rsaEncrypt.loadPrivateKey(RSAEncrypt.DEFAULT_PRIVATE_KEY);
			System.out.println("加载私钥成功");
			String key = RSAKeyUtil.getPEMkey(rsaEncrypt.getPrivateKey());
			System.out.println("key=\n" + key);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("加载私钥失败");
		}

		// 测试字符串
		String encryptStr = "meadlai@77";
		System.out.println("私钥长度："
				+ rsaEncrypt.getPrivateKey().toString().length());
		System.out.println("公钥长度："
				+ rsaEncrypt.getPublicKey().toString().length());
		try {
			// 加密
			byte[] cipher = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(),
					encryptStr.getBytes());

			// 解密
			byte[] plainText = rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(),
					cipher);

			System.out.println("密文长度:" + cipher.length);
			System.out.println(RSAEncrypt.byteArrayToString(cipher));
			String secure = org.apache.commons.codec.binary.Base64
					.encodeBase64String(cipher);
			System.out.println("secureWith64=\n" + secure);

			System.out.println("明文长度:" + plainText.length);
			System.out.println(RSAEncrypt.byteArrayToString(plainText));
			String plain = org.apache.commons.codec.binary.Base64
					.encodeBase64String(plainText);
			System.out.println("plainWith64=\n" + plain);

			System.out.println(new String(plainText));
			// 解密客户端数据
			String iosString1 = "iSrDsNFUsA0NbFYODSgLm8s/jbjf08KHPSP69SpE4+B7hiachzoOI4ouIY27O05mm6VkEtlVggLh+eFK4sWFhcdjIkI++0AZouJw6RiYvWOxLEnV+OG9t5m9z18NHZgM4+IzE2QyhALjvnxnpbxYnXDVcbzRjHC9l42H8RXhL5A=";
			byte[] data = org.apache.commons.codec.binary.Base64
					.decodeBase64(iosString1);
			plainText = rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), data);
			System.out.println("解密字符串=" + new String(plainText));
			iosString1 = "Sx4A00xBAmW5vL4H4cIxl1MZVr9pXb+/Y82RmkGK04f4J1282hurtbBO3tSxLPU+raRF7GIlN6HsNZQXu4i1PSZ9nqaiyM1qApmaNQwPQX+IqiRB69C3ZvtOqmF9dEmYXN8MQ/dSUNPm5DtbvxT0A0aipOV3r/rHTKKoBJMb7f0=";
			data = org.apache.commons.codec.binary.Base64
					.decodeBase64(iosString1);
			plainText = rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), data);
			System.out.println("解密字符串=" + new String(plainText));

			
			iosString1 = "g40fqAetpkn34xVnWafXvoK6PKuwIVoa2j1tn/bv1cvvXWrI4FmvIKqAxWZMHUKoGv+N3ddBZVjkunq7HYyUdznRBy4zA5hJYSAGWpP8xGTRh3MQgLrgtPeWy0+pZz0B8QsXSMPCobE0jVpGHrgnwGn9YlDqr7Y8aCUqG25UVEE=";
			data = org.apache.commons.codec.binary.Base64
					.decodeBase64(iosString1);
			plainText = rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), data);
			System.out.println("iosString1解密字符串=" + new String(plainText));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
