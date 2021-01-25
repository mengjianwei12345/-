




public class VigenereCipherImpl implements IVigenereCipher{

	@Override
	public String vigenereEncode(String phrase, String key) throws CipherException {
		// Check if key contains special characters or numbers
		if(key.matches("[\\d\\W_]")) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key should not contain numbers and special characters!"));
		}
		
		// Convert phrase to char array
		char[] wordArray = phrase.toCharArray();
		
		// Get key phrase
		int trimmedPhraseLength = phrase.replace(" ", "").length();
		int keyCounter = 0;
		char[] keyPhrase = new char[trimmedPhraseLength];
		for(int i=0; i<keyPhrase.length; i++) {
			keyCounter = keyCounter >= key.length() ? 0 : keyCounter;
			keyPhrase[i] = key.charAt(keyCounter++); 
		}
		
		// Instantiate Tableau
		char[][]tableau;
		
		//Instantiate Alphabet String
		String alphabet;
		
		//Encode message
		int keyPhraseCounter = 0;
		for(int i=0; i<wordArray.length; i++) {
			
			if(GlobalMethod.checkForUpperCase(wordArray[i])) {
				
				tableau = GlobalMethod.upperCaseTableau();
				alphabet = GlobalMethod.alphabet.toUpperCase();
				
				int row = alphabet.indexOf(Character.toUpperCase(keyPhrase[keyPhraseCounter++]));
				int column = alphabet.indexOf(wordArray[i]);
				
				wordArray[i] = tableau[column][row];
				
				continue;
				
				
			}else if(GlobalMethod.checkForLowerCase(wordArray[i])) {
				
				tableau = GlobalMethod.lowerCaseTableau();
				alphabet = GlobalMethod.alphabet.toLowerCase();
				
				int row = alphabet.indexOf(Character.toLowerCase(keyPhrase[keyPhraseCounter++]));
				int column = alphabet.indexOf(wordArray[i]);
				
				wordArray[i] = tableau[column][row];
				
				continue;
				
			}else {
				continue;
			}
			
			
		}
		
		return new String(wordArray);
	}

	@Override
	public String vigenereDecode(String phrase, String key) throws CipherException {
		
		// Check if key contains special characters or numbers
		if(key.matches("[\\d\\W_]")) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key should not contain numbers and special characters!"));
		}
		
		
		// Convert phrase to char array
		char[] wordArray = phrase.toCharArray();
		
		// Get key phrase
		int trimmedPhraseLength = phrase.replace(" ", "").length();
		int keyCounter = 0;
		char[] keyPhrase = new char[trimmedPhraseLength];
		for(int i=0; i<keyPhrase.length; i++) {
			keyCounter = keyCounter >= key.length() ? 0 : keyCounter;
			keyPhrase[i] = key.charAt(keyCounter++); 
		}
		
		// Instantiate Tableau
		char[][]tableau;
		
		//Instantiate Alphabet String
		String alphabet;
		
		// Decode message
		int keyPhraseCounter = 0;
		outerLoop:
		for (int i=0; i<wordArray.length; i++) {
			
			if(GlobalMethod.checkForUpperCase(wordArray[i])) {
				
				tableau = GlobalMethod.upperCaseTableau();
				alphabet = GlobalMethod.alphabet.toUpperCase();
				
				int row = alphabet.indexOf(Character.toUpperCase(keyPhrase[keyPhraseCounter++]));
				
				for(int j=0; j<tableau.length; j++) {
					
					if(tableau[j][row] == wordArray[i]) {
						wordArray[i] = tableau[j][0];
						continue outerLoop;
					}
					
				}
				
			}else if(GlobalMethod.checkForLowerCase(wordArray[i])) {
				
				tableau = GlobalMethod.lowerCaseTableau();
				alphabet = GlobalMethod.alphabet.toLowerCase();
				
				int row = alphabet.indexOf(Character.toLowerCase(keyPhrase[keyPhraseCounter++]));
				
				for(int j=0; j<tableau.length; j++) {
					
					if(tableau[j][row] == wordArray[i]) {
						wordArray[i] = tableau[j][0];
						continue outerLoop;
					}
					
				}
				
			}else {
				continue;
			}
			
		}
		
		
		return new String(wordArray);
	}

}
