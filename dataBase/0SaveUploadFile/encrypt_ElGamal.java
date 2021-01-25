

public class ElGamal {
	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		Random r = new Random();
		System.out.println("Enter the value of the prime number for your El Gamal key.");
		
		BigInteger p = getNextPrime(stdin.next());	
		BigInteger g = getGenerator(p, r);

		if (g != null) {
			BigInteger a = new BigInteger(p.bitCount()-1, r);
			BigInteger b = g.modPow(a, p);
			System.out.println("Post p = "+p+" g = "+g+" b = "+b);
			BigInteger k = new BigInteger(p.bitCount()-1, r);
			
			BigInteger c1 = g.modPow(k, p);
			BigInteger c2 = b.modPow(k, p);
			System.out.println("Please enter your message in between 1 and "+p);
			BigInteger m = new BigInteger(stdin.next());
			c2 = c2.multiply(m);
			c2 = c2.mod(p);
			System.out.println("The corresponding cipher texts are c1 = "+c1+" c2 = "+c2);
			BigInteger temp = c1.modPow(a,p);
			temp = temp.modInverse(p);
			
			System.out.println("Here is c1^ -a = "+temp);
			BigInteger recover = temp.multiply(c2);
			recover = recover.mod(p);
			System.out.println("The original message = "+recover);
		}
		else
			System.out.println("Sorry, a generator for your prime couldn't be found.");
	}


	// Incrementally tries each BigInteger starting at the value passed
	// in as a parameter until one of them is tests as being prime.
	public static BigInteger getNextPrime(String ans) {
		
		BigInteger one = new BigInteger("1");
		BigInteger test = new BigInteger(ans);
		while (!test.isProbablePrime(99))
			test = test.add(one);
		return test;		
	}
	public static BigInteger getGenerator(BigInteger p, Random r) {
		int numtries = 0;
		while (numtries < 1000) {
    		BigInteger rand = new BigInteger(p.bitCount()-1,r);
    		BigInteger exp = BigInteger.ONE;
    		BigInteger next = rand.mod(p);
    		while (!next.equals(BigInteger.ONE)) {
      			next = (next.multiply(rand)).mod(p);
      			exp = exp.add(BigInteger.ONE);
    		}
    		if (exp.equals(p.subtract(BigInteger.ONE)))
      			return rand;
      	}     	
      	return null;
  }
}
