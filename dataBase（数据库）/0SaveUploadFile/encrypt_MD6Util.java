


 
/**
 * MD6
 * 即非对称加密，且同一字符每次加密结果不一致
 * @author YQ
 *
 */
public class MD6Util {
	
	private static final String CHS_STR = "0123456789abcdefghijklmnopqrstuvwxyz";
	/**
	 * MD6字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String MD6(String s) {
		Random rd = new Random();
		int rd1 = rd.nextInt(35);
		rd = new Random();
		int rd2 = rd.nextInt(35);
		String rdStr = String.valueOf( CHS_STR.charAt(rd1)) +String.valueOf( CHS_STR.charAt(rd2))  ;
		String str = MD5Util.MD5(rdStr + s);
		str = rdStr + str.substring(0,30);
		return str;
	}
	
	public static boolean MD6Equal(String md6Str,String s) {
		if(md6Str ==null || md6Str.length() != 32){
			return false;
		}
		String rdStr = md6Str.substring(0,2);
		String str = MD5Util.MD5(rdStr + s);
		str = rdStr + str.substring(0,30);
		if(str.equals(md6Str)){
			return true;
		}
		return false;
	}
	

}
