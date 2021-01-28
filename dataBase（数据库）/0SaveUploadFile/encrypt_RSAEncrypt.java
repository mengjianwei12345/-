






















/**
 * 
 * @author mead
 * 
 */
public final class RSAEncrypt {
	// Ĭ�Ϲ�Կ��˽Կ�����ڲ���
	public static String DEFAULT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMw9jllsLVz7uWEeEA0jJvw+FiZ4sGmgcliUaBu0ggJtaLu0DarOufxjRFENEe/lRa4/fUkcFg5TqYWsRTQOZ6oWgads0DdTlkYgMwnZBbUZJFmCGRg0ibkF2hx9uUf/UqyJEj4y93v9KWgyba+qBJTitcjYBEobS/rxBiIMlNBwIDAQAB";
	public static String DEFAULT_PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMzD2OWWwtXPu5YR4QDSMm/D4WJniwaaByWJRoG7SCAm1ou7QNqs65/GNEUQ0R7+VFrj99SRwWDlOphaxFNA5nqhaBp2zQN1OWRiAzCdkFtRkkWYIZGDSJuQXaHH25R/9SrIkSPjL3e/0paDJtr6oElOK1yNgEShtL+vEGIgyU0HAgMBAAECgYEArBVnYcNqqE+1QXBzHMKJ+p2u+4kzMHAf027jn0FqYwLgyXusDFrIhnUHFogEhzyMXfo6HB4GW00qCkI1vhXL2wA/LCLzVpkQx864vo/YK/gqwM+e45HZgihKpDE4MUzhIGvP9VFKLlOGfA5yRQmU0xq/uEPFj1dYdggq9nt1zlECQQDu6PVUGBIrDY4VhaWdCtc1yctGv2FSodom8ERlZJhQOLqFj/OM0rKslaz9s0OiEIjHwdKzOSL36u0NUJbE3F35AkEA22mb4hGot9ETUN71u3GSeotBArBizcJlub3ZFDCmCOUlB2RMX4fYUJMHQU9NPQIzwHxUl9ghlHuREE2E8tnC/wJBANQwZcw1wNUqKdqlo80SFsh4HKoOSkMY62eQGD8xlDIT4F3F15toezbbjRSbBePH6aP5WsjBY3Rr2/Tqca4QxwkCQQCvGy60EiHTaYF/5iC5Ep+zOxWMHtcLnVxwCyqG6Xho2u9dYddt2k1xqJV+stpSmUJSGSet82iBRshz+VnkMpRjAkAuIm5o0IG31mUWi22ifZZr2tw2OHIV+aSt8bjK7X+/YwHYoKCMRzi+pMSTb0ZV3IDDmFu2i9bQe1y52h14pqht";

	/**
	 * ˽Կ
	 */
	private RSAPrivateKey privateKey;

	/**
	 * ��Կ
	 */
	private RSAPublicKey publicKey;

