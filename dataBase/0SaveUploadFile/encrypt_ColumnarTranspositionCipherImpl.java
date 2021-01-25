






public class ColumnarTranspositionCipherImpl implements IColumnarTranspositionCipher{

	@Override
	public String columnarTranspositionEncode(String phrase, String key, char characterPad) throws CipherException {
		
		// Check if key contains special characters or numbers
		if(key.matches("[\\d\\W_]")) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key should not contain numbers and special characters!"));
		}
		
		//Check if cipher characters is unique
		for(int i=0; i<key.length(); i++) {
			for(int j=0; j<key.length(); j++) {
				if(j != i && key.charAt(j) == key.charAt(i)) {
					throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key characters should be unique to each other"));
				}
			}
		}
		
		//Get row count
		int rowMod = phrase.length() % key.length();
		int row = rowMod > 0 ? ((phrase.length()/key.length()) + 2) : (phrase.length()/key.length() + 1);
		
		//Create dimension for columnar table
		char[][] columnarTable = new char[row][key.length()];
		
		// Convert phrase to lower case
		phrase = phrase.toLowerCase();
		key = key.toLowerCase();
		
		// Plot columnar table values
		int counter = 0;
		for(int i=0; i<row; i++) {
			for(int j=0; j<key.length(); j++) {
				if(i == 0) {
					columnarTable[0][j] = key.charAt(j);
				}else {
					if(counter >= phrase.length()) {
						columnarTable[i][j] = characterPad;
					}else {
						columnarTable[i][j] = phrase.charAt(counter++);
					}
				}
			}
		}
		
		// Sort 2d array by key
		for(int i=0; i<columnarTable[0].length; i++) {
			for(int j=0; j<(columnarTable[0].length-1); j++) {
				if(columnarTable[0][j] > columnarTable[0][j+1]) {
					
					char tempChar = columnarTable[0][j+1];
					columnarTable[0][j+1] = columnarTable[0][j];
					columnarTable[0][j] = tempChar;
					
					for(int k=1; k<columnarTable.length; k++) {
						
						tempChar = columnarTable[k][j+1];
						columnarTable[k][j+1] = columnarTable[k][j];
						columnarTable[k][j] = tempChar;
						
					}
				}
			}
		}
		
		// Build message
		StringBuilder encodedMessage = new StringBuilder();
		for(int i=0; i<columnarTable[0].length; i++) {
			for(int j=1; j<columnarTable.length; j++) {
				encodedMessage.append(columnarTable[j][i]);
			}
		}
		
		
		return new String(encodedMessage);
	}

	@Override
	public String columnarTranspositionDecode(String phrase, String key, char characterPad) throws CipherException {
		
		// Check if key contains special characters or numbers
		if(key.matches("[\\d\\W_]")) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key should not contain numbers and special characters!"));
		}
		
		//Check if cipher characters is unique
		for(int i=0; i<key.length(); i++) {
			for(int j=0; j<key.length(); j++) {
				if(j != i && key.charAt(j) == key.charAt(i)) {
					throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key characters should be unique to each other"));
				}
			}
		}
		
		// Convert key to char array
		char[]keyArray = key.toCharArray();
		
		//Get row count
		int rowMod = phrase.length() % key.length();
		int row = rowMod > 0 ? ((phrase.length()/key.length()) + 2) : (phrase.length()/key.length() + 1);
		
		//Create dimension for columnar table
		char[][] columnarTable = new char[row][key.length()];
		
		//Sort key
		Arrays.sort(keyArray);
		
		// Pass key to first row
		columnarTable[0] = keyArray;
		
		// Plot columnar table values
		int counter = 0;
		for(int i=0; i<columnarTable[0].length; i++) {
			for(int j=1; j<columnarTable.length; j++) {
				columnarTable[j][i] = phrase.charAt(counter++);
			}
		}
		
		//Rearrange table to original form
		outerLoop:
		for(int i=0; i<columnarTable[0].length; i++) {
			for(int j=0; j<key.length(); j++) {
				if(columnarTable[0][i] == key.charAt(j)) {
					
					char tempChar = columnarTable[0][j];
					columnarTable[0][j] = columnarTable[0][i];
					columnarTable[0][i] = tempChar;
					
					for(int k=1; k<columnarTable.length; k++) {
						
						tempChar = columnarTable[k][j];
						columnarTable[k][j] = columnarTable[k][i];
						columnarTable[k][i] = tempChar;
						
					}
					continue outerLoop;
				}
			}
		}
		
		// Build message
		StringBuilder encodedMessage = new StringBuilder();
		for(int i=1; i<columnarTable.length; i++) {
			for(int j=0; j<columnarTable[0].length; j++) {
				encodedMessage.append(columnarTable[i][j]);
			}
		}
		
		//Replace all character pad with blank
		return new String(encodedMessage).replace(Character.toString(characterPad), "");
	}

}
