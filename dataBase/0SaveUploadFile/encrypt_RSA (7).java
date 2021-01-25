





public class RSA {

  private BigInteger d, e, N, p, q, phi;
  private Random r;
  private int bitLength;

  public RSA() {
    // Two random unique prime numbers (p and q)
    // NOTE: If the message is larger than the modulus (N), the message recieved
    //       after decryption will be inaccurate. Use large prime numbers to avoid this.
    bitLength = 1024;
    r = new Random();
    p = BigInteger.probablePrime(bitLength, r);
    q = BigInteger.probablePrime(bitLength, r);

    // First part of public key (modulus N)
    N = p.multiply(q);
    // The totient (phi)
    phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    // Second part of public key (encryption exponent e)
    // (1 < e < phi) AND (e is coprime to phi)
    e = BigInteger.valueOf(2);
    // e = BigInteger.probablePrime(bitLength / 2, r);
    while ((e.compareTo(phi) < 0) && (e.gcd(phi).compareTo(BigInteger.ONE) > 0)) {
        e = e.add(BigInteger.ONE);
    }

    // Private key (decryption exponent d)
    // d * e = 1 + (k * phi)
    // TODO: Try to solve for d by implementing the extended Euclid algorithm
    d = e.modInverse(phi);

    // TODO: Generate files to store key values instead of printing to screen
    System.out.println(String.format("Public: %d , %d", e, N));
    System.out.println(String.format("Private: %d", d));
  }

  public RSA(BigInteger e, BigInteger d, BigInteger N) {
    this.e = e;
    this.d = d;
    this.N = N;
  }

  public String getE() {
    return ((this.e).toString());
  }

  public void setE(BigInteger e) {
    this.e = e;
  }

  public String getD() {
    return ((this.d).toString());
  }

  public void setD(BigInteger d) {
    this.d = d;
  }

  public String getN() {
    return ((this.N).toString());
  }

  public void setN(BigInteger N) {
    this.N = N;
  }

  public byte[] encrypt(byte[] message) {
    // x = (message ^ e) % n
    return (new BigInteger(message)).modPow(e, N).toByteArray();
  }

  public byte[] decrypt(byte[] message) {
    // x = (message ^ d) % n
    return (new BigInteger(message)).modPow(d, N).toByteArray();
  }
}
