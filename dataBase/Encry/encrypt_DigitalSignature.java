//- Digital signature




class DigitalSignature{
	final static BigInteger one = new BigInteger("1");
	final static BigInteger zero = new BigInteger("0");

	public static BigInteger nextPrime(String ans){
		BigInteger test= new BigInteger(ans);
		while(!test.isProbablePrime(99)){
			test=test.add(one);
		}
		return test;
	}

	public static BigInteger FindQ(BigInteger n){
		BigInteger start=new BigInteger("2");
		while(!n.isProbablePrime(99)){
			while(!(n.mod(start)).equals(zero)){
				start.add(one);
			}
			n=n.divide(start);
		}
		return n;
	}

	public static BigInteger Getg(BigInteger p,BigInteger q,Random r){
		BigInteger h=new BigInteger(p.bitLength(),r);
		h=h.mod(p);
		return h.modPow(((p.subtract(one)).divide(q)),p);
	}

	public static void main (String[] args) throws java.lang.Exception{
		Random randObj = new Random();
		
		//global keys
		BigInteger p=nextPrime("106");
		BigInteger q=FindQ(p.subtract(one));
		BigInteger g=Getg(p,q,randObj);

		//printing public key components
		System.out.println("simulation of Digital Signature Algorithm");
		System.out.println("global public key components are:");
		System.out.println("p is: " + p);
		System.out.println("q is: " + q);
		System.out.println("g is: " + g);

		//private components
		//finding the privatekey x
		BigInteger x=new BigInteger(q.bitLength(),randObj);
		x=x.mod(q);

		//finding the publickey y
		BigInteger y=g.modPow(x,q);
		
		//finding the k
		BigInteger k=new BigInteger(q.bitLength(),randObj);
		k=k.mod(q);


		//signing

		BigInteger r=(g.modPow(k,p)).mod(q);
		BigInteger hash=new BigInteger(p.bitLength(),randObj);
		BigInteger kInv=k.modInverse(q);
		BigInteger s=(kInv.multiply(hash.add(x.multiply(r)))).mod(q);
		//printing the values
		System.out.println("secret information are:");
		System.out.println("x (private) is: " + x);
		System.out.println("k (secret) is: " + k);
		System.out.println("y (public) is: " + y);
		System.out.println("h (rndhash) is: " + hash);
		System.out.println("generating digital signature:");
		System.out.println("r is : " + r);
		System.out.println("s is : " + s);


		//verifying

		BigInteger w=s.modInverse(q);
		BigInteger u1=(hash.multiply(w)).mod(q);
		BigInteger u2=(r.multiply(w)).mod(q);
		BigInteger v=(g.modPow(u1,p)).multiply(y.modPow(u2,p));
		v=(v.mod(p)).mod(q);

		//printing
		System.out.println("verifying digital signature (checkpoints):");
		System.out.println("w is : " + w);
		System.out.println("u1 is : " + u1);
		System.out.println("u2 is : " + u2);
		System.out.println("v is : " + v);
		if (v.equals(r)){
			System.out.println("success: digital signature is verified! " + r);
		}else{
			System.out.println("error: incorrect digital signature");
		}
	} 
}
