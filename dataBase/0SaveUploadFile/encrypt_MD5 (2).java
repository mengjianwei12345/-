



public class MD5 {
	public static String ToMD5(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		BigInteger hash = new BigInteger(1, md.digest(password.getBytes()));
		return hash.toString();
	}
}
