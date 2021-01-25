




public class PolybiusSquareCipherImpl implements IPolybiusSquareCipher{
	
	

	@Override
	public String polybiusSquareEncode(String phrase, String key, String cipherCharacter) throws CipherException {
		
		// Check if key contains special characters or numbers
		if(key.matches("[\\d\\W_]")) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key should not contain numbers and special characters!"));
		}
		
		//If length is not equal to 5
		if(cipherCharacter.length() != 5) {
			throw new CipherException(CipherException.INVALID_CIPHER_CHARACTER, new Throwable("Cipher character length should be equal to 5"));
		}
		
		//Check if cipher characters is unique
		for(int i=0; i<cipherCharacter.length(); i++) {
			for(int j=0; j<cipherCharacter.length(); j++) {
				if(j != i && cipherCharacter.charAt(j) == cipherCharacter.charAt(i)) {
					throw new CipherException(CipherException.INVALID_CIPHER_CHARACTER, new Throwable("Cipher character should be unique to each other"));
				}
			}
		}
		
		//Check if key characters is unique
		for(int i=0; i<key.length(); i++) {
			for(int j=0; j<key.length(); j++) {
				if(j != i && key.charAt(i) == key.charAt(j)) {
					throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key must consist of unique characters"));
				}
			}
		}
			
		//Create 5x5 table for key
		char[][] polybiusTable = new char[5][5];
		int counter = 0;
		
		for(int i=0; i<5; i++) {
			for(int j=0; j<5; j++) {
				
				polybiusTable[i][j] = key.charAt(counter++);
			}
		}
		
		
		//Replace all j with i
		phrase = phrase.replace("j", "j");
		
		StringBuilder encodedMessage = new StringBuilder();
		//Encode phrase
		phraseLoop:
		for(int i=0; i<phrase.length(); i++) {
			for(int j=0; j<5; j++) {
				for(int k=0; k<5; k++) {
					if(GlobalMethod.checkForUpperCase(phrase.charAt(i))) {
						if(Character.toUpperCase(polybiusTable[j][k]) == phrase.charAt(i)) {
							encodedMessage.append(cipherCharacter.charAt(j));
							encodedMessage.append(cipherCharacter.charAt(k));
							continue phraseLoop;
						}
					}else if(GlobalMethod.checkForLowerCase(phrase.charAt(i))) {
						if(Character.toLowerCase(polybiusTable[j][k]) == phrase.charAt(i)) {
							encodedMessage.append(Character.toLowerCase(cipherCharacter.charAt(j)));
							encodedMessage.append(Character.toLowerCase(cipherCharacter.charAt(k)));
							continue phraseLoop;
						}
					}else {
						encodedMessage.append(phrase.charAt(i));
						continue phraseLoop;
					}
					
				}
			}
		}
		
		return new String(encodedMessage);
	}

	@Override
	public String polybiusSquareDecode(String phrase, String key, String cipherCharacter) throws CipherException{
		
		// Check if key contains special characters or numbers
		if(key.matches("[\\d\\W_]")) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key should not contain numbers and special characters!"));
		}
		
		//If length is not equal to 5
		if(cipherCharacter.length() != 5) {
			throw new CipherException(CipherException.INVALID_CIPHER_CHARACTER, new Throwable("Cipher character length should be equal to 5"));
		}
		
		//Check if cipher characters is unique
		for(int i=0; i<cipherCharacter.length(); i++) {
			for(int j=0; j<cipherCharacter.length(); j++) {
				if(j != i && cipherCharacter.charAt(j) == cipherCharacter.charAt(i)) {
					throw new CipherException(CipherException.INVALID_CIPHER_CHARACTER, new Throwable("Cipher character shoul be unique to each other"));
				}
			}
		}
		
		//Check if key characters is unique
		for(int i=0; i<key.length(); i++) {
			for(int j=0; j<key.length(); j++) {
				if(j != i && key.charAt(i) == key.charAt(j)) {
					throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key must consist of unique characters"));
				}
			}
		}
		
		//Create 5x5 table for key
		char[][] polybiusTable = new char[5][5];
		int counter = 0;
		
		for(int i=0; i<5; i++) {
			for(int j=0; j<5; j++) {
				
				polybiusTable[i][j] = key.charAt(counter++);
			}
		}
		
		StringBuilder encodedMessage = new StringBuilder();
		
		//Encode phrase
		phraseLoop:
		for(int i=0; i<phrase.length(); i++) {
			if(GlobalMethod.checkForUpperCase(phrase.charAt(i))) {
				int row = 0;
				int column = 0;
				for(int j=0; j<5; j++) {
					if(Character.toUpperCase(cipherCharacter.charAt(j)) == phrase.charAt(i))
						row = j;
					
					if(Character.toUpperCase(cipherCharacter.charAt(j)) == phrase.charAt(i+1))
						column = j;
				}
				encodedMessage.append(Character.toUpperCase(polybiusTable[row][column]));
				++i;
				continue phraseLoop;
			}else if(GlobalMethod.checkForLowerCase(phrase.charAt(i))) {
				int row = 0;
				int column = 0;
				for(int j=0; j<5; j++) {
					if(Character.toLowerCase(cipherCharacter.charAt(j)) == phrase.charAt(i))
						row = j;
					
					if(Character.toLowerCase(cipherCharacter.charAt(j)) == phrase.charAt(i+1))
						column = j;
				}
				encodedMessage.append(Character.toLowerCase(polybiusTable[row][column]));
				++i;
				continue phraseLoop;
			}else {
				encodedMessage.append(phrase.charAt(i));
				continue phraseLoop;
			}
		}
		
		return new String(encodedMessage);
	}

}
