



public class SHA{
	public static void main(String args[]) throws NoSuchAlgorithmException{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the plain text:");
		String s=sc.nextLine();
		System.out.println("The hashed results:");
		System.out.println(Func_sha(s));
	}
	static String Func_sha(String s) throws NoSuchAlgorithmException{
		MessageDigest mDigest= MessageDigest.getInstance("SHA1");
		byte[] result= mDigest.digest(s.getBytes());
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<result.length;i++){
			sb.append(Integer.toString((result[i] & 0xff)+0x100, 16).substring(1));
		}
		return sb.toString();
	}
}
