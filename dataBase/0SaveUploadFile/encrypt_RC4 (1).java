
/**
 * Implementacao do algoritmo RC4 stream cipher.
 * 
 * [PRIMEIRA OPCAO DE EXECUCAO] 
 * 1. Abra sua IDE [Eclipse/NetBeans]; 
 * 2. Carreque o projeto, selecionando o diret�rio `RC4/`; 
 * 3. Compile o projeto; 
 * 4. Execute o arquivo `RC4.java`, pois este possui a main.
 * 
 * [SEGUNDA OPCAO DE EXECUCAO] 
 * 1. Abra seu terminal [BASH/CMD]; 
 * 2. V� at� o diret�rio onde se encontra o arquivo `RC4.java`; 
 * 3. Digite em seu console: 'nano RC4.java', delete a primeira linha do arquivo e salve-o; 
 * 4. Digite em seu console: 'javac RC4.java'; 
 * 5. Digite em seu console: 'java RC4'.
 * 
 * @author Yuri Oliveira Alves.
 * @see <a href="https://github.com/yurioliveira3">Meu reposit�rio no GitHub</a>
 */
public class RC4 {

	/**
	 * Arrays S e T, utilizados para permutacao de bytes.
	 */
	static short[] S;
	static short[] T;

	/**
	 * Construtor da classe RC4.
	 *
	 * @param keyString, 'seed' utilizada para a criacao da key.
	 * @throws IllegalArgumentException, exception utilizada caso o argumento seja
	 *                                   invalido.
	 */
	public RC4(String keyString) throws IllegalArgumentException {

		try {
			if (keyString.length() < 1 && keyString.length() > 256)
				throw new IllegalArgumentException();

			byte[] tempKey = keyString.getBytes();
			short[] key = new short[tempKey.length];

			for (int i = 0; i < tempKey.length; i++)
				key[i] = (short) ((short) tempKey[i] & 0xff);

			KSA(key);

		} catch (IllegalArgumentException ex) {
			System.out.println("O tamanho da Key deve estar entre 1 e  256");
			ex.printStackTrace();
		}

	}

	/**
	 * Metodo Ksa [key-scheduling], usado para inicializar a permutacao no array S.
	 *
	 * @param key chave a ser utilizada para a cifra. 
	 * {@code 
	 * 		Funcao utilizada para copiar o array S em T,
	 * 		System.arraycopy(Object src, int srcPos, Object dest, int destPos, Object src.length); 
	 * }
	 */
	private void KSA(short[] key) {

		S = new short[256];
		T = new short[256];

		for (int i = 0; i < 256; i++)
			S[i] = (short) i;

		int j = 0;
		for (int i = 0; i < 256; i++) {
			j = (j + S[i] + key[i % key.length]) % 256;
			swap(i, j, S);
		}

		System.arraycopy(S, 0, T, 0, S.length);

	}

	/**
	 * Metodo PRGA [Stream Generator], utilizado para alterar o estado e a saida dos
	 * bytes.
	 *
	 * @param length, tamanho do texto a ser encriptado.
	 * @return byte[], retorna um array com a chave a ser utilizada na criptografia.
	 */
	private byte[] PRGA(int length) {

		System.arraycopy(S, 0, T, 0, S.length);
		int i = 0, j = 0;
		byte[] tempPpad = new byte[length];

		for (int k = 0; k < length; k++) {
			i = (i + 1) % 256;
			j = (j + T[i]) % 256;

			swap(i, j, T);

			tempPpad[k] = (byte) (T[(T[i] + T[j]) % 256]);
		}

		return tempPpad;

	}

	/**
	 * Metodo Encrypt, utilizado para cifrar a mensagem, com a chave gerada.
	 *
	 * @param plain, o texto a ser cifrado.
	 * @return byte[], retorna o texto cifrado byte a byte com a chave gerada.
	 */
	private byte[] encrypt(byte[] plain) {

		byte[] pad = PRGA(plain.length);
		byte[] encrypt = new byte[plain.length];

		for (int i = 0; i < plain.length; i++)
			encrypt[i] = (byte) (plain[i] ^ pad[i]); // byte XOR byte

		return encrypt;

	}

	/**
	 * Metoro Swap, utilizado para troca as posicoes entre dois elementos de um
	 * vetor.
	 *
	 * @param i     atual.
	 * @param j     atual.
	 * @param vetor a ser embaralhado S
	 */
	private void swap(int i, int j, short[] S) {

		short temp = S[i];
		S[i] = S[j];
		S[j] = temp;

	}

	/**
	 * Metodo Principal, utilizado na inicializacao do programa
	 * 
	 * @see <a href="https://pt.wikipedia.org/wiki/RC4">RC4</a>
	 * 
	 * TESTES VALIDADOS 
	 * 'KEY' 	|| 'PLAINTEXT' 		|| 'CIPHERTEXT' 
	 * Key 		|| Plaintext 		|| BBF316E8D940AF0AD3
	 * Wiki 	|| pedia 			|| 1021BF0420 
	 * Secret 	|| Attack at dawn	|| 45A01F645FC35B383552544B9BF5
	 */
	public static void main(String[] args) {

		byte[] key = "Key".getBytes();
		RC4 rc = new RC4(new String(key));

		String plainText = "Plaintext";

		System.out.println("Texto Original:\t" + plainText);

		byte[] enText = rc.encrypt(plainText.getBytes());

		System.out.print("Texto Cifrado:\t");
		for (int i = 0; i < enText.length; i++)
			System.out.print(String.format("%02X", enText[i]));

		byte[] deText = rc.encrypt(enText);
		System.out.println("\nTexto Decifrado:" + new String(deText));

	}

}
