


class rsaAlg{
	private BigInteger p, q, n, phi, e, d; /* public key components */
	private int bitLen = 1024;
	private int blkSz = 256; /* block size in bytes */
	private Random rand;

	public rsaAlg(){
		rand = new Random();
		p = BigInteger.probablePrime(bitLen, rand);
		q = BigInteger.probablePrime(bitLen, rand);
		n = p.multiply(q);
		phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)); 
		e = BigInteger.probablePrime(bitLen/2, rand);
		while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0){
			e.add(BigInteger.ONE);
		}
		d = e.modInverse(phi);
	}


	/* convert bytes to string */
	private static String bytesToString(byte[] encrypted){
		String str = "";
		for (byte b : encrypted){
			str += Byte.toString(b);
		}
		return str;
	}

	/* encrypt message */
	public byte[] encrypt(byte[] msg){
		return (new BigInteger(msg)).modPow(e, n).toByteArray();
	}


	/* decrypt message */
	public byte[] decrypt(byte[] msg){
		return (new BigInteger(msg)).modPow(d, n).toByteArray();
	}

	//main
	public static void main (String[] args) throws java.lang.Exception{
		rsaAlg rsaObj = new rsaAlg();
		String msg = "Hello world! Security Laboratory";
		System.out.println("simulation of RSA algorithm");
		System.out.println("message(string) : " + msg);
		System.out.println("message(bytes) : " + bytesToString(msg.getBytes()));
		/* encrypt test message */
		byte[] ciphertext = rsaObj.encrypt(msg.getBytes());
		System.out.println("ciphertext(bytes) : " + bytesToString(ciphertext)); 
		/*decrypt ciphertext */
		byte[] plaintext = rsaObj.decrypt(ciphertext);
		System.out.println("plaintext(bytes) : " + bytesToString(plaintext));
		System.out.println("plaintext(string) : " + new String(plaintext));
	}
} 