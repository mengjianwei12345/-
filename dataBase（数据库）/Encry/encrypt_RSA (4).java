


/**
 * Implementacao do algoritmo RSA rivest-shamir-adleman.
 * 
 * [PRIMEIRA OPCAO DE EXECUCAO] 
 * 1. Abra sua IDE [Eclipse/NetBeans]; 
 * 2. Carreque o projeto, selecionando o diret�rio `RSA/`; 
 * 3. Compile o projeto; 
 * 4. Execute o arquivo `RSA.java`, pois este possui a main.
 * 
 * [SEGUNDA OPCAO DE EXECUCAO] 
 * 1. Abra seu terminal [BASH/CMD]; 
 * 2. V� at� o diret�rio onde se encontra o arquivo `RSA.java`; 
 * 3. Digite em seu console: 'nano RSA.java', delete a primeira linha do arquivo e salve-o; 
 * 4. Digite em seu console: 'javac RSA.java'; 
 * 5. Digite em seu console: 'java RSA'.
 * 
 * @author Yuri Oliveira Alves.
 * @see <a href="https://github.com/yurioliveira3">Meu reposit�rio no GitHub</a>
 */
@SuppressWarnings("unused")
public class RSA {

	/**
	 * Vari�veis n, d, e, utilizadas para manipula��o dos valores que ser�o calculados.
	 */
	private BigInteger n, d, e;

	/**
	 * Construtor da classe RSA.
	 *
	 * @param bitlen, tamanho dos numeros p e q, a serem gerados
	 */
	public RSA(int bitlen) {
		
		/** 1. Escolha de forma aleat�ria, de dois n�meros primos grandes, p e q **/
		SecureRandom r = new SecureRandom();
		BigInteger p = new BigInteger(bitlen / 2, 100, r);
		BigInteger q = new BigInteger(bitlen / 2, 100, r);

		/** 2. Calcula n = (p * q) **/
		n = p.multiply(q);

		/** 3. Calcula fun��o totiene, phi(n) = (p -1) (q -1) **/
		BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

		/** 4. Escolha de um inteiro `e`, que satisfaz 1 < `e` < m, `e` e phi(n) devem ser primos entre si **/
		e = new BigInteger("3");
		while (m.gcd(e).intValue() > 1)
			e = e.add(new BigInteger("2"));

		/** 5. `d` deve ser inverso multiplicativo de `e` **/
		d = e.modInverse(m);
		
		/** Setando as vari�veis com os valores calculados**/
		sete(e);
		setn(n);
		setd(d);
		
		/** Print dos valores calculados**/
		System.out.println("p: " + p + "\nq: " + q + "\nn: " + n + "\ne: " + e + "\nd: " + d + "\n");
		
	}
	
	/** Getter de D **/
	private BigInteger getd() {
		return d;
	}
	/** Setter de D **/
	private void setd(BigInteger d) {
		d = this.d;
	}
	/** Getter de N **/
	private BigInteger getn() {
		return n;
	}
	/** Setter de D **/
	private void setn(BigInteger n) {
		n = this.n;
	}
	/** Getter de E **/
	private BigInteger gete() {
		return e;
	}
	/** Setter de D **/
	private void sete(BigInteger e) {
		e = this.e;
	}

	/**
	 * RS A encrypt.
	 *
	 * @param text, texto a ser encriptado.
	 * @param Chave publica(n,e).
	 * @return BigInteger, texto encriptado.
	 */
	private static String RSA_encrypt(String text, BigInteger e, BigInteger n) {
		
		String textcifrado;
		return textcifrado = new BigInteger(text.getBytes()).modPow(e, n).toString();
		
	}

	/**
	 * RS A decrypt.
	 *
	 * @param text, texto a ser decriptada.
	 * @param Chave privada (n,d).
	 * @return String, texto decriptado.
	 */
	private static String RSA_decrypt(String text, BigInteger d, BigInteger n) {
		
		String textdecifrado;
		return textdecifrado = new String(new BigInteger(text).modPow(d, n).toByteArray());
		
	}
	
	/**
	 * Metodo Principal, utilizado na inicializacao do programa
	 * 
	 * @see <a href="https://pt.wikipedia.org/wiki/RSA_(sistema_criptogr%C3%A1fico)">RSA</a>
	 * 
	 */
	public static void main(String args[]) {
		
		String text = "Teste";
		int bitlen = 2048;

		RSA rsa = new RSA(bitlen);

		System.out.println("Mensagem original:\t" + text);
		
		System.out.println("Mensagem cifrada:\t" + RSA_encrypt(text, rsa.gete(), rsa.getn()));
		
		System.out.println("Mensagem decifrada:\t" + RSA_decrypt(RSA_encrypt(text, rsa.gete(), rsa.getn()), rsa.getd(), rsa.getn()));

	}

}
