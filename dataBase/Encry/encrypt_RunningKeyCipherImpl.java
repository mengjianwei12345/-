




public class RunningKeyCipherImpl implements IRunningKeyCipher{

	@Override
	public String runningKeyEncode(String phrase, String key) throws CipherException {
		
		// Trim key and convert to char array
		char[] trimmedKey = key.replace(" ", "").toCharArray();
		
		// Check key if it is longer or equal to phrase length
		if(trimmedKey.length < phrase.replace(" ", "").length()) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key length should be longer or equal to the phrase!"));
		}
		
		// Check if key contains special characters or numbers
		if(key.matches("[\\d\\W_]")) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key should not contain numbers and special characters!"));
		}
		
		// Convert phrase to char array
		char[]wordArray = phrase.toCharArray();
		
		// Instantiate Tableau
		char[][]tableau;
		
		// Instantiate alphabet
		String alphabet;
		
		// Encode phrase
		int trimmedKeyCounter = 0;
		for(int i=0; i<wordArray.length; i++) {
			
			if(GlobalMethod.checkForUpperCase(wordArray[i])) {
				
				tableau = GlobalMethod.upperCaseTableau();
				alphabet = GlobalMethod.alphabet.toUpperCase();
				
				int row = alphabet.indexOf(Character.toUpperCase(trimmedKey[trimmedKeyCounter++]));
				int column = alphabet.indexOf(wordArray[i]);
				
				wordArray[i] = tableau[column][row];
				
				continue;
				
			}else if(GlobalMethod.checkForLowerCase(wordArray[i])) {
				
				tableau = GlobalMethod.lowerCaseTableau();
				alphabet = GlobalMethod.alphabet.toLowerCase();
				
				int row = alphabet.indexOf(Character.toLowerCase(trimmedKey[trimmedKeyCounter++]));
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
	public String runningKeyDecode(String phrase, String key) throws CipherException {
		
		// Trim key and convert to char array
		char[] trimmedKey = key.replace(" ", "").toCharArray();
		
		// Check key if it is longer or equal to phrase length
		if(trimmedKey.length < phrase.replace(" ", "").length()) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key length should be longer or equal to the phrase!"));
		}
		
		// Check if key contains special characters or numbers
		if(key.matches("[\\d\\W_]")) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key should not contain numbers and special characters!"));
		}
		
		// Convert phrase to char array
		char[]wordArray = phrase.toCharArray();
		
		// Instantiate Tableau
		char[][]tableau;
		
		// Instantiate alphabet
		String alphabet;
		
		// Encode phrase
		int trimmedKeyCounter = 0;
		outerLoop:
		for(int i=0; i<wordArray.length; i++) {
			
			if(GlobalMethod.checkForUpperCase(wordArray[i])) {
				
				tableau = GlobalMethod.upperCaseTableau();
				alphabet = GlobalMethod.alphabet.toUpperCase();
				
				int row = alphabet.indexOf(Character.toUpperCase(trimmedKey[trimmedKeyCounter++]));
				
				for(int j=0; j<tableau.length; j++) {
					if(tableau[j][row] == wordArray[i]) {
						wordArray[i] = tableau[j][0];
						continue outerLoop;
					}
				}
				
				
			}else if(GlobalMethod.checkForLowerCase(wordArray[i])) {
				
				tableau = GlobalMethod.lowerCaseTableau();
				alphabet = GlobalMethod.alphabet.toLowerCase();
				
				int row = alphabet.indexOf(Character.toLowerCase(trimmedKey[trimmedKeyCounter++]));
				
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
