



class DiffieHellman{
	public static void main(String[] args) throws java.lang.Exception{
		//global parameters
		BigInteger g=new BigInteger("11");
		BigInteger a=new BigInteger("2");
		//private key of userA
		BigInteger Xa=new BigInteger("9");
		BigInteger Ya=a.modPow(Xa,g);

		//private key of userB
		BigInteger Xb=new BigInteger("4");
		BigInteger Yb=a.modPow(Xb,g);

		//calculating K

		BigInteger ka=Ya.modPow(Xb,g);
		BigInteger kb=Yb.modPow(Xa,g);

		System.out.println(Ya);
		System.out.println(Yb);
		System.out.println(ka);
		System.out.println(kb);




		if(ka.equals(kb)){
			System.out.println("Verified");
		}	
		else{
			System.out.println("Not VErified");
		}

	}
}