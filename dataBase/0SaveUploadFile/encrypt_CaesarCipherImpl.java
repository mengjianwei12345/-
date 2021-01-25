




public class CaesarCipherImpl implements ICaesarCipher{

	@Override
	public String caesarEncode(String phrase, int shift) throws CipherException{
		
		
		// Error checks
		//If shift is < 0 or > 25 throw CipherException Error
		if(shift < 0 || shift > 25) {
			throw new CipherException(CipherException.INVALID_SHIFT, new Throwable("Key shift should not be less than 0 and greater than 25."));
		}
		
		//Convert phrase to char array
		char[]wordArray = phrase.toCharArray();
		
		//Iterate phrase
		for(int i=0; i<wordArray.length; i++) {
			
			if(GlobalMethod.checkForUpperCase(wordArray[i])) { // Check if Upper case in ascii
				
				//Get value after adding the shift to the char
				int charCount = (char) (wordArray[i] + shift);
				
				if(charCount > 90) { // If charCount exceeded 90 after adding the shift
					// Get char from difference of charCount from 90 which is the limit and adding it to 65
					wordArray[i] = (char)(65 + (charCount - 90));
				}else {
					// If charCount doesn't exceed 90
					wordArray[i] = (char)charCount;
				}
				
			}else if(GlobalMethod.checkForLowerCase(wordArray[i])) { // Check if lower case in ascii
				
				int charCount = (char)(wordArray[i] + shift);
				if(charCount > 122) { // If charCount exceeded 122 after adding the shift
					// Get char from difference of charCount from 122 which is the limit and adding it to 97
					wordArray[i] = (char)(97 + (charCount - 122));
					
				}else {
					// If charCount doesn't exceed 122
					wordArray[i] = (char)charCount;
				}
				
			}else { // Special characters are skipped
				continue;
			}
		}
		return new String(wordArray);
	}

	@Override
	public String caesarDecode(String phrase, int shift) throws CipherException {
		
		// Error checks
		//If shift is < 0 or > 25 throw CipherException Error
		if(shift < 0 || shift > 25) {
			throw new CipherException(CipherException.INVALID_SHIFT, new Throwable("Key shift should not be less than 0 and greater than 25."));
		}
		
		//Convert phrase to char array
		char[]wordArray = phrase.toCharArray();
		
		//Iterate phrase
		for(int i=0; i<wordArray.length; i++) {
			
			if(GlobalMethod.checkForUpperCase(wordArray[i])) { // Check if Upper case in ascii
				
				int charCount = (char)(wordArray[i] - shift);
				if(charCount < 65) { // If charCount recede 65 after adding the shift.
					// Get char from difference of 65 from charCount which is the limit and adding it to 97
					wordArray[i] = (char)(90 - (65 - charCount));
				}else {
					wordArray[i] = (char)charCount;
				}
			}else if(GlobalMethod.checkForLowerCase(wordArray[i])) { // Check if lower case in ascii
				
				int charCount = (char)(wordArray[i] - shift);
				if(charCount < 97) {
					wordArray[i] = (char)(122 - (97 - charCount));
				}else {
					wordArray[i] = (char)charCount;
				}
			}else { // Special characters are skipped
				continue;
			}
		}
		return new String(wordArray);
	}

}
