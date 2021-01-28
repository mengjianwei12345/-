














public class AESFileEncryption {

	public static String new_path;

	static String AESEncryption(String password, String path) throws Exception {
		FileInputStream inFile = new FileInputStream(path);
		new_path = new String("/Users/shreya/Downloads/Encoded/aes_encryptedfile.des");

		File myObj = new File(path);
		myObj.delete(); 
		
		FileOutputStream outFile = new FileOutputStream(new_path);
		byte[] salt = new byte[8];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(salt);
		FileOutputStream saltOutFile = new FileOutputStream("/Users/shreya/Downloads/Encoded/salt.enc");
		saltOutFile.write(salt);
		saltOutFile.close();

		SecretKeyFactory factory = SecretKeyFactory
				.getInstance("PBKDF2WithHmacSHA1");
		KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,
				256);
		SecretKey secretKey = factory.generateSecret(keySpec);
		SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		FileOutputStream ivOutFile = new FileOutputStream("/Users/shreya/Downloads/Encoded/iv.enc");
		byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
		ivOutFile.write(iv);
		ivOutFile.close();

		byte[] input = new byte[64];
		int bytesRead;

		while ((bytesRead = inFile.read(input)) != -1) {
			byte[] output = cipher.update(input, 0, bytesRead);
			if (output != null)
				outFile.write(output);
		}

		byte[] output = cipher.doFinal();
		if (output != null)
			outFile.write(output);

		inFile.close();
		outFile.flush();
		outFile.close();

		System.out.println("File Encrypted.");

		return new_path;
	}

}