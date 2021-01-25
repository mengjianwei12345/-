




public class AutokeyCipherImpl implements IAutokeyCipher{

	@Override
	public String autokeyEncode(String phrase, String key) throws CipherException{
		
		// Check if key contains special characters or numbers
		if(key.matches("[\\d\\W_]")) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key should not contain numbers and special characters!"));
		}

		// Convert phrase to char array
		char[]wordArray = phrase.toCharArray();
		
		// Remove whitespaces from phrase
		String trimmedPhrase = phrase.replace(" ", "").toLowerCase();
		
		//Append key and trimmed phrase
		String keyPhrase = (key.toLowerCase() + trimmedPhrase); 
		keyPhrase = keyPhrase.substring(0, phrase.length());
		
		//Instantiate tableau
		char[][]tableau;
		
		// Encode phrase
		int keyPhraseCounter = 0;
		for(int i=0; i<wordArray.length; i++) {
			
			if(GlobalMethod.checkForUpperCase(phrase.charAt(i))) {
				
				tableau = GlobalMethod.upperCaseTableau();
				String alphabet = GlobalMethod.alphabet.toUpperCase();
				
				int row = alphabet.indexOf(wordArray[i]);
				int column = alphabet.indexOf(keyPhrase.toUpperCase().charAt(keyPhraseCounter++));
				
				wordArray[i] = tableau[row][column];
				
				
			}else if(GlobalMethod.checkForLowerCase(phrase.charAt(i))) {
				
				tableau = GlobalMethod.lowerCaseTableau();
				String alphabet = GlobalMethod.alphabet.toLowerCase();
				
				int row = alphabet.indexOf(wordArray[i]);
				int column = alphabet.indexOf(keyPhrase.charAt(keyPhraseCounter++));
				
				wordArray[i] = tableau[row][column];
				
			}else {
				continue;
			}
			
		}
		
		return new String(wordArray);
	}

	@Override
	public String autokeyDecode(String phrase, String key) throws CipherException {
		
		// Check if key contains special characters or numbers
		if(key.matches("[\\d\\W_]")) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key should not contain numbers and special characters!"));
		}
		
		// Convert phrase to char array
		char[]wordArray = phrase.toCharArray();
		
		// Convert key to lower case
		String keyPhrase = key.toLowerCase();
		
		// Instantiate Tableau
		char[][] tableau;
		
		// Decode Message
		int keyPhraseCounter = 0;
		outerLoop:
		for(int i=0; i<wordArray.length; i++) {
			
			if(GlobalMethod.checkForUpperCase(phrase.charAt(i))) {
				
				tableau = GlobalMethod.upperCaseTableau();
				String alphabet = GlobalMethod.alphabet.toUpperCase();
				
				int row = alphabet.indexOf(Character.toUpperCase(keyPhrase.charAt(keyPhraseCounter)));
				
				
				if(keyPhrase.length() >= phrase.length()) {
					
					for(int j=0; j<tableau.length; j++) {
						if(tableau[j][row] == wordArray[i]) {
							wordArray[i] = tableau[j][0];
							keyPhraseCounter++;
							continue outerLoop;
						}
					}
				}else {
					for(int j=0; j<tableau.length; j++) {
						if(tableau[j][row] == wordArray[i]) {
							wordArray[i] = tableau[j][0];
							keyPhrase += tableau[j][0];
							keyPhraseCounter++;
							continue outerLoop;
						}
					}
				}
				
				
				
			}else if(GlobalMethod.checkForLowerCase(phrase.charAt(i))) {
				
				tableau = GlobalMethod.lowerCaseTableau();
				String alphabet = GlobalMethod.alphabet.toLowerCase();
				
				int row = alphabet.indexOf(Character.toLowerCase(keyPhrase.charAt(keyPhraseCounter)));
				
				
				
				if(keyPhrase.length() >= phrase.length()) {
					for(int j=0; j<tableau.length; j++) {
						if(tableau[j][row] == wordArray[i]) {
							wordArray[i] = tableau[j][0];
							keyPhraseCounter++;
							continue outerLoop;
						}
					}
					
				}else {
					for(int j=0; j<tableau.length; j++) {
						
						if(tableau[j][row] == wordArray[i]) {
							wordArray[i] = tableau[j][0];
							keyPhrase += tableau[j][0];
							keyPhraseCounter++;
							
							continue outerLoop;
						}
					}
				}
				
				
			}else {
				continue;
			}
			
		}
		
		return new String(wordArray);
	}

}
