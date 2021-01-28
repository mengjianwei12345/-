



public class RailfenceCipherImpl implements IRailfenceCipher{

	@Override
	public String railfenceEncode(String phrase, int key) throws CipherException {
		
		if(key < 0) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key should not be less than 0"));
		}
		
		//Instantiate Word rail for Railfence cipher
		char[][]wordRail = new char[key][phrase.length()];
		
		//Fill the rail with a random character
		for(int i=0; i<key; i++) {
			for(int j=0; j<phrase.length(); j++) {
				wordRail[i][j] = '-';
			}
		}
		
		//To check whether to go up or down the rail
		boolean goingDown = false;
		int row = 0;
		int column = 0;
		
		//Populate rail with the phrase
		for(int i=0; i<phrase.length(); i++) {
			
			//Check direction of filling
			if(row == 0 || row == (key-1)) {
				goingDown = !goingDown;
			}
				
			//Fill the text in the rail
			wordRail[row][column++] = phrase.charAt(i);
			
			//Check if going up or down
			row = goingDown ? ++row : --row; 
			
		}
		
		//Construct the word
		char[]wordArray = new char[phrase.length()];
		int counter = 0;
		
		for(int i=0; i<key; i++) {
			for(int j=0; j<phrase.length(); j++) {
				
				if(wordRail[i][j] != '-') {
					wordArray[counter] = wordRail[i][j];
					counter++;
				}
				
			}
		}
		
		return new String(wordArray);
	}

	@Override
	public String railfenceDecode(String phrase, int key) throws CipherException {
		
		if(key < 0) {
			throw new CipherException(CipherException.INVALID_KEY, new Throwable("Key should not be less than 0"));
		}
		
		//Instantiate Word rail for Railfence cipher
		char[][]wordRail = new char[key][phrase.length()];
		
		//Fill the rail with a random character
		for(int i=0; i<key; i++) {
			for(int j=0; j<phrase.length(); j++) {
				wordRail[i][j] = '-';
			}
		}
		
		//To check whether to go up or down the rail
		boolean goingDown = false;
		int row = 0;
		int column = 0;
		
		//Populate rail with markers
		for(int i=0; i<phrase.length(); i++) {
			
			//Check direction of filling
			if(row == 0 || row == (key-1)) {
				goingDown = !goingDown;
			}
				
			//place marker
			wordRail[row][column++] = '*';
			
			//Check if going up or down
			row = goingDown ? ++row : --row; 
			
		}
		
		//Construct the rail to replace markers
		int index = 0;
		for(int i=0; i<key; i++) {
			for(int j=0; j<phrase.length(); j++) {
				
				if(wordRail[i][j] == '*' && index < phrase.length()) {
					wordRail[i][j] = phrase.charAt(index++);
				}
				
			}
		}
		
		//Construc the decoded message
		char[]wordArray = new char[phrase.length()];
		
		int counter = 0;
		row = 0;
		column = 0;
		goingDown = false;
		
		for(int i=0; i<phrase.length(); i++) {
			
			if(row == 0 || row == (key-1)) {
				goingDown = !goingDown;
			}
			
			if(wordRail[row][column] != '*') {
				wordArray[counter++] = wordRail[row][column++];
			}
			
			row = goingDown ? ++row : --row; 
			
		}
		
		return new String(wordArray);
	}
 
}
