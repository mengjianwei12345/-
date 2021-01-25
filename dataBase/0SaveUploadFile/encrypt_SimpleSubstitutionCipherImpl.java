





public class SimpleSubstitutionCipherImpl implements ISimpleSubstitutionCipher{

	@Override
	public String simpleSubstitutionEncode(String phrase, String key) throws CipherException {

		// Error check
		
		// Check if key contains special characters or numbers
		if(key.matches("[\\d\\W_]")) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key should not contain numbers and special characters!"));
		}
		
		//Check if key characters is unique
		for(int i=0; i<key.length(); i++) {
			for(int j=0; j<key.length(); j++) {
				if(j != i && key.charAt(i) == key.charAt(j)) {
					throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key must consist of unique characters"));
				}
			}
		}
		
		// Convert phrase to word array
		char[]wordArray = phrase.toCharArray();
		
		// Iterate phrase
		for(int i=0; i<wordArray.length; i++) {
			
			if(GlobalMethod.checkForUpperCase(wordArray[i])) {
				
				String alphabet = GlobalMethod.alphabet.toUpperCase();
				key = key.toUpperCase();
				
				wordArray[i] = key.charAt(alphabet.indexOf(wordArray[i]));
				
				
			}else if(GlobalMethod.checkForLowerCase(wordArray[i])) {
				
				String alphabet = GlobalMethod.alphabet;
				key = key.toLowerCase();
				
				wordArray[i] = key.charAt(alphabet.indexOf(wordArray[i]));
				
			}else {
				continue;
			}
			
		}
		
		
		return new String(wordArray);
	}

	@Override
	public String simpleSubstitutionDecode(String phrase, String key) throws CipherException {
		
		// Error check
		
		// Check if key contains special characters or numbers
		if(key.matches("[\\d\\W_]")) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key should not contain numbers and special characters!"));
		}
		
		//Check if key characters is unique
		for(int i=0; i<key.length(); i++) {
			for(int j=0; j<key.length(); j++) {
				if(j != i && key.charAt(i) == key.charAt(j)) {
					throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key must consist of unique characters"));
				}
			}
		}
		
		// Convert phrase to word array
		char[]wordArray = phrase.toCharArray();
		
		// Iterate phrase
		for(int i=0; i<wordArray.length; i++) {
			
			if(GlobalMethod.checkForUpperCase(wordArray[i])) {
				
				String alphabet = GlobalMethod.alphabet.toUpperCase();
				key = key.toUpperCase();
				
				wordArray[i] = alphabet.charAt(key.indexOf(wordArray[i]));
				
				
			}else if(GlobalMethod.checkForLowerCase(wordArray[i])) {
				
				String alphabet = GlobalMethod.alphabet;
				key = key.toLowerCase();
				
				wordArray[i] = alphabet.charAt(key.indexOf(wordArray[i]));
				
			}else {
				continue;
			}
			
		}
		
		
		return new String(wordArray);
	}

}
