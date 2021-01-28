































 
public class AES128_CTR
{ 

    	public static SecretKey generateAESKey() throws Exception
        {
    		SecureRandom random = SecureRandom.getInstance("SHA1PRNG"); //creating a pseudo random number generator
		        KeyGenerator keyGen = KeyGenerator.getInstance("AES"); //creating a key generator instance
	        keyGen.init(128,random); //initializing the key generator with key size and a pseudo random number generator
	        SecretKey secretKey = keyGen.generateKey(); //generates a secret key
        		return secretKey; //return the secret key
        }
        public static byte[] encryptAES(SecretKey secretKey, byte data[]) throws Exception
        {
               Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding"); //create a cipher instance for aes encryption scheme with ctr mode and no padding
               cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[16])); //initialize the cipher by specifying the mode i.e. encryption, secret key and an initialization vector
               return cipher.doFinal(data); //performs and returns the encryption of data
        }
 
        public static byte[] decryptAES(SecretKey key, byte msg[]) throws Exception
        {
               Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding"); //create a cipher instance for aes encryption scheme with ctr mode and no padding
               cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16]));//initialize the cipher by specifying the mode i.e. decryption, secret key and an initialization vector
               return cipher.doFinal(msg);//performs and returns the decryption of data
        }
        void aes(String value) throws Exception 
    	{
        	InputStream is = null;
    		BufferedInputStream bis = null;
        	double entime=0, detime=0;//used to calculate the time per encryption and per decryption respectively
		double ensum=0, desum=0;//used to calculate the total time for 100 encryptions and 100 decryptions respectively
        	double totalentime[] = new double[100];//used to store the time per encryption for calculating median
        	double totatdetime[] = new double[100];//used to store the time per decryption for calculating median
        	System.out.println("---[AES128]---");
        	try 
    		{
    			for(int i = 1; i<=100;i++) //performing aes encryption and decryption 100 times
    			{
	    			is = new FileInputStream(value);//used to read input file
	    			bis = new BufferedInputStream(is);//used to read input file
	    			int length = bis.available();//calcuates the number of bytes in the input file
	    			byte data[] = new byte[length];//creating buffer which is used to store the input file
	    			SecretKey key = generateAESKey();//gets the secret key
	    			byte encrypted[] = new byte[length];//used to store the encrypted text
	    			byte decrypted[] = new byte[length];//used to store the decrypted text
	    			entime=0;
	    			detime=0;
	    			
	    				bis.read(data, 0, length);//read the entire file
	    	           	long startTime = System.nanoTime();//start counting time
	    				encrypted = encryptAES(key, data);//get the encrypted text
	    	            long difference = System.nanoTime() - startTime; //note the time taken for encryption           
	    	            double Seconds = (double)difference/ 1000000000.0; //bring the time in terms of seconds
	    				entime = Seconds; 
	    			totalentime[i-1] = entime;
				ensum += entime;
			
	    			is.close();
	    			bis.close();
	    			     	startTime = System.nanoTime();
	    				decrypted = decryptAES(key,encrypted);//get the decrypted text
	    	            difference = System.nanoTime() - startTime;            
	    	            Seconds = (double)difference/ 1000000000.0;
	    				detime = Seconds;
					totatdetime[i-1] = detime;
	    			
	    			desum += detime;
	    			
	    			System.out.println("["+i+"] AES128 Encrypt Running Time = "+entime);
	    			System.out.println("["+i+"] AES128 Decrypt Running Time = "+detime);					    			
	    			
    			}
    			System.out.println("AES 128 Encryption Mean :"+(ensum/100));
    			System.out.println("AES 128 Decryption Mean :"+(desum/100));
    			Arrays.sort(totatdetime);//sorting the arrays to obtain the median
    			Arrays.sort(totalentime);
    			System.out.println("Encryption Median :"+((totalentime[48]+totalentime[49])/2));
    			System.out.println("Decryption Median :"+((totatdetime[48]+totatdetime[49])/2));
    			
    			
    		}    		
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
}