	/**
	 * �ֽ�����ת�ַ���ר�ü���
	 */
	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * ��ȡ˽Կ
	 * 
	 * @return ��ǰ��˽Կ����
	 */
	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}

	/**
	 * ��ȡ��Կ
	 * 
	 * @return ��ǰ�Ĺ�Կ����
	 */
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * ���������Կ��
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
	 * ���ļ����������м��ع�Կ
	 * 
	 * @param in
	 *            ��Կ������
	 * @throws Exception
	 *             ���ع�Կʱ�������쳣
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
			throw new Exception("��Կ��������ȡ����");
		} catch (NullPointerException e) {
			throw new Exception("��Կ������Ϊ��");
		}
	}

	/**
	 * ���ַ����м��ع�Կ
	 * 
	 * @param publicKeyStr
	 *            ��Կ�����ַ���
	 * @throws Exception
	 *             ���ع�Կʱ�������쳣
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
			throw new Exception("�޴��㷨");
		} catch (InvalidKeySpecException e) {
			throw new Exception("��Կ�Ƿ�");
		} catch (NullPointerException e) {
			throw new Exception("��Կ�������ݶ�ȡ����");
		} catch (Exception e) {
			throw new Exception("��Կ����Ϊ��");
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
			throw new Exception("˽Կ���ݶ�ȡ����");
		} catch (NullPointerException e) {
			throw new Exception("˽Կ������Ϊ��");
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
			throw new Exception("�޴��㷨");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new Exception("˽Կ�Ƿ�");
		} catch (NullPointerException e) {
			throw new Exception("˽Կ�������ݶ�ȡ����");
		} catch (Exception e) {
			throw new Exception("˽Կ����Ϊ��");
		}
	}

	/**
	 * ���ܹ���
	 * 
	 * @param publicKey
	 *            ��Կ
	 * @param plainTextData
	 *            ��������
	 * @return
	 * @throws Exception
	 *             ���ܹ����е��쳣��Ϣ
	 */
	public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)
			throws Exception {
		if (publicKey == null) {
			throw new Exception("���ܹ�ԿΪ��, ������");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA");// , new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("�޴˼����㷨");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("���ܹ�Կ�Ƿ�,����");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("���ĳ��ȷǷ�");
		} catch (BadPaddingException e) {
			throw new Exception("������������");
		}
	}

	/**
	 * ���ܹ���
	 * 
	 * @param privateKey
	 *            ˽Կ
	 * @param cipherData
	 *            ��������
	 * @return ����
	 * @throws Exception
	 *             ���ܹ����е��쳣��Ϣ
	 */
	public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)
			throws Exception {
		if (privateKey == null) {
			throw new Exception("����˽ԿΪ��, ������");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA");// , new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("�޴˽����㷨");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("����˽Կ�Ƿ�,����");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("���ĳ��ȷǷ�");
		} catch (BadPaddingException e) {
			throw new Exception("������������");
		}
	}

	/**
	 * �ֽ�����תʮ�������ַ���
	 * 
	 * @param data
	 *            ��������
	 * @return ʮ����������
	 */
	public static String byteArrayToString(byte[] data) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			// ȡ���ֽڵĸ���λ ��Ϊ�����õ���Ӧ��ʮ�����Ʊ�ʶ�� ע���޷�������
			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
			// ȡ���ֽڵĵ���λ ��Ϊ�����õ���Ӧ��ʮ�����Ʊ�ʶ��
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

		// ���ع�Կ
		try {
			rsaEncrypt.loadPublicKey(RSAEncrypt.DEFAULT_PUBLIC_KEY);
			System.out.println("���ع�Կ�ɹ�");
			String key = RSAKeyUtil.getPEMkey(rsaEncrypt.getPublicKey());
			System.out.println("key=\n" + key);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("���ع�Կʧ��");
		}

		// ����˽Կ
		try {
			rsaEncrypt.loadPrivateKey(RSAEncrypt.DEFAULT_PRIVATE_KEY);
			System.out.println("����˽Կ�ɹ�");
			String key = RSAKeyUtil.getPEMkey(rsaEncrypt.getPrivateKey());
			System.out.println("key=\n" + key);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("����˽Կʧ��");
		}

		// �����ַ���
		String encryptStr = "meadlai@77";
		System.out.println("˽Կ���ȣ�"
				+ rsaEncrypt.getPrivateKey().toString().length());
		System.out.println("��Կ���ȣ�"
				+ rsaEncrypt.getPublicKey().toString().length());
		try {
			// ����
			byte[] cipher = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(),
					encryptStr.getBytes());

			// ����
			byte[] plainText = rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(),
					cipher);

			System.out.println("���ĳ���:" + cipher.length);
			System.out.println(RSAEncrypt.byteArrayToString(cipher));
			String secure = org.apache.commons.codec.binary.Base64
					.encodeBase64String(cipher);
			System.out.println("secureWith64=\n" + secure);

			System.out.println("���ĳ���:" + plainText.length);
			System.out.println(RSAEncrypt.byteArrayToString(plainText));
			String plain = org.apache.commons.codec.binary.Base64
					.encodeBase64String(plainText);
			System.out.println("plainWith64=\n" + plain);

			System.out.println(new String(plainText));
			// ���ܿͻ�������
			String iosString1 = "iSrDsNFUsA0NbFYODSgLm8s/jbjf08KHPSP69SpE4+B7hiachzoOI4ouIY27O05mm6VkEtlVggLh+eFK4sWFhcdjIkI++0AZouJw6RiYvWOxLEnV+OG9t5m9z18NHZgM4+IzE2QyhALjvnxnpbxYnXDVcbzRjHC9l42H8RXhL5A=";
			byte[] data = org.apache.commons.codec.binary.Base64
					.decodeBase64(iosString1);
			plainText = rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), data);
			System.out.println("�����ַ���=" + new String(plainText));
			iosString1 = "Sx4A00xBAmW5vL4H4cIxl1MZVr9pXb+/Y82RmkGK04f4J1282hurtbBO3tSxLPU+raRF7GIlN6HsNZQXu4i1PSZ9nqaiyM1qApmaNQwPQX+IqiRB69C3ZvtOqmF9dEmYXN8MQ/dSUNPm5DtbvxT0A0aipOV3r/rHTKKoBJMb7f0=";
			data = org.apache.commons.codec.binary.Base64
					.decodeBase64(iosString1);
			plainText = rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), data);
			System.out.println("�����ַ���=" + new String(plainText));

			
			iosString1 = "g40fqAetpkn34xVnWafXvoK6PKuwIVoa2j1tn/bv1cvvXWrI4FmvIKqAxWZMHUKoGv+N3ddBZVjkunq7HYyUdznRBy4zA5hJYSAGWpP8xGTRh3MQgLrgtPeWy0+pZz0B8QsXSMPCobE0jVpGHrgnwGn9YlDqr7Y8aCUqG25UVEE=";
			data = org.apache.commons.codec.binary.Base64
					.decodeBase64(iosString1);
			plainText = rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), data);
			System.out.println("iosString1�����ַ���=" + new String(plainText));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
