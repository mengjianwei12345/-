




/**
* @author wjn
* MD5加密工具类
*/
public class MD5Util {

	public static String getPwd(String pwd){
		//mysql:900150983cd24fb0d6963f7d28e17f72
		//自己的：  90150983cd24fb0d6963f7d28e17f72
		//自己2：   900150983cd24fb0d6963f7d28e17f72
		try {
			//获取加密的对象
			MessageDigest digest = MessageDigest.getInstance("md5");
			//加密动作
			byte[] bs = digest.digest(pwd.getBytes());
			//向着mysql的方式优化——将所有的数据，转换成16进制格式
			String str = "";
			for (byte b : bs) {
				//第一步：先将所有的数据转换成正数
				//byte  b :1011 1111
				//int   255 :0000 0000 0000 0000 0000 0000 1111 1111 
				//b :   0000 0000 0000 0000 0000 0000 1011 1111
				int temp = b & 255;
				//第二步：将数据转换成16进制格式
				//判断，如果当前数据小于16 ，并且大于等于0，那么转换成16进制格式的时候，补上一个0
				if(temp >= 0 && temp <16){
					
					str = str + "0" +Integer.toHexString(temp);
				}else{
					str = str + Integer.toHexString(temp);
				}
			}
			return str;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("用户密码加密异常");
		}
	}
	
}

