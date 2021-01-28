

























class Keys //data structure used for storing both the keys
{
	PublicKey pk;
	PrivateKey pr;
}

public class RSA_4096 {

	public static Keys generateRSAKey() throws Exception
    {
		//Generates secure keys (public and private)
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");//creating a pseudo random number generator
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");//creating a key pair generator instance
        	kpg.initialize(4096,random);//initializing the key pair generator with key size and a pseudo random number generator
        	KeyPair kp = kpg.genKeyPair(); ////generates a key pair
        	Keys k = new Keys();
        	k.pk = kp.getPublic(); //get the public key
        	k.pr = kp.getPrivate(); //get the private key
        	return k; //return the keys
    }
	
	public static byte[] rsaEncrypt(byte[] original, PublicKey key) throws Exception
	  {
		//Generates a cipher block using RSA in ECB mode.
	      Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");//create a cipher instance for rsa encryption scheme with ecb mode and no padding
	      cipher.init(Cipher.ENCRYPT_MODE, key); //initialize the cipher by specifying the mode i.e. encryption and public key
	      return cipher.doFinal(original); //performs and returns the encryption of data
	  }
	
	public static byte[] rsaDecrypt(byte[] encrypted, PrivateKey key) throws Exception
	  {
		//Decryptes a cipher block using RSA in ECB mode.
	      Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");//create a cipher instance for rsa decryption scheme with ecb mode and no padding
	      cipher.init(Cipher.DECRYPT_MODE, key);//initialize the cipher by specifying the mode i.e. decryption and private key
	      return cipher.doFinal(encrypted);//performs and returns the decryption of data
	  }
	
	void rsa(String value) throws Exception 
	{
    	InputStream is = null;
		BufferedInputStream bis = null;
		double entime=0, detime=0;//used to calculate the time per encryption and per decryption respectively
		double ensum=0, desum=0;//used to calculate the total time for 2 encryptions and 2 decryptions respectively
		System.out.println("---RSA 4096---");
		try 
        {
			for(int i = 0; i<2 ;i++)  // performing encryption and decryption 2 times
			{
			is = new FileInputStream(value);//used to read input file
			bis = new BufferedInputStream(is);//used to read input file
			int length = bis.available();//calcuates the number of bytes in the input file
			byte data[] = new byte[length];//creating buffer which is used to store the input file
			Keys key = generateRSAKey();//gets the secret key pair
			byte e[] = new byte[length];//used to store the encrypted text
			byte d[] = new byte[length];//used to store the decrypted text
			byte encrypted[] = new byte[512];//used to store the encrypted block
			byte decrypted[] = new byte[512];//used to store the decrypted block
			entime=0; detime=0; 
			bis.read(data, 0, length);//read the entire file
			int blocks = length/512;//calculate the number of 128 blocks in the input file 
			int b = 0;
			while(b<blocks)  //performing encryption block by block
			{
					byte bb[] = Arrays.copyOfRange(data, b*512, (b+1)*512); //get the ith plain text block from data
					long startTime = System.nanoTime();//start counting time
					encrypted = rsaEncrypt(bb, key.pk ); //get the encrypted block
    	            long difference = System.nanoTime() - startTime; //note the time taken for encryption
    	            double Seconds = (double)difference/ 1000000000.0; //bring the time in terms of seconds
    				entime += Seconds;
    				System.arraycopy(encrypted, 0, e, b*512, 512);//store the ith encrypted block in e
					b++;
			}
			
			is.close();
			bis.close();
			b=0;
			while(b<blocks)  //performing encryption block by block
			{
				byte bb[] = Arrays.copyOfRange(e, b*512, (b+1)*512);//get the ith cipher text block from e
				long startTime = System.nanoTime();//start counting time
				decrypted = rsaDecrypt(bb, key.pr);//get the decrypted block
	            long difference = System.nanoTime() - startTime; //note the time taken for encryption
	            double Seconds = (double)difference/ 1000000000.0;//bring the time in terms of seconds
				detime += Seconds;
				System.arraycopy(decrypted, 0, d, b*512, 512);//store the ith decrypted block in d
				b++;
			}

			System.out.println("["+i+"] RSA Encrypt Running Time = "+entime);
			System.out.println("["+i+"] RSA Decrypt Running Time = "+detime);
			ensum += entime;
			desum += detime;
		}
		System.out.println("Encryption Mean and Median: "+(ensum/2)); //as we are performing rsa encryption and decryption only for 2 times the median will be same as mean.
		System.out.println("Decryption Mean and Median: "+(desum/2));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
