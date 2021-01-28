




public class FourSquareCipherImpl implements IFourSquareCipher{

	@Override
	public String fourSquareEncode(String phrase, String firstKey, String secondKey, char characterPad) throws CipherException {

		// Check if key contains special characters or numbers
		if(firstKey.matches("[\\d\\W_]") || secondKey.matches("[\\d\\W_]")) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key should not contain numbers and special characters!"));
		}
		
		//Check if first key characters is unique
		for(int i=0; i<firstKey.length(); i++) {
			for(int j=0; j<firstKey.length(); j++) {
				if(j != i && firstKey.charAt(i) == firstKey.charAt(j)) {
					throw new CipherException(CipherException.INVALID_KEY, new Throwable("First key must consist of unique characters"));
				}
			}
		}
		
		//Check if second key characters is unique
		for(int i=0; i<secondKey.length(); i++) {
			for(int j=0; j<secondKey.length(); j++) {
				if(j != i && secondKey.charAt(i) == secondKey.charAt(j)) {
					throw new CipherException(CipherException.INVALID_KEY, new Throwable("Second key must consist of unique characters"));
				}
			}
		}
		
		//Check length of keys
		if(firstKey.length() > 25 || secondKey.length() > 25) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key should consist of unique alphabet letters without \"J\"! "));
		}
		
		// Instantiate Alphabet, First and second key table
		char[][]alphabetTable = new char[5][5];
		char[][]firstKeyTable = new char[5][5];
		char[][]secondKeyTable = new char[5][5];
		
		// Get randomized alphabet Strings
		String polybiusAlphabet = GlobalMethod.polybiusAlphabet;
		
		//Make copy of phrase without
		String trimmedPhrase = phrase.replaceAll("[\\d\\W_]", "").replace(" ", "").toLowerCase().replace("j", "i");
		
		//Append Extra character if phrase length is not even
		trimmedPhrase = trimmedPhrase.length()%2==0 ? trimmedPhrase : (trimmedPhrase+characterPad);
		
		//Plot values to the table
		int tableIndexCounter = 0;
		for(int i=0; i<alphabetTable.length; i++) {
			for(int j=0; j<alphabetTable.length; j++) {
				
				alphabetTable[i][j] = polybiusAlphabet.charAt(tableIndexCounter);
				firstKeyTable[i][j] = firstKey.charAt(tableIndexCounter);
				secondKeyTable[i][j] = secondKey.charAt(tableIndexCounter++);
			}
		}
		
		//Encode message
		StringBuilder encodedMessage = new StringBuilder();
		for(int i=0; i<trimmedPhrase.length(); i++) {
			int firstRow = 0;
			int firstColumn = 0;
			int secondRow = 0;
			int secondColumn = 0;
			for(int j=0; j<5; j++) {
				for(int k=0; k<5; k++) {
					
					if(alphabetTable[j][k] == trimmedPhrase.charAt(i)) {
						
						firstRow = k;
						firstColumn = j;
						
					}
					
					if(alphabetTable[j][k] == trimmedPhrase.charAt(i+1)) {
						
						secondRow = k;
						secondColumn = j;
						
					}
					
				}
			}
			
			encodedMessage.append(firstKeyTable[firstColumn][secondRow]);
			encodedMessage.append(secondKeyTable[secondColumn][firstRow]);
			++i;
		}
		
		return new String(encodedMessage);
	}

	@Override
	public String fourSquareDecode(String phrase, String firstKey, String secondKey, char characterPad) throws CipherException {
		
		// Check if key contains special characters or numbers
		if(firstKey.matches("[\\d\\W_]") || secondKey.matches("[\\d\\W_]")) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key should not contain numbers and special characters!"));
		}
		
		//Check if first key characters is unique
		for(int i=0; i<firstKey.length(); i++) {
			for(int j=0; j<firstKey.length(); j++) {
				if(j != i && firstKey.charAt(i) == firstKey.charAt(j)) {
					throw new CipherException(CipherException.INVALID_KEY, new Throwable("First key must consist of unique characters"));
				}
			}
		}
		
		//Check if second key characters is unique
		for(int i=0; i<secondKey.length(); i++) {
			for(int j=0; j<secondKey.length(); j++) {
				if(j != i && secondKey.charAt(i) == secondKey.charAt(j)) {
					throw new CipherException(CipherException.INVALID_KEY, new Throwable("Second key must consist of unique characters"));
				}
			}
		}
		
		//Check length of keys
		if(firstKey.length() > 25 || secondKey.length() > 25) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key should consist of unique alphabet letters without \"J\"! "));
		}
		
		// Instantiate Alphabet, First and second key table
		char[][]alphabetTable = new char[5][5];
		char[][]firstKeyTable = new char[5][5];
		char[][]secondKeyTable = new char[5][5];
		
		// Get randomized alphabet Strings
		String polybiusAlphabet = GlobalMethod.polybiusAlphabet;
		
		//Make copy of phrase without
		String trimmedPhrase = phrase.replaceAll("[\\d\\W_]", "").replace(" ", "").toLowerCase().replace("j", "i");
		
		//Append Extra character if phrase length is not even
		trimmedPhrase = trimmedPhrase.length()%2==0 ? trimmedPhrase : (trimmedPhrase+characterPad);
		
		//Plot values to the table
		int tableIndexCounter = 0;
		for(int i=0; i<alphabetTable.length; i++) {
			for(int j=0; j<alphabetTable.length; j++) {
				
				alphabetTable[i][j] = polybiusAlphabet.charAt(tableIndexCounter);
				firstKeyTable[i][j] = firstKey.charAt(tableIndexCounter);
				secondKeyTable[i][j] = secondKey.charAt(tableIndexCounter++);
			}
		}
		
		//Encode Message
		StringBuilder encodedMessage = new StringBuilder();
		for(int i=0; i<trimmedPhrase.length(); i++) {
			
			int firstRow = 0;
			int firstColumn = 0;
			int secondRow = 0;
			int secondColumn = 0;
			
			for(int j=0; j<alphabetTable.length; j++) {
				for(int k=0; k<alphabetTable.length; k++) {
					
					if(firstKeyTable[j][k] == trimmedPhrase.charAt(i)) {
						firstColumn = j;
						firstRow = k;
					}
					
					if(secondKeyTable[j][k] == trimmedPhrase.charAt(i+1)) {
						secondColumn = j;
						secondRow = k;
					}
					
				}
			}
			
			encodedMessage.append(alphabetTable[firstColumn][secondRow]);
			encodedMessage.append(alphabetTable[secondColumn][firstRow]);
			++i;
			
		}
		return new String(encodedMessage).replace(Character.toString(characterPad), "");
	}

}
