




public class BeaufortCipherImpl implements IBeaufortCipher{

	@Override
	public String beaufortEncode(String phrase, String key) throws CipherException {
		
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
		
		//Instantiate Tableau
		char[][]tableau;
		
		// Encode phrase
		int keyPhraseCounter = 0;
		outerLoop:
		for(int i=0; i<wordArray.length; i++) {
			
			if(GlobalMethod.checkForUpperCase(phrase.charAt(i))) {
				
				tableau = GlobalMethod.upperCaseTableau();
				String alphabet = GlobalMethod.alphabet.toUpperCase();
				
				int row = alphabet.indexOf(wordArray[i]);
				
				char keyPhraseToCompare = Character.toUpperCase(keyPhrase[keyPhraseCounter]);
				
				for(int j=0; j<tableau.length; j++) {
					if(tableau[j][row] == keyPhraseToCompare) {
						wordArray[i] = tableau[j][0];
						keyPhraseCounter++;
						continue outerLoop;
					}
				}
				
				
			}else if(GlobalMethod.checkForLowerCase(phrase.charAt(i))) {
				
				tableau = GlobalMethod.lowerCaseTableau();
				String alphabet = GlobalMethod.alphabet.toLowerCase();
				
				int row = alphabet.indexOf(wordArray[i]);
				
				char keyPhraseToCompare = Character.toLowerCase(keyPhrase[keyPhraseCounter]);
				
				for(int j=0; j<tableau.length; j++) {
					if(tableau[j][row] == keyPhraseToCompare) {
						wordArray[i] = tableau[j][0];
						keyPhraseCounter++;
						continue outerLoop;
					}
				}
				
			}else {
				continue;
			}
			
		}
		
		
		return new String(wordArray);
	}

	@Override
	public String beaufortDecode(String phrase, String key) throws CipherException {
		
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
		
		//Instantiate Tableau
		char[][]tableau;
		
		// Decode phrase
		int keyPhraseCounter = 0;
		outerLoop:
		for(int i=0; i<wordArray.length; i++) {
			
			if(GlobalMethod.checkForUpperCase(phrase.charAt(i))) {
				
				tableau = GlobalMethod.upperCaseTableau();
				String alphabet = GlobalMethod.alphabet.toUpperCase();
				
				int column = alphabet.indexOf(wordArray[i]);
				
				char keyPhraseToCompare = Character.toUpperCase(keyPhrase[keyPhraseCounter]);
				
				for(int j=0; j<tableau.length; j++) {
					if(tableau[column][j] == keyPhraseToCompare) {
						wordArray[i] = tableau[0][j];
						keyPhraseCounter++;
						continue outerLoop;
					}
				}
				
				
			}else if(GlobalMethod.checkForLowerCase(phrase.charAt(i))) {
				
				tableau = GlobalMethod.lowerCaseTableau();
				String alphabet = GlobalMethod.alphabet.toLowerCase();
				
				int column = alphabet.indexOf(wordArray[i]);
				
				char keyPhraseToCompare = Character.toLowerCase(keyPhrase[keyPhraseCounter]);
				
				for(int j=0; j<tableau.length; j++) {
					if(tableau[column][j] == keyPhraseToCompare) {
						wordArray[i] = tableau[0][j];
						keyPhraseCounter++;
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
